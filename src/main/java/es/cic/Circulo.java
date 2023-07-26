package es.cic;

public class Circulo extends AbstractFigura {
    private int radio;

    public Circulo(Posicion posicion, String color) {
        super(posicion, color);
    }

    public Circulo(Posicion posicion, int radio, String color) {
        super(posicion, color);
        if (!esValida(radio)) throw new RuntimeException("Longitud de radio no válida < 0");
        this.radio = radio;
    }

    @Override
    public void cambiarTamano(int longitud) {
        if (longitud > 0) {
            radio = longitud;
        } else throw new RuntimeException("Longitud de radio no válida < 0");
    }

    public int getRadio() {
        return radio;
    }
}
