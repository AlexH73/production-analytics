package com.production.analytics.service;

import com.production.analytics.dto.CreateEventRequest;
import com.production.analytics.dto.ProductionEventResponse;

import java.util.List;

public interface EventService {
    ProductionEventResponse create(CreateEventRequest request);
    List<ProductionEventResponse> getAll(String type);
    ProductionEventResponse getById(Long id);
}
