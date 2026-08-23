package com.production.analytics.service;

import com.production.analytics.dto.CreateEventRequest;
import com.production.analytics.entity.ProductionEvent;
import com.production.analytics.repository.EventRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;

    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public ProductionEvent create(CreateEventRequest request) {
        ProductionEvent event = new ProductionEvent();
        event.setType(request.getType());
        event.setDescription(request.getDescription());
        event.setOccurredAt(request.getOccurredAt());

        return eventRepository.save(event);
    }

    @Override
    public List<ProductionEvent> getAll(String type) {
        if (type != null && !type.isBlank()) {
            return eventRepository.findByType(type);
        }

        return eventRepository.findAll();
    }

    @Override
    public ProductionEvent getById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found"));
    }
}
