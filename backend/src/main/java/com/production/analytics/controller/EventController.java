package com.production.analytics.controller;

import com.production.analytics.dto.CreateEventRequest;
import com.production.analytics.dto.ProductionEventResponse;
import com.production.analytics.service.EventService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping
    public ResponseEntity<ProductionEventResponse> createEvent(@Valid @RequestBody CreateEventRequest request) {
        ProductionEventResponse created = eventService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<List<ProductionEventResponse>> getAllEvents(@RequestParam(required = false) String type) {
        List<ProductionEventResponse> events = eventService.getAll(type);
        return ResponseEntity.ok(events);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductionEventResponse> getEventById(@PathVariable Long id) {
        ProductionEventResponse event = eventService.getById(id);
        return ResponseEntity.ok(event);
    }
}
