package es.cic;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public class GestorFicheros<X> {
    private File fichero;


    private static final ScheduledExecutorService pool = Executors.newScheduledThreadPool(1);

    public GestorFicheros(File fichero) {
        this.fichero = fichero;
    }

    public void guardarFiguras(X obj) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(Files.newOutputStream(fichero.toPath()))) {
            outputStream.writeObject(obj);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public X cargarFiguras()  {
        if (!fichero.exists()) {
            try {
                fichero.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        X resultado = null;
        try (ObjectInputStream inputStream = new ObjectInputStream(Files.newInputStream(fichero.toPath()))) {
            resultado = (X) inputStream.readObject();
        } catch (Exception e) {

        }
        return resultado;
    }

    public void autoGuardado (Supplier<X> supplier) {
        pool.scheduleAtFixedRate(()->guardarFiguras(supplier.get()),0,2, TimeUnit.SECONDS);
    }

    public static void main() {
        String ruta = "ruta_del_fichero/nombre_del_fichero.txt";

        Path filePath = Paths.get(ruta);

        try {
            Files.createFile(filePath);
        } catch (IOException e) {
            System.out.println("Ocurrió un error al crear el fichero: " + e.getMessage());
        }
    }
}