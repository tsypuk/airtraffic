package ua.in.smartjava.domain.board;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

public class BoardDTO {
    @Getter
    @Setter
    @NotNull
    String hex;

    @Getter @Setter
    @NotNull
    String flight;

    public BoardDTO(Board board) {
        this.hex = board.getHex();
        this.flight = board.getFlight();
    }
}