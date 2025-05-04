package org.example;
import org.example.holidayagency.*;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Map<String, String> airportToCity = Map.of(
                "L", "London",
                "P", "Paris",
                "R", "Rome",
                "M", "Madrid"
        );

        List<Flight> flights = List.of(
                // Direct Flights (some expensive)
                Flight.parseFlightDetails("LP1200"), // London → Paris (expensive on purpose)
                Flight.parseFlightDetails("PL800"),  // Paris → London

                Flight.parseFlightDetails("LM700"),  // London → Madrid
                Flight.parseFlightDetails("ML500"),  // Madrid → London (cheaper return)

                Flight.parseFlightDetails("LR1000"), // London → Rome (expensive)
                Flight.parseFlightDetails("RL1000"), // Rome → London

                Flight.parseFlightDetails("PR900"),  // Paris → Rome
                Flight.parseFlightDetails("RP900"),  // Rome → Paris

                Flight.parseFlightDetails("PM700"),  // Paris → Madrid
                Flight.parseFlightDetails("MP700"),  // Madrid → Paris

                Flight.parseFlightDetails("MR600"),  // Madrid → Rome
                Flight.parseFlightDetails("RM600"),  // Rome → Madrid

                // Indirect Routes to enable alternate cheaper paths
                Flight.parseFlightDetails("LR400"),  // London → Rome (shorter leg)
                Flight.parseFlightDetails("RP400"),  // Rome → Paris (shorter leg)
                Flight.parseFlightDetails("PM300"),  // Paris → Madrid (shorter)
                Flight.parseFlightDetails("MR200"),  // Madrid → Rome (shorter)
                Flight.parseFlightDetails("RL600"),  // Rome → London (alternate)
                Flight.parseFlightDetails("LM300")   // London → Madrid (shorter leg)
        );

        FlightRouter router = new FlightRouter();
        router.addAllFlights(flights);
        JourneyPlanner planner = new JourneyPlanner(router);

        Scanner scanner = new Scanner(System.in);

        System.out.println("🌍 Welcome to the Holiday Journey Planner!");
        System.out.println("Available Cities:");
        airportToCity.forEach((code, city) ->
                System.out.println("  " + code + " → " + city)
        );
        System.out.println("====================================================");

        System.out.print("📍 Enter departure city code (L/P/R/M): ");
        String departureCode = scanner.nextLine().trim().toUpperCase();

        if (!airportToCity.containsKey(departureCode)) {
            System.out.println("❌ Invalid departure city.");
            return;
        }

        System.out.print("🚗 How far (in miles) are you from " + airportToCity.get(departureCode) + " airport? ");
        int distanceToAirport = Integer.parseInt(scanner.nextLine());

        System.out.print("👥 Enter number of passengers: ");
        int passengers = Integer.parseInt(scanner.nextLine());

        System.out.print("🛫 Enter destination city code (L/P/R/M): ");
        String destinationCode = scanner.nextLine().trim().toUpperCase();

        if (!airportToCity.containsKey(destinationCode)) {
            System.out.println("❌ Invalid destination city.");
            return;
        }

        Journey journey = new Journey(passengers, departureCode, distanceToAirport, destinationCode);
        JourneySuggestion suggestion = planner.planJourney(journey);

        System.out.println("\n✅ Journey Suggestion:");
        System.out.println("From: " + airportToCity.get(departureCode));
        System.out.println("To: " + airportToCity.get(destinationCode));
        System.out.println("--------------------------------------------------");

        System.out.println("Vehicle: " + suggestion.getVehicle());
        System.out.printf("Vehicle Return Cost: £%.2f%n", suggestion.getVehicleCost());

        if (suggestion.getOutboundRoute().isEmpty() || suggestion.getOutboundRoute().get(0).equals("No outbound flight")) {
            System.out.println("Outbound Flight Route: No outbound flight");
            System.out.printf("Outbound Flight Cost: £%.2f%n", 0.0);
        } else {
            String outbound = suggestion.getOutboundRoute().stream()
                    .map(code -> routeToReadable(code, airportToCity))
                    .collect(Collectors.joining(" => "));
            System.out.println("Outbound Flight Route: " + outbound);
            System.out.printf("Outbound Flight Cost: £%.2f%n", suggestion.getOutboundCost());
        }

        if (suggestion.getInboundRoute().isEmpty() || suggestion.getInboundRoute().get(0).equals("No inbound flight")) {
            System.out.println("Inbound Flight Route: No inbound flight");
            System.out.printf("Inbound Flight Cost: £%.2f%n", 0.0);
        } else {
            String inbound = suggestion.getInboundRoute().stream()
                    .map(code -> routeToReadable(code, airportToCity))
                    .collect(Collectors.joining(" => "));
            System.out.println("Inbound Flight Route: " + inbound);
            System.out.printf("Inbound Flight Cost: £%.2f%n", suggestion.getInboundCost());
        }

        System.out.printf("Total Journey Cost: £%.2f%n", suggestion.getTotalCost());
    }

    private static String routeToReadable(String flightCode, Map<String, String> airportMap) {
        if (flightCode == null || flightCode.length() < 3) return "Invalid flight";
        String from = flightCode.substring(0, 1);
        String to = flightCode.substring(1, 2);
        String miles = flightCode.substring(2);

        String fromCity = airportMap.getOrDefault(from, from);
        String toCity = airportMap.getOrDefault(to, to);

        return fromCity + " → " + toCity + " [" + miles + " mi]";
    }
}