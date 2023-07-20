package es.cic;

import es.cic.excepciones.OutOfBoundsException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AbstractFiguraTest {
//    @Test
//    void crearFigura_DeberiaCrearFiguraConCoordenadasYColorCorrectos() {
//        int x = 10;
//        int y = 20;
//        String color = "rojo";
//
//        Figura figura = new Punto(x, y, color);
//
//        assertEquals(x, figura.getX());
//        assertEquals(y, figura.getY());
//        assertEquals(color, figura.getColor());
//    }

    @Test
    void setPosicion_DeberiaModificarPosicionDeLaFigura() {
        Posicion posicion = new Posicion(10,20);

        Posicion nuevaPosicion = new Posicion(20,50);
        String color = "rojo";
        AbstractFigura figura = new Punto(posicion, color);

        figura.mover(nuevaPosicion);

        assertEquals(nuevaPosicion, figura.getPosicion());
    }

    @Test
    void setPosicion_DeberiaNoModificarPosicionDeLaFigura() {
        Posicion posicion = new Posicion(10,20);

        Posicion nuevaPosicion = new Posicion(-20,50);
        String color = "rojo";
        AbstractFigura figura = new Punto(posicion, color);

        assertThrows(OutOfBoundsException.class, () -> figura.mover(nuevaPosicion));
    }

    @Test
    void setPosicionLinea_DeberiaModificarPosicionDeLaFigura() {
        Posicion posicion1 = new Posicion(10,10);
        Posicion posicion2 = new Posicion(20,20);
        Posicion nuevaPosicion1 = new Posicion(20,50);
        Posicion nuevaPosicion2 = new Posicion(35,27);
        AbstractFigura figura = new Linea(posicion1,posicion2,"verde");
        figura.mover(nuevaPosicion1,true);
        assertEquals(nuevaPosicion1, figura.getPosicion());
        figura.mover(nuevaPosicion2,false);
        assertEquals(nuevaPosicion2, figura.getPosicion2());


    }

    @Test
    void setPosicionLinea_DeberiaNoModificarPosicionDeLaFigura() {
        Posicion nuevaPosicion3 = new Posicion(-35,27);

        AbstractFigura figura = new Linea(new Posicion(10,10),new Posicion(20,20),"verde");
        assertThrows(OutOfBoundsException.class, () -> figura.mover(nuevaPosicion3,true));
        assertThrows(OutOfBoundsException.class, () -> figura.mover(nuevaPosicion3,false));
    }


    @Test
    void pruebaTest() throws IOException, InterruptedException {
        Lienzo lienzo = new Lienzo("figuras.txt");

        Thread.sleep(5000);
        lienzo.agregarFigura("Punto01",new Punto(new Posicion(10,10),"rojo"));
        Thread.sleep(5000);


//        assertThrows(OutOfBoundsException.class, () -> figura.mover(nuevaPosicion));
    }

}
