package avfaletyonok.tictactoe.domain.model;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Game implements GameInterface {

    private Long id;
    private final UUID uuid; // random - version 4
    private final UUID userUuid; // random - version 4
    private final Marks userMark; // X
    private final Marks computerMark; // 0
    private GameStatus status;
    private Marks winner;
    private final Board board;
    private final int depth;

    public Game(int n, UUID uuid, UUID userUuid) {
        board = new SquareBoard(n);
        this.uuid = uuid;
        this.userUuid = userUuid;
        userMark = Marks.X;
        computerMark = Marks.O;
        status = GameStatus.ACTIVE;
        winner = Marks.NO;
        depth = 10;
    }

    public boolean isAvailableStep(int iRow, int iColumn) {
        return status == GameStatus.ACTIVE && board.getField()[iRow][iColumn] == 0;
    }

    public void makeMove(int iRow, int iColumn, Marks mark) {
        if (isAvailableStep(iRow, iColumn)) {
            board.getField()[iRow][iColumn] = mark.getValue();
            checkEnd();
            if (status == GameStatus.FINISHED) return;
            makeAIBestMove();
        }
    }

    private void makeAIBestMove() {
        int bestScore = Integer.MIN_VALUE;
        int[] bestMove = {-1, -1};
        // TODO: Optimization
        //  save all AI scores to matrix and refresh only few elements after each turn

        int size = board.getSize();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board.getField()[i][j] == 0) {
                    board.getField()[i][j] = computerMark.getValue();
                    int curScore = minimax(depth - 1, false);
                    board.getField()[i][j] = 0;
                    if (curScore > bestScore) {
                        bestScore = curScore;
                        bestMove[0] = i;
                        bestMove[1] = j;
                    }
                }
            }
        }
        board.getField()[bestMove[0]][bestMove[1]] = computerMark.getValue();
    }

    // TODO: For unlimited field use heap with priority to fold available moves: near existing AI/human marks with higher priority
    private int minimax(int depth, boolean isMaximizing) {

        checkEnd();
        int score = 0;
        if (depth == 0 || status == GameStatus.FINISHED) {
            int result = 0;
            if (winner != Marks.NO) {
                result = winner == userMark ? -1 * depth : depth;
                if (winner == Marks.DRAW) result = 0; // RAW
            }
            status = GameStatus.ACTIVE;
            winner = Marks.NO;
            return result;
        }

        int maxScore;
        int minScore;
        int size = board.getSize();
        if (isMaximizing) { // AI turn
            maxScore = Integer.MIN_VALUE;
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (board.getField()[i][j] == 0) {
                        board.getField()[i][j] = computerMark.getValue();
                        minScore = minimax(depth - 1, false);
                        maxScore = maxScore > minScore ? maxScore : minScore;
                        board.getField()[i][j] = 0;
                    }
                }
            }
            return maxScore;
        } else { // human turn
            minScore = Integer.MAX_VALUE;
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (board.getField()[i][j] == 0) {
                        board.getField()[i][j] = userMark.getValue();
                        maxScore = minimax(depth - 1, true);
                        minScore = minScore < maxScore ? minScore : maxScore;
                        board.getField()[i][j] = 0;
                    }
                }
            }
            return minScore;
        }
    }

    /** implemented only for 3x3 field
     *
     * @return X    - winner X (1)
     *         0    - winner O (7)
     */
    // TODO: for boundless field 5-in-line use 1 and 7
    private void checkEnd() {
        int size = board.getSize();
        int[][] field = board.getField();

        // checking rows
        for (int i = 0; i < size; i++) {
            if (field[i][0] != 0 &&
                    field[i][0] == field[i][1] &&
                    field[i][0] == field[i][2]) {
                status = GameStatus.FINISHED;
                winner = field[i][0] == Marks.X.getValue() ? Marks.X : Marks.O;
            }
        }
        // checking columns
        for (int j = 0; j < size; j++) {
            if (field[0][j] != 0 &&
                    field[0][j] == field[1][j] &&
                    field[0][j] == field[2][j]) {
                status = GameStatus.FINISHED;
                winner = field[0][j] == Marks.X.getValue() ? Marks.X : Marks.O;
            }
        }
        // checking diagonals
        if (field[0][0] != 0 &&
                field[0][0] == field[1][1] &&
                field[0][0] == field[2][2] ||
                field[0][2] != 0 &&
                        field[0][2] == field[1][1] &&
                        field[0][2] == field[2][0]) {
            status = GameStatus.FINISHED;
            winner = field[1][1] == Marks.X.getValue() ? Marks.X : Marks.O;
        }
    }
}
