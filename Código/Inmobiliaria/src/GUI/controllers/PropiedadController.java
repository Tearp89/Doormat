package GUI.controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import logic.classes.Propiedad;

public class PropiedadController {
    @FXML
    private Label labelPrecio;

    @FXML
    private Label labelPropiedadEn;

    @FXML
    private Label labelEstancias;

    @FXML
    private Label labelBanios;

    @FXML
    private Label labelHabitaciones;

    @FXML
    private Label labelTamanio;

    @FXML Label labelResumen;

    @FXML
    private Button buttonConsultar;

    private Propiedad propiedadUso;

    public void ingresarInformacion(Propiedad propiedad){
        this.propiedadUso = propiedad;
        labelPrecio.setText(String.valueOf(propiedad.getPrecio()));
        labelPropiedadEn.setText(propiedad.getPropiedadEn());
        labelBanios.setText(String.valueOf(propiedad.getNoBanios()));
        labelEstancias.setText(String.valueOf(propiedad.getNoEstancias()));
        labelHabitaciones.setText(String.valueOf(propiedad.getNoHabitaciones()));
        labelTamanio.setText(String.valueOf(propiedad.getTamanio()));
        labelResumen.setText(propiedad.getResumen());
    }

    @FXML
    public void consultarPropiedad(ActionEvent event){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("null"));
        try {
            Parent root = loader.load();
            EditarPropiedadController controller = loader.getController();
            controller.initialize(propiedadUso.getIdPropiedad());
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            Node source = (Node) event.getSource();
            Stage actual = (Stage) source.getScene().getWindow();
            actual.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
