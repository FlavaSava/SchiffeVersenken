/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.gfx;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFrame;

/**
 *
 * @author lucash
 */
public class SettingsWindow extends JFrame implements ActionListener {
    
    private final Window window;

    @SuppressWarnings("")
    public SettingsWindow(Window window) {
        initComponents();
        this.window = window;
        ok.addActionListener(this);
        cancle.addActionListener(this);
        apply.addActionListener(this);
        setLocationRelativeTo(window);
    }
    
    public void refresh() {
        gridcolor.setText(Feld.GRID_COLOR.getRed()+","+Feld.GRID_COLOR.getGreen()+","+Feld.GRID_COLOR.getBlue());
        labelcolor.setText(Feld.LABEL_COLOR.getRed()+","+Feld.LABEL_COLOR.getGreen()+","+Feld.LABEL_COLOR.getBlue());
        shipcolor.setText(Feld.SHIP_COLOR.getRed()+","+Feld.SHIP_COLOR.getGreen()+","+Feld.SHIP_COLOR.getBlue());
        hitcolor.setText(Feld.HIT_COLOR.getRed()+","+Feld.HIT_COLOR.getGreen()+","+Feld.HIT_COLOR.getBlue());
        watercolor.setText(Feld.WATER_COLOR.getRed()+","+Feld.WATER_COLOR.getGreen()+","+Feld.WATER_COLOR.getBlue());
        
        combobackground.removeAllItems();
        combobackground.addItem("Standard");
        combobackground.addItem("Kein");
        if(new File("background").exists()) {
            for(File f : new File("background").listFiles()) {
                if(f.isFile() && f.getName().endsWith(".png")) {
                    combobackground.addItem(f.getName());
                }
            }
        }
    }
    
    private void apply() {
        applyGridColor();
        applyHitColor();
        applyLabelColor();
        applyShipColor();
        applyWaterColor();
        applyBackgroundImage();
    }
    
    private void applyBackgroundImage() {
        String res = combobackground.getSelectedItem().toString();
        
        switch (res) {
            case "Standard":
                window.setBackgroundImage(null);
                break;
            case "Kein":
                window.getActionField().setBackgroundImage(null);
                window.getViewField().setBackgroundImage(null);
                break;
            default:
                window.setBackgroundImage(new File("background/"+res));
                break;
        }
    }
    
    private void applyGridColor() {
        String[] split;
        split = gridcolor.getText().split(",");
        if(split.length == 3) {
            try {
                Color c = new Color((int)Math.min(Math.abs(Integer.parseInt(split[0])), 255), (int)Math.min(Math.abs(Integer.parseInt(split[1])), 255), (int)Math.min(Math.abs(Integer.parseInt(split[2])), 255));
                Feld.GRID_COLOR = c;
            } catch(NumberFormatException e) {
                gridcolor.setText(Feld.GRID_COLOR.getRed()+","+Feld.GRID_COLOR.getGreen()+","+Feld.GRID_COLOR.getBlue());
            }
        } else {
            gridcolor.setText(Feld.GRID_COLOR.getRed()+","+Feld.GRID_COLOR.getGreen()+","+Feld.GRID_COLOR.getBlue());
        }
    }
    
    private void applyLabelColor() {
        String[] split;
        split = labelcolor.getText().split(",");
        if(split.length == 3) {
            try {
                Color c = new Color((int)Math.min(Math.abs(Integer.parseInt(split[0])), 255), (int)Math.min(Math.abs(Integer.parseInt(split[1])), 255), (int)Math.min(Math.abs(Integer.parseInt(split[2])), 255));
                Feld.LABEL_COLOR = c;
            } catch(NumberFormatException e) {
                labelcolor.setText(Feld.LABEL_COLOR.getRed()+","+Feld.LABEL_COLOR.getGreen()+","+Feld.LABEL_COLOR.getBlue());
            }
        } else {
            labelcolor.setText(Feld.LABEL_COLOR.getRed()+","+Feld.LABEL_COLOR.getGreen()+","+Feld.LABEL_COLOR.getBlue());
        }
    }
    
