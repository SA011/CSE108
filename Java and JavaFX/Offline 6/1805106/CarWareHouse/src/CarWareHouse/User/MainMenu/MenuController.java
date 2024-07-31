package CarWareHouse.User.MainMenu;

import CarWareHouse.User.SceneLoader;
import javafx.event.ActionEvent;

public class MenuController {
    private SceneLoader sceneLoader;
    public void setSceneLoader(SceneLoader sceneLoader) {
        this.sceneLoader = sceneLoader;
    }

    public void exitClicked(ActionEvent actionEvent) {
        System.exit(0);
    }

    public void loginClicked(ActionEvent actionEvent) {
        sceneLoader.loadLogin();
    }
}
