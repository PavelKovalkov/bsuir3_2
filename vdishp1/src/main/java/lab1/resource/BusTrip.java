package lab1.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusTrip extends RailwayTrip {
    @JsonProperty("travel_time")
    private String travelTime;
    @JsonProperty("bus_model")
    private String busModel;
}
