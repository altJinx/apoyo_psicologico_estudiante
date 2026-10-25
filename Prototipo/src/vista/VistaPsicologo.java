package vista;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import modelo.*;

public class VistaPsicologo {
    private Stage stage;
    private VBox root;
    private SimuladorDatos datos;
    private Psicologo psicologoActual;
    
    public VistaPsicologo(Stage stage) {
        this.stage = stage;
        this.datos = SimuladorDatos.getInstancia();
        this.psicologoActual = datos.getPsicologos().get(0); // Simulación: primer psicólogo
        crearVista();
    }
    
    private void crearVista() {
        root = new VBox(10);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #f5f5f5;");
        
        // Header
        HBox header = new HBox(20);
        header.setPadding(new Insets(10));
        header.setStyle("-fx-background-color: #27ae60; -fx-background-radius: 5;");
        
        Label titulo = new Label("Portal del Psicólogo");
        titulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: white;");
        
        Label psicologoInfo = new Label("Dr(a). " + psicologoActual.getNombreCompleto());
        psicologoInfo.setStyle("-fx-font-size: 14px; -fx-text-fill: white;");
        
        Button btnVolver = new Button("← Volver");
        btnVolver.setStyle("-fx-background-color: #ecf0f1;");
        btnVolver.setOnAction(e -> volverAPrincipal());
        
        header.getChildren().addAll(btnVolver, titulo, psicologoInfo);
        header.setHgrow(titulo, Priority.ALWAYS);
        
        // Lista de citas del psicólogo
        VBox contenido = new VBox(15);
        contenido.setPadding(new Insets(20));
        
        Label lblCitas = new Label("Citas Asignadas");
        lblCitas.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        
        ListView<Cita> listViewCitas = new ListView<>();
        var citasPsicologo = datos.getCitas().stream()
            .filter(c -> c.getPsicologo().getId().equals(psicologoActual.getId()))
            .toList();
        listViewCitas.getItems().addAll(citasPsicologo);
        
        listViewCitas.setCellFactory(param -> new ListCell<Cita>() {
            @Override
            protected void updateItem(Cita cita, boolean empty) {
                super.updateItem(cita, empty);
                if (empty || cita == null) {
                    setText(null);
                } else {
                    setText(cita.getEstudiante().getNombreCompleto() + 
                           " - " + cita.getFechaHora() + 
                           " - " + cita.getEstado() +
                           " - Riesgo: " + cita.getNivelRiesgo());
                }
            }
        });
        
        // Botones de acción
        HBox botones = new HBox(10);
        
        Button btnTriage = new Button("📊 Triage Clínico");
        btnTriage.setStyle("-fx-background-color: #f39c12; -fx-text-fill: white;");
        btnTriage.setOnAction(e -> realizarTriage(listViewCitas));
        
        Button btnNotaClinica = new Button("📝 Nota Clínica y Firma");
        btnNotaClinica.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");
        btnNotaClinica.setOnAction(e -> redactarNotaClinica(listViewCitas));
        
        botones.getChildren().addAll(btnTriage, btnNotaClinica);
        
        contenido.getChildren().addAll(lblCitas, listViewCitas, botones);
        
        root.getChildren().addAll(header, contenido);
    }
    
    private void realizarTriage(ListView<Cita> listViewCitas) {
        Cita seleccionada = listViewCitas.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Triage Clínico");
            alert.setHeaderText("Seleccione una cita");
            alert.setContentText("Por favor, seleccione una cita de la lista para realizar el triage.");
            alert.showAndWait();
            return;
        }
        
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Triage Clínico Humano");
        dialog.setHeaderText("Estudiante: " + seleccionada.getEstudiante().getNombreCompleto());
        
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        
        ComboBox<NivelRiesgo> cbRiesgo = new ComboBox<>();
        cbRiesgo.getItems().addAll(NivelRiesgo.BAJO, NivelRiesgo.MODERADO, NivelRiesgo.ALTO_VITAL);
        cbRiesgo.getSelectionModel().select(NivelRiesgo.SIN_EVALUAR);
        
        TextArea txtObservaciones = new TextArea();
        txtObservaciones.setPromptText("Observaciones clínicas...");
        txtObservaciones.setPrefRowCount(4);
        
