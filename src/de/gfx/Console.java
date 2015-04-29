/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.gfx;

import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

/**
 *
 * @author Lucas Hartel
 */
public class Console extends JScrollPane{
    
    protected final JTextPane out;
    
    protected boolean autoscroll;
    
    public Console() {
        out = new JTextPane();
        autoscroll = true;
        
        this.setViewportView(out);
        
        out.setEditable(false);
        out.setBackground(Color.WHITE);
        out.setFocusable(true);
        out.setContentType("text/html");
        out.setText(" ");
    }
    
    public void print(String msg) {
        EventQueue.invokeLater(() -> {
            out.setText(out.getText().substring(0, out.getText().indexOf("</body>\n</html>")) + msg+"</body></html>");
            if(autoscroll) {
                scrollDown();
            }         
        });
    }
    
    public void print(Object o) {
        print(o.toString());
    }
    
    public void println() {
        EventQueue.invokeLater(() -> {
            out.setText(out.getText().substring(0, out.getText().indexOf("</body>\n</html>"))+"<br></body></html>");
            if(autoscroll) {
                scrollDown();
            }
        });
    }
    
    public void println(String msg) {
        EventQueue.invokeLater(() -> (out.setText(out.getText().substring(0, out.getText().indexOf("</body>\n</html>")) + msg+"</body></html>")));
        println();
    }
    
    public void println(Object o) {
        println(o.toString());
    }
    
    public void clear() {
        EventQueue.invokeLater(() -> (out.setText(" ")));
    }
    
    public void setAutoscroll(boolean autoscroll) {
        this.autoscroll = autoscroll;
    }
    
    protected void scrollDown() {
        out.selectAll();
        out.select(out.getSelectionEnd(),out.getSelectionEnd());
    }

}
