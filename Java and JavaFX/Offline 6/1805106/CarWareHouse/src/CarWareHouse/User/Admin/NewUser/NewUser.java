package CarWareHouse.User.Admin.NewUser;

import CarWareHouse.User.Admin.AdminController;
import CarWareHouse.User.LoginPage.LoginFailed;
import CarWareHouse.UserInfo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class NewUser {

    private static final int width = 324, height = 173;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField usernameField;
    @FXML
    private AnchorPane rootPane;

    private Stage window;

    final private String invalid = "-fx-border-color: red;";
    final private String valid = "-fx-border-color: -fx-control-inner-background;";


    private static UserInfo newUserInfo;
    private static NewUser newUser = null;
    private static AdminController aController;
    public static UserInfo showNewUserWindow(UserInfo user,AdminController adminController){
        try{
            newUserInfo = null;
            aController = adminController;
            newUser = new NewUser(user);
            newUser = null;
        }catch (Exception e){
            return null;
        }
        return newUserInfo;
    }

    private NewUser(UserInfo user){
        try {
            window = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("NewUserFXML.fxml"));
            loader.setController(this);
            Scene scene = new Scene(loader.load(), width, height);
            window.setScene(scene);
            window.setTitle("Add Manufacturer");
            loadUser(user);
            window.showAndWait();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void loadUser(UserInfo user){
        if(user == null)return;

        usernameField.setText(user.getUserName());
        passwordField.setText(user.getPassword());

    }

    private boolean checkEmpty(TextField text){
        if(text.getText().isEmpty()){
            text.setStyle(invalid);
            return false;
        }
        text.setStyle(valid);
        return true;
    }

    public void closeClicked(ActionEvent actionEvent) {
        window.close();
    }

    public void saveClicked(ActionEvent actionEvent) {
        if(!checkEmpty(usernameField))
            return;
        if(!checkEmpty(passwordField))
            return;
        newUserInfo = new UserInfo(usernameField.getText(),passwordField.getText(),"Manufacturer");
        if(aController.checkDuplicate(newUserInfo)){
            LoginFailed.showloginFailed(rootPane,2,"USER ALREADY EXIST",32);
            newUserInfo = null;
        }
        else{
            window.close();
        }

    }
}
