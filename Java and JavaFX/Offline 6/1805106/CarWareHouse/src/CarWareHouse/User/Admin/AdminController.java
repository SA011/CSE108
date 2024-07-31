package CarWareHouse.User.Admin;

import CarWareHouse.NetworkUtil;
import CarWareHouse.User.Admin.NewUser.NewUser;
import CarWareHouse.User.Admin.UserCell.UserCell;
import CarWareHouse.User.SceneLoader;
import CarWareHouse.User.updateHandler;
import CarWareHouse.UserInfo;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminController implements Initializable {
    @FXML
    private ListView userList;


    private SceneLoader sceneLoader;
    private NetworkUtil connection;
    private updateHandler uHandler;

    private ObservableList<UserCell> userCollection;
    public void setSceneLoader(SceneLoader sceneLoader, NetworkUtil connection, updateHandler uHandler) {
        this.connection = connection;
        this.sceneLoader = sceneLoader;
        this.uHandler = uHandler;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userCollection = FXCollections.observableArrayList();

        userList.setItems(userCollection);

        userList.setCellFactory(listView -> new ListCell(){
            @Override
            protected void updateItem(Object o, boolean b) {
                super.updateItem(o, b);
                if(b){
                    setGraphic(null);
                }
                else{
                    setGraphic(((UserCell)o).getBox());
                }
            }
        });
    }

    public void logout(){
        sceneLoader.loadMenu();
    }

    public void addUser(Object o, AdminController aController, int numberOfLogin) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                userCollection.add(new UserCell((UserInfo)o,aController,numberOfLogin));
            }
        });
    }

    public void removeUser(Object o) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                for(UserCell cell:userCollection){
                    if(cell.getCurrentUser().equals(o)){
                        userCollection.remove(cell);
                        break;
                    }
                }
            }
        });
    }

    public void decreaseUser(Object o) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                for(UserCell cell:userCollection){
                    if(cell.getCurrentUser().equals(o)){
                        cell.decreaseUser();
                        break;
                    }
                }
            }
        });
    }

    public void increaseUser(Object o) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                for(UserCell cell:userCollection){
                    if(cell.getCurrentUser().equals(o)){
                        cell.increaseUser();
                        break;
                    }
                }
            }
        });
    }

    public void editQuery(Object obj, Object obj2, Object obj3, AdminController aController) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                String query = (String)obj;
                if(query.equals("ADD")){
                    addUser(obj3,aController,0);
                }
                else if(query.equals("REMOVE")){
                    removeUser(obj3);
                }
                else if(query.equals("EDIT")){
                    removeUser(obj2);
                    addUser(obj3,aController,0);
                }
                else if(query.equals("DECREASE")){
                    decreaseUser(obj3);
                }
                else if(query.equals("NEWLOGIN")){
                    increaseUser(obj3);
                }
            }
        });
    }

    public void logoutClicked(ActionEvent actionEvent) {
        try{
            connection.write("LOGOUT");
        }catch (Exception e){

        }
    }

    public void addClicked(ActionEvent actionEvent) {
        UserInfo newUser = NewUser.showNewUserWindow(null,this);
        if(newUser == null)
            return;
        try{
            connection.write("ADDUSER");
            connection.write(newUser);
        }catch (Exception e){

        }
    }

    public void removeClicked(UserCell userCell) {
        try{
            connection.write("REMOVEUSER");
            connection.write(userCell.getCurrentUser());
        }catch(Exception e){

        }
    }

    public void logoutClicked(UserCell userCell) {
        try{
            connection.write("LOGOUTADMIN");
            connection.write(userCell.getCurrentUser());
        }catch (Exception e){

        }
    }
    public boolean checkDuplicate(UserInfo newUserInfo) {
        for(UserCell cell:userCollection){
            if(newUserInfo.getUserName().equals(cell.getCurrentUser().getUserName())&&newUserInfo.getType().equals(cell.getCurrentUser().getType()))
                return true;
        }
        return false;
    }
    public void userEdited(UserInfo olduser,UserInfo newUser){
        try{
            connection.write("EDITUSER");
            connection.write(olduser);
            connection.write(newUser);
        }catch (Exception e){

        }
    }
}
