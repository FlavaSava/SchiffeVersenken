/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package server;

import de.gfx.Feld;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;


/**
 *
 * @author Lucas Hartel
 */
public class SchiffServer extends Thread {
    
    private ServerSocket server;
    private CThread client1;
    private CThread client2;
    private boolean exit;
    
    public SchiffServer(int port) {
        try {
            System.out.println("Connecting to port" +port);
            server = new ServerSocket(port);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    @Override
    public void run(){
        Socket tmp = null;
        System.out.println("Looking for Player 1/2");
        while(client1 == null) {
            try {
                tmp = server.accept();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            client1 = new CThread(tmp);
            System.out.println("Successfull!");
        }
        System.out.println("Looking for Player 2/2");
        while(client2 == null) {
            try {
                tmp = server.accept();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            client2 = new CThread(tmp);
            System.out.println("Successfull");
        }
        System.out.println("Sending start signal");
        client1.start();
        client2.start();
        client1.sendMessage("start");
        client2.sendMessage("start");
        client1.sendMessage("dran");
        
        while(!exit) {
            try {
                Thread.sleep(1500);
            } catch(InterruptedException e) {
                
            }
        }
    }
    
    private void handleMessage(String msg, CThread sender) {
        System.out.println("empfange message: "+msg);
        String[] split = msg.split("\\|");
        switch(split[0]) {
            case "shoot": {
                System.out.println("leite shoot weiter");
                getOpposite(sender).sendMessage(msg);
                break;
            }
            case "loose": {
                getOpposite(sender).sendMessage("win");
                getOpposite(sender).sendMessage("exit");
                sender.sendMessage("exit");
            }
            case "res": {
                getOpposite(sender).sendMessage(msg);
                if(Byte.parseByte(split[1]) == Feld.ID_HIT) {
                    getOpposite(sender).sendMessage("dran");
                    sender.sendMessage("nodran");
                } else {
                    getOpposite(sender).sendMessage("nodran");
                    sender.sendMessage("dran");
                }
            }
            default: {
                System.out.println("Ungültige nachricht: "+msg);
            }
        }
    }
    
    private CThread getOpposite(CThread c) {
        if(c == client1) {
            return client2;
        } else {
            return client1;
        }
    }
    
    private class CThread extends Thread {
        
        private boolean dran;
        private Socket client;
        private ObjectOutputStream out;
        private ObjectInputStream in;
        private boolean isRunning;
        
        public CThread(Socket client) {
            this.client = client;
            try {
                this.out = new ObjectOutputStream(client.getOutputStream());
                this.in = new ObjectInputStream(client.getInputStream());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            dran = false;
        }
        
        @Override
        public void run() {
            isRunning = true;
            while(isRunning) {
                Message m = receiveMessage();
                if(m != null) {
                    handleMessage(m.msg, this);
                }
            }
        }
        
        public Message receiveMessage() {
            Message m = null;
            try {
                m = (Message)in.readObject();
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            }
            return m;
        }
        
        public void sendMessage(Message m) {
            try {
                out.writeObject(m);
                out.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        
        public void sendMessage(String msg) {
            sendMessage(new Message(msg));
        }
        
    }


}
