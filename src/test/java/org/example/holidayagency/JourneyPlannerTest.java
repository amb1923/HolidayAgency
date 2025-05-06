package org.example.holidayagency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JourneyPlannerTest {

    private FlightRouter router;
    private JourneyPlanner planner;

    @BeforeEach
    void setUp() {
        router = new FlightRouter();
        router.addAllFlights(List.of(
                Flight.parseFlightDetails("AB100"),
                Flight.parseFlightDetails("BC100"),
                Flight.parseFlightDetails("AC300"),
                Flight.parseFlightDetails("AD200"),
                Flight.parseFlightDetails("CA300"),
                Flight.parseFlightDetails("CB300"),
                Flight.parseFlightDetails("DA200")
                ));
        planner = new JourneyPlanner(router);
    }

    // Direct outbound and inbound flights exist
    @Test
    void testDirectOutboundAndInboundFlights() {
        Journey journey = new Journey(1, "A", 10, "D");
        JourneySuggestion suggestion = planner.planJourney(journey);

        assertEquals("Car", suggestion.getVehicle());
        assertEquals(7.0, suggestion.getVehicleCost(), 0.01);
        assertEquals(List.of("AD200"), suggestion.getOutboundRoute());
        assertEquals(20.0, suggestion.getOutboundCost(), 0.01);
        assertEquals(List.of("DA200"), suggestion.getInboundRoute());
        assertEquals(20.0, suggestion.getInboundCost(), 0.01);
        assertEquals(47.0, suggestion.getTotalCost(), 0.01);
    }

    // Direct outbound and Indirect inbound flights
    @Test
    void testDirectOutboundAndIndirectInboundFlights() {
        Journey journey = new Journey(1, "A", 10, "B");
        JourneySuggestion suggestion = planner.planJourney(journey);

        assertEquals("Car", suggestion.getVehicle());
        assertEquals(7.0, suggestion.getVehicleCost(), 0.01);
        assertEquals(List.of("AB100"), suggestion.getOutboundRoute());
        assertEquals(10.0, suggestion.getOutboundCost(), 0.01);
        assertEquals(List.of("BC100", "CA300"), suggestion.getInboundRoute());
        assertEquals(40.0, suggestion.getInboundCost(), 0.01);
        assertEquals(57.0, suggestion.getTotalCost(), 0.01);
    }

    // Indirect outbound and inbound flights only
    @Test
    void testIndirectOutboundAndInboundFlights() {
        router = new FlightRouter();
        router.addAllFlights(List.of(
                Flight.parseFlightDetails("AB100"),
                Flight.parseFlightDetails("BC100"),
                Flight.parseFlightDetails("CB300"),
                Flight.parseFlightDetails("BA100")
        ));
        planner = new JourneyPlanner(router);

        Journey journey = new Journey(1, "A", 10, "C");
        JourneySuggestion suggestion = planner.planJourney(journey);

        assertEquals("Car", suggestion.getVehicle());
        assertEquals(7.0, suggestion.getVehicleCost(), 0.01);
        assertEquals(List.of("AB100", "BC100"), suggestion.getOutboundRoute());
        assertEquals(20.0, suggestion.getOutboundCost(), 0.01);
        assertEquals(List.of("CB300","BA100"), suggestion.getInboundRoute());
        assertEquals(40.0, suggestion.getInboundCost(), 0.01);
        assertEquals(67.0, suggestion.getTotalCost(), 0.01);
    }

    // No outbound or inbound flight available
    @Test
    void testMissingOutboundAndInboundFlights() {
        Journey journey = new Journey(1, "B", 5, "X");
        JourneySuggestion suggestion = planner.planJourney(journey);

        assertEquals("Taxi", suggestion.getVehicle());
        assertEquals(4.0, suggestion.getVehicleCost(), 0.01);
        assertEquals(List.of("No outbound flight"), suggestion.getOutboundRoute());
        assertEquals(0.0, suggestion.getOutboundCost(), 0.01);
        assertEquals(List.of("No inbound flight"), suggestion.getInboundRoute());
        assertEquals(0.0, suggestion.getInboundCost(), 0.01);
        assertEquals(0.0, suggestion.getTotalCost(), 0.01);
    }
}
