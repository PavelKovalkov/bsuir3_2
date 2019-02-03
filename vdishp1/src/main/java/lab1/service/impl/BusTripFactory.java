package lab1.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lab1.resource.BusTrip;
import lab1.resource.RailwayTrip;
import lab1.service.RailwayTripFactory;

import java.net.URL;

public class BusTripFactory implements RailwayTripFactory {
    private final ObjectMapper mapper;
    private String resourcePath;

    public BusTripFactory(ObjectMapper objectMapper) {
        this.mapper = objectMapper;
    }

    @Override
    public RailwayTripFactory withResourcePath(String resourcePath) {
        this.resourcePath = resourcePath;
        return this;
    }

    @Override
    public RailwayTrip[] createRailwayTripsFromFile() throws Exception {
        URL resourceUrl = BusTripFactory.class.getResource(resourcePath);
        BusTrip[] busTrips = mapper.readValue(resourceUrl, BusTrip[].class);
        return busTrips;
    }
}
