package GUI.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import GUI.windows.ChangeWindowManager;
import GUI.windows.UserSessionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import logic.DAOs.ClienteDAO;
import logic.DAOs.LoginDAO;
import logic.classes.Agente;
import logic.classes.Cliente;

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
        String usuario = textFieldCorreoIniciar.getText();
        if(!(correo.trim().isEmpty() && contraseña.trim().isEmpty())){
            buttonIniciar.setDisable(false);
        }
        LoginDAO loginDAO = new LoginDAO();
        if(loginDAO.validarAgente(usuario, contraseña)){
            Agente agenteData = new Agente();
            agenteData.setContrasenia(contraseña);
            agenteData.setUsuarioAgente(usuario);
            UserSessionManager.getInstance().loginAgente(agenteData);
            Node source = (Node) event.getSource();
            stage = (Stage) source.getScene().getWindow();
            stage.close();


            try{
                FXMLLoader agenteInicioLoader = new FXMLLoader(getClass().getResource("/GUI/fxml/consultarPropiedadesAdmin.fxml"));
                Parent root = agenteInicioLoader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();

            } catch (IOException e){
                e.printStackTrace();
            }
            


        } else if(loginDAO.validarCliente(correo, contraseña)) {
            Cliente clienteData = new Cliente();
            ClienteDAO clienteDAO = new ClienteDAO();
            clienteData.setContrasenia(contraseña);
            clienteData.setCorreo(correo);
            try{
                clienteData.setUsuarioCliente(clienteDAO.obtenerUsuarioClientePorUsuario(correo));

            }catch (SQLException e){
                e.printStackTrace();
            }
            UserSessionManager.getInstance().loginCliente(clienteData);
            Node source = (Node) event.getSource();
            stage = (Stage) source.getScene().getWindow();
            stage.close();
            try{
                FXMLLoader clienteInicioLoader = new FXMLLoader(getClass().getResource("/GUI/fxml/consultarPropiedadesCliente.fxml"));
                Parent root = clienteInicioLoader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e){
                e.printStackTrace();
            }
        } else {
            Alert noExisteUsuario = new Alert(AlertType.ERROR);
            noExisteUsuario.setTitle("No existe en el sistema");
            noExisteUsuario.setHeaderText(null);
            noExisteUsuario.setContentText("No existe una cuenta con sus credenciales");
            noExisteUsuario.show();
        }
    }
    @FXML
    private Button buttonCrear;
    @FXML
    
    private void goToCrearCuenta(ActionEvent event){
        Node source = (Node) event.getSource();
        stage = (Stage) source.getScene().getWindow();
        stage.close();
        try{
            FXMLLoader clienteInicioLoader = new FXMLLoader(getClass().getResource("/GUI/fxml/SignUp.fxml"));
            Parent root = clienteInicioLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e){
            e.printStackTrace();
        }
        
    }

    

    public void initialize(){
        
        
    }

    public static boolean esCorreoValido(String correoElectronico) {
        String regex = "^[a-zA-Z0-9_+&-]+(?:\\.[a-zA-Z0-9_+&-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(correoElectronico);
        return matcher.matches();
    }
}
