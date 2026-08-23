package com.production.analytics.service;

import com.production.analytics.dto.CreateEventRequest;
import com.production.analytics.entity.ProductionEvent;

import java.util.List;

public interface EventService {
    ProductionEvent create(CreateEventRequest request);
    List<ProductionEvent> getAll(String type);
    ProductionEvent getById(Long id);
}
