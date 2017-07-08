import RSA.AlgoritmoAttaccoEsponentiBassiWiener.AlgoritmoAttaccoWiener;
import RSA.AlgoritmoAttaccoFattorizzazione.AlgoritmoFattorizzazione;
import RSA.RSA;
import Utility.PrivateKey;
import Utility.Utils;

import javax.rmi.CORBA.Util;
import java.util.ArrayList;
import java.util.Scanner;


/**
 * Applicazione per la cifratura, decifratura e attacco con Algoritmo di Fattorizzazione e Algoritmo di Wiener per Esponenti Bassi che chiede un messaggio da cifrare, numero di cifre per la grandezza delle chiavi e per Wiener prova su 20 combinazioni di chiavi pubbliche e private quali di queste viene trovata
 *
 * @author gaetano
 */
public class mainTest {

    public static void main(String[] args) {

        boolean exit = false;

        while (!exit){

            Scanner inputScanner = new Scanner(System.in);

            Utils.stampa_divisore();

            System.out.println("Quante cifre desideri che sia la chiave di Bob? (Es.: 16, 32, 64, 128,...");
            String numeroCifreString = inputScanner.nextLine();
            int numeroCifre = Integer.parseInt(numeroCifreString);
            if (numeroCifre < 10 || numeroCifre > 1024){
                numeroCifre = 10;
                System.out.println("Numero Cifre troppo basso o maggiore di 1024, impostato a 10 cifre");
            }

            Utils.stampa_divisore();

            boolean esponentiBassi = false;

            System.out.println("Vuoi che le chiavi generate siano vulnerabili all'Attacco di Wiener?");
            String vulnerabile = inputScanner.nextLine();
            switch (vulnerabile){
                case "s":
                case "S":
                case "si":
                case "Si":
                case "SI": {
                    esponentiBassi = true;
                    System.out.println("Generazione Chiavi Vulnerabili!");
                }
                default:
            }

            Utils.stampa_divisore();

            System.out.println("Inserisci il messaggio di Alice da mandare ad Bob: ");
            String msg = inputScanner.nextLine();

            String msgScelto = msg;

            Utils.stampa_divisore();

            int tentativiWiener = 1;

            System.out.println("Vuoi provare la generazione di 20 Chiavi Pubbliche e Private per vedere quante volte Wiener trova quelle esatte? [S/N]");
            String venti_tentativi = inputScanner.nextLine();
            switch (venti_tentativi){
                case "s":
                case "S":
                case "si":
                case "Si":
                case "SI": tentativiWiener = 20;
            }

            RSA rsa = new RSA(numeroCifre, esponentiBassi);

            Utils.stampa_divisore();

            System.out.println("Le Chiavi Pubbliche e Private di Bob sono:");
            rsa.stampaChiavi();

            Utils.stampa_divisore();

            ArrayList<byte[]> arrayMsgCifrato = rsa.cifraMessaggio(msgScelto, rsa.getChiavePubblica().get_e(), rsa.getChiavePubblica().get_n());
            String stringMsgCifrato = Utils.arraybytesToString(arrayMsgCifrato);
            System.out.println("Alice ottiene la Chiave Pubblica di Bob e cifra il messaggio ottenendo: " + stringMsgCifrato);

            Utils.stampa_divisore();

            String msgDecifrato = rsa.decifraMessaggio(arrayMsgCifrato, rsa.getChiavePubblica().get_n(), rsa.getChiavePrivata().get_d());
            System.out.println("Bob decifra il messaggio cifrato di Alice con la sua Chiave Privata ed ottiene il messaggio originale: " + msgDecifrato);

            Utils.stampa_divisore();

            int numeroMassimoTentativiFattorizzazione = 200000;
            PrivateKey chiavePrivataEve = AlgoritmoFattorizzazione.calcolaPrivateKey(rsa.getChiavePubblica(), numeroMassimoTentativiFattorizzazione);
            if (chiavePrivataEve != null){
                String msgDecifratoEve = rsa.decifraMessaggio(arrayMsgCifrato, rsa.getChiavePubblica().get_n(), chiavePrivataEve.get_d());
                System.out.println("Messaggio decifrato da Eve con la Fattorizzazione: " + msgDecifratoEve);
            }else{
                System.out.println("ERRORE: Chiavi Troppo grandi, la Fattorizzazione al " + numeroMassimoTentativiFattorizzazione + "° tentativo ancora non trova alcun risultato");
            }

            boolean trovata = false;
            int successiWiener = 0;
            for(int i = 0; i < tentativiWiener; i++){
                Utils.stampa_divisore();

                System.out.println("Tentativo Wiener #" + (i+1));
                rsa.stampaChiavi();
                PrivateKey privateKeyWiener = AlgoritmoAttaccoWiener.calcoloChiavePrivata(rsa.getChiavePubblica());
                if (privateKeyWiener != null){
                    System.out.println("Wiener chiave trovata!");
                    System.out.println("d Privata: " + rsa.getChiavePrivata().get_d());
                    System.out.println("d Wiener: " + privateKeyWiener.get_d());
                    String msgDecifratoEveWiener = rsa.decifraMessaggio(arrayMsgCifrato, rsa.getChiavePubblica().get_n(), privateKeyWiener.get_d());
                    System.out.println("Messaggio decifrato da Eve con Attacco di Wiener: " + msgDecifratoEveWiener);
                    trovata = true;
                    successiWiener++;
                } else{
                    System.out.println("Wiener chiave non trovata!");
                }
                rsa = new RSA(numeroCifre, esponentiBassi);
                arrayMsgCifrato = rsa.cifraMessaggio(msgScelto, rsa.getChiavePubblica().get_e(), rsa.getChiavePubblica().get_n());
            }

            Utils.stampa_divisore();

            System.out.println("Wiener ha avuto " + successiWiener + " successo/i su " + tentativiWiener + " tentativo/i");

            Utils.stampa_divisore();
            Utils.stampa_divisore();
            Utils.stampa_divisore();

            System.out.println("Vuoi riprovare? [S/N]");
            String uscita = inputScanner.nextLine();
            switch (uscita){
                case "n":
                case "N":
                case "no":
                case "NO":
                case "No": exit = true;
            }
        }
    }
}
