package com.javafx;

// * Clase de modelo que representa una pregunta del Quiz y sus opciones correspondientes
public class Pregunta {
    private String enunciado;
    private String opcion1;
    private String opcion2;
    private String opcion3;
    private String opcion4;
    private int respuestaCorrecta;

    // * Constructor completo para instanciar una pregunta, aplicando validación
    // estricta
    public Pregunta(String enunciado, String opcion1, String opcion2, String opcion3, String opcion4,
            int respuestaCorrecta) {
        setEnunciado(enunciado);
        setOpcion1(opcion1);
        setOpcion2(opcion2);
        setOpcion3(opcion3);
        setOpcion4(opcion4);
        setRespuestaCorrecta(respuestaCorrecta);
    }

    public String getEnunciado() {
        return enunciado;
    }

    public String getOpcion1() {
        return opcion1;
    }

    public String getOpcion2() {
        return opcion2;
    }

    public String getOpcion3() {
        return opcion3;
    }

    public String getOpcion4() {
        return opcion4;
    }

    public int getRespuestaCorrecta() {
        return respuestaCorrecta;
    }

    // ! Validación: El enunciado no puede ser nulo o contener únicamente espacios
    public void setEnunciado(String enunciado) {
        if (enunciado == null || enunciado.trim().isEmpty()) {
            throw new IllegalArgumentException("El enunciado de la pregunta no puede estar vacío.");
        }
        this.enunciado = enunciado;
    }

    // ! Validación: La opción 1 no puede estar vacía
    public void setOpcion1(String opcion1) {
        if (opcion1 == null || opcion1.trim().isEmpty()) {
            throw new IllegalArgumentException("La opción 1 no puede estar vacía.");
        }
        this.opcion1 = opcion1;
    }

    // ! Validación: La opción 2 no puede estar vacía
    public void setOpcion2(String opcion2) {
        if (opcion2 == null || opcion2.trim().isEmpty()) {
            throw new IllegalArgumentException("La opción 2 no puede estar vacía.");
        }
        this.opcion2 = opcion2;
    }

    // ! Validación: La opción 3 no puede estar vacía
    public void setOpcion3(String opcion3) {
        if (opcion3 == null || opcion3.trim().isEmpty()) {
            throw new IllegalArgumentException("La opción 3 no puede estar vacía.");
        }
        this.opcion3 = opcion3;
    }

    // ! Validación: La opción 4 no puede estar vacía
    public void setOpcion4(String opcion4) {
        if (opcion4 == null || opcion4.trim().isEmpty()) {
            throw new IllegalArgumentException("La opción 4 no puede estar vacía.");
        }
        this.opcion4 = opcion4;
    }

    // ! Validación: El índice de la respuesta correcta debe estar obligatoriamente
    // entre 1 y 4
    public void setRespuestaCorrecta(int respuestaCorrecta) {
        if (respuestaCorrecta < 1 || respuestaCorrecta > 4) {
            throw new IllegalArgumentException("La respuesta correcta debe estar entre 1 y 4.");
        }
        this.respuestaCorrecta = respuestaCorrecta;
    }

    // * Compara si un índice numérico corresponde con el de la respuesta correcta
    public boolean esRespuestaCorrecta(int respuesta) {
        return respuesta == respuestaCorrecta;
    }
}