package com.ge.repository;

public interface BusRouteRepository {
    boolean existsDirectRoute(int departureStationId, int arrivalStationId);
}
