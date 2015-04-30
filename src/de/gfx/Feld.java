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
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.HashSet;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

/**
 *
 * @author Lucas Hartel
 */
public class Feld extends JPanel implements MouseMotionListener {
    
    private static final byte ROWS = 10+1; //Anzahl der Zeilen: 10 (+1 Beschriftung)
    private static final byte COLS = 10+1; //Anzahl der Spalten: 10 (+1 Beschriftung)
    
    public static final byte ID_VOID = 0;
    public static final byte ID_WATER = 1;
    public static final byte ID_SHIP = 2;
    public static final byte ID_HIT = 3;
    public static final byte ID_HIT_DONE = 4;
    
    public static Color GRID_COLOR = Color.BLACK;
    public static Color LABEL_COLOR = Color.BLACK;
    public static Color SHIP_COLOR = new Color(137, 219, 74);
    public static Color HIT_COLOR = Color.RED;
    public static Color WATER_COLOR = Color.BLUE;
    public static Color ACTIVE_FIELD_COLOR = Color.ORANGE;
    
    private final ArrayList<Schiff> schiffe;
    private final HashSet<Point> schiffpunkte;
    private boolean refresh;
    private final byte[][] feld;
    private boolean drawActiveField;
    private Image backgound;
    
    private int xOff;
    private int yOff;
    
    private final Point mousePos;
    
    @SuppressWarnings("")
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
        xOff = 1;
        yOff = 1;
        addMouseMotionListener(this);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        if(backgound != null) {
            g2d.drawImage(backgound, 0, 0, null);
        }
        drawGrid(g2d);
        drawLabel(g2d);
        drawContent(g2d);
        if(drawActiveField) {
            drawActiveField(g2d);
        }
    }
    
    public void setBackgroundImage(Image i) {
        this.backgound = i;
    }
    
    private void drawActiveField(Graphics2D g) {
        Point field = getActiveField();
        if(field == null) {
            return;
        }
        g.setStroke(new BasicStroke(2));
        g.setColor(ACTIVE_FIELD_COLOR);
        g.drawRect(xOff + (xOff * field.x) + 2, yOff + (yOff * field.y) + 2, xOff - 4, yOff - 4);
    }
    
    private void drawContent(Graphics2D g) {
        for(int y = 0; y < feld.length; y++) {
            for(int x = 0; x < feld[y].length; x++) {
                switch(feld[y][x]) {
                    case ID_WATER: {
                        g.setColor(WATER_COLOR);
                        g.drawOval(xOff + (xOff * x) + xOff/4, yOff + (yOff * y) + yOff/4, xOff/2, yOff/2);
                        break;
                    }
                    case ID_HIT: {
                        g.setStroke(new BasicStroke(3));
                        g.setColor(HIT_COLOR);
                        //Rotes X im Kästchen zeichnen
                        g.drawLine(xOff + (xOff * x) + xOff/4, yOff + (yOff * y) + yOff/4, (int)(xOff + (xOff * x) + xOff * 0.75), (int)(yOff + (yOff * y) + yOff * 0.75));
                        g.drawLine((int)(xOff + (xOff * x) + xOff * 0.75), yOff + (yOff * y) + yOff/4, xOff + (xOff * x) + xOff/4, (int)(yOff + (yOff * y) + yOff * 0.75));
                        break;
                    }
                    case ID_SHIP: {
                        drawSchiff(x, y, getSchiffByCoords(new Point(x, y)), g);
                        break;
                    }
                    case  ID_HIT_DONE: {
                        g.setStroke(new BasicStroke(3));
                        g.setColor(HIT_COLOR);
                        //Rotes X im Kästchen zeichnen
                        g.drawLine(xOff + (xOff * x) + xOff/4, yOff + (yOff * y) + yOff/4, (int)(xOff + (xOff * x) + xOff * 0.75), (int)(yOff + (yOff * y) + yOff * 0.75));
                        g.drawLine((int)(xOff + (xOff * x) + xOff * 0.75), yOff + (yOff * y) + yOff/4, xOff + (xOff * x) + xOff/4, (int)(yOff + (yOff * y) + yOff * 0.75));
                        
                        if(getSchiffByCoords(new Point(x, y)).getType() == Schiff.TYPE_LEFT_RIGHT) {
                            //Waagerechter Strich
                            g.drawLine(xOff + (xOff * x), yOff + (yOff * y) + yOff/2, xOff + (xOff * x) + xOff, yOff + (yOff * y) + yOff/2);
                        } else {
                            //Senkrechter Strich
                            g.drawLine(xOff + (xOff * x) + xOff/2, yOff + (yOff * y), xOff + (xOff * x) + xOff/2, yOff + (yOff * y) + yOff);
                        }
                        break;
                    }
                }
            }
        }
    }
    
    private void drawSchiff(int x, int y, Schiff f, Graphics2D g) {
        if(f == null) {
            return;
        }
        int seg = f.getSegment(new Point(x, y));
        byte type = f.getType();
        
        g.setStroke(new BasicStroke(5));
        g.setColor(SHIP_COLOR);
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
        g.setFont(new Font("Arial", Font.BOLD, (int)Math.min(xOff*0.75, yOff*0.75)));
        g.setColor(LABEL_COLOR);
        for(int x = 0; x < COLS-1; x++) {
            g.drawString((x + 1)+"", (xOff/2) + (xOff * (x+1)) - g.getFont().getSize()/2, yOff/2 + g.getFont().getSize() / 2);
        }
        for(int y = 0; y < ROWS-1; y++) {
            g.drawString((char)(65 + y)+"", xOff/2 - g.getFont().getSize() / 2, (yOff/2) + (yOff * (y+1)) + g.getFont().getSize()/2);
        }
    }
    
    private void drawGrid(Graphics2D g) {
        g.setStroke(new BasicStroke(3));
        g.setColor(GRID_COLOR);
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
    
    public Schiff getSchiffByCoords(Point p) {
        for(Schiff f : schiffe) {
            if(f.getSegment(p) != -1) {
                return f;
            }
        }
        return null;
    }
    
    public byte getIDAtCurrentField() {
        Point field = getActiveField();
        if(field != null) {
            return getIDAtField(field);
        }
        return -1;
    }
    
    public byte getIDAtField(Point p) {
        return feld[p.y][p.x];
    }
    
    public void setIDAtField(Point field, byte id) {
        feld[field.y][field.x] = id;
    }
    
    private void calcOffset() {
        Dimension size = getSize();
        xOff = size.width / COLS; //Spaltengröße
        yOff = size.height / ROWS; //Zeilengröße
    }
    
    public Point getActiveField() {
        int x = (mousePos.x - xOff) / xOff;
        int y = (mousePos.y - yOff) / yOff;
        if(x >= 0 && x <= 10 && y >= 0 && y <= 10) {
            return new Point(x, y);
        }
        return null;
    }
    
    public boolean allSchiffDestroyed() {
        return schiffe.stream().noneMatch((f) -> (!f.isDestroyed()));
    }
    
    public final void stopRefresh() {
        refresh = false;
    }
    
    public void addSchiff(Schiff f, boolean toField) {
        if(!f.isVerified()) {
            throw new IllegalStateException("Schiff ist nicht validiert! "+f);
        }
        for(Point p : f.getCoords()) {
            if(schiffpunkte.contains(p)) {
                throw new IllegalArgumentException("Schiff scheidet sich mit einm anderen! "+f);
            }
        }
        if(toField) {
            for(Point p : f.getCoords()) {
                schiffpunkte.add(p);
                feld[p.y][p.x] = ID_SHIP;
            }
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
