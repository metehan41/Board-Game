import java.util.ArrayList;
import java.util.List;

public class Zorde extends Character{

    public static List<Zorde> zordeList = new ArrayList<>();

    public Zorde(int HP, int AP, int maxMove, int number, String xC, String yC, String letter) {
        super(HP, AP, maxMove, number, xC, yC, letter);
        zordeList.add(this);
    }

    @Override
    public void initCharacter() {
        Board.boardL.get(getxC()).set(this.getyC(), this);
    }

}
