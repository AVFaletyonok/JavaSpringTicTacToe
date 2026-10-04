package avfaletyonok.tictactoe.domain.service;

import avfaletyonok.tictactoe.datasource.mapper.GameEntityMapper;
import avfaletyonok.tictactoe.datasource.model.GameEntity;
import avfaletyonok.tictactoe.datasource.repository.GameRepository;
import avfaletyonok.tictactoe.domain.model.Game;
import avfaletyonok.tictactoe.domain.model.GameInterface;
import avfaletyonok.tictactoe.domain.model.GameStatus;
import avfaletyonok.tictactoe.web.mapper.GameDtoMapper;
import avfaletyonok.tictactoe.web.model.GameDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GameService {

    private final GameRepository gameRepository;

    public Optional<GameDto> getGame(final UUID userUuid) {
        Optional<GameEntity> gameEntity = gameRepository.findByUserUuid(userUuid);
        if (gameEntity.isEmpty()) {
            return Optional.empty();
        }
        GameInterface game = GameEntityMapper.toGameModel(gameEntity.get());
        GameDto gameDto = GameDtoMapper.toGameDto((Game)game);

        return Optional.of(gameDto);
    }

    /**
     * This method receives the current game with an updated board from the user
     * and returns the current game with the updated board for the computer's turn.
     * If an invalid current game or updated board is sent,
     * the method should return an error with a description
     * @param userUuid
     * @param iRow
     * @param iColumn
     * @return
     */
    @Transactional
    public Optional<GameDto> postMove(final UUID userUuid, int iRow, int iColumn) {
        boolean existsGame = gameRepository.existsByUserUuid(userUuid);
        if (!existsGame) {
            return Optional.empty();
        }
        Optional<GameEntity> gameDB = gameRepository.findByUserUuid(userUuid);
        if (gameDB.isEmpty()) {
            return Optional.empty();
        }
        GameInterface game = GameEntityMapper.toGameModel(gameDB.get());
        GameDto gameDto;

        if (!game.isAvailableStep(iRow, iColumn)) {
            gameDto = GameDtoMapper.toGameDto((Game)game);
            gameDto.setResponseStatus(HttpStatus.BAD_REQUEST);
            if (game.getStatus() != GameStatus.ACTIVE) {
                gameDto.setResponseStatus(HttpStatus.FORBIDDEN);
            }
            return Optional.of(gameDto);
        }
        game.makeMove(iRow, iColumn, game.getUserMark());

        GameEntity gameEntity = GameEntityMapper.toGameEntity((Game)game);
        gameRepository.save(gameEntity);
        gameDto = GameDtoMapper.toGameDto((Game)game);

        return Optional.of(gameDto);
    }

    @Transactional
    public Optional<GameDto> createGame() {
        UUID uuid = UUID.randomUUID();
        boolean existsGame = gameRepository.existsByUuid(uuid);
        while(existsGame) {
            uuid = UUID.randomUUID();
            existsGame = gameRepository.existsByUuid(uuid);
        }
        UUID userUuid = UUID.randomUUID();
        existsGame = gameRepository.existsByUserUuid(userUuid);
        while(existsGame) {
            userUuid = UUID.randomUUID();
            existsGame = gameRepository.existsByUserUuid(userUuid);
        }

        GameInterface game = new Game(3, uuid, userUuid);
        GameEntity gameEntity = GameEntityMapper.toGameEntity((Game)game);
        gameRepository.save(gameEntity);
        GameDto gameDto = GameDtoMapper.toGameDto((Game)game);

        return Optional.of(gameDto);
    }
}
