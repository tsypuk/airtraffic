package ua.in.smartjava.domain.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class BoardsService {

    @Autowired
    private BoardRepository boardRepository;

    public Optional<Board> getBoardByHexAndFlight(String boardHex, String flight) {
        return boardRepository.getBoardByHexAndFlight(boardHex, flight);
    }

    public Collection<Board> getAllBoards() {
        return boardRepository.findAll();
    }

    public void save(Board board) {
        boardRepository.save(board);
    }
}