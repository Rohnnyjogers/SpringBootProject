package com.spring.springbootproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.spring.springbootproject.entity.Emission;
import com.spring.springbootproject.service.Service;

@RestController
public class Controller {

	@Autowired
	private Service service;
	
	@GetMapping(value="/")
	public String getPage() {
		return "Hello World!";
	}

	@GetMapping(value="/emissions")
	public List<Emission> getAllEmission(){
		return service.getAllEmissions();
	}
	
	@GetMapping(value="/emissions/{id}")
	public ResponseEntity<Emission> getEmissionById(@PathVariable Long id){
		
		Emission e = service.getEmissionById(id);
		
		if(e != null) {
			
			return new ResponseEntity<>(e, HttpStatus.OK);
		}else {
			
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping(value="/addEmission")
	public ResponseEntity<Emission> addEmission(@RequestBody Emission emission){
		
		Emission e = service.addEmission(emission);
		return new ResponseEntity<>(e, HttpStatus.OK);
	}
	
	@PutMapping(value="/updateEmission/{id}")
	public ResponseEntity<Emission> updateEmission(@PathVariable Long id, @RequestBody Emission emission){
		
		Emission e = service.updateEmission(id, emission);
		
		if(e != null) {
			
			return new ResponseEntity<>(e, HttpStatus.OK);
		}else {
			
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping(value="/deleteEmission/{id}")
	public ResponseEntity<Void> deleteEmission(@PathVariable Long id){
		
		service.deleteEmission(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
