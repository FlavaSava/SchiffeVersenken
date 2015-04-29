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
public class RuderBoot extends Schiff {
    
    public RuderBoot(Point p1, Point p2) {
        super(2);
        coords[0] = new Point(p1);
        coords[1] = new Point(p2);
    }
    
    @Override
    public String toString() {
        return "RuderBoot " + super.toString();
    }

}
