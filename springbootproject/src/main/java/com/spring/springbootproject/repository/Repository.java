package com.spring.springbootproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.springbootproject.entity.Emission;


public interface Repository extends JpaRepository<Emission, Long>{

}
