package GUI.controllers;

import java.sql.SQLException;
import java.util.function.UnaryOperator;
import GUI.windows.UserSessionManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import logic.DAOs.PropiedadDAO;
import logic.classes.Agente;
import logic.classes.Propiedad;

public class RegistrarNuevaPropiedadController {
    @FXML
    private TextField textFieldResumen;

    @FXML
    private TextField textFieldCiudad;

    @FXML
    private ComboBox<String> comboBoxZona;

    @FXML
    private TextField textFieldDireccion;

    @FXML
    private CheckBox checkBoxDepartamento;

    @FXML
    private CheckBox checkBoxCuarto;

    @FXML
    private CheckBox checkBoxCasa;

    @FXML
    private CheckBox checkBoxRenta;

    @FXML
    private CheckBox checkBoxVenta;

    @FXML
    private TextField textFieldPrecio;

    @FXML
    private TextField textFieldNumeroHabitaciones;

    @FXML
    private TextField textFieldNumeroEstancias;

    @FXML
    private TextField textFieldNumeroBanios;

    @FXML
    private CheckBox checkBoxSiCochera;

    @FXML
    private CheckBox checkBoxNoCochera;

    @FXML
    private TextField textFieldCantidadCochera;

    @FXML
    private TextField textFieldTamanioMetros;

    @FXML
    private TextArea textAreaDescripcion;

    @FXML
    private Button buttonFinalizarRegistro;

    @FXML 
    private Button buttonCancelar;

    @FXML
    private Label labelUser;

    @FXML
    public void initialize(){
        //Agente agenteData = UserSessionManager.getInstance().getAgenteData();
        //labelUser.setText(agenteData.getUsuarioAgente());
        ObservableList<String> opcionesZona = FXCollections.observableArrayList();
        opcionesZona.add("Centro");
        opcionesZona.add("Orilla");
        opcionesZona.add("Residencial");
        comboBoxZona.setItems(opcionesZona);
        aplicarTextFormatterNumerico(textFieldCantidadCochera);
        aplicarTextFormatterNumerico(textFieldPrecio);
        aplicarTextFormatterNumerico(textFieldNumeroHabitaciones);
        aplicarTextFormatterNumerico(textFieldNumeroEstancias);
        aplicarTextFormatterNumerico(textFieldNumeroBanios);
        aplicarTextFormatterNumerico(textFieldTamanioMetros);
        textFieldCantidadCochera.setDisable(true);
    }

    private void aplicarTextFormatterNumerico(TextField textField) {
        UnaryOperator<TextFormatter.Change> filtro = change -> {
            String newText = change.getControlNewText();
            if (newText.isEmpty() || newText.matches("[0-9]+")) {
                return change;
            }
            return null;
        };
        TextFormatter<String> textFormatter = new TextFormatter<>(filtro);
        textField.setTextFormatter(textFormatter);
    }

    @FXML
    public void seleccionarDepartamento(ActionEvent event){
        if(checkBoxDepartamento.isSelected()){
            checkBoxCuarto.setSelected(false);
            checkBoxCasa.setSelected(false);
        }
    }

    @FXML
    public void seleccionarCasa(ActionEvent event){
        if(checkBoxCasa.isSelected()){
            checkBoxDepartamento.setSelected(false);
            checkBoxCuarto.setSelected(false);
        }
    }

    @FXML
    public void seleccionarCuarto(ActionEvent event){
        if(checkBoxCuarto.isSelected()){
            checkBoxDepartamento.setSelected(false);
            checkBoxCasa.setSelected(false);
        }
    }

    @FXML
    public void seleccionarRenta(ActionEvent event){
        if(checkBoxRenta.isSelected()){
            checkBoxVenta.setSelected(false);
        }
    }

    @FXML
    public void seleccionarVenta(ActionEvent event){
        if(checkBoxVenta.isSelected()){
            checkBoxRenta.setSelected(false);
        }
    }

    @FXML
    public void seleccionarSiCochera(ActionEvent event){
        if(checkBoxSiCochera.isSelected()){
            checkBoxSiCochera.setDisable(true);
            checkBoxNoCochera.setSelected(false);
            textFieldCantidadCochera.setDisable(false);
        }
    }

