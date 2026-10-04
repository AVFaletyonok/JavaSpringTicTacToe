package avfaletyonok.tictactoe.domain.model;

public interface GameInterface {
    public boolean isAvailableStep(int iRow, int iColumn);
    public void makeMove(int iRow, int iColumn, Marks mark);
    public GameStatus getStatus();
    public Marks getUserMark();
}
