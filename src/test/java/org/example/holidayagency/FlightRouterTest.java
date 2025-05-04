package org.example.holidayagency;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class FlightRouterTest {

    private FlightRouter router;

    @BeforeEach
    void setup() {
        router = new FlightRouter();

        // Sample flight graph
        router.addAllFlights(List.of(
                Flight.parseFlightDetails("AB100"),
                Flight.parseFlightDetails("BC100"),
                Flight.parseFlightDetails("AC300"),
                Flight.parseFlightDetails("CD200"),
                Flight.parseFlightDetails("DA100")
        ));
    }

    @Test
    void shouldFindDirectRoute() {
        List<Flight> route = router.findCheapestRoute("A", "B", 1);
        assertEquals(1, route.size());
        assertEquals("AB100", route.get(0).toString());
    }

    @Test
    void shouldFindCheapestIndirectRouteWhenItCostsLessThanDirect() {
        // A -> B (100), B -> C (100) = 200
        // A -> C (300) direct is more expensive
        List<Flight> route = router.findCheapestRoute("A", "C", 1);
        assertEquals(2, route.size());
        assertEquals("AB100", route.get(0).toString());
        assertEquals("BC100", route.get(1).toString());
    }

    @Test
    void shouldSelectSameRouteRegardlessOfPassengerCount() {
        // Cheapest route by distance: A → B → C (100 + 100 = 200)
        List<Flight> routeWith1Passenger = router.findCheapestRoute("A", "C", 1);
        List<Flight> routeWith10Passengers = router.findCheapestRoute("A", "C", 10);

        assertEquals(routeWith1Passenger, routeWith10Passengers);
    }

    @Test
    void shouldReturnEmptyListWhenNoRouteExists() {
        List<Flight> route = router.findCheapestRoute("C", "F", 1);
        assertTrue(route.isEmpty());
    }

}
