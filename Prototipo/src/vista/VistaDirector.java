package vista;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import modelo.*;

public class VistaDirector {
    private Stage stage;
    private VBox root;
    private SimuladorDatos datos;
    
    public VistaDirector(Stage stage) {
        this.stage = stage;
        this.datos = SimuladorDatos.getInstancia();
        crearVista();
    }
    
    private void crearVista() {
        root = new VBox(10);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #f5f5f5;");
        
        // Header
        HBox header = new HBox(20);
        header.setPadding(new Insets(10));
        header.setStyle("-fx-background-color: #2c3e50; -fx-background-radius: 5;");
        
        Label titulo = new Label("Director de Bienestar (Administrador)");
        titulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: white;");
        
        Button btnVolver = new Button("← Volver");
        btnVolver.setStyle("-fx-background-color: #ecf0f1;");
        btnVolver.setOnAction(e -> volverAPrincipal());
        
        header.getChildren().addAll(btnVolver, titulo);
        header.setHgrow(titulo, Priority.ALWAYS);
        
        // TabPane para las funcionalidades
        TabPane tabPane = new TabPane();
        
        // Tab 1: Gestionar Horarios
        Tab tabHorarios = new Tab("📅 Gestionar Horarios");
        tabHorarios.setContent(crearVistaHorarios());
        tabHorarios.setClosable(false);
        
        // Tab 2: Dashboard Estadístico
        Tab tabDashboard = new Tab("📊 Dashboard Estadístico");
        tabDashboard.setContent(crearVistaDashboard());
        tabDashboard.setClosable(false);
        
        // Tab 3: Romper el Cristal
        Tab tabCristal = new Tab("🔓 Romper el Cristal");
        tabCristal.setContent(crearVistaRompimientoCristal());
        tabCristal.setClosable(false);
        
        tabPane.getTabs().addAll(tabHorarios, tabDashboard, tabCristal);
        
        root.getChildren().addAll(header, tabPane);
    }
    
    private VBox crearVistaHorarios() {
        VBox vbox = new VBox(15);
        vbox.setPadding(new Insets(20));
        
        Label lblTitulo = new Label("Gestión de Horarios del Equipo de Psicólogos");
        lblTitulo.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        
        // Lista de psicólogos y sus horarios
        ListView<Psicologo> listViewPsicologos = new ListView<>();
        listViewPsicologos.getItems().addAll(datos.getPsicologos());
        
        listViewPsicologos.setCellFactory(param -> new ListCell<Psicologo>() {
            @Override
            protected void updateItem(Psicologo psicologo, boolean empty) {
                super.updateItem(psicologo, empty);
                if (empty || psicologo == null) {
                    setText(null);
                } else {
                    StringBuilder sb = new StringBuilder();
                    sb.append(psicologo.getNombreCompleto()).append("\n");
                    sb.append("Especialidad: ").append(psicologo.getEspecialidad()).append("\n");
                    sb.append("Facultad: ").append(psicologo.getFacultadAsignada()).append("\n");
                    sb.append("Disponible: ").append(psicologo.isDisponible() ? "Sí" : "No").append("\n");
                    sb.append("Horarios: ");
                    for (Horario h : psicologo.getHorarios()) {
                        sb.append(h.toString()).append("; ");
                    }
                    setText(sb.toString());
                }
            }
        });
        
        // Botones de gestión
        HBox botones = new HBox(10);
        
        Button btnAgregarHorario = new Button("➕ Agregar Horario");
        btnAgregarHorario.setOnAction(e -> agregarHorario(listViewPsicologos));
        
        Button btnToggleDisponibilidad = new Button("🔄 Toggle Disponibilidad");
        btnToggleDisponibilidad.setOnAction(e -> toggleDisponibilidad(listViewPsicologos));
        
        botones.getChildren().addAll(btnAgregarHorario, btnToggleDisponibilidad);
        
        vbox.getChildren().addAll(lblTitulo, listViewPsicologos, botones);
        return vbox;
    }
    