    @FXML
    public void seleccionarNoCochera(ActionEvent event){
        if(checkBoxNoCochera.isSelected()){
            checkBoxSiCochera.setDisable(false);
            checkBoxSiCochera.setSelected(false);
            textFieldCantidadCochera.setDisable(true);
            textFieldCantidadCochera.setText(null);
        }
    }

    @FXML
    public void registrarPropiedad(ActionEvent event){
        Propiedad propiedad = new Propiedad();
        String resumen = textFieldResumen.getText();
        String ciudad = textFieldCiudad.getText();
        String zona = comboBoxZona.getValue();
        String direccion = textFieldDireccion.getText();
        
        String descripcion = textAreaDescripcion.getText();

        if(!resumen.isBlank() && !ciudad.isBlank() && ((zona != null && !zona.isBlank())|| (zona != null && !zona.equals("Introduce la zona"))) &&
        !direccion.isBlank() && (checkBoxDepartamento.isSelected() || checkBoxCuarto.isSelected() || checkBoxCasa.isSelected()) && (checkBoxRenta.isSelected() || checkBoxVenta.isSelected()) &&
        (!textFieldPrecio.getText().isBlank() || (Integer.parseInt(textFieldPrecio.getText()) != 0) && (!textFieldNumeroHabitaciones.getText().isBlank() || (Integer.parseInt(textFieldNumeroHabitaciones.getText())) != 0) && (!textFieldNumeroEstancias.getText().isBlank() || (Integer.parseInt(textFieldNumeroEstancias.getText())) != 0) && 
        (!textFieldNumeroBanios.getText().isBlank() || (Integer.parseInt(textFieldNumeroBanios.getText())) != 0) && ((checkBoxSiCochera.isSelected() && (!textFieldCantidadCochera.getText().isBlank() || (Integer.parseInt(textFieldCantidadCochera.getText())) != 0))|| checkBoxNoCochera.isSelected()) && 
        (!textFieldTamanioMetros.getText().isBlank() || (Integer.parseInt(textFieldTamanioMetros.getText())) != 0) && !descripcion.isBlank())){
            int precio = Integer.parseInt(textFieldPrecio.getText());
            int noHabitaciones = Integer.parseInt(textFieldNumeroHabitaciones.getText());
            int noEstancias = Integer.parseInt(textFieldNumeroEstancias.getText());
            int noBanios = Integer.parseInt(textFieldNumeroBanios.getText());
            int noCocheras = Integer.parseInt(textFieldCantidadCochera.getText());
            int tamanio = Integer.parseInt(textFieldTamanioMetros.getText());
            propiedad.setResumen(resumen);
            propiedad.setCiudad(ciudad);
            propiedad.setZona(zona);
            propiedad.setDireccion(direccion);
            propiedad.setPrecio(precio);
            propiedad.setNoHabitaciones(noHabitaciones);
            propiedad.setNoEstancias(noEstancias);
            propiedad.setNoBanios(noBanios);
            propiedad.setCochera(noCocheras);
            propiedad.setTamanio(tamanio);
            propiedad.setDescripcion(descripcion);
            propiedad.setUsuarioAgente("Agente de Prueba");
            try {
                PropiedadDAO propiedadDAO = new PropiedadDAO();
                propiedadDAO.agregarPropiedad(propiedad);
                Alert agregoPropiedad = new Alert(AlertType.INFORMATION);
                agregoPropiedad.setTitle("Confirmación registro");
                agregoPropiedad.setHeaderText("Confirmación se agrego la propiedad");
                agregoPropiedad.setContentText("Se abrio de manera exitosa la propiedad");
                agregoPropiedad.show();
            } catch (SQLException e) {
                Alert alertBaseDeDatos = new Alert(AlertType.ERROR);
                alertBaseDeDatos.setTitle("Ocurrio un error en la base");
                alertBaseDeDatos.setContentText("Hubo un error al activar la colaboración");
                alertBaseDeDatos.setHeaderText("Ocurrio un error");
                alertBaseDeDatos.show();
                e.printStackTrace();
            }
        } else {
            Alert alertDatosVacios = new Alert(AlertType.ERROR);
            alertDatosVacios.setTitle("No ha ingresado datos correctos");
            alertDatosVacios.setContentText("Revise si hay datos por rellenar o dejo alguno en 0");
            alertDatosVacios.setHeaderText("Ocurrio un error");
            alertDatosVacios.show();
        }
    }
}
