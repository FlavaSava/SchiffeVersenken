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
            System.out.println("Connecting to port " +port);
            server = new ServerSocket(port);
        } catch (IOException ex) {
            System.out.println("Unable to bind server on Port "+port+"!");
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
                System.out.println("An error occured during connectiontry");
            }
            client1 = new CThread(tmp);
            try {
                Thread.sleep(100);
            } catch(InterruptedException e) {
                //...
            }
            client1.sendMessage("msg|Warte auf zweiten Spieler");
            System.out.println("Successfull!");
        }
        System.out.println("Looking for Player 2/2");
        while(client2 == null) {
            try {
                tmp = server.accept();
            } catch (IOException ex) {
                System.out.println("An error occured during connectiontry");
            }
            client2 = new CThread(tmp);
            try {
                Thread.sleep(100);
            } catch(InterruptedException e) {
                
            }
            client2.sendMessage("msg|Du bist der zweite Spieler");
            System.out.println("Successfull!");
        }
        client1.start();
        client2.start();
        client1.sendMessage("msg|Spiel startet");
        client2.sendMessage("msg|Spiel startet");
        client1.sendMessage("dran");
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
                disconnect();
                exit(false);
                break;
            }
            case "res": {
                getOpposite(sender).sendMessage(msg);
                if(Byte.parseByte(split[1]) == Feld.ID_HIT || Byte.parseByte(split[1]) == Feld.ID_HIT_DONE) {
                    getOpposite(sender).sendMessage("dran");
                    sender.sendMessage("nodran");
                } else {
                    getOpposite(sender).sendMessage("nodran");
                    sender.sendMessage("dran");
                }
                break;
            }
            default: {
                System.out.println("Ungültige nachricht: "+msg);
            }
        }
    }
    
    private void disconnect() {
        client1.disconnect();
        client2.disconnect();
    }
    
    private void exit(boolean force) {
        if(force) {
            System.out.println("Problem detected. Server is shutting down");
        } else {
            System.out.println("Game ended");
        }
        System.exit(0);
    }
    
    private CThread getOpposite(CThread c) {
        if(c == client1) {
            return client2;
        } else {
            return client1;
        }
    }
    
    private class CThread extends Thread {
        
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
                System.out.println("Unable do open stream to client");
            }
        }
        
        @Override
        public void run() {
            isRunning = true;
            while(isRunning) {
                String msg = receiveMessage();
                if(msg != null) {
                    handleMessage(msg, this);
                }
            }
        }
        
        public void disconnect() {
            isRunning = false;
            try {
                in.close();
                out.close();
                client.close();
            } catch (IOException ex) {
                System.out.println("Disconnect was not successfull");
            }
        }
        
        public String receiveMessage() {
            String msg = null;
            try {
                msg = (String)in.readObject();
            } catch (IOException | ClassNotFoundException ex) {
                if(isRunning) {
                    SchiffServer.this.disconnect();
                    exit(true);
                }
                isRunning = false;
            }
            return msg;
        }
        
        public void sendMessage(String msg) {
            try {
                out.writeObject(msg);
                out.flush();
            } catch (IOException ex) {
                if(isRunning) {
                    SchiffServer.this.disconnect();
                    exit(true);
                }
                isRunning = false;
            }
        }
    }


}
