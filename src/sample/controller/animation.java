package sample.controller;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;
import javafx.util.Duration;

public class animation {
    public static void startingAnimation(ViewPage controller){
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000), controller.getRootNode());
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.play();
    }

    public static void closingAnimation(Controller controller){
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000), controller.getRootNode());
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
        fadeTransition.play();
    }
    public static void scaleTransitioningStarting(Node node){
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(800)) ;
        scaleTransition.setFromX(0);
        scaleTransition.setToX(1);
        scaleTransition.setFromY(0);
        scaleTransition.setByY(1);
        scaleTransition.setNode(node);
        scaleTransition.play();
    }

    public static void startingAnimationController(Controller controller) {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000), controller.getRootNode());
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.play();
    }

    public static void startingAnimationClubPage(ClubPage clubPage) {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000), clubPage.getRootNode());
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.play();
    }

    public static void startingAnimationPlayer_Details(player_details player_details) {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000), player_details.getRootNode());
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.play();
    }

    public static void startingAnimationConfirmation(Confirmation confirmation) {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000), confirmation.getRootNode());
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.play();
    }

    public static void startingAnimationControllerAddPLayer(AddPlayer addPlayer) {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000), addPlayer.getRootNode());
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.play();
    }

    public static void startingAnimationControllerEditPlayer(EditPage editPage) {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000), editPage.getRootNode());
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.play();
    }
}
