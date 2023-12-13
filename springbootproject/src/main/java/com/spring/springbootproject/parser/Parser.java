package com.spring.springbootproject.parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

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
		
		return jsonArr;
	}
	
	//Parse XML function
	public ArrayList parseXML () {
		
		
		
		ArrayList<Object> xmlEmissions = new ArrayList<Object>();
		
		return xmlEmissions;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
