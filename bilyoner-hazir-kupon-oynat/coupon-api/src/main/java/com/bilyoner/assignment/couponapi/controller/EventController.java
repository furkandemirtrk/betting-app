package com.bilyoner.assignment.couponapi.controller;

import com.bilyoner.assignment.couponapi.model.EventDTO;
import com.bilyoner.assignment.couponapi.service.EventService;
import com.bilyoner.assignment.couponapi.util.ApiPaths;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(ApiPaths.EventController.CONTROLLER)
@RequiredArgsConstructor
@Api(value = ApiPaths.EventController.CONTROLLER, tags = { "Event APIs" })
@Slf4j
public class EventController {

    /**
     * TODO : Implement missing parts
     */

    private final EventService eventService;

    @GetMapping(ApiPaths.EventController.ALL)
    @ApiOperation(value = "Get all events", response = EventDTO.class)
    public List<EventDTO> getAllEvents() {
        return eventService.getAllEvents();
    }

    @PostMapping(ApiPaths.EventController.CREATE_EVENT)
    @ApiOperation(value = "Created event", response = EventDTO.class)
    public EventDTO createEvent(@RequestBody @Valid EventDTO eventRequest) {
        return eventService.createEvent(eventRequest);
    }

    /**
     * Implement event endpoints
     */
}
