package GUI.controllers;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class SingUpController {

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
}
