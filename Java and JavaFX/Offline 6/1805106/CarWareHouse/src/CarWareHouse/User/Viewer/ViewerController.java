package CarWareHouse.User.Viewer;

import CarWareHouse.Car;
import CarWareHouse.NetworkUtil;
import CarWareHouse.User.CellType;
import CarWareHouse.User.SceneLoader;
import CarWareHouse.User.Viewer.ListItem.ListItemController;
import CarWareHouse.User.updateHandler;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class ViewerController implements Initializable {
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


    public void addCar(Object obj,ViewerController viewer){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                carCollection.add(new ListItemController((Car)obj,viewer));
            }
        });
    }

    public void editQuery(Object string,Object reg,Object car,ViewerController viewerController){
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
                            addCar(car, viewerController);
                        } else return;
                    }
                    if ((carMake.isEmpty() || carMake.equals(((Car) car).getCarMake())) && (carModel.isEmpty() || carModel.equals(((Car) car).getCarModel()))) {
                        addCar(car, viewerController);
                    }
                }
            }
        });
    }


    public void logout(){
        sceneLoader.loadMenu();
    }


    public void setSceneLoader(SceneLoader sceneLoader, NetworkUtil connection, updateHandler uHandler) {
        this.sceneLoader = sceneLoader;
        this.connection = connection;
        this.uHandler = uHandler;
    }


    public void searchCarClicked(ActionEvent actionEvent) {
        carReg = registrationField.getText();
        carMake = manufacturerField.getText();
        carModel = modelField.getText();

        try{
            connection.write("QUERY");
            connection.write(carReg);
            connection.write(carMake);
            connection.write(carModel);
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    public void logoutButtonClicked(ActionEvent actionEvent) {
        try{
            connection.write("LOGOUT");
        }catch (Exception e){

        }
    }

    public void buyClicked(ListItemController element) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if(element.getCar().getQuantity()<=0){
                    System.out.println("SOLD OUT");
                }
                else{
                    try{
                        connection.write("EDIT");
                        Car temp = element.getCar();
                        connection.write(temp.getCarReg());
                        temp.setQuantity(temp.getQuantity()-1);
                        connection.write(temp);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
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
