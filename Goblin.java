import java.util.HashMap;

public class Goblin extends Zorde{

    public static HashMap<Integer, Goblin> goblinHashMap = new HashMap<>();

    private static String letter = "G";
    private static int totalNumber = 0;

    public Goblin(String x, String y) {
        super(Constants.goblinHP, Constants.goblinAP, Constants.goblinMaxMove, Goblin.totalNumber, x, y, Goblin.letter);
        Goblin.goblinHashMap.put(Goblin.totalNumber, this);
        Goblin.totalNumber++;
        initCharacter();
    }

    @Override
    public void initCharacter() {
        Board.boardL.get(getxC()).set(this.getyC(), this);
    }
}

