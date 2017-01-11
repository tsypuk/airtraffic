package ua.in.smartjava.domain.board;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {
    Optional<Board> getBoardByHexAndFlight(String hex, String flight);
}