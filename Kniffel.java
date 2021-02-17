import Prog1Tools.IOTools;

public class Kniffel {
    public static void main(String[] args){
        int playeramount;
        String decisions;

        playeramount = IOTools.readInt("Mitspieleranzahl eingeben: ");
        String[] player = new String[playeramount];
        Player spieler = new Player();

        spieler.setplayeramount(playeramount);
        spieler.setarray(playeramount);

        for (int i = 0; i < playeramount; i++) {
            spieler.setName(IOTools.readString("Gib den Spielername ein: "));
            player[i] = spieler.getName();
        }

        System.out.println();
        System.out.println("////////////////START\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\");
        System.out.println();

        for(int j = 0; j < (13*playeramount); j++){
            spieler.rolldices(player);
            for(int k = 0; k < 2; k++){
                decisions = IOTools.readString("    Welche Würfel sollen nochmal gewürfelt werden? (In Form 2-3-5 eingeben) Ansonsten end eintippen, um Zug zu beenden: ");
                spieler.choice(decisions, playeramount);
                if(decisions.equals("end")){
                    break;
                }
            }
            spieler.checkdices();
            spieler.checkoptions();
        }

        spieler.berechneresult();

        System.out.println();
        System.out.println("////////////////ENDE\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\");
        System.out.println();
    }
}

