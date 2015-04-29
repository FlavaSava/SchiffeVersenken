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
public class Kreuzer extends Schiff {
    
    public Kreuzer(Point p1, Point p2, Point p3) {
        super(3);
        coords[0] = new Point(p1);
        coords[1] = new Point(p2);
        coords[2] = new Point(p3);
    }
    
    @Override
    public String toString() {
        return "Kreuzer " + super.toString();
    }

}
