/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package filespackage;

/**
 *
 * @author USUARIO
 */
public class Pair<P, N> {

    private P p;
    private N n;

    /**
     * Constructora
     *
     * @param _p
     * @param _n
     */
    public Pair(P _p, N _n) {
        this.p = _p;
        this.n = _n;
    }

    /*
      public Pair(String row) {
        String[] split = row.split(" ");
       this.p = (P) split[0];
       this.n = (N) split[1];
*/




    public P getP() {
        return this.p;
    }

    public N getN() {
        return this.n;
    }

    public void setP(P p) {
        this.p = p;
    }

    public void setN(N n) {
        this.n = n;
    }
}
