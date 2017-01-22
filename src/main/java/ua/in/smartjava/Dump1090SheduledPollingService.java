package ua.in.smartjava;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import ua.in.smartjava.domain.flight.Flight;
import ua.in.smartjava.domain.flight.FlightDTO;
import ua.in.smartjava.domain.flight.FlightsService;
import ua.in.smartjava.utils.PredicateUtils;

@Service
public class Dump1090SheduledPollingService {
    private static final Logger LOGGER = LoggerFactory.getLogger(Dump1090SheduledPollingService.class);

    @Autowired
    FlightsService flightsService;

    @Autowired
    RestTemplate restTemplate;

    @Value("${dump1090.url}")
    String dumpl1090Url;

    Set<FlightDTO> previousPollingResults = new HashSet<>();

    @Scheduled(fixedRateString = "${dump1090.rate}", initialDelayString = "${dump1090.initialDelay}")
    public void pollDump1090ServerWithRate() {

        ResponseEntity<Flight[]> dump1090Boards = restTemplate.getForEntity(dumpl1090Url, Flight[].class);

        LOGGER.info("QueryDump1090 {} {}", dump1090Boards.getStatusCode(), dump1090Boards.getBody().length);
        Set<Flight> currentPollingResults = Arrays.stream(dump1090Boards.getBody())
                .filter(PredicateUtils.correctlyFilledFlightPredicate)
                .collect(Collectors.toSet());

        Set<FlightDTO> currentPollingResultsNoPrevious = currentPollingResults.stream()
                .map(FlightDTO::new)
                .collect(Collectors.toSet());
        currentPollingResultsNoPrevious.removeAll(previousPollingResults);

        LOGGER.debug("Previous polling {}", previousPollingResults);

        if (!CollectionUtils.isEmpty(currentPollingResultsNoPrevious)) {
            currentPollingResultsNoPrevious.stream().forEach(flightDto -> {
                flightDto.setFlight(StringUtils.trimAllWhitespace(flightDto.getFlight()));
                flightsService.saveFlight(new Flight(flightDto));
                LOGGER.info("Saved flightDto {}", flightDto);
            });
        } else {
            LOGGER.debug("No new flights found.");
        }

        previousPollingResults = currentPollingResults.stream()
                .map(FlightDTO::new)
                .collect(Collectors.toSet());
    }

}