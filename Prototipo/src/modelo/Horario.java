package modelo;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class Horario {
    private String id;
    private Psicologo psicologo;
    private DayOfWeek dia;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private boolean disponible;

    public Horario(String id, Psicologo psicologo, DayOfWeek dia, 
                  LocalTime horaInicio, LocalTime horaFin) {
        this.id = id;
        this.psicologo = psicologo;
        this.dia = dia;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.disponible = true;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public Psicologo getPsicologo() { return psicologo; }
    public void setPsicologo(Psicologo psicologo) { this.psicologo = psicologo; }

    public DayOfWeek getDia() { return dia; }
    public void setDia(DayOfWeek dia) { this.dia = dia; }

    public LocalTime getHoraInicio() { return horaInicio; }
    public void setHoraInicio(LocalTime horaInicio) { this.horaInicio = horaInicio; }

    public LocalTime getHoraFin() { return horaFin; }
    public void setHoraFin(LocalTime horaFin) { this.horaFin = horaFin; }

    public boolean isDisponible() { return disponible; }
    public void setDisponible(boolean disponible) { this.disponible = disponible; }

    @Override
    public String toString() {
        return dia + " " + horaInicio + " - " + horaFin;
    }
}
