package vista;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import modelo.*;
import java.time.LocalDateTime;

public class VistaEstudiante {
    private Stage stage;
    private VBox root;
    private SimuladorDatos datos;
    private Estudiante estudianteActual;
    
    public VistaEstudiante(Stage stage) {
        this.stage = stage;
        this.datos = SimuladorDatos.getInstancia();
        this.estudianteActual = datos.getEstudiantes().get(0); // Simulación: primer estudiante
        crearVista();
    }
    
    private void crearVista() {
        root = new VBox(10);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #f5f5f5;");
        
        // Header
        HBox header = new HBox(20);
        header.setPadding(new Insets(10));
        header.setStyle("-fx-background-color: #3498db; -fx-background-radius: 5;");
        
        Label titulo = new Label("Portal del Estudiante");
        titulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: white;");
        
        Label estudianteInfo = new Label("Bienvenido, " + estudianteActual.getNombreCompleto());
        estudianteInfo.setStyle("-fx-font-size: 14px; -fx-text-fill: white;");
        
        Button btnVolver = new Button("← Volver");
        btnVolver.setStyle("-fx-background-color: #ecf0f1;");
        btnVolver.setOnAction(e -> volverAPrincipal());
        
        header.getChildren().addAll(btnVolver, titulo, estudianteInfo);
        header.setHgrow(titulo, Priority.ALWAYS);
        
        // Botones de funcionalidad
        VBox contenido = new VBox(15);
        contenido.setPadding(new Insets(20));
        
        GridPane grid = new GridPane();
        grid.setHgap(15);
        grid.setVgap(15);
        grid.setPadding(new Insets(20));
        
        Button btnSOS = new Button("🆘 SOS / Atención Inmediata");
        btnSOS.setStyle("-fx-font-size: 14px; -fx-pref-width: 280px; -fx-pref-height: 60px; -fx-background-color: #e74c3c; -fx-text-fill: white;");
        btnSOS.setOnAction(e -> mostrarSOS());
        
        Button btnAgendar = new Button("📅 Agendar Cita");
        btnAgendar.setStyle("-fx-font-size: 14px; -fx-pref-width: 280px; -fx-pref-height: 60px;");
        btnAgendar.setOnAction(e -> mostrarAgendarCita());
        
        Button btnCancelar = new Button("❌ Cancelar/Reprogramar Cita");
        btnCancelar.setStyle("-fx-font-size: 14px; -fx-pref-width: 280px; -fx-pref-height: 60px;");
        btnCancelar.setOnAction(e -> mostrarCancelarCita());
        
        Button btnAntecedentes = new Button("📁 Cargar Antecedentes");
        btnAntecedentes.setStyle("-fx-font-size: 14px; -fx-pref-width: 280px; -fx-pref-height: 60px;");
        btnAntecedentes.setOnAction(e -> mostrarAntecedentes());
        
        Button btnRetroalimentacion = new Button("⭐ Retroalimentación");
        btnRetroalimentacion.setStyle("-fx-font-size: 14px; -fx-pref-width: 280px; -fx-pref-height: 60px;");
        btnRetroalimentacion.setOnAction(e -> mostrarRetroalimentacion());
        
        grid.add(btnSOS, 0, 0);
        grid.add(btnAgendar, 1, 0);
        grid.add(btnCancelar, 0, 1);
        grid.add(btnAntecedentes, 1, 1);
        grid.add(btnRetroalimentacion, 0, 2);
        
        contenido.getChildren().add(grid);
        
        // Simulación de internet
        HBox internetSim = new HBox(10);
        internetSim.setPadding(new Insets(10));
        CheckBox chkInternet = new CheckBox("Simular falla de internet");
        chkInternet.setSelected(datos.isInternetDisponible());
        chkInternet.setOnAction(e -> datos.setInternetDisponible(chkInternet.isSelected()));
        internetSim.getChildren().add(chkInternet);
        
        root.getChildren().addAll(header, contenido, internetSim);
    }
    
