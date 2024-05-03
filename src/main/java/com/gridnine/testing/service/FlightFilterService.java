package com.gridnine.testing.service;

import com.gridnine.testing.model.*;

import java.util.List;


public interface FlightFilterService {

    List<Flight> excludedFlightsWithDepartureTimeBeforeNow(List<Flight> list);
    List<Flight> excludedFlightsWithArrivalTimeBeforeDepartureTime(List<Flight> list);
    List<Flight> excludedFlightsWithTimeBetweenSegmentsMoreThanTwoHours(List<Flight> list);

}
