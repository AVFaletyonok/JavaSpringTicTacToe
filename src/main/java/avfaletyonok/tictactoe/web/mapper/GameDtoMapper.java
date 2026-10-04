package avfaletyonok.tictactoe.web.mapper;

import avfaletyonok.tictactoe.domain.model.Board;
import avfaletyonok.tictactoe.domain.model.Game;
import avfaletyonok.tictactoe.domain.model.Marks;
import avfaletyonok.tictactoe.web.model.GameDto;
import org.springframework.http.HttpStatus;

public final class GameDtoMapper {

    private GameDtoMapper() {}

    public static GameDto toGameDto(Game game, HttpStatus responseStatus) {
        GameDto gameDto = toGameDto(game);
        gameDto.setResponseStatus(responseStatus);
        return gameDto;
    }

    public static GameDto toGameDto(Game game) {

        Board board = game.getBoard();

        char[][] field = new char[board.getSize()][board.getSize()];
        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                int spot = board.getField()[i][j];
                if (spot == Marks.X.getValue()) {
                    field[i][j] = Marks.X.toString().charAt(0);
                } else if (spot == Marks.O.getValue()) {
                    field[i][j] = Marks.O.toString().charAt(0);
                } else {
                    field[i][j] = ' ';
                }
            }
        }

        GameDto gameDto = GameDto.builder()
                .userUuid(game.getUserUuid())
                .gameStatus(game.getStatus().toString())
                .winner(game.getWinner())
                .size(board.getSize())
                .field(field)
                .responseStatus(HttpStatus.OK)
                .build();

        return gameDto;
    }
}
