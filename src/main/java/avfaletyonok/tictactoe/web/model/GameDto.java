package avfaletyonok.tictactoe.web.model;

import avfaletyonok.tictactoe.domain.model.Marks;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.UUID;

@Getter
@Setter
@Builder
public class GameDto {

    private UUID userUuid;
    private String gameStatus;
    private Marks winner;
    private int size;
    private char[][] field;
    private HttpStatus responseStatus;
}
