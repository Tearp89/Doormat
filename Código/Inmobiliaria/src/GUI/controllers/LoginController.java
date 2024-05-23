package GUI.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {
    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }
    
    
    @FXML
    private TextField textFieldCorreoIniciar;
    @FXML
    private TextField textFieldContraseñaIniciar;
    @FXML
    private Button buttonIniciar;
    
    @FXML
    private void goToInicio(ActionEvent event){
        String correo = textFieldCorreoIniciar.getText();
        String contraseña = textFieldContraseñaIniciar.getText();
        if(!(correo.isEmpty() && contraseña.isEmpty())){
            buttonIniciar.setDisable(false);
        }
        LoginDAO loginDAO = new LoginDAO();
        if(loginDAO.validarAgente(correo, contraseña)){
            FXMLLoader agenteInicioLoader = new FXMLLoader(getClass().getResource("/GUI/fxml/consultarPropiedadesAdmin.fxml"));
            Parent root = agenteInicioLoader.load();
            ConsultarPropiedadesAgenteController controller = agenteInicioLoader.getController();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();


        } else if(loginDAO.validarCliente(correo, contraseña)) {
            FXMLLoader clienteInicioLoader = new FXMLLoader(getClass().getResource("/GUI/fxml/consultarPropiedadesCliente.fxml"));
            Parent root = clienteInicioLoader.load();
            ConsultarPropiedadesAgenteController controller = clienteInicioLoader.getController();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

        
        }
    }
    @FXML
    private TextField textFieldUsuarioCrear;
    @FXML
    private TextField textFieldContraseñaCrear;
    @FXML
    private void crear(ActionEvent event){
        ClienteDAO clienteDao = new ClienteDAO();
        String contraseña = textFieldContraseñaCrear.getText();
        String usuario = textFieldUsuarioCrear.getText();
        clienteDao.agregarCliente(usuario, contraseña);
        FXMLLoader clienteInicioLoader = new FXMLLoader(getClass().getResource("/GUI/fxml/consultarPropiedadesCliente.fxml"));
        Parent root = clienteInicioLoader.load();
        ConsultarPropiedadesAgenteController controller = clienteInicioLoader.getController();
        Stage stage = new Stage();            stage.setScene(new Scene(root));
        stage.show();


    }

    @FXML
    private Button buttonCrear;
    @FXML
    private void initialize(){
        buttonIniciar.setDisable(true);
        buttonCrear.setDisable(true);
    }

    
}
