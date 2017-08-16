package com.ge.controller;

import com.ge.model.DirectRouteResponse;
import com.ge.service.DirectRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DirectRouteController {
    private final DirectRouteService directRouteService;

    @Autowired
    public DirectRouteController(DirectRouteService directRouteService) {
        this.directRouteService = directRouteService;
    }

    @RequestMapping(value = "/api/direct", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    DirectRouteResponse direct(@RequestParam("dep_sid") int departureStationId,
                               @RequestParam("arr_sid") int arrivalStationId) {
        return new DirectRouteResponse(
                departureStationId,
                arrivalStationId,
                directRouteService.existsDirectRoute(departureStationId, arrivalStationId));
    }
}
