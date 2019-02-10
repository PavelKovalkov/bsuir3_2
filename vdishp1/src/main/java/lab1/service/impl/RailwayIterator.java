package lab1.service.impl;

import lab1.resource.DepartureDateFilterParam;
import lab1.resource.RailwayTrip;
import lombok.Getter;

import java.time.LocalDate;

public class RailwayIterator {
    @Getter
    private RailwayTrip[] railwayTrips;
    private int index = 0;

    public RailwayIterator(RailwayTrip[] railwayTrips) {
        this.railwayTrips = railwayTrips.clone();
    }

    public RailwayTrip next() {
        return railwayTrips[index++];
    }

    public boolean hasNext() {
        return index != railwayTrips.length;
    }

    public RailwayIterator filterByDepartureDate(DepartureDateFilterParam param, LocalDate filteredValue) {
        RailwayTrip[] trips;
        int i = 0;
        int newSize = 0;
        switch (param) {
            case AFTER:
                for (RailwayTrip railwayTrip : railwayTrips) {
                    if (railwayTrip.getDepartureDate().isAfter(filteredValue)) {
                        newSize++;
                    }
                }
                trips = new RailwayTrip[newSize];
                for (RailwayTrip railwayTrip : railwayTrips) {
                    if (railwayTrip.getDepartureDate().isAfter(filteredValue)) {
                        trips[i++] = railwayTrip;
                    }
                }
                break;
            case EQUAL:
                for (RailwayTrip railwayTrip : railwayTrips) {
                    if (railwayTrip.getDepartureDate().isEqual(filteredValue)) {
                        newSize++;
                    }
                }
                trips = new RailwayTrip[newSize];
                for (RailwayTrip railwayTrip : railwayTrips) {
                    if (railwayTrip.getDepartureDate().isEqual(filteredValue)) {
                        trips[i++] = railwayTrip;
                    }
                }
                break;
            case BEFORE:
                for (RailwayTrip railwayTrip : railwayTrips) {
                    if (railwayTrip.getDepartureDate().isBefore(filteredValue)) {
                        newSize++;
                    }
                }
                trips = new RailwayTrip[newSize];
                for (RailwayTrip railwayTrip : railwayTrips) {
                    if (railwayTrip.getDepartureDate().isBefore(filteredValue)) {
                        trips[i++] = railwayTrip;
                    }
                }
                break;
            default:
                throw new RuntimeException();
        }

        railwayTrips = trips;
        index = 0;
        return this;
    }

    public RailwayIterator filterByDestination(String filterValue) {
        int newSize = 0;
        for (RailwayTrip trip : railwayTrips) {
            if (trip.getDestination().equalsIgnoreCase(filterValue)) {
                newSize++;
            }
        }
        RailwayTrip[] trips = new RailwayTrip[newSize];
        int i = 0;
        for (RailwayTrip trip : railwayTrips) {
            if (trip.getDestination().equalsIgnoreCase(filterValue)) {
                trips[i++] = trip;
            }
        }
        railwayTrips = trips;
        index = 0;
        return this;
    }

    public RailwayIterator filterByPlatform(int filterValue) {
        int newSize = 0;
        for (RailwayTrip trip : railwayTrips) {
            if (trip.getDeparturePlatform() == filterValue) {
                newSize++;
            }
        }
        RailwayTrip[] trips = new RailwayTrip[newSize];
        int i = 0;
        for (RailwayTrip trip : railwayTrips) {
            if (trip.getDeparturePlatform() == filterValue) {
                trips[i++] = trip;
            }
        }
        railwayTrips = trips;
        index = 0;
        return this;
    }
}
