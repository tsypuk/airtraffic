package ua.in.smartjava;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

import ua.in.smartjava.domain.board.Board;
import ua.in.smartjava.domain.board.BoardsService;
import ua.in.smartjava.domain.flight.FlightsService;
import ua.in.smartjava.utils.PredicateUtils;

/**
 * Service reads data from Flights table and adds unique found Boards to persistent store.
 */
@Service
public class BoardDetailsSheduledService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BoardDetailsSheduledService.class);

    private FlightsService flightsService;
    private BoardsService boardsService;

    @Autowired
    public BoardDetailsSheduledService(FlightsService flightsService, BoardsService boardsService) {
        this.flightsService = flightsService;
        this.boardsService = boardsService;
    }

    @Scheduled(fixedRateString = "${boards.update.rate}")
    public void fillBoards() {
        LOGGER.debug("Checking for new boards.");
        // read unique "hex", "flight" for last hour.
        Set<Board> lastBoards = flightsService.checkForNewUniqueFlights()
                .stream()
                .filter(PredicateUtils.correctlyFilledFlightPredicate)
                .map(Board::new)
                .collect(Collectors.toSet());

        lastBoards.removeAll(loadKnownBoards());

        lastBoards.forEach(this::addBoardIfNotPresent);
    }

    private Set<Board> loadKnownBoards() {
        return boardsService.getAllBoards()
                .stream()
                .collect(Collectors.toSet());
    }

    private void addBoardIfNotPresent(Board board) {
        if (!boardsService.getBoardByHexAndFlight(board.getHex(), board.getFlight()).isPresent()) {
            boardsService.save(board);
            LOGGER.debug("Added unique board {}", board);
        }
    }

}