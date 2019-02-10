package lab1.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import lab1.service.xml.reader.XmlReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MainConfiguration {
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper().configure(JsonGenerator.Feature.IGNORE_UNKNOWN, true);
    }

    @Bean
    public XmlReader xmlReader() {
        return XmlReader.getInstance("/xml/trips.xml");
    }
}
