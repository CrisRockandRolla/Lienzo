package es.cic;

import es.cic.excepciones.NoSuchElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LienzoTest {

    private GestorFicheros<Map<String, AbstractFigura>> gestorFicheros;
    private Lienzo lienzo;

    @BeforeEach
    void setUp() throws IOException {
        gestorFicheros = mock(GestorFicheros.class);
        lienzo = new Lienzo("figuras.txt");
    }

    @Test
    void testAgregarFigura_ValidFigure_DeberiaAgregarFigura() {
        String id = "circulo001";
        AbstractFigura figura = mock(Punto.class);
        when(figura.estaDentroLimites(id)).thenReturn(true);

        lienzo.agregarFigura(id, figura);

        // Assert
        assertTrue(lienzo.existeId(id));
    }

    @Test
    void testAgregarFigura_IdExiste_DeberiaLanzarExcepcion() throws IOException {

        String id = "Punto01";
        AbstractFigura figura1 = mock(Punto.class);
        when(figura1.estaDentroLimites(id)).thenReturn(true);

        AbstractFigura figura2 = mock(Linea.class);
        when(figura2.estaDentroLimites(id)).thenReturn(true);

        Map<String, AbstractFigura> figuras = new HashMap<>();
        figuras.put(id, figura1);
        when(gestorFicheros.cargarFiguras()).thenReturn(figuras);
        lienzo = new Lienzo("figuras.txt");

        assertThrows(NoSuchElementException.class, () -> lienzo.agregarFigura(id, figura2));

    }

    @Test
    void testModificarPosicion_FiguraValida_DeberiaCambiarPosicion() {

        String id = "Linea01";
        AbstractFigura figura = mock(Punto.class);
        when(figura.estaDentroLimites(id)).thenReturn(true);
        lienzo.agregarFigura(id, figura);
        Posicion nuevaPosicion = new Posicion(20, 20);


        lienzo.modificarPosicion(id, nuevaPosicion);


        verify(figura).mover(nuevaPosicion, true);
    }

    @Test
    void testEliminarFigura_FiguraValida_DeberiaEliminarFigura() {

        String id = "linea01";
        AbstractFigura figura = mock(Punto.class);
        when(figura.estaDentroLimites(id)).thenReturn(true);
        lienzo.agregarFigura(id, figura);


        lienzo.eliminarFigura(id);


        assertFalse(lienzo.existeId(id));
    }

    @Test
    void testModificarTamano_FiguraValida_DeberiaCambiarTamano() {
        String id = "Cuadrado01";
        AbstractFigura figura = mock(Cuadrado.class);
        when(figura.estaDentroLimites(id)).thenReturn(true);
        lienzo.agregarFigura(id, figura);
        int newLength = 30;

        lienzo.modificarTamano(id, newLength);

        verify(figura).cambiarTamano(newLength);
    }

}
