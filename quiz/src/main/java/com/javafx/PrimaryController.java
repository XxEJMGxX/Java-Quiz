package com.javafx;

import java.io.IOException;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextArea;

// * Controlador para la pantalla de inicio y configuración del jugador (primary.fxml)
public class PrimaryController {

    @FXML
    private TextField txtJugador;

    // * Valida el nombre de usuario, carga preguntas de disco e inicia la sesión de
    // juego
    @FXML
    private void switchToSecondary() throws IOException {
        String nombre = txtJugador.getText();

        // ! Advertencia: no se permite jugar con un nombre vacío o lleno de espacios en
        // blanco
        if (nombre == null || nombre.trim().isEmpty()) {
            Alert alerta = new Alert(AlertType.WARNING);
            alerta.setTitle("Nombre Requerido");
            alerta.setHeaderText(null);
            alerta.setContentText("Por favor, introduce tu nombre para comenzar a jugar.");
            alerta.showAndWait();
            return;
        }

        List<Pregunta> bancoPreguntas = App.gestor.leerPreguntas();

        // ! Error crítico: el juego no puede empezar si el archivo de preguntas no está
        // accesible o está vacío
        if (bancoPreguntas == null || bancoPreguntas.isEmpty()) {
            Alert alerta = new Alert(AlertType.ERROR);
            alerta.setTitle("Error de datos");
            alerta.setHeaderText("No se han encontrado preguntas");
            alerta.setContentText("El archivo de preguntas está vacío o tiene un formato incorrecto.");
            alerta.showAndWait();
            return;
        }

        // * Inicializar el Quiz actual (mezclará preguntas aleatoriamente) y redirigir
        // a la pantalla del juego
        App.quizActual = new Quiz(bancoPreguntas, nombre.trim());
        App.setRoot("secondary");
    }

    // * Carga y formatea el historial de puntuaciones en un panel de texto
    // interactivo con barra de scroll
    @FXML
    private void verPuntuaciones() {
        String historial = App.gestor.obtenerHistorialPuntuaciones();

        Alert alerta = new Alert(AlertType.INFORMATION);
        alerta.setTitle("Historial de Puntuaciones");
        alerta.setHeaderText("Registro histórico de partidas:");

        // ? Usamos TextArea no editable para permitir scroll y copia de textos si la
        // lista es grande
        TextArea textArea = new TextArea(historial);
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);

        alerta.getDialogPane().setContent(textArea);
        alerta.showAndWait();
    }

    // * Alterna el modo de pantalla completa a través de la ventana global
    // principal
    @FXML
    private void alternarPantallaCompleta() {
        boolean estaEnPantallaCompleta = App.getStage().isFullScreen();
        App.getStage().setFullScreen(!estaEnPantallaCompleta);
    }

    // ! Cierre limpio e inmediato de la aplicación JavaFX y del proceso del sistema
    // operativo (JVM)
    @FXML
    private void salir() {
        javafx.application.Platform.exit();
        System.exit(0);
    }
}