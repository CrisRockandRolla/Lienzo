package es.cic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CuadradoTest {

    Cuadrado cut;

    @BeforeEach
    void setUp() {
        cut = new Cuadrado(new Posicion(10, 10), 10, "rojo");
    }

    @Test
    void cambiarTamanoValidoTest() {
        cut.cambiarTamano(20);
        assertThat(cut.getLado(), is(20));
    }

    @Test
    void cambiarTamanoNoValidoTest() {
        assertThrows(RuntimeException.class, () -> cut.cambiarTamano(-20));
    }
}