package CarWareHouse.User.Manufacturer.ListItem;

import CarWareHouse.Car;
import CarWareHouse.User.CellType;
import CarWareHouse.User.Manufacturer.ManufacturerController;
import CarWareHouse.User.Manufacturer.newCarWindow.NewCarController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;


public class ListItemController implements CellType {
    @FXML
    private HBox hBox;
    @FXML
    private Label manufacturerLabel;
    @FXML
    private Label modelLabel;
    @FXML
    private Circle color1;
    @FXML
    private Circle  color2;
    @FXML
    private Circle  color3;
    @FXML
    private Label priceLabel;
    @FXML
    private Label quantityLabel;
    @FXML
    private Label yearLabel;
    @FXML
    private Label registrationLabel;
    @FXML
    private Label soldoutTag;
    @FXML
    private ImageView imageField;

    public Car getCar() {
        return element;
    }

    private Car element;
    private ManufacturerController manufacturerController;



    public void setInfo(Car elem){
        element = elem;
        update();
    }



    public void update(){
        manufacturerLabel.setText(element.getCarMake());
        modelLabel.setText(element.getCarModel());
        try {
            color1.setFill(Paint.valueOf(element.getColor1()));
            color1.setVisible(true);
        }catch (Exception e){
            color1.setVisible(false);
        }
        try {
            color2.setFill(Paint.valueOf(element.getColor2()));
            color2.setVisible(true);
        }catch (Exception e){
            color2.setVisible(false);
        }
        try {
            color3.setFill(Paint.valueOf(element.getColor3()));
            color3.setVisible(true);
        }catch (Exception e){
            color3.setVisible(false);
        }
        priceLabel.setText("$"+Integer.valueOf(element.getPrice()).toString());
        quantityLabel.setText(Integer.valueOf(element.getQuantity()).toString());
        yearLabel.setText(Integer.valueOf(element.getYear()).toString());
        registrationLabel.setText(element.getCarReg());
        if(element.getQuantity()>0)soldoutTag.setVisible(false);
        else soldoutTag.setVisible(true);
        imageField.setImage(NewCarController.createImage(element.getImage()));
    }



    public ListItemController(Car elem, ManufacturerController manufacturer){
        element = elem;
        manufacturerController = manufacturer;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ListItemFXML.fxml"));
        loader.setController(this);
        try{
            loader.load();
        }catch (Exception e){

        }
        update();
    }

    public void editClicked(ActionEvent actionEvent){
        manufacturerController.editClicked(this);
    }

    public void removeClicked(ActionEvent actionEvent){
        manufacturerController.removeClicked(this);
    }


    public HBox getBox(){
        return hBox;
    }
}
