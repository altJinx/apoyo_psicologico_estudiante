package vista;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import modelo.*;

public class VistaSecretaria {
    private Stage stage;
    private VBox root;
    private SimuladorDatos datos;
    
    public VistaSecretaria(Stage stage) {
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
        header.setStyle("-fx-background-color: #9b59b6; -fx-background-radius: 5;");
        
        Label titulo = new Label("Secretaría de Bienestar");
        titulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: white;");
        
        Label info = new Label("Vista Ciega - Solo datos demográficos");
        info.setStyle("-fx-font-size: 12px; -fx-text-fill: #ecf0f1;");
        
        Button btnVolver = new Button("← Volver");
        btnVolver.setStyle("-fx-background-color: #ecf0f1;");
        btnVolver.setOnAction(e -> volverAPrincipal());
        
        header.getChildren().addAll(btnVolver, titulo, info);
        header.setHgrow(titulo, Priority.ALWAYS);
        
        // Contenido
        VBox contenido = new VBox(15);
        contenido.setPadding(new Insets(20));
        
        Label lblInstruccion = new Label("⚠️ Agendar Cita Presencial (Vista Ciega)");
        lblInstruccion.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #e74c3c;");
        
        Label lblAdvertencia = new Label("Nota: Solo tiene acceso a datos demográficos básicos. " +
                                        "No se muestran diagnósticos, notas clínicas ni triage.");
        lblAdvertencia.setStyle("-fx-font-size: 12px; -fx-text-fill: #7f8c8d; -fx-font-style: italic;");
        
        // Formulario de agendamiento
        GridPane grid = new GridPane();
        grid.setHgap(15);
        grid.setVgap(15);
        grid.setPadding(new Insets(20));
        
        ComboBox<Estudiante> cbEstudiantes = new ComboBox<>();
        cbEstudiantes.getItems().addAll(datos.getEstudiantes());
        cbEstudiantes.setPromptText("Seleccione estudiante");
        
        ComboBox<Psicologo> cbPsicologos = new ComboBox<>();
        cbPsicologos.getItems().addAll(datos.getPsicologos());
        cbPsicologos.setPromptText("Seleccione psicólogo");
        
        ComboBox<String> cbHorarios = new ComboBox<>();
        cbHorarios.getItems().addAll("Lunes 9:00-10:00", "Lunes 10:00-11:00",
                                     "Martes 9:00-10:00", "Martes 14:00-15:00",
                                     "Miércoles 9:00-10:00", "Miércoles 15:00-16:00",
                                     "Jueves 10:00-11:00", "Jueves 14:00-15:00",
                                     "Viernes 9:00-10:00", "Viernes 11:00-12:00");
        cbHorarios.setPromptText("Seleccione horario");
        
        ComboBox<String> cbTipo = new ComboBox<>();
        cbTipo.getItems().addAll("PRESENCIAL");
        cbTipo.getSelectionModel().selectFirst();
        
        grid.add(new Label("Estudiante:"), 0, 0);
        grid.add(cbEstudiantes, 1, 0);
        grid.add(new Label("Psicólogo:"), 0, 1);
        grid.add(cbPsicologos, 1, 1);
        grid.add(new Label("Horario:"), 0, 2);
        grid.add(cbHorarios, 1, 2);
        grid.add(new Label("Tipo:"), 0, 3);
        grid.add(cbTipo, 1, 3);
        
        Button btnAgendar = new Button("📅 Agendar Cita Presencial");
        btnAgendar.setStyle("-fx-font-size: 14px; -fx-pref-width: 200px; -fx-background-color: #9b59b6; -fx-text-fill: white;");
        btnAgendar.setOnAction(e -> {
            if (cbEstudiantes.getValue() != null && cbPsicologos.getValue() != null && 
                cbHorarios.getValue() != null) {
                Cita nuevaCita = new Cita("C" + System.currentTimeMillis(),
                                         cbEstudiantes.getValue(),
                                         cbPsicologos.getValue(),
                                         java.time.LocalDateTime.now().plusDays(1),
                                         cbTipo.getValue());
                datos.agregarCita(nuevaCita);
                
                Alert confirm = new Alert(Alert.AlertType.INFORMATION);
                confirm.setTitle("Cita Agendada");
                confirm.setHeaderText("Reserva Exitosa");
                confirm.setContentText("Cita presencial agendada:\n" +
                                     "Estudiante: " + cbEstudiantes.getValue().getNombreCompleto() + "\n" +
                                     "Psicólogo: " + cbPsicologos.getValue().getNombreCompleto() + "\n" +
                                     "Horario: " + cbHorarios.getValue() + "\n" +
                                     "Tipo: " + cbTipo.getValue());
                confirm.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Datos Incompletos");
                alert.setHeaderText("Complete todos los campos");
                alert.setContentText("Por favor, seleccione estudiante, psicólogo y horario.");
                alert.showAndWait();
            }
        });
        
        VBox formulario = new VBox(15);
        formulario.getChildren().addAll(grid, btnAgendar);
        
        // Lista de citas agendadas por secretaría
        Label lblCitas = new Label("Citas Agendadas por Secretaría");
        lblCitas.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        
        ListView<Cita> listViewCitas = new ListView<>();
        var citasPresenciales = datos.getCitas().stream()
            .filter(c -> c.getTipo().equals("PRESENCIAL"))
            .toList();
        listViewCitas.getItems().addAll(citasPresenciales);
        
        listViewCitas.setCellFactory(param -> new ListCell<Cita>() {
            @Override
            protected void updateItem(Cita cita, boolean empty) {
                super.updateItem(cita, empty);
                if (empty || cita == null) {
                    setText(null);
                } else {
                    setText(cita.getEstudiante().getNombreCompleto() + 
                           " - " + cita.getPsicologo().getNombreCompleto() + 
                           " - " + cita.getFechaHora() + 
                           " - " + cita.getEstado());
                }
            }
        });
        
        contenido.getChildren().addAll(lblInstruccion, lblAdvertencia, formulario, lblCitas, listViewCitas);
        
        root.getChildren().addAll(header, contenido);
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
