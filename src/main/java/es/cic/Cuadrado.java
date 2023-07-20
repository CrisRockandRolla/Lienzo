package es.cic;

public class Cuadrado extends AbstractFigura {
    private int lado;

    public Cuadrado(Posicion posicion, int lado, String color) {
        super(posicion, color);
        this.lado = lado;
    }


    @Override
    public void cambiarTamano(int longitud) {
        lado = longitud;
    }

    public int getLado() {
        return lado;
    }
}
