package org.example.holidayagency.acceptance;

import org.example.holidayagency.*;

import org.junit.jupiter.api.Test;

import java.util.List;

public class JourneyPlannerAcceptanceTest {
    @Test
    void shouldPrintFullJourneySuggestionsTable() {
        List<Flight> flights = List.of(
                Flight.parseFlightDetails("AB800"), Flight.parseFlightDetails("BC900"),
                Flight.parseFlightDetails("CD400"), Flight.parseFlightDetails("DE400"),
                Flight.parseFlightDetails("BF400"), Flight.parseFlightDetails("CE300"),
                Flight.parseFlightDetails("DE300"), Flight.parseFlightDetails("EB600"),
                Flight.parseFlightDetails("CE200"), Flight.parseFlightDetails("DC700"),
                Flight.parseFlightDetails("EB500"), Flight.parseFlightDetails("FD200")
        );

        List<Journey> journeys = List.of(
                Journey.parseJourneyDetails("2, B20, D"),   // #1
                Journey.parseJourneyDetails("1, B30, D"),   // #2
                Journey.parseJourneyDetails("2, A20, D"),   // #3
                Journey.parseJourneyDetails("2, C30, A"),   // #4
                Journey.parseJourneyDetails("2, B10, C"),   // #5
                Journey.parseJourneyDetails("5, B10, C"),   // #6
                Journey.parseJourneyDetails("1, D25, B"),   // #7
                Journey.parseJourneyDetails("4, D40, A"),   // #8
                Journey.parseJourneyDetails("2, B5, D"),    // #9
                Journey.parseJourneyDetails("9, B30, D")    // #10
        );

        FlightRouter router = new FlightRouter();
        router.addAllFlights(flights);
        JourneyPlanner planner = new JourneyPlanner(router);

        System.out.printf("%-3s | %-7s | %-19s | %-30s | %-14s | %-30s | %-14s | %-11s%n",
                "#", "Vehicle", "Vehicle Cost", "Outbound Route", "Outbound Cost", "Inbound Route", "Inbound Cost", "Total Cost");
        System.out.println("----+---------+---------------------+--------------------------------+----------------+--------------------------------+----------------+-------------");

        int id = 1;
        for (Journey journey : journeys) {
            JourneySuggestion s = planner.planJourney(journey);

            String outboundRoute = String.join("--", s.getOutboundRoute());
            String inboundRoute = String.join("--", s.getInboundRoute());

            System.out.printf("%-3d | %-7s | £%-18.2f | %-30s | £%-12.2f | %-30s | £%-12.2f | £%-9.2f%n",
                    id++,
                    s.getVehicle(),
                    s.getVehicleCost(),
                    outboundRoute,
                    s.getOutboundCost(),
                    inboundRoute,
                    s.getInboundCost(),
                    s.getTotalCost()
            );
        }
    }
}
