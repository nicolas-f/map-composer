package com.mapcomposer.view.graphicalelement;

import com.mapcomposer.model.graphicalelement.interfaces.GraphicalElement;
import com.mapcomposer.model.graphicalelement.element.text.TextElement;
import com.mapcomposer.view.ui.ConfigurationShutter;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;
import java.text.AttributedString;
import javax.swing.UIManager;

/**
 * Renderer associated to the scale GraphicalElement.
 */
public class TextRenderer extends GERenderer{

    @Override
    public BufferedImage getcontentImage(GraphicalElement ge){
        TextElement te = ((TextElement)ge);
        //Drawing on a BufferedImage the text.
        BufferedImage bi = new BufferedImage(ge.getWidth(), ge.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D graph = bi.createGraphics();
        graph.setBackground(ConfigurationShutter.getInstance().getBackground());
        graph.setColor(ConfigurationShutter.getInstance().getBackground());
        Color color = new Color(    te.getColorBack().getRed(), 
                                    te.getColorBack().getGreen(), 
                                    te.getColorBack().getBlue(), 
                                    te.getAlpha()
                                );
        graph.setColor(color);
        graph.fillRect(0, 0, te.getWidth(), te.getHeight());
        graph = bi.createGraphics();
        
        //Draw the string and make it fit to the TextElement bounds
        AttributedString attributedString = new AttributedString(te.getText());
        attributedString.addAttribute(TextAttribute.FONT, new Font(te.getFont(), te.getStyle(), te.getFontSize()));
        color = te.getColorText();
        attributedString.addAttribute(TextAttribute.FOREGROUND, color);
        int x = 0;
        int y = 0;
        LineBreakMeasurer measurer = new LineBreakMeasurer(attributedString.getIterator(),graph.getFontRenderContext());
        while (measurer.getPosition() < attributedString.getIterator().getEndIndex()) {
            TextLayout textLayout = measurer.nextLayout(te.getWidth());
            y += textLayout.getAscent();
            switch(te.getAlignment()){
                case LEFT:
                    x=0;
                    break;
                case CENTER:
                    x=(int) ((te.getWidth()-textLayout.getBounds().getWidth())/2);
                    break;
                case RIGHT:
                    x=(int) (te.getWidth()-textLayout.getBounds().getWidth());
                    break;
            }
            textLayout.draw(graph, x, y);
            y += textLayout.getDescent() + textLayout.getLeading();
        }
        
        
        
        
        
        //graph.drawString(te.getText(), 0, ge.getHeight()/2);
        return bi;
    }
}
