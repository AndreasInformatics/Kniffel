import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import Prog1Tools.IOTools;

public class Player {

    public enum Choices {
        ONE, TWO, THREE, FOUR, FIVE, SIX, THREEPASCH, FOURPASCH, FULLHOUSE, SMALLSTREET, GRANDSTREET, PASCH, CHANCE
    }

    public String name;
    public String strichchoice;
    public String listchoice;
    public int player;
    public int listmatcher;
    public int strichmatcher;
    private int number;
    public int spielerindex = 0;
    private int zugindex = 1;
    public int round = 1;
    public int[] dice = new int[5];
    public int[][] wahlarray;
    public List<String> reroll = new ArrayList<>();
    public List<Integer> matcher;
    public List<String> enumvalues;
    public ArrayList<String> options;
    public ArrayList<String> numbers;
    public ArrayList<Choices> freeoptions;
    public ArrayList<Choices> strichoptions;
    public Set<String> duplicatednumbers;

    public void setName(String name){
        this.name = name;
    }

    public void setplayeramount(int player){
        this.player = player;
    }

    public void setarray(int player){ wahlarray = new int[player][13]; }

    public String getName(){
        return this.name;
    }

    public void rolldices(String[] player){
        System.out.println("Runde: " + round + " Spieler: " + player[spielerindex]);
        diceroll();
        System.out.println("    Zug: " + zugindex + " Würfelzahlen= " + dice[0] + "|" + dice[1] + "|" + dice[2] + "|" + dice[3] + "|" + dice[4]);
    }

    public void diceroll(){
        for (int j = 0; j < 5; j++) {
            dice[j] = (int) ((Math.random() * ((6 - 1) + 1)) + 1);
        }
    }

    public void choice(String decision, int playeramount){
        if(decision.equals("end") || zugindex == 3){
            endzug(playeramount);
        } else {
            Collections.addAll(reroll, decision.split("-"));
            newzug(playeramount);
        }
    }

    public void endzug(int playernumber){
        System.out.println();
        if (spielerindex == (playernumber - 1)) {
            spielerindex = 0;
            zugindex = 1;
            round++;
        } else {
            spielerindex++;
            zugindex = 1;
        }
    }

    public void newzug(int playernumber){
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
        if(zugindex == 3){
            endzug(playernumber);
        }
    }

    public void checkdices(){
        numbers = new ArrayList<>();
        duplicatednumbers = new HashSet<>();
        for (int dices : dice) {
            numbers.add(Integer.toString(dices));
            duplicatednumbers.add(String.valueOf(Choices.values()[(dices - 1)]));
        }

        options = new ArrayList<>(duplicatednumbers);
        Collections.sort(options);

        duplicateoptions();
        fullhouseoptions();
        streetoptions();

        options.add("chance");
    }

    public void duplicateoptions(){
        for(int x = 1; x < 7; x++){
            if (Collections.frequency(numbers, String.valueOf(x)) >= 3){
                options.add("threepasch");
            }
            if (Collections.frequency(numbers, String.valueOf(x)) >= 4){
                options.add("fourpasch");
            }
            if (Collections.frequency(numbers, String.valueOf(x)) == 5){
                options.add("pasch");
            }
        }
    }

    public void fullhouseoptions(){
        for(int y = 1; y < 7; y++){
            for(int z = 1; z < 7; z++){
                if(Collections.frequency(numbers, String.valueOf(y)) == 3 && Collections.frequency(numbers, String.valueOf(z)) == 2 && y != z){
                    options.add("fullhouse");
                }
            }
        }
    }

    public void streetoptions(){
        Collections.sort(numbers);
        Set<String> streets = new HashSet<>(numbers);
        numbers.clear();
        numbers.addAll(streets);
        StringBuilder numberiterator = new StringBuilder();

        for (String s : numbers){
            numberiterator.append(s);
        }

        if(numberiterator.toString().contains("1234")|| numberiterator.toString().contains("2345")|| numberiterator.toString().contains("3456")){
            options.add("smallstreet");
        }
        if(numberiterator.toString().contains("12345")|| numberiterator.toString().contains("23456")){
            options.add("grandstreet");
        }
    }

