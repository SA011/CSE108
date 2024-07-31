package CarWareHouse.User.Manufacturer;

import CarWareHouse.Car;
import CarWareHouse.NetworkUtil;
import CarWareHouse.User.CellType;
import CarWareHouse.User.Manufacturer.ListItem.ListItemController;
import CarWareHouse.User.Manufacturer.newCarWindow.NewCarController;
import CarWareHouse.User.SceneLoader;
import CarWareHouse.User.updateHandler;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ManufacturerController implements Initializable {
    @FXML
    private Button removeCarButton;
    @FXML
    private Button editCarButton;
    @FXML
    private ListView carList;
    @FXML
    private TextField registrationField;
    @FXML
    private TextField manufacturerField;
    @FXML
    private TextField modelField;

    private SceneLoader sceneLoader;
    private NetworkUtil connection;

    private ObservableList<CellType> carCollection;

    private String carReg=null,carMake=null,carModel=null;
    private updateHandler uHandler;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        carCollection = FXCollections.observableArrayList();
        carList.setItems(carCollection);
        carList.setCellFactory(listView -> new ListCell(){
            @Override
            protected void updateItem(Object o, boolean b) {
                super.updateItem(o, b);
                if(b){
                    setGraphic(null);
                }
                else{
                    setGraphic(((CellType)o).getBox());
                }
            }
        });

    }


    public void addCar(Object obj, ManufacturerController manufacturerController){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                carCollection.add(new ListItemController((Car)obj,manufacturerController));
            }
        });
    }

    public void editQuery(Object string,Object reg,Object car,ManufacturerController manufacturerController){
        if(carReg == null)
            return;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {


                String str = (String) string;
                if (str.equals("EDIT")) {
                    for (CellType c : carCollection) {
                        if (c.getCar().getCarReg().equals(reg)) {
                            c.setInfo((Car) car);
                            break;
                        }
                    }
                } else if (str.equals("REMOVE")) {
                    for (CellType c : carCollection) {
                        if (c.getCar().getCarReg().equals(reg)) {
                            carCollection.remove(c);
                            break;
                        }
                    }
                } else {
                    if (!carReg.isEmpty()) {
                        if (carReg.equals(reg)) {
                            addCar(car, manufacturerController);
                        } else return;
                    }
                    if ((carMake.isEmpty() || carMake.equals(((Car) car).getCarMake())) && (carModel.isEmpty() || carModel.equals(((Car) car).getCarModel()))) {
                        addCar(car, manufacturerController);
                    }
                }
            }
        });
    }


    public void setSceneLoader(SceneLoader sceneLoader, NetworkUtil connection, updateHandler uHandler) {
        this.sceneLoader = sceneLoader;
        this.connection = connection;
        this.uHandler = uHandler;
    }

    public void addCarClicked(ActionEvent actionEvent) {
        try{
            Car newCar = NewCarController.showWindow(null);
            if(newCar !=null){
                connection.write("ADD");
                connection.write(newCar);
            }
        }catch (Exception e){

        }
    }


    public void searchCarClicked(ActionEvent actionEvent) {
        carMake = manufacturerField.getText();
        carReg = registrationField.getText();
        carModel = modelField.getText();
        try{
            connection.write("QUERY");
            connection.write(carReg);
            connection.write(carMake);
            connection.write(carModel);
        }catch (Exception e){

        }
    }
    public void logout(){
        NewCarController.logout();
        sceneLoader.loadMenu();
    }
    public void logoutButtonClicked(ActionEvent actionEvent) {
        try{
            connection.write("LOGOUT");
        }catch (Exception e){

        }
    }



    public void removeClicked(ListItemController element) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try{
                    connection.write("REMOVE");
                    connection.write(element.getCar().getCarReg());
                }catch (Exception e){

                }
            }
        });

    }

    public void editClicked(ListItemController element) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try{
                    String reg = element.getCar().getCarReg();
                    Car newCar = NewCarController.showWindow(element.getCar());
                    if(newCar !=null){
                        connection.write("EDIT");
                        connection.write(reg);
                        connection.write(newCar);
                    }
                }catch (Exception e){

                }
            }
        });

    }



    public void clear() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                carCollection.clear();
            }
        });
    }
}
