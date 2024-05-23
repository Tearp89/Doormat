package GUI.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import logic.DAOs.PropiedadDAO;
import GUI.windows.ChangeWindowManager;
import GUI.windows.UserSessionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import logic.classes.Agente;
import logic.classes.Propiedad;

public class ConsultarPropiedadesAgenteController {
    @FXML
    private CheckBox checkBoxDepartamento;

    @FXML
    private CheckBox checkBoxCuarto;

    @FXML
    private CheckBox checkBoxCasa;

    @FXML
    private CheckBox checkBoxRenta;

    @FXML
    private CheckBox checkBoxCompra;

    @FXML
    private TextField textFieldPrecioMin;

    @FXML
    private TextField textFieldPrecioMax;

    @FXML
    private TextField textFieldHabitaciones;

    @FXML
    private TextField textFieldEstancias;

    @FXML
    private TextField textFieldBanios;

    @FXML
    private TextField textFieldCochera;

    @FXML
    private CheckBox checkBoxXalapa, checkBoxOrizaba, checkBoxVeracruz;

    @FXML
    private CheckBox checkBoxCentro, checkBoxOrillas, checkBoxResidencial;

    @FXML
    private GridPane gridPaneResultados;

    @FXML
    private Button buttonAplicarFiltros;

    @FXML
    private void initialize(){
        aplicarFiltros(null);
    }

    @FXML
    private void aplicarFiltros(ActionEvent event) {
        PropiedadDAO propiedadDAO = new PropiedadDAO();
        try {
            ArrayList<Propiedad> propiedades = propiedadDAO.buscarPropiedadesFiltradas(
                    checkBoxRenta.isSelected(),
                    checkBoxCompra.isSelected(),
                    checkBoxDepartamento.isSelected(),
                    checkBoxCuarto.isSelected(),
                    checkBoxCasa.isSelected(),
                    checkBoxXalapa.isSelected(),
                    checkBoxOrizaba.isSelected(),
                    checkBoxVeracruz.isSelected(),
                    checkBoxCentro.isSelected(),
                    checkBoxOrillas.isSelected(),
                    checkBoxResidencial.isSelected(),
                    parseIntOrDefault(textFieldPrecioMin.getText(), 0),
                    parseIntOrDefault(textFieldPrecioMax.getText(), Integer.MAX_VALUE),
                    parseIntOrDefault(textFieldHabitaciones.getText(), 0),
                    parseIntOrDefault(textFieldEstancias.getText(), 0),
                    parseIntOrDefault(textFieldBanios.getText(), 0),
                    parseIntOrDefault(textFieldCochera.getText(), 0)
            );
            populateGridPane(propiedades);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private int parseIntOrDefault(String value, int defaultValue) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    private void populateGridPane(ArrayList<Propiedad> propiedades) {
        gridPaneResultados.getChildren().clear();
        
        int columnas = 0;
        int filas = 0;
        for (int i = 0; i < propiedades.size(); i++) {
            Propiedad propiedad = propiedades.get(i);
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/GUI/fxml/propiedad.fxml"));
            try {
                Parent root = loader.load();
                PropiedadController controller = loader.getController();
                controller.ingresarInformacion(propiedad);

                gridPaneResultados.add(root, columnas, filas);
                GridPane.setMargin(root, new Insets(10));

                columnas++;
                if(columnas == 3){ 
                    columnas = 0;
                    ++filas;
                }

                
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    @FXML
    public void goToAgregarPropiedad(ActionEvent event){
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/fxml/RegistrarNuevaPropiedad.fxml"));
        ChangeWindowManager.changeWindowTo(event, loader);
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
            checkBoxCompra.setSelected(false);
        }
    }

    @FXML
    public void seleccionarCompra(ActionEvent event){
        if(checkBoxCompra.isSelected()){
            checkBoxRenta.setSelected(false);
        }
    }

    @FXML
    public void seleccionarZonaCentro(ActionEvent event){
        if(checkBoxCentro.isSelected()){
            checkBoxOrillas.setSelected(false);
            checkBoxResidencial.setSelected(false);
        }
    }

    @FXML
    public void seleccionarZonaOrillas(ActionEvent event){
        if(checkBoxOrillas.isSelected()){
            checkBoxCentro.setSelected(false);
            checkBoxResidencial.setSelected(false);
        }
    }

    @FXML
    public void seleccionarZonaResidencial(ActionEvent event){
        if(checkBoxResidencial.isSelected()){
            checkBoxCentro.setSelected(false);
            checkBoxOrillas.setSelected(false);
        }
    }

    @FXML
    public void seleccionarCiudadOrizaba(ActionEvent event){
        if(checkBoxOrizaba.isSelected()){
            checkBoxXalapa.setSelected(false);
            checkBoxVeracruz.setSelected(false);
        }
    }

    @FXML
    public void seleccionarCiudadXalapa(ActionEvent event){
        if(checkBoxXalapa.isSelected()){
            checkBoxOrizaba.setSelected(false);
            checkBoxVeracruz.setSelected(false);
        }
    }

    @FXML
    public void seleccionarCiudadVeracruz(ActionEvent event){
        if(checkBoxVeracruz.isSelected()){
            checkBoxOrizaba.setSelected(false);
            checkBoxXalapa.setSelected(false);
        }
    }
}
