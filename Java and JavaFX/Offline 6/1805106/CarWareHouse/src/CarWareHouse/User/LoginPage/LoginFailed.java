package CarWareHouse.User.LoginPage;

import javafx.animation.FadeTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class LoginFailed {
    private static final int width = 300,height = 160;
    public static void showloginFailed(AnchorPane root, long time, String message,int fontsize){
        StackPane newPane = new StackPane();
        Label failed = new Label(message);
        failed.setPrefWidth(width);
        failed.setPrefHeight(height);
        failed.setAlignment(Pos.CENTER);
        failed.setFont(Font.font("Impact",fontsize));
        failed.setStyle("-fx-background-color: #b42222; -fx-border-color: Black; -fx-border-width: 2");
        failed.setLayoutX((root.getWidth()-width)/2);
        failed.setLayoutY((root.getHeight()-height)/2);
        failed.setOpacity(0.75);
        root.getChildren().add(failed);

        FadeTransition fadIn = new FadeTransition(Duration.seconds(time/2),failed);
        fadIn.setFromValue(0);
        fadIn.setToValue(1);
        fadIn.setCycleCount(1);
        FadeTransition fadOut = new FadeTransition(Duration.seconds(time/2),failed);
        fadOut.setFromValue(1);
        fadOut.setToValue(0);
        fadOut.setCycleCount(1);

        fadIn.play();

        fadIn.setOnFinished(actionEvent -> {
            fadOut.play();
        });

        fadOut.setOnFinished(actionEvent -> {
            root.getChildren().remove(failed);
        });

    }
}
