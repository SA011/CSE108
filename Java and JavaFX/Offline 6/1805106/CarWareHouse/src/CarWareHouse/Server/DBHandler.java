package CarWareHouse.Server;

import CarWareHouse.Car;
import CarWareHouse.UserInfo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class DBHandler {
    //
    private static final String loginStatement = "SELECT * FROM Logininfo where USERNAME = ? and PASSWORD = ? and TYPE = ?";
    private static final String addCarStatement = "INSERT INTO Carlist(Registration,Year,Color1,Color2,Color3," +
            "Manufacturer,Model,Price,Quantity,Image) VALUES(?,?,?,?,?,?,?,?,?,?)";
    private static final String addUserStatement = "INSERT INTO LoginInfo(USERNAME,PASSWORD,TYPE) VALUES(?,?,?)";
    private static final String deleteStatement = "DELETE FROM Carlist where Registration = ?";
    private static final String deleteUserStatement = "DELETE FROM LoginInfo where USERNAME = ? and PASSWORD = ? and TYPE = ?";
    //


    private Connection sqlConnection;


    public DBHandler() {
        try{
            Class.forName("org.sqlite.JDBC");
            String dbAddress = "jdbc:sqlite:src/CarWareHouse/Server/CarDB.sqlite";
            sqlConnection = DriverManager.getConnection(dbAddress);
        }catch (Exception e){
            System.out.println(e);
            sqlConnection = null;
        }
    }


    public boolean loginVerification(String userName, String password, String type){
        if(type.equals("Viewer"))
            return true;
        ResultSet rs = null;
        try {
            PreparedStatement pr =sqlConnection.prepareStatement(loginStatement);
            pr.setString(1,userName);
            pr.setString(2,password);
            pr.setString(3,type);
            rs = pr.executeQuery();
            return rs.next();
        } catch (SQLException throwables) {

        }
        return false;
    }


    public List query(String registration, String carMake, String carModel){
        List list = new ArrayList<Car>();
        try{
            String s = "SELECT * FROM Carlist";
            if(!registration.isEmpty()||!carMake.isEmpty()||!carModel.isEmpty())s = s + " where";

            int ind = 0;
            if(!registration.isEmpty()){
                s = s + " Registration = ?";
                ind = 1;
            }
            if(!carMake.isEmpty()){
                s = s + (ind==0?"":" and") + " Manufacturer = ?";
                ind = 1;
            }
            if(!carModel.isEmpty()) s = s + (ind==0?"":" and") +" Model = ?";

            PreparedStatement pr = sqlConnection.prepareStatement(s);
            if(!registration.isEmpty())pr.setString(ind++,registration);
            if(!carMake.isEmpty())pr.setString(ind++,carMake);
            if(!carModel.isEmpty())pr.setString(ind++,carModel);

            ResultSet rs = pr.executeQuery();

            while(rs.next()){
                String[] val = new String[10];
                for(int i=0;i<9;i++)
                    val[i] = rs.getString(i+1);
                byte[] img = rs.getBytes(10);
                list.add(new Car(val,img));
            }
        }catch(Exception e){

        }
        return list;
    }



    public void addCar(Car newCar){
        try{
            PreparedStatement pr = sqlConnection.prepareStatement(addCarStatement);
            pr.setString(1,newCar.getCarReg());
            pr.setString(2,Integer.valueOf(newCar.getYear()).toString());
            pr.setString(3,newCar.getColor1());
            pr.setString(4,newCar.getColor2());
            pr.setString(5,newCar.getColor3());
            pr.setString(6,newCar.getCarMake());
            pr.setString(7,newCar.getCarModel());
            pr.setString(8,Integer.valueOf(newCar.getPrice()).toString());
            pr.setString(9,Integer.valueOf(newCar.getQuantity()).toString());
            pr.setBytes(10,newCar.getImage());
            pr.execute();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void addUser(UserInfo newUser){
        try{
            PreparedStatement pr = sqlConnection.prepareStatement(addUserStatement);
            pr.setString(1,newUser.getUserName());
            pr.setString(2,newUser.getPassword());
            pr.setString(3,newUser.getType());
            pr.execute();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void removeUser(UserInfo oldUser) {
        try{
            PreparedStatement pr = sqlConnection.prepareStatement(deleteUserStatement);

            pr.setString(1,oldUser.getUserName());
            pr.setString(2,oldUser.getPassword());
            pr.setString(3,oldUser.getType());

            pr.execute();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public Object queryUser() {
        List allUser = new ArrayList<UserInfo>();
        try{
            PreparedStatement pr =sqlConnection.prepareStatement("SELECT * FROM LoginInfo");
            ResultSet rs = pr.executeQuery();
            while (rs.next()){
                allUser.add(new UserInfo(rs.getString(1),rs.getString(2),rs.getString(3)));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return allUser;
    }


    public void removeCar(String registration){
        try{
            PreparedStatement pr =sqlConnection.prepareStatement(deleteStatement);
            pr.setString(1,registration);
            pr.execute();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void closeConnection(){
        try {
            sqlConnection.close();
        } catch (SQLException throwables) {

        }
    }

}
