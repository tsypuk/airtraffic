package ua.in.smartjava.domain.flight;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightsService {

    @Autowired
    private FlightRepository flightRepository;

    //TODO change this implementation that will be based on time unit from properties and use DISTINCT
    public List<Flight> checkForNewUniqueFlights() {
        return flightRepository.findFirst100ByOrderByIdDesc();
    }

    public void saveFlight(Flight flight) {
        flightRepository.save(flight);
    }

    public void saveFlights(List<Flight> flights) {
        flightRepository.save(flights);
    }

}