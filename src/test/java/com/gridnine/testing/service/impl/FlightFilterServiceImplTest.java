package com.gridnine.testing.service.impl;

import com.gridnine.testing.model.Flight;
import com.gridnine.testing.model.Segment;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class FlightFilterServiceImplTest {

    public static final LocalDateTime NOW_PLUS_1H = LocalDateTime.now().plusHours(1);

    public static final Segment SEGMENT_2H_IN_AIR_DEPARTURE_1H_AFTER_NOW = new Segment(NOW_PLUS_1H, NOW_PLUS_1H.plusHours(2));
    public static final Segment SEGMENT_2H_IN_AIR_DEPARTURE_3H_AFTER_NOW = new Segment(NOW_PLUS_1H.plusHours(2), NOW_PLUS_1H.plusHours(4));
    public static final Segment SEGMENT_4H_IN_AIR_DEPARTURE_11H_AFTER_NOW = new Segment(NOW_PLUS_1H.plusHours(10), NOW_PLUS_1H.plusHours(14));
    public static final Segment SEGMENT_6H_DEPARTURE_1H_BEFORE_NOW = new Segment(NOW_PLUS_1H.minusHours(2), NOW_PLUS_1H.plusHours(4));
    public static final Segment SEGMENT_ARRIVAL_BEFORE_DEPARTURE = new Segment(NOW_PLUS_1H.plusHours(4), NOW_PLUS_1H.minusHours(2));


    public static final Flight FLIGHT_2H_IN_AIR_DEPARTURE_1H_AFTER_NOW_0H_BETWEEN = new Flight(Arrays.asList(SEGMENT_2H_IN_AIR_DEPARTURE_1H_AFTER_NOW));
    public static final Flight FLIGHT_6H_IN_AIR_DEPARTURE_1H_AFTER_NOW_8H_BETWEEN = new Flight(Arrays.asList(SEGMENT_2H_IN_AIR_DEPARTURE_1H_AFTER_NOW, SEGMENT_4H_IN_AIR_DEPARTURE_11H_AFTER_NOW));
    public static final Flight FLIGHT_4H_IN_AIR_DEPARTURE_1H_AFTER_NOW_0H_BETWEEN = new Flight(Arrays.asList(SEGMENT_2H_IN_AIR_DEPARTURE_1H_AFTER_NOW, SEGMENT_2H_IN_AIR_DEPARTURE_3H_AFTER_NOW));
    public static final Flight FLIGHT_6H_IN_AIR_DEPARTURE_BEFORE_NOW_0H_BETWEEN = new Flight(Arrays.asList(SEGMENT_6H_DEPARTURE_1H_BEFORE_NOW));
    public static final Flight FLIGHT_ARRIVAL_BEFORE_DEPARTURE = new Flight(Arrays.asList(SEGMENT_ARRIVAL_BEFORE_DEPARTURE));
    public static final List<Flight> BASIC_LIST_OF_FLIGHTS = Arrays.asList(
            FLIGHT_2H_IN_AIR_DEPARTURE_1H_AFTER_NOW_0H_BETWEEN,
            FLIGHT_6H_IN_AIR_DEPARTURE_1H_AFTER_NOW_8H_BETWEEN,
            FLIGHT_4H_IN_AIR_DEPARTURE_1H_AFTER_NOW_0H_BETWEEN,
            FLIGHT_6H_IN_AIR_DEPARTURE_BEFORE_NOW_0H_BETWEEN,
            FLIGHT_ARRIVAL_BEFORE_DEPARTURE
    );

    public static final List<Flight> LIST_OF_FLIGHTS_TIME_BETWEEN_SEGMENTS_LESS_2H = Arrays.asList(
            FLIGHT_2H_IN_AIR_DEPARTURE_1H_AFTER_NOW_0H_BETWEEN,
            FLIGHT_4H_IN_AIR_DEPARTURE_1H_AFTER_NOW_0H_BETWEEN,
            FLIGHT_6H_IN_AIR_DEPARTURE_BEFORE_NOW_0H_BETWEEN,
            FLIGHT_ARRIVAL_BEFORE_DEPARTURE
    );

    public static final List<Flight> LIST_OF_FLIGHTS_WITHOUT_DEPARTURE_BEFORE_NOW = Arrays.asList(
            FLIGHT_2H_IN_AIR_DEPARTURE_1H_AFTER_NOW_0H_BETWEEN,
            FLIGHT_6H_IN_AIR_DEPARTURE_1H_AFTER_NOW_8H_BETWEEN,
            FLIGHT_4H_IN_AIR_DEPARTURE_1H_AFTER_NOW_0H_BETWEEN,
            FLIGHT_ARRIVAL_BEFORE_DEPARTURE
    );

    public static final List<Flight> LIST_OF_FLIGHTS_ARRIVAL_BEFORE_DEPARTURE = Arrays.asList(
            FLIGHT_2H_IN_AIR_DEPARTURE_1H_AFTER_NOW_0H_BETWEEN,
            FLIGHT_6H_IN_AIR_DEPARTURE_1H_AFTER_NOW_8H_BETWEEN,
            FLIGHT_4H_IN_AIR_DEPARTURE_1H_AFTER_NOW_0H_BETWEEN,
            FLIGHT_6H_IN_AIR_DEPARTURE_BEFORE_NOW_0H_BETWEEN
    );

    private final FlightFilterServiceImpl out = new FlightFilterServiceImpl();


    @Test
    void excludedFlightsWithDepartureTimeBeforeNow() {
        List<Flight> result = out.excludedFlightsWithDepartureTimeBeforeNow(BASIC_LIST_OF_FLIGHTS);
        List<Flight> expected = LIST_OF_FLIGHTS_WITHOUT_DEPARTURE_BEFORE_NOW;
        Assertions.assertThat(result).isEqualTo(expected);
    }

    @Test
    void excludedFlightsWithArrivalTimeBeforeDepartureTime() {
        List<Flight> result = out.excludedFlightsWithArrivalTimeBeforeDepartureTime(BASIC_LIST_OF_FLIGHTS);
        List<Flight> expected = LIST_OF_FLIGHTS_ARRIVAL_BEFORE_DEPARTURE;
        Assertions.assertThat(result).isEqualTo(expected);
    }

    @Test
    void excludedFlightsWithTimeBetweenSegmentsMoreThanTwoHours() {
        List<Flight> result = out.excludedFlightsWithTimeBetweenSegmentsMoreThanTwoHours(BASIC_LIST_OF_FLIGHTS);
        List<Flight> expected = LIST_OF_FLIGHTS_TIME_BETWEEN_SEGMENTS_LESS_2H;
        Assertions.assertThat(result).isEqualTo(expected);
    }
}