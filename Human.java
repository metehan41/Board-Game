import java.util.HashMap;

public class Human extends Calliance {

    public static HashMap<Integer, Human> humanHashMap = new HashMap<>();

    private static String letter = "H";
    private static int totalNumber = 0;

    public Human(String x, String y) {
        super(Constants.humanHP, Constants.humanAP, Constants.humanMaxMove, Human.totalNumber, x, y, Human.letter);
        Human.humanHashMap.put(Human.totalNumber, this);
        Human.totalNumber++;
        initCharacter();
    }

    @Override
    public void initCharacter() {
        Board.boardL.get(getxC()).set(this.getyC(), this);
    }
}