    public void checkoptions() {
        matcher = new ArrayList<>();

        enumvalues = Stream.of(Choices.values()).map(Choices::name).collect(Collectors.toList());

        for (String option : options) {
            for (int j = 0; j < enumvalues.size(); j++) {
                if (option.equals(enumvalues.get(j))) {
                    matcher.add(j);
                }
            }
        }
        Collections.sort(matcher);
        checkfinaloptions(matcher);
    }

    public void checkfinaloptions(List<Integer> matcher){
        freeoptions = new ArrayList<>();
        for (Integer integer : matcher) {
            if (wahlarray[spielerindex][integer] == 0) {
                freeoptions.add(Choices.values()[integer]);
            }
        }
        checklist();
    }

    public void checklist(){
        if(freeoptions.size() == 0){
            strich();
        } else {
            choicetolist();
        }
    }

    public void strich(){
        strichoptions = new ArrayList<>();
        System.out.print("          Streiche ein Element, um weiter zu spielen. Du kannst diese Elemente noch streichen: ");
        for(int i = 0; i < wahlarray[spielerindex].length; i++){
            if(wahlarray[spielerindex][i] == 0){
                strichoptions.add(Choices.values()[i]);
            }
        }
        System.out.print(strichoptions);
        strichchoice = IOTools.readString(" Gebe deine Wahl an, welches Element du streichen möchtest: ");
        strichtolist();
    }

    public void strichtolist(){
        for (int i = 0; i < enumvalues.size(); i++) {
            if (strichchoice.equals(enumvalues.get(i))) {
                strichmatcher = i;
                strichtoarray();
            }
        }
    }

    public void strichtoarray(){
        wahlarray[spielerindex][strichmatcher] = 777;
        System.out.println();
    }

    public void choicetolist(){
        listchoice = IOTools.readString("        Wähle welcher der Optionen "+freeoptions+" du wählen möchtest. Möchtest du ein Objekt streichen, gib strich ein: ");
        if(listchoice.equals("strich")){
            System.out.println();
            strich();
        } else {
            for (int i = 0; i < enumvalues.size(); i++) {
                if (listchoice.equals(enumvalues.get(i))) {
                    listmatcher = i;
                    listentry();
                }
            }
        }
    }

    public void listentry(){
        switch (listmatcher){
            case 0: case 1: case 2: case 3: case 4: case 5:
                explicitnumber();
                break;
            case 6: case 7: case 12:
                wholeammount();
                break;
            case 8: case 9: case 10: case 11:
                explicitammount();
                break;
        }
        numbertolist();
    }

    public void explicitnumber(){
        number = 0;
        for (int dices : dice) {
            if (dices == (listmatcher + 1)) {
                number = number + dices;
            }
        }
    }

    public void wholeammount(){
        number = 0;
        for (int dices : dice) {
            number = number + dices;
        }
    }

    public void explicitammount(){
        switch (listmatcher){
            case 8:
                number = 25;
                break;
            case 9:
                number = 30;
                break;
            case 10:
                number = 40;
                break;
            case 11:
                number = 50;
                break;
        }
    }

    public void numbertolist(){
        wahlarray[spielerindex][listmatcher] = number;
        System.out.println();
    }

    public void berechneresult(){
        for(int i = 0; i < wahlarray.length; i++){
            int finalsum = 0;
            for(int j = 0; j < wahlarray[i].length; j++){
                if(wahlarray[i][j] != 777){
                    finalsum = finalsum + wahlarray[i][j];
                    if(j == 5 && finalsum >= 63){
                        finalsum = finalsum + 35;
                    }
                }
            }
            System.out.println("Spieler: "+(i+1)+" hat "+ finalsum +" Punkte erreicht!! Glückwunsch!");
        }
    }
}
