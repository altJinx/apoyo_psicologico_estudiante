package modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Estudiante {
    private String id;
    private String nombre;
    private String apellido;
    private String facultad;
    private String carrera;
    private String email;
    private String telefono;
    private LocalDate fechaIngreso;
    private List<String> antecedentesDocumentos;
    private List<Retroalimentacion> retroalimentaciones;

    public Estudiante(String id, String nombre, String apellido, String facultad, 
                     String carrera, String email, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.facultad = facultad;
        this.carrera = carrera;
        this.email = email;
        this.telefono = telefono;
        this.fechaIngreso = LocalDate.now();
        this.antecedentesDocumentos = new ArrayList<>();
        this.retroalimentaciones = new ArrayList<>();
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getFacultad() { return facultad; }
    public void setFacultad(String facultad) { this.facultad = facultad; }

    public String getCarrera() { return carrera; }
    public void setCarrera(String carrera) { this.carrera = carrera; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public LocalDate getFechaIngreso() { return fechaIngreso; }
    public void setFechaIngreso(LocalDate fechaIngreso) { this.fechaIngreso = fechaIngreso; }

    public List<String> getAntecedentesDocumentos() { return antecedentesDocumentos; }
    public void agregarAntecedente(String documento) { this.antecedentesDocumentos.add(documento); }

    public List<Retroalimentacion> getRetroalimentaciones() { return retroalimentaciones; }
    public void agregarRetroalimentacion(Retroalimentacion retro) { this.retroalimentaciones.add(retro); }

    public String getNombreCompleto() { return nombre + " " + apellido; }

    @Override
    public String toString() {
        return nombre + " " + apellido + " (" + id + ")";
    }
}
