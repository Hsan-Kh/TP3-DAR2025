package tp3.act_3_2;

import java.io.Serializable;

/**
 * Classe représentant une opération arithmétique
 * Cette classe est sérialisable pour pouvoir être envoyée via des sockets
 */
public class Operation implements Serializable {
    private static final long serialVersionUID = 1L;

    private double op1;
    private double op2;
    private char operateur;

    public Operation(double op1, double op2, char operateur) {
        this.op1 = op1;
        this.op2 = op2;
        this.operateur = operateur;
    }

    public double getOp1() {
        return op1;
    }

    public double getOp2() {
        return op2;
    }

    public char getOperateur() {
        return operateur;
    }

    @Override
    public String toString() {
        return op1 + " " + operateur + " " + op2;
    }
}