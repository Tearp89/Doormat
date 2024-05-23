package GUI.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import GUI.windows.ChangeWindowManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import logic.DAOs.PropiedadDAO;
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
        int filas = 1;
        for (int i = 0; i < propiedades.size(); i++) {
            Propiedad propiedad = propiedades.get(i);
            FXMLLoader loader = new FXMLLoader();
            try {
                loader.setLocation(getClass().getResource("/GUI/fxml/propiedad.fxml"));
                Parent root = loader.load();
                PropiedadController controller = loader.getController();
                controller.ingresarInformacion(propiedad);
                if(columnas == 3){ 
                    columnas = 0;
                    ++filas;
                }

                gridPaneResultados.add(root, columnas, filas);
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
}
