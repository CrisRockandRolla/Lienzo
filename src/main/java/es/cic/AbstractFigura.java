package es.cic;

import es.cic.excepciones.OutOfBoundsException;

abstract class AbstractFigura implements Figura {
    private Posicion posicion;
    private Posicion posicion2;
    private String color;


    public AbstractFigura(Posicion posicion, String color) {
        this(posicion, null, color);
    }

    public AbstractFigura(Posicion posicion, Posicion posicion2, String color) {
        this.posicion = posicion;
        this.posicion2 = posicion2;
        this.color = color;
    }

    public void mover(Posicion nuevaPosicion) {
        mover(nuevaPosicion, true);
    }

    public void mover(Posicion nuevaPosicion, boolean isPuntoAplicacion) {
        if (estaDentroLimites(nuevaPosicion)) {
            if (isPuntoAplicacion) {
                this.posicion = nuevaPosicion;
            } else {
                this.posicion2 = nuevaPosicion;
            }
        } else {
            throw new OutOfBoundsException("Posicion " + posicion.toString() + " no válida.");
        }
    }

    public boolean estaDentroLimites(Posicion posicion) {
        return posicion.getCoordenadaX() <= Lienzo.getAncho() && posicion.getCoordenadaX() >= 0 && posicion.getCoordenadaY() <= Lienzo.getAlto() && posicion.getCoordenadaY() >= 0;
    }

    public boolean estaDentroLimites(String id) {
        if (id.toLowerCase().contains("linea"))
            return estaDentroLimites(this.getPosicion()) && estaDentroLimites(posicion2);
        else return estaDentroLimites(this.getPosicion());
    }

    public boolean esValida(int longitud) {
        return longitud > 0;
    }

    public void cambiarTamano(int longitud) {
        throw new RuntimeException("No se puede cambiar el tamaño de esta figura");
    }

    public Posicion getPosicion() {
        return posicion;
    }

    public Posicion getPosicion2() {
        return posicion2;
    }

//    public int getX() {
//        return posicion.getCoordenadaX();
//    }
//
//    public int getY() {
//        return posicion.getCoordenadaY();
//    }

    public String getColor() {
        return color;
    }

    public void setPosicion(Posicion posicion) {
        this.posicion = posicion;
    }

    public void setPosicion2(Posicion posicion2) {
        this.posicion2 = posicion2;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
