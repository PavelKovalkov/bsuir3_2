package lab1.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lab1.resource.RailwayTrip;
import lab1.resource.TrainTrip;
import lab1.service.RailwayTripFactory;

import java.net.URL;

public class TrainTripFactory implements RailwayTripFactory {
    private final ObjectMapper mapper;
    private String resourcePath;

    public TrainTripFactory(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public RailwayTripFactory withResourcePath(String resourcePath) {
        this.resourcePath = resourcePath;
        return this;
    }

    @Override
    public RailwayTrip[] createRailwayTripsFromFile() throws Exception {
        URL resourceUrl = TrainTripFactory.class.getResource(resourcePath);
        TrainTrip[] trainTrips = mapper.readValue(resourceUrl, TrainTrip[].class);
        return trainTrips;
    }
}
