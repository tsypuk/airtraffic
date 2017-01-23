package ua.in.smartjava.controllers;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import ua.in.smartjava.domain.board.Board;
import ua.in.smartjava.domain.flight.Flight;
import ua.in.smartjava.domain.flight.FlightRepository;

import static org.hamcrest.Matchers.iterableWithSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class FlightsControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private FlightRepository flightRepository;

    @Before
    public void setup() {
        mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @After
    public void cleanup() {
        flightRepository.deleteAll();
    }

    @Test
    public void getFlights() throws Exception {

    }

    @Test
    public void getFlightById() throws Exception {

    }

    @Test
    public void getFlightByFlightIdAndHex() throws Exception {
        Flight savedFlight = flightRepository.save(new Flight(
                "HEX", "FLIGHT", "10", "20", "30", "40", "50"
        ));
        Long id = savedFlight.getId();

        mockMvc.perform(get("/flights/1"))
                .andExpect(status().isOk());
    }

    @Test
    @Ignore
    public void testGetFlightByFlightIdAndHexNotFound() throws Exception {
        mockMvc.perform(get("/flights/1"))
                .andExpect(status().isNotFound());
    }

}