package com.estonianport.geservapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.estonianport.geservapp.commons.GenericServiceImpl;
import com.estonianport.geservapp.dao.EventDao;
import com.estonianport.geservapp.model.Event;
import com.estonianport.geservapp.service.EventService;

@Service
public class EventServiceImpl extends GenericServiceImpl<Event, Long> implements EventService{

	@Autowired
	private EventDao eventDao;

	@Override
	public CrudRepository<Event, Long> getDao() {
		return eventDao;
	}
}
