package org.example.holidayagency;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@Data
@AllArgsConstructor
public class JourneySuggestion {
    private String vehicle;
    private double vehicleCost;

    private List<String> outboundRoute;
    private double outboundCost;

    private List<String> inboundRoute;
    private double inboundCost;

    private double totalCost;

    public static JourneySuggestion empty() {
        return new JourneySuggestion(
                "N/A", 0.0,
                List.of("No outbound flight"), 0.0,
                List.of("No inbound flight"), 0.0,
                0.0
        );
    }

}
