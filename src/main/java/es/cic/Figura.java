package es.cic;

public interface Figura {
    void mover(Posicion posicion);

    void cambiarTamano(int longitud);

    Posicion getPosicion();

    String getColor();

}
