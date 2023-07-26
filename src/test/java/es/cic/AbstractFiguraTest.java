package es.cic;

import es.cic.excepciones.OutOfBoundsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AbstractFiguraTest {
    private AbstractFigura figura;

    @BeforeEach
    void setUp() {
        figura = new Cuadrado(new Posicion(10, 100), 3, "rojo");
    }

    /*
    =========================================================
                         MOVER FIGURA
    =========================================================
     */
    @Test
    void setPosicion_DeberiaModificarPosicionDeLaFigura() {
        Posicion nuevaPosicionOk = new Posicion(50, 50);
        figura.mover(nuevaPosicionOk);
        assertThat(figura.getPosicion(), is(nuevaPosicionOk));
    }

    @Test
    void setPosicion_DeberiaNoModificarPosicionDeLaFigura() {
        Posicion nuevaPosicionKo = new Posicion(-10, 10);
        assertThrows(OutOfBoundsException.class, () -> figura.mover(nuevaPosicionKo));
    }

    @Test
    void setPosicionLinea_DeberiaModificarPosicionDeLaFigura() {
        Posicion posicion = new Posicion(0, 0);
        Posicion posicion2 = new Posicion(10, 10);
        AbstractFigura figura = new Linea(posicion, posicion2, "rojo");

        Posicion nuevaPosicionOk = new Posicion(50, 50);
        figura.mover(nuevaPosicionOk, true);
        assertThat(figura.getPosicion(), is(nuevaPosicionOk));

        figura.mover(nuevaPosicionOk, true);
        assertThat(figura.getPosicion(), is(nuevaPosicionOk));

        figura.mover(nuevaPosicionOk, false);
        assertThat(figura.getPosicion2(), is(nuevaPosicionOk));
    }

    @Test
    void setPosicionLinea_DeberiaNoModificarPosicionDeLaFigura() {
        Posicion posicion = new Posicion(0, 0);
        Posicion posicion2 = new Posicion(10, 10);
        AbstractFigura figura = new Linea(posicion, posicion2, "rojo");

        Posicion nuevaPosicionKo = new Posicion(-50, 50);
        assertThrows(OutOfBoundsException.class, () -> figura.mover(nuevaPosicionKo, true));
        assertThrows(OutOfBoundsException.class, () -> figura.mover(nuevaPosicionKo, false));
    }

    /*
    =========================================================================
                                CAMBIAR TAMAÑO
    =========================================================================
     */

    @Test
    void setTamano_DeberiaModificarTamanoDeLaFigura() {
        figura.cambiarTamano(10);
        assertThat(((Cuadrado) figura).getLado(), is(10));
    }

    @Test
    void setTamano_NoDeberiaModificarTamanoDeLaFigura() {
        assertThrows(RuntimeException.class, () -> figura.cambiarTamano(-10));
    }

    @Test
    void setTamanoPunto_NoDeberiaModificarTamanoDeLaFigura() {
        Posicion posicion = new Posicion(0, 0);
        AbstractFigura figura = new Punto(posicion, "azul");
        assertThrows(RuntimeException.class, () -> figura.cambiarTamano(-10));
        AbstractFigura figura2 = new Linea(posicion, new Posicion(100, 100), "azul");
        assertThrows(RuntimeException.class, () -> figura2.cambiarTamano(10));
    }

    @Test
    void setTamanoLinea_DeberiaModificarTamanoDeLaFigura() {
        Posicion posicion = new Posicion(1, 1);
        Posicion posicion2 = new Posicion(100, 100);
        AbstractFigura figura = new Linea(posicion, posicion2, "verde");

        Posicion nuevaPosicionOk = new Posicion(50, 50);
        figura.mover(nuevaPosicionOk, true);
        assertThat(figura.getPosicion(), is(nuevaPosicionOk));

        figura.mover(nuevaPosicionOk, false);
        assertThat(figura.getPosicion2(), is(nuevaPosicionOk));
    }

    @Test
    void setTamanoLinea_NoDeberiaModificarTamanoDeLaFigura() {
        Posicion posicion = new Posicion(1, 1);
        Posicion posicion2 = new Posicion(100, 100);
        AbstractFigura figura = new Linea(posicion, posicion2, "verde");
        Posicion nuevaPosicionKo = new Posicion(-50, 50);
        assertThrows(OutOfBoundsException.class, () -> figura.mover(nuevaPosicionKo, true));
        assertThrows(OutOfBoundsException.class, () -> figura.mover(nuevaPosicionKo, false));
    }

}
