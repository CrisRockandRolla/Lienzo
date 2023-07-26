package es.cic;

import es.cic.excepciones.IdNotFoundException;
import es.cic.excepciones.NoSuchElementException;
import es.cic.excepciones.OutOfBoundsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LienzoTest {

    private Lienzo lienzo;
    private static final String nombreArchivo = "figuras.txt";
    private final String id = "Punto001";
    private final String id3 = "Circulo003";
    private final String id4 = "Linea004";

    @BeforeEach
    void setUp() {
        lienzo = new Lienzo(nombreArchivo);
    }

    @Test
    void testAgregar_DeberiaAgregarFigura() {
        AbstractFigura punto = new Punto(new Posicion(10, 10), "rojo");
        lienzo.agregar(id, punto);
        assertTrue(lienzo.existeId(id));

        AbstractFigura cuadrado = new Cuadrado(new Posicion(10, 10), 5, "rojo");
        String id2 = "Cuadrado002";
        lienzo.agregar(id2, cuadrado);
        assertTrue(lienzo.existeId(id2));

        AbstractFigura circulo = new Circulo(new Posicion(10, 10), 5, "rojo");
        lienzo.agregar(id3, circulo);
        assertTrue(lienzo.existeId(id3));

        AbstractFigura linea = new Linea(new Posicion(10, 10), new Posicion(10, 0), "rojo");
        lienzo.agregar(id4, linea);
        assertTrue(lienzo.existeId(id4));
    }

    @Test
    void testAgregar_IdExiste_DeberiaLanzarExcepcion() {
        AbstractFigura punto = new Punto(new Posicion(10, 10), "rojo");
        lienzo.agregar(id, punto);

        assertThrows(IdNotFoundException.class, () -> lienzo.agregar(id, punto));
    }

    @Test
    void testAgregar_FueraLimites_DeberiaLanzarExcepcion() {
        AbstractFigura punto = new Punto(new Posicion(-10, 10), "rojo");
        assertThrows(OutOfBoundsException.class, () -> lienzo.agregar(id, punto));

        AbstractFigura linea = new Linea(new Posicion(10, 10), new Posicion(-50, 50), "rojo");
        assertThrows(OutOfBoundsException.class, () -> lienzo.agregar(id4, linea));

        AbstractFigura linea2 = new Linea(new Posicion(-10, 10), new Posicion(50, 50), "rojo");
        assertThrows(OutOfBoundsException.class, () -> lienzo.agregar(id4, linea2));
    }

    @Test
    void testAgregar_TamanoNoValido_DeberiaLanzarExcepcion() {
        assertThrows(RuntimeException.class, () -> new Cuadrado(new Posicion(10, 10), -5, "rojo"));
        assertThrows(RuntimeException.class, () -> new Circulo(new Posicion(10, 10), -5, "rojo"));
    }

    @Test
    void testEliminarFigura_DeberiaEliminarFigura() {
        AbstractFigura punto = new Punto(new Posicion(10, 10), "rojo");
        lienzo.agregar(id, punto);

        lienzo.eliminarFigura(id);
        assertFalse(lienzo.existeId(id));
    }

    @Test
    void testEliminarFigura_IdNoExiste_DeberiaLanzarExcepcion() {
        assertThrows(NoSuchElementException.class, () -> lienzo.eliminarFigura(id));
    }

    @Test
    void testModificarPosicion_DeberiaModificarFigura() {
        AbstractFigura circulo = new Circulo(new Posicion(10, 10), 5, "rojo");
        lienzo.agregar(id, circulo);
        Posicion nuevaPosicion = new Posicion(20, 20);
        lienzo.modificarPosicionFigura(id, nuevaPosicion);
        assertThat(circulo.getPosicion(), is(nuevaPosicion));

        AbstractFigura linea = new Linea(new Posicion(10, 10), new Posicion(0, 0), "rojo");
        lienzo.agregar(id4, linea);
        lienzo.modificarPosicionFigura(id4, nuevaPosicion);
        assertThat(linea.getPosicion(), is(nuevaPosicion));

        lienzo.modificarPosicionFigura(id4, nuevaPosicion, false);
        assertThat(linea.getPosicion2(), is(nuevaPosicion));
    }

    @Test
    public void testModificarPosicion_FueraLimites_DeberiaLanzarExcepcion() {
        AbstractFigura cuadrado = new Cuadrado(new Posicion(10, 10), 10, "rojo");
        lienzo.agregar(id, cuadrado);
        Posicion nuevaPosicion = new Posicion(-20, 20);
        assertThrows(OutOfBoundsException.class, () -> lienzo.modificarPosicionFigura(id, nuevaPosicion));

        AbstractFigura linea = new Linea(new Posicion(10, 10), new Posicion(0, 0), "rojo");
        lienzo.agregar(id4, linea);
        assertThrows(OutOfBoundsException.class, () -> lienzo.modificarPosicionFigura(id4, nuevaPosicion));

        assertThrows(OutOfBoundsException.class, () -> lienzo.modificarPosicionFigura(id4, nuevaPosicion, false));
    }

    @Test
    public void testModificarPosicion_IdNoExiste_DeberiaLanzarExcepcion() {
        Posicion nuevaPosicion = new Posicion(-20, 20);
        assertThrows(NoSuchElementException.class, () -> lienzo.modificarPosicionFigura(id, nuevaPosicion));

        assertThrows(NoSuchElementException.class, () -> lienzo.modificarPosicionFigura(id4, nuevaPosicion));

        assertThrows(NoSuchElementException.class, () -> lienzo.modificarPosicionFigura(id4, nuevaPosicion, false));
    }

    @Test
    void testModificarTamano() {
        Cuadrado cuadrado = mock(Cuadrado.class);
        when(cuadrado.estaDentroLimites(id)).thenReturn(true);
        lienzo.agregar(id, cuadrado);

        when(cuadrado.esValida(cuadrado.getLado())).thenReturn(true);
        lienzo.modificarTamanoFigura(id, 40);

        verify(cuadrado).cambiarTamano(cuadrado.getLado());
    }

    @Test
    public void generarFicheroTest() throws IOException {
        lienzo.agregar(id3, new Circulo(new Posicion(10, 10), 5, "verde"));
        int lineasFichero = lienzo.guardarFiguras(nombreArchivo);
        assertEquals(lienzo.getFiguras().size(), lineasFichero);
    }

    @Test
    public void cargarFicheroTest() {
        Map<String, AbstractFigura> figuras = lienzo.cargarFiguras(nombreArchivo);
        assertEquals(1, figuras.size());
    }

}
