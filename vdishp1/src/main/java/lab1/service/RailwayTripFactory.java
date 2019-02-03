package lab1.service;

import lab1.resource.RailwayTrip;

public interface RailwayTripFactory {
    RailwayTripFactory withResourcePath(String resourcePath);

    RailwayTrip[] createRailwayTripsFromFile() throws Exception;
}
