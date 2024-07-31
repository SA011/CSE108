package CarWareHouse.User.Admin.UserCell;

import CarWareHouse.User.Admin.AdminController;
import CarWareHouse.User.Admin.NewUser.NewUser;
import CarWareHouse.UserInfo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class UserCell {
    private AdminController adminController;

    @FXML
    private HBox hBox;

    @FXML
    private Label usernameField;
    @FXML
    private Label passwordField;
    @FXML
    private Label userTypeField;
    @FXML
    private Label loggedinCounter;
    @FXML
    private Button editButton;
    @FXML
    private Button removeButton;
    @FXML
    private Button logoutButton;


    private UserInfo currentUser;

    private int numberOfLogin;

    private void update(){
        usernameField.setText(currentUser.getUserName());
        passwordField.setText(currentUser.getPassword());
        userTypeField.setText(currentUser.getType());
        loggedinCounter.setText(Integer.valueOf(numberOfLogin).toString());

        if(currentUser.getType().equals("Viewer")||currentUser.getType().equals("Admin")){
            editButton.setDisable(true);
            logoutButton.setDisable(true);
            removeButton.setDisable(true);
            editButton.setVisible(false);
            logoutButton.setVisible(false);
            removeButton.setVisible(false);
        }
        else{
            editButton.setDisable(numberOfLogin>0);
            logoutButton.setDisable(numberOfLogin==0);
            removeButton.setDisable(numberOfLogin>0);
            editButton.setVisible(true);
            logoutButton.setVisible(true);
            removeButton.setVisible(true);
        }
    }


    public UserInfo getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(UserInfo currentUser,int i) {
        this.currentUser = currentUser;
        numberOfLogin = i;
        update();
    }


    public UserCell(UserInfo user, AdminController manufacturer, int i){
        currentUser = user;
        numberOfLogin = i;
        adminController = manufacturer;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("CellFXML.fxml"));
        loader.setController(this);
        try{
            loader.load();
        }catch (Exception e){

        }
        update();
    }

    public HBox getBox(){
        return hBox;
    }

    public int getNumberOfLogin() {
        return numberOfLogin;
    }


    public void removeClicked(ActionEvent event){
        adminController.removeClicked(this);
    }

    public void editClicked(ActionEvent event){
        UserInfo newUser = NewUser.showNewUserWindow(currentUser,adminController);
        if(newUser == null)
            return;
        adminController.userEdited(currentUser,newUser);
    }

    public void logoutClicked(ActionEvent event){
        adminController.logoutClicked(this);
    }

    public void decreaseUser() {
        numberOfLogin--;
        update();
    }

    public void increaseUser() {
        numberOfLogin++;
        update();
    }
}
