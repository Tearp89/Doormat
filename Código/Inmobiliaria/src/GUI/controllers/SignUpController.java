package GUI.controllers;

import java.io.IOError;
import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.apache.poi.sl.usermodel.TextParagraph.TextAlign;

import GUI.windows.UserSessionManager;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;
import logic.DAOs.ClienteDAO;
import logic.classes.Cliente;

public class SignUpController {

    @FXML
    private StackPane stackPane;

    @FXML
    private Pane pane1;

    @FXML
    private Pane pane2;

    @FXML
    private Pane pane3;

    @FXML
    private Pane pane4;

    @FXML
    private Pane pane5;

    @FXML
    private TextField textFieldNombre;

    @FXML
    private TextField textFieldCorreo;

    @FXML
    private TextField textFieldContrasenia;

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
    private Button buttonCrear;
    @FXML
    private void goToCrearCuenta(ActionEvent event){

        String preferencias = getSelectedCheckBoxes();
        String nombre = textFieldNombre.getText();
        String correo = textFieldCorreo.getText();
        String contraseña = textFieldContrasenia.getText();
        ClienteDAO clienteDAO = new ClienteDAO();
        try{
            if(!esCorreoValido(correo)){
                Alert correoInvalidoAlert = new Alert(AlertType.ERROR);
                correoInvalidoAlert.setTitle("Correo inválido");
                correoInvalidoAlert.setHeaderText("Correo inválido");
                correoInvalidoAlert.setContentText("Por favor ingrese un correo válido");
                correoInvalidoAlert.show();
            }else if (!esNombreValido(nombre)){
                Alert nombreInvalidoAlert = new Alert(AlertType.ERROR);
                nombreInvalidoAlert.setTitle("Nombre inválido");
                nombreInvalidoAlert.setHeaderText("Nombre inválido");
                nombreInvalidoAlert.setContentText("Por favor ingrese un nombre válido, iniciando por mayúscula");
                nombreInvalidoAlert.show();
            } else if (!validatePassword(contraseña)){
                Alert contraseñaInvalidaAlert = new Alert(AlertType.ERROR);
                contraseñaInvalidaAlert.setTitle("Contraseña inválida");
                contraseñaInvalidaAlert.setHeaderText("Contraseña inválida");
                contraseñaInvalidaAlert.setContentText("Por favor ingrese una contraseña valida");
                contraseñaInvalidaAlert.show();
            }
            else if(!clienteDAO.correoDisponible(correo)){
                Alert correoRepetidoAlert = new Alert(AlertType.ERROR);
                correoRepetidoAlert.setTitle("Correo ya registrado");
                correoRepetidoAlert.setHeaderText("Correo ya registrado");
                correoRepetidoAlert.setContentText("No se puede crear la cuenta, el correo electrónico ya se encuentra registrado");
                correoRepetidoAlert.show();
                
            }else {
                Cliente cliente = new Cliente();
                String[] parteCorreo = correo.split("@");
                String usuario = parteCorreo[0];
                cliente.setUsuarioCliente(usuario);
                cliente.setCorreo(correo);
                cliente.setnombre(nombre);
                cliente.setContrasenia(contraseña);
                cliente.setPreferencias(preferencias);
                int resultado = clienteDAO.agregarCliente(cliente);
                if(resultado > 0 ){
                   /* Cliente clienteData = new Cliente();
                    clienteData.setContrasenia(contraseña);
                    clienteData.setCorreo(correo);*/

                    UserSessionManager.getInstance().loginCliente(cliente);
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
                    
                } else{
                    Alert errorRegistroAlert = new Alert(AlertType.ERROR);
                    errorRegistroAlert.setTitle("Error de registro");
                    errorRegistroAlert.setHeaderText("Error de registro");
                    errorRegistroAlert.setContentText("No se pudo crear la cuenta, intente de nuevo más tarde");
                    errorRegistroAlert.show();
                }
                
            } 
        } catch(SQLException e){
            e.printStackTrace();
        }
       


        
    }

