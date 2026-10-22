package com.javafx;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

// * Clase principal que gestiona el ciclo de vida de la aplicación JavaFX
public class App extends Application {

    // ! primaryStage y scene se almacenan de forma estática para facilitar la
    // navegación y pantalla completa
    private static Scene scene;
    private static Stage primaryStage;

    // * Componentes globales accesibles por todos los controladores
    public static GestorArchivos gestor = new GestorArchivos();
    public static Quiz quizActual;

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        // ! Crear estructura de datos del usuario en disco antes de inicializar la
        // interfaz
        gestor.crearArchivosIniciales();

        // * Definir la escena inicial base (800x600 px) con la pantalla de inicio
        // "primary"
        scene = new Scene(loadFXML("primary"), 800, 600);
        stage.setTitle("JavaQuiz - Edición DAW");
        stage.setScene(scene);

        // * Forzar modo pantalla completa de forma predeterminada para maximizar la
        // inmersión del juego
        stage.setFullScreen(true);
        stage.setFullScreenExitHint("Pulsa ESC para salir de la pantalla completa o usa el botón del menú");

        stage.show();
    }

    // * Cambia de pantalla cargando dinámicamente un archivo FXML
    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    // ? Método auxiliar para localizar y parsear ficheros FXML en los recursos
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    // * Getter público para dar acceso a la ventana principal desde los
    // controladores
    public static Stage getStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch();
    }
}