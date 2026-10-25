import javafx.application.Application;
import javafx.stage.Stage;
import vista.VistaPrincipal;

public class Main extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        try {
            VistaPrincipal vistaPrincipal = new VistaPrincipal(primaryStage);
            vistaPrincipal.mostrar();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error al iniciar la aplicación: " + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
