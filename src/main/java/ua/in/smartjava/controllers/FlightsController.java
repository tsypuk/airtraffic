package ua.in.smartjava.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
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
    public FlightDTO getFlightById(@PathVariable("id") long flightId) throws Exception {
        return Optional.ofNullable(flightRepository.getOne(flightId))
                .map(FlightDTO::new)
                .orElseThrow(() -> new Exception("Flight not found in DB"));
    }

}