package com.gridnine.testing.service;

import com.gridnine.testing.model.Flight;
import com.gridnine.testing.model.Segment;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FlightBuilderService {

    private DateTimeFormatter getDateFormatter() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
    }
    DateTimeFormatter dateTimeFormatter = getDateFormatter();


    public void getAllFlights(List<Flight> flights) {
        for (Flight flight : flights) {
            System.out.println("Flight number " +  flight.getId());
            for (int i = 0; i < flight.getSegments().size(); i++) {
                System.out.println(dateTimeFormatter.format(flight.getSegments().get(i).getDepartureDate())
                        + " - "
                        + dateTimeFormatter.format(flight.getSegments().get(i).getArrivalDate()));
            }
        }
    }

    public void filterDepartureBeforeNow(List<Flight> flights) {
        LocalDateTime timeNow = LocalDateTime.now();
        Set<Flight> resultSet = new HashSet<>();
        List<Segment> split = new ArrayList<>();
        for (Flight flight : flights ) {
            split.addAll(flight.getSegments());

            while (split.size() > 0) {
                LocalDateTime departureTime = split.get(0).getDepartureDate();
                LocalDateTime arrivalTime = split.remove(0).getArrivalDate();
                if(departureTime.isAfter(timeNow)) {
                    showCorrectDepartureFlight(flight, departureTime, arrivalTime);
                    resultSet.add(flight);
                }
            }

        }
    }

    public Set<Flight> filterArrivalBeforeDeparture(List<Flight> flights) {
        Set<Flight> resultSet = new HashSet<>();
        List<Segment> split = new ArrayList<>();
        for (Flight flight : flights ) {
            split.addAll(flight.getSegments());

            while (split.size() > 0) {
                LocalDateTime departureTime = (split.get(0).getDepartureDate());
                LocalDateTime arrivalTime = (split.remove(0).getArrivalDate());
                if(arrivalTime.isAfter(departureTime)) {
                    showCorrectDepartureFlight(flight, departureTime, arrivalTime);
                    resultSet.add(flight);
                }
            }

        }
        return resultSet;
    }

    public void filterSumTimeOnGroundMoreThanTwoHours(List<Flight> flights) {
        Set<Flight> resultSet = new HashSet<>(flights);
        List<Segment> split = new ArrayList<>();
        for (Flight flight : flights ) {
            split.addAll(flight.getSegments());
            if (split.size() > 2) {
                while (split.size() > 2) {
                    LocalDateTime arrivalTime = split.remove(0).getArrivalDate();
                    LocalDateTime departureTime = split.remove(1).getDepartureDate();
                    if(arrivalTime.plusHours(2).isAfter(departureTime)) {
                        showCorrectArrivalFlight(flight, arrivalTime, departureTime);
                        resultSet.remove(flight);
                    }
                }
            }
        }
    }

    public void showCorrectDepartureFlight(Flight flight, LocalDateTime departureTime, LocalDateTime arrivalTime) {
        DateTimeFormatter dateTimeFormatter = getDateFormatter();
        System.out.println( "Flight number - " + flight.getId()  + "\n" +
                "Departure time: " + dateTimeFormatter.format(departureTime) + "\n" +
                "Arrival time: " + dateTimeFormatter.format(arrivalTime) );
        System.out.println("---------------------------------------------------------------------------");
    }

    private void showCorrectArrivalFlight(Flight flight, LocalDateTime arrivalTime, LocalDateTime departureTime) {
        DateTimeFormatter dateTimeFormatter = getDateFormatter();
        System.out.println( "Flight number - " + flight.getId()  + "\n" +
                "Arrival time: " + dateTimeFormatter.format(arrivalTime) + "\n" +
                "Departure time: " + dateTimeFormatter.format(departureTime)  );
        System.out.println("---------------------------------------------------------------------------");

    }

}
