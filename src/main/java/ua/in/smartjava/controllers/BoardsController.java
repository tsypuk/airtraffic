package ua.in.smartjava.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.stream.Collectors;

import ua.in.smartjava.domain.board.Board;
import ua.in.smartjava.domain.board.BoardDTO;
import ua.in.smartjava.domain.board.BoardRepository;

@RestController
public class BoardsController {

    private BoardRepository boardRepository;

    @Autowired
    public BoardsController(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @GetMapping("/boards")
    public ResponseEntity<List<BoardDTO>> getBoards() {
        List<Board> boards = boardRepository.findAll();
        if (boards.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(boards.stream()
            .map(BoardDTO::new)
            .collect(Collectors.toList()), HttpStatus.OK);
        }
    }
}