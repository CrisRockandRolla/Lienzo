package es.cic;

import java.io.Serializable;

public class Posicion implements Serializable {
    private int coordenadaX;
    private int coordenadaY;

    public Posicion() {
    }

    public Posicion(int coordenadaX, int coordenadaY) {
        this.coordenadaX = coordenadaX;
        this.coordenadaY = coordenadaY;
    }

    public int getCoordenadaX() {
        return coordenadaX;
    }

    public int getCoordenadaY() {
        return coordenadaY;
    }
}