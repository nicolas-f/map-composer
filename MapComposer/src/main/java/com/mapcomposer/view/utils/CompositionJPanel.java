 package com.mapcomposer.view.utils;

import com.mapcomposer.controller.UIController;
import com.mapcomposer.model.graphicalelement.element.Document;
import com.mapcomposer.model.graphicalelement.interfaces.GraphicalElement;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 * This panel extends from JPanel define the action to do when the user click on it.
 */
public class CompositionJPanel extends JPanel implements MouseListener, MouseMotionListener{
    
    /**Panel contained by the CompositionJPanel.*/
    private JPanel panel;
    /**GraphicalElement displayed*/
    private final GraphicalElement ge;
    /**Select state of the panel*/
    private boolean selected;
    /**X initial position when user want to move the panel.*/
    private int startX;
    /**Y initial position when user want to move the panel.*/
    private int startY;
    /**Type of move the user want to do.*/
    private char moveMod;
    
    /**
     * Main constructor.
     * @param ge GraphicalElement to display.
     */
    public CompositionJPanel(GraphicalElement ge){
        super(new BorderLayout());
        moveMod = 0;
        this.ge=ge;
        panel=new JPanel();
        selected = false;
        this.add(panel, BorderLayout.CENTER);
        startX=0;
        startY=0;
        //Disable listeners if it's a Document panel.
        if(ge instanceof Document)
            this.setEnabled(false);
        else{
            this.addMouseListener(this);
            this.addMouseMotionListener(this);
        }
    }
    
    /**
     * Sets the panel contained by the object.
     * @param panel New panel.
     */
    public void setPanel(JPanel panel){
        this.panel = panel;
        this.selected=false;
        setBorders();
    }

    /**
     * Draw border if the CompositionJPanel is selected.
     */
    private void setBorders() {
       if(selected){
           panel.setBorder(BorderFactory.createLineBorder(Color.ORANGE));
       }
       else{
           panel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
       }
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        this.selected=!selected;
        setBorders();
        if(selected){
            UIController.getInstance().selectGE(ge);
        }
        else{
            UIController.getInstance().unselectGE(ge);
        }
        setBorders();
    }

