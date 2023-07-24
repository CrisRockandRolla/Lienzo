package es.cic;

import es.cic.excepciones.OutOfBoundsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AbstractFiguraTest {
    private Posicion posicion, posicion2, nuevaPosicionOk, nuevaPosicionKo;
    private final String color = "rojo";
    private final int nuevaLongitud = 20;
    private Cuadrado cuadrado;

    @BeforeEach
    void setUp() {

        posicion = mock(Posicion.class);
        when(posicion.getCoordenadaX()).thenReturn(10);
        when(posicion.getCoordenadaY()).thenReturn(20);

        cuadrado = new Cuadrado(posicion, 10, color);

        posicion2 = mock(Posicion.class);
        when(posicion.getCoordenadaX()).thenReturn(50);
        when(posicion.getCoordenadaY()).thenReturn(50);

        nuevaPosicionOk = mock(Posicion.class);
        when(nuevaPosicionOk.getCoordenadaX()).thenReturn(20);
        when(nuevaPosicionOk.getCoordenadaY()).thenReturn(50);

        nuevaPosicionKo = mock(Posicion.class);
        when(nuevaPosicionKo.getCoordenadaX()).thenReturn(-20);
        when(nuevaPosicionKo.getCoordenadaY()).thenReturn(50);
    }

    /*
    =========================================================
                         MOVER FIGURA
    =========================================================
     */
    @Test
    void setPosicion_DeberiaModificarPosicionDeLaFigura() {
        AbstractFigura figura = new Punto(posicion, color);
        figura.mover(nuevaPosicionOk);
        assertEquals(nuevaPosicionOk, figura.getPosicion());
    }

    @Test
    void setPosicion_DeberiaNoModificarPosicionDeLaFigura() {
        AbstractFigura figura = new Punto(posicion, color);
        assertThrows(OutOfBoundsException.class, () -> figura.mover(nuevaPosicionKo));
    }

    @Test
    void setPosicionLinea_DeberiaModificarPosicionDeLaFigura() {
        AbstractFigura figura = new Linea(posicion, posicion2, color);
        figura.mover(nuevaPosicionOk, true);
        assertEquals(nuevaPosicionOk, figura.getPosicion());
        figura.mover(nuevaPosicionOk, false);
        assertEquals(nuevaPosicionOk, figura.getPosicion2());
    }

    @Test
    void setPosicionLinea_DeberiaNoModificarPosicionDeLaFigura() {
        AbstractFigura figura = new Linea(posicion, posicion2, color);
        assertThrows(OutOfBoundsException.class, () -> figura.mover(nuevaPosicionKo, true));
        assertThrows(OutOfBoundsException.class, () -> figura.mover(nuevaPosicionKo, false));
    }

    /*
    =========================================================================
                         FALTAN LOS DE CAMBIAR TAMAÑO
    =========================================================================
     */

    @Test
    void setTamano_DeberiaModificarTamanoDeLaFigura() {
        cuadrado.cambiarTamano(nuevaLongitud);
        assertEquals(nuevaLongitud, cuadrado.getLado());
    }

    @Test
    void setTamano_NoDeberiaModificarTamanoDeLaFigura() {
        assertThrows(RuntimeException.class, () -> cuadrado.cambiarTamano(-nuevaLongitud));
    }

    @Test
    void setTamanoPunto_NoDeberiaModificarTamanoDeLaFigura() {
        AbstractFigura figura = new Punto(posicion, color);
        assertThrows(RuntimeException.class, () -> figura.cambiarTamano(nuevaLongitud));
    }

    @Test
    void setTamanoLinea_DeberiaModificarTamanoDeLaFigura() {
        AbstractFigura figura = new Linea(posicion, posicion2, color);

        figura.mover(nuevaPosicionOk, true);
        assertEquals(nuevaPosicionOk, figura.getPosicion());

        figura.mover(nuevaPosicionOk, false);
        assertEquals(nuevaPosicionOk, figura.getPosicion2());
    }

    @Test
    void setTamanoLinea_NoDeberiaModificarTamanoDeLaFigura() {
        AbstractFigura figura = new Linea(posicion, posicion2, color);

        assertThrows(OutOfBoundsException.class, () -> figura.mover(nuevaPosicionKo, true));
        assertThrows(OutOfBoundsException.class, () -> figura.mover(nuevaPosicionKo, false));
    }

}