    private void mostrarSOS() {
        Alert alert;
        if (datos.isInternetDisponible()) {
            var psicologosDisponibles = datos.getPsicologosDisponibles();
            if (psicologosDisponibles.isEmpty()) {
                alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("SOS - Sin psicólogos disponibles");
                alert.setHeaderText("No hay psicólogos disponibles en este momento");
                alert.setContentText("Por favor, intente más tarde o use el botón de emergencia.");
                alert.showAndWait();
            } else {
                Psicologo asignado = psicologosDisponibles.get(0);
                Cita citaSOS = new Cita("SOS-" + System.currentTimeMillis(), 
                                      estudianteActual, asignado, LocalDateTime.now(), "SOS");
                citaSOS.setEstado(EstadoCita.PROGRAMADA);
                datos.agregarCita(citaSOS);
                
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("SOS - Conexión Exitosa");
                alert.setHeaderText("Llamada en curso");
                alert.setContentText("Conectando con " + asignado.getNombreCompleto() + 
                                   "\nEspecialidad: " + asignado.getEspecialidad() +
                                   "\n\nPor favor, espere un momento...");
                alert.showAndWait();
            }
        } else {
            // Fallback offline
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("SOS - Sin Conexión");
            alert.setHeaderText("No hay conexión a internet");
            alert.setContentText("Se ha activado el protocolo de contingencia.\n\n" +
                               "Teléfono de emergencia: +56 9 1234 5678\n" +
                               "Línea 24/7: 800 123 4567");
            
            ButtonType btnLlamar = new ButtonType("📞 Llamar Ahora");
            alert.getButtonTypes().setAll(btnLlamar, ButtonType.CANCEL);
            
            alert.showAndWait().ifPresent(response -> {
                if (response == btnLlamar) {
                    Alert confirmacion = new Alert(Alert.AlertType.INFORMATION);
                    confirmacion.setTitle("Llamada");
                    confirmacion.setHeaderText("Iniciando llamada...");
                    confirmacion.setContentText("Marcando +56 9 1234 5678...");
                    confirmacion.showAndWait();
                }
            });
        }
    }
    
    private void mostrarAgendarCita() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Agendar Cita");
        dialog.setHeaderText("Seleccione horario disponible");
        
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        
        ComboBox<Psicologo> cbPsicologos = new ComboBox<>();
        var psicologosFacultad = datos.getPsicologosPorFacultad(estudianteActual.getFacultad());
        cbPsicologos.getItems().addAll(psicologosFacultad);
        if (!psicologosFacultad.isEmpty()) {
            cbPsicologos.getSelectionModel().selectFirst();
        }
        
        ComboBox<String> cbHorarios = new ComboBox<>();
        cbHorarios.getItems().addAll("Mañana 9:00-10:00", "Mañana 10:00-11:00", 
                                     "Tarde 14:00-15:00", "Tarde 15:00-16:00", 
                                     "Tarde 16:00-17:00");
        cbHorarios.getSelectionModel().selectFirst();
        
        ComboBox<String> cbTipo = new ComboBox<>();
        cbTipo.getItems().addAll("VIRTUAL", "PRESENCIAL");
        cbTipo.getSelectionModel().selectFirst();
        
