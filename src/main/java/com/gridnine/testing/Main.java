package com.gridnine.testing;

import com.gridnine.testing.model.Flight;
import com.gridnine.testing.model.FlightBuilder;
import com.gridnine.testing.service.FlightBuilderService;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        FlightBuilder flightBuilder = new FlightBuilder();
        List<Flight> flightList = flightBuilder.createFlights();
        FlightBuilderService flightFilter = new FlightBuilderService();

        System.out.println("List of all flights" +"\n");
        flightFilter.getAllFlights(flightList);

        System.out.println("Method that excludes departure until the current point in time" + "\n");
        flightFilter.filterDepartureBeforeNow(flightList);

        System.out.println("Method that excludes segments with an arrival date earlier than the departure date" + "\n");
        flightFilter.filterArrivalBeforeDeparture(flightList);

        System.out.println("Method that excludes flights where the total time spent on the ground exceeds two hours" + "\n");
        flightFilter.filterSumTimeOnGroundMoreThanTwoHours(flightList);

    }
}