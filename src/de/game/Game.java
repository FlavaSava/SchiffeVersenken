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
import de.schiffe.SchiffGenerator;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.JOptionPane;
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
    private ZugThread zThread;
    private Point lastShot;
    private boolean listening;
    
    @SuppressWarnings("")
    public Game() {
        mouse = new boolean[3];
        window = new Window();
        window.setConnected(false);
        window.getActionField().addMouseListener(this);
        ip = JOptionPane.showInputDialog(window, "IP-Adresse des Servers", null);
        try {
            port = Integer.parseInt(JOptionPane.showInputDialog(window, "Port des Servers", null));
        } catch(NumberFormatException e) {
            System.exit(1);
        }
        zThread = new ZugThread();
    }
    
    public void start() {
        SchiffPlatzierer dlg = new SchiffPlatzierer(window);
        Window.console.println("Schiffsplatzierung..");
        dlg.setVisible(true);
        Schiff[] schiffe = dlg.getSchiffe();
        if(schiffe == null) {
            System.exit(1);
        }
        for(Schiff f : schiffe) {
            window.getViewField().addSchiff(f, true);
        }
        Window.console.print("Verbindungsversuch zum Server "+ip+":"+port+" : ");
        try {
            socket = new Socket(ip, port);
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
            window.setConnected(true);
            Window.console.println("<font color=green>Erfolg!</font>");
        } catch (IOException ex) {
            Window.console.println("<font color=red>Fehlgeschlagen!</font>");
        }
        startListening();
    }
    
    private void clickAction() {
        if(window.getActionField().getIDAtCurrentField() == Feld.ID_VOID && zThread.isDran) {
            Point p = window.getActionField().getActiveField();
            lastShot = p;
            Window.console.print("Schuss auf "+(char)(p.y + 65)+""+(p.x+1)+": ");
            sendMessageToServer("shoot|"+p.x+""+p.y);
            zThread.isDran = false;
        }
    }
    
    private byte hitAt(Point p) {
        byte id = window.getViewField().getIDAtField(p);
        if(id == Feld.ID_VOID) {
            window.getViewField().setIDAtField(p, Feld.ID_WATER);
            Window.console.println("Gegner schießt ins <font color=blue><b>Wasser</b></font>!");
            return Feld.ID_WATER;
        } else {
            Schiff f = window.getViewField().getSchiffByCoords(p);
            f.hit(p.x, p.y);
            window.getViewField().setIDAtField(p, Feld.ID_HIT);
            if(f.isDestroyed()) {
                for(Point po : f.getCoords()) {
                    window.getViewField().setIDAtField(po, Feld.ID_HIT_DONE);
                }
                Window.console.println("Gegner <font color=red><b>versenkt</b></font> dein "+f);
                return Feld.ID_HIT_DONE;
            } else {
                Window.console.println("Gegner <font color=red><b>trifft</b></font> dein "+f);
            }
            return Feld.ID_HIT;
        }
    }
    
    private void handleMessage(String msg) {
        String[] split = msg.split("\\|");
        switch(split[0]) {
            case "dran": {
                Window.console.println("Du bist dran");
                zThread = new ZugThread();
                zThread.isDran = true;
                zThread.start();
                break;
            }
            case "nodran": {
                Window.console.println("Gegner ist dran");
                zThread.isDran = false;
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
                    if(window.getViewField().allSchiffDestroyed()) {
                        sendMessageToServer("loose");
                    }
                } else {
                    sendMessageToServer("res|"+id);
                }
                break;
            }
            case "res": {
                byte id = Byte.parseByte(split[1]);
                if(id != Feld.ID_HIT_DONE) {
                    window.getActionField().setIDAtField(lastShot, id);
                    if(id == Feld.ID_HIT) {
                        Window.console.println("<font color=red>Treffer!</font>");
                    } else {
                        Window.console.println("<font color=blue>Wasser..</font>");
                    }
                } else {
                    Point[] tmp = new Point[split.length - 2];
                    window.getActionField().stopRefresh();
                    for(int x = 2; x < split.length; x++) {
                        Point point = new Point(Integer.parseInt(split[x].split(",")[0]), Integer.parseInt(split[x].split(",")[1]));
                        tmp[x-2] = point;
                        window.getActionField().setIDAtField(point, Feld.ID_HIT_DONE);
                    }
                    window.getActionField().addSchiff(SchiffGenerator.generateSchiff(tmp), false);
                    window.getActionField().startRefresh();
                    Window.console.println("<font color=red>Treffer versenkt!</font>");
                }
                break;
            }
            case "win": {
                Window.console.println("Du hast gewonnen!");
                break;
            }
            case "exit": {
                zThread.isDran = false;
                window.getActionField().stopRefresh();
                window.getViewField().stopRefresh();
                disconnect();
                Window.console.print("Spiel schließt automatisch");
                for(int x = 0; x < 5; x++) {
                    try {
                        Thread.sleep(1000);
                    } catch(InterruptedException e){
                        
                    }
                    Window.console.print(". ");
                }
                System.exit(0);
                break;
            }
            case "msg": {
                Window.console.println("<b>Nachricht vom Server:</b> "+split[1]);
                break;
            }
            default: {
                System.out.println("Ungültige Nachricht: "+msg);
            }
        }
    }
    
    private void disconnect() {
        try {
            out.close();
            in.close();
            socket.close();
        } catch(IOException e) {
            //...
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //...
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
        //...
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //...
    }
    
    private void sendMessageToServer(String msg) {
        try {
            out.writeObject(msg);
            out.flush();
        } catch (IOException ex) {
            window.setConnected(false);
            listening = false;
        }
    }
    
    private String receiveMessage() {
        String msg = null;
        try {
            msg = (String)in.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            window.setConnected(false);
            listening = false;
        }
        return msg;
    }
    
    private void startListening() {
        listening = true;
        new Thread() {
            @Override
            public void run() {
                while(listening) {
                    String msg = receiveMessage();
                    if(msg != null) {
                        handleMessage(msg);
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
    
    private class ZugThread extends Thread {
        
        private boolean isDran;
        
        @Override
        public void run() {
            while(isDran) {
                if(mouse[0]) {
                    clickAction();
                }
                try {
                    Thread.sleep(10);
                } catch(InterruptedException e) {
                    // ...
                }
                if(!isDran) {
                    break;
                }
            }
        }
    }
}
