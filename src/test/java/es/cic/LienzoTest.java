package es.cic;

import es.cic.excepciones.IdNotFoundException;
import es.cic.excepciones.NoSuchElementException;
import es.cic.excepciones.OutOfBoundsException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LienzoTest {

    private Lienzo lienzo;
    private static final String nombreArchivo = "figuras.txt";
    private final String id = "Figura001";

    @BeforeEach
    void setUp() throws IOException {
        lienzo = new Lienzo(nombreArchivo);
    }

    @Test
    void testAgregar_DeberiaAgregarFigura() {
        AbstractFigura figura = mock(Punto.class);
        when(figura.estaDentroLimites(id)).thenReturn(true);
        lienzo.agregar(id, figura);
        assertTrue(lienzo.existeId(id));
    }

    @Test
    void testAgregarLinea_DeberiaAgregarFigura() {
        AbstractFigura figura = mock(Linea.class);
        when(figura.estaDentroLimites(id)).thenReturn(true);
        lienzo.agregar(id, figura);
        assertTrue(lienzo.existeId(id));
    }

    @Test
    void testAgregar_IdExiste_DeberiaLanzarExcepcion() throws IOException {
        AbstractFigura figura1 = mock(Punto.class);
        when(figura1.estaDentroLimites(id)).thenReturn(true);

        AbstractFigura figura2 = mock(Linea.class);
        when(figura2.estaDentroLimites(id)).thenReturn(true);

        lienzo.agregar(id, figura1);

        assertThrows(IdNotFoundException.class, () -> lienzo.agregar(id, figura2));

    }

    @Test
    void testAgregar_FueraLimites_DeberiaLanzarExcepcion() throws IOException {
        AbstractFigura figura1 = mock(Punto.class);
        when(figura1.estaDentroLimites(id)).thenReturn(false);

        assertThrows(OutOfBoundsException.class, () -> lienzo.agregar(id, figura1));

    }

    @Test
    void testModificarPosicion_DeberiaCambiarPosicion() {
        AbstractFigura figura = mock(Punto.class);
        when(figura.estaDentroLimites(id)).thenReturn(true);
        lienzo.agregar(id, figura);

        Posicion nuevaPosicion = new Posicion(-20, 20);
        lienzo.modificarPosicionFigura(id, nuevaPosicion, true);

        verify(figura, times(1)).mover(nuevaPosicion, true);
    }

    @Test
    void testModificarPosicionLinea_DeberiaCambiarPosicion() {
        AbstractFigura figura = mock(Linea.class);
        when(figura.estaDentroLimites(id)).thenReturn(true);
        lienzo.agregar(id, figura);
        Posicion nuevaPosicion = new Posicion(20, 20);

        lienzo.modificarPosicionFigura(id, nuevaPosicion, false);
        verify(figura, times(1)).mover(nuevaPosicion, false);
    }

    @Test
    public void ModificarPosicion_NoDeberiaCambiarPosicion() {
        AbstractFigura figura = mock(Punto.class);
        when(figura.estaDentroLimites(id)).thenReturn(true);
        lienzo.agregar(id, figura);

        Posicion nuevaPosicion = new Posicion(-20, 20);
        doThrow(OutOfBoundsException.class).when(figura).mover(nuevaPosicion);
        assertThrows(OutOfBoundsException.class, () -> figura.mover(nuevaPosicion));
    }

    @Test
    public void ModificarPosicionLinea_NoDeberiaCambiarPosicion() {
        AbstractFigura figura = mock(Linea.class);
        when(figura.estaDentroLimites(id)).thenReturn(true);
        lienzo.agregar(id, figura);

        Posicion nuevaPosicion = new Posicion(20, 20);
        doThrow(OutOfBoundsException.class).when(figura).mover(nuevaPosicion);
        assertThrows(OutOfBoundsException.class, () -> figura.mover(nuevaPosicion));
    }


    @Test
    void testModificarTamano_FiguraValida_DeberiaCambiarTamano() {
        Cuadrado cuadrado = mock(Cuadrado.class);
        when(cuadrado.estaDentroLimites(id)).thenReturn(true);
        lienzo.agregar(id, cuadrado);

        when(cuadrado.esValida(cuadrado.getLado())).thenReturn(true);
        lienzo.modificarTamanoFigura(id, 40);

        verify(cuadrado).cambiarTamano(cuadrado.getLado());
    }

    @Test
    void testModificarTamano_LongitudNoValida_DeberiaLanzarExcepcion() {
        AbstractFigura figura = mock(Cuadrado.class);
        when(figura.estaDentroLimites(id)).thenReturn(true);
        lienzo.agregar(id, figura);

        assertThrows(RuntimeException.class, () -> lienzo.modificarTamanoFigura(id, -40));
    }


    @Test
    void testEliminarFigura_DeberiaEliminarFigura() {
        AbstractFigura figura = mock(Punto.class);
        when(figura.estaDentroLimites(id)).thenReturn(true);
        lienzo.agregar(id, figura);

        lienzo.eliminarFigura(id);

        assertFalse(lienzo.existeId(id));
    }

    @Test
    void testEliminarFigura_IdNoExiste_DeberiaLanzarExcepcion() {

        assertThrows(NoSuchElementException.class, () -> lienzo.eliminarFigura(id));
    }

    @Test
    public void generarFicheroTest() throws IOException {
        lienzo.agregar(id, new Circulo(new Posicion(10, 10), 5, "verde"));
        int lineasFichero = lienzo.guardarFiguras(nombreArchivo);
        Assertions.assertEquals(lienzo.getFiguras().size(), lineasFichero);
    }

    @Test
    public void cargarFicheroTest() throws IOException {
        Map<String, AbstractFigura> figuras = lienzo.cargarFiguras(nombreArchivo);
        Assertions.assertEquals(1, figuras.size());
    }

}
