package practica;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AdministradorDeTareasApplication extends Application{

    @Override
    public void start(Stage stage) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(
                AdministradorDeTareasApplication.class
                        .getResource("/com/example/practicadeparcial2/AdministradorDeTareas-view.fxml")
        );
        Scene scene = new Scene(fxmlLoader.load());

        stage.setTitle("Administrador de Tareas");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }


}
