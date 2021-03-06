/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.gfx;

import de.schiffe.Schiff;
import de.schiffe.SchiffGenerator;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.JDialog;
import javax.swing.JFrame;

/**
 *
 * @author lucash
 */
public class SchiffPlatzierer extends JDialog implements ActionListener {
    
    private final Schiff[] schiffe;
    private boolean erfolg;

    @SuppressWarnings("")
    public SchiffPlatzierer(JFrame parent) {
        super(parent, true);
        initComponents();
        schiffe = new Schiff[5];
        setLocationRelativeTo(parent);
        ok.addActionListener(this);
        cancle.addActionListener(this);
        standard.addActionListener(this);
        zufall.addActionListener(this);
    }
    
    private void check() {
        erfolg = true;
        //Ruderboot
        Point[] punkte = getPunkte(coordsRuderboot.getText());
        if(punkte == null) {
            erfolg = false;
            coordsRuderboot.setForeground(Color.RED);
        } else {
            punkte = getIntervall(punkte[0], punkte[1]);
            if(punkte == null || punkte.length != 2) {
                erfolg = false;
                coordsRuderboot.setForeground(Color.RED);
            } else {
                schiffe[0] = SchiffGenerator.generateSchiff(punkte);
            }
        }
        
        //Kreuzer
        punkte = getPunkte(coordsKreuzer.getText());
        if(punkte == null) {
            erfolg = false;
            coordsKreuzer.setForeground(Color.RED);
        } else {
            punkte = getIntervall(punkte[0], punkte[1]);
            if(punkte == null || punkte.length != 3) {
                erfolg = false;
                coordsKreuzer.setForeground(Color.RED);
            } else {
                schiffe[1] = SchiffGenerator.generateSchiff(punkte);
            }
        }
        
        //Panzerschiff 1
        punkte = getPunkte(coordsPanzerschiff1.getText());
        if(punkte == null) {
            erfolg = false;
            coordsPanzerschiff1.setForeground(Color.RED);
        } else {
            punkte = getIntervall(punkte[0], punkte[1]);
            if(punkte == null || punkte.length != 4) {
                erfolg = false;
                coordsPanzerschiff1.setForeground(Color.RED);
            } else {
                schiffe[2] = SchiffGenerator.generateSchiff(punkte);
            }
        }
        
        //Panzerschiff 2
        punkte = getPunkte(coordsPanzerschiff2.getText());
        if(punkte == null) {
            erfolg = false;
            coordsPanzerschiff2.setForeground(Color.RED);
        } else {
            punkte = getIntervall(punkte[0], punkte[1]);
            if(punkte == null || punkte.length != 4) {
                erfolg = false;
                coordsPanzerschiff2.setForeground(Color.RED);
            } else {
                schiffe[3] = SchiffGenerator.generateSchiff(punkte);
            }
        }
        
        //Flugzeugträger
        punkte = getPunkte(coordsFlugzeug.getText());
        if(punkte == null) {
            erfolg = false;
            coordsFlugzeug.setForeground(Color.RED);
        } else {
            punkte = getIntervall(punkte[0], punkte[1]);
            if(punkte == null || punkte.length != 5) {
                erfolg = false;
                coordsFlugzeug.setForeground(Color.RED);
            } else {
                schiffe[4] = SchiffGenerator.generateSchiff(punkte);
            }
        }
        
        if(erfolg) {
            dispose();
        } else {
            msgLabel.setForeground(Color.RED);
        }
    }
    
    private Point[] getPunkte(String text) {
        String[] split = text.split("\\-");
        if(split.length != 2) {
            return null;
        }
        split[0] = split[0].trim();
        split[1] = split[1].trim();
        
        Point[] tmp = new Point[2];
        try {
            tmp[0] = new Point(Integer.parseInt(split[0].substring(1) + ""), split[0].charAt(0) - 65);
            tmp[1] = new Point(Integer.parseInt(split[1].substring(1) + ""), split[1].charAt(0) - 65);
        } catch(NumberFormatException e) {
            return null;
        }
        return tmp;
    }
    
    private Point[] getIntervall(Point start, Point ende) {
        if(start.x == ende.x) {
            if(start.y >= ende.y) {
                return null;
            }
            Point[] tmp = new Point[(ende.y - start.y) + 1];
            for(int x = 0; x < tmp.length; x++) {
                tmp[x] = new Point(start.x - 1,  start.y + x);
            }
            return tmp;
        } else if(start.y == ende.y) {
            if(start.x >= ende.x) {
                return null;
            }
            Point[] tmp = new Point[(ende.x - start.x) + 1];
            for(int x = 0; x < tmp.length; x++) {
                tmp[x] = new Point(start.x + x - 1,  start.y);
            }
            return tmp;
        } else {
            return null;
        }
    }
    
    public Schiff[] getSchiffe() {
        if(erfolg) {
            return schiffe;
        } else {
            return null;
        }
    }
    
    private void standard() {
        coordsRuderboot.setText("A1-A2");
        coordsRuderboot.setForeground(Color.BLACK);
        coordsKreuzer.setText("B1-B3");
        coordsKreuzer.setForeground(Color.BLACK);
        coordsPanzerschiff1.setText("C1-C4");
        coordsPanzerschiff1.setForeground(Color.BLACK);
        coordsPanzerschiff2.setText("D1-D4");
        coordsPanzerschiff2.setForeground(Color.BLACK);
        coordsFlugzeug.setText("E1-E5");
        coordsFlugzeug.setForeground(Color.BLACK);
    }
    
