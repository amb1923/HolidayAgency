package org.example.holidayagency;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Journey {
    private int passengers;
    private String departureAirport;
    private int distanceToAirport;
    private String destinationAirport;

    // Parse from a string like "2, A20, D"
    public static Journey parseJourneyDetails(String input) {
        String[] parts = input.split(",");
        int passengers = Integer.parseInt(parts[0].trim());

        String airportDistanceStr = parts[1].trim();
        String departureAirport = airportDistanceStr.substring(0, 1);
        int distanceToAirport = Integer.parseInt(airportDistanceStr.substring(1));

        String destinationAirport = parts[2].trim();

        return new Journey(passengers, departureAirport, distanceToAirport, destinationAirport);
    }
}
