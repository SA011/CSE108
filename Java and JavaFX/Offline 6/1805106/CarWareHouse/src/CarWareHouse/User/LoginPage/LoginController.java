package CarWareHouse.User.LoginPage;

import CarWareHouse.NetworkUtil;
import CarWareHouse.User.SceneLoader;
import CarWareHouse.User.updateHandler;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public RadioButton viewerButton;
    @FXML
    private AnchorPane root;
    @FXML
    private TextField usernameField;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private PasswordField passwordField;
    @FXML
    private ToggleGroup typeGroup;

    private SceneLoader sceneLoader;
    private NetworkUtil connection;

    String currentType = "Viewer";
    private updateHandler uHandler;


    private void typeChange(String newType){
        if(newType.equals(currentType))
            return;
        boolean disable= newType.equals("Viewer");

        if(!disable)usernameField.setPromptText("Username");
        else usernameField.setPromptText("Viewer");

        usernameField.setDisable(disable);
        usernameLabel.setDisable(disable);
        passwordField.setDisable(disable);
        passwordLabel.setDisable(disable);

        usernameField.setText(null);
        passwordField.setText(null);

        currentType = newType;
    }
    public void init(){
        typeChange("Viewer");
        viewerButton.setSelected(true);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        typeGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle toggle, Toggle t1) {
                if(typeGroup.getSelectedToggle() != null) {
                    typeChange(((RadioButton)typeGroup.getSelectedToggle()).getText());

                }
            }
        });
    }

    public void setSceneLoader(SceneLoader sceneLoader, NetworkUtil connection, updateHandler uHandler) {
        this.sceneLoader = sceneLoader;
        this.connection = connection;
        this.uHandler = uHandler;
    }

    public void resetClicked(ActionEvent actionEvent) {
        typeChange("Viewer");
        viewerButton.setSelected(true);
    }

    private boolean loginCheck(String name, String pass){
        if(name == null) name = "";
        if(pass == null) pass = "";
        try{
            connection.write("LOGIN");
            connection.write(name);
            connection.write(pass);
            connection.write(currentType);
            String status = (String) connection.read();
            return status.equals("Successful");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    private void loginFailed(){
        LoginFailed.showloginFailed(root,2,"LOGIN FAILED",48);
    }
    public void loginClicked(ActionEvent actionEvent) {
        if(loginCheck(usernameField.getText(),passwordField.getText()))
        {
            uHandler.setElements(currentType);
            if(currentType.equals("Manufacturer")){
                sceneLoader.loadManufacturer();
            }
            else if(currentType.equals("Viewer")){
                sceneLoader.loadViewer();
            }
            else if(currentType.equals("Admin")){
                sceneLoader.loadAdmin();;
            }
        }
        else{
            loginFailed();
        }
    }

    public void returnClicked(ActionEvent actionEvent) {
        sceneLoader.loadMenu();
    }
}