    private void zufall() {
        new Thread() {
            @Override
            public void run() {
                Schiff[] tmp = new Schiff[5];

                tmp[0] = getRandomSchiff(2);
                coordsRuderboot.setText((char)(tmp[0].getCoords()[0].y+65)+""+(tmp[0].getCoords()[0].x+1)+" - "+(char)(tmp[0].getCoords()[1].y+65)+""+(tmp[0].getCoords()[1].x+1));
                do {
                    tmp[1] = getRandomSchiff(3);
                } while(!SchiffGenerator.validate(tmp));
                coordsKreuzer.setText((char)(tmp[1].getCoords()[0].y+65)+""+(tmp[1].getCoords()[0].x+1)+" - "+(char)(tmp[1].getCoords()[2].y+65)+""+(tmp[1].getCoords()[2].x+1));
                do {
                    tmp[2] = getRandomSchiff(4);
                } while(!SchiffGenerator.validate(tmp));
                coordsPanzerschiff1.setText((char)(tmp[2].getCoords()[0].y+65)+""+(tmp[2].getCoords()[0].x+1)+" - "+(char)(tmp[2].getCoords()[3].y+65)+""+(tmp[2].getCoords()[3].x+1));
                do {
                    tmp[3] = getRandomSchiff(4);
                } while(!SchiffGenerator.validate(tmp));
                coordsPanzerschiff2.setText((char)(tmp[3].getCoords()[0].y+65)+""+(tmp[3].getCoords()[0].x+1)+" - "+(char)(tmp[3].getCoords()[3].y+65)+""+(tmp[3].getCoords()[3].x+1));
                do {
                    tmp[4] = getRandomSchiff(5);
                } while(!SchiffGenerator.validate(tmp));
                coordsFlugzeug.setText((char)(tmp[4].getCoords()[0].y+65)+""+(tmp[4].getCoords()[0].x+1)+" - "+(char)(tmp[4].getCoords()[4].y+65)+""+(tmp[4].getCoords()[4].x+1));

                zufall.setEnabled(true);
            }
        }.start();
    }
    
    private Schiff getRandomSchiff(int size) {
        boolean vertical = new Random(System.nanoTime()).nextBoolean();
        if(vertical) {
            int xStart = zufallsZahl(1, 10);
            int yStart = zufallsZahl(0, 9-(size-1));
            
            return SchiffGenerator.generateSchiff(getIntervall(new Point(xStart, yStart), new Point(xStart, yStart + (size-1))));
        } else {
            int xStart = zufallsZahl(1, 10 - (size-1));
            int yStart = zufallsZahl(0, 9);
            
            return SchiffGenerator.generateSchiff(getIntervall(new Point(xStart, yStart), new Point(xStart + (size-1), yStart)));
        }
    }
    
    public int zufallsZahl(int min, int max) {
        return (int)(Math.random() * ((max+1) - min) + min);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        coordsRuderboot = new javax.swing.JTextField();
        coordsKreuzer = new javax.swing.JTextField();
        coordsPanzerschiff1 = new javax.swing.JTextField();
        coordsPanzerschiff2 = new javax.swing.JTextField();
        coordsFlugzeug = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        ok = new javax.swing.JButton();
        cancle = new javax.swing.JButton();
        msgLabel = new javax.swing.JLabel();
        standard = new javax.swing.JButton();
        zufall = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Schiffe platzieren");
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setText("Platziere deine Schiffe...");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Ruderboot (Größe 2)");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("Kreuzer (Größe 3)");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("Panzerschiff (Größe 4)");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("Flugzeugträger (Größe 5)");

        jLabel6.setFont(new java.awt.Font("Tahoma", 2, 14)); // NOI18N
        jLabel6.setText("Beispieleingabe: A1 - A3");

        ok.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        ok.setText("Setzten!");
        ok.setFocusPainted(false);

        cancle.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        cancle.setText("Abbrechen");
        cancle.setFocusPainted(false);

        msgLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        standard.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        standard.setText("Standardverteilung");

        zufall.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        zufall.setText("Zufall");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(msgLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 531, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(coordsRuderboot, javax.swing.GroupLayout.DEFAULT_SIZE, 289, Short.MAX_VALUE)
                            .addComponent(coordsKreuzer)
                            .addComponent(coordsPanzerschiff1)
                            .addComponent(coordsPanzerschiff2)
                            .addComponent(coordsFlugzeug)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cancle)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(zufall)
                        .addGap(18, 18, 18)
                        .addComponent(standard)
                        .addGap(18, 18, 18)
                        .addComponent(ok)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(30, 30, 30)
                        .addComponent(jLabel2))
                    .addComponent(coordsRuderboot, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(coordsKreuzer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(coordsPanzerschiff1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(coordsPanzerschiff2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5)
                    .addComponent(coordsFlugzeug, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addComponent(msgLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ok)
                    .addComponent(cancle)
                    .addComponent(standard)
                    .addComponent(zufall))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancle;
    private javax.swing.JTextField coordsFlugzeug;
    private javax.swing.JTextField coordsKreuzer;
    private javax.swing.JTextField coordsPanzerschiff1;
    private javax.swing.JTextField coordsPanzerschiff2;
    private javax.swing.JTextField coordsRuderboot;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel msgLabel;
    private javax.swing.JButton ok;
    private javax.swing.JButton standard;
    private javax.swing.JButton zufall;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == cancle) {
            erfolg = false;
            dispose();
        } else if(e.getSource() == standard) {
            standard();
        } else if(e.getSource() == zufall) {
            zufall.setEnabled(false);
            zufall();
        } else {
            check();
        }
    }
}
