package CarWareHouse.User.Manufacturer.newCarWindow;

import CarWareHouse.Car;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import javax.imageio.ImageIO;
import java.io.*;
import java.nio.file.Files;

public class NewCarController {
    private static final int width = 480, height = 360;
    static Car newCar = null;
    static NewCarController newController = null;
    public static Car showWindow(Car car) {
        try{
            newController = new NewCarController(car);
            newController = null;
        }catch (Exception e){
            return null;
        }
        return newCar;
    }

    public static void logout(){
        if(newController != null){
            newController.window.close();
        }
    }
    @FXML
    private TextField manufacturerField;
    @FXML
    private TextField modelField;
    @FXML
    private TextField registrationField;
    @FXML
    private TextField yearField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField quantityField;
    @FXML
    private ColorPicker color1Picker;
    @FXML
    private ColorPicker color2Picker;
    @FXML
    private ColorPicker color3Picker;
    @FXML
    private ImageView imageField;

    private Stage window;

    final private String invalid = "-fx-border-color: red;";
    final private String valid = "-fx-border-color: -fx-control-inner-background;";


    public static Image createImage(byte[] s) {
        if(s == null) return null;
        return new Image(new ByteArrayInputStream(s));
    }


    private byte[] imagetoByteArray(Image img){
        if(img == null)return null;
        ByteArrayOutputStream s = new ByteArrayOutputStream();
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(img,null),"png",s);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s.toByteArray();
    }


    static byte[] defaultCar;
    private void loadCar(Car car){
        if(car == null){
            imageField.setImage(createImage(defaultCar));
            return;
        }
        manufacturerField.setText(car.getCarMake());
        modelField.setText(car.getCarModel());
        yearField.setText(Integer.valueOf(car.getYear()).toString());
        priceField.setText(Integer.valueOf(car.getPrice()).toString());
        quantityField.setText(Integer.valueOf(car.getQuantity()).toString());
        color1Picker.setValue(Color.valueOf(car.getColor1()));
        color2Picker.setValue(Color.valueOf(car.getColor2()));
        color3Picker.setValue(Color.valueOf(car.getColor3()));
        registrationField.setText(car.getCarReg());
        imageField.setImage(createImage(car.getImage()));
    }



    private FileChooser fileChooser;


    public NewCarController(Car car)throws Exception{

        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif", "*.jpeg"));

        try{
            defaultCar = Files.readAllBytes(new File("src/default.png").toPath());
        }
        catch (Exception e){
            e.printStackTrace();
        }
        try {
            window = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("NewCarFXML.fxml"));
            loader.setController(this);
            Scene scene = new Scene(loader.load(), width, height);
            window.setScene(scene);
            window.setTitle("Add Car");
            loadCar(car);
            window.showAndWait();
        }catch (Exception e){
            e.printStackTrace();
        }
    }



    private boolean checkEmpty(TextField text,boolean numberCheck){
        if(text.getText().isEmpty()){
            text.setStyle(invalid);
            return false;
        }
        if(numberCheck){
            if(!text.getText().matches("[0-9]+")){
                text.setStyle(invalid);
                return false;
            }
        }
        text.setStyle(valid);
        return true;
    }



    public void saveClicked(ActionEvent actionEvent){
        if(!checkEmpty(manufacturerField,false))return;
        if(!checkEmpty(registrationField,false))return;
        if(!checkEmpty(modelField,false))return;
        if(!checkEmpty(yearField,true))return;
        if(!checkEmpty(priceField,true))return;
        if(!checkEmpty(quantityField,true))return;

        String[] values = {registrationField.getText(),yearField.getText(),color1Picker.getValue().toString(),color2Picker.getValue().toString(),
                color3Picker.getValue().toString(),manufacturerField.getText(),modelField.getText(),priceField.getText(),quantityField.getText()};
        newCar = new Car(values,imagetoByteArray(imageField.getImage()));
        window.close();
    }



    public void resetClicked(ActionEvent actionEvent){
        manufacturerField.setText(null);
        registrationField.setText(null);
        modelField.setText(null);
        priceField.setText(null);
        quantityField.setText(null);
        yearField.setText(null);
        color1Picker.setValue(Color.WHITE);
        color2Picker.setValue(Color.WHITE);
        color3Picker.setValue(Color.WHITE);
        imageField.setImage(null);
    }

    public void addImageClicked(ActionEvent actionEvent){
        File file = fileChooser.showOpenDialog(window);
        if( file != null )
            imageField.setImage(new Image(file.toURI().toString(),imageField.getFitWidth(),imageField.getFitHeight(),true,true));
    }
    public void closeClicked(){
        window.close();
    }
}
