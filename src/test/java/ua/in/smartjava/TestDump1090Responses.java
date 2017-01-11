package ua.in.smartjava;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import ua.in.smartjava.domain.flight.Flight;
import ua.in.smartjava.domain.flight.FlightsService;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TestDump1090Responses {

    private static final String DUMP1090_URL = "http://localhost/dummyDump";
    
    @InjectMocks
    private Dump1090SheduledPollingService service;

    @Mock
    FlightsService flightsService;

    @Mock
    RestTemplate restTemplate;

    @Before
    public void init() {
        ReflectionTestUtils.setField(service, "dumpl1090Url", DUMP1090_URL);
    }

    /**
     * 1 call NO_DATA []
     */
    @Test
    public void testNoDataFromDump1090() {
        /* GIVEN */
        /* WHEN */
        when(restTemplate.getForEntity(eq(DUMP1090_URL), eq(Flight[].class))).thenReturn(
                new ResponseEntity(new Flight[]{}, HttpStatus.OK)
        );
        service.pollDump1090ServerWithRate();
        /* THEN */
        verifyZeroInteractions(flightsService);
    }

    /**
     * 1 call [hex = 1, lat = 1, lon = 1]
     */
    @Test
    public void testOneNewElement() {
        /* GIVEN */
        final Flight flightToSave = Flight.builder().hex("1").lat("1").lon("1").build();
        final Flight[] flights = {flightToSave};
        /* WHEN */
        when(restTemplate.getForEntity(eq(DUMP1090_URL), eq(Flight[].class))).thenReturn(
                new ResponseEntity(flights, HttpStatus.OK)
        );
        service.pollDump1090ServerWithRate();
        /* THEN */
        verify(flightsService).saveFlight(eq(flightToSave));
    }

    /**
     * 1 call [hex = 1, lat = 1, lon = 1]
     * 2 call [hex = 1, lat = 1, lon = 1]
     */
    @Test
    public void test2TimesSameElement() {
        /* GIVEN */
        final Flight flightToSave = Flight.builder().hex("1").lat("1").lon("1").build();
        final Flight[] flights = {flightToSave};
        /* WHEN */
        when(restTemplate.getForEntity(eq(DUMP1090_URL), eq(Flight[].class))).thenReturn(
                new ResponseEntity(flights, HttpStatus.OK)
        );
        service.pollDump1090ServerWithRate();
        /* THEN */
        verify(flightsService).saveFlight(eq(flightToSave));
        
        service.pollDump1090ServerWithRate();
        service.pollDump1090ServerWithRate();
        verifyNoMoreInteractions(flightsService);
    }

    /**
     * 1 call [hex = 1, lat = 1, lon = 1, flight = "FL"]
     * 2 call [hex = 1, lat = 1, lon = 1, flight = "FL "]
     */
    @Test
    public void test2TimesSameWithSpacesElement() {
        /* GIVEN */
        final Flight flightToSave = Flight.builder().hex("1").lat("1").lon("1").flight("FL").build();
        Flight[] flights = {flightToSave};
        /* WHEN */
        when(restTemplate.getForEntity(eq(DUMP1090_URL), eq(Flight[].class))).thenReturn(
                new ResponseEntity(flights, HttpStatus.OK)
        );
        service.pollDump1090ServerWithRate();
        /* THEN */
        verify(flightsService).saveFlight(eq(flightToSave));

        /* GIVEN */
        final Flight secondFlight = Flight.builder().hex("1").lat("1").lon("1").flight("FL ").build();
        flights = new Flight[]{secondFlight};
        /* WHEN */
        reset(restTemplate);
        when(restTemplate.getForEntity(eq(DUMP1090_URL), eq(Flight[].class))).thenReturn(
                new ResponseEntity(flights, HttpStatus.OK)
        );
        service.pollDump1090ServerWithRate();
        /* THEN */
        verifyNoMoreInteractions(flightsService);
    }

    /**
     * 1 call [hex = 1, lat = 1, lon = 1]
     * 2 call [{hex = 1, lat = 1, lon = 1}, {hex = 2, lat = 2, lon = 2}]
     */
    @Test
    public void testSameWithNewElementsTest() {
        /* GIVEN */
        final Flight firstFlightToSave = Flight.builder().hex("1").lat("1").lon("1").build();
        final Flight secondFlightToSave = Flight.builder().hex("2").lat("2").lon("2").build();

        Flight[] flights = {firstFlightToSave};
        /* WHEN */
        when(restTemplate.getForEntity(eq(DUMP1090_URL), eq(Flight[].class))).thenReturn(
                new ResponseEntity(flights, HttpStatus.OK)
        );
        service.pollDump1090ServerWithRate();
        /* THEN */
        verify(flightsService).saveFlight(eq(firstFlightToSave));
        verifyNoMoreInteractions(flightsService);

        /* GIVEN */
        flights = new Flight[] {firstFlightToSave, secondFlightToSave};
        /* WHEN */
        reset(restTemplate);
        reset(flightsService);
        when(restTemplate.getForEntity(eq(DUMP1090_URL), eq(Flight[].class))).thenReturn(
                new ResponseEntity(flights, HttpStatus.OK)
        );
        /* THEN */
        service.pollDump1090ServerWithRate();
        verify(flightsService).saveFlight(eq(secondFlightToSave));
        verifyNoMoreInteractions(flightsService);
    }

    /**
     * 1 call [{hex = 1, lat = 1, lon = 1}, {hex = 2, lat = 2, lon = 2}]
     * 2 call [hex = 2, lat = 2, lon = 2]
     */
    @Test
    public void testSameTwoElementsTest() {
        /* GIVEN */
        final Flight firstFlightToSave = Flight.builder().hex("1").lat("1").lon("1").build();
        final Flight secondFlightToSave = Flight.builder().hex("2").lat("2").lon("2").build();

        Flight[] flights = {firstFlightToSave, secondFlightToSave};
        /* WHEN */
        when(restTemplate.getForEntity(eq(DUMP1090_URL), eq(Flight[].class))).thenReturn(
                new ResponseEntity(flights, HttpStatus.OK)
        );
        service.pollDump1090ServerWithRate();
        /* THEN */
        verify(flightsService).saveFlight(eq(firstFlightToSave));
        verify(flightsService).saveFlight(eq(secondFlightToSave));
        verifyNoMoreInteractions(flightsService);

        /* GIVEN */
        flights = new Flight[]{ secondFlightToSave };
        /* WHEN */
        reset(restTemplate);
        reset(flightsService);
        when(restTemplate.getForEntity(eq(DUMP1090_URL), eq(Flight[].class))).thenReturn(
                new ResponseEntity(flights, HttpStatus.OK)
        );
        /* THEN */
        service.pollDump1090ServerWithRate();
        verifyNoMoreInteractions(flightsService);
    }

}