    @FXML
    private Button buttonRegresar;
    @FXML
    private void goToLogin(ActionEvent event){
        try {
            FXMLLoader inicioLoader = new FXMLLoader(getClass().getResource("/GUI/fxml/login.fxml"));
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
    

    private int currentPaneIndex = 0;
    private Pane[] panes;

    public void initialize() {
        panes = new Pane[]{pane1, pane2, pane3, pane4, pane5};

        // Inicializar las transiciones
        for (int i = 0; i < panes.length; i++) {
            Pane pane = panes[i];
            if (i != 0) {
                pane.setVisible(false);
            }
        }

        // Crear y configurar la animación
        SequentialTransition mainTransition = new SequentialTransition();
        for (Pane pane : panes) {
            SequentialTransition transition = createTransition(pane);
            mainTransition.getChildren().add(transition);
        }
        mainTransition.setCycleCount(SequentialTransition.INDEFINITE);
        mainTransition.play();
    }

    private SequentialTransition createTransition(Pane pane) {
        // Aparecer con deslizamiento y desvanecimiento
        TranslateTransition translateIn = new TranslateTransition(Duration.seconds(1), pane);
        translateIn.setFromY(stackPane.getHeight());
        translateIn.setToY(0);

        FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), pane);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);

        PauseTransition pauseTransitionIn = new PauseTransition(Duration.seconds(2.5));

        // Desvanecerse y deslizarse hacia arriba
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), pane);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setOnFinished(e -> pane.setVisible(false));

        TranslateTransition translateOut = new TranslateTransition(Duration.seconds(1), pane);
        translateOut.setByY(-stackPane.getHeight());

        PauseTransition pauseTransitionOut = new PauseTransition(Duration.seconds(1));

        SequentialTransition sequentialTransition = new SequentialTransition(
            new SequentialTransition(translateIn, fadeIn),
            pauseTransitionIn,
            fadeOut,
            translateOut,
            pauseTransitionOut
        );

        sequentialTransition.setOnFinished(e -> {
            pane.setVisible(false);
            currentPaneIndex = (currentPaneIndex + 1) % panes.length;
            Pane nextPane = panes[currentPaneIndex];
            nextPane.setVisible(true);
        });

        return sequentialTransition;
    }

    private String getSelectedCheckBoxes() {
        StringBuilder selectedCheckBoxes = new StringBuilder();

        if (checkBoxDepartamento.isSelected()) {
            selectedCheckBoxes.append(checkBoxDepartamento.getText()).append(", ");
        }
        if (checkBoxCuarto.isSelected()) {
            selectedCheckBoxes.append(checkBoxCuarto.getText()).append(", ");
        }
        if (checkBoxCasa.isSelected()) {
            selectedCheckBoxes.append(checkBoxCasa.getText()).append(", ");
        }
        if (checkBoxRenta.isSelected()) {
            selectedCheckBoxes.append(checkBoxRenta.getText()).append(", ");
        }
        if (checkBoxCompra.isSelected()) {
            selectedCheckBoxes.append(checkBoxCompra.getText()).append(", ");
        }

        // Remover la última coma y espacio si hay elementos seleccionados
        if (selectedCheckBoxes.length() > 0) {
            selectedCheckBoxes.setLength(selectedCheckBoxes.length() - 2);
        }

        return selectedCheckBoxes.toString();
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

     public static boolean esCorreoValido(String correoElectronico) {
        String regex = "^[a-zA-Z0-9_+&-]+(?:\\.[a-zA-Z0-9_+&-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(correoElectronico);
        return matcher.matches();
    }

    public static boolean onlyText(String textFieldTrim){
        textFieldTrim = textFieldTrim.trim();
        if(!textFieldTrim.isBlank()){
            return textFieldTrim.replaceAll("[.,-]", "") 
                               .matches("[a-zA-ZñÑáéíóúÁÉÍÓÚ\\s]+");
        } else {            
            return false;
        }
    }
    
    
    public static boolean onlyNumber(String textFieldTrim){
        textFieldTrim = textFieldTrim.trim();
        if(!textFieldTrim.isBlank()){
            return textFieldTrim.matches("\\d+");
        } else {
            return false;
        }
    }
    
    public static boolean isEmail(String email){
        email = email.trim();
        try {
            InternetAddress internetAddress = new InternetAddress(email);
            internetAddress.validate();
            return true;
        } catch  (AddressException ex) {
            return false;
        }
    }

    public static boolean onlyTextAndNumbers(String textFieldTrim) {
        textFieldTrim = textFieldTrim.trim();
        if (!textFieldTrim.isBlank()) {
            return textFieldTrim.matches("[a-zA-ZñÑáéíóúÁÉÍÓÚ\\d\\s]+");
        } else {
            return false;
        }
    }

    public static boolean esNombreValido(String textFieldTrim) {
        textFieldTrim = textFieldTrim.trim(); 
        if (!textFieldTrim.isBlank()) {
            
            return textFieldTrim.matches("([A-ZÁÉÍÓÚÑ][a-záéíóúñ]*[\\s\\-']?)+");
        } else {
            return false;
        }
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
