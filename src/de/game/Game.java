/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.game;

import de.gfx.Feld;
import de.gfx.SchiffPlatzierer;
import de.gfx.Window;
import de.schiffe.Schiff;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.JOptionPane;
import server.Message;
import server.SchiffServer;

/**
 *
 * @author Lucas Hartel
 */
public class Game implements MouseListener {
    
    private final boolean[] mouse;
    private final Window window;
    private Socket socket;
    private String ip;
    private int port;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private boolean isDran;
    private Point lastShot;
    
    public Game() {
        mouse = new boolean[3];
        window = new Window();
        window.getActionField().addMouseListener(this);
        ip = JOptionPane.showInputDialog(window, "IP-Adresse des Servers", null);
        port = Integer.parseInt(JOptionPane.showInputDialog(window, "Port des Servers", null));
        isDran = false;
    }
    
    public void start() {
        SchiffPlatzierer dlg = new SchiffPlatzierer(window);
        dlg.setVisible(true);
        Schiff[] schiffe = dlg.getSchiffe();
        if(schiffe == null) {
            System.exit(1);
        }
        for(Schiff f : schiffe) {
            window.getViewField().addSchiff(f);
        }
        try {
            socket = new Socket(ip, port);
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        startListening();
    }
    
    private void doZug() {
        new Thread() {
            @Override
            public void run() {
                Window.console.println("Du bist dran");
                while(isDran) {
                    if(mouse[0]) {
                        clickAction();
                    }
                    try {
                        Thread.sleep(10);
                    } catch(InterruptedException e) {
                        // ...
                    }
                }
            }
        }.start();
    }
    
    private void clickAction() {
        if(window.getActionField().getIDAtCurrentField() == Feld.ID_VOID && isDran) {
            Point p = window.getActionField().getActiveField();
            lastShot = p;
            System.out.println("sendMessage shoot");
            sendMessageToServer("shoot|"+p.x+""+p.y);
            isDran = false;
        }
    }
    
    private byte hitAt(Point p) {
        byte id = window.getViewField().getIDAtField(p);
        if(id == Feld.ID_VOID) {
            window.getViewField().setIDAtField(p, Feld.ID_WATER);
            return Feld.ID_WATER;
        } else {
            window.getViewField().getSchiffByCoords(p).hit(p.x, p.y);
            window.getViewField().setIDAtField(p, Feld.ID_HIT);
            if(window.getViewField().getSchiffByCoords(p).isDestroyed()) {
                for(Point po : window.getViewField().getSchiffByCoords(p).getCoords()) {
                    window.getViewField().setIDAtField(po, Feld.ID_HIT_DONE);
                }
                return Feld.ID_HIT_DONE;
            }
            return Feld.ID_HIT;
        }
    }
    
    private void handleMessage(String msg) {
        String[] split = msg.split("\\|");
        switch(split[0]) {
            case "dran": {
                isDran = true;
                doZug();
                break;
            }
            case "nodran": {
                Window.console.println("Gegner ist dran");
                isDran = false;
                break;
            }
            case "shoot": {
                byte id = hitAt(new Point(Integer.parseInt(split[1].charAt(0)+""), Integer.parseInt(split[1].charAt(1)+"")));
                if(id == Feld.ID_HIT_DONE) {
                    String co = "";
                    for(Point p : window.getViewField().getSchiffByCoords(new Point(Integer.parseInt(split[1].charAt(0)+""), Integer.parseInt(split[1].charAt(1)+""))).getCoords()) {
                        co += p.x+","+p.y+"|";
                    }
                    sendMessageToServer("res|"+id+"|"+co.substring(0, co.length()-1));
                } else {
                    sendMessageToServer("res|"+id);
                }
                break;
            }
            case "res": {
                byte id = Byte.parseByte(split[1]);
                if(id != Feld.ID_HIT_DONE) {
                    window.getActionField().setIDAtField(lastShot, id);
                } else {
                    for(int x = 2; x < split.length; x++) {
                        Point point = new Point(Integer.parseInt(split[x].split(",")[0]), Integer.parseInt(split[x].split(",")[1]));
                        window.getActionField().setIDAtField(point, Feld.ID_HIT_DONE);
                    }
                }
                break;
            }
            case "win": {
                //Gewinn benachrichtigung
                break;
            }
            case "exit": {
                System.exit(0);
                break;
            }
            default: {
                System.out.println("Ungültige Nachricht: "+msg);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mouse[e.getButton()-1] = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mouse[e.getButton()-1] = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
    
    private void sendMessageToServer(String msg) {
        try {
            out.writeObject(new Message(msg));
            out.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    private Message receiveMessage() {
        Message m = null;
        try {
            m = (Message) in.readObject();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return m;
    }
    
    private void startListening() {
        new Thread() {
            @Override
            public void run() {
                while(true) {
                    Message m = receiveMessage();
                    if(m != null) {
                        handleMessage(m.msg);
                    }
                }
            }
        }.start();
    }
    
    public static void main(String[] args) {
        if(args.length == 2) {
            if(args[0].equals("server")) {
                new SchiffServer(Integer.parseInt(args[1])).start();
            }
        } else {
            Game game = new Game();
            game.start();
        }
    }
}
