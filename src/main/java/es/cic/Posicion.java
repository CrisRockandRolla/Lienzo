package es.cic;

public class Posicion {
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

    @Override
    public String toString() {
        return "coordenadaX=" + coordenadaX +
                ", coordenadaY=" + coordenadaY;
    }
}