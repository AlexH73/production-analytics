package com.production.analytics.repository;

import com.production.analytics.entity.ProductionEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository  extends JpaRepository<ProductionEvent, Long> {
    List<ProductionEvent> findByType(String type);
}
