package com.javafx;

import java.io.IOException;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.util.Duration;
import javafx.event.ActionEvent;

public class SecondaryController {

    @FXML
    private Label lblPregunta;
    @FXML
    private Label lblContador;
    @FXML
    private Label lblTiempo; // ¡NUEVO COMPONENTE FXML PARA EL RELOJ!
    @FXML
    private Button btnOpcion1;
    @FXML
    private Button btnOpcion2;
    @FXML
    private Button btnOpcion3;
    @FXML
    private Button btnOpcion4;
    @FXML
    private ProgressBar progressBar;

    private Timeline temporizador;
    // ! segundosRestantes es campo de instancia porque se comparte entre el lambda
    // del temporizador y actualizarPantalla()
    private int segundosRestantes;
    private static final int TIEMPO_POR_PREGUNTA = 20; // 20 segundos para responder

    // * Estilos base de cada botón con su gradiente Kahoot a 135° (vecinos de 60°
    // en rueda de color)
    private static final String BORDE_TRANSPARENTE = "-fx-border-color: transparent; -fx-border-width: 8px; -fx-border-radius: 10px; -fx-effect: none;";
    private static final String ESTILO_BTN1 = "-fx-font-size: 21px; -fx-font-weight: bold; -fx-text-fill: white;"
            + "-fx-background-color: linear-gradient(from 100% 0% to 0% 100%, #E21B3C, #E87722);"
            + "-fx-background-radius: 10px; -fx-cursor: hand; "
            + BORDE_TRANSPARENTE;
    private static final String ESTILO_BTN2 = "-fx-font-size: 21px; -fx-font-weight: bold; -fx-text-fill: white;"
            + "-fx-background-color: linear-gradient(from 100% 0% to 0% 100%, #1368CE, #7B2FBE);"
            + "-fx-background-radius: 10px; -fx-cursor: hand; "
            + BORDE_TRANSPARENTE;
    private static final String ESTILO_BTN3 = "-fx-font-size: 21px; -fx-font-weight: bold; -fx-text-fill: white;"
            + "-fx-background-color: linear-gradient(from 100% 0% to 0% 100%, #D89E00, #8FB300);"
            + "-fx-background-radius: 10px; -fx-cursor: hand; "
            + BORDE_TRANSPARENTE;
    private static final String ESTILO_BTN4 = "-fx-font-size: 21px; -fx-font-weight: bold; -fx-text-fill: white;"
            + "-fx-background-color: linear-gradient(from 100% 0% to 0% 100%, #26890C, #009B8D);"
            + "-fx-background-radius: 10px; -fx-cursor: hand; "
            + BORDE_TRANSPARENTE;

    @FXML
    public void initialize() {
        configurarTemporizador();
        actualizarPantalla();
    }

