package avfaletyonok.tictactoe.domain.model;

import java.util.Optional;

public enum Marks {
    X (1), O (7), DRAW(0), NO (-1);

    private final int value;

    Marks(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }
    public static Optional<Marks> fromValueOptional(int value) {
        for (Marks mark : values()) {
            if (mark.value == value) {
                return Optional.of(mark);
            }
        }
        return Optional.empty();
    }
}
