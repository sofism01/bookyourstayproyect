package org.uniquindio.application.domain;

import java.io.Serializable;

public class Resena implements Serializable {

    private String comentario;
    private int calificacion;
    private Alojamiento alojamiento;

    public Resena(String comentario, int calificacion, Alojamiento alojamiento) {
        this.comentario = comentario;
        this.calificacion = calificacion;
        this.alojamiento = alojamiento;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public int getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(int calificacion) {
        this.calificacion = calificacion;
    }


    public Alojamiento getAlojamiento() {
        return alojamiento;
    }

    public void setAlojamiento(Alojamiento alojamiento) {
        this.alojamiento = alojamiento;
    }
}
