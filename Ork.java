import java.util.HashMap;

public class Ork extends Zorde {

    public static HashMap<Integer, Ork> orkHashMap = new HashMap<>();

    private static String letter = "O";
    private static int totalNumber = 0;

    public Ork(String x, String y) {
        super(Constants.orkHealPoints, Constants.orkAP, Constants.orkMaxMove, Ork.totalNumber, x, y, Ork.letter);
        Ork.orkHashMap.put(Ork.totalNumber, this);
        Ork.totalNumber++;
        initCharacter();
    }

    @Override
    public void initCharacter() {
        Board.boardL.get(getxC()).set(this.getyC(), this);
    }

}