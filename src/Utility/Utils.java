package Utility;

import Utility.AlgoritmoTestPrimalita.AlgoritmoTestPrimalitaMillerRabin;
import sun.rmi.runtime.Log;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Questa classe contiene vari metodi per calcoli su BigInteger.
 *
 * @author gaetano
 */
public class Utils {

    static final String SPACE = "\u0020";

    private static int _accuracy = 100;
    private static BigInteger _upperBoundRicercaPrimi = new BigInteger("256");

    /**
     * Metodo per la ricerca di un numero primo con grandezza pari al numero di bit scelto
     * @param numeroCifre numero bit scelto per dimensione ricerca numero primo
     * @param r
     * @return numero primo
     */
    public static BigInteger randomNumeroPrimo(int numeroCifre, Random r){

        return BigInteger.probablePrime(numeroCifre, r);
    }

    /**
     * Ricerca di un numero primo dopo p
     * @param p numero primo scelto come limite inferiore per la ricerca
     * @return numero primo più grande di p
     */
    public static BigInteger randomNumeroPrimoDopoP(BigInteger p){

        return getFirstPrimeNumberAfterNumber(p.add(BigInteger.ONE), _accuracy);
    }

    /**
     * Metodo per il prodotto di due numeri primi
     * @param numPrimo1
     * @param numPrimo2
     * @return prodotto di numPrimo1 con numPrimo2
     */

    public static BigInteger prodottoDueNumeriPrimi(BigInteger numPrimo1, BigInteger numPrimo2){

        return numPrimo1.multiply(numPrimo2);
    }

    /**
     * Metodo per la ricerca di una phi del prodotto di due numeri primi
     * @param numPrimo1
     * @param numPrimo2
     * @return phi((numPrimo1-1)(numPrimo2-1))
     */

    public static BigInteger phiDiProdottoDueNumeriPrimi(BigInteger numPrimo1, BigInteger numPrimo2){

        return numPrimo1.subtract(BigInteger.ONE).multiply(numPrimo2.subtract(BigInteger.ONE));
    }

    /**
     * Metodo per la ricerca di un numero coprimo piu piccolo con un altro numero
     * @param n numero su cui basare la ricerca
     * @param numeroCifre numero di cifre per la grandezza del risultato
     * @param r
     * @return numero coprimo con n più piccolo di n
     */

    public static BigInteger coprimoPiuPiccolo(BigInteger n, int numeroCifre, Random r){

        BigInteger e = BigInteger.probablePrime(numeroCifre, r);
        while(e.compareTo(BigInteger.ONE) <= 0 || e.compareTo(n) >= 0 || !e.gcd(n).equals(BigInteger.ONE)){
            e = BigInteger.probablePrime(numeroCifre, r);
        }
        //while (n.gcd(e).compareTo(BigInteger.ONE) > 0 && e.compareTo(n) < 0 );
        return e;
    }

    //calcola il numero d tale che il suo prodotto con e sia congruo a 1 mod phi(n) ovvero che ed=1 mod(phi(n))

    /**
     * Metodo per la ricerca dell'inverso di un numero modulo n
     * @param m modulo dell'operazione
     * @param e numero di cui si vuole trovare l'inverso
     * @return numero inverso di e modulo m
     */
    public static BigInteger trovaDconEDcongruo1modn(BigInteger m, BigInteger e){

        return e.modInverse(m);
    }

    /**
     * Metodo per la conversione da tipo byte a tipo String
     * @param encrypted array di byte
     * @return conversione di un array di byte in String
     */

    public static String bytesToString(byte[] encrypted) {

        String test = "";
        for (byte b : encrypted) {
            test += Byte.toString(b);
        }
        return test;
    }

    public static String arraybytesToString(ArrayList<byte[]> msg){

        String result = "";
        for (int i = 0; i < msg.size(); i++){
            result += bytesToString(msg.get(i));
        }
        return result;
    }

    /**
     * Metoodo per la divisione in blocchi per messaggi più lunghi della chiave di cifratura
     * @param msg messaggio originale
     * @param numeroCifre dimensione di ogni blocco
     * @return ArrayList di byte[] del messaggio originale
     */

    public static ArrayList<byte[]> divisioneInBlocchi(byte[] msg, int numeroCifre){

        ArrayList<byte[]> msgInBlocchi = new ArrayList<>();
        int maxBytes = numeroCifre * 2 / 8 / 2;
        int indexArray = 1;
        int bytesRimanenti = msg.length;
        do{
            int k = 0;
            int dimBlocco;
            if(bytesRimanenti <= maxBytes){
                dimBlocco = bytesRimanenti;
            }else{
                dimBlocco = maxBytes;
                bytesRimanenti -= maxBytes;
            }
            /*
            int dimBlocco = maxBytes; //appena messo
            */
            byte[] blocco = new byte[dimBlocco];
            for (int i = (indexArray - 1) * maxBytes; i < indexArray * maxBytes && i < msg.length; i++) {
                blocco[k] = msg[i];
                k++;
            }
            /*
            //aggiunge padding di SPACE
            for(int i = k+1; i < maxBytes; i++){
                blocco[k] = SPACE.getBytes()[0];
            }
            */
            msgInBlocchi.add(blocco);
            indexArray++;
        }while(msg.length > maxBytes * (indexArray - 1));

        return msgInBlocchi;
    }

