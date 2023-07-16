import java.util.HashMap;

public class Dwarf extends Calliance{

    public static HashMap<Integer, Dwarf> dwarfHashMap = new HashMap<>();

    private static final String letter = "D";
    private static int totalNumber = 0;

    public Dwarf(String x, String y) {
        super(Constants.dwarfHP, Constants.dwarfAP, Constants.dwarfMaxMove, Dwarf.totalNumber, x, y, Dwarf.letter);
        Dwarf.dwarfHashMap.put(Dwarf.totalNumber, this);
        Dwarf.totalNumber++;
        initCharacter();

    }

    @Override
    public void initCharacter() {
        Board.boardL.get(getxC()).set(this.getyC(), this);
    }
}
