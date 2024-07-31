package CarWareHouse;

import java.io.Serial;
import java.io.Serializable;

public class Car implements Serializable {
    //@Serial
    //private static final long serialVersionUID = 7684197124656193113L;
    private String carReg;
    private String color1;
    private String color2;
    private String color3;

    public Car() {
    }

    public void setCarMake(String carMake) {
        this.carMake = carMake;
    }

    private String carMake;
    private String carModel;
    private byte[] image;
    private int price;
    private int year;

    public void setCarReg(String carReg) {
        this.carReg = carReg;
    }

    public void setColor1(String color1) {
        this.color1 = color1;
    }

    public void setColor2(String color2) {
        this.color2 = color2;
    }

    public void setColor3(String color3) {
        this.color3 = color3;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    private int quantity;
    public Car(String[] value,byte[] img){
        if(value.length<9)
            return;
        carReg = value[0];
        year = Integer.valueOf(value[1]);
        color1 = value[2];
        color2 = value[3];
        color3 = value[4];
        carMake = value[5];
        carModel = value[6];
        price = Integer.valueOf(value[7]);
        quantity = Integer.valueOf(value[8]);
        image = img;
    }

    public byte[] getImage() {
        return image;
    }

    public String getCarReg() {
        return carReg;
    }

    public String getColor1() {
        return color1;
    }

    public String getColor2() {
        return color2;
    }

    public String getColor3() {
        return color3;
    }

    public String getCarMake() {
        return carMake;
    }

    public String getCarModel() {
        return carModel;
    }

    public int getPrice() {
        return price;
    }

    public int getYear() {
        return year;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "Car Details: \n"+
                "   Car Registration Number = " + carReg + "\n" +
                "   Year = " + year +"\n" +
                "   Color1 = " + color1 + "\n" +
                "   Color2 = " + color2 + "\n" +
                "   Color3 = " + color3 + "\n" +
                "   CarMake = " + carMake + "\n" +
                "   CarModel = " + carModel + "\n" +
                "   Price = " + price + "\n" +
                "   Quantity = " + quantity + "\n" +
                "   Image = " + image + "\n" ;

    }
}
