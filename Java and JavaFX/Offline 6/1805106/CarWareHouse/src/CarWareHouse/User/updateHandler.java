package CarWareHouse.User;

import CarWareHouse.User.Admin.AdminController;
import CarWareHouse.User.Manufacturer.ManufacturerController;
import CarWareHouse.User.Viewer.ViewerController;
import CarWareHouse.NetworkUtil;
import java.util.List;

public class updateHandler implements Runnable{
    private Thread thread;
    private String userType;
    private NetworkUtil connection;
    private ViewerController vController;
    private ManufacturerController mController;
    private AdminController aController;


    public void setControllers(ViewerController vController, ManufacturerController mController, AdminController aController, NetworkUtil connection){
        this.vController = vController;
        this.mController = mController;
        this.aController = aController;
        this.connection = connection;
    }


    public void setElements(String type){
        userType = type;
        thread = new Thread(this);
        thread.start();
    }


    public void logout(){
        if(userType.equals("Viewer")){
            vController.logout();
        }
        else if(userType.equals("Manufacturer")){
            mController.logout();
        }
        else if(userType.equals("Admin")){
            aController.logout();
        }
        userType =null;
    }
    @Override
    public void run() {
        while(userType != null){
            try{
                Object obj = connection.read();
                if(obj == null)continue;
                if(obj instanceof List){
                    if(userType.equals("Admin")){
                        Object obj2 = connection.read();
                        int len = ((List)obj).size();
                        for(int i=0 ; i<len ; i++){
                            aController.addUser(((List)obj).get(i),aController,((List<Integer>)obj2).get(i));
                        }
                        continue;
                    }
                    if(userType.equals("Viewer"))vController.clear();
                    if(userType.equals("Manufacturer"))mController.clear();
                    for(Object o: (List)obj){
                        if(userType.equals("Viewer")){
                            vController.addCar(o,vController);
                        }
                        else if(userType.equals("Manufacturer")){
                            mController.addCar(o,mController);
                        }
                        else if(userType.equals("Admin")){
                            aController.addUser(o,aController,0);
                        }
                    }
                }
                else if(("LOGOUT".equals((String)obj))){
                    logout();
                }
                else{
                    Object obj2 = connection.read();
                    Object obj3 = connection.read();
                    if(userType.equals("Viewer")){
                        vController.editQuery(obj,obj2,obj3,vController);
                    }
                    else if(userType.equals("Manufacturer")){
                        mController.editQuery(obj,obj2,obj3,mController);
                    }
                    else if(userType.equals("Admin")){
                        aController.editQuery(obj,obj2,obj3,aController);
                    }
                }
            }catch (Exception e){

            }
        }
    }
}
