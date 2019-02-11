package lab2.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Task {
    @JsonProperty("id")
    private int id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("responsible")
    private String responsible;
    @JsonProperty("estimate")
    private String estimate;
    @JsonProperty("state")
    private String state;
}