    private void configurarTemporizador() {
        // Ejecuta el bloque interno de código exactamente una vez cada segundo
        temporizador = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            segundosRestantes--;

            // * Cuando queda poco tiempo (≤5s) el color del contador cambia a rojo urgente
            if (segundosRestantes <= 5) {
                lblTiempo.setStyle("-fx-font-size: 21px; -fx-text-fill: #E21B3C; -fx-font-weight: bold;");
            }
            lblTiempo.setText(segundosRestantes + "s");

            if (segundosRestantes <= 0) {
                temporizador.stop();
                procesarRespuesta(0); // 0 significa tiempo agotado (cuenta como error)
            }
        }));
        temporizador.setCycleCount(Timeline.INDEFINITE);
    }

    private void actualizarPantalla() {
        if (App.quizActual == null)
            return;

        // Limpiar los colores de los bordes de la pregunta anterior y reactivar botones
        restablecerEstilosBotones();
        desactivarBotones(false);

        Pregunta pregunta = App.quizActual.obtenerPreguntaActual();

        if (pregunta != null) {
            lblPregunta.setText(pregunta.getEnunciado());
            btnOpcion1.setText(pregunta.getOpcion1());
            btnOpcion2.setText(pregunta.getOpcion2());
            btnOpcion3.setText(pregunta.getOpcion3());
            btnOpcion4.setText(pregunta.getOpcion4());

            int actual = App.quizActual.getPreguntaActual() + 1;
            lblContador.setText("Pregunta: " + actual + " / " + Quiz.MAX_PREGUNTAS);

            double progreso = (double) App.quizActual.getPreguntaActual() / Quiz.MAX_PREGUNTAS;
            progressBar.setProgress(progreso);

            segundosRestantes = TIEMPO_POR_PREGUNTA;
            lblTiempo.setText(segundosRestantes + "s");
            // * Restaurar color dorado al reiniciar el temporizador
            lblTiempo.setStyle("-fx-font-size: 21px; -fx-text-fill: #ffd700; -fx-font-weight: bold;");
            temporizador.playFromStart();
        }
    }

    @FXML
    private void responder(ActionEvent event) {
        temporizador.stop(); // Congelar el reloj en el segundo actual
        Button botonPulsado = (Button) event.getSource();
        int seleccion = 0;

        if (botonPulsado == btnOpcion1)
            seleccion = 1;
        else if (botonPulsado == btnOpcion2)
            seleccion = 2;
        else if (botonPulsado == btnOpcion3)
            seleccion = 3;
        else if (botonPulsado == btnOpcion4)
            seleccion = 4;

        procesarRespuesta(seleccion);
    }

    private void procesarRespuesta(int seleccion) {
        desactivarBotones(true); // Congelar botones para evitar dobles clics

        Pregunta preguntaActual = App.quizActual.obtenerPreguntaActual();
        int correcta = preguntaActual.getRespuestaCorrecta();

        // * Resaltado de bordes estilo Kahoot: verde para acierto, rojo para error
        resaltarBoton(correcta, "#26890C");

        if (seleccion != correcta && seleccion != 0) {
            resaltarBoton(seleccion, "#E21B3C");
        }

        // Registrar la selección en el motor del Quiz
        App.quizActual.comprobarRespuesta(seleccion);

        // Crear una pausa dramática de 2 segundos para ver las respuestas correctas
        // antes del cambio
        Timeline pausaCambio = new Timeline(new KeyFrame(Duration.seconds(2), e -> {
            try {
                if (App.quizActual.haTerminado()) {
                    App.gestor.guardarPuntuacion(
                            App.quizActual.getJugador(),
                            App.quizActual.getPuntuacion(),
                            App.quizActual.getErrores());
                    App.setRoot("tertiary");
                } else {
                    actualizarPantalla();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }));
        pausaCambio.play();
    }

    /**
     * Resalta el borde del botón indicado con un borde grueso y brillo (dropshadow)
     * manteniendo su gradiente Kahoot.
     * 
     * @param indice     número de opción (1-4)
     * @param colorBorde color CSS del borde (#26890C verde correcto / #E21B3C rojo
     *                   incorrecto)
     */
    private void resaltarBoton(int indice, String colorBorde) {
        // * Borde ancho + glow exterior para máxima visibilidad sin oscurecer el botón
        String bordeYGlow = "-fx-border-color: " + colorBorde
                + "; -fx-border-width: 8px; -fx-border-radius: 10px;"
                + " -fx-effect: dropshadow(gaussian, " + colorBorde + ", 28, 0.85, 0, 0);";
        if (indice == 1)
            btnOpcion1.setStyle(ESTILO_BTN1.replace(BORDE_TRANSPARENTE, bordeYGlow));
        else if (indice == 2)
            btnOpcion2.setStyle(ESTILO_BTN2.replace(BORDE_TRANSPARENTE, bordeYGlow));
        else if (indice == 3)
            btnOpcion3.setStyle(ESTILO_BTN3.replace(BORDE_TRANSPARENTE, bordeYGlow));
        else if (indice == 4)
            btnOpcion4.setStyle(ESTILO_BTN4.replace(BORDE_TRANSPARENTE, bordeYGlow));
    }

    // * Restaura el estilo completo (gradiente + borde transparente + sin glow) de
    // cada botón
    private void restablecerEstilosBotones() {
        btnOpcion1.setStyle(ESTILO_BTN1);
        btnOpcion2.setStyle(ESTILO_BTN2);
        btnOpcion3.setStyle(ESTILO_BTN3);
        btnOpcion4.setStyle(ESTILO_BTN4);
    }

    // * setMouseTransparent bloquea clics SIN oscurecer los botones (a diferencia
    // de setDisable)
    private void desactivarBotones(boolean desactivar) {
        btnOpcion1.setMouseTransparent(desactivar);
        btnOpcion2.setMouseTransparent(desactivar);
        btnOpcion3.setMouseTransparent(desactivar);
        btnOpcion4.setMouseTransparent(desactivar);
    }
}