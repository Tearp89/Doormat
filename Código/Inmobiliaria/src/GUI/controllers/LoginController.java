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
        if(!(correo.isEmpty() && contraseña.isEmpty())){
            buttonIniciar.setDisable(false);
        }
        LoginDAO loginDAO = new LoginDAO();
        if(loginDAO.validarAgente(correo, contraseña)){
            Agente agenteData = new Agente();
            agenteData.setContrasenia(contraseña);
            agenteData.setUsuarioAgente(correo);
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
            clienteData.setContrasenia(contraseña);
            clienteData.setCorreo(correo);

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
            

        }else if (correo.isEmpty() || contraseña.isEmpty()){
            Alert emptyFieldsAlert = new Alert(AlertType.ERROR);
                emptyFieldsAlert.setTitle("Campos incorrectos o vacíos");
                emptyFieldsAlert.setHeaderText("Campos incorrectos o vacíos");
                emptyFieldsAlert.setContentText("Hay campos vacíos y/o incorrectos.");
                emptyFieldsAlert.showAndWait();
        
        }else {
            Alert userNotInDatabase = new Alert(AlertType.INFORMATION);
            userNotInDatabase.setHeaderText("No existe en el sistema");
            userNotInDatabase.setTitle("No hay existencia del usuario");
            userNotInDatabase.setContentText("No hay un registro de su cuenta en el sistema");
            userNotInDatabase.show();
        }
    }
    /*@FXML
    private TextField textFieldCorreoCrear;
    @FXML
    private TextField textFieldContraseñaCrear;
    @FXML
    private void crear(ActionEvent event){
        ClienteDAO clienteDao = new ClienteDAO();
        String contraseña = textFieldContraseñaCrear.getText();
        String correo = textFieldCorreoCrear.getText();
        if (esCorreoValido(correo)){
            try{
                Cliente cliente = new Cliente();
                cliente.setContrasenia(contraseña);
                cliente.setCorreo(correo);
                String[] parteCorreo = correo.split("@");
                String usuario = parteCorreo[0];
                cliente.setUsuarioCliente(usuario);
                cliente.setPreferencias("Ninguna");
                try{
                    clienteDao.agregarCliente(cliente);
                    UserSessionManager.getInstance().loginCliente(cliente);
                    FXMLLoader clienteInicioLoader = new FXMLLoader(getClass().getResource("/GUI/fxml/consultarPropiedadesCliente.fxml"));
                    Parent root = clienteInicioLoader.load();
                    Stage stage = new Stage();            
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (SQLException e){
                    Alert alertDatosVacios = new Alert(AlertType.ERROR);
                    alertDatosVacios.setTitle("Error ingresar");
                    alertDatosVacios.setHeaderText("Error ingresar");
                    alertDatosVacios.setContentText("Hubo un error al intentar agregar su usuario");
                    alertDatosVacios.show();
                    e.printStackTrace();
                }
            } catch (IOException e){
                e.printStackTrace();
            }
        } else {
            Alert alertDatosVacios = new Alert(AlertType.ERROR);
            alertDatosVacios.setTitle("Correo incorrecto");
            alertDatosVacios.setHeaderText("Correo incorrecto");
            alertDatosVacios.setContentText("Por favor, asegúrate de que haya ingresado un correo valido");
            alertDatosVacios.show();
        }
    }

    @FXML
    private Button buttonCrear;
    @FXML*/

    @FXML
    public void goToCrearCuenta(ActionEvent event){
        try{
        
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/fxml/SingUp.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
        } catch (IOException e){
                e.printStackTrace();
            }
        
    }

    private void initialize(){
        
        
    }

    public static boolean esCorreoValido(String correoElectronico) {
        String regex = "^[a-zA-Z0-9_+&-]+(?:\\.[a-zA-Z0-9_+&-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(correoElectronico);
        return matcher.matches();
    }
}
