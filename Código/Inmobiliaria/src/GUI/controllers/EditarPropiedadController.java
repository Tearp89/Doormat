package GUI.controllers;

import java.sql.SQLException;
import java.util.function.UnaryOperator;

<<<<<<< HEAD
import GUI.windows.ChangeWindowManager;
=======
import logic.DAOs.PropiedadDAO;
>>>>>>> origin/main
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import logic.classes.Propiedad;

public class EditarPropiedadController {
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
    private Button buttonEditarPropiedad;

    @FXML 
    private Button buttonCancelar;

    @FXML
    private Button buttonEliminar;

    @FXML
    private Label labelUser;

    private int idPropiedadUsada;

    @FXML
    public void initialize(int idPropiedad){
        //Agente agenteData = UserSessionManager.getInstance().getAgenteData();
        //labelUser.setText(agenteData.getUsuarioAgente());
        this.idPropiedadUsada = idPropiedad;
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

        PropiedadDAO propiedadDAO = new PropiedadDAO();
        try {
            Propiedad propiedad = propiedadDAO.obtenerPropiedadPorId(idPropiedad);
            textFieldResumen.setText(propiedad.getResumen());
            textFieldCiudad.setText(propiedad.getCiudad());
            comboBoxZona.setValue(propiedad.getZona());
            textFieldDireccion.setText(propiedad.getDireccion());
            String tipo = propiedad.getTipoPropiedad();
            if(tipo == "Departamento"){
                checkBoxDepartamento.setSelected(true);
            } else if(tipo == "Cuarto"){
                checkBoxCuarto.setSelected(true);
            } else {
                checkBoxCasa.setSelected(true);
            }

            String propiedadEn = propiedad.getPropiedadEn();
            if(propiedadEn == "Renta"){
                checkBoxRenta.setSelected(true);
            } else {
                checkBoxVenta.setSelected(true);
            }

            textFieldPrecio.setText(String.valueOf(propiedad.getPrecio()));
            textFieldNumeroHabitaciones.setText(String.valueOf(propiedad.getNoHabitaciones()));
            textFieldNumeroEstancias.setText(String.valueOf(propiedad.getNoEstancias()));
            textFieldNumeroBanios.setText(String.valueOf(propiedad.getNoBanios()));

            int cochera = propiedad.getCochera();
            if(cochera > 0){
                checkBoxSiCochera.setSelected(true);
                checkBoxSiCochera.setDisable(true);
                textFieldCantidadCochera.setText(String.valueOf(cochera));
            } else {
                checkBoxNoCochera.setSelected(true);
                checkBoxSiCochera.setDisable(false);
                textFieldCantidadCochera.setDisable(true);
            }

            textFieldTamanioMetros.setText(String.valueOf(propiedad.getTamanio()));
            textAreaDescripcion.setText(propiedad.getDescripcion());
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
    public void editarPropiedad(ActionEvent event) {
        Propiedad propiedad = new Propiedad();
        String resumen = textFieldResumen.getText();
        String ciudad = textFieldCiudad.getText();
        String zona = comboBoxZona.getValue();
        String direccion = textFieldDireccion.getText();
        String descripcion = textAreaDescripcion.getText();
    
        if (!resumen.isBlank() && !ciudad.isBlank() && (zona != null && !zona.isBlank()) &&
            !direccion.isBlank() && (checkBoxDepartamento.isSelected() || checkBoxCuarto.isSelected() || checkBoxCasa.isSelected()) && 
            (checkBoxRenta.isSelected() || checkBoxVenta.isSelected())) {
            
            try {
                int precio = Integer.parseInt(textFieldPrecio.getText());
                int noHabitaciones = Integer.parseInt(textFieldNumeroHabitaciones.getText());
                int noEstancias = Integer.parseInt(textFieldNumeroEstancias.getText());
                int noBanios = Integer.parseInt(textFieldNumeroBanios.getText());
                int tamanio = Integer.parseInt(textFieldTamanioMetros.getText());
    
                propiedad.setIdPropiedad(idPropiedadUsada);
                propiedad.setResumen(resumen);
                propiedad.setCiudad(ciudad);
                propiedad.setZona(zona);
                propiedad.setDireccion(direccion);
                propiedad.setPrecio(precio);
                propiedad.setNoHabitaciones(noHabitaciones);
                propiedad.setNoEstancias(noEstancias);
                propiedad.setNoBanios(noBanios);
    
                if (checkBoxSiCochera.isSelected() && !textFieldCantidadCochera.getText().isBlank()) {
                    int noCocheras = Integer.parseInt(textFieldCantidadCochera.getText());
                    propiedad.setCochera(noCocheras);
                } else {
                    propiedad.setCochera(0);
                }
    
                propiedad.setTamanio(tamanio);
                propiedad.setDescripcion(descripcion);
                propiedad.setUsuarioAgente("Agente de Prueba");
    
                if (checkBoxDepartamento.isSelected()) {
                    propiedad.setTipoPropiedad("Departamento");
                } else if (checkBoxCuarto.isSelected()) {
                    propiedad.setTipoPropiedad("Cuarto");
                } else {
                    propiedad.setTipoPropiedad("Casa");
                }
    
                if (checkBoxRenta.isSelected()) {
                    propiedad.setPropiedadEn("Renta");
                } else if(checkBoxVenta.isSelected()){
                    propiedad.setPropiedadEn("Venta");
                }

                PropiedadDAO propiedadDAO = new PropiedadDAO();
                propiedadDAO.actualizarPropiedadPorIdPropiedad(propiedad);
    
                Alert agregoPropiedad = new Alert(AlertType.INFORMATION);
                agregoPropiedad.setTitle("Confirmación registro");
                agregoPropiedad.setHeaderText("Confirmación se agrego la propiedad");
                agregoPropiedad.setContentText("Se abrio de manera exitosa la propiedad");
                agregoPropiedad.show();
    
            } catch (NumberFormatException e) {
                Alert alertDatosInvalidos = new Alert(AlertType.ERROR);
                alertDatosInvalidos.setTitle("Datos inválidos");
                alertDatosInvalidos.setHeaderText("Error en el formato de los datos");
                alertDatosInvalidos.setContentText("Por favor, asegúrate de que todos los campos numéricos contienen valores válidos.");
                alertDatosInvalidos.show();
                e.printStackTrace();
            } catch (SQLException e) {
                Alert alertBaseDeDatos = new Alert(AlertType.ERROR);
                alertBaseDeDatos.setTitle("Ocurrió un error en la base de datos");
                alertBaseDeDatos.setHeaderText("Ocurrió un error al interactuar con la base de datos");
                alertBaseDeDatos.setContentText("Hubo un error al intentar agregar la propiedad. Por favor, intenta de nuevo.");
                alertBaseDeDatos.show();
                e.printStackTrace();
            }
        } else {
            Alert alertDatosVacios = new Alert(AlertType.ERROR);
            alertDatosVacios.setTitle("Datos incompletos");
            alertDatosVacios.setHeaderText("Faltan datos por completar");
            alertDatosVacios.setContentText("Por favor, asegúrate de que todos los campos obligatorios están llenos y contienen valores válidos.");
            alertDatosVacios.show();
        }
    }

    @FXML
    public void regresarInicio(ActionEvent event){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/fxml/consultarPropiedadesAdmin.fxml"));
        ChangeWindowManager.changeWindowTo(event, loader);
    }

    @FXML
    public void eliminarPropiedad(ActionEvent event){
        PropiedadDAO propiedadDAO = new PropiedadDAO();
        Propiedad propiedad = new Propiedad();
        propiedad.setIdPropiedad(idPropiedadUsada);
        try {
            propiedadDAO.eliminarPropiedadPorId(propiedad);
            Alert agregoPropiedad = new Alert(AlertType.INFORMATION);
            agregoPropiedad.setTitle("Confirmación eliminación");
            agregoPropiedad.setHeaderText("Se elimino correctamente");
            agregoPropiedad.setContentText("Se elimino de manera exitosa la propiedad");
            agregoPropiedad.show();
            regresarInicio(event);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
