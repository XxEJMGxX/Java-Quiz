# ☕ Quiz de Conocimientos de Java

Una aplicación de escritorio interactiva desarrollada en Java orientada a poner a prueba conocimientos mediante un sistema de cuestionarios. 

## 🎥 Demostración
![JavaFX](https://github.com/user-attachments/assets/caa0b52e-c438-40fc-9cef-c5744bff72af)

## 🏗️ Arquitectura y Tecnologías
Este proyecto está gestionado con **Maven** y estructurado bajo el patrón de diseño **MVC (Modelo-Vista-Controlador)**, garantizando código escalable y modular.

* **Lenguaje:** Java (con soporte de Módulos mediante `module-info.java`).
* **Framework UI:** JavaFX (con archivos `.fxml` diseñados mediante Gluon Scene Builder).
* **Gestión de dependencias:** Maven (`pom.xml`).

### Estructura del Código
* **Modelos:** Lógica de negocio orientada a objetos (`Pregunta.java`, `Quiz.java`).
* **Vistas y Controladores:** Navegación fluida entre tres pantallas principales (`primary`, `secondary`, `tertiary`) con sus respectivos controladores.
* **Persistencia:** Gestión de entrada/salida de datos externa a través de la clase `GestorArchivos.java`.

## 🚀 Cómo probar la aplicación
No necesitas compilar el código para probar el juego:
1. Ve a la sección de **Releases** en este repositorio.
2. Descarga el archivo ejecutable `.jar`.
3. Asegúrate de tener Java instalado en tu equipo.
4. Ejecútalo haciendo doble clic o desde la terminal: `java -jar quiz.jar`