    private void agregarHorario(ListView<Psicologo> listView) {
        Psicologo seleccionado = listView.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Agregar Horario");
            alert.setHeaderText("Seleccione un psicólogo");
            alert.setContentText("Por favor, seleccione un psicólogo de la lista.");
            alert.showAndWait();
            return;
        }
        
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Agregar Horario");
        dialog.setHeaderText("Psicólogo: " + seleccionado.getNombreCompleto());
        
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        
        ComboBox<String> cbDia = new ComboBox<>();
        cbDia.getItems().addAll("Lunes", "Martes", "Miércoles", "Jueves", "Viernes");
        cbDia.getSelectionModel().selectFirst();
        
        ComboBox<String> cbInicio = new ComboBox<>();
        cbInicio.getItems().addAll("08:00", "09:00", "10:00", "11:00", "12:00", 
                                   "13:00", "14:00", "15:00", "16:00", "17:00");
        cbInicio.getSelectionModel().select("09:00");
        
        ComboBox<String> cbFin = new ComboBox<>();
        cbFin.getItems().addAll("09:00", "10:00", "11:00", "12:00", "13:00", 
                                "14:00", "15:00", "16:00", "17:00", "18:00");
        cbFin.getSelectionModel().select("12:00");
        
        grid.add(new Label("Día:"), 0, 0);
        grid.add(cbDia, 1, 0);
        grid.add(new Label("Hora Inicio:"), 0, 1);
        grid.add(cbInicio, 1, 1);
        grid.add(new Label("Hora Fin:"), 0, 2);
        grid.add(cbFin, 1, 2);
        
        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        
        dialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                Horario nuevoHorario = new Horario(
                    "H" + System.currentTimeMillis(),
                    seleccionado,
                    java.time.DayOfWeek.valueOf(cbDia.getValue().toUpperCase()),
                    java.time.LocalTime.parse(cbInicio.getValue()),
                    java.time.LocalTime.parse(cbFin.getValue())
                );
                seleccionado.agregarHorario(nuevoHorario);
                datos.agregarHorario(nuevoHorario);
                
                Alert confirm = new Alert(Alert.AlertType.INFORMATION);
                confirm.setTitle("Horario Agregado");
                confirm.setHeaderText("Horario Registrado");
                confirm.setContentText("Horario agregado exitosamente para " + 
                                     seleccionado.getNombreCompleto());
                confirm.showAndWait();
                
