package RSA;

import Utility.PrivateKey;
import Utility.PublicKey;
import Utility.Utils;

import javax.rmi.CORBA.Util;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;

/**
 * Implementazione Classe RSA per Generazione di Chiave Pubblica e Privata
 *
 * @author gaetano
 */

//chiavi pubbliche (n,e) chiavi private (p,q,d)
public class RSA {

    //BigInteger p, q, d, n, e;
    private PrivateKey chiavePrivata;
    private PublicKey chiavePubblica;
    int numeroCifre;

    public RSA(int numeroCifre, boolean esponentiBassi) {
        this.numeroCifre = numeroCifre;
        Random r = new Random();
        chiavePrivata = new PrivateKey();
        chiavePubblica = new PublicKey();

        // p numero primo di lunghezza pari "numeroCifre"
        chiavePrivata.set_p(Utils.randomNumeroPrimo(this.numeroCifre, r));

        // q numero primo di lunghezza pari "numeroCifre"
        chiavePrivata.set_q(Utils.randomNumeroPrimo(this.numeroCifre, r));

        // phi del prodotto tra p e q
        BigInteger phin = Utils.phiDiProdottoDueNumeriPrimi(chiavePrivata.get_p(), chiavePrivata.get_q());

        // n prodotto tra p e q
        chiavePubblica.set_n(Utils.prodottoDueNumeriPrimi(chiavePrivata.get_p(), chiavePrivata.get_q()));

        if(esponentiBassi){
            ArrayList<BigInteger> e_d = Utils.calcolaE_DVulnerabili(phin);
            chiavePubblica.set_e(e_d.get(0));
            chiavePrivata.set_d(e_d.get(1));
        }else{
        // e coprimo con n pù piccolo di phi(n)
        chiavePubblica.set_e(Utils.coprimoPiuPiccolo(phin, this.numeroCifre, r));

        // d inverso mod(phi(n)) di e
        chiavePrivata.set_d(Utils.trovaDconEDcongruo1modn(phin, chiavePubblica.get_e()));
        }
    }

    public PrivateKey getChiavePrivata() {
        return chiavePrivata;
    }

    public void setChiavePrivata(PrivateKey chiavePrivata) {
        this.chiavePrivata = chiavePrivata;
    }

    public PublicKey getChiavePubblica() {
        return chiavePubblica;
    }

    public void setChiavePubblica(PublicKey chiavePubblica) {
        this.chiavePubblica = chiavePubblica;
    }

    public int getNumeroCifre() {
        return numeroCifre;
    }

    /*
     * Prende un messaggio di tipo String e ritorna il messaggio cifrato in array di byte
     */
    public byte[] cifraMessaggioOne(byte[] msg, BigInteger e, BigInteger n) {
        return (new BigInteger(msg)).modPow(e, n).toByteArray();
    }

    public ArrayList<byte[]> cifraMessaggio(String msg, BigInteger e, BigInteger n){
        ArrayList<byte[]> arrayMsgCorretto = Utils.divisioneInBlocchi(msg.getBytes(), numeroCifre);
        ArrayList<byte[]> arrayMsgCifrato = new ArrayList<>();
        for (int i = 0; i < arrayMsgCorretto.size(); i++){
            arrayMsgCifrato.add(cifraMessaggioOne(arrayMsgCorretto.get(i), e, n));
        }
        return arrayMsgCifrato;
    }

    public ArrayList<byte[]> cifraMessaggio(ArrayList<byte[]> msg, BigInteger e, BigInteger n){
        ArrayList<byte[]> arrayMsgCifrato = new ArrayList<>();
        for (int i = 0; i < msg.size(); i++){
            arrayMsgCifrato.add(cifraMessaggioOne(msg.get(i), e, n));
        }
        return arrayMsgCifrato;
    }

    /*
     * Prende un messaggio cifrato di tipo byte e ritorna il messaggio originale in String
     */
    public String decifraMessaggioOne(byte[] msg, BigInteger n, BigInteger d) {
        return new String((new BigInteger(msg)).modPow(d, n).toByteArray());
    }

    public String decifraMessaggio(ArrayList<byte[]> arrayMsgCifrato, BigInteger n, BigInteger d){
        String msgDecifrato = "";
        for (int i = 0; i < arrayMsgCifrato.size(); i++){
            msgDecifrato += decifraMessaggioOne(arrayMsgCifrato.get(i), n, d);
        }
        return msgDecifrato;
    }

    public void stampaChiavi(){
        System.out.println("Chiave Pubblica [n:" + chiavePubblica.get_n() + " e:" + chiavePubblica.get_e() + "]");
        System.out.println("Chiave Privata [p:" + chiavePrivata.get_p() + " q:" + chiavePrivata.get_q() + " d:" + chiavePrivata.get_d() + "]");
    }

}
