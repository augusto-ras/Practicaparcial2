package practica;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class AdministradorDeTareasController {
    @FXML
    private TextField txtNombre;

    @FXML
    private ListView<String> listaTareas;

    private final ObservableList<String> tareas = FXCollections.observableArrayList();

    private final Path archivo = Path.of("tareas.txt");

    @FXML
    public void initialize(){
        listaTareas.setItems(tareas);
        cargarTareas();
    }

    @FXML
    private void agregarTarea(){
        String nombre = txtNombre.getText().trim();

        if(nombre.isEmpty()){
            mostrarMensaje(Alert.AlertType.WARNING,"Campo vacío", "Debe ingresar el nombre de una tarea.");
            return;
        }

        tareas.add(nombre);

        guardarTareas();
        txtNombre.clear();
        txtNombre.requestFocus();

    }

    @FXML
    private void eliminarTarea(){

        String seleccionada =listaTareas.getSelectionModel().getSelectedItem();

        if (seleccionada == null){
            mostrarMensaje(Alert.AlertType.WARNING,"Sin selección", "Debe seleccionar una tarea para eliminar.");
            return;
        }

        tareas.remove(seleccionada);

        guardarTareas();

    }
    private void guardarTareas(){
        try {
            Files.write(archivo, tareas, StandardCharsets.UTF_8);
        } catch (IOException e){
            mostrarMensaje(Alert.AlertType.ERROR, "Error al guardar", "No se pudieron guardar las tareas.");
            e.printStackTrace();
        }
    }

    private void cargarTareas(){
        try {
            if(Files.exists(archivo)){
                List<String> limeas = Files.readAllLines(archivo,StandardCharsets.UTF_8);
                tareas.setAll(limeas);
            }
        }catch (IOException e){
            mostrarMensaje(Alert.AlertType.ERROR, "Error al cargar", "No se pudieron cargar las tareas.");
            e.printStackTrace();
        }
    }

    private void mostrarMensaje(Alert.AlertType tipo, String titulo, String mensaje){
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
