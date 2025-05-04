package org.example.holidayagency;

import java.util.*;
public class FlightRouter {
    private final Map<String, List<Flight>> adjacencyList = new HashMap<>();

    // Add a single flight to the graph
    public void addFlight(Flight flight) {
        adjacencyList
                .computeIfAbsent(flight.getFrom(), k -> new ArrayList<>())
                .add(flight);
    }

    // Add all flights
    public void addAllFlights(List<Flight> flights) {
        for (Flight flight : flights) {
            addFlight(flight);
        }
    }

    // Dijkstra's Algorithm to find the cheapest route
    public List<Flight> findCheapestRoute(String from, String to, int passengers) {
        Map<String, Double> cost = new HashMap<>();
        Map<String, Flight> previous = new HashMap<>();
        PriorityQueue<String> queue = new PriorityQueue<>(Comparator.comparingDouble(cost::get));

        cost.put(from, 0.0);
        queue.add(from);

        while (!queue.isEmpty()) {
            String current = queue.poll();

            if (current.equals(to)) break;

            for (Flight flight : adjacencyList.getOrDefault(current, new ArrayList<>())) {
                String neighbor = flight.getTo();
                double flightCost = flight.getDistance() * 0.10 * passengers;
                double newCost = cost.get(current) + flightCost;

                if (newCost < cost.getOrDefault(neighbor, Double.MAX_VALUE)) {
                    cost.put(neighbor, newCost);
                    previous.put(neighbor, flight);
                    queue.add(neighbor);
                }
            }
        }

        // Reconstruct the path
        List<Flight> path = new LinkedList<>();
        String current = to;

        while (previous.containsKey(current)) {
            Flight f = previous.get(current);
            path.add(0, f);  // Add to front
            current = f.getFrom();
        }

        return path.isEmpty() || !path.get(0).getFrom().equals(from)
                ? Collections.emptyList()
                : path;
    }
}
