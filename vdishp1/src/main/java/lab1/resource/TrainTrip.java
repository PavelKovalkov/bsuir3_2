package lab1.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lab1.serializer.CustomLocalDateSerializer;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TrainTrip extends RailwayTrip {
    @JsonProperty("ticket_type")
    private String ticketType;
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    private LocalDate arrivalDate;
    @JsonProperty("arrival_time")
    private String arrivalTime;

    @JsonProperty("arrival_date")
    public void setArrivalDate(String arrivalDate) {
        String[] yearMonthDay = arrivalDate.split("-");
        this.arrivalDate = LocalDate.of(Integer.valueOf(yearMonthDay[2]),
            Integer.valueOf(yearMonthDay[1]),
            Integer.valueOf(yearMonthDay[0]));
    }
}
