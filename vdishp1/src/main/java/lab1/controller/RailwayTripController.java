package lab1.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lab1.resource.DepartureDateFilterParam;
import lab1.resource.RailwayTrip;
import lab1.resource.RailwayTripFilterEntry;
import lab1.service.RailwayTripFactory;
import lab1.service.impl.BusTripFactory;
import lab1.service.impl.RailwayIterator;
import lab1.service.impl.TrainTripFactory;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
public class RailwayTripController {
    private final ObjectMapper mapper;

    @Autowired
    public RailwayTripController(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @GetMapping(path = "/get/{trip_type}")
    public ResponseEntity<RailwayTrip[]> getAllRailwayTrip(@PathVariable("trip_type") String tripType) throws Exception {
        RailwayTripFactory factory;
        switch (tripType) {
            case "bus":
                factory = new BusTripFactory(mapper).withResourcePath("/json/bus.json");
                break;
            case "train":
                factory = new TrainTripFactory(mapper).withResourcePath("/json/train.json");
                break;
            default:
                throw new RuntimeException();
        }
        RailwayTrip[] railwayTrips = factory.createRailwayTripsFromFile();
        return new ResponseEntity<>(railwayTrips, HttpStatus.OK);
    }

    @GetMapping(path = "/get/filter/{trip_type}")
    public ResponseEntity<RailwayTrip[]> getFilteredRailwayTrip(
        @RequestHeader("Filter-entry") String filterEntry,
        @PathVariable("trip_type") String tripType,
        @RequestBody String filteredDataJson
    ) throws Exception {
        RailwayTripFactory factory;

        switch (tripType) {
            case "bus":
                factory = new BusTripFactory(mapper).withResourcePath("/json/bus.json");
                break;
            case "train":
                factory = new TrainTripFactory(mapper).withResourcePath("/json/train.json");
                break;
            default:
                throw new RuntimeException();
        }

        RailwayTrip[] railwayTrips = factory.createRailwayTripsFromFile();
        RailwayIterator iterator = new RailwayIterator(railwayTrips);
        JSONObject jsonObject = new JSONObject(filteredDataJson);

        switch (RailwayTripFilterEntry.valueOf(filterEntry.toUpperCase())) {
            case DESTINATION:
                iterator.filterByDestination(jsonObject.getString("destination"));
                break;
            case DEPARTURE_DATE:
                LocalDate filtered = LocalDate.of(
                    jsonObject.getInt("year"),
                    jsonObject.getInt("month"),
                    jsonObject.getInt("day")
                );
                iterator.filterByDepartureDate(
                    DepartureDateFilterParam.valueOf(jsonObject.getString("filtered_type").toUpperCase()),
                    filtered);
                break;
            default:
                throw new RuntimeException();
        }

        return new ResponseEntity<>(iterator.getRailwayTrips(), HttpStatus.OK);
    }
}
