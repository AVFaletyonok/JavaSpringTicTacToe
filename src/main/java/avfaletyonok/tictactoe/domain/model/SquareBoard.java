package avfaletyonok.tictactoe.domain.model;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
public final class SquareBoard implements Board {

    private Long id;
    private int size;
    private int[][] field; // 1 - x
//    private Set<int[]> availableMoves;

    public SquareBoard() {
        this(3);
    }

    public SquareBoard(int n) {
        this.size = n;
        this.field = new int[n][n];

//        availableMoves = new HashSet<>(n * n);
//        for (int i = 0; i < n; i++) {
//            for (int j = 0; j < n; j++) {
//                availableMoves.add(new int[] {i, j});
//            }
//        }
    }

//    public int[] getEmptySpots() {
//
//    }

//    public boolean hasEmptySpot() {
//        return !availableMoves.isEmpty();
//    }
}
