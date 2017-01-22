package ua.in.smartjava.domain.board;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ua.in.smartjava.domain.flight.Flight;

@Entity
@Table(name = "boards")
@EqualsAndHashCode(of = {"hex", "flight"})
@ToString(of = {"hex", "flight"})
public class Board {

    @Getter @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Getter @Setter
    @NotNull
    String hex;

    @Getter @Setter
    @NotNull
    String flight;

    public Board(Flight flight) {
        this.hex = flight.getHex();
        this.flight = flight.getFlight();
    }

    public Board() {
    }

    public Board(String hex, String flight) {
        this.hex = hex;
        this.flight = flight;
    }
}