                listView.refresh();
            }
        });
    }
    
    private void toggleDisponibilidad(ListView<Psicologo> listView) {
        Psicologo seleccionado = listView.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Toggle Disponibilidad");
            alert.setHeaderText("Seleccione un psicólogo");
            alert.setContentText("Por favor, seleccione un psicólogo de la lista.");
            alert.showAndWait();
            return;
        }
        
        seleccionado.setDisponible(!seleccionado.isDisponible());
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Disponibilidad Actualizada");
        alert.setHeaderText("Estado Cambiado");
        alert.setContentText(seleccionado.getNombreCompleto() + 
                           "\nDisponible: " + (seleccionado.isDisponible() ? "Sí" : "No"));
        alert.showAndWait();
        
        listView.refresh();
    }
    
    private VBox crearVistaDashboard() {
        VBox vbox = new VBox(15);
        vbox.setPadding(new Insets(20));
        
        Label lblTitulo = new Label("Dashboard Estadístico (Anonimizado)");
        lblTitulo.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        
        Label lblAdvertencia = new Label("⚠️ Todos los datos están anonimizados para proteger la privacidad.");
        lblAdvertencia.setStyle("-fx-font-size: 12px; -fx-text-fill: #e74c3c; -fx-font-style: italic;");
        
        // Métricas
        GridPane grid = new GridPane();
        grid.setHgap(20);
        grid.setVgap(15);
        grid.setPadding(new Insets(20));
        
        int totalCitas = datos.getCitas().size();
        int citasCompletadas = (int) datos.getCitas().stream()
            .filter(c -> c.getEstado() == EstadoCita.COMPLETADA).count();
        int citasProgramadas = (int) datos.getCitas().stream()
            .filter(c -> c.getEstado() == EstadoCita.PROGRAMADA).count();
        
        double promedioSatisfaccion = datos.getRetroalimentaciones().stream()
            .mapToInt(Retroalimentacion::getCalificacion)
            .average()
            .orElse(0.0);
        
        int riesgoAlto = (int) datos.getCitas().stream()
            .filter(c -> c.getNivelRiesgo() == NivelRiesgo.ALTO_VITAL).count();
        int riesgoModerado = (int) datos.getCitas().stream()
            .filter(c -> c.getNivelRiesgo() == NivelRiesgo.MODERADO).count();
        int riesgoBajo = (int) datos.getCitas().stream()
            .filter(c -> c.getNivelRiesgo() == NivelRiesgo.BAJO).count();
        
        grid.add(new Label("Total de Citas:"), 0, 0);
        grid.add(new Label(String.valueOf(totalCitas)), 1, 0);
        
        grid.add(new Label("Citas Completadas:"), 0, 1);
        grid.add(new Label(String.valueOf(citasCompletadas)), 1, 1);
        
        grid.add(new Label("Citas Programadas:"), 0, 2);
        grid.add(new Label(String.valueOf(citasProgramadas)), 1, 2);
        
        grid.add(new Label("Promedio Satisfacción:"), 0, 3);
        grid.add(new Label(String.format("%.1f/5.0", promedioSatisfaccion)), 1, 3);
        
        grid.add(new Label("Riesgo Alto/Vital:"), 0, 4);
        grid.add(new Label(String.valueOf(riesgoAlto)), 1, 4);
        
        grid.add(new Label("Riesgo Moderado:"), 0, 5);
        grid.add(new Label(String.valueOf(riesgoModerado)), 1, 5);
        
        grid.add(new Label("Riesgo Bajo:"), 0, 6);
        grid.add(new Label(String.valueOf(riesgoBajo)), 1, 6);
        
        vbox.getChildren().addAll(lblTitulo, lblAdvertencia, grid);
        return vbox;
    }
    
    private VBox crearVistaRompimientoCristal() {
        VBox vbox = new VBox(15);
        vbox.setPadding(new Insets(20));
        
        Label lblTitulo = new Label("🔓 Romper el Cristal - Acceso Restringido");
        lblTitulo.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #c0392b;");
        
        Label lblAdvertencia = new Label("⚠️ Este acceso excepcional requiere registro obligatorio en el log de auditoría.");
        lblAdvertencia.setStyle("-fx-font-size: 12px; -fx-text-fill: #e74c3c; -fx-font-style: italic;");
        
        // Lista de citas
        ListView<Cita> listViewCitas = new ListView<>();
        listViewCitas.getItems().addAll(datos.getCitas());
        
        listViewCitas.setCellFactory(param -> new ListCell<Cita>() {
            @Override
            protected void updateItem(Cita cita, boolean empty) {
                super.updateItem(cita, empty);
                if (empty || cita == null) {
                    setText(null);
                } else {
                    setText("Cita #" + cita.getId() + 
                           " - Estudiante: " + cita.getEstudiante().getNombreCompleto() +
                           " - Estado: " + cita.getEstado() +
                           (cita.isRompimientoCristal() ? " [ACCESO AUTORIZADO]" : ""));
                }
            }
        });
        
        Button btnAcceder = new Button("🔓 Acceder a Expediente");
        btnAcceder.setStyle("-fx-background-color: #c0392b; -fx-text-fill: white; -fx-font-weight: bold;");
        btnAcceder.setOnAction(e -> accederExpediente(listViewCitas));
        
        // Log de auditoría
        Label lblAuditoria = new Label("📋 Log de Auditoría");
        lblAuditoria.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        
        ListView<AuditoriaRompimientoCristal> listViewAuditoria = new ListView<>();
        listViewAuditoria.getItems().addAll(datos.getAuditorias());
        listViewAuditoria.setPrefHeight(150);
        
        vbox.getChildren().addAll(lblTitulo, lblAdvertencia, listViewCitas, btnAcceder, 
                                 lblAuditoria, listViewAuditoria);
        return vbox;
    }
    
    private void accederExpediente(ListView<Cita> listView) {
        Cita seleccionada = listView.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Acceso Restringido");
            alert.setHeaderText("Seleccione una cita");
            alert.setContentText("Por favor, seleccione una cita para acceder a su expediente.");
            alert.showAndWait();
            return;
        }
        
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("🔓 Romper el Cristal - Auditoría Obligatoria");
        dialog.setHeaderText("Cita #" + seleccionada.getId() + 
                           "\nEstudiante: " + seleccionada.getEstudiante().getNombreCompleto());
        
        VBox vbox = new VBox(15);
        vbox.setPadding(new Insets(20));
        
        Label lblCausales = new Label("Seleccione la causal legal:");
        ComboBox<String> cbCausales = new ComboBox<>();
        cbCausales.getItems().addAll(
            "1. Riesgo inminente de daño físico o psicológico",
            "2. Orden judicial expresa",
            "3. Consentimiento informado del paciente",
            "4. Emergencia médica vital",
            "5. Investigación de delito con autorización fiscal"
        );
        cbCausales.setPromptText("Seleccione causal legal");
        
        Label lblJustificacion = new Label("Justificación obligatoria:");
        TextArea txtJustificacion = new TextArea();
        txtJustificacion.setPromptText("Describa detalladamente la razón del acceso...");
        txtJustificacion.setPrefRowCount(4);
        
        Label lblAutorizador = new Label("Autorizador (nombre completo):");
        TextField txtAutorizador = new TextField();
        txtAutorizador.setPromptText("Nombre del autorizador");
        
        vbox.getChildren().addAll(lblCausales, cbCausales, lblJustificacion, txtJustificacion,
                                 lblAutorizador, txtAutorizador);
        
        dialog.getDialogPane().setContent(vbox);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        
        dialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                if (cbCausales.getValue() == null || txtJustificacion.getText().isEmpty() || 
                    txtAutorizador.getText().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Datos Incompletos");
                    alert.setHeaderText("Complete todos los campos");
                    alert.setContentText("La causal legal, justificación y autorizador son obligatorios.");
                    alert.showAndWait();
                    return;
                }
                
                // Registrar en auditoría
                AuditoriaRompimientoCristal auditoria = new AuditoriaRompimientoCristal(
                    "A" + System.currentTimeMillis(),
                    seleccionada,
                    cbCausales.getValue(),
                    txtJustificacion.getText(),
                    txtAutorizador.getText()
                );
                datos.agregarAuditoria(auditoria);
                
                // Marcar cita como con acceso autorizado
                seleccionada.setRompimientoCristal(true);
                
                // Mostrar expediente
                Alert expediente = new Alert(Alert.AlertType.INFORMATION);
                expediente.setTitle("🔓 Expediente Clínico - ACCESO AUTORIZADO");
                expediente.setHeaderText("EXPEDIENTE DE: " + seleccionada.getEstudiante().getNombreCompleto().toUpperCase());
                
                String contenido = "ID Estudiante: " + seleccionada.getEstudiante().getId() + "\n" +
                                  "Facultad: " + seleccionada.getEstudiante().getFacultad() + "\n" +
                                  "Carrera: " + seleccionada.getEstudiante().getCarrera() + "\n\n" +
                                  "NIVEL DE RIESGO: " + seleccionada.getNivelRiesgo() + "\n\n" +
                                  "NOTA CLÍNICA:\n" + 
                                  (seleccionada.getNotaClinica() != null ? seleccionada.getNotaClinica() : "Sin nota clínica") + "\n\n" +
                                  "FIRMA: " + 
                                  (seleccionada.getFirmaPsicologo() != null ? seleccionada.getFirmaPsicologo() : "Sin firma") + "\n\n" +
                                  "CAUSAL LEGAL: " + cbCausales.getValue() + "\n" +
                                  "AUTORIZADO POR: " + txtAutorizador.getText() + "\n" +
                                  "REGISTRADO EN AUDITORÍA: Sí";
                
                expediente.setContentText(contenido);
                expediente.showAndWait();
                
                listView.refresh();
            }
        });
    }
    
    private void volverAPrincipal() {
        VistaPrincipal vistaPrincipal = new VistaPrincipal(stage);
        Scene scene = new Scene(vistaPrincipal.getRoot(), 600, 500);
        stage.setScene(scene);
        stage.setTitle("Sistema de Apoyo Psicológico Estudiantil");
    }
    
    public VBox getRoot() {
        return root;
    }
}