    private void applyShipColor() {
        String[] split;
        split = shipcolor.getText().split(",");
        if(split.length == 3) {
            try {
                Color c = new Color((int)Math.min(Math.abs(Integer.parseInt(split[0])), 255), (int)Math.min(Math.abs(Integer.parseInt(split[1])), 255), (int)Math.min(Math.abs(Integer.parseInt(split[2])), 255));
                Feld.SHIP_COLOR = c;
            } catch(NumberFormatException e) {
                shipcolor.setText(Feld.SHIP_COLOR.getRed()+","+Feld.SHIP_COLOR.getGreen()+","+Feld.SHIP_COLOR.getBlue());
            }
        } else {
            shipcolor.setText(Feld.SHIP_COLOR.getRed()+","+Feld.SHIP_COLOR.getGreen()+","+Feld.SHIP_COLOR.getBlue());
        }
    }
    
    private void applyWaterColor() {
        String[] split;
        split = watercolor.getText().split(",");
        if(split.length == 3) {
            try {
                Color c = new Color((int)Math.min(Math.abs(Integer.parseInt(split[0])), 255), (int)Math.min(Math.abs(Integer.parseInt(split[1])), 255), (int)Math.min(Math.abs(Integer.parseInt(split[2])), 255));
                Feld.WATER_COLOR = c;
            } catch(NumberFormatException e) {
                watercolor.setText(Feld.WATER_COLOR.getRed()+","+Feld.WATER_COLOR.getGreen()+","+Feld.WATER_COLOR.getBlue());
            }
        } else {
            watercolor.setText(Feld.WATER_COLOR.getRed()+","+Feld.WATER_COLOR.getGreen()+","+Feld.WATER_COLOR.getBlue());
        }
    }
    
    private void applyHitColor() {
        String[] split;
        split = hitcolor.getText().split(",");
        if(split.length == 3) {
            try {
                Color c = new Color((int)Math.min(Math.abs(Integer.parseInt(split[0])), 255), (int)Math.min(Math.abs(Integer.parseInt(split[1])), 255), (int)Math.min(Math.abs(Integer.parseInt(split[2])), 255));
                Feld.HIT_COLOR = c;
            } catch(NumberFormatException e) {
                hitcolor.setText(Feld.HIT_COLOR.getRed()+","+Feld.HIT_COLOR.getGreen()+","+Feld.HIT_COLOR.getBlue());
            }
        } else {
            hitcolor.setText(Feld.HIT_COLOR.getRed()+","+Feld.HIT_COLOR.getGreen()+","+Feld.HIT_COLOR.getBlue());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        gridcolor = new javax.swing.JTextField();
        labelcolor = new javax.swing.JTextField();
        shipcolor = new javax.swing.JTextField();
        hitcolor = new javax.swing.JTextField();
        watercolor = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        ok = new javax.swing.JButton();
        apply = new javax.swing.JButton();
        cancle = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        combobackground = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Einstellungen");

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("Farben");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Gitterfarbe:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Beschriftungsfarbe:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Schiffsfarbe:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("\"Treffer\"-Farbe:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("\"Wasser\"-Farbe:");

        ok.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        ok.setText("Ok");

        apply.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        apply.setText("Übernehmen");

        cancle.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        cancle.setText("Schließen");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Hintergrundbild:");

        combobackground.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(gridcolor, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 66, Short.MAX_VALUE)
                        .addComponent(labelcolor, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(shipcolor, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(hitcolor, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(watercolor, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(cancle)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(apply)
                        .addGap(18, 18, 18)
                        .addComponent(ok))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(combobackground, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(gridcolor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(labelcolor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(shipcolor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(hitcolor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(watercolor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(combobackground, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ok)
                    .addComponent(apply)
                    .addComponent(cancle))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton apply;
    private javax.swing.JButton cancle;
    private javax.swing.JComboBox combobackground;
    private javax.swing.JTextField gridcolor;
    private javax.swing.JTextField hitcolor;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField labelcolor;
    private javax.swing.JButton ok;
    private javax.swing.JTextField shipcolor;
    private javax.swing.JTextField watercolor;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == ok) {
            apply();
            dispose();
        } else if(e.getSource() == apply) {
            apply();
        } else if(e.getSource() == cancle) {
            dispose();
        }
    }
}
