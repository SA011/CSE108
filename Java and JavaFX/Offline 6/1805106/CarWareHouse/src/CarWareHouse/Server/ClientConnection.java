package CarWareHouse.Server;

import CarWareHouse.Car;
import CarWareHouse.NetworkUtil;
import CarWareHouse.UserInfo;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ClientConnection implements Runnable{
    private NetworkUtil con;
    private Thread thread;
    private DBHandler dbcon;
    private ClientUpdater cUpdate;
    private UserInfo currentUser = null;
    private HashMap<UserInfo,Integer> loginCounter;
    public String getLoggedInType() {
        return loggedInType;
    }
    private String loggedInType = null;
    public ClientConnection(Socket s, DBHandler db, ClientUpdater cUpd,HashMap<UserInfo,Integer> loginCounter){
        this.loginCounter = loginCounter;
        try {
            con = new NetworkUtil(s);
            dbcon = db;
            cUpdate = cUpd;
        } catch (IOException e) {
            return;
        }
        thread = new Thread(this);
        thread.start();
    }
    private void login() throws Exception{
        String name = (String)con.read();
        String pass = (String)con.read();
        String type = (String)con.read();
        boolean ok = dbcon.loginVerification(name,pass,type);
        if(ok){
            boolean admin = type.equals("Admin");
            Object[] data = new Object[(admin?3:1)];
            data[0] = "Successful";
            if(admin) data[1] = dbcon.queryUser();
            loggedInType = type;
            if(admin){
                List l = new ArrayList<Integer>();
                for(UserInfo user:(List<UserInfo>)data[1]){
                    l.add(loginCounter.getOrDefault(user,0));
                }
                data[2] = l;
            }

            currentUser = new UserInfo(name,pass,type);
            loginCounter.put(currentUser,loginCounter.getOrDefault(currentUser,0)+1);
            sendData(data);
            cUpdate.addChange("NEWLOGIN","",currentUser);
        }
        else {
            con.write("Failed");
        }
    }

    private void query() throws Exception{
        String reg = (String)con.read();
        String carMake = (String)con.read();
        String carModel = (String)con.read();
        List l = dbcon.query(reg,carMake,carModel);
        Object[] obj = new Object[1];
        obj[0]= l;
        sendData(obj);

    }

    private void addCar() throws Exception{
        Car newCar = (Car)con.read();
        List l = dbcon.query(newCar.getCarReg(),newCar.getCarMake(),newCar.getCarModel());
        if(!l.isEmpty())return;
        dbcon.addCar(newCar);

        cUpdate.addChange("ADD",newCar.getCarReg(),newCar);
    }

    private void removeCar() throws Exception{
        String reg = (String)con.read();
        dbcon.removeCar(reg);

        cUpdate.addChange("REMOVE",reg,new Car());
    }

    private void editCar() throws Exception{
        String reg = (String)con.read();
        Car newCar = (Car)con.read();
        dbcon.removeCar(reg);
        dbcon.addCar(newCar);

        cUpdate.addChange("EDIT",reg,newCar);
    }


    private void addUser() throws Exception{
        UserInfo newUser = (UserInfo)con.read();
        dbcon.addUser(newUser);

        cUpdate.addChange("ADD","",newUser);
    }

    private void removeUser() throws Exception{
        UserInfo oldUser = (UserInfo)con.read();

        dbcon.removeUser(oldUser);

        cUpdate.addChange("REMOVE","",oldUser);
    }

    private void editUser() throws Exception{
        removeUser();
        addUser();
    }
    public boolean checkUser(UserInfo user){
        return currentUser!=null&&currentUser.equals(user);
    }
    private void logOutUser() throws Exception{
        UserInfo user = (UserInfo)con.read();

        cUpdate.addChange("LOGOUT","",user);
    }

    public synchronized void sendData(Object[] obj){
        if(con.isClose())return;
        for(int i=0;i < obj.length ;i++){
            try {
                con.write(obj[i]);
            } catch (IOException e) {

            }
        }
    }
    public synchronized void logOut(){
        loginCounter.put(currentUser,loginCounter.getOrDefault(currentUser,1)-1);
        loggedInType = null;
        Object[] obj = new Object[1];
        obj[0]= "LOGOUT";
        sendData(obj);
        cUpdate.addChange("DECREASE","",currentUser);
        currentUser = null;
    }
    @Override
    public void run() {
        while(!con.isClose()){
            String s = null;
            try {
                s = (String) con.read();
                if(s == null)continue;
                if(s.equals("LOGIN")){
                    login();
                }
                else if(s.equals("QUERY")) {
                    query();
                }
                else if(s.equals("ADD")){
                    addCar();
                }
                else if(s.equals("REMOVE")){
                    removeCar();
                }
                else if(s.equals("EDIT")){
                    editCar();
                }
                else if(s.equals("LOGOUT")){
                    logOut();
                }
                else if(s.equals("REMOVEUSER")){
                    removeUser();
                }
                else if(s.equals("ADDUSER")){
                    addUser();
                }
                else if(s.equals("EDITUSER")){
                    editUser();
                }
                else if(s.equals("LOGOUTADMIN")){
                    logOutUser();
                }
            } catch (Exception e) {
                try {
                    con.closeConnection();
                } catch (IOException ioException) {

                }
                return;
            }
        }
    }
}
