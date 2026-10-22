package com.javafx;

import java.io.IOException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

// * Controlador para la pantalla final de resultados (tertiary.fxml)
public class TertiaryController {

    @FXML
    private Label lblJugador;
    @FXML
    private Label lblNota;
    @FXML
    private Label lblDetalle;

    // * Inicializa y maqueta la información de fin de juego con las notas
    // ponderadas
    @FXML
    public void initialize() {
        if (App.quizActual != null) {
            lblJugador.setText("¡Partida Completada, " + App.quizActual.getJugador() + "!");
            lblNota.setText("Calificación: " + App.quizActual.calcularNotaFinal() + " / 10.0");
            lblDetalle.setText(
                    "Aciertos: " + App.quizActual.getPuntuacion() + " | Errores: " + App.quizActual.getErrores());
        }
    }

    // * Limpia la sesión del juego en memoria y redirige a la pantalla de
    // bienvenida
    @FXML
    private void volverAlMenu() throws IOException {
        // ! Limpiamos la partida anterior y regresamos a la pantalla de login
        App.quizActual = null;
        App.setRoot("primary");
    }

    // ! Detiene por completo la aplicación JavaFX y aborta de forma segura el
    // proceso JVM
    @FXML
    private void salir() {
        Platform.exit();
        System.exit(0);
    }
}