package com.production.analytics.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ProductionEventResponse {
    private Long id;
    private String type;
    private String description;
    private LocalDateTime occurredAt;
}
