package es.cic;


import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import es.cic.excepciones.IdNotFoundException;
import es.cic.excepciones.NoSuchElementException;
import es.cic.excepciones.OutOfBoundsException;
import org.apache.commons.io.FilenameUtils;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Lienzo {
    private static int ancho = 1000000;
    private static int alto = 1000000;
    private final Map<String, AbstractFigura> figuras;

    public Lienzo(String nombreArchivo) throws IOException {
        figuras = new HashMap<>();
        if (Files.exists(Paths.get(nombreArchivo))) cargarFiguras(nombreArchivo);
    }

    public void agregar(String id, AbstractFigura figura) {
        if (!existeId(id) && figura.estaDentroLimites(id)) {
            figuras.put(id, figura);
        } else {
            if (existeId(id)) {
                throw new IdNotFoundException("Ya existe una figura con el id " + id);
            } else {
                throw new OutOfBoundsException("Posicion " + figura.getPosicion() + " no válida.");
            }
        }
    }

    public void eliminarFigura(String id) {
        if (existeId(id)) {
            figuras.remove(id);
        } else {
            throw new NoSuchElementException("La figura con id " + id + " no existe");
        }
    }

    public void modificarPosicionFigura(String id, Posicion nuevaPosicion) {
        modificarPosicionFigura(id, nuevaPosicion, true);
    }

    public void modificarPosicionFigura(String id, Posicion nuevaPosicion, boolean isPuntoAplicacion) {
        if (existeId(id)) {
            AbstractFigura figura = figuras.get(id);
            figura.mover(nuevaPosicion, isPuntoAplicacion);
        } else {
            throw new NoSuchElementException("La figura con id " + id + " no existe");
        }
    }

    public void modificarTamanoFigura(String id, int longitud) {
        if (existeId(id)) {
            AbstractFigura figura = figuras.get(id);
            figura.cambiarTamano(longitud);
        } else {
            throw new NoSuchElementException("La figura con id " + id + " no existe");
        }
    }

    public void modificarTamanoFigura(String id, Posicion nuevaPosicion, boolean isPuntoAplicacion) {
        modificarPosicionFigura(id, nuevaPosicion, isPuntoAplicacion);
    }

    public int guardarFiguras(String csvFilePath) throws IOException {
        try (CSVWriter writer = new CSVWriter(new FileWriter(FilenameUtils.normalize(csvFilePath)))) {
            for (Map.Entry<String, AbstractFigura> entry : figuras.entrySet()) {
                String id = entry.getKey();
                AbstractFigura figura = entry.getValue();
                String[] data = null;
                switch (figura.getClass().getSimpleName()) {
                    case "Punto":
                        data = new String[]{id, figura.getClass().getSimpleName(), figura.getPosicion() + "", figura.getColor()};
                        break;
                    case "Linea":
                        data = new String[]{id, figura.getClass().getSimpleName(), figura.getPosicion() + "", figura.getColor(), figura.getPosicion2().getCoordenadaX() + "", figura.getPosicion2().getCoordenadaY() + ""};
                        break;
                    case "Cuadrado":
                        data = new String[]{id, figura.getClass().getSimpleName(), figura.getPosicion() + "", figura.getColor(), ((Cuadrado) figura).getLado() + ""};
                        break;
                    case "Circulo":
                        data = new String[]{id, figura.getClass().getSimpleName(), figura.getPosicion() + "", figura.getColor(), ((Circulo) figura).getRadio() + ""};
                        break;
                    default:
                        throw new UnsupportedOperationException("Caso no válido");
                }
                writer.writeNext(data);
            }
        }
        return figuras.size();
    }

    public Map<String, AbstractFigura> cargarFiguras(String csvFilePath) {
        Map<String, AbstractFigura> figuras = new HashMap<>();

        try (CSVReader reader = new CSVReader(new FileReader(csvFilePath))) {
            String[] nextLine;

            while ((nextLine = reader.readNext()) != null) {
                String id = nextLine[0];
                String figuraType = nextLine[1];
                int x = Integer.parseInt(nextLine[2]);
                int y = Integer.parseInt(nextLine[3]);
                String color = nextLine[4];

                AbstractFigura figura;
                switch (figuraType) {
                    case "Punto":
                        figura = new Punto(new Posicion(x, y), color);
                        break;
                    case "Linea":
                        int x2 = Integer.parseInt(nextLine[5]);
                        int y2 = Integer.parseInt(nextLine[6]);
                        figura = new Linea(new Posicion(x, y), new Posicion(x2, y2), color);
                        break;
                    case "Cuadrado":
                        int lado = Integer.parseInt(nextLine[5]);
                        figura = new Cuadrado(new Posicion(x, y), lado, color);
                        break;
                    case "Circulo":
                        int radio = Integer.parseInt(nextLine[5]);
                        figura = new Circulo(new Posicion(x, y), radio, color);
                        break;
                    default:
                        throw new UnsupportedOperationException("Operación no válida");
                }

                figuras.put(id, figura);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        }
        return figuras;
    }


    public boolean existeId(String id) {
        return figuras.containsKey(id);
    }

//    private boolean isDuplicado(Figura figura){
//        List<AbstractFigura> fig = new ArrayList<>();
//        fig.add(new Punto(new Posicion(10,10),"rojo"));
//        fig.stream()
//           .filter(i->i.getX()==figura.getX())
//           .findFirst()
//           .orElseThrow(() -> new java.util.NoSuchElementException("Figura no encontrado con posicion: " + figura.getX()));
//
//        return  false;
//    }


    public Map<String, AbstractFigura> getFiguras() {
        return figuras;
    }

    public static int getAncho() {
        return ancho;
    }

    public static void setAncho(int ancho) {
        Lienzo.ancho = ancho;
    }

    public static int getAlto() {
        return alto;
    }

    public static void setAlto(int alto) {
        Lienzo.alto = alto;
    }

}