package vista;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class VistaPrincipal {
    private Stage stage;
    private VBox root;
    
    public VistaPrincipal(Stage stage) {
        this.stage = stage;
        crearVista();
    }
    
    private void crearVista() {
        root = new VBox(20);
        root.setPadding(new Insets(30));
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #f5f5f5;");
        
        Label titulo = new Label("Sistema de Apoyo Psicológico Estudiantil");
        titulo.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        
        Label subtitulo = new Label("Departamento de Bienestar Universitario");
        subtitulo.setStyle("-fx-font-size: 14px; -fx-text-fill: #7f8c8d;");
        
        Label seleccionLabel = new Label("Seleccione su rol:");
        seleccionLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #34495e;");
        
        Button btnEstudiante = new Button("👨‍🎓 Estudiante");
        btnEstudiante.setStyle("-fx-font-size: 14px; -fx-pref-width: 250px; -fx-pref-height: 40px;");
        btnEstudiante.setOnAction(e -> navegarAEstudiante());
        
        Button btnPsicologo = new Button("👩‍⚕️ Psicólogo");
        btnPsicologo.setStyle("-fx-font-size: 14px; -fx-pref-width: 250px; -fx-pref-height: 40px;");
        btnPsicologo.setOnAction(e -> navegarAPsicologo());
        
        Button btnSecretaria = new Button("📋 Secretaría de Bienestar");
        btnSecretaria.setStyle("-fx-font-size: 14px; -fx-pref-width: 250px; -fx-pref-height: 40px;");
        btnSecretaria.setOnAction(e -> navegarASecretaria());
        
        Button btnDirector = new Button("👔 Director de Bienestar");
        btnDirector.setStyle("-fx-font-size: 14px; -fx-pref-width: 250px; -fx-pref-height: 40px;");
        btnDirector.setOnAction(e -> navegarADirector());
        
        root.getChildren().addAll(titulo, subtitulo, seleccionLabel, btnEstudiante, 
                                 btnPsicologo, btnSecretaria, btnDirector);
    }
    
    private void navegarAEstudiante() {
        VistaEstudiante vistaEstudiante = new VistaEstudiante(stage);
        Scene scene = new Scene(vistaEstudiante.getRoot(), 900, 700);
        stage.setScene(scene);
        stage.setTitle("Estudiante - Sistema de Apoyo Psicológico");
    }
    
    private void navegarAPsicologo() {
        VistaPsicologo vistaPsicologo = new VistaPsicologo(stage);
        Scene scene = new Scene(vistaPsicologo.getRoot(), 900, 700);
        stage.setScene(scene);
        stage.setTitle("Psicólogo - Sistema de Apoyo Psicológico");
    }
    
    private void navegarASecretaria() {
        VistaSecretaria vistaSecretaria = new VistaSecretaria(stage);
        Scene scene = new Scene(vistaSecretaria.getRoot(), 900, 700);
        stage.setScene(scene);
        stage.setTitle("Secretaría - Sistema de Apoyo Psicológico");
    }
    
    private void navegarADirector() {
        VistaDirector vistaDirector = new VistaDirector(stage);
        Scene scene = new Scene(vistaDirector.getRoot(), 900, 700);
        stage.setScene(scene);
        stage.setTitle("Director - Sistema de Apoyo Psicológico");
    }
    
    public VBox getRoot() {
        return root;
    }
    
    public void mostrar() {
        Scene scene = new Scene(root, 600, 500);
        stage.setScene(scene);
        stage.setTitle("Sistema de Apoyo Psicológico Estudiantil");
        stage.show();
    }
}
