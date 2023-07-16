import java.util.HashMap;

public class Troll extends Zorde{

    public static HashMap<Integer, Troll> trollHashMap = new HashMap<>();

    private static String letter = "T";
    private static int totalNumber = 0;

    public Troll(String x, String y) {
        super(Constants.trollHP,  Constants.trollAP, Constants.trollMaxMove, Troll.totalNumber, x, y, Troll.letter);
        Troll.trollHashMap.put(Troll.totalNumber, this);
        Troll.totalNumber++;
        initCharacter();
    }

    @Override
    public void initCharacter() {
        Board.boardL.get(getxC()).set(this.getyC(), this);
    }

}
