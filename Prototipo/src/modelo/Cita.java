package modelo;

import java.time.LocalDateTime;

public class Cita {
    private String id;
    private Estudiante estudiante;
    private Psicologo psicologo;
    private LocalDateTime fechaHora;
    private String tipo; // "VIRTUAL", "PRESENCIAL", "SOS"
    private EstadoCita estado;
    private NivelRiesgo nivelRiesgo;
    private String notaClinica;
    private String firmaPsicologo;
    private boolean rompimientoCristal;

    public Cita(String id, Estudiante estudiante, Psicologo psicologo, 
               LocalDateTime fechaHora, String tipo) {
        this.id = id;
        this.estudiante = estudiante;
        this.psicologo = psicologo;
        this.fechaHora = fechaHora;
        this.tipo = tipo;
        this.estado = EstadoCita.PROGRAMADA;
        this.nivelRiesgo = NivelRiesgo.SIN_EVALUAR;
        this.rompimientoCristal = false;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public Estudiante getEstudiante() { return estudiante; }
    public void setEstudiante(Estudiante estudiante) { this.estudiante = estudiante; }

    public Psicologo getPsicologo() { return psicologo; }
    public void setPsicologo(Psicologo psicologo) { this.psicologo = psicologo; }

    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public EstadoCita getEstado() { return estado; }
    public void setEstado(EstadoCita estado) { this.estado = estado; }

    public NivelRiesgo getNivelRiesgo() { return nivelRiesgo; }
    public void setNivelRiesgo(NivelRiesgo nivelRiesgo) { this.nivelRiesgo = nivelRiesgo; }

    public String getNotaClinica() { return notaClinica; }
    public void setNotaClinica(String notaClinica) { this.notaClinica = notaClinica; }

    public String getFirmaPsicologo() { return firmaPsicologo; }
    public void setFirmaPsicologo(String firmaPsicologo) { this.firmaPsicologo = firmaPsicologo; }

    public boolean isRompimientoCristal() { return rompimientoCristal; }
    public void setRompimientoCristal(boolean rompimientoCristal) { this.rompimientoCristal = rompimientoCristal; }

    @Override
    public String toString() {
        return "Cita #" + id + " - " + estudiante.getNombreCompleto() + 
               " con " + psicologo.getNombreCompleto() + " - " + fechaHora;
    }
}
