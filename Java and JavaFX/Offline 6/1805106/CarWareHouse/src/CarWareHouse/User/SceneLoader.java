package CarWareHouse.User;

import CarWareHouse.User.Admin.AdminController;
import CarWareHouse.User.LoginPage.LoginController;
import CarWareHouse.User.MainMenu.MenuController;
import CarWareHouse.User.Manufacturer.ManufacturerController;
import CarWareHouse.User.Viewer.ViewerController;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import CarWareHouse.NetworkUtil;
public class SceneLoader {

    private final int width = 640;
    private final int height = 480;

    private Stage primaryStage;

    private Scene mainScene, loginScene, manufacturerScene, viewerScene, adminScene;


    private MenuController menuController;
    private LoginController loginController;
    private ManufacturerController manufacturerController;
    private ViewerController viewerController;
    private AdminController adminController;


    public ManufacturerController getManufacturerController() {
        return manufacturerController;
    }

    public ViewerController getViewerController() {
        return viewerController;
    }

    public AdminController getAdminController() {
        return adminController;
    }


    private void initScenes(NetworkUtil connection, updateHandler uHandler) throws Exception{
        //MainMenu
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("MainMenu/MenuFXML.fxml"));
        mainScene = new Scene(loader.load(), width, height);
        menuController = ((MenuController)loader.getController());
        menuController.setSceneLoader(this);


        //LoginPage
        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("LoginPage/LoginFXML.fxml"));
        loginScene = new Scene(loader.load(), width, height);
        loginController = ((LoginController)loader.getController());
        loginController.setSceneLoader(this, connection,uHandler);


        //Manufacturer
        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Manufacturer/ManufacturerFXML.fxml"));
        manufacturerScene = new Scene(loader.load(), width, height);
        manufacturerController = ((ManufacturerController)loader.getController());
        manufacturerController.setSceneLoader(this, connection,uHandler);


        //Viewer
        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Viewer/ViewerFXML.fxml"));
        viewerScene = new Scene(loader.load(), width, height);
        viewerController = ((ViewerController)loader.getController());
        viewerController.setSceneLoader(this, connection,uHandler);

        //Admin
        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Admin/AdminFXML.fxml"));
        adminScene = new Scene(loader.load(), width, height);
        adminController = ((AdminController)loader.getController());
        adminController.setSceneLoader(this, connection,uHandler);


        uHandler.setControllers(viewerController,manufacturerController,adminController,connection);

    }


    public SceneLoader(Stage stage, NetworkUtil connection, updateHandler uHandler) throws Exception{
        primaryStage = stage;

        primaryStage.setOnCloseRequest(e ->{
            System.exit(0);
        });
        try {
            initScenes(connection,uHandler);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void loadMenu(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                primaryStage.setScene(mainScene);
                primaryStage.setTitle("CAR WAREHOUSE");
                primaryStage.show();
            }
        });
    }

    public void loadLogin(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                primaryStage.setScene(loginScene);
                primaryStage.setTitle("LOGIN");
                loginController.init();
                primaryStage.show();
            }
        });
    }

    public void loadManufacturer(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                primaryStage.setScene(manufacturerScene);
                primaryStage.setTitle("MANUFACTURER");
                primaryStage.show();
            }
        });
    }

    public void loadViewer(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                primaryStage.setScene(viewerScene);
                primaryStage.setTitle("VIEWER");
                primaryStage.show();
            }
        });
    }

    public void loadAdmin(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                primaryStage.setScene(adminScene);
                primaryStage.setTitle("ADMIN");
                primaryStage.show();
            }
        });
    }
}
