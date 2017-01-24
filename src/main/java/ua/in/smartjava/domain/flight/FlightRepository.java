package ua.in.smartjava.domain.flight;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Long> {
    List<Flight> findFirst100ByOrderByIdDesc();
    List<Flight> findByFlight(String flight);
    List<Flight> findByFlightAndHex(String flight, String hex);
    Flight findFlightById(String id);

}