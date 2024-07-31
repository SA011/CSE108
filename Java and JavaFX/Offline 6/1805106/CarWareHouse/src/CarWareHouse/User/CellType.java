package CarWareHouse.User;
import CarWareHouse.Car;
import javafx.scene.layout.HBox;

public interface CellType {
    public Car getCar();
    public void update();
    public HBox getBox();

    void setInfo(Car car);
}
