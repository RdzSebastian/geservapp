package com.estonianport.geservapp.dao;

import org.springframework.data.repository.CrudRepository;

import com.estonianport.geservapp.model.Event;

public interface EventDao extends CrudRepository<Event, Long> {
	
}
