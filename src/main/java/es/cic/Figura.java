package es.cic;

public interface Figura {
    void mover(Posicion posicion);
    void cambiarTamano(int longitud);
    boolean estaDentroLimites(Posicion posicion);
    int getX();
    int getY();

    String getColor();

}
