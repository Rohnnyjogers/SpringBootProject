package com.spring.springbootproject;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.spring.springbootproject.dao.EmissionDAO;
import com.spring.springbootproject.entity.Emission;
import com.spring.springbootproject.parser.Parser;

@SpringBootApplication
public class SpringbootprojectApplication {

	public static void main(String[] args) throws FileNotFoundException, ParseException {
		
		Parser parser = new Parser();
		
		ArrayList jsonList = parser.parseJSON();
		JSONObject jsonObj = null;
		
		Emission emission = new Emission();
		EmissionDAO eDAO = new EmissionDAO();
		
		for(int i = 0; i < jsonList.size(); i++) {
			
			jsonObj = (JSONObject) jsonList.get(i);
			emission.setCategory((String) jsonObj.get("Category"));
			emission.setGasUnits((String) jsonObj.get("Gas Units"));

			
			eDAO.persist(emission);
			
		}
		
		System.out.println("Done.");
		
		SpringApplication.run(SpringbootprojectApplication.class, args);
	}

}
