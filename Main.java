import java.io.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;


public class Main {


    public static void main(String[] args) throws FileNotFoundException {
        File file = new File(args[2]);
        PrintStream stream = new PrintStream(file);
        System.setOut(stream);
        initializeBoard(args[0]);
        printWriter();
        command(args[1]);
        isGameFinish();
    }

    public static void command(String commandPath) {
        try {
            Scanner scanCommand = new Scanner(new File(commandPath));
            while (scanCommand.hasNextLine()) {
                String[] command = scanCommand.next().trim().split("");
                String whichCharacterName = command[0];
                String[] commands = scanCommand.next().trim().split(";");
                int move = commands.length / 2;
                int whichCharacterNumber = Integer.parseInt(command[1]);
                if (isGameFinish()) {
                    switch (whichCharacterName) {

                        case "D":
                            Character dwarf = Dwarf.dwarfHashMap.get(whichCharacterNumber);
                            isLegal(dwarf, move, commands);
                            break;

                        case "O":
                            Character ork = Ork.orkHashMap.get(whichCharacterNumber);
                            hpIncrease(ork, ork.getxC(), ork.getyC());
                            isLegal(ork, move, commands);
                            break;

                        case "E":
                            Character elf = Elf.elfHashMap.get(whichCharacterNumber);
                            isLegal(elf, move, commands);
                            break;

                        case "T":
                            Character troll = Troll.trollHashMap.get(whichCharacterNumber);
                            isLegal(troll, move, commands);
                            break;

                        case "H":
                            Character human = Human.humanHashMap.get(whichCharacterNumber);
                            isLegal(human, move, commands);
                            break;

                        case "G":
                            Character goblin = Goblin.goblinHashMap.get(whichCharacterNumber);
                            isLegal(goblin, move, commands);
                            break;

                        default:
                            break;
                    }
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void initializeBoard(String initials) {
        try {
            Scanner scanBoard = new Scanner(new File(initials));
            scanBoard.nextLine();
            int boardSize = Integer.parseInt(scanBoard.nextLine().trim().split("x")[0]);
            Board board = new Board(boardSize);
            scanBoard.nextLine();
            while (scanBoard.hasNextLine()) {

                String[] whichCharacter = scanBoard.nextLine().trim().split(" ");
                switch (whichCharacter[0]) {

                    case "ELF":
                        new Elf(whichCharacter[2], whichCharacter[3]);
                        break;

                    case "DWARF":
                        new Dwarf(whichCharacter[2], whichCharacter[3]);
                        break;

                    case "HUMAN":
                        new Human(whichCharacter[2], whichCharacter[3]);
                        break;

                    case "GOBLIN":
                        new Goblin(whichCharacter[2], whichCharacter[3]);
                        break;

                    case "TROLL":
                        new Troll(whichCharacter[2], whichCharacter[3]);
                        break;

                    case "ORK":
                        new Ork(whichCharacter[2], whichCharacter[3]);
                        break;
                }

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void printWriter() {
        String str = String.join("", Collections.nCopies(Board.boardL.size() * 2 + 2, "*"));
        System.out.print(str + "\n");

        for (List<Character> characterList : Board.boardL) {
            System.out.print("*");
            for (Character character : characterList) {
                System.out.print(character.getLetter());
            }
            System.out.print("*\n");
        }
        System.out.print(str + "\n\n");
        Collections.sort(Character.characterList, new sortById());

        for (Character character : Character.characterList) {
            if (!character.getLetter().equals("  ")) {
                System.out.println(character.getLetter() + "\t" + character.getHP() + "\t" + "(" + character.getMaxHP() + ")");
            }
        }
        System.out.println();
    }

    public static void move(Character character, String[] commands) {
        try {
            for (int i = 0; i < character.getMaxMove() * 2; i += 2) {
                int oldX = character.getxC();
                int oldY = character.getyC();
                int newY = oldY + Integer.parseInt(commands[i]);
                int newX = oldX + Integer.parseInt(commands[i + 1]);

                if (newX < Board.boardL.size() && newY < Board.boardL.size() && newX >= 0 && newY >= 0) {

                    if (Board.boardL.get(newX).get(newY).getLetter().equals("  ")) {
                        moveTwo(character, newX, newY, oldX, oldY);

                        if (character instanceof Elf && i == (character.getMaxMove() * 2 - 2)) {
                            rangedAttack(character, newX, newY);
                            removeDeath();
                        } else if (character instanceof Human) {
                            if (i == (character.getMaxMove() * 2 - 2)) {
                                classicAttack(character, newX, newY);
                                removeDeath();
                            }
                        } else {
                            classicAttack(character, newX, newY);
                            removeDeath();
                        }
                    } else if (!Board.boardL.get(newX).get(newY).getLetter().equals("  ") && !isFriends(Board.boardL.get(newX).get(newY), character)) {
                        fightToDeath(character, newX, newY, oldX, oldY);
                        break;
                    }
                } else if (newX >= Board.boardL.size() || newY >= Board.boardL.size() || newX < 0 || newY < 0) {
                    if (i == 0) {
                        System.out.println("Error : Game board boundaries are exceeded. Input line ignored.\n");
                        return;
                    } else {
                        printWriter();
                        System.out.println("Error : Game board boundaries are exceeded. Input line ignored.\n");
                        return;
                    }
                }
            }
            printWriter();
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    public static void moveTwo(Character character, int newX, int newY, int oldX, int oldY) {
        Board.boardL.get(newX).set(newY, character);
        Board.boardL.get(oldX).set(oldY, new Character("  "));
        character.setxC(newX);
        character.setyC(newY);
    }

    public static void isLegal(Character character, int move, String[] commands) {
        if (move == character.getMaxMove()) {
            move(character, commands);
        } else {
            System.out.println("Error : Move sequence contains wrong number of move steps. Input line ignored.\n");
        }
    }

    public static boolean isGameFinish() {
        if (Character.characterList.size() == 1 && Character.characterList.get(0) instanceof Calliance) {
            System.out.println("Game Finished\nCalliance wins");
            System.exit(0);
        } else if (Character.characterList.size() == 1 && Character.characterList.get(0) instanceof Zorde) {
            System.out.println("Game Finished\nZorde wins");
            System.exit(0);
        }
        return true;
    }

    public static void classicAttack(Character character, int x, int y) {
        for (int i = (Math.max((x - 1), 0)); i < ((x + 1) < Board.boardL.size() ? x + 2 : Board.boardL.size()); i++) {
            for (int j = (Math.max((y - 1), 0)); j < ((y + 1) < Board.boardL.size() ? y + 2 : Board.boardL.size()); j++) {
                if (!Board.boardL.get(i).get(j).getLetter().equals("  ") && !Board.boardL.get(i).get(j).getLetter().equals(character.getLetter())) {
                    if (!isFriends(Board.boardL.get(i).get(j), character)) {
                        Board.boardL.get(i).get(j).setHP(-character.getAP());
                    }
                }
            }
        }
    }

    public static void rangedAttack(Character character, int x, int y) {
        for (int i = (Math.max((x - 2), 0)); i < ((x + 2) < Board.boardL.size() ? x + 3 : Board.boardL.size()); i++) {
            for (int j = (Math.max((y - 2), 0)); j < ((y + 2) < Board.boardL.size() ? y + 3 : Board.boardL.size()); j++) {
                if (!Board.boardL.get(i).get(j).getLetter().equals("  ") && !Board.boardL.get(i).get(j).getLetter().equals(character.getLetter())) {
                    if (!isFriends(Board.boardL.get(i).get(j), character)) {
                        Board.boardL.get(i).get(j).setHP(-Constants.elfRangedAP);
                    }
                }
            }
        }
    }

    public static void fightToDeath(Character character, int x, int y, int oldX, int oldY) {
        Board.boardL.get(x).get(y).setHP(-character.getAP());
        if (character.getHP() > Board.boardL.get(x).get(y).getHP() && Board.boardL.get(x).get(y).getHP() > 0) {
            character.setHP(-Board.boardL.get(x).get(y).getHP());
            Board.boardL.get(x).get(y).setHP(-Board.boardL.get(x).get(y).getHP());
            removeDeath();
            moveTwo(character, x, y, oldX, oldY);
        } else if (character.getHP() > Board.boardL.get(x).get(y).getHP() && Board.boardL.get(x).get(y).getHP() <= 0 && character.getHP() > 0) {
            removeDeath();
            moveTwo(character, x, y, oldX, oldY);
        } else if (character.getHP() < Board.boardL.get(x).get(y).getHP() && character.getHP() > 0) {
            Board.boardL.get(x).get(y).setHP(-character.getHP());
            character.setHP(-character.getHP());
            removeDeath();
        } else if (character.getHP() < Board.boardL.get(x).get(y).getHP() && character.getHP() < 0 && Board.boardL.get(x).get(y).getHP() > 0) {
            removeDeath();
        } else {
            removeDeath();
        }
    }

    public static void hpIncrease(Character character, int x, int y) {
        for (int i = (x > 0 ? x - 1 : 0); i < (Math.min((x + 2), Board.boardL.size())); i++) {
            for (int j = (y > 0 ? y - 1 : 0); j < (Math.min((y + 2), Board.boardL.size())); j++) {
                if (!Board.boardL.get(i).get(j).getLetter().equals("  ")) {
                    if (isFriends(Board.boardL.get(i).get(j), character)) {
                        Board.boardL.get(i).get(j).setHP(10);
                    }
                }
            }
        }
    }

    public static boolean isFriends(Character character1, Character character2) {
        if (character1 instanceof Calliance && character2 instanceof Calliance) {
            return true;
        } else if (character1 instanceof Calliance && character2 instanceof Zorde) {
            return false;
        } else if (character1 instanceof Zorde && character2 instanceof Calliance) {
            return false;
        } else if (character1 instanceof Zorde && character2 instanceof Zorde) {
            return true;
        } else {
            return true;
        }
    }

    public static void removeDeath() {
        for (int i = 0; i < Board.boardL.size(); i++) {
            for (int j = 0; j < Board.boardL.size(); j++) {
                if (Board.boardL.get(i).get(j).getHP() <= 0 && !Board.boardL.get(i).get(j).getLetter().equals("  ")) {
                    Character.characterHashMap.remove(Board.boardL.get(i).get(j));
                    Character.characterList.remove(Board.boardL.get(i).get(j));
                    Board.boardL.get(i).set(j, new Character("  "));
                }
            }
        }
    }
}

class sortById implements Comparable<Character>, Comparator<Character> {

    @Override
    public int compareTo(Character o) {
        return 0;
    }

    @Override
    public int compare(Character character1, Character character2) {
        return character1.getLetter().compareTo(character2.getLetter());
    }
}
