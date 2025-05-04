package org.example.holidayagency;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Flight {
    private String from;
    private String to;
    private int distance;

    public static Flight parseFlightDetails(String flightString) {
        String from = flightString.substring(0, 1);
        String to = flightString.substring(1, 2);
        int distance = Integer.parseInt(flightString.substring(2));
        return new Flight(from, to, distance);
    }

    @Override
    public String toString() {
        return from + to + distance;
    }
}
