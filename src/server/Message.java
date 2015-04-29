/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package server;

import java.io.Serializable;

/**
 *
 * @author Lucas Hartel
 */
public class Message implements Serializable {
    
    public String msg;
    
    public Message(String msg) {
        this.msg = msg;
    }

}
