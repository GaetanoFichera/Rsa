/**
 *
 */
package Utility.AlgoritmoTestPrimalita;

import Utility.UtilityIntegerNumber;

import java.math.BigInteger;

/**
 * Questa classe rappresenta l'implementazione per verificare la primalit� di un numero intero mediante l'applicazione
 * dell'algoritmo di Miller-Rabin.
 *
 * @author gaetano
 */
public class AlgoritmoTestPrimalitaMillerRabin {
    /**
     * Elemento dell'equazione n-1=2^s * r
     */
    private BigInteger _s;
    /**
     * Elemento dell'equazione n-1=2^s * r
     */
    private BigInteger _r;
    /**
     * Generico a, di ogni iterazione
     */
    private BigInteger _a;
    /**
     * Generico elemento utilizzato per effettuare i controlli.
     */
    private BigInteger _y;

    /**
     * Testa la primalit� del numero intero dispari n, per diverse volte.
     *
     * @param n     Numero intero da testare.
     * @param times Numero di volte in cui effettuare il test.
     * @return True se il numero � probabilmente primo. False se � composto.
     */
    public boolean testaPrimalitaIntero(BigInteger n, int times) {
        // Inizializzo _s e _r nulli.
        _s = BigInteger.ZERO;
        _r = BigInteger.ZERO;
        // Calcolo r,s tali che n-1 = 2^s * r
        this.calcola_r_s(n);
        // Ciclo times volte.
        for (int i = 0; i < times; i++) {
            // Genero un numero casuale nell'intervallo [2, n-2]
            _a = UtilityIntegerNumber.generaInteroCasualeInIntervallo(new BigInteger("2"), n.subtract(new BigInteger("2")));
            // Calcolo y = a^r (mod n)
            _y = _a.modPow(_r, n);
            // Controllo che y sia diverso da 1 e da n-1
            if ((_y.compareTo(BigInteger.ONE) != 0) && (_y.compareTo(n.subtract(new BigInteger("2"))) != 0)) {
                // Ciclo per j che va da 1 a s-1
                for (int j = 1; j < _s.intValue(); j++) {
                    // Controllo che y != n-1
                    if (_y.compareTo(n.subtract(new BigInteger("1"))) != 0) {
                        // y = y^2 (mod n)
                        _y = _y.modPow(new BigInteger("2"), n);
                        // Se y = 1, numero composto
                        if (_y.compareTo(BigInteger.ONE) == 0) {
                            return false;
                        }
                    }
                }
                // Se y != n - 1, numero composto
                if (_y.compareTo(n.subtract(new BigInteger("1"))) != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Metodo per calcolare i componenti dell'equazione n-1=2^s * r.
     *
     * @param n Numero di cui si vuole testare la primalit�.
     */
    private void calcola_r_s(BigInteger n) {
        // Calcolo n - 1
        BigInteger n_meno_1 = n.subtract(BigInteger.ONE);
        // Ciclo finch� n-1 non diventa dispari
        while (UtilityIntegerNumber.isPari(n_meno_1)) {
            // Effettuo n-1 / 2
            n_meno_1 = n_meno_1.divide(new BigInteger("2"));
            // Incremento s di 1.
            _s = _s.add(BigInteger.ONE);
        }
        // Per ricavare r, effettuo la divisione tra n e 2^s.
        _r = n.divide(new BigInteger("2").pow(_s.intValue()));

//		System.out.println("s: " + _s.toString());
//		System.out.println("r: " + _r.toString());
//		System.out.println("2^s * r: " + new BigInteger("2").pow(_s.intValue()).multiply(_r));
    }

}
