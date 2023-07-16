import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Character {

    public static HashMap<Integer, Character> characterHashMap = new HashMap<>();
    public static List<Character> characterList = new ArrayList<>();

    private int HP = 0;
    private int maxHP = 0;
    private int AP = 0;
    private int maxMove = 0;
    private int number = 0;
    private int xC = 0;
    private int yC = 0;
    private String letter = "  ";


    public Character(String a) {
        this.setHP(0);
        this.setAP(0);
        this.setMaxMove(0);
        this.setNumber(0);
        this.setxC(0);
        this.setLetter("  ");
    }

    public Character(int HP, int AP, int maxMove, int number, String xC, String yC, String letter) {
        this.setMaxHP(HP);
        this.setHP(HP);
        this.setAP(AP);
        this.setMaxMove(maxMove);
        this.setNumber(number);
        this.setxC(Integer.parseInt(yC));
        this.setyC(Integer.parseInt(xC));
        this.setLetter(letter + number);
        Character.characterHashMap.put(number, this);
        Character.characterList.add(this);
    }

    public void initCharacter() {
        Board.boardL.get(getxC()).set(this.getyC(), this);
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP += HP;
        if (this.HP > this.getMaxHP()) {
            this.HP = this.getMaxHP();
        }
    }

    public int getAP() {
        return AP;
    }

    public void setAP(int AP) {
        this.AP = AP;
    }

    public int getMaxMove() {
        return maxMove;
    }

    public void setMaxMove(int maxMove) {
        this.maxMove = maxMove;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getxC() {
        return xC;
    }

    public void setxC(int xC) {
        this.xC = xC;
    }

    public int getyC() {
        return yC;
    }

    public void setyC(int yC) {
        this.yC = yC;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public void setMaxHP(int maxHP) {
        this.maxHP = maxHP;
    }

}

