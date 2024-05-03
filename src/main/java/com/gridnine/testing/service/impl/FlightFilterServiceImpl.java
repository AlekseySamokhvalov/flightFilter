package com.gridnine.testing.service.impl;

import com.gridnine.testing.model.Flight;
import com.gridnine.testing.model.Segment;
import com.gridnine.testing.service.FlightFilterService;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FlightFilterServiceImpl implements FlightFilterService {

    @Override
    // фильтрация списка перелётов (Flight) на основе условия, что время вылета сегмента перелёта раньше текущего момента времени.
    public List<Flight> excludedFlightsWithDepartureTimeBeforeNow(List<Flight> list) {

        List<Flight> elementToDelete = new ArrayList<>(); // список для хранения перелётов, которые нужно исключить из исходного списка.
        List<Flight> incomingList = new ArrayList<>(); // копия исходного списка, в которой будут происходить изменения.
        incomingList.addAll(list); //  добавление всех элементов из списка list в список incomingList

        for (Flight flight : incomingList) {
            List<Segment> segments = flight.getSegments();

            for (Segment segment : segments) {
                // если дата вылета (departureDate) какого-либо сегмента раньше текущего момента времени (LocalDateTime.now()),
                if (segment.getDepartureDate().isBefore(LocalDateTime.now())) {
                    elementToDelete.add(flight);  // то такой перелет добавляется в список elementToDelete.
                }

            }

        }
        // удаляем из списка incomingList все перелёты, которые были помещены в список elementToDelete
        incomingList.removeAll(elementToDelete);
        return incomingList;
    }

    public List<Flight> excludedFlightsWithArrivalTimeBeforeDepartureTime(List<Flight> list) {
        List<Flight> elementToDelete = new ArrayList<>();
        List<Flight> incomingList = new ArrayList<>();
        incomingList.addAll(list);

        for (Flight flight : incomingList) {
            List<Segment> segments = flight.getSegments();

            for (Segment segment : segments) {
                // если дата вылета (departureDate) сегмента перелёта позже даты прилёта (arrivalDate) этого же сегмента
                if (segment.getDepartureDate().isAfter(segment.getArrivalDate())) {
                    elementToDelete.add(flight); // добавляется в список elementToDelete для последующего удаления
                }

            }

        }
        incomingList.removeAll(elementToDelete);
        return incomingList;
    }

    public List<Flight> excludedFlightsWithTimeBetweenSegmentsMoreThanTwoHours(List<Flight> list) {
        List<Flight> elementToDelete = new ArrayList<>();
        List<Flight> incomingList = new ArrayList<>();
        incomingList.addAll(list);

        for (Flight flight : incomingList) {
            List<Segment> segments = flight.getSegments(); // Получаем список сегментов (segments) данного перелёта
            long count = 0; // для подсчета общего времени, проведенного на земле между сегментами перелёта

            for (int i = 0; i < segments.size(); i++) {
                Segment current = segments.get(i);

                if (i + 1 < segments.size()) { // если существует следующий сегмент
                    Segment next = segments.get(i+1); // получаем следующий сегмент для подсчета времени между прибытием текущего и вылетом следующего
                    // Считаем количество минут (Duration) между временем прибытия текущего сегмента и временем вылета следующего сегмента, добавляя это время к общему времени count
                    count = count + Duration.between(current.getArrivalDate(), next.getDepartureDate()).toMinutes();
                }
            }
            if (count/60.0 > 2) {
                // Если общее время на земле между сегментами больше 2 часов, добавляем данный перелёт в список elementToDelete для последующего удаления
                elementToDelete.add(flight);
                count = 0;
            }

        }
        incomingList.removeAll(elementToDelete);
        return incomingList;
    }
}
