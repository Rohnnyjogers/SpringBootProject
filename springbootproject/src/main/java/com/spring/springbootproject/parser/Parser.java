package com.spring.springbootproject.parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.xml.sax.SAXException;

import com.spring.springbootproject.entity.Emission;

public class Parser {
	
	public Parser() {}
	
	//Parse JSON function
	public JSONArray parseJSON() throws FileNotFoundException, ParseException {
		
		//Declare emissions file and empty json string 
		File jsonFile = new File("GreenhouseGasEmissions2023.json");
		String jsonStr = "";
		
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
			
			jsonObj = (JSONObject) jsonArr.get(i);
			
			Emission emission = new Emission();
			emission.setCategory((String) jsonObj.get("Category"));
			emission.setGasUnits((String) jsonObj.get("Gas Units"));
			
			double value = ((Number) jsonObj.get("Value")).doubleValue();
			emission.setValue(value);
			
			//System.out.print(value+"\n");
		}
		
		return jsonArr;
	}
	
	//Parse XML function
	public ArrayList<Emission> parseXML () throws ParserConfigurationException, SAXException, IOException {
		
		ArrayList<Emission> xmlEmissions = new ArrayList<Emission>();
		
		//Declare xml file and create a Document to parse the xml
		File xmlFile = new File("EmissionDataXml.xml");
		xmlFile.exists();
		xmlFile.length();
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
				
				if(addEmission) {
					
					Emission e = new Emission();
					e.setCategory(categoryStr);
					e.setGasUnits(gasUnitStr);
					e.setValue(valueDbl);
					
					xmlEmissions.add(e);
				}
			}
		}
		
		for(Emission e: xmlEmissions) {
			System.out.println(e.getCategory());
			System.out.println(e.getGasUnits());
			System.out.println(e.getValue());
		}
		
		return xmlEmissions;
	}
	
	
	public static void main(String args[]) throws ParserConfigurationException, SAXException, IOException, ParseException {
		
		Parser p = new Parser();
		ArrayList<Emission> al = p.parseJSON();
		ArrayList<Emission> al1 = p.parseXML();
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
