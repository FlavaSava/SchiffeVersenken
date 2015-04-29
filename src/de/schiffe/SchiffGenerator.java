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
public class SchiffGenerator {
    
    public static Schiff generateSchiff(Point[] coords) {
        Schiff tmp;
        if(coords.length == 2) {
            tmp = new RuderBoot(coords[0], coords[1]);
        } else if(coords.length == 3) {
            tmp = new Kreuzer(coords[0], coords[1], coords[2]);
        } else if(coords.length == 4) {
            tmp = new PanzerSchiff(coords[0], coords[1], coords[2], coords[3]);
        } else if(coords.length == 5) {
            tmp = new FlugzeugTraeger(coords[0], coords[1], coords[2], coords[3], coords[4]);
        } else {
            return null;
        }
        tmp.verify();
        if(!tmp.isVerified()) {
            System.out.println("=======================");
            System.out.println("Fehler beim Verifizieren des Schiffs:");
            System.out.println(tmp);
            System.out.println("=======================");
        }
        return tmp;
    }

}
