package com.production.analytics.repository;

import com.production.analytics.entity.ProductionEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository  extends JpaRepository<ProductionEvent, Long> {
    void findByProductionId(Long productionId);
    void deleteByProductionId(Long productionId);
}
