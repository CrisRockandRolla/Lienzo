package es.cic;

public class Circulo extends AbstractFigura{
        private int radio;

        public Circulo(Posicion posicion, int radio, String color) {
            super(posicion, color);
            this.radio = radio;
        }

        public int getRadio() {
            return radio;
        }

    @Override
    public void cambiarTamano(int longitud) {
        radio = longitud;
    }
}