    /**
     * Metodo per la ricerca di un numero primo maggiore di un altro
     * @param number numero su cui si vuole basare la ricerca
     * @param accuracy accuratezza
     * @return numero primo maggiore di number
     */

    public static BigInteger getFirstPrimeNumberAfterNumber(BigInteger number, int accuracy) {
        // Numero primo da restituire
        BigInteger primeNumber = null;
        // Booleano che rappresenta la condizione di uscita.
        boolean trovato = false;
        // Strategia
        AlgoritmoTestPrimalitaMillerRabin algoritmoTestPrimalitaStrategy = new AlgoritmoTestPrimalitaMillerRabin();
        //IAlgoritmoTestPrimalitaStrategy algoritmoTestPrimalitaStrategy = new AlgoritmoTestPrimalitàFermatStrategy();
//		System.out.println("Numero di partenza: " + number);

        // Carico la lista dei numeri primi precedenti a number.
        List<BigInteger> listaNumeriPrimiPrecedentiNumber = UtilityIntegerNumber.getListaPrimiPrecedentiNumber(_upperBoundRicercaPrimi, _accuracy);
        // Ciclo finch� non trovo il numero primo.
        while (!trovato) {
            // Effettuo il test e salvo l'esito in trovato
            trovato = algoritmoTestPrimalitaStrategy.testaPrimalitaIntero(number, accuracy);
            // Se l'esito del test � positivo assegno il valore di number a primeNumber.
            if (trovato == true) {
                primeNumber = number;
            } else {
				/*
				 * Ricavo il numero intero, successivo a number, buon candidato ad essere un numero primo.
				 */
                number = Utils.nextIntegerNotDivisibleBySeveralPrime(number, listaNumeriPrimiPrecedentiNumber);
            }
        }
        return primeNumber;
    }


    /**
     * Metodo per ottenere un numero intero successivo a quello dato, non divisibile dalla lista
     * dei numeri primi precedenti all'attributo _upperBoundRicercaPrimi, quindi si ottiene un buon
     * candidato ad essere un numero primo.
     *
     * @param number     Numero dal quale si parte per effettuare il test.
     * @param listaPrimi Lista dei primi sulla quale effettuare il test.
     * @return Numero intero non divisibile dalla lista dei numeri primi precedenti di _upperBoundRicercaPrimi.
     */
    private static BigInteger nextIntegerNotDivisibleBySeveralPrime(BigInteger number, List<BigInteger> listaPrimi) {
        // BigInteger da restituire
        BigInteger integerNotDivisibleBySeveralPrime = null;
        // Condizione di uscita dal ciclo
        boolean trovato = false;
        // Incremento number di 1, per passare ad effettuare il testing dal primo numero successivo a quello dato.
        number = number.add(BigInteger.ONE);
        // Ciclo finche non trovo un buon numero
        while (!trovato) {
			/*
			 * Condizione per verificare se il numero da testare in questa iterazione del
			 * ciclo � o meno multiplo della lista dei primi.
			 */
            boolean multiplo = false;
            // Ciclo per verificare che il numero nell'iterazione corrente non sia multiplo di un numero primo.
            for (Iterator<BigInteger> iterator = listaPrimi.iterator(); iterator.hasNext(); ) {
                // Generico elemento della lista dei numeri primi
                BigInteger primeNumber = (BigInteger) iterator.next();
                // Verifico che number non sia multiplo di primeNumber
                if (UtilityIntegerNumber.A_multiplo_B(number, primeNumber) == true) {
                    multiplo = true;
                }
            }
			/*
			 * Se il numero appena testato � un multiplo di uno dei primi, incremento
			 * di uno e al prossimo ciclo testo nuovamente.
			 */
            if (multiplo == true) {
                number = number.add(BigInteger.ONE);
            } else {
				/*
				 * Se il numero appena testato non era multiplo di nessuno dei primi allora
				 * � un buon candidato e usciamo dal ciclo.
				 */
                trovato = true;
                // Assegno il valore del numero trovato alla variabile da fornire in output.
                integerNotDivisibleBySeveralPrime = new BigInteger(number.toString());
            }
        }
        return integerNotDivisibleBySeveralPrime;
    }

    /**
     * Metodo per il calcolo di E e D vulnerabili per l'algoritmo di Wiener degli esponenti bassi
     * @param phi_n
     * @return
     */

    public static ArrayList<BigInteger> calcolaE_DVulnerabili(BigInteger phi_n){
        ArrayList<BigInteger> e_d = new ArrayList<>();
        // Calcolo d.
        BigInteger numberStart_d = new BigInteger(10, new SecureRandom());
        boolean d_primo_con_phin = false;
        BigInteger d = null;
        //trovo un d che sia primo con phi_n
        while(!d_primo_con_phin) {
            d = getFirstPrimeNumberAfterNumber(numberStart_d, _accuracy);
            if (d.gcd(phi_n).equals(BigInteger.ONE)){
                d_primo_con_phin = true;
            }
        }
        //Calcolo e, in modo tale che e*d = 1 (mod fi_n)
        BigInteger e = d.modInverse(phi_n);
        e_d.add(e);
        e_d.add(d);

        return e_d;
    }

    public static void stampa_divisore(){
        System.out.println("----------------------------------------------------------------");
    }
}
