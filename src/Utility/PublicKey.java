/**
 *
 */
package Utility;

import java.math.BigInteger;

/**
 * Questa classe rappresenta la chiave pubblica dell'algoritmo Utility.RSA.
 *
 * @author gaetano
 */
public class PublicKey {

    /**
     * Componente della chiave pubblica. Si ottiene dal prodotto dei numeri primi p,q.
     */
    private BigInteger _n;
    /**
     * Esponente di cifratura. Deve essere tale che d*e = 1 (mod(p-1)(q-1))
     */
    private BigInteger _e;

    /**
     * Costruttore vuoto
     */
    public PublicKey(){

    }

    /**
     * Costruttore.
     *
     * @param _n Prodotto dei fattori primi p,q.
     * @param _e Esponente di cifratura.
     */
    public PublicKey(BigInteger _n, BigInteger _e) {
        this._n = _n;
        this._e = _e;
    }

    /**
     * @return the _n
     */
    public BigInteger get_n() {
        return _n;
    }

    /**
     * @param _n the _n to set
     */
    public void set_n(BigInteger _n) {
        this._n = _n;
    }

    /**
     * @return the _e
     */
    public BigInteger get_e() {
        return _e;
    }

    /**
     * @param _e the _e to set
     */
    public void set_e(BigInteger _e) {
        this._e = _e;
    }


}
