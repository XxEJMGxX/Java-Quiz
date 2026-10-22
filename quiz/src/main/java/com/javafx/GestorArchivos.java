package com.javafx;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

// * Clase utilitaria encargada de la persistencia de datos en disco (E/S de ficheros)
public class GestorArchivos {
    // ! Rutas absolutas a los archivos de datos relocalizadas en el directorio
    // personal del usuario
    private static final String DIRECTORIO_DATOS = System.getProperty("user.home") + File.separator + "JavaQuiz";
    private static final String RUTA_PREGUNTAS = DIRECTORIO_DATOS + File.separator + "preguntas.txt";
    private static final String RUTA_PUNTUACIONES = DIRECTORIO_DATOS + File.separator + "puntuaciones.txt";

    // * Inicializa el directorio de datos y crea los archivos por defecto si no
    // existen
    public void crearArchivosIniciales() {
        File directorio = new File(DIRECTORIO_DATOS);
        // ! Asegurar la existencia física de la carpeta contenedora en disco
        if (!directorio.exists()) {
            directorio.mkdirs();
        }

        File archivoPreguntas = new File(RUTA_PREGUNTAS);
        // * Escribir base de datos inicial de preguntas (15 preguntas con formato CSV
        // separado por ';')
        if (!archivoPreguntas.exists()) {
            try (PrintWriter out = new PrintWriter(
                    new BufferedWriter(new FileWriter(RUTA_PREGUNTAS, StandardCharsets.UTF_8)))) {
                out.println(
                        "1;¿Java es lo mismo que Javascript?;Sí, JavaScript es la versión web.;No, son lenguajes totalmente independientes.;Sí, comparten el motor de ejecución.;No, Java es una extensión de HTML.;2");
                out.println(
                        "2;¿Qué herramienta es estrictamente imprescindible para EJECUTAR Java?;El JDK.;El IDE.;La JVM.;El compilador javac.;3");
                out.println(
                        "3;¿En qué sistemas operativos se puede usar o ejecutar Java?;Solo en Windows.;En cualquier sistema con una JVM compatible.;Solo en UNIX.;Solo en Android.;2");
                out.println("4;¿Cuál fue el primer nombre oficial del proyecto Java?;Oak.;Green.;C++++;Duke.;1");
                out.println(
                        "5;¿Para qué sirve principalmente la clase Scanner en Java?;Buscar virus.;Leer datos introducidos por teclado.;Compilar código.;Digitalizar imágenes.;2");
                out.println(
                        "6;¿Cuál es la instrucción para crear un objeto a partir de una clase?;class MiObjeto;new MiClase();MiClase.create();instanceof MiClase;2");
                out.println(
                        "7;¿int e Integer representan exactamente lo mismo en Java?;Sí, son idénticos.;No, int es primitivo e Integer es clase envolvente.;Sí, Integer es nomenclatura de Java 11.;No, int es para enteros y Integer para decimales.;2");
                out.println(
                        "8;Si declaramos int[] numeros = new int[5], ¿cuál es el índice del primer elemento?;1;-1;0;5;3");
                out.println(
                        "9;¿Es lo mismo un array tradicional que un ArrayList?;Sí, ambos son dinámicos.;No, el array es estático y el ArrayList dinámico.;Sí, ArrayList es para JavaFX.;No, ArrayList solo guarda números.;2");
                out.println(
                        "10;¿En qué se parecen un bucle while, un for, un foreach y un do-while?;Evalúan al principio.;Requieren contador i++.;Son estructuras iterativas cíclicas.;Se detienen con null.;3");
                out.println(
                        "11;¿Cuál es la palabra clave para instanciar directamente una clase abstracta?;abstract;new;extends;No se pueden instanciar directamente.;4");
                out.println(
                        "12;Si int a=5 y int b=5, ¿qué resulta de a + b frente a \"a\" + \"b\"?;Ambos 55.;Suma (10) y concatenación (ab).;Error de compilación.;Ambos 10.;2");
                out.println(
                        "13;¿Cómo se define correctamente una constante inmutable en Java?;const;public static final;immutable;MAYUSCULAS;2");
                out.println(
                        "14;Respecto a la declaración de variables en Java, ¿cuál es la regla?;Tipado dinámico.;Tipado estático obligatorio.;Solo en el main.;Solo envolventes.;2");
                out.println(
                        "15;¿Cómo se llama la clase obligatoria para iniciar un proyecto Java?;Main o Principal.;Cualquiera con public static void main.;System.;Application.;2");
            } catch (IOException e) {
                System.out.println("Error creando el archivo de preguntas: " + e.getMessage());
            }
        }

        File archivoPuntuaciones = new File(RUTA_PUNTUACIONES);
        // * Asegurar la existencia del registro histórico de puntuaciones
        if (!archivoPuntuaciones.exists()) {
            try {
                archivoPuntuaciones.createNewFile();
            } catch (IOException e) {
                System.out.println("Error creando el archivo de puntuaciones: " + e.getMessage());
            }
        }
    }

    // * Parsea el archivo de preguntas plano CSV y lo convierte en una lista de
    // objetos Pregunta
    public List<Pregunta> leerPreguntas() {
        List<Pregunta> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(RUTA_PREGUNTAS, StandardCharsets.UTF_8))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";");
                // ? Validamos que la línea contiene exactamente los 7 componentes esperados
                if (partes.length == 7) {
                    String enunciado = partes[1];
                    String opcion1 = partes[2];
                    String opcion2 = partes[3];
                    String opcion3 = partes[4];
                    String opcion4 = partes[5];
                    // ! Aplicamos trim() en el índice para evitar saltos de línea (\r) en Windows
                    int correcta = Integer.parseInt(partes[6].trim());
                    lista.add(new Pregunta(enunciado, opcion1, opcion2, opcion3, opcion4, correcta));
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error al leer las preguntas: " + e.getMessage());
        }
        return lista;
    }

    // * Registra una puntuación histórica agregándola al final del archivo (modo
    // append)
    public void guardarPuntuacion(String jugador, int puntuacion, int errores) {
        try (PrintWriter out = new PrintWriter(
                new BufferedWriter(new FileWriter(RUTA_PUNTUACIONES, StandardCharsets.UTF_8, true)))) {
            out.println("Jugador: " + jugador + " | Aciertos: " + puntuacion + " | Errores: " + errores);
        } catch (IOException e) {
            System.out.println("Error guardando la puntuación: " + e.getMessage());
        }
    }

    // * Retorna el contenido íntegro formateado de todas las puntuaciones
    // registradas
    public String obtenerHistorialPuntuaciones() {
        StringBuilder sb = new StringBuilder();
        File archivo = new File(RUTA_PUNTUACIONES);
        if (!archivo.exists())
            return "No hay registros de puntuación todavía.";

        try (BufferedReader br = new BufferedReader(new FileReader(archivo, StandardCharsets.UTF_8))) {
            String linea;
            int contador = 1;
            while ((linea = br.readLine()) != null) {
                sb.append(contador).append(") ").append(linea).append("\n");
                contador++;
            }
        } catch (IOException e) {
            return "Error al leer el historial.";
        }

        return sb.length() == 0 ? "No hay registros de puntuación todavía." : sb.toString();
    }

    // * Devuelve el directorio físico en el que se ubican los archivos de la app
    public String getRutaDatos() {
        return DIRECTORIO_DATOS;
    }
}