package avfaletyonok.tictactoe.web.controller;

import avfaletyonok.tictactoe.domain.service.GameService;
import avfaletyonok.tictactoe.web.model.GameDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/game")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    @GetMapping({"/", ""})
    public ResponseEntity<GameDto> getGame(@CookieValue(name = "user_id", required = false) Optional<String> userId) {

        if (userId.isEmpty()) {
            return createGame();
        }
        UUID userUuid = UUID.fromString(userId.get());
        Optional<GameDto> gameDto = gameService.getGame(userUuid);

        if (gameDto.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        if (gameDto.get().getResponseStatus() != HttpStatus.OK) {
            return ResponseEntity.status(gameDto.get().getResponseStatus()).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(gameDto.get());
    }

    /**
     * This method receives the current game with an updated board from the user
     * and returns the current game with the updated board for the computer's turn.
     * If an invalid current game or updated board is sent,
     * the method should return an error with a description
     *
     * каждый UUID должен строго соответствовать одной уникальной игровой сессии
     * @param userId
     * @param iRow
     * @param iColumn
     * @return
     */
    @PostMapping("/move{iRow},{iColumn}")
    public ResponseEntity<GameDto> postMove(@CookieValue(name = "user_id", required = false) Optional<String> userId,
                                            @PathVariable int iRow,
                                            @PathVariable int iColumn){

        if (userId.isEmpty()) {
            return createGame();
        }
        UUID userUuid = UUID.fromString(userId.get());

        // - userUuid is not found - .NOT_FOUND
        // - the game is finished - .FORBIDDEN
        // - such turn is unavailable - .BAD_REQUEST
        Optional<GameDto> gameDto = gameService.postMove(userUuid, iRow, iColumn);

        if (gameDto.isEmpty()) { // game is not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        if (gameDto.get().getResponseStatus() != HttpStatus.OK) {
            return ResponseEntity.status(gameDto.get().getResponseStatus()).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(gameDto.get());
    }

    @GetMapping("/new")
    public ResponseEntity<GameDto> createGame() {

        Optional<GameDto> gameDto = gameService.createGame();

        if (gameDto.isEmpty()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        if (gameDto.get().getResponseStatus() != HttpStatus.OK) {
            return ResponseEntity.status(gameDto.get().getResponseStatus()).build();
        }
        ResponseCookie cookie = ResponseCookie.from("user_id", gameDto.get().getUserUuid().toString())
                .httpOnly(true)
                .path("/")
                .maxAge(Duration.ofDays(30))
                .build();

        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(gameDto.get());
    }
}
