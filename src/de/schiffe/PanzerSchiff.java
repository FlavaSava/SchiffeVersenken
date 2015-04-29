/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.schiffe;

import java.awt.Point;

/**
 *
 * @author Lucas Hartel
 */
public class PanzerSchiff extends Schiff {
    
    public PanzerSchiff(Point p1, Point p2, Point p3, Point p4) {
        super(4);
        coords[0] = new Point(p1);
        coords[1] = new Point(p2);
        coords[2] = new Point(p3);
        coords[3] = new Point(p4);
    }
    
    @Override
    public String toString() {
        return "PanzerSchiff " + super.toString();
    }

}
