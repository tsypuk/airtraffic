package ua.in.smartjava.domain.flight;

import org.springframework.util.StringUtils;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "flights")
@EqualsAndHashCode(of = {"hex", "lat", "lon", "altitude", "track", "speed"})
@ToString(of = {"hex", "flight", "speed", "lat", "lon", "altitude"})
@Builder
@AllArgsConstructor
public class Flight {
    @Getter @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Getter @Setter @NotNull
    String hex;

    @Getter @Setter
    @NotNull
    String flight;

    @Getter @Setter
    @NotNull
    String lat;

    @Getter @Setter
    @NotNull
    String lon;

    @Getter @Setter
    String altitude;

    @Getter @Setter
    String track;

    @Getter @Setter
    String speed;

    public Flight() {
    }

    public Flight(String hex, String flight, String lat, String lon, String altitude, String track, String speed) {
        this.hex = hex;
        this.flight = flight;
        this.lat = lat;
        this.lon = lon;
        this.altitude = altitude;
        this.track = track;
        this.speed = speed;
    }

    public Flight(FlightDTO flightDTO) {
        this.hex = flightDTO.getHex();
        this.flight = StringUtils.trimAllWhitespace(flightDTO.getFlight());
        this.lat = flightDTO.getLat();
        this.lon = flightDTO.getLon();
        this.altitude = flightDTO.getAltitude();
        this.track = flightDTO.getTrack();
        this.speed = flightDTO.getSpeed();
    }
}