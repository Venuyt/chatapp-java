 import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;

class user1 implements ActionListener, Runnable {

    JTextField textField;
    JTextArea textArea;
    Socket socket;
    DataInputStream dis;
    DataOutputStream dos;
    Thread thread;

     user1() {
        
        JFrame frame = new JFrame("User1");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

       
        textField = new JTextField(20);
        textField.setPreferredSize(new Dimension(200, 30));

       
        textArea = new JTextArea(5, 20);
        textArea.setPreferredSize(new Dimension(250, 100));
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        
        JButton button = new JButton("Send");
        button.setPreferredSize(new Dimension(100, 40));
        button.addActionListener(this);

       
        try {
            socket = new Socket("localhost", 12000);
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }

     
        frame.add(textField);
        frame.add(scrollPane);
        frame.add(button);

       
        thread = new Thread(this);
        thread.start();
        

        frame.setSize(350, 250);
        frame.setVisible(true);
    }

   
    public void actionPerformed(ActionEvent e) {
        String msg = textField.getText();
        textArea.append("User1: " + msg + "\n");
                textField.setText("");
       
            try {
                dos.writeUTF(msg);
                dos.flush();
                
            } catch (IOException ex) {
                
            }
        }
    

   
    public void run() {
        while (true) {
            try {
                String msg = dis.readUTF();
                textArea.append("User2: " + msg + "\n");
            } catch (IOException e) {
               
            }
        }
    }

    public static void main(String[] args) {
        new user1();
    }
}
