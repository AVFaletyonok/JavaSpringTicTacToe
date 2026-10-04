package avfaletyonok.tictactoe.datasource.repository;

import avfaletyonok.tictactoe.datasource.model.SquareBoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SquareBoardRepository
        extends JpaRepository<SquareBoardEntity, Long> {
}
