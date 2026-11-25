package Registro;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class RegistroDeProductosApplication extends Application{
    @Override
    public void start(Stage stage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/practicadeparcial2/RegistroDeProductos-view.fxml"));

        Scene scene = new Scene(loader.load());
        stage.setTitle("registro de productos");
        stage.setScene(scene);
        stage.show();
    }
}
