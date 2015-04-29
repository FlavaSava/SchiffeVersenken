/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.gfx;

import de.schiffe.Schiff;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

/**
 *
 * @author Lucas Hartel
 */
public class Feld extends JPanel implements MouseMotionListener {
    
    private static final byte ROWS = 10+1; //Anzahl der Zeilen: 10 (+1 Beschriftung)
    private static final byte COLS = 10+1; //Anzahl der Spalten: 10 (+1Beschriftung)
    
    public static final byte ID_VOID = 0;
    public static final byte ID_WATER = 1;
    public static final byte ID_SHIP = 2;
    public static final byte ID_HIT = 3;
    
    private final ArrayList<Schiff> schiffe;
    private final HashSet<Point> schiffpunkte;
    private boolean refresh;
    private final byte[][] feld;
    private boolean drawActiveField;
    
    private int xOff;
    private int yOff;
    
    private Point mousePos;
    
    public Feld(Dimension size) {
        schiffe = new ArrayList<>();
        schiffpunkte = new HashSet<>();
        feld = new byte[10][10];
        mousePos = new Point(0,0);
        stopRefresh();
        drawActiveField = false;
        setBorder(new LineBorder(Color.BLACK, 2));
        setPreferredSize(size);
        setSize(size);
        addMouseMotionListener(this);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        drawGrid(g2d);
        drawLabel(g2d);
        drawContent(g2d);
        if(drawActiveField) {
            drawActiveField(g2d);
        }
    }
    
    private void drawActiveField(Graphics2D g) {
        Point relMouse = new Point(mousePos.x - xOff, mousePos.y - yOff);
        int x = relMouse.x / xOff;
        int y = relMouse.y / yOff;
        g.setStroke(new BasicStroke(2));
        g.setColor(Color.ORANGE);
        if(relMouse.x >= 0 && relMouse.x <= getSize().width && relMouse.y >= 0 && relMouse.y <= getSize().height) {
            g.drawRect(xOff + (xOff * x), yOff + (yOff * y), xOff - 2, yOff - 2);
        }
    }
    
    private void drawContent(Graphics2D g) {
        for(int y = 0; y < feld.length; y++) {
            for(int x = 0; x < feld[y].length; x++) {
                switch(feld[y][x]) {
                    case ID_WATER: {
                        g.setColor(Color.BLUE);
                        g.drawOval(xOff + (xOff * x) + xOff/4, yOff + (yOff * y) + yOff/4, xOff/2, yOff/2);
                        break;
                    }
                    case ID_HIT: {
                        g.setStroke(new BasicStroke(3));
                        g.setColor(Color.RED);
                        //Rotes X im Kästchen zeichnen
                        g.drawLine(xOff + (xOff * x) + xOff/4, yOff + (yOff * y) + yOff/4, (int)(xOff + (xOff * x) + xOff * 0.75), (int)(yOff + (yOff * y) + yOff * 0.75));
                        g.drawLine((int)(xOff + (xOff * x) + xOff * 0.75), yOff + (yOff * y) + yOff/4, xOff + (xOff * x) + xOff/4, (int)(yOff + (yOff * y) + yOff * 0.75));
                        break;
                    }
                    case ID_SHIP: {
                        drawSchiff(x, y, getSchiffByCoords(new Point(x, y)), g);
                        break;
                    }
                }
            }
        }
    }
    
