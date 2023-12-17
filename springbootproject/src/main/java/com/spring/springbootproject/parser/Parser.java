package com.spring.springbootproject.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.xml.sax.SAXException;

import com.spring.springbootproject.entity.Emission;

public class Parser {
	
	private HashMap<String, String> map; 
	
	
	public Parser() {}
	
	
	public void setMap(HashMap<String, String> map){
		this.map = map;
	}
	
	
	public HashMap<String, String> descriptionMap() throws IOException {
		
		FileInputStream descriptionFile = new FileInputStream("EmissionsDescriptions.xlsx");
		HashMap<String, String> map = new HashMap<String, String>();
		
		XSSFWorkbook workBook = new XSSFWorkbook(descriptionFile);
		XSSFSheet workSheet = workBook.getSheetAt(0);
		
		//Might be diff iterator
		Iterator<Row> rowIterator = workSheet.iterator();
		
		while(rowIterator.hasNext()) {
			
			Row row = rowIterator.next();
			
			Iterator<Cell> cellIterator = row.cellIterator();
			
			while(cellIterator.hasNext()) {
				
				Cell cell = cellIterator.next();
				String cellStr = cell.getStringCellValue();
				String keyCellStr = cellStr.split(" ")[0];

				map.put(keyCellStr, cellStr);			
			}
		}
		workBook.close();
		descriptionFile.close();
		
		return map;
	}
	
	
	//Parse JSON function
	public ArrayList<Emission> parseJSON() throws ParseException, IOException {
		
		//Declare emissions file and empty json string 
		File jsonFile = new File("GreenhouseGasEmissions2023.json");
		String jsonStr = "";
		ArrayList<Emission> jsonEmissions = new ArrayList<Emission>();
		HashMap<String, String> descriptionMap = map;
		boolean addEmission = true;
		
		//Use a scanner to populate json string with data from file 
		Scanner scanner = new Scanner(jsonFile);
		while(scanner.hasNext()) {
			jsonStr += scanner.nextLine();
		}
		scanner.close();
		
		//Parse data and return
		JSONParser parser = new JSONParser();
		JSONObject jsonObj = (JSONObject) parser.parse(jsonStr);
		JSONArray jsonArr = (JSONArray) jsonObj.get("Emissions");
		
		for(int i = 0; i < jsonArr.size(); i++) {
			
			Emission emission = new Emission();
			
			jsonObj = (JSONObject) jsonArr.get(i);
			
			String categoryStr = (String) jsonObj.get("Category");
			
			
			String description;
			
			if(categoryStr.charAt(0) >= 'A' && categoryStr.charAt(0) <= 'Z') {
				
				description = categoryStr;
			}else {
				
				String categoryKeyStr = categoryStr.replace(".", "");
				description = descriptionMap.get(categoryKeyStr);
			}
			
			double value = 0;
			try {
				 value = ((Number) jsonObj.get("Value")).doubleValue();
			}catch(NumberFormatException e) {
				addEmission = false;
			}
			if(value <= 0) {
				addEmission = false;
			}
			
			if(addEmission) {
				
				emission.setCategory((String) jsonObj.get("Category"));
				emission.setGasUnits((String) jsonObj.get("Gas Units"));
				emission.setDescription(description);
				emission.setValue(value);
				
				jsonEmissions.add(emission);
			}			
		}
		
		return jsonEmissions;
	}
	
	
	//Parse XML function
	public ArrayList<Emission> parseXML () throws ParserConfigurationException, SAXException, IOException {
		
		ArrayList<Emission> xmlEmissions = new ArrayList<Emission>();
		HashMap<String, String> descriptionMap = map;
		
		//Declare xml file and create a Document to parse the xml
		File xmlFile = new File("EmissionDataXml.xml");
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();                                                 
		Document doc = db.parse(xmlFile);
		
		//Get all elements with tag 'Row'
		NodeList emissions = doc.getElementsByTagName("Row");
		
		for(int i = 0; i < emissions.getLength(); i++) {
			
			boolean addEmission = true;
			Node emission = emissions.item(i);
			
			if(emission.getNodeType() == Node.ELEMENT_NODE) {
				
				Element element = (Element) emission;
				
				Node scenario = element.getElementsByTagName("Scenario").item(0);
				String scenarioStr = scenario.getTextContent();
				
				if(!scenarioStr.equals("WEM")) {
					addEmission = false;
				}
								
				Node year = element.getElementsByTagName("Year").item(0);
				String yearStr = year.getTextContent();
				
				int yearInt = 0;
				try {
					 yearInt = Integer.parseInt(yearStr);					
				}catch(NumberFormatException e) {
					addEmission = false;
				}
				if(yearInt != 2023) {
					addEmission = false;
				}
				
				Node category = element.getElementsByTagName("Category__1_3").item(0);
				String categoryStr = category.getTextContent();
				char firstChar = categoryStr.charAt(0);
				String description;
				
				
				if(categoryStr.charAt(0) >= 'A' && categoryStr.charAt(0) <= 'Z') {
					
					description = categoryStr;
				}else {
					
					String categoryKeyStr = categoryStr.replace(".","");					
					description = descriptionMap.get(categoryKeyStr);
				}
				
				
							
				Node gasUnit = element.getElementsByTagName("Gas___Units").item(0);
				String gasUnitStr = gasUnit.getTextContent();
				
				Node value = element.getElementsByTagName("Value").item(0);
				String valueStr = value.getTextContent();
				
				double valueDbl = 0;
				try {
					valueDbl = Double.parseDouble(valueStr);
				}catch(NumberFormatException e) {
					addEmission = false;
				}
				if(valueDbl <= 0) {
					addEmission = false;
				}
				
				if(addEmission) {
					
					Emission e = new Emission();
					e.setCategory(categoryStr);
					e.setGasUnits(gasUnitStr);
					e.setDescription(description);
					e.setEmissionId();
					e.setValue(valueDbl);
					
					xmlEmissions.add(e);
				}
			}
		}
		
		ArrayList<Emission> returnList = new ArrayList<Emission>();
		
		for(Emission e: xmlEmissions) {
			
			if(!returnList.contains(e.getValue())) {
				
				returnList.add(e);
			}
		}
		
		return returnList;
	}	
}