        grid.add(new Label("Nivel de Riesgo:"), 0, 0);
        grid.add(cbRiesgo, 1, 0);
        grid.add(new Label("Observaciones:"), 0, 1);
        grid.add(txtObservaciones, 1, 1);
        
        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        
        dialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK && cbRiesgo.getValue() != null) {
                seleccionada.setNivelRiesgo(cbRiesgo.getValue());
                
                Alert confirm = new Alert(Alert.AlertType.INFORMATION);
                confirm.setTitle("Triage Actualizado");
                confirm.setHeaderText("Riesgo Registrado");
                confirm.setContentText("Nivel de riesgo: " + cbRiesgo.getValue() + "\n" +
                                     "Observaciones: " + txtObservaciones.getText() + "\n\n" +
                                     "Registro actualizado en tiempo real.");
                confirm.showAndWait();
                
                // Actualizar la vista
                listViewCitas.refresh();
            }
        });
    }
    
    private void redactarNotaClinica(ListView<Cita> listViewCitas) {
        Cita seleccionada = listViewCitas.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Nota Clínica");
            alert.setHeaderText("Seleccione una cita");
            alert.setContentText("Por favor, seleccione una cita de la lista para redactar la nota clínica.");
            alert.showAndWait();
            return;
        }
        
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Nota Clínica y Firma");
        dialog.setHeaderText("Estudiante: " + seleccionada.getEstudiante().getNombreCompleto());
        
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));
        
        // Mostrar antecedentes
        Label lblAntecedentes = new Label("Antecedentes del Estudiante:");
        ListView<String> listaAntecedentes = new ListView<>();
        listaAntecedentes.getItems().addAll(seleccionada.getEstudiante().getAntecedentesDocumentos());
        listaAntecedentes.setPrefHeight(80);
        
        // Nota clínica
        TextArea txtNotaClinica = new TextArea();
        txtNotaClinica.setPromptText("Evolución de la sesión, observaciones, recomendaciones...");
        txtNotaClinica.setPrefRowCount(6);
        if (seleccionada.getNotaClinica() != null) {
            txtNotaClinica.setText(seleccionada.getNotaClinica());
        }
        
        // Firma simulada
        HBox firmaBox = new HBox(10);
        CheckBox chkFirma = new CheckBox("Firmar electrónicamente");
        TextField txtFirma = new TextField(psicologoActual.getNombreCompleto() + " - Firma Digital");
        txtFirma.setEditable(false);
        txtFirma.setDisable(true);
        
        chkFirma.setOnAction(e -> {
            txtFirma.setDisable(!chkFirma.isSelected());
            if (chkFirma.isSelected()) {
                txtFirma.setStyle("-fx-background-color: #d5f4e6; -fx-border-color: #27ae60;");
            } else {
                txtFirma.setStyle("");
            }
        });
        
        firmaBox.getChildren().addAll(chkFirma, txtFirma);
        
        vbox.getChildren().addAll(lblAntecedentes, listaAntecedentes, 
                                new Label("Nota Clínica:"), txtNotaClinica, firmaBox);
        
        dialog.getDialogPane().setContent(vbox);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        
        dialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                seleccionada.setNotaClinica(txtNotaClinica.getText());
                if (chkFirma.isSelected()) {
                    seleccionada.setFirmaPsicologo(txtFirma.getText());
                    seleccionada.setEstado(EstadoCita.COMPLETADA);
                    
                    Alert confirm = new Alert(Alert.AlertType.INFORMATION);
                    confirm.setTitle("Atención Cerrada");
                    confirm.setHeaderText("Nota Clínica Guardada y Firmada");
                    confirm.setContentText("La atención ha sido cerrada exitosamente.\n" +
                                         "Firma: " + txtFirma.getText() + "\n" +
                                         "Estado: COMPLETADA");
                    confirm.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Sin Firma");
                    alert.setHeaderText("Nota guardada sin firma");
                    alert.setContentText("La nota clínica ha sido guardada pero la atención\n" +
                                       "no se considera cerrada hasta que sea firmada.");
                    alert.showAndWait();
                }
                
                listViewCitas.refresh();
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
