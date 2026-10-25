package modelo;

import java.time.LocalDateTime;

public class AuditoriaRompimientoCristal {
    private String id;
    private Cita cita;
    private String causalLegal;
    private String justificacion;
    private LocalDateTime fecha;
    private String autorizador;

    public AuditoriaRompimientoCristal(String id, Cita cita, String causalLegal, 
                                      String justificacion, String autorizador) {
        this.id = id;
        this.cita = cita;
        this.causalLegal = causalLegal;
        this.justificacion = justificacion;
        this.fecha = LocalDateTime.now();
        this.autorizador = autorizador;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public Cita getCita() { return cita; }
    public void setCita(Cita cita) { this.cita = cita; }

    public String getCausalLegal() { return causalLegal; }
    public void setCausalLegal(String causalLegal) { this.causalLegal = causalLegal; }

    public String getJustificacion() { return justificacion; }
    public void setJustificacion(String justificacion) { this.justificacion = justificacion; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }

    public String getAutorizador() { return autorizador; }
    public void setAutorizador(String autorizador) { this.autorizador = autorizador; }

    @Override
    public String toString() {
        return "[" + fecha + "] " + autorizador + " accedió a expediente #" + 
               cita.getId() + " - Causal: " + causalLegal;
    }
}