    @Override
    public void mousePressed(MouseEvent me) {
        startX = me.getLocationOnScreen().x;
        startY = me.getLocationOnScreen().y;
        
        if((me.getY()>=0 && me.getY()<=10) && (me.getX()>=0 && me.getX()<=10)){
            moveMod=2;
            this.setCursor(new Cursor(Cursor.NW_RESIZE_CURSOR));
        }
        else if((me.getX()>=0 && me.getX()<=10) && (me.getY()>=ge.getHeight()-10 && me.getY()<=ge.getHeight())){
            moveMod=4;
            this.setCursor(new Cursor(Cursor.SW_RESIZE_CURSOR));
        }
        else if((me.getY()>=ge.getHeight()-10 && me.getY()<=ge.getHeight()) &&
                (me.getX()>=ge.getWidth()-10 && me.getX()<=ge.getWidth())){
            moveMod=6;
            this.setCursor(new Cursor(Cursor.SE_RESIZE_CURSOR));
        }
        else if((me.getX()>=ge.getWidth()-10 && me.getX()<=ge.getWidth()) && (me.getY()>=0 && me.getY()<=10)){
            moveMod=8;
            this.setCursor(new Cursor(Cursor.NE_RESIZE_CURSOR));
        }
        else if(me.getY()>=0 && me.getY()<=10){
            moveMod=1;
            this.setCursor(new Cursor(Cursor.N_RESIZE_CURSOR));
        }
        else if(me.getX()>=0 && me.getX()<=10){
            moveMod=3;
            this.setCursor(new Cursor(Cursor.W_RESIZE_CURSOR));
        }
        else if(me.getY()>=ge.getHeight()-10 && me.getY()<=ge.getHeight()){
            moveMod=5;
            this.setCursor(new Cursor(Cursor.S_RESIZE_CURSOR));
        }
        else if(me.getX()>=ge.getWidth()-10 && me.getX()<=ge.getWidth()){
            moveMod=7;
            this.setCursor(new Cursor(Cursor.E_RESIZE_CURSOR));
        }
        else{
            this.setCursor(new Cursor(Cursor.MOVE_CURSOR));
            moveMod=9;
        }
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        switch(moveMod){
            case 1:
                ge.setHeight(Math.abs(startY-me.getLocationOnScreen().y+ge.getHeight()));
                ge.setY(ge.getY()-(startY-me.getLocationOnScreen().y));
                panel.setBounds(ge.getX(), ge.getY(), ge.getWidth(), ge.getHeight());
                break;
            case 2:
                ge.setHeight(Math.abs(startY-me.getLocationOnScreen().y+ge.getHeight()));
                ge.setY(ge.getY()-(startY-me.getLocationOnScreen().y));
                ge.setWidth(Math.abs(startX-me.getLocationOnScreen().x+ge.getWidth()));
                ge.setX(ge.getX()-(startX-me.getLocationOnScreen().x));
                panel.setBounds(ge.getX(), ge.getY(), ge.getWidth(), ge.getHeight());
                break;
            case 3:
                ge.setWidth(Math.abs(startX-me.getLocationOnScreen().x+ge.getWidth()));
                ge.setX(ge.getX()-(startX-me.getLocationOnScreen().x));
                panel.setBounds(ge.getX(), ge.getY(),  ge.getWidth(), ge.getHeight());
                break;
            case 4:
                ge.setWidth(Math.abs(startX-me.getLocationOnScreen().x+ge.getWidth()));
                ge.setX(ge.getX()-(startX-me.getLocationOnScreen().x));
                ge.setHeight(Math.abs(-(startY-me.getLocationOnScreen().y)+ge.getHeight()));
                panel.setBounds(ge.getX(), ge.getY(), ge.getWidth(), ge.getHeight());
                break;
            case 5:
                ge.setHeight(Math.abs(-(startY-me.getLocationOnScreen().y)+ge.getHeight()));
                panel.setBounds(ge.getX(), ge.getY(), ge.getWidth(), ge.getHeight());
                break;
            case 6:
                ge.setHeight(Math.abs(-(startY-me.getLocationOnScreen().y)+ge.getHeight()));
                ge.setWidth(Math.abs(-(startX-me.getLocationOnScreen().x)+ge.getWidth()));
                panel.setBounds(ge.getX(), ge.getY(),  ge.getWidth(), ge.getHeight());
                break;
            case 7:
                ge.setWidth(Math.abs(-(startX-me.getLocationOnScreen().x)+ge.getWidth()));
                panel.setBounds(ge.getX(), ge.getY(), ge.getWidth(), ge.getHeight());
                break;
            case 8 :
                ge.setWidth(Math.abs(-(startX-me.getLocationOnScreen().x)+ge.getWidth()));
                ge.setHeight(Math.abs(startY-me.getLocationOnScreen().y+ge.getHeight()));
                ge.setY(ge.getY()-(startY-me.getLocationOnScreen().y));
                panel.setBounds(ge.getX(), ge.getY(), ge.getWidth(), ge.getHeight());
                break;
            case 9:
                ge.setX(ge.getX()-startX+me.getLocationOnScreen().x);
                ge.setY(ge.getY()-startY+me.getLocationOnScreen().y);
                this.panel.setLocation(ge.getX(), ge.getY());
                break;
        }
        if(selected){
            UIController.getInstance().unselectGE(ge);
            UIController.getInstance().selectGE(ge);
        }
        setBorders();
        moveMod=0;
        
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    @Override
    public void mouseEntered(MouseEvent me) {}

    @Override
    public void mouseExited(MouseEvent me) {}

    @Override
    public void mouseDragged(MouseEvent me) {
        if(moveMod==9){
            panel.setLocation(ge.getX()-(startX-me.getLocationOnScreen().x), ge.getY()-(startY-me.getLocationOnScreen().y));
        }
    }

    @Override
    public void mouseMoved(MouseEvent me) {
        int x = me.getX();
        int y = me.getY();
        if((y>=0 && y<=10) && (x>=0 && x<=10)){
            this.setCursor(new Cursor(Cursor.NW_RESIZE_CURSOR));
        }
        else if((x>=0 && x<=10) && (y>=ge.getHeight()-5 && y<=ge.getHeight())){
            this.setCursor(new Cursor(Cursor.SW_RESIZE_CURSOR));
        }
        else if((y>=ge.getHeight()-10 && y<=ge.getHeight()) &&
                (x>=ge.getWidth()-10 && x<=ge.getWidth())){
            this.setCursor(new Cursor(Cursor.SE_RESIZE_CURSOR));
        }
        else if((x>=ge.getWidth()-10 && x<=ge.getWidth()) && (y>=0 && y<=10)){
            this.setCursor(new Cursor(Cursor.NE_RESIZE_CURSOR));
        }
        else if(x>=0 && y<=10){
            this.setCursor(new Cursor(Cursor.N_RESIZE_CURSOR));
        }
        else if(x>=0 && x<=10){
            this.setCursor(new Cursor(Cursor.W_RESIZE_CURSOR));
        }
        else if(y>=ge.getHeight()-5 && y<=ge.getHeight()){
            this.setCursor(new Cursor(Cursor.S_RESIZE_CURSOR));
        }
        else if(x>=ge.getWidth()-5 && x<=ge.getWidth()){
            this.setCursor(new Cursor(Cursor.E_RESIZE_CURSOR));
        }
        else{
            this.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
    }
    
    /**
     * Draw red border to the element for one second.
     */
    public void hightlight(){
        try {
            Rectangle r = new Rectangle(this.getLocation(), this.getSize());
            panel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.red));
            this.paintImmediately(this.getVisibleRect());
            sleep(1000);
            setBorders();
        } catch (InterruptedException ex) {
            Logger.getLogger(CompositionJPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
