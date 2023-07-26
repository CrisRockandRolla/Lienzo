package es.cic;

import es.cic.excepciones.OutOfBoundsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LienzoIntegrationTest {
    private Lienzo cut;
    private final String id1 = "Circulo001";
    private final String id2 = "Linea001";


    @BeforeEach
    void setUp() {
        String nombreArchivo = "figuras.txt";
        this.cut = new Lienzo(nombreArchivo);
    }

    @Test
    void cambiarTamanoValidoTest() {
        Circulo circulo = new Circulo(new Posicion(10, 10), 10, "rojo");
        cut.agregar(id1, circulo);
        int nuevoTamano = 10;
        cut.modificarTamanoFigura(id1, nuevoTamano);
        assertThat(circulo.getRadio(), is(nuevoTamano));

        Linea linea = new Linea(new Posicion(10, 10), new Posicion(0, 0), "rojo");
        cut.agregar(id2, linea);
        Posicion nuevaPosicion = new Posicion(20, 50);
        cut.modificarTamanoFigura(id2, nuevaPosicion, true);
        assertThat(linea.getPosicion(), is(nuevaPosicion));
        cut.modificarTamanoFigura(id2, nuevaPosicion, false);
        assertThat(linea.getPosicion2(), is(nuevaPosicion));
    }

    @Test
    void cambiarTamanoNoValidoTest() {
        Circulo circulo = new Circulo(new Posicion(10, 10), 10, "rojo");
        cut.agregar(id1, circulo);
        int nuevoTamano = -10;

        assertThrows(RuntimeException.class, () -> cut.modificarTamanoFigura(id1, nuevoTamano));

        Linea linea = new Linea(new Posicion(10, 10), new Posicion(0, 0), "rojo");
        cut.agregar(id2, linea);
        Posicion nuevaPosicion = new Posicion(-20, 50);
        assertThrows(OutOfBoundsException.class, () -> cut.modificarTamanoFigura(id2, nuevaPosicion, true));
        assertThrows(OutOfBoundsException.class, () -> cut.modificarTamanoFigura(id2, nuevaPosicion, false));
    }
}
