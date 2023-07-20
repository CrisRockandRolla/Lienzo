package es.cic;


import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import es.cic.excepciones.IdNotFoundException;
import es.cic.excepciones.NoSuchElementException;
import es.cic.excepciones.OutOfBoundsException;

import java.io.*;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Lienzo {
    private static int ancho = 1000000;
    private static int alto = 1000000;
//    private GestorFicheros<Map<String,AbstractFigura>> gestorFicheros;
    private Map<String, AbstractFigura> figuras;

    public Lienzo(String nombreArchivo) throws IOException {
        figuras = new HashMap<>();
        File archivo = new File(nombreArchivo);
//      gestorFicheros = new GestorFicheros<>(archivo);

        figuras = cargarFigurasDesdeCSV("figuras.txt");
        if (figuras==null) figuras = new HashMap<>();


//        gestorFicheros.autoGuardado(()->{
//            Map<String, AbstractFigura> figuras1 = getFiguras();
//            System.out.println("Vuelta:");
//            figuras1.forEach((k,v)-> System.out.println(k+"-"+v));
//            return figuras1;
//        });
    }

    public void agregarFigura(String id, AbstractFigura figura) {
        if (!existeId(id) && figura.estaDentroLimites(id)) {
            figuras.put(id, figura);
        } else {
            if (existeId(id)){
                throw new NoSuchElementException("Ya existe una figura con el id " + id);
            }else{
                throw new OutOfBoundsException("Posicion "+figura.getX()+","+figura.getY()+" no válida.");
            }
        }
    }

    public void eliminarFigura(String id) {
        if (existeId(id)) {
            figuras.remove(id);
        } else {
            throw new IdNotFoundException("La figura con id " + id + " no existe");
        }
    }

    public void modificarPosicion(String id, Posicion nuevaPosicion) {
       modificarPosicion(id,nuevaPosicion,true);
    }

    public void modificarPosicion(String id, Posicion nuevaPosicion, boolean isPuntoAplicacion) {
        if (existeId(id)) {
            AbstractFigura figura = figuras.get(id);
            figura.mover(nuevaPosicion,isPuntoAplicacion);
        } else {
            throw new IdNotFoundException("La figura con id " + id + " no existe");
        }
    }

    public void modificarTamano(String id,int longitud) {
        if (existeId(id)) {
            AbstractFigura figura = figuras.get(id);
            figura.cambiarTamano(longitud);
        } else {
            throw new IdNotFoundException("La figura con id " + id + " no existe");
        }
    }

    public void modificarTamano(String id, Posicion nuevaPosicion, boolean isPuntoAplicacion) {
        modificarPosicion(id, nuevaPosicion, isPuntoAplicacion);
    }


    public void guardarFigurasEnCSV(String csvFilePath) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(csvFilePath))) {
            for (Map.Entry<String, AbstractFigura> entry : figuras.entrySet()) {
                String id = entry.getKey();
                AbstractFigura figura = entry.getValue();
                String[] data = new String[]{id, figura.getClass().getSimpleName(), figura.getX() + "", figura.getY() + "", figura.getColor()};
                writer.writeNext(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public Map<String, AbstractFigura> cargarFigurasDesdeCSV(String csvFilePath) {
        Map<String, AbstractFigura> loadedFiguras = new HashMap<>();

        try (CSVReader reader = new CSVReader(new FileReader(csvFilePath))) {
            String[] nextLine;

            while ((nextLine = reader.readNext()) != null) {
                String id = nextLine[0];
                String figuraType = nextLine[1];
                int x = Integer.parseInt(nextLine[2]);
                int y = Integer.parseInt(nextLine[3]);
                String color = nextLine[4];

                AbstractFigura figura = null;

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
                    // Add more cases for other figure types if needed
                    default:
                        // Handle unknown figure types or errors
                        break;
                }

                if (figura != null) {
                    loadedFiguras.put(id, figura);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        }

        return loadedFiguras;
    }


    public boolean existeId(String id) {
        return figuras.containsKey(id);
    }


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