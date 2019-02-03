package lab1.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lab1.serializer.CustomLocalDateSerializer;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class RailwayTrip {
    @JsonProperty("id")
    private int id;
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    private LocalDate departureDate;
    @JsonProperty("departure_time")
    private String departureTime;
    @JsonProperty("destination")
    private String destination;
    @JsonProperty("departure_station")
    private String departureStation;
    @JsonProperty("departure_platform")
    private int departurePlatform;
    @JsonProperty("arrival_station")
    private String arrivalStation;
    @JsonProperty("ticket_price")
    private double ticketPrice;

    @JsonProperty("departure_date")
    public void setDepartureDate(String departureDate) {
        String[] yearMonthDay = departureDate.split("-");
        this.departureDate = LocalDate.of(Integer.valueOf(yearMonthDay[2]),
            Integer.valueOf(yearMonthDay[1]),
            Integer.valueOf(yearMonthDay[0]));
    }
}
