package chatapp;
import java.awt.*;
import java.awt.event.*;
import java.net.Socket;
import java.io.DataInputStream;
import java.io.DataOutputStream;
public class Client extends Frame implements Runnable,ActionListener{
    TextField textField;
    TextArea textArea;
    Button send;

    Socket socket;
    DataInputStream datain;
    DataOutputStream dataout;
    Thread chat;
    Client(){
        textField=new TextField();
        textArea=new TextArea();
        send=new Button("send");
        send.addActionListener(this);

        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    sendMessage(); // Call common message sending logic
                }
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                System.exit(0); // Terminate the application
            }
        });

        try{

            socket=new Socket("localhost",21000);
            datain=new DataInputStream(socket.getInputStream());
            dataout=new DataOutputStream(socket.getOutputStream());
        }
        catch(Exception e){
            
        }
        add(textField);
        add(textArea);
        add(send);
        
        chat=new Thread(this);
        chat.setDaemon(true);
        chat.start();

        setSize(600,600);
        setTitle("Client");
        textField.setBounds(50, 50, 500, 30);
        textArea.setBounds(50, 200, 500, 200);
        send.setBounds(150, 500,80 , 50);
        setLayout(null);
        setVisible(true);
        
    }
    public static void main(String[] args) {
        new Client();
    }
    public void run(){
        while(true){
            try{
                String msg =datain.readUTF();
                textArea.append("Server: "+msg+"\n");
            }catch(Exception e){

            }
        }
    }
    @Override

    public void actionPerformed(ActionEvent e){
        sendMessage();
        
    }
    public void sendMessage(){
        String msg=textField.getText();
        textArea.append("Client: "+msg+"\n");
        textField.setText("");
        try{
            dataout.writeUTF(msg);
            dataout.flush();
        }catch(Exception r){

        }
    }
    

}
