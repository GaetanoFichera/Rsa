package RSA.AlgoritmoAttaccoFattorizzazione;

import Utility.PrivateKey;
import Utility.PublicKey;

import java.math.BigInteger;
import java.util.ArrayList;

/**
 * Implementazione algoritmo attacco Rsa mediante Fattorizzazione
 *
 * @author gaetano
 */

public class AlgoritmoFattorizzazione {

    /*
     * Ricerca della Chiave Privata
     */
    public static PrivateKey calcolaPrivateKey(PublicKey chiavePubblica, int maxTentativi) {
        BigInteger nP = chiavePubblica.get_n();
        BigInteger eP = chiavePubblica.get_e();
        double n = nP.doubleValue();
        double radice = Math.sqrt(n);
        radice = Math.ceil(radice);
        if (radice % 2 == 0) {
            radice = radice + 1;
        }
        Integer radice1 = (int) radice;
        BigInteger rad = new BigInteger(radice1.toString());
        int i = 0;
        while (nP.mod(rad) != BigInteger.ZERO && rad.compareTo(BigInteger.ZERO) >= 0 && i <= maxTentativi) {
            rad = rad.subtract(new BigInteger("2"));
            i++;
        }
        PrivateKey privateKey = null;
        if (i < maxTentativi){
            System.out.println("Cicli necessari per Fattorizzazione: " + i);
            BigInteger p = rad;
            BigInteger q = nP.divide(p);
            BigInteger fi_n = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
            BigInteger d = eP.modInverse(fi_n);
            privateKey = new PrivateKey(p, q, d);
        }
        return privateKey;
    }
}
