package modelo;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class SimuladorDatos {
    private static SimuladorDatos instancia;
    
    private List<Estudiante> estudiantes;
    private List<Psicologo> psicologos;
    private List<Cita> citas;
    private List<Horario> horarios;
    private List<Retroalimentacion> retroalimentaciones;
    private List<AuditoriaRompimientoCristal> auditorias;
    
    private boolean internetDisponible;
    
    private SimuladorDatos() {
        inicializarDatos();
        this.internetDisponible = true;
    }
    
    public static SimuladorDatos getInstancia() {
        if (instancia == null) {
            instancia = new SimuladorDatos();
        }
        return instancia;
    }
    
    private void inicializarDatos() {
        estudiantes = new ArrayList<>();
        psicologos = new ArrayList<>();
        citas = new ArrayList<>();
        horarios = new ArrayList<>();
        retroalimentaciones = new ArrayList<>();
        auditorias = new ArrayList<>();
        
        // Crear psicólogos
        Psicologo p1 = new Psicologo("P001", "María", "González", "Clínica", "Ingeniería");
        Psicologo p2 = new Psicologo("P002", "Carlos", "Rodríguez", "Psicoterapia", "Medicina");
        Psicologo p3 = new Psicologo("P003", "Ana", "Martínez", "Orientación", "Derecho");
        Psicologo p4 = new Psicologo("P004", "Pedro", "Sánchez", "Psicología Educativa", "Ciencias");
        
        psicologos.add(p1);
        psicologos.add(p2);
        psicologos.add(p3);
        psicologos.add(p4);
        
        // Crear horarios para psicólogos
        p1.agregarHorario(new Horario("H001", p1, DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(12, 0)));
        p1.agregarHorario(new Horario("H002", p1, DayOfWeek.WEDNESDAY, LocalTime.of(14, 0), LocalTime.of(18, 0)));
        p1.agregarHorario(new Horario("H003", p1, DayOfWeek.FRIDAY, LocalTime.of(10, 0), LocalTime.of(13, 0)));
        
        p2.agregarHorario(new Horario("H004", p2, DayOfWeek.TUESDAY, LocalTime.of(8, 0), LocalTime.of(12, 0)));
        p2.agregarHorario(new Horario("H005", p2, DayOfWeek.THURSDAY, LocalTime.of(15, 0), LocalTime.of(19, 0)));
        
        p3.agregarHorario(new Horario("H006", p3, DayOfWeek.MONDAY, LocalTime.of(13, 0), LocalTime.of(17, 0)));
        p3.agregarHorario(new Horario("H007", p3, DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(12, 0)));
        
        p4.agregarHorario(new Horario("H008", p4, DayOfWeek.TUESDAY, LocalTime.of(10, 0), LocalTime.of(14, 0)));
        p4.agregarHorario(new Horario("H009", p4, DayOfWeek.THURSDAY, LocalTime.of(8, 0), LocalTime.of(12, 0)));
        
        horarios.addAll(p1.getHorarios());
        horarios.addAll(p2.getHorarios());
        horarios.addAll(p3.getHorarios());
        horarios.addAll(p4.getHorarios());
        
        // Crear estudiantes
        Estudiante e1 = new Estudiante("E001", "Juan", "Pérez", "Ingeniería", "Ingeniería Civil", 
                                       "juan.perez@universidad.edu", "+56912345678");
        Estudiante e2 = new Estudiante("E002", "Laura", "Silva", "Medicina", "Medicina General", 
                                       "laura.silva@universidad.edu", "+56987654321");
        Estudiante e3 = new Estudiante("E003", "Roberto", "Díaz", "Derecho", "Derecho Penal", 
                                       "roberto.diaz@universidad.edu", "+56911223344");
        Estudiante e4 = new Estudiante("E004", "Camila", "Torres", "Ciencias", "Biología", 
                                       "camila.torres@universidad.edu", "+56955667788");
        Estudiante e5 = new Estudiante("E005", "Diego", "Morales", "Ingeniería", "Ingeniería de Sistemas", 
                                       "diego.morales@universidad.edu", "+56999887766");
        
        estudiantes.add(e1);
        estudiantes.add(e2);
        estudiantes.add(e3);
        estudiantes.add(e4);
        estudiantes.add(e5);
        
        // Agregar antecedentes simulados
        e1.agregarAntecedente("Historia_clinica_anterior.pdf");
        e2.agregarAntecedente("Informe_psiquiatrico_2023.pdf");
        e3.agregarAntecedente("Evaluacion_psicologica_inicial.pdf");
        
        // Crear citas de ejemplo
        Cita c1 = new Cita("C001", e1, p1, LocalDateTime.now().plusDays(1).withHour(10).withMinute(0), "VIRTUAL");
        Cita c2 = new Cita("C002", e2, p2, LocalDateTime.now().plusDays(2).withHour(14).withMinute(0), "PRESENCIAL");
        Cita c3 = new Cita("C003", e3, p3, LocalDateTime.now().plusDays(3).withHour(9).withMinute(0), "VIRTUAL");
        
        c1.setEstado(EstadoCita.PROGRAMADA);
        c2.setEstado(EstadoCita.COMPLETADA);
        c2.setNivelRiesgo(NivelRiesgo.MODERADO);
        c2.setNotaClinica("Paciente muestra signos de ansiedad moderada. Se recomienda seguimiento.");
        c2.setFirmaPsicologo("Dr. Carlos Rodríguez - Firma Digital Validada");
        c3.setEstado(EstadoCita.PROGRAMADA);
        
        citas.add(c1);
        citas.add(c2);
        citas.add(c3);
        
        // Crear retroalimentaciones de ejemplo
        Retroalimentacion r1 = new Retroalimentacion("R001", c2, 5, "Excelente atención, muy empático");
        retroalimentaciones.add(r1);
        e2.agregarRetroalimentacion(r1);
    }
    
    // Getters
    public List<Estudiante> getEstudiantes() { return estudiantes; }
    public List<Psicologo> getPsicologos() { return psicologos; }
    public List<Cita> getCitas() { return citas; }
    public List<Horario> getHorarios() { return horarios; }
    public List<Retroalimentacion> getRetroalimentaciones() { return retroalimentaciones; }
    public List<AuditoriaRompimientoCristal> getAuditorias() { return auditorias; }
    
    public boolean isInternetDisponible() { return internetDisponible; }
    public void setInternetDisponible(boolean internetDisponible) { this.internetDisponible = internetDisponible; }
    
    // Métodos de búsqueda
    public Estudiante buscarEstudiantePorId(String id) {
        return estudiantes.stream().filter(e -> e.getId().equals(id)).findFirst().orElse(null);
    }
    
    public Psicologo buscarPsicologoPorId(String id) {
        return psicologos.stream().filter(p -> p.getId().equals(id)).findFirst().orElse(null);
    }
    
    public Cita buscarCitaPorId(String id) {
        return citas.stream().filter(c -> c.getId().equals(id)).findFirst().orElse(null);
    }
    
    public List<Psicologo> getPsicologosPorFacultad(String facultad) {
        List<Psicologo> resultado = new ArrayList<>();
        for (Psicologo p : psicologos) {
            if (p.getFacultadAsignada().equals(facultad)) {
                resultado.add(p);
            }
        }
        return resultado;
    }
    
    public List<Psicologo> getPsicologosDisponibles() {
        List<Psicologo> resultado = new ArrayList<>();
        for (Psicologo p : psicologos) {
            if (p.isDisponible()) {
                resultado.add(p);
            }
        }
        return resultado;
    }
    
    // Métodos de agregación
    public void agregarEstudiante(Estudiante estudiante) { estudiantes.add(estudiante); }
    public void agregarPsicologo(Psicologo psicologo) { psicologos.add(psicologo); }
    public void agregarCita(Cita cita) { citas.add(cita); }
    public void agregarHorario(Horario horario) { horarios.add(horario); }
    public void agregarRetroalimentacion(Retroalimentacion retro) { retroalimentaciones.add(retro); }
    public void agregarAuditoria(AuditoriaRompimientoCristal auditoria) { auditorias.add(auditoria); }
    
    // Métodos de eliminación
    public void eliminarCita(Cita cita) { citas.remove(cita); }
}
