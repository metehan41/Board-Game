import java.util.HashMap;

public class Elf extends Calliance{

    public static HashMap<Integer, Elf> elfHashMap = new HashMap<>();

    private static String letter = "E";
    private static int totalNumber = 0;

    public Elf(String x, String y) {
        super(Constants.elfHP, Constants.elfAP, Constants.elfMaxMove, Elf.totalNumber, x, y, Elf.letter);
        Elf.elfHashMap.put(Elf.totalNumber, this);
        initCharacter();
        Elf.totalNumber++;

    }

    @Override
    public void initCharacter() {
        Board.boardL.get(getxC()).set(this.getyC(), this);
    }

}
