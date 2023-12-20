package com.spring.springbootproject.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.spring.springbootproject.entity.Emission;
import com.spring.springbootproject.repository.Repository;

@org.springframework.stereotype.Service
public class Service {

	@Autowired
	private Repository repository;
	
	public List<Emission> getAllEmissions(){
		
		return repository.findAll();
	}
	
	public Emission getEmissionById(Long id) {
		
		return repository.findById(id).orElse(null);
	}
	
	public Emission addEmission(Emission emission) {
		
		return repository.save(emission);
	}
	
	public Emission updateEmission(Long id, Emission emission) {
			
		Optional<Emission> existing = repository.findById(id);
		
		if(existing.isPresent()) {
			
			Emission e = existing.get();
			e.setCategory(emission.getCategory());
			e.setDescription(emission.getDescription());
			e.setGasUnits(emission.getGasUnits());
			e.setValue(emission.getValue());
			
			return repository.save(e);
		}else {
			
			return null;
		}
	}
	
	public void deleteEmission(Long id) {
		repository.deleteById(id);
	}
}
