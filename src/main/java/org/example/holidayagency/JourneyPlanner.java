package org.example.holidayagency;
import java.util.ArrayList;
import java.util.List;
public class JourneyPlanner {
    private final FlightRouter router;

    public JourneyPlanner(FlightRouter router) {
        this.router = router;
    }

    public JourneySuggestion planJourney(Journey journey) {
        int passengers = journey.getPassengers();
        int milesToAirport = journey.getDistanceToAirport();
        String homeAirport = journey.getDepartureAirport();
        String destinationAirport = journey.getDestinationAirport();

        // 1. Vehicle logic
        String vehicle = VehicleCostHelper.chooseBestVehicle(passengers, milesToAirport);
        double vehicleCost = VehicleCostHelper.calculateCost(vehicle, passengers, milesToAirport);

        // 2. Outbound route
        List<Flight> outboundFlights = router.findCheapestRoute(homeAirport, destinationAirport, passengers);
        double outboundCost = calculateFlightCost(outboundFlights, passengers);
        List<String> outboundRoute = flightListToStringList(outboundFlights);

        // 3. Inbound route (reverse)
        List<Flight> inboundFlights = router.findCheapestRoute(destinationAirport, homeAirport, passengers);
        double inboundCost = calculateFlightCost(inboundFlights, passengers);
        List<String> inboundRoute = flightListToStringList(inboundFlights);

        // 4. Handle missing routes
        if (outboundFlights.isEmpty() || inboundFlights.isEmpty()) {
            return new JourneySuggestion(
                    vehicle,
                    vehicleCost,
                    outboundFlights.isEmpty() ? List.of("No outbound flight") : outboundRoute,
                    outboundFlights.isEmpty() ? 0.0 : outboundCost,
                    inboundFlights.isEmpty() ? List.of("No inbound flight") : inboundRoute,
                    inboundFlights.isEmpty() ? 0.0 : inboundCost,
                    0.0  // Total cost is 0 if either route is missing
            );
        }

        // 5. Total cost
        double totalCost = vehicleCost + outboundCost + inboundCost;

        return new JourneySuggestion(vehicle, vehicleCost, outboundRoute, outboundCost, inboundRoute, inboundCost, totalCost);
    }

    private double calculateFlightCost(List<Flight> flights, int passengers) {
        return flights.stream()
                .mapToDouble(f -> f.getDistance() * 0.10 * passengers)
                .sum();
    }

    private List<String> flightListToStringList(List<Flight> flights) {
        List<String> list = new ArrayList<>();
        for (Flight flight : flights) {
            list.add(flight.toString());
        }
        return list;
    }
}
