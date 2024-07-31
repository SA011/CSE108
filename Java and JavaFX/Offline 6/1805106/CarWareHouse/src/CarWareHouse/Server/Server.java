package CarWareHouse.Server;

import CarWareHouse.UserInfo;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Server {
    private static final int portNumber = 10278;

    public static void main(String[] args) throws IOException {
        DBHandler dbConnection = new DBHandler();

        List clientList = new ArrayList<ClientConnection>();

        HashMap<UserInfo,Integer> loginCounter = new HashMap<>();

        ServerSocket serverSocket  = new ServerSocket(portNumber);

        ClientUpdater cUpdate = new ClientUpdater(clientList);

        while (true){
            Socket sc = serverSocket.accept();

            clientList.add(new ClientConnection(sc,dbConnection,cUpdate,loginCounter));
        }
    }

}