    private void drawSchiff(int x, int y, Schiff f, Graphics2D g) {
        if(f == null) return;
        int seg = f.getSegment(new Point(x, y));
        byte type = f.getType();
        
        g.setStroke(new BasicStroke(3));
        if(type == Schiff.TYPE_LEFT_RIGHT) {
            if(seg == 1) {
                //AnfangsSpitze zeichnen
                g.drawLine(xOff + (xOff * x), yOff + (yOff * y) + yOff/2, xOff + (xOff * x) + xOff, yOff + (yOff * y) + 8);
                g.drawLine(xOff + (xOff * x), yOff + (yOff * y) + yOff/2, xOff + (xOff * x) + xOff, yOff + (yOff * y) + yOff - 8);
            } else if(seg == f.getSize()) {
                //EndSpitze zeichnen
                g.drawLine(xOff + (xOff * x) + xOff, yOff + (yOff * y) + yOff/2, xOff + (xOff * x), yOff + (yOff * y) + 8);
                g.drawLine(xOff + (xOff * x) + xOff, yOff + (yOff * y) + yOff/2, xOff + (xOff * x), yOff + (yOff * y) + yOff - 8);
            } else {
                //Mitte zeichnen
                g.drawLine(xOff + (xOff * x), yOff + (yOff * y) + 8, xOff + (xOff * x) + xOff, yOff + (yOff * y) + 8);
                g.drawLine(xOff + (xOff * x), yOff + (yOff * y) + yOff - 8, xOff + (xOff * x) + xOff, yOff + (yOff * y) + yOff - 8);
            }
        } else {
            if(seg == 1) {
                //AnfangsSpitze zeichnen
                g.drawLine(xOff + (xOff * x) + xOff/2, yOff + (yOff * y), xOff + (xOff * x) + xOff - 8, yOff + (yOff * y) + yOff);
                g.drawLine(xOff + (xOff * x) + xOff/2, yOff + (yOff * y), xOff + (xOff * x) + 8, yOff + (yOff * y) + yOff);
            } else if(seg == f.getSize()) {
                //EndSpitze zeichnen
                g.drawLine(xOff + (xOff * x) + xOff/2, yOff + (yOff * y) + yOff, xOff + (xOff * x) + xOff - 8, yOff + (yOff * y));
                g.drawLine(xOff + (xOff * x) + xOff/2, yOff + (yOff * y) + yOff, xOff + (xOff * x) + 8, yOff + (yOff * y));
            } else {
                //Mitte zeichnen
                g.drawLine(xOff + (xOff * x) + 8, yOff + (yOff * y), xOff + (xOff * x) + 8, yOff + (yOff * y) + yOff);
                g.drawLine(xOff + (xOff * x) + xOff - 8, yOff + (yOff * y), xOff + (xOff * x) + xOff - 8, yOff + (yOff * y) + yOff);
            }
        }
    }
    
    private void drawLabel(Graphics2D g) {
        g.setFont(new Font("Arial", Font.BOLD, (int)Math.min(xOff/2, yOff/2)));
        for(int x = 0; x < COLS-1; x++) {
            g.drawString((x + 1)+"", (xOff/2) + (xOff * (x+1)) - g.getFont().getSize()/2, yOff/2 + g.getFont().getSize() / 2);
        }
        for(int y = 0; y < ROWS-1; y++) {
            g.drawString((char)(65 + y)+"", xOff/2 - g.getFont().getSize() / 2, (yOff/2) + (yOff * (y+1)) + g.getFont().getSize()/2);
        }
    }
    
    private void drawGrid(Graphics2D g) {
        g.setStroke(new BasicStroke(3));
        Dimension dim = this.getSize();
        for(int x = 0; x < COLS; x++) {
            g.drawLine(xOff + (xOff * x), 0, xOff + (xOff * x), dim.height);
        }
        for(int y = 0; y < ROWS; y++) {
            g.drawLine(0, yOff + (yOff * y), dim.width, yOff + (yOff * y));
        }
    }
    
    @SuppressWarnings("")
    public final void startRefresh() {
        if(!refresh) {
            refresh = true;
            new Thread() {
                @Override
                public void run() {
                    while(refresh) {
                        calcOffset();
                        Feld.this.repaint();
                        try {
                            Thread.sleep(100);
                        } catch(InterruptedException e) {
                            // ...
                        }
                    }
                }
            }.start();
        }
    }
    
    private Schiff getSchiffByCoords(Point p) {
        for(Schiff f : schiffe) {
            if(f.getSegment(p) != -1) {
                return f;
            }
        }
        return null;
    }
    
    private void calcOffset() {
        Dimension size = getSize();
        xOff = size.width / COLS; //Spaltengröße
        yOff = size.height / ROWS; //Zeilengröße
    }
    
    public final void stopRefresh() {
        refresh = false;
    }
    
    public void addSchiff(Schiff f) {
        if(!f.isVerified()) {
            throw new IllegalStateException("Schiff ist nicht validiert! "+f);
        }
        for(Point p : f.getCoords()) {
            if(schiffpunkte.contains(p)) {
                throw new IllegalArgumentException("Schiff scheidet sich mit einm anderen! "+f);
            }
        }
        for(Point p : f.getCoords()) {
            schiffpunkte.add(p);
            feld[p.y][p.x] = ID_SHIP;
        }
        schiffe.add(f);
    }
    
    public void drawActivField(boolean draw) {
        this.drawActiveField = draw;
    }
    
    public Point getMousePos() {
        return new Point(mousePos);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mousePos.x = e.getX();
        mousePos.y = e.getY();
    }

}
