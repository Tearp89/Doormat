package GUI.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import logic.DAOs.ClienteDAO;
import logic.classes.Cliente;

public class EditarPerfilController {

    @FXML
    private TextField textFieldNombre;

    @FXML
    private TextField textFieldContraseniaVieja;

    @FXML
    private TextField textFieldContraseniaNueva;

    @FXML
    private TextField textFieldCorreo;

    @FXML
    private Label labelUsuario;

    @FXML
    private Label labelNombreCompleto;

    @FXML
    private Button buttonEliminarPerfil;
    @FXML
    private void eliminarPerfil(ActionEvent event){
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmación de eliminación");
        alert.setHeaderText("¿Está seguro de que desea eliminar su perfil?");
        alert.setContentText("Esta acción no se puede deshacer.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Llamar a ClienteDAO para eliminar el perfil
            ClienteDAO clienteDAO = new ClienteDAO();
            try {
                clienteDAO.eliminarClientePorUsuario(UserSessionManager.getInstance().getClienteData());
                UserSessionManager.getInstance().logoutCliente();
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.close();
            } catch (SQLException e) {
                e.printStackTrace();
                Alert errorAlert = new Alert(AlertType.ERROR);
                errorAlert.setTitle("Error al eliminar perfil");
                errorAlert.setHeaderText("Ocurrió un error al intentar eliminar el perfil.");
                errorAlert.setContentText("Por favor, inténtelo de nuevo más tarde.");
                errorAlert.showAndWait();
            }
        } else {
            alert.close();
        }
    }

    @FXML

