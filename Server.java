import java.net.*;
import java.io.*;
public class Server{



    ServerSocket server;
    Socket socket;
    BufferedReader br;
    PrintWriter out;



    // Constructor
    public Server(){
        try {
            server = new ServerSocket(8080);
            System.out.println("Server is ready to accept connection!");
            System.out.println("Waiting...");
            socket = server.accept();

            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());

            startReading();
            startWriting();


        }catch (Exception e){
            e.printStackTrace();
        }
    
    }

    public void startReading(){
        Runnable r1 = ()-> {
            System.out.println("Reader Started...");

            try{
            while(true){
                
                String msg = br.readLine();
                if(msg.equals("exit")){
                    System.out.println("Clinet terminated the chat:(");
                    socket.close();
                    break;
                }
                System.out.println("Tokyo: "+ msg);
            }
            } catch (Exception e){
                // e.printStackTrace();
                System.out.println("Connection is Cloesd");

            }
            
        };
        
        new Thread(r1).start();
    }

    public void startWriting(){
        Runnable r2 = ()->{
            System.out.println("Writer started..");
            try{
            while(!socket.isClosed()){
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                    String content = br1.readLine();

                    out.println(content);
                    out.flush();
                   
                    if(content.equals("exit")){
                        socket.close();
                        break;
                    }

            }
                System.out.println("Connection is Cloesd");


            } catch (Exception e){
                e.printStackTrace();

            }
        };

        new Thread(r2).start();
    }








    public static void main(String[]agrs){
        System.out.println("Server is running Not Possible to stop the server.....");
        new Server();
    }
}