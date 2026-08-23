package com.production.analytics.service;

import com.production.analytics.dto.CreateEventRequest;
import com.production.analytics.entity.ProductionEvent;
import com.production.analytics.repository.EventRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("EventServiceImpl unit tests")
class EventServiceImplTest {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventServiceImpl eventService;

    @Test
    @DisplayName("create: should map DTO to entity and save event")
    void create_shouldMapAndSaveEvent() {
        CreateEventRequest request = new CreateEventRequest(
                "MACHINE_STARTED",
                "Line started",
                LocalDateTime.of(2026, 8, 23, 10, 30)
        );

        ProductionEvent saved = new ProductionEvent(
                1L,
                "MACHINE_STARTED",
                "Line started",
                LocalDateTime.of(2026, 8, 23, 10, 30)
        );

        when(eventRepository.save(any(ProductionEvent.class))).thenReturn(saved);

        ProductionEvent result = eventService.create(request);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("MACHINE_STARTED", result.getType());

        ArgumentCaptor<ProductionEvent> captor = ArgumentCaptor.forClass(ProductionEvent.class);
        verify(eventRepository).save(captor.capture());
        ProductionEvent toSave = captor.getValue();

        assertEquals("MACHINE_STARTED", toSave.getType());
        assertEquals("Line started", toSave.getDescription());
        assertEquals(LocalDateTime.of(2026, 8, 23, 10, 30), toSave.getOccurredAt());
    }

    @Test
    @DisplayName("getAll: should return filtered events when type is provided")
    void getAll_shouldReturnFiltered_whenTypeProvided() {
        List<ProductionEvent> events = List.of(
                new ProductionEvent(1L, "MACHINE_STARTED", "desc", LocalDateTime.now())
        );
        when(eventRepository.findByType("MACHINE_STARTED")).thenReturn(events);

        List<ProductionEvent> result = eventService.getAll("MACHINE_STARTED");

        assertEquals(1, result.size());
        verify(eventRepository).findByType("MACHINE_STARTED");
        verify(eventRepository, never()).findAll();
    }

    @Test
    @DisplayName("getAll: should return all events when type is null")
    void getAll_shouldReturnAll_whenTypeIsNull() {
        when(eventRepository.findAll()).thenReturn(List.of());

        List<ProductionEvent> result = eventService.getAll(null);

        assertNotNull(result);
        verify(eventRepository).findAll();
        verify(eventRepository, never()).findByType(anyString());
    }

    @Test
    @DisplayName("getAll: should return all events when type is blank")
    void getAll_shouldReturnAll_whenTypeIsBlank() {
        when(eventRepository.findAll()).thenReturn(List.of());

        List<ProductionEvent> result = eventService.getAll("   ");

        assertNotNull(result);
        verify(eventRepository).findAll();
        verify(eventRepository, never()).findByType(anyString());
    }

    @Test
    @DisplayName("getById: should return event when it exists")
    void getById_shouldReturnEvent_whenFound() {
        ProductionEvent event = new ProductionEvent(
                1L,
                "MACHINE_STARTED",
                "desc",
                LocalDateTime.of(2026, 8, 23, 10, 30)
        );
        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));

        ProductionEvent result = eventService.getById(1L);

        assertSame(event, result);
        verify(eventRepository).findById(1L);
    }

    @Test
    @DisplayName("getById: should throw 404 when event is not found")
    void getById_shouldThrow404_whenNotFound() {
        when(eventRepository.findById(999L)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(
                ResponseStatusException.class,
                () -> eventService.getById(999L)
        );

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }
}
