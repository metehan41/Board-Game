import java.util.*;

public class Board {

    public static List<List<Character>> boardL = new ArrayList<>();

    public Board(int a) {
        for (int i = 0; i < a; i++) {
            List<Character> row = new ArrayList<>();
            for (int j = 0; j < a; j++) {
                row.add(new Character("  "));
            }
            Board.boardL.add(row);
        }
    }
}