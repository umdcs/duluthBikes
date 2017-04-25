package com.example.sam.duluthbikes;

import org.junit.Test;

/**
 * Unit Test for Ride Statistics
 */

public class UnitTestRideStatistics {

    @Test
    public void testStatsCalculations() throws Exception {

        UnitConverter endRide = new UnitConverter();
        double distance = 10000; //10 km
        long timelaspe = 3600000; //1hour in milisec

        //assertEquals(10.0, endRide.getKmPerHour(distance, timelaspe), 0.001);
        //assertEquals(6.21371, endRide.getMilesPerHour(distance, timelaspe), 0.001);
        //assertEquals(10.0, endRide.getDistInKm(distance), 0.001);
        //assertEquals(6.21371, endRide.getDistInMiles(distance), 0.001);

    }
}
