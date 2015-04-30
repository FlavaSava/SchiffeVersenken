/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.gfx;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

/**
 *
 * @author lucash
 */
public class Window extends JFrame implements ComponentListener, ActionListener {
    
    public static final Console console = new Console();
    
    private final Feld actionField;
    private final Feld viewField;
    private final SettingsWindow settingsWindow;

    @SuppressWarnings("")
    public Window() {
        initComponents();
        actionField = new Feld(new Dimension(550,550));
        viewField = new Feld(new Dimension(550,550));
        settingsWindow = new SettingsWindow(this);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        feldSlotEigen.add(viewField);
        feldSlotView.add(actionField);
        console.setSize(consoleSlot.getSize());
        consoleSlot.add(console);
        settings.addActionListener(this);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        actionField.drawActivField(true);
        addComponentListener(this);
        setTitle("Schiffe versenken v1.0a");
        setBackgroundImage(null);
        actionField.startRefresh();
        viewField.startRefresh();
    }
    
    protected void setBackgroundImage(File f) {
        Image i = null;
        if(f == null) {
            try {
                i = ImageIO.read(getClass().getClassLoader().getResourceAsStream("water.png")).getScaledInstance(feldSlotEigen.getWidth(), feldSlotEigen.getHeight(), Image.SCALE_SMOOTH);
            } catch(IOException e) {
                //...
            }
        } else {
            try {
                i = ImageIO.read(f).getScaledInstance(feldSlotEigen.getWidth(), feldSlotEigen.getHeight(), Image.SCALE_SMOOTH);
            } catch (IOException ex) {
                //...
            }
        }
        actionField.setBackgroundImage(i);
        viewField.setBackgroundImage(i);
    }
    
    public void setConnected(boolean connected) {
        if(connected) {
            connectStatus.setBackground(Color.GREEN);
            connectStatus.setText("Verbindungsstatus: Verbunden");
        } else {
            connectStatus.setBackground(Color.RED);
            connectStatus.setText("Verbindungsstatus: Nicht verbunden");
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        feldSlotView = new javax.swing.JPanel();
        feldSlotEigen = new javax.swing.JPanel();
        consoleSlot = new javax.swing.JPanel();
        connectStatus = new javax.swing.JLabel();
        settings = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        feldSlotView.setBackground(new java.awt.Color(255, 255, 255));
        feldSlotView.setPreferredSize(new java.awt.Dimension(550, 550));

        javax.swing.GroupLayout feldSlotViewLayout = new javax.swing.GroupLayout(feldSlotView);
        feldSlotView.setLayout(feldSlotViewLayout);
        feldSlotViewLayout.setHorizontalGroup(
            feldSlotViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 550, Short.MAX_VALUE)
        );
        feldSlotViewLayout.setVerticalGroup(
            feldSlotViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 550, Short.MAX_VALUE)
        );

        feldSlotEigen.setBackground(new java.awt.Color(255, 255, 255));
        feldSlotEigen.setPreferredSize(new java.awt.Dimension(550, 550));

        javax.swing.GroupLayout feldSlotEigenLayout = new javax.swing.GroupLayout(feldSlotEigen);
        feldSlotEigen.setLayout(feldSlotEigenLayout);
        feldSlotEigenLayout.setHorizontalGroup(
            feldSlotEigenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 550, Short.MAX_VALUE)
        );
        feldSlotEigenLayout.setVerticalGroup(
            feldSlotEigenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 550, Short.MAX_VALUE)
        );

        consoleSlot.setBackground(new java.awt.Color(123, 154, 210));

        javax.swing.GroupLayout consoleSlotLayout = new javax.swing.GroupLayout(consoleSlot);
        consoleSlot.setLayout(consoleSlotLayout);
        consoleSlotLayout.setHorizontalGroup(
            consoleSlotLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        consoleSlotLayout.setVerticalGroup(
            consoleSlotLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 134, Short.MAX_VALUE)
        );

        connectStatus.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        connectStatus.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        connectStatus.setText("Verbindungsstatus");
        connectStatus.setOpaque(true);

        settings.setIcon(new javax.swing.ImageIcon(getClass().getResource("/settings.png"))); // NOI18N
        settings.setFocusPainted(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(consoleSlot, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(feldSlotEigen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 70, Short.MAX_VALUE)
                        .addComponent(feldSlotView, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(connectStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(settings, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(feldSlotEigen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(feldSlotView, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(connectStatus)
                    .addComponent(settings, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(consoleSlot, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel connectStatus;
    private javax.swing.JPanel consoleSlot;
    private javax.swing.JPanel feldSlotEigen;
    private javax.swing.JPanel feldSlotView;
    private javax.swing.JButton settings;
    // End of variables declaration//GEN-END:variables

    public Feld getActionField() {
        return actionField;
    }
    
    public Feld getViewField() {
        return viewField;
    }
    
    @Override
    public void componentResized(ComponentEvent e) {
        console.setSize(consoleSlot.getSize());
    }

    @Override
    public void componentMoved(ComponentEvent e) {
        //...
    }

    @Override
    public void componentShown(ComponentEvent e) {
        //...
    }

    @Override
    public void componentHidden(ComponentEvent e) {
        //...
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == settings) {
            settingsWindow.refresh();
            settingsWindow.setVisible(true);
        }
    }
}
