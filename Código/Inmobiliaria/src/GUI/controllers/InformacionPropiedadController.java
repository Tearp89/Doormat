package GUI.controllers;

import java.sql.SQLException;

import GUI.windows.ChangeWindowManager;
import GUI.windows.UserSessionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import logic.DAOs.AgenteDAO;
import logic.DAOs.PropiedadDAO;
import logic.DAOs.VisitaDAO;
import logic.classes.Cliente;
import logic.classes.Propiedad;
import logic.classes.Visita;

public class InformacionPropiedadController {
    @FXML
    private Label labelResumen, labelCiudad, labelZona, labelDireccion;

    @FXML
    private Label labelTipo, labelPropiedadEn, labelPrecio, labelCochera, labelTamanio;

    @FXML
    private Label labelHabitaciones, labelEstancias, labelBanios;
    
    @FXML
    private TextArea textAreaDescripcion;

    @FXML
    private Button buttonVisitar, buttonCalificar, buttonAgendar;

    @FXML
    private Button buttonCal1Propiedad, buttonCal2Propiedad, buttonCal3Propiedad, buttonCal4Propiedad, buttonCal5Propiedad; 

    @FXML
    private Button buttonCal1Asesor, buttonCal2Asesor, buttonCal3Asesor, buttonCal4Asesor, buttonCal5Asesor;

    @FXML
    private AnchorPane anchorPaneCalificar, anchorPaneVisitar;

    @FXML
    private DatePicker datePickerVisita;

    @FXML
    private Button buttonCerrarCalificar, buttonCerrarVisita;

    @FXML
    private Label labelUser;

    private int idPropiedadUso;
    private Propiedad propiedad;

    @FXML
    public void abrirVisita(ActionEvent event){
        anchorPaneVisitar.setVisible(true);
    }

    @FXML
    public void cerrarVisita(ActionEvent event){
        anchorPaneVisitar.setVisible(false);
    }

    @FXML
    public void abrirCalificar(ActionEvent event){
        anchorPaneCalificar.setVisible(true);
    }

    @FXML
    public void cerrarCalificar(ActionEvent event){
        anchorPaneCalificar.setVisible(false);
    }

