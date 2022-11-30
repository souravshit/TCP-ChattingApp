import java.net.*;
import javax.swing.*;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JFrame;
import java.awt.Font;
import java.awt.event.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import java.io.*;


public class Client extends JFrame{

    Socket socket;
    BufferedReader br;
    PrintWriter out;

    //Declare Components
    private JLabel heading = new JLabel("Client Area");
    private JTextArea messageArea = new JTextArea("");
    private JTextField messageInput = new JTextField();
    private Font font = new Font("Roboto", Font.PLAIN, 20);





    //constructor
    public Client(){
        try{

            System.out.println("Sending request to server");
            socket = new Socket("127.0.0.1", 8080);
            System.out.println("Connection Done");

            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());

            // createGUI();
            // handleEvents();

            
            startReading();
            startWriting();



        }catch (Exception e){
            e.printStackTrace();
        }
    }

    // private void handleEvents(){

    //     messageInput.addKeyListener(new KeyListener(){

    //     });
    // }





    // private void createGUI(){
    //     // GUI code.
    //     this.setTitle("Client Messanger[END]");
    //     this.setSize(500, 650);
    //     this.setLocationRelativeTo(null);
    //     this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    //     //coding for components
    //     heading.setFont(font);
    //     messageArea.setFont(font);
    //     messageInput.setFont(font);
    //     heading.setIcon(new ImageIcon("inchat.png"));
    //     heading.setHorizontalTextPosition(JTextField.CENTER);
    //     heading.setHorizontalAlignment(JTextField.CENTER);
    //     heading.setVerticalTextPosition(JTextField.BOTTOM);
    //     heading.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
    //     messageInput.setHorizontalAlignment(JTextField.CENTER);

        


    //     // set frame layout
    //     this.setLayout(new BorderLayout());

    //     //adding components to frame
    //     this.add(heading, BorderLayout.NORTH);
    //     this.add(messageArea, BorderLayout.CENTER);
    //     this.add(messageInput, BorderLayout.SOUTH);

    //     this.setVisible(true);
    // }


    public void startReading(){
        Runnable r1 = ()-> {
            System.out.println("Reader Started...");

            try{
            while(true){
                
                String msg = br.readLine();
                if(msg.equals("exit")){
                    System.out.println("Server terminated the chat:(");
                    socket.close();
                    break;
                }
                System.out.println("Server: "+ msg);
                
            }
            }catch (Exception e){
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
            }catch (Exception e){
                // e.printStackTrace();
                System.out.println("Connection is Cloesd");
            }

        };

        new Thread(r2).start();
    }





    public static void main(String[]args){
        System.out.println("OK");
        new Client();
    }
}