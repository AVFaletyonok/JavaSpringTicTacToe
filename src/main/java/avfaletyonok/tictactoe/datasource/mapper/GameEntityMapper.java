package avfaletyonok.tictactoe.datasource.mapper;

import avfaletyonok.tictactoe.datasource.model.GameEntity;
import avfaletyonok.tictactoe.datasource.model.SquareBoardEntity;
import avfaletyonok.tictactoe.domain.model.Board;
import avfaletyonok.tictactoe.domain.model.Game;
import avfaletyonok.tictactoe.domain.model.Marks;
import avfaletyonok.tictactoe.domain.model.SquareBoard;

public final class GameEntityMapper {

    private GameEntityMapper() {}

    public static GameEntity toGameEntity(Game game) {
        // TODO: Add check if game.getBoard() instanceof SquareBoard

        SquareBoard board = (SquareBoard) game.getBoard();
        SquareBoardEntity boardEntity = SquareBoardEntity.builder()
                .id(board.getId())
                .size(board.getSize())
                .field(board.getField())
                .build();

        GameEntity gameEntity = GameEntity.builder()
                .id(game.getId())
                .uuid(game.getUuid())
                .userUuid(game.getUserUuid())
                .userMark(game.getUserMark().getValue())
                .computerMark(game.getComputerMark().getValue())
                .status(game.getStatus())
                .winner(game.getWinner().getValue())
                .board(boardEntity)
                .depth(game.getDepth())
                .build();

        return gameEntity;
    }

    public static Game toGameModel(GameEntity gameEntity) {

        SquareBoardEntity boardEntity = gameEntity.getBoard();
        Board board = SquareBoard.builder()
                .id(boardEntity.getId())
                .size(boardEntity.getSize())
                .field(boardEntity.getField())
                .build();
        Game game = Game.builder()
                .id(gameEntity.getId())
                .uuid(gameEntity.getUuid())
                .userUuid(gameEntity.getUserUuid())
                .userMark(Marks.fromValueOptional(gameEntity.getUserMark()).get())
                .computerMark(Marks.fromValueOptional(gameEntity.getComputerMark()).get())
                .status(gameEntity.getStatus())
                .winner(Marks.fromValueOptional(gameEntity.getWinner()).get())
                .board(board)
                .depth(gameEntity.getDepth())
                .build();

        return game;
    }
}
