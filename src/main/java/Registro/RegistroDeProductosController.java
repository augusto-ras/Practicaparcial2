package Registro;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.*;
import java.nio.file.Files;
public class RegistroDeProductosController {
    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtPrecio;

    @FXML
    private ChoiceBox<String> choiceCategoria;

    @FXML
    private TableView<Producto> tableProductos;

    @FXML
    private TableColumn<Producto, String> colNombre;

    @FXML
    private TableColumn<Producto, Double> colPrecio;

    @FXML
    private TableColumn<Producto, String> colCategoria;

    private List<Producto> listaProductos = new ArrayList<>();

    private final String ARCHIVO = "productos.dat";

    @FXML
    public void initialize(){
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));

        colCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));

        choiceCategoria.getItems().addAll("Electrónica", "Alimentos", "Ropa", "Limpieza", "Otros");



        cargarProductos();
        actualizarTabla();
    }

    @FXML
    public void btnAgregar(){
        String nombre = txtNombre.getText().trim();
        String precioTexto = txtPrecio.getText().trim();
        String categoria = choiceCategoria.getValue();

        if(nombre.isEmpty()){
            mostrarAlerta("Error", "Ingrese el nombre del producto");
            return;
        }

        double precio;

        try{
            precio = Double.parseDouble(precioTexto);
            if(!Double.isFinite(precio) || precio <= 0){
                mostrarAlerta("Error", "El precio debe ser mayor a cero.");
                return;

            }
        }catch (NumberFormatException e){
            mostrarAlerta("Error", "Ingrese un precio válido.");
            return;
        }

        if (categoria == null) {
            mostrarAlerta("Error", "Seleccione una categoría.");
            return;
        }

        Producto producto = new Producto(nombre, precio, categoria);

        listaProductos.add(producto);

        guardarProductos();
        actualizarTabla();
        txtNombre.clear();
        txtPrecio.clear();
        choiceCategoria.getSelectionModel().clearSelection();
    }

    @FXML
    public void btnEliminar(){

        Producto seleccionado =  tableProductos.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            mostrarAlerta("Error", "Seleccione un producto para eliminar.");
            return;
        }
        listaProductos.remove(seleccionado);

        guardarProductos();
        actualizarTabla();
    }

    private void guardarProductos(){
        try(ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(ARCHIVO))){
            List<Producto> productos = new ArrayList<>(listaProductos);
            salida.writeObject(productos);
        } catch (IOException e){
            mostrarAlerta("Error", "No se pudieron guardar los productos.");
            e.printStackTrace();
        }

    }
    @SuppressWarnings("unchecked")
    private void cargarProductos(){
        File archivo = new File(ARCHIVO);

        if(!archivo.exists()){
            return;
        }

        try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(archivo))){
            List<Producto> productos = (List<Producto>) entrada.readObject();

            listaProductos = productos;
        } catch (IOException |  ClassNotFoundException e){
            mostrarAlerta("Error", "No se pudieron cargar los productos.");
            e.printStackTrace();
        }


    }
    private void actualizarTabla() {
        tableProductos.getItems().setAll(listaProductos);
        tableProductos.refresh();
    }
    private void mostrarAlerta(String titulo, String mensaje) {

        Alert alerta = new Alert(Alert.AlertType.INFORMATION);

        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);

        alerta.showAndWait();
    }




}
