package avfaletyonok.tictactoe.datasource.model;

import avfaletyonok.tictactoe.domain.model.GameStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "games", indexes = {
        @Index(name = "idx_game_uuid", columnList = "game_uuid", unique = true),
        @Index(name = "idx_user_uuid", columnList = "user_uuid", unique = true)
})
@Builder
public class GameEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, updatable = false)
    private Long id;

    @Column(name = "game_uuid", columnDefinition = "uuid", nullable = false, updatable = false)
    private UUID uuid; // random - version 4

    @Column(name = "user_uuid", columnDefinition = "uuid", nullable = false, updatable = false)
    private UUID userUuid; // random - version 4

    @Column(name = "user_mark",columnDefinition = "smallint")
    private int userMark;
    @Column(name = "computer_mark", columnDefinition = "smallint")
    private int computerMark;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private GameStatus status;

    @Column(columnDefinition = "smallint")
    private int winner;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "board_id", referencedColumnName = "id")
    private SquareBoardEntity board;

    private int depth;
}
