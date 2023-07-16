import java.util.ArrayList;
import java.util.List;

public class Calliance extends Character {

    public static List<Calliance> callianceList = new ArrayList<>();

    public Calliance(int HP, int AP, int maxMove, int number, String xC, String yC, String letter) {
        super(HP, AP, maxMove, number, xC, yC, letter);
        callianceList.add(this);
    }

    @Override
    public void initCharacter() {
        Board.boardL.get(getxC()).set(this.getyC(), this);
    }
}