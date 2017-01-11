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
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

/**
 * Test cases with invalid filled hex, lat, lon
 */
@RunWith(MockitoJUnitRunner.class)
public class TestInvalidFlightsResponses {

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

    @Test
    public void testInvalidHex() {
        /* GIVEN */
        final Flight[] flights = {Flight.builder().lat("1").lon("1").build()};
        /* WHEN */
        when(restTemplate.getForEntity(eq(DUMP1090_URL), eq(Flight[].class))).thenReturn(
                new ResponseEntity(flights, HttpStatus.OK)
        );
        service.pollDump1090ServerWithRate();
        /* THEN */
        verifyZeroInteractions(flightsService);
    }

    @Test
    public void testNullLat() {
        /* GIVEN */
        final Flight[] flights = {Flight.builder().hex("1").lon("1").build()};
        /* WHEN */
        when(restTemplate.getForEntity(eq(DUMP1090_URL), eq(Flight[].class))).thenReturn(
                new ResponseEntity(flights, HttpStatus.OK)
        );
        service.pollDump1090ServerWithRate();
        /* THEN */
        verifyZeroInteractions(flightsService);
    }

    @Test
    public void testZeroLat() {
        /* GIVEN */
        final Flight[] flights = {Flight.builder().hex("1").lon("1").lat("0").build()};
        /* WHEN */
        when(restTemplate.getForEntity(eq(DUMP1090_URL), eq(Flight[].class))).thenReturn(
                new ResponseEntity(flights, HttpStatus.OK)
        );
        service.pollDump1090ServerWithRate();
        /* THEN */
        verifyZeroInteractions(flightsService);
    }

    @Test
    public void testNotANumberLat() {
        /* GIVEN */
        final Flight[] flights = {Flight.builder().hex("1").lon("1").lat("abc").build()};
        /* WHEN */
        when(restTemplate.getForEntity(eq(DUMP1090_URL), eq(Flight[].class))).thenReturn(
                new ResponseEntity(flights, HttpStatus.OK)
        );
        service.pollDump1090ServerWithRate();
        /* THEN */
        verifyZeroInteractions(flightsService);
    }

    @Test
    public void testNullLon() {
        /* GIVEN */
        final Flight[] flights = {Flight.builder().hex("1").lat("1").build()};
        /* WHEN */
        when(restTemplate.getForEntity(eq(DUMP1090_URL), eq(Flight[].class))).thenReturn(
                new ResponseEntity(flights, HttpStatus.OK)
        );
        service.pollDump1090ServerWithRate();
        /* THEN */
        verifyZeroInteractions(flightsService);
    }

    @Test
    public void testZeroLon() {
        /* GIVEN */
        final Flight[] flights = {Flight.builder().hex("1").lat("1").lon("0").build()};
        /* WHEN */
        when(restTemplate.getForEntity(eq(DUMP1090_URL), eq(Flight[].class))).thenReturn(
                new ResponseEntity(flights, HttpStatus.OK)
        );
        service.pollDump1090ServerWithRate();
        /* THEN */
        verifyZeroInteractions(flightsService);
    }

    @Test
    public void testNotANumberLon() {
        /* GIVEN */
        final Flight[] flights = {Flight.builder().hex("1").lon("abc").lat("1").build()};
        /* WHEN */
        when(restTemplate.getForEntity(eq(DUMP1090_URL), eq(Flight[].class))).thenReturn(
                new ResponseEntity(flights, HttpStatus.OK)
        );
        service.pollDump1090ServerWithRate();
        /* THEN */
        verifyZeroInteractions(flightsService);
    }
}