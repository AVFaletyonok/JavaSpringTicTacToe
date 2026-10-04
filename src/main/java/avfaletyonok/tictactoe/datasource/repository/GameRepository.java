package avfaletyonok.tictactoe.datasource.repository;

import avfaletyonok.tictactoe.datasource.model.GameEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface GameRepository extends JpaRepository<GameEntity, Long> {

    Boolean existsByUuid(UUID uuid);
    Boolean existsByUserUuid(UUID userUuid);

    Optional<GameEntity> findByUuid(UUID uuid);
    Optional<GameEntity> findByUserUuid(UUID userUuid);
}
