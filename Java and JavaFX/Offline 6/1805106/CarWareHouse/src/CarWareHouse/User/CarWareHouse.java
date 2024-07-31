package CarWareHouse.User;

import javafx.application.Application;
import javafx.stage.Stage;
import CarWareHouse.NetworkUtil;

public class CarWareHouse extends Application {
    private static final int portNumber = 10278;
    @Override
    public void start(Stage stage) throws Exception {
        updateHandler uHandler = new updateHandler();
        SceneLoader multiScene = new SceneLoader(stage , new NetworkUtil("localhost",portNumber),uHandler);

        multiScene.loadMenu();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
