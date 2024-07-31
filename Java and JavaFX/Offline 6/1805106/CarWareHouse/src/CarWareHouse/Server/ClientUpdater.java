package CarWareHouse.Server;

import CarWareHouse.UserInfo;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ClientUpdater implements Runnable{
    private Thread thread;
    private Queue queue;
    private List clientList;
    public ClientUpdater(List clients){
        queue = new LinkedList();
        thread = new Thread(this);
        clientList = clients;
        thread.start();
    }
    public synchronized void addChange(Object changeType, Object oldVal, Object newVal){
        queue.add(changeType);
        queue.add(oldVal);
        queue.add(newVal);
        notifyAll();
    }
    @Override
    public void run() {
        while(true){
            while(queue.size()<3){
                synchronized (this){
                    try {
                        wait();
                    } catch (InterruptedException e) {

                    }
                }
            }
            Object[] obj= new Object[3];
            for(int i=0;i<3;i++){
                obj[i] = queue.peek();
                queue.remove();
            }


            if("LOGOUT".equals((String)obj[0])){
                UserInfo user = (UserInfo)obj[2];
                for(Object c:clientList){
                    if(((ClientConnection)c).checkUser(user)){
                        ((ClientConnection)c).logOut();
                        break;
                    }
                }
                continue;
            }


            boolean admin = (obj[2] instanceof UserInfo);

            for(Object c:clientList){
                if(((ClientConnection)c).getLoggedInType() == null)continue;
                if(admin && !((ClientConnection)c).getLoggedInType().equals("Admin"))continue;
                if(!admin && ((ClientConnection)c).getLoggedInType().equals("Admin"))continue;
                ((ClientConnection)c).sendData(obj);
            }
        }
    }

}
