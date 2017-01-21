package ua.in.smartjava.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import ua.in.smartjava.domain.flight.FlightDTO;
import ua.in.smartjava.domain.flight.FlightRepository;

@RestController
public class FlightsController {

    private FlightRepository flightRepository;

    @Autowired
    public FlightsController(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    @GetMapping("/flights")
    public ResponseEntity<List<FlightDTO>> getFlights() {
        List<FlightDTO> flightDtos = flightRepository.findAll()
//                .findFirst100ByOrderByIdDesc()
                .stream()
                .map(FlightDTO::new)
                .collect(Collectors.toList());
        return new ResponseEntity<>(flightDtos, HttpStatus.OK);
    }

    @RequestMapping(value = "/flights/{id}", method = RequestMethod.GET)
    public List<FlightDTO> getFlightById(@PathVariable("id") String flightId) throws Exception {
        return flightRepository.findByFlight(flightId).stream()
                .map(FlightDTO::new)
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/flights_hex", method = RequestMethod.GET)
    public List<FlightDTO> getFlightByFlightIdAndHex(@RequestParam(value = "flightId") String flightId,
                                                     @RequestParam(value = "hexId") String hexId) throws Exception {
        return flightRepository.findByFlightAndHex(flightId, hexId).stream()
                .filter(flight -> !flight.getLat().isEmpty())
                .filter(flight -> new Double(flight.getLat()).intValue() > 0)
                .map(FlightDTO::new)
                .collect(Collectors.toList());
    }

}