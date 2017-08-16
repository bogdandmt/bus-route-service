package com.ge.repository.impl;

import com.ge.repository.BusRouteRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class BusRouteRepositoryImpl implements BusRouteRepository {
    private static final String DELIMITER = " ";

    private final String busRouteDataLocation;
    private final RouteContainer routeContainer = new RouteContainer();

    public BusRouteRepositoryImpl(@Value("${busRouteDataLocation}") String busRouteDataLocation) {
        this.busRouteDataLocation = busRouteDataLocation;
    }

    @Override
    public boolean existsDirectRoute(int departureStationId, int arrivalStationId) {
        return routeContainer.existsDirectRoute(departureStationId, arrivalStationId);
    }

    @PostConstruct
    public void init() {
        File file = new File(busRouteDataLocation);
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                List<Integer> stationIds = Arrays.stream(line.split(DELIMITER))
                        .skip(1)
                        .map(Integer::parseInt)
                        .collect(Collectors.toList());
                routeContainer.addRoute(stationIds);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class RouteContainer {
        private Map<Integer, Set<Integer>> departureArrivals = new HashMap<>();

        private void addRoute(List<Integer> stationIds) {
            for (int i = 0; i < stationIds.size() - 1; ++i) {
                Integer currentDeparture = stationIds.get(i);
                Set<Integer> arrivals = departureArrivals.get(currentDeparture);
                List<Integer> nextDepartures = stationIds.subList(i + 1, stationIds.size());
                if (arrivals == null) {
                    departureArrivals.put(currentDeparture,
                            new HashSet<>(nextDepartures));
                } else {
                    arrivals.addAll(nextDepartures);
                }
            }
        }

        private boolean existsDirectRoute(int departureStationId, int arrivalStationId) {
            Set<Integer> arrivalStationIds = departureArrivals.get(departureStationId);
            return arrivalStationIds != null && arrivalStationIds.contains(arrivalStationId);
        }
    }
}
