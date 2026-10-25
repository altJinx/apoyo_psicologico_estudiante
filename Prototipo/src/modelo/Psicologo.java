package modelo;

import java.util.ArrayList;
import java.util.List;

public class Psicologo {
    private String id;
    private String nombre;
    private String apellido;
    private String especialidad;
    private String facultadAsignada;
    private boolean disponible;
    private List<Horario> horarios;

    public Psicologo(String id, String nombre, String apellido, String especialidad, 
                    String facultadAsignada) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.especialidad = especialidad;
        this.facultadAsignada = facultadAsignada;
        this.disponible = true;
        this.horarios = new ArrayList<>();
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }

    public String getFacultadAsignada() { return facultadAsignada; }
    public void setFacultadAsignada(String facultadAsignada) { this.facultadAsignada = facultadAsignada; }

    public boolean isDisponible() { return disponible; }
    public void setDisponible(boolean disponible) { this.disponible = disponible; }

    public List<Horario> getHorarios() { return horarios; }
    public void agregarHorario(Horario horario) { this.horarios.add(horario); }

    public String getNombreCompleto() { return nombre + " " + apellido; }

    @Override
    public String toString() {
        return "Dr(a). " + nombre + " " + apellido + " - " + especialidad;
    }
}
