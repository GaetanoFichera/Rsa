/**
 *
 */
package Utility;

import java.math.BigInteger;

/**
 * Questa classe rappresenta la chiave privata dell'algoritmo Utility.RSA.
 *
 * @author gaetano
 */
public class PrivateKey {

    /**
     * Numero primo estremamente grande, necessario per ottenere n=p*q.
     */
    private BigInteger _p;
    /**
     * Numero primo estremamente grande, necessario per ottenere n=p*q.
     */
    private BigInteger _q;
    /**
     * Esponente di decifratura. Deve essere tale che d*e = 1 (mod(p-1)(q-1)).
     */
    private BigInteger _d;

    /**
     * Costruttore vuoto
     */
    public PrivateKey(){

    }

    /**
     * Costruttore con parametri
     *
     * @param _p Numero primo, molto grande.
     * @param _q Numero primo, molto grande.
     * @param _d Esponente di decifratura.
     */
    public PrivateKey(BigInteger _p, BigInteger _q, BigInteger _d) {
        this._p = _p;
        this._q = _q;
        this._d = _d;
    }

    /**
     * @return the _p
     */
    public BigInteger get_p() {
        return _p;
    }

    /**
     * @param _p the _p to set
     */
    public void set_p(BigInteger _p) {
        this._p = _p;
    }

    /**
     * @return the _q
     */
    public BigInteger get_q() {
        return _q;
    }

    /**
     * @param _q the _q to set
     */
    public void set_q(BigInteger _q) {
        this._q = _q;
    }

    /**
     * @return the _d
     */
    public BigInteger get_d() {
        return _d;
    }

    /**
     * @param _d the _d to set
     */
    public void set_d(BigInteger _d) {
        this._d = _d;
    }


}
