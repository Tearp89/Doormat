package GUI.windows;


import GUI.controllers.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainLogin extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/fxml/login.fxml"));
        Parent root = loader.load();

        LoginController loginController = loader.getController();

        loginController.setStage(primaryStage);
        

        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
