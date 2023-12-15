package com.spring.springbootproject;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.xml.sax.SAXException;

import com.spring.springbootproject.dao.EmissionDAO;
import com.spring.springbootproject.entity.Emission;
import com.spring.springbootproject.parser.Parser;

@SpringBootApplication
public class SpringbootprojectApplication {

	public static void main(String[] args) throws ParseException, ParserConfigurationException, SAXException, IOException {
		
		Parser parser = new Parser();
		
		ArrayList jsonList = parser.parseJSON();
		ArrayList<Emission> xmlList = parser.parseXML();
		JSONObject jsonObj = null;
		EmissionDAO eDAO = new EmissionDAO();
		
		for(int i = 0; i < jsonList.size(); i++) {
			
			jsonObj = (JSONObject) jsonList.get(i);
			
			Emission emission = new Emission();
			emission.setCategory((String) jsonObj.get("Category"));
			emission.setGasUnits((String) jsonObj.get("Gas Units"));
			
			long value = (long) jsonObj.get("Value");
			emission.setValue(value);

			
			eDAO.persist(emission);
			
		}
		
		for(Emission e: xmlList) {
			
			eDAO.persist(e);
		}
		
		System.out.println(xmlList);
		
		SpringApplication.run(SpringbootprojectApplication.class, args);
	}

}
