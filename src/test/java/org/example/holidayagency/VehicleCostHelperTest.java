package org.example.holidayagency;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class VehicleCostHelperTest {
    @Test
    void shouldSelectCarWhenMileageSavingsExceedParkingFee() {
        String vehicle = VehicleCostHelper.chooseBestVehicle(4, 10);
        assertEquals("Car", vehicle);
    }

    @Test
    void shouldSelectTaxiWhenShortTripMakesCarParkingTooCostly() {
        String vehicle = VehicleCostHelper.chooseBestVehicle(1, 2);
        assertEquals("Taxi", vehicle);
    }

    @Test
    void testTaxiCostWith1Taxi() {
        double cost = VehicleCostHelper.calculateCost("Taxi", 3, 5);
        // 2 * 5 * 0.40 * 1
        assertEquals(4.0, cost, 0.01);
    }

    @Test
    void testCarCostWith1Car() {
        double cost = VehicleCostHelper.calculateCost("Car", 4, 10);
        // 2*10*0.20 + 3
        assertEquals(7.0, cost, 0.01);
    }

    @Test
    void testTaxiCostWith2Taxis() {
        double cost = VehicleCostHelper.calculateCost("Taxi", 5, 10);
        // 2 * 10 * 0.40 * 2 = 16.00
        assertEquals(16.0, cost, 0.01);
    }

    @Test
    void testCarCostWith3Cars() {
        double cost = VehicleCostHelper.calculateCost("Car", 9, 15);
        // Each car: 2 * 15 * 0.20 = 6.00 + 3 parking = 9.00 → total 3 * 9 = 27
        assertEquals(27.0, cost, 0.01);
    }
}