    private void editarPerfil(ActionEvent event) {
        String nombre = textFieldNombre.getText();
        String correo = textFieldCorreo.getText();
        String nuevaContraseña = textFieldContraseniaNueva.getText();
        String confirmarContraseña = textFieldContraseniaVieja.getText();
        String usuarioActual = labelUsuario.getText(); // Obtener el usuario actual
        String[] parteCorreo = correo.split("@");
        String usuario = parteCorreo[0];
        ClienteDAO clienteDAO = new ClienteDAO();
    
        try {
            if (!esCorreoValido(correo) || !esNombreValido(nombre)) {
                Alert camposInvalidos = new Alert(AlertType.ERROR);
                camposInvalidos.setTitle("Campos inválidos");
                camposInvalidos.setHeaderText(null);
                camposInvalidos.setContentText("No se puede actualizar el perfil, hay campos inválidos");
                camposInvalidos.showAndWait();
            } else if (clienteDAO.correoRepetido(correo, usuarioActual)) {
                Alert correoDuplicadoAlert = new Alert(AlertType.ERROR);
                correoDuplicadoAlert.setTitle("Correo duplicado");
                correoDuplicadoAlert.setContentText("No se puede actualizar el correo, está duplicado");
                correoDuplicadoAlert.setHeaderText(null);
                correoDuplicadoAlert.show();
            } else {
                Cliente cliente = new Cliente();
                cliente.setUsuarioCliente(usuario);
                cliente.setCorreo(correo);
                cliente.setNombre(nombre);
    
                if (nuevaContraseña.trim().isEmpty()) {
                   
                    int resultado = clienteDAO.actualizarClienteSinContrasenia(cliente, usuarioActual);
                    labelUsuario.setText(usuario);
                    mostrarResultadoActualizacion(resultado);
                } else {
                    if(confirmarContraseña.equals(nuevaContraseña) && validatePassword(nuevaContraseña)){
                        cliente.setContrasenia(nuevaContraseña);
                        int resultado = clienteDAO.actualizarClienteConContrasenia(cliente, confirmarContraseña, usuarioActual);
                        labelUsuario.setText(usuario);
                        mostrarResultadoActualizacion(resultado);
                    } else {
                        Alert contaseñaInvalidaAlert = new Alert(AlertType.ERROR);
                        contaseñaInvalidaAlert.setTitle("Contraseña inválida o equivocada");
                        contaseñaInvalidaAlert.setHeaderText(null);
                        contaseñaInvalidaAlert.setContentText("No se puede actualizar el perfil, verifique que la contraseña ingresada sea una contraseña valida, debe contener al menos una mayúscula, una minúscula, un caracter especial y una longitud miníma de 8 caracteres");
                        contaseñaInvalidaAlert.show();
                    }
                    
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alerta = new Alert(AlertType.ERROR);
            alerta.setTitle("Error de base de datos");
            alerta.setHeaderText(null);
            alerta.setContentText("Error al actualizar el perfil en la base de datos");
            alerta.showAndWait();
        }
    }
    
    private void mostrarResultadoActualizacion(int resultado) {
        Alert alerta;
        if (resultado > 0) {
            alerta = new Alert(AlertType.INFORMATION);
            alerta.setTitle("Edición de perfil");
            alerta.setHeaderText(null);
            alerta.setContentText("Perfil actualizado correctamente");
            
        } else {
            alerta = new Alert(AlertType.ERROR);
            alerta.setTitle("Edición de perfil");
            alerta.setHeaderText(null);
            alerta.setContentText("No se pudo actualizar el perfil, intente de nuevo más tarde");
        }
        alerta.showAndWait();
    }
    
    @FXML
    private Button logo;
    @FXML
    private void regresarInicio(ActionEvent event){
        try {


            FXMLLoader inicioLoader = new FXMLLoader(getClass().getResource("/GUI/fxml/consultarPropiedadesCliente.fxml"));
            Parent root = inicioLoader.load();
            Scene scene = new Scene(root);

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            Stage newStage = new Stage();
            newStage.setScene(scene);
            newStage.setTitle("Consultar Propiedades Clientes");
            newStage.show();

            currentStage.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    @FXML
    private Button buttonCancelar;
    @FXML
    private void cancelar(ActionEvent event){
        try {


            FXMLLoader inicioLoader = new FXMLLoader(getClass().getResource("/GUI/fxml/consultarPropiedadesCliente.fxml"));
            Parent root = inicioLoader.load();
            Scene scene = new Scene(root);

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            Stage newStage = new Stage();
            newStage.setScene(scene);
            newStage.setTitle("Consultar Propiedades Clientes");
            newStage.show();

            currentStage.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

    }

    public static boolean esCorreoValido(String correoElectronico) {
        String regex = "^[a-zA-Z0-9_+&-]+(?:\\.[a-zA-Z0-9_+&-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(correoElectronico);
        return matcher.matches();
    }

    public static boolean esNombreValido(String textFieldTrim) {
        textFieldTrim = textFieldTrim.trim(); 
        if (!textFieldTrim.isBlank()) {
            
            return textFieldTrim.matches("([A-ZÁÉÍÓÚÑ][a-záéíóúñ]*[\\s\\-']?)+");
        } else {
            return false;
        }
    }


        







    @FXML
    private void initialize(){
        Cliente clienteData = new Cliente();
        clienteData = UserSessionManager.getInstance().getClienteData();
        labelUsuario.setText(clienteData.getUsuarioCliente());
        ClienteDAO clienteDAO = new ClienteDAO();
        String usuario = labelUsuario.getText();
        try{
            textFieldNombre.setText(clienteDAO.obtenerNombreClientePorUsuario(usuario));
            textFieldCorreo.setText(clienteDAO.obtenerCorreoClientePorUsuario(usuario));
            labelNombreCompleto.setText(clienteDAO.obtenerNombreClientePorUsuario(usuario));
        } catch(SQLException e){{
            e.printStackTrace();
        }}
        
    }

    private boolean validatePassword(String password) {
        if (password.length() < 8) {
            return false;
        }
        Pattern upperCasePattern = Pattern.compile("[A-Z]");
        Pattern lowerCasePattern = Pattern.compile("[a-z]");
        Pattern digitPattern = Pattern.compile("[0-9]");
        Pattern specialCharacterPattern = Pattern.compile("[^a-zA-Z0-9]");
        Matcher upperCaseMatcher = upperCasePattern.matcher(password);
        Matcher lowerCaseMatcher = lowerCasePattern.matcher(password);
        Matcher digitMatcher = digitPattern.matcher(password);
        Matcher specialCharacterMatcher = specialCharacterPattern.matcher(password);

        return upperCaseMatcher.find() && lowerCaseMatcher.find() && digitMatcher.find() && specialCharacterMatcher.find();
    }

        
        
   

    



}
