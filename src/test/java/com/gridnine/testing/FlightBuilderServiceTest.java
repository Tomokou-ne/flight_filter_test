package com.gridnine.testing;

import com.gridnine.testing.model.Flight;
import com.gridnine.testing.model.FlightBuilder;
import com.gridnine.testing.model.Segment;
import com.gridnine.testing.service.FlightBuilderService;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.Assert.assertEquals;

public class FlightBuilderServiceTest {

    private final FlightBuilderService flightFilter = new FlightBuilderService();
    private static final LocalDateTime threeDaysFromNow = LocalDateTime.now().plusDays(3);
    private static final Segment segment1 = new Segment(threeDaysFromNow, threeDaysFromNow.plusHours(2));
    private static final Segment segment2 = new Segment(threeDaysFromNow.plusHours(3), threeDaysFromNow.plusHours(4));
    private static final Segment segment3 = new Segment(LocalDateTime.now().minusDays(3), LocalDateTime.now().minusDays(3).plusHours(2));
    private static final Segment segment4 = new Segment(threeDaysFromNow, threeDaysFromNow.minusHours(6));
    private static final Segment segment5 = new Segment(threeDaysFromNow.plusHours(6), threeDaysFromNow.plusHours(7));

    //A normal flight
    protected static final Flight flight1 = new Flight(Arrays.asList(segment1, segment2), 1);
    //A flight departing in the past
    protected static final Flight flight2 = new Flight(Collections.singletonList(segment3), 2);
    //A flight that arrives earlier than it left
    protected static final Flight flight3 = new Flight(Collections.singletonList(segment4), 3);
    //A flight with more than two hours ground time
    protected static final Flight flight4 = new Flight(Arrays.asList(segment1, segment2, segment5), 4);

    protected final List<Flight> flights = List.of(flight1, flight2, flight3, flight4);

    @Test
    public void getArrivalDateLessDepartureDate() {
        final Set<Flight> actualList = flightFilter.filterArrivalBeforeDeparture(flights);
        final List<Flight> expectedList = Arrays.asList(flight1, flight2, flight4);
        Assert.assertNotNull(flights);
        assertEquals(expectedList.size(), actualList.size());
    }

    @Test
    public void getDepartureDateEarlierThanTimeNow() {
        final Set<Flight> actualList = flightFilter.filterArrivalBeforeDeparture(flights);
        final List<Flight> expectedList = Arrays.asList(flight1, flight3, flight4);
        Assert.assertNotNull(flights);
        assertEquals(expectedList.size(), actualList.size());
    }

    @Test
    public void getFlightsWithoutTwoHoursOnTheGround() {
        final Set<Flight> actualList = flightFilter.filterArrivalBeforeDeparture(flights);
        final List<Flight> expectedList = Arrays.asList(flight1, flight2, flight3);
        Assert.assertNotNull(flights);
        assertEquals(expectedList.size(), actualList.size());
    }
}