    @FXML
    public void initialize(int idPropiedad){
        idPropiedadUso = idPropiedad;
        Cliente cliente = UserSessionManager.getInstance().getClienteData();
        labelUser.setText(cliente.getUsuarioCliente());
        PropiedadDAO propiedadDAO = new PropiedadDAO();
        try {
            propiedad = propiedadDAO.obtenerPropiedadPorId(idPropiedad);
            labelResumen.setText(propiedad.getResumen());
            labelCiudad.setText(propiedad.getCiudad());
            labelZona.setText(propiedad.getZona());
            labelDireccion.setText(propiedad.getDireccion());
            labelTipo.setText(propiedad.getTipoPropiedad());
            labelPropiedadEn.setText(propiedad.getPropiedadEn());
            labelPrecio.setText(String.valueOf(propiedad.getPrecio()));
            labelHabitaciones.setText(String.valueOf(propiedad.getNoHabitaciones()));
            labelEstancias.setText(String.valueOf(propiedad.getNoEstancias()));
            labelBanios.setText(String.valueOf(propiedad.getNoBanios()));
            labelCochera.setText(String.valueOf(propiedad.getCochera()));
            labelTamanio.setText(String.valueOf(propiedad.getTamanio()));
            textAreaDescripcion.setText(propiedad.getDescripcion());
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @FXML
    public void agendarVisita(ActionEvent event){
        PropiedadDAO propiedadDAO = new PropiedadDAO();
        VisitaDAO visitaDAO = new VisitaDAO();
        Visita visita = new Visita();
        try {
            propiedad = propiedadDAO.obtenerPropiedadPorId(idPropiedadUso);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        visita.setIdPropiedad(idPropiedadUso);
        visita.setUsuarioAgente(propiedad.getUsuarioAgente());
        visita.setUsuarioCliente(UserSessionManager.getInstance().getClienteData().getUsuarioCliente());
        visita.setFecha(datePickerVisita.getValue());
        try {
            visitaDAO.agregarVisita(visita);
            Alert agendoVisita = new Alert(AlertType.INFORMATION);
            agendoVisita.setTitle("Confirmación visita");
            agendoVisita.setHeaderText("Se agendo correctamente");
            agendoVisita.setContentText("Se agendo de manera exitosa la visita");
            agendoVisita.show();
        } catch (SQLException e) {
            Alert alertDatosVacios = new Alert(AlertType.ERROR);
            alertDatosVacios.setTitle("Error al agendar");
            alertDatosVacios.setHeaderText("Error al agendar");
            alertDatosVacios.setContentText("Ocurrio un error al momento de agendar su visita");
            alertDatosVacios.show();
            e.printStackTrace();
        }
        anchorPaneVisitar.setVisible(false);
        buttonVisitar.setDisable(true);
    }

    @FXML
    public void regresarInicio(ActionEvent event){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/fxml/consultarPropiedadesCliente.fxml"));
        ChangeWindowManager.changeWindowTo(event, loader);
    }

    @FXML
    public void califcarPropiedad1(ActionEvent event){
        PropiedadDAO propiedadDAO = new PropiedadDAO();
        try {
            propiedadDAO.calificarPropiedad(1, idPropiedadUso);
            Alert califica = new Alert(AlertType.INFORMATION);
            califica.setTitle("Propiedad calificada");
            califica.setHeaderText("Se califico correctamente");
            califica.setContentText("Se dio una calificacion de 1 estrella");
            califica.show();
            buttonCal1Propiedad.setDisable(true);
            buttonCal2Propiedad.setDisable(true);
            buttonCal3Propiedad.setDisable(true);
            buttonCal4Propiedad.setDisable(true);
            buttonCal5Propiedad.setDisable(true);
            buttonCalificar.setDisable(true);
        } catch (SQLException e) {
            
            e.printStackTrace();
        }
    }

    @FXML
    public void califcarPropiedad2(ActionEvent event){
        PropiedadDAO propiedadDAO = new PropiedadDAO();
        try {
            propiedadDAO.calificarPropiedad(2, idPropiedadUso);
            Alert califica = new Alert(AlertType.INFORMATION);
            califica.setTitle("Propiedad calificada");
            califica.setHeaderText("Se califico correctamente");
            califica.setContentText("Se dio una calificacion de 2 estrella");
            califica.show();
            buttonCal1Propiedad.setDisable(true);
            buttonCal2Propiedad.setDisable(true);
            buttonCal3Propiedad.setDisable(true);
            buttonCal4Propiedad.setDisable(true);
            buttonCal5Propiedad.setDisable(true);
            buttonCalificar.setDisable(true);
        } catch (SQLException e) {
            
            e.printStackTrace();
        }
    }

    @FXML
    public void califcarPropiedad3(ActionEvent event){
        PropiedadDAO propiedadDAO = new PropiedadDAO();
        try {
            propiedadDAO.calificarPropiedad(3, idPropiedadUso);
            Alert califica = new Alert(AlertType.INFORMATION);
            califica.setTitle("Propiedad calificada");
            califica.setHeaderText("Se califico correctamente");
            califica.setContentText("Se dio una calificacion de 3 estrella");
            califica.show();
            buttonCal1Propiedad.setDisable(true);
            buttonCal2Propiedad.setDisable(true);
            buttonCal3Propiedad.setDisable(true);
            buttonCal4Propiedad.setDisable(true);
            buttonCal5Propiedad.setDisable(true);
            buttonCalificar.setDisable(true);
        } catch (SQLException e) {
            
            e.printStackTrace();
        }
    }

    @FXML
    public void califcarPropiedad4(ActionEvent event){
        PropiedadDAO propiedadDAO = new PropiedadDAO();
        try {
            propiedadDAO.calificarPropiedad(4, idPropiedadUso);
            Alert califica = new Alert(AlertType.INFORMATION);
            califica.setTitle("Propiedad calificada");
            califica.setHeaderText("Se califico correctamente");
            califica.setContentText("Se dio una calificacion de 4 estrella");
            califica.show();
            buttonCal1Propiedad.setDisable(true);
            buttonCal2Propiedad.setDisable(true);
            buttonCal3Propiedad.setDisable(true);
            buttonCal4Propiedad.setDisable(true);
            buttonCal5Propiedad.setDisable(true);
            buttonCalificar.setDisable(true);
        } catch (SQLException e) {
            
            e.printStackTrace();
        }
    }

    @FXML
    public void califcarPropiedad5(ActionEvent event){
        PropiedadDAO propiedadDAO = new PropiedadDAO();
        try {
            propiedadDAO.calificarPropiedad(5, idPropiedadUso);
            Alert califica = new Alert(AlertType.INFORMATION);
            califica.setTitle("Propiedad calificada");
            califica.setHeaderText("Se califico correctamente");
            califica.setContentText("Se dio una calificacion de 5 estrella");
            califica.show();
            buttonCal1Propiedad.setDisable(true);
            buttonCal2Propiedad.setDisable(true);
            buttonCal3Propiedad.setDisable(true);
            buttonCal4Propiedad.setDisable(true);
            buttonCal5Propiedad.setDisable(true);
            buttonCalificar.setDisable(true);
        } catch (SQLException e) {
            
            e.printStackTrace();
        }
    }

    @FXML
    public void califcarAgente1(ActionEvent event){
        AgenteDAO agenteDAO = new AgenteDAO();
        try {
            agenteDAO.calificarAgente(1, propiedad.getUsuarioAgente());
            Alert califica = new Alert(AlertType.INFORMATION);
            califica.setTitle("Agente calificada");
            califica.setHeaderText("Se califico correctamente");
            califica.setContentText("Se dio una calificacion de 1 estrella");
            califica.show();
            buttonCal1Asesor.setDisable(true);
            buttonCal2Asesor.setDisable(true);
            buttonCal3Asesor.setDisable(true);
            buttonCal4Asesor.setDisable(true);
            buttonCal5Asesor.setDisable(true);
            buttonCalificar.setDisable(true);
        } catch (SQLException e) {
            
            e.printStackTrace();
        }
    }
    
    @FXML
    public void califcarAgente2(ActionEvent event){
        AgenteDAO agenteDAO = new AgenteDAO();
        try {
            agenteDAO.calificarAgente(2, propiedad.getUsuarioAgente());
            Alert califica = new Alert(AlertType.INFORMATION);
            califica.setTitle("Agente calificada");
            califica.setHeaderText("Se califico correctamente");
            califica.setContentText("Se dio una calificacion de 2 estrella");
            califica.show();
            buttonCal1Asesor.setDisable(true);
            buttonCal2Asesor.setDisable(true);
            buttonCal3Asesor.setDisable(true);
            buttonCal4Asesor.setDisable(true);
            buttonCal5Asesor.setDisable(true);
            buttonCalificar.setDisable(true);
        } catch (SQLException e) {
            
            e.printStackTrace();
        }
    }

    @FXML
    public void califcarAgente3(ActionEvent event){
        AgenteDAO agenteDAO = new AgenteDAO();
        try {
            agenteDAO.calificarAgente(3, propiedad.getUsuarioAgente());
            Alert califica = new Alert(AlertType.INFORMATION);
            califica.setTitle("Agente calificada");
            califica.setHeaderText("Se califico correctamente");
            califica.setContentText("Se dio una calificacion de 3 estrella");
            califica.show();
            buttonCal1Asesor.setDisable(true);
            buttonCal2Asesor.setDisable(true);
            buttonCal3Asesor.setDisable(true);
            buttonCal4Asesor.setDisable(true);
            buttonCal5Asesor.setDisable(true);
            buttonCalificar.setDisable(true);
        } catch (SQLException e) {
            
            e.printStackTrace();
        }
    }

    @FXML
    public void califcarAgente4(ActionEvent event){
        AgenteDAO agenteDAO = new AgenteDAO();
        try {
            agenteDAO.calificarAgente(4, propiedad.getUsuarioAgente());
            Alert califica = new Alert(AlertType.INFORMATION);
            califica.setTitle("Agente calificada");
            califica.setHeaderText("Se califico correctamente");
            califica.setContentText("Se dio una calificacion de 4 estrella");
            califica.show();
            buttonCal1Asesor.setDisable(true);
            buttonCal2Asesor.setDisable(true);
            buttonCal3Asesor.setDisable(true);
            buttonCal4Asesor.setDisable(true);
            buttonCal5Asesor.setDisable(true);
            buttonCalificar.setDisable(true);
        } catch (SQLException e) {
            
            e.printStackTrace();
        }
    }

    @FXML
    public void califcarAgente5(ActionEvent event){
        AgenteDAO agenteDAO = new AgenteDAO();
        try {
            agenteDAO.calificarAgente(5, propiedad.getUsuarioAgente());
            Alert califica = new Alert(AlertType.INFORMATION);
            califica.setTitle("Agente calificada");
            califica.setHeaderText("Se califico correctamente");
            califica.setContentText("Se dio una calificacion de 5 estrella");
            califica.show();
            buttonCal1Asesor.setDisable(true);
            buttonCal2Asesor.setDisable(true);
            buttonCal3Asesor.setDisable(true);
            buttonCal4Asesor.setDisable(true);
            buttonCal5Asesor.setDisable(true);
            buttonCalificar.setDisable(true);
        } catch (SQLException e) {
            
            e.printStackTrace();
        }
    }
}