        grid.add(new Label("Psicólogo:"), 0, 0);
        grid.add(cbPsicologos, 1, 0);
        grid.add(new Label("Horario:"), 0, 1);
        grid.add(cbHorarios, 1, 1);
        grid.add(new Label("Tipo:"), 0, 2);
        grid.add(cbTipo, 1, 2);
        
        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        
        dialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK && cbPsicologos.getValue() != null) {
                Psicologo seleccionado = cbPsicologos.getValue();
                Cita nuevaCita = new Cita("C" + System.currentTimeMillis(), 
                                         estudianteActual, seleccionado, 
                                         LocalDateTime.now().plusDays(1), cbTipo.getValue());
                datos.agregarCita(nuevaCita);
                
                Alert confirm = new Alert(Alert.AlertType.INFORMATION);
                confirm.setTitle("Cita Agendada");
                confirm.setHeaderText("Reserva Exitosa");
                confirm.setContentText("Su cita ha sido agendada con:\n" +
                                     "Psicólogo: " + seleccionado.getNombreCompleto() + "\n" +
                                     "Horario: " + cbHorarios.getValue() + "\n" +
                                     "Tipo: " + cbTipo.getValue() + "\n\n" +
                                     "Puede cancelar o reprogramar sin penalización.");
                confirm.showAndWait();
            }
        });
    }
    
    private void mostrarCancelarCita() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Cancelar/Reprogramar Cita");
        dialog.setHeaderText("Seleccione una cita para modificar");
        
        ListView<Cita> listView = new ListView<>();
        var citasEstudiante = datos.getCitas().stream()
            .filter(c -> c.getEstudiante().getId().equals(estudianteActual.getId()) && 
                        c.getEstado() == EstadoCita.PROGRAMADA)
            .toList();
        listView.getItems().addAll(citasEstudiante);
        
        dialog.getDialogPane().setContent(listView);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        
        dialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK && listView.getSelectionModel().getSelectedItem() != null) {
                Cita seleccionada = listView.getSelectionModel().getSelectedItem();
                
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Modificar Cita");
                alert.setHeaderText("¿Qué desea hacer?");
                alert.setContentText("Cita con " + seleccionada.getPsicologo().getNombreCompleto());
                
                ButtonType btnCancelar = new ButtonType("Cancelar Cita");
                ButtonType btnReprogramar = new ButtonType("Reprogramar");
                alert.getButtonTypes().setAll(btnCancelar, btnReprogramar, ButtonType.CANCEL);
                
                alert.showAndWait().ifPresent(acciones -> {
                    if (acciones == btnCancelar) {
                        seleccionada.setEstado(EstadoCita.CANCELADA);
                        Alert info = new Alert(Alert.AlertType.INFORMATION);
                        info.setTitle("Cita Cancelada");
                        info.setHeaderText("Cancelación sin penalización");
                        info.setContentText("Su cita ha sido cancelada exitosamente.");
                        info.showAndWait();
                    } else if (acciones == btnReprogramar) {
                        Alert info = new Alert(Alert.AlertType.INFORMATION);
                        info.setTitle("Reprogramar");
                        info.setHeaderText("Funcionalidad de reprogramación");
                        info.setContentText("En una implementación completa, aquí se mostrarían\n" +
                                           "nuevos horarios disponibles para reprogramar.");
                        info.showAndWait();
                    }
                });
            }
        });
    }
    
    private void mostrarAntecedentes() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Cargar Antecedentes");
        dialog.setHeaderText("Documentos clínicos previos");
        
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));
        
        ListView<String> listaDocumentos = new ListView<>();
        listaDocumentos.getItems().addAll(estudianteActual.getAntecedentesDocumentos());
        
        Button btnAgregar = new Button("📎 Agregar Documento");
        btnAgregar.setOnAction(e -> {
            TextInputDialog input = new TextInputDialog();
            input.setTitle("Agregar Documento");
            input.setHeaderText("Nombre del archivo");
            input.setContentText("Ingrese el nombre del documento:");
            input.showAndWait().ifPresent(nombre -> {
                estudianteActual.agregarAntecedente(nombre);
                listaDocumentos.getItems().add(nombre);
            });
        });
        
        vbox.getChildren().addAll(new Label("Documentos cargados:"), listaDocumentos, btnAgregar);
        dialog.getDialogPane().setContent(vbox);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CLOSE);
        
        dialog.showAndWait();
    }
    
    private void mostrarRetroalimentacion() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Retroalimentación");
        dialog.setHeaderText("Califique su atención");
        
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        
        ComboBox<Integer> cbCalificacion = new ComboBox<>();
        cbCalificacion.getItems().addAll(1, 2, 3, 4, 5);
        cbCalificacion.getSelectionModel().select(5);
        
        TextArea txtComentario = new TextArea();
        txtComentario.setPromptText("Deje sus comentarios sobre la atención recibida...");
        txtComentario.setPrefRowCount(3);
        
        grid.add(new Label("Calificación (1-5):"), 0, 0);
        grid.add(cbCalificacion, 1, 0);
        grid.add(new Label("Comentarios:"), 0, 1);
        grid.add(txtComentario, 1, 1);
        
        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        
        dialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                var citasCompletadas = datos.getCitas().stream()
                    .filter(c -> c.getEstudiante().getId().equals(estudianteActual.getId()) && 
                                c.getEstado() == EstadoCita.COMPLETADA)
                    .findFirst();
                
                if (citasCompletadas.isPresent()) {
                    Retroalimentacion retro = new Retroalimentacion(
                        "R" + System.currentTimeMillis(),
                        citasCompletadas.get(),
                        cbCalificacion.getValue(),
                        txtComentario.getText()
                    );
                    datos.agregarRetroalimentacion(retro);
                    estudianteActual.agregarRetroalimentacion(retro);
                    
                    Alert confirm = new Alert(Alert.AlertType.INFORMATION);
                    confirm.setTitle("Gracias");
                    confirm.setHeaderText("Retroalimentación registrada");
                    confirm.setContentText("¡Gracias por su opinión! Nos ayuda a mejorar.");
                    confirm.showAndWait();
                }
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
