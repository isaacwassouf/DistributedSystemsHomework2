package ServerSide;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class ServerClient extends Thread {

    private Socket socket;
    private InetAddress ip;

    public ServerClient(Socket socket){
        this.socket=socket;
        ip= socket.getInetAddress();
        System.out.println(ip.toString()+" just connected");
    }


    @Override
    public void run(){
        try{
            while (!socket.isClosed()) {
                DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                String command = dataInputStream.readUTF();
                if (command.equals("PUT")){
                    put();
                }else if (command.equals("GET")){
                    get();
                }else if (command.equals("EXIT")){
                    System.out.println(ip.toString()+" just closed the connecting");
                    socket.close();

                }
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void put(){
        try {
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            String fileName=  dataInputStream.readUTF();
            String content=  dataInputStream.readUTF();
            FileWriter fileWriter = new FileWriter(fileName);
            fileWriter.write(content);
            fileWriter.flush();
            fileWriter.close();
            dataOutputStream.writeUTF("Uploading Complete");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void get(){
        try{
            DataInputStream dataInputStream= new DataInputStream(socket.getInputStream());
            DataOutputStream dataOutputStream=new DataOutputStream(socket.getOutputStream());
            String fileName= dataInputStream.readUTF();
            File file=  new File(fileName);
            if(file.exists()){
                dataOutputStream.writeUTF("exists");
                String content = readFile(fileName);
                dataOutputStream.writeUTF(content);
            }else{
                dataOutputStream.writeUTF("!exists");
                System.out.println("file does not exists .. closing connecting");
                socket.close();
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private String readFile(String fileName){
        String result="";
        try {
            FileReader fileReader = new FileReader(fileName);
            int letter = fileReader.read();
            while(letter!=-1){
                result+=(char)letter;
                letter= fileReader.read();
            }
            fileReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

}
