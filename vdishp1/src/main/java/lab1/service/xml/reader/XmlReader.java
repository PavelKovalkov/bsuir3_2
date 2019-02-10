package lab1.service.xml.reader;

import lab1.resource.BusTrip;
import lab1.resource.RailwayTrip;
import lab1.resource.TrainTrip;
import lab1.service.impl.BusTripFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.net.URL;

public final class XmlReader {
    private static volatile XmlReader instance;
    private String filePath;

    public static XmlReader getInstance(String filePath) {
        if (instance == null) {
            synchronized (XmlReader.class) {
                if (instance == null) {
                    instance = new XmlReader(filePath);
                }
            }
        }
        return instance;
    }

    private XmlReader(String filePath) {
        this.filePath=filePath;
    }

    public static RailwayTrip[] obtainRailwayTripsFromXmlFile(String transportType) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        URL resourceUrl = BusTripFactory.class.getResource(instance.filePath);

        Document document = builder.parse(resourceUrl.toURI().toString());
        NodeList nodeList = document.getElementsByTagName(transportType);
        RailwayTrip[] railwayTrips = new RailwayTrip[nodeList.getLength()];

        for (int i = 0; i < nodeList.getLength(); i++) {
            railwayTrips[i] = getTrip(nodeList.item(i), transportType);
        }

        return railwayTrips;
    }

    private static RailwayTrip getTrip(Node item, String type) {
        RailwayTrip trip = null;
        if (item.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) item;
            if (type.equalsIgnoreCase("bus")) {
                trip = new BusTrip();
                ((BusTrip) trip).setTravelTime(getTagValue("travel_time", element));
                ((BusTrip) trip).setBusModel(getTagValue("bus_model", element));
            } else if (type.equalsIgnoreCase("train")) {
                trip = new TrainTrip();
                ((TrainTrip) trip).setTicketType(getTagValue("ticket_type", element));
                ((TrainTrip) trip).setArrivalDate(getTagValue("arrival_date", element));
                ((TrainTrip) trip).setArrivalTime(getTagValue("arrival_time", element));
            } else {
                throw new RuntimeException();
            }
            trip.setId(Integer.parseInt(getTagValue("id", element)));
            trip.setDepartureDate(getTagValue("departure_date", element));
            trip.setDepartureTime(getTagValue("departure_time", element));
            trip.setDestination(getTagValue("destination", element));
            trip.setDepartureStation(getTagValue("departure_station", element));
            trip.setDeparturePlatform(Integer.parseInt(getTagValue("departure_platform", element)));
            trip.setArrivalStation(getTagValue("arrival_station", element));
            trip.setTicketPrice(Integer.parseInt(getTagValue("ticket_price", element)));
        }

        return trip;
    }

    private static String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = nodeList.item(0);
        return node.getNodeValue();
    }
}
