import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

class Car{
    private String carReg , color1, color2, color3, carMake, carModel;
    private int price, year;
    Car(String[] value){
        carReg = value[0];
        year = Integer.valueOf(value[1]);
        color1 = value[2];
        color2 = value[3];
        color3 = value[4];
        carMake = value[5];
        carModel = value[6];
        price = Integer.valueOf(value[7]);
    }
    String getCarReg(){
        return carReg;
    }
    String getCarMake(){
        return carMake;
    }
    String getCarModel(){
        return carModel;
    }
    String toFileFormat(){
        return carReg+"," + year + "," + color1 + "," + color2 + "," + color3 + "," + carMake + "," + carModel + "," +price;
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
                "   Price = " + price + "\n" ;
    }
}
public class Main{
    static void LoadWarehouse(String filename, List<Car> Cars){
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String s = br.readLine();
            while (s != null) {
                Cars.add(new Car(s.split(",")));
                s = br.readLine();
            }
            br.close();
        }
        catch (Exception e){
            e.getStackTrace();
        }
    }
    static void WriteWarehouse(String filename, List<Car> Cars){
        try {
            BufferedWriter bw= new BufferedWriter(new FileWriter(filename));
            for(Car c: Cars) {
                bw.write(c.toFileFormat());
                bw.write("\n");
            }
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    static int searchByReg(String s, List<Car> Cars){
        for(int i = 0;i < Cars.size();i++){
            if(Cars.get(i).getCarReg().equalsIgnoreCase(s)){
                return i;
            }
        }
        return -1;
    }
    static void SearchCar(Scanner sc, List<Car> Cars){
        System.out.println("(1) By Registration Number");
        System.out.println("(2) By Car Make and Car Model");
        System.out.println("(3) Back to Main Menu");
        try {
            int inp = sc.nextInt();
            if (inp == 1) {
                System.out.print("Enter the Car's Registration Number: ");
                String s = sc.next();
                int ind = searchByReg(s, Cars);
                if (ind == -1)
                    System.out.println("No such car with this Registration Number");
                else
                    System.out.println(Cars.get(ind));
            } else if (inp == 2) {
                System.out.print("Enter the name of Car's Manufacturer: ");
                String s = sc.next();
                System.out.print("Enter the name of Car's Model: ");
                String s2 = sc.next();
                int ind = -1;
                boolean any = s2.equalsIgnoreCase("ANY");
                for (int i = 0; i < Cars.size(); i++) {
                    Car temp = Cars.get(i);
                    if (temp.getCarMake().equalsIgnoreCase(s) && (any || temp.getCarModel().equalsIgnoreCase(s2))) {
                        ind = i;
                        System.out.println(temp);
                    }
                }
                if (ind == -1)
                    System.out.println("No such car with this Car Make and Car Model");
            } else if (inp == 3) return;
            else System.out.println("INVALID INPUT. Please Enter a number between 1 and 3");
        }catch (InputMismatchException e){
            System.out.println("INVALID INPUT. Please Enter a number between 1 and 3");
            sc.next();
        }
        SearchCar(sc, Cars);
    }
    static void AddCar(Scanner sc, List<Car> Cars){
        String[] values = new String[8];
        System.out.print("Enter the Car's Registration Number: ");
        values[0] = sc.next();
        if(searchByReg(values[0],Cars)!=-1){
            System.out.println("The Registration Number already exists.");
            return;
        }
        try {
            System.out.print("Enter the Car's Manufacturing Year: ");
            values[1] = Integer.valueOf(sc.nextInt()).toString();
            System.out.print("Enter the Car's Color 1: ");
            values[2] = sc.nextLine();
            System.out.print("Enter the Car's Color 2: ");
            values[3] = sc.nextLine();
            System.out.print("Enter the Car's Color 3: ");
            values[4] = sc.nextLine();
            System.out.print("Enter the name of Car's Manufacturer: ");
            values[5] = sc.next();
            System.out.print("Enter the name of Car's Model: ");
            values[6] = sc.next();
            System.out.print("Enter the Car's Price: ");
            values[7] = Integer.valueOf(sc.nextInt()).toString();
            Cars.add(new Car(values));
        }catch (InputMismatchException e){
            System.out.println("INVALID INPUT.");
            sc.next();
        }

    }
    static void DeleteCar(Scanner sc, List<Car> Cars){
        System.out.print("Enter the Car's Registration Number: ");
        String s = sc.next();
        int ind = -1;
        for (int i = 0; i < Cars.size(); i++) {
            if (Cars.get(i).getCarReg().equalsIgnoreCase(s)) {
                ind = i;
                Cars.remove(ind);
                break;
            }
        }
        if (ind == -1)
            System.out.println("No such car with this Registration Number");
    }
    static void MainMenu(Scanner sc, List<Car> Cars){
        System.out.println("(1) Search Cars");
        System.out.println("(2) Add Car");
        System.out.println("(3) Delete Car");
        System.out.println("(4) Exit System");
        try {
            int inp = sc.nextInt();
            if (inp == 1) SearchCar(sc, Cars);
            else if (inp == 2) AddCar(sc, Cars);
            else if (inp == 3) DeleteCar(sc, Cars);
            else if (inp == 4) return;
            else System.out.println("INVALID INPUT. Please Enter a number between 1 and 4");
        }
        catch (InputMismatchException e){
            System.out.println("INVALID INPUT. Please Enter a number between 1 and 4");
            sc.next();
        }
        MainMenu(sc, Cars);
    }
    public static void main(String[] args) {
        String filename = "cars.txt";
        List<Car> Cars = new ArrayList<>();
        LoadWarehouse(filename , Cars);
        Scanner sc = new Scanner(System.in);
        MainMenu(sc, Cars);
        WriteWarehouse(filename,Cars);
    }
}