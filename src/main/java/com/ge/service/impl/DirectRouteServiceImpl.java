package com.ge.service.impl;

import com.ge.repository.BusRouteRepository;
import com.ge.service.DirectRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DirectRouteServiceImpl implements DirectRouteService {
    private final BusRouteRepository repository;

    @Autowired
    public DirectRouteServiceImpl(BusRouteRepository repository) {
        this.repository = repository;
    }

    public boolean existsDirectRoute(int departureStationId, int arrivalStationId) {
        return repository.existsDirectRoute(departureStationId, arrivalStationId);
    }
}
