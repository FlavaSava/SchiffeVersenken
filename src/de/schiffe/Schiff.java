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
public abstract class Schiff {
    
    public static final byte TYPE_LEFT_RIGHT = 1;
    public static final byte TYPE_TOP_DOWN = 10;

    protected final Point[] coords;
    private final boolean[] hit;
    private boolean verified;
    
    public Schiff(int size) {
        coords = new Point[size];
        hit = new boolean[size];
        verified = false;
    }
    
    public void hit(int x, int y) {
        for(int z = 0; z < coords.length; z++) {
            if(coords[z].x == x && coords[z].y == y) {
                hit[z] = true;
            }
        }
    }
    
    public int getSize() {
        return coords.length;
    }
    
    public int getSegment(Point p) {
        for(int x = 0; x < coords.length; x++) {
            if(coords[x].equals(p)) {
                return x+1;
            }
        }
        return -1;
    }
    
    protected void verify() {
        //Koordinaten checken
        final byte type = getType();
        for(int x = 0; x < coords.length-1; x++) {
            if(type == TYPE_LEFT_RIGHT) {
                if(coords[x].x != (coords[x+1].x - 1)) {
                    verified = false;
                    return;
                }
            } else {
                if(coords[x].y != (coords[x+1].y - 1)) {
                    verified = false;
                    return;
                }
            }
        }
        verified = true;
    }
    
    public byte getType() {
        if(coords[0].x == (coords[1].x - 1)) {
            return TYPE_LEFT_RIGHT;
        } else {
            return TYPE_TOP_DOWN;
        }
    }
    
    public boolean isVerified() {
        return verified;
    }
    
    public boolean isDestroyed() {
        for(boolean h : hit) {
            if(!h) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public String toString() {
        String res = "{";
        for(Point p : coords) {
            if(p != null) {
                res += (char)(p.y+65)+""+(p.x+1)+", ";
            } else {
                res += "n/A, ";
            }
        }
        return res.substring(0, res.length()-2) + "}";
    }
    
    public Point[] getCoords() {
        return coords;
    }
    
}
