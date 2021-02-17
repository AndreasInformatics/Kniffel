import java.io.*;
import java.util.*;
import Prog1Tools.IOTools;

public class Main {

    public static void main(String[] args) throws IOException {

        String decisions;
        String[] finaldec;

        List<String> reroll = new ArrayList<>();

        int playeramount;
        int spielerindex = 0;
        int zugindex = 1;
        int round = 1;
        int[] dice = new int[5];

        playeramount = IOTools.readInt("Mitspieleranzahl eingeben: ");
        String[] player = new String[playeramount];

        for (int i = 0; i < playeramount; i++) {
            player[i] = IOTools.readString("Gib den " + (i + 1) + ". Spielername ein: ");
        }

        System.out.println("");
        System.out.println("////////////////START\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\");
        System.out.println("");

        for (int i = 0; i < (13 * (playeramount)); i++) {
            System.out.println("Runde: " + round + " Spieler: " + player[spielerindex]);

            for (int j = 0; j < 5; j++) {
                dice[j] = (int) ((Math.random() * ((6 - 1) + 1)) + 1);
            }

            System.out.println("    Zug: " + zugindex + " Würfelzahlen= " + dice[0] + "|" + dice[1] + "|" + dice[2] + "|" + dice[3] + "|" + dice[4]);

            for (int k = 0; k < 2; k++) {
                decisions = IOTools.readString("    Welche Würfel sollen nochmal gewürfelt werden? (In Form 2-3-5 eingeben) Ansonsten end eintippen, um Zug zu beenden: ");
                finaldec = decisions.split("-");

                if(!decisions.equals("end")){
                    Collections.addAll(reroll, finaldec);
                }

                if (decisions.equals("end")) {
                    if (spielerindex == (playeramount - 1)) {
                        spielerindex = 0;
                        round++;
                    } else {
                        spielerindex++;
                    }
                    break;
                } else {
                    for (int m = 0; m < dice.length; m++) {
                        for (int l = 0; l < reroll.size(); l++) {
                            if (dice[m] == Integer.parseInt(reroll.get(l))) {
                                dice[m] = (int) ((Math.random() * ((6 - 1) + 1)) + 1);
                                reroll.remove(0);
                            }
                        }
                    }
                    zugindex++;
                    System.out.println("    Zug: " + zugindex + " Würfelzahlen= " + dice[0] + "|" + dice[1] + "|" + dice[2] + "|" + dice[3] + "|" + dice[4]);
                }
            }
            zugindex = 1;
            if (spielerindex == (playeramount - 1)) {
                spielerindex = 0;
                round++;
            } else {
                spielerindex++;
            }
            checkdices(dice);
        }
    }

    public static void checkdices(int[] wurf){
        ArrayList<String> numbers = new ArrayList<>();
        Set<String> duplicatednumbers = new HashSet<>();

        for(int i = 0; i < wurf.length; i++){
            switch (wurf[i]) {
                case 1:
                    numbers.add("1");
                    duplicatednumbers.add("1");
                    break;
                case 2:
                    numbers.add("2");
                    duplicatednumbers.add("2");
                    break;
                case 3:
                    numbers.add("3");
                    duplicatednumbers.add("3");
                    break;
                case 4:
                    numbers.add("4");
                    duplicatednumbers.add("4");
                    break;
                case 5:
                    numbers.add("5");
                    duplicatednumbers.add("5");
                    break;
                case 6:
                    numbers.add("6");
                    duplicatednumbers.add("6");
                    break;
            }
        }

        ArrayList<String> options = new ArrayList<>(duplicatednumbers);

        Collections.sort(options);

        for(int x = 1; x < 7; x++){
            if (Collections.frequency(numbers, String.valueOf(x)) >= 3){
                options.add("3er");
            }
            if (Collections.frequency(numbers, String.valueOf(x)) >= 4){
                options.add("4er");
            }
            if (Collections.frequency(numbers, String.valueOf(x)) == 5){
                options.add("kn");
            }
        }

        for(int y = 1; y < 7; y++){
            for(int z = 1; z < 7; z++){
                if(Collections.frequency(numbers, String.valueOf(y)) == 3 && Collections.frequency(numbers, String.valueOf(z)) == 2 && y != z){
                    options.add("fh");
                }
            }
        }

        Collections.sort(numbers);
        Set<String> smallstreet = new HashSet<>(numbers);
        numbers.clear();
        numbers.addAll(smallstreet);
        String numberiterator = "";

        for (String s : numbers){
            numberiterator += s + "";
        }

        if(numberiterator.contains("1234")||numberiterator.contains("2345")||numberiterator.contains("3456")){
            options.add("ks");
        }
        if(numberiterator.contains("12345")||numberiterator.contains("23456")){
            options.add("gs");
        }

        options.add("ch");

        System.out.println(options);

    }
}