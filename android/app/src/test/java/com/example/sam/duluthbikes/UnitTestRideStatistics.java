package com.example.sam.duluthbikes;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Unit Test for Ride Statistics
 */

public class UnitTestRideStatistics {

    @Test
    public void testStatsCalculations() throws Exception {

        EndRideActivity endRide = new EndRideActivity();
        double distance = 10000; //10 km
        long timelaspe = 3600000; //1hour in milisec

        assertEquals(10.0, endRide.getKmPerHour(distance, timelaspe), 0.001);


    }
}
