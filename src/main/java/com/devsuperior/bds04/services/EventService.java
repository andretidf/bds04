package com.devsuperior.bds04.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.bds04.dto.EventDTO;
import com.devsuperior.bds04.entities.City;
import com.devsuperior.bds04.entities.Event;
import com.devsuperior.bds04.repositories.CityRepository;
import com.devsuperior.bds04.repositories.EventRepository;

@Service
public class EventService {

	@Autowired
	private EventRepository repository;
	
	@Autowired
	private CityRepository cityRepository;

	@Transactional(readOnly = true)
	public Page<EventDTO> findAll(Pageable page) {
		Page<Event> list = repository.findAll(page);
		
		return list.map((x) -> new EventDTO(x));
	}

	@Transactional
	public EventDTO insert(EventDTO dto) {
		City city = cityRepository.findById(dto.getCityId()).get();
		
		Event entity = new Event();
		entity.setCity(city);
		entity.setDate(dto.getDate());
		entity.setName(dto.getName());
		entity.setUrl(dto.getUrl());
		
		return new EventDTO(repository.save(entity));
	}
	
}
