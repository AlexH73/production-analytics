package com.production.analytics.service;

import com.production.analytics.dto.CreateEventRequest;
import com.production.analytics.dto.ProductionEventResponse;
import com.production.analytics.entity.ProductionEvent;
import com.production.analytics.exception.EventNotFoundException;
import com.production.analytics.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;

    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public ProductionEventResponse create(CreateEventRequest request) {
        ProductionEvent event = new ProductionEvent();
        event.setType(request.getType());
        event.setDescription(request.getDescription());
        event.setOccurredAt(request.getOccurredAt());

        ProductionEvent savedEvent = eventRepository.save(event);
        return toResponse(savedEvent);
    }

    @Override
    public List<ProductionEventResponse> getAll(String type) {
        List<ProductionEvent> events;

        if (type != null && !type.isBlank()) {
            events = eventRepository.findByType(type);
        } else {
            events = eventRepository.findAll();
        }

        return events.stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public ProductionEventResponse getById(Long id) {
        return eventRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new EventNotFoundException(id));
    }

    private ProductionEventResponse toResponse(ProductionEvent event) {
        return new ProductionEventResponse(
                event.getId(),
                event.getType(),
                event.getDescription(),
                event.getOccurredAt()
        );
    }
}
