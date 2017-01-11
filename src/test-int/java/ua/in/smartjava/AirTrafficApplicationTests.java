package ua.in.smartjava;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import ua.in.smartjava.domain.flight.FlightRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AirTrafficApplicationTests {

    @Autowired
    FlightRepository flightRepository;

    @Autowired
    RestTemplate restTemplate;

    @Test
    @Ignore
    public void contextLoads() {
    }

}