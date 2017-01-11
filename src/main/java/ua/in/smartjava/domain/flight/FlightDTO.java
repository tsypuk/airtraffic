package ua.in.smartjava.domain.flight;

import org.springframework.util.StringUtils;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode(of = {"hex", "lat", "lon", "altitude", "track", "speed"})
@ToString(of = {"hex", "flight", "speed", "lat", "lon", "altitude"})
public class FlightDTO {

    @Getter
    @Setter
    String hex;

    @Getter
    @Setter
    String flight;

    @Getter
    @Setter
    String lat;

    @Getter
    @Setter
    String lon;

    @Getter
    @Setter
    String altitude;

    @Getter
    @Setter
    String track;

    @Getter
    @Setter
    String speed;

    public FlightDTO(Flight flight) {
        this.hex = flight.getHex();
        this.flight = StringUtils.trimAllWhitespace(flight.getFlight());
        this.lat = flight.getLat();
        this.lon = flight.getLon();
        this.altitude = flight.getAltitude();
        this.track = flight.getTrack();
        this.speed = flight.getSpeed();
    }
}