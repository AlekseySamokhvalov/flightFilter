package com.gridnine.testing;

import com.gridnine.testing.model.Flight;
import com.gridnine.testing.model.FlightBuilder;
import com.gridnine.testing.service.FlightFilterService;
import com.gridnine.testing.service.impl.FlightFilterServiceImpl;


import java.util.List;

public class TestingApplication {

	public static void runFilters() {

		FlightFilterService filterService = new FlightFilterServiceImpl();
		List<Flight> initialFlightSet = FlightBuilder.createFlights();

		System.out.println("\nНачальный полетный набор: \n" + initialFlightSet + "\n");

		System.out.println("\nРейсы с предыдущим временем вылета были исключены: \n" +
				filterService.excludedFlightsWithDepartureTimeBeforeNow(initialFlightSet).toString() + "\n");

		System.out.println("\nРейсы, время прибытия которых было раньше времени вылета, было исключено: \n" +
				filterService.excludedFlightsWithArrivalTimeBeforeDepartureTime(initialFlightSet).toString() + "\n");

		System.out.println("\nИсключены рейсы со временем между сегментами более двух часов: \n" +
				filterService.excludedFlightsWithTimeBetweenSegmentsMoreThanTwoHours(initialFlightSet).toString() + "\n");
	}

}
