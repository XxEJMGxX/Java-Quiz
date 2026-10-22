package com.javafx;

import java.util.Collections;
import java.util.List;

// * Lógica principal y control de estado de la partida de Quiz
public class Quiz {
    // ! Constante central que define la longitud de cada partida (15 preguntas)
    public static final int MAX_PREGUNTAS = 15;

    private List<Pregunta> preguntas;
    private int puntuacion;
    private int errores;
    private int preguntaActual;
    private String jugador;

    // * Inicializa y asocia los datos de una partida, barajando el banco al
    // instante
    public Quiz(List<Pregunta> preguntas, String jugador) {
        setJugador(jugador);
        setPreguntas(preguntas);
        mezclarPreguntas(); // Desordenar el banco de preguntas al iniciar la partida
    }

    // * Mezcla aleatoriamente las preguntas para garantizar partidas no repetitivas
    public void mezclarPreguntas() {
        Collections.shuffle(preguntas);
    }

    public List<Pregunta> getPreguntas() {
        return preguntas;
    }

    // ! Configura las preguntas y limpia los acumuladores a 0 al inicializar la
    // partida
    public void setPreguntas(List<Pregunta> preguntas) {
        if (preguntas == null || preguntas.isEmpty()) {
            throw new IllegalArgumentException("La lista de preguntas no puede estar vacía.");
        }
        this.preguntas = preguntas;
        this.preguntaActual = 0;
        this.puntuacion = 0;
        this.errores = 0;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        if (puntuacion < 0) {
            throw new IllegalArgumentException("La puntuación no puede ser negativa.");
        }
        this.puntuacion = puntuacion;
    }

    public int getErrores() {
        return errores;
    }

    public void setErrores(int errores) {
        if (errores < 0) {
            throw new IllegalArgumentException("El número de errores no puede ser negativo.");
        }
        this.errores = errores;
    }

    public int getPreguntaActual() {
        return preguntaActual;
    }

    public void setPreguntaActual(int preguntaActual) {
        if (preguntaActual < 0 || preguntaActual > preguntas.size()) {
            throw new IndexOutOfBoundsException("Índice de pregunta fuera de rango.");
        }
        this.preguntaActual = preguntaActual;
    }

    public String getJugador() {
        return jugador;
    }

    public void setJugador(String jugador) {
        if (jugador == null || jugador.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del jugador no puede estar vacío.");
        }
        this.jugador = jugador;
    }

    // * Devuelve el elemento Pregunta correspondiente al índice actual si quedan
    // rondas
    public Pregunta obtenerPreguntaActual() {
        if (!haTerminado()) {
            return preguntas.get(preguntaActual);
        }
        return null;
    }

    // * Valida la opción elegida por el jugador, sumando aciertos/errores y
    // avanzando el contador
    public void comprobarRespuesta(int seleccionJugador) {
        Pregunta pregunta = obtenerPreguntaActual();
        if (pregunta != null) {
            if (pregunta.esRespuestaCorrecta(seleccionJugador)) {
                this.puntuacion++;
            } else {
                this.errores++;
            }
            this.preguntaActual++;
        }
    }

    // ? Calcula la nota académica final ponderada sobre 10 con redondeo de un
    // decimal
    public double calcularNotaFinal() {
        int totalPreguntas = Math.min(MAX_PREGUNTAS, preguntas.size());
        double nota = (double) (puntuacion * 10) / totalPreguntas;
        return Math.round(nota * 10.0) / 10.0;
    }

    // * Determina si el jugador ha completado la cantidad de preguntas establecidas
    public boolean haTerminado() {
        return preguntaActual >= MAX_PREGUNTAS || preguntaActual >= preguntas.size();
    }
}