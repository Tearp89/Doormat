package GUI.controllers;

import java.io.IOException;
import java.sql.SQLException;

import org.apache.log4j.jmx.Agent;

import GUI.windows.UserSessionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import logic.DAOs.AgenteDAO;
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
            AgenteDAO agenteDAO = new AgenteDAO();
            agenteData.setContrasenia(contraseña);
            agenteData.setUsuarioAgente(correo);
            UserSessionManager.getInstance().loginAgente(agenteData);
            Node source = (Node) event.getSource();
            stage = (Stage) source.getScene().getWindow();
            stage.close();


            try{
                FXMLLoader agenteInicioLoader = new FXMLLoader(getClass().getResource("/GUI/fxml/consultarPropiedadesAdmin.fxml"));
            Parent root = agenteInicioLoader.load();
            ConsultarPropiedadesAgenteController controller = agenteInicioLoader.getController();
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

            UserSessionManager.getInstance().loginCliente(clienteData);
            Node source = (Node) event.getSource();
            stage = (Stage) source.getScene().getWindow();
            stage.close();
            try{
                FXMLLoader clienteInicioLoader = new FXMLLoader(getClass().getResource("/GUI/fxml/consultarPropiedadesCliente.fxml"));
            Parent root = clienteInicioLoader.load();
            ConsultarPropiedadesAgenteController controller = clienteInicioLoader.getController();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            } catch (IOException e){
                e.printStackTrace();
            }
            

        
        }
    }
    @FXML
    private TextField textFieldCorreoCrear;
    @FXML
    private TextField textFieldContraseñaCrear;
    @FXML
    private void crear(ActionEvent event){
        ClienteDAO clienteDao = new ClienteDAO();
        String contraseña = textFieldContraseñaCrear.getText();
        String usuario = textFieldCorreoCrear.getText();
        try{
            Cliente cliente = new Cliente();
        cliente.setContrasenia(contraseña);
        cliente.setCorreo(usuario);
        try{
            clienteDao.agregarCliente(cliente);

        } catch (SQLException e){
            e.printStackTrace();
        }
        FXMLLoader clienteInicioLoader = new FXMLLoader(getClass().getResource("/GUI/fxml/consultarPropiedadesCliente.fxml"));
        Parent root = clienteInicioLoader.load();
        ConsultarPropiedadesAgenteController controller = clienteInicioLoader.getController();
        Stage stage = new Stage();            
        stage.setScene(new Scene(root));
        stage.show();

        } catch (IOException e){
            e.printStackTrace();
        }
        


    }

    @FXML
    private Button buttonCrear;
    @FXML
    private void initialize(){
        
        
    }

    
}
