package es.cic;

public class Cuadrado extends AbstractFigura {
    private int lado;

    public Cuadrado(Posicion posicion, int lado, String color) {
        super(posicion, color);
        this.lado = lado;
    }

    @Override
    public void cambiarTamano(int longitud) {
        if (esValida(longitud)) {
            lado = longitud;
        }
        else throw new RuntimeException("Longitud de lado no válida < 0");
    }

    public int getLado() {
        return lado;
    }
}
