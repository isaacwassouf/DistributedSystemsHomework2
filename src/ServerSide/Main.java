package ServerSide;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    
    public static void main(String[] args) {

        try{
            System.out.println();
            ServerSocket serverSocket=  new ServerSocket(1234);
            while(true){
                Socket socket = serverSocket.accept();
                ServerClient serverClient =new ServerClient(socket);
                serverClient.start();

            }
        } catch(Exception e){
            e.printStackTrace();
        }


    }
}
