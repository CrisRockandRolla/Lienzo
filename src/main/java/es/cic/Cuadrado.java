package es.cic;

public class Cuadrado extends AbstractFigura {
    private int lado;

    public Cuadrado(Posicion posicion, String color) {
        super(posicion, color);
    }

    public Cuadrado(Posicion posicion, int lado, String color) {
        super(posicion, color);
        if (esValida(lado)) this.lado = lado;
        else throw new RuntimeException("Longitud de lado no válida < 0");
    }

    @Override
    public void cambiarTamano(int longitud) {
        if (!esValida(longitud)) throw new RuntimeException("Longitud de lado no válida < 0");
        lado = longitud;
    }

    public int getLado() {
        return lado;
    }
}
