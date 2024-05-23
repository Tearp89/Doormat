package GUI.controllers;

import java.sql.SQLException;
import java.util.ArrayList;

import PropiedadDAO;
import GUI.windows.ChangeWindowManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
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
        for (int i = 0; i < propiedades.size(); i++) {
            Propiedad propiedad = propiedades.get(i);
            TextField direccion = new TextField(propiedad.getDireccion());
            TextField precio = new TextField(String.valueOf(propiedad.getPrecio()));
            TextField resumen = new TextField(propiedad.getResumen());
            gridPaneResultados.add(direccion, 0, i);
            gridPaneResultados.add(precio, 1, i);
            gridPaneResultados.add(resumen, 2, i);
        }
    }

    @FXML
    public void goToAgregarPropiedad(ActionEvent event){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/fxml/RegistrarNuevaPropiedad.fxml"));
        ChangeWindowManager.changeWindowTo(event, loader);
    }
}
