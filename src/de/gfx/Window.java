/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.gfx;

import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.swing.JFrame;

/**
 *
 * @author lucash
 */
public class Window extends JFrame implements ComponentListener {
    
    public static final Console console = new Console();
    
    private final Feld actionField;
    private final Feld viewField;

    public Window() {
        initComponents();
        actionField = new Feld(new Dimension(550,550));
        viewField = new Feld(new Dimension(550,550));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        feldSlotEigen.add(viewField);
        feldSlotView.add(actionField);
        console.setSize(consoleSlot.getSize());
        consoleSlot.add(console);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        actionField.drawActivField(true);
        actionField.startRefresh();
        viewField.startRefresh();
        console.print("Talentfreie chinesen");
        addComponentListener(this);
        setTitle("Schiffe versenken v1.0a");
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        feldSlotView = new javax.swing.JPanel();
        feldSlotEigen = new javax.swing.JPanel();
        consoleSlot = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

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
            .addGap(0, 120, Short.MAX_VALUE)
        );

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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                        .addComponent(feldSlotView, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(feldSlotEigen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(feldSlotView, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(consoleSlot, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel consoleSlot;
    private javax.swing.JPanel feldSlotEigen;
    private javax.swing.JPanel feldSlotView;
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
    }

    @Override
    public void componentShown(ComponentEvent e) {
    }

    @Override
    public void componentHidden(ComponentEvent e) {
    }
}
