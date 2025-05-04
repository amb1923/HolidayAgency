package org.example.holidayagency;

public class VehicleCostHelper {

    private static final double TAXI_COST_PER_MILE = 0.40;
    private static final double CAR_COST_PER_MILE = 0.20;
    private static final double CAR_PARKING_FEE = 3.00;
    private static final int VEHICLE_CAPACITY = 4;

    public static String chooseBestVehicle(int passengers, int miles) {
        double taxiCost = calculateTaxiCost(passengers, miles);
        double carCost = calculateCarCost(passengers, miles);
        return taxiCost <= carCost ? "Taxi" : "Car";
    }

    public static double calculateCost(String vehicleType, int passengers, int miles) {
        return switch (vehicleType) {
            case "Taxi" -> calculateTaxiCost(passengers, miles);
            case "Car" -> calculateCarCost(passengers, miles);
            default -> throw new IllegalArgumentException("Unknown vehicle type: " + vehicleType);
        };
    }


    private static double calculateTaxiCost(int passengers, int miles) {
        int numTaxis = (int) Math.ceil(passengers / (double) VEHICLE_CAPACITY);
        return 2 * miles * TAXI_COST_PER_MILE * numTaxis; // Return journey
    }


    private static double calculateCarCost(int passengers, int miles) {
        int numCars = (int) Math.ceil(passengers / (double) VEHICLE_CAPACITY);
        return 2 * miles * CAR_COST_PER_MILE * numCars + CAR_PARKING_FEE * numCars;
    }
}
