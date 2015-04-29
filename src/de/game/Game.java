/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.game;

import de.gfx.SchiffPlatzierer;
import de.gfx.Window;
import de.schiffe.Schiff;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 *
 * @author Lucas Hartel
 */
public class Game implements MouseListener {
    
    private final boolean[] mouse;
    private Window window;
    
    public Game() {
        mouse = new boolean[3];
        window = new Window();
    }
    
    public void start() {
        SchiffPlatzierer dlg = new SchiffPlatzierer(window);
        dlg.setVisible(true);
        Schiff[] schiffe = dlg.getSchiffe();
        if(schiffe == null) {
            System.exit(1);
        }
        for(Schiff f : schiffe) {
            window.getActionField().addSchiff(f);
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
    
    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }
}
