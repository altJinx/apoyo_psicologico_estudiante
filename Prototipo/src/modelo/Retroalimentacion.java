package modelo;

import java.time.LocalDateTime;

public class Retroalimentacion {
    private String id;
    private Cita cita;
    private int calificacion; // 1-5 estrellas
    private String comentario;
    private LocalDateTime fecha;

    public Retroalimentacion(String id, Cita cita, int calificacion, String comentario) {
        this.id = id;
        this.cita = cita;
        this.calificacion = calificacion;
        this.comentario = comentario;
        this.fecha = LocalDateTime.now();
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public Cita getCita() { return cita; }
    public void setCita(Cita cita) { this.cita = cita; }

    public int getCalificacion() { return calificacion; }
    public void setCalificacion(int calificacion) { this.calificacion = calificacion; }

    public String getComentario() { return comentario; }
    public void setComentario(String comentario) { this.comentario = comentario; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }

    @Override
    public String toString() {
        return "Calificación: " + calificacion + "/5 - " + comentario;
    }
}
