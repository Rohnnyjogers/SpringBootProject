package com.spring.springbootproject;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

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
		EmissionDAO eDAO = new EmissionDAO();
		
		parser.setMap(parser.descriptionMap());
		
		ArrayList<Emission> emissionsList = parser.parseJSON();
		ArrayList<Emission> xmlList = parser.parseXML();
		
		// emissionsList.addAll(xmlList);
		
		for(Emission e: emissionsList) {
			
			eDAO.persist(e);
		}
		
		for(Emission e: xmlList) {
					
			eDAO.persist(e);
		}
		
		SpringApplication.run(SpringbootprojectApplication.class, args);
	}
}
