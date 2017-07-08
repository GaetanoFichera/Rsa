/**
 *
 */
package Utility;

import Utility.AlgoritmoTestPrimalita.AlgoritmoTestPrimalitaMillerRabin;

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Questa classe contiene differenti metodi, utili per il calcolo con numeri interi.
 *
 * @author gaetano
 */
public class UtilityIntegerNumber {
    /**
     * Metodo per verificare che un intero A, divide un intero B.
     *
     * @param A Intero A divisore.
     * @param B Intero B dividendo.
     * @return True se A divide B. False altrimenti.
     */
    public static boolean A_divide_B(BigInteger A, BigInteger B) {
        // Esito della domanda.
        boolean esito = false;
        // Controllo se A divide B. Ovvero se il resto dalla divisione � zero.
        if (A.remainder(B).compareTo(BigInteger.ZERO) == 0) {
            esito = true;
        }
        return esito;
    }

    /**
     * Metodo per verificare che un intero A, sia un multiplo di B
     *
     * @param A Numero su cui si vuole effettuare il test per verificare che sia multiplo di B.
     * @param B
     * @return True se A � un multiplo di B. False altrimenti.
     */
    public static boolean A_multiplo_B(BigInteger A, BigInteger B) {
        // Esito della domanda.
        boolean esito = false;
        // Controllo se A � un multiplo di B. Ovvero se il resto dalla divisione � zero.
        if (A.remainder(B).compareTo(BigInteger.ZERO) == 0) {
            esito = true;
        }
        return esito;
    }

    /**
     * Metodo per verificare che un numero reale A, sia intero, ovvero che il numero ha parte frazionaria nulla.
     *
     * @param n Numero reale da verificare.
     * @return True se intero. False altrimenti.
     */
    public static boolean isIntero(double n) {
        // Esito della domanda.
        boolean esito = false;
        // Controllo se A � un intero
        if ((n == Math.floor(n)) && !Double.isInfinite(n)) {
            esito = true;
        }
        return esito;
    }

    /**
     * Metodo per verificare un numero intero sia pari.
     *
     * @param n Numero intero da verificare.
     * @return True se pari. False se dispari.
     */
    public static boolean isPari(BigInteger n) {
        // Esito della domanda.
        boolean esito = false;
        // Controllo se n � pari
        if (n.remainder(new BigInteger("2")).compareTo(BigInteger.ZERO) == 0) {
            esito = true;
        }
        return esito;
    }

    /**
     * Metodo per ottenere la lista dei numeri primi, precedenti al numero dato.
     *
     * @param number   Upper bound, della lista dei numeri primi.
     * @param accuracy Accuratezza della verifica di primalit�.
     * @return Lista dei numeri primi precedenti al numero dato.
     */
    public static List<BigInteger> getListaPrimiPrecedentiNumber(BigInteger number, int accuracy) {
        // Lista nella quale inserire i primi, precedenti al numero dato
        List<BigInteger> listaPrimiPrecedentiNumero = new LinkedList<BigInteger>();
        // Algoritmo per testare la primalit� di un numero
        AlgoritmoTestPrimalitaMillerRabin algoritmoTestPrimalitaStrategy = new AlgoritmoTestPrimalitaMillerRabin();
        // Generico elemento da testare
        BigInteger integerToTest = null;
        // Esito del test sul generico elemento
        boolean esito;
        // Aggiungo alla lista dei primi 2
        listaPrimiPrecedentiNumero.add(new BigInteger("2"));
        // Ciclo fino ad arrivare all'upper boud
        for (int i = 3; i < number.intValue(); i++) {
            integerToTest = new BigInteger(String.valueOf(i));
            // Verifico che il numero sia dispari
            if (!UtilityIntegerNumber.isPari(integerToTest)) {
                // Applico il test
                esito = algoritmoTestPrimalitaStrategy.testaPrimalitaIntero(integerToTest, accuracy);
                // integerToTest � un numero primo, allora lo aggiungo alla lista.
                if (esito == true) {
                    listaPrimiPrecedentiNumero.add(integerToTest);
//					System.out.println("primo: " + integerToTest.toString());
                }
            }
        }

        return listaPrimiPrecedentiNumero;
    }

    /**
     * Metodo per generare un numero casuale nell'intervallo [min,max].
     *
     * @param min Estremo inferiore dell'intervallo.
     * @param max Estremo superiore dell'intervallo.
     * @return Numero casuale generato nell'intervallo.
     */
    public static BigInteger generaInteroCasualeInIntervallo(BigInteger min, BigInteger max) {
        /*
         *  Controllo che il max non sia minore del min. In tal caso inverto le parti.
    	 */
        if (max.compareTo(min) < 0) {
            BigInteger tmp = min;
            min = max;
            max = tmp;
        } 
        /*
         * Controllo che il max e il min non siano lo stesso numero.
         */
        else if (max.compareTo(min) == 0) {
            return min;
        }
        /*
         *  Incremento di 1, il massimo per considerare anch'esso nell'intervallo dei possibili candidati.
         */
        max = max.add(BigInteger.ONE);
        BigInteger range = max.subtract(min);
        // Numero di bit del range tra min e max.
        int length = range.bitLength();
        // Numero casuale generato.
        BigInteger result = new BigInteger(length, new Random());
        // Controllo che il numero generato non sia out of bound.
        while (result.compareTo(range) >= 0) {
            result = new BigInteger(length, new Random());
        }
        // Riporto il risultato nell'intervallo corretto.
        result = result.add(min);

//        System.out.println("------");
//        System.out.println("min: " + min.toString());
//        System.out.println("max: " + max.toString());
//        System.out.println("result: " + result.toString());


        return result;
    }
}
