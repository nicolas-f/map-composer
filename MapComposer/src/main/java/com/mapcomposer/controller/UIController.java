package com.mapcomposer.controller;

import com.mapcomposer.model.configurationattribute.ConfigurationAttribute;
import com.mapcomposer.model.configurationattribute.utils.CAManager;
import com.mapcomposer.model.configurationattribute.utils.interfaces.CARefresh;
import com.mapcomposer.model.graphicalelement.GraphicalElement;
import com.mapcomposer.model.graphicalelement.element.cartographic.Key;
import com.mapcomposer.model.graphicalelement.element.cartographic.MapImage;
import com.mapcomposer.model.graphicalelement.element.cartographic.Orientation;
import com.mapcomposer.model.graphicalelement.element.cartographic.Scale;
import com.mapcomposer.model.graphicalelement.element.illustration.Image;
import com.mapcomposer.model.graphicalelement.element.text.TextElement;
import com.mapcomposer.model.graphicalelement.utils.GEManager;
import com.mapcomposer.model.graphicalelement.utils.GERefresh;
import com.mapcomposer.view.ui.CompositionArea;
import com.mapcomposer.view.ui.ConfigurationShutter;
import com.mapcomposer.view.utils.CompositionJPanel;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;

/**
 * This class manager the interaction between the user, the UI and the data model.
 */
public class UIController{
    /**Unique instance of the class*/
    private static UIController INSTANCE = null;
    /**Map doing the link between GraphicalElements and their CompositionJPanel*/
    private static Map<GraphicalElement, CompositionJPanel> map;
    /**Selected GraphicalElement */
    private static List<GraphicalElement> listGE;
    
    /**
     * Main constructor.
     */
    private UIController(){
        map = new HashMap<>();
        listGE = new ArrayList<>();
        //as example
        // map example
        MapImage mi = new MapImage();
        mi.setHeight(400);
        mi.setWidth(400);
        mi.setX(20);
        mi.setY(120);
        mi.setRotation(0);
        mi.setOwsContext("/home/sylvain/OrbisGIS/maps/MyMap.ows");
        map.put(mi, new CompositionJPanel(mi));
        CompositionArea.getInstance().addGE(getPanel(mi));
        map.get(mi).setPanel(GEManager.getInstance().render(mi.getClass()).render(mi));
        // scale example
        Scale s = new Scale();
        s.setHeight(20);
        s.setWidth(400);
        s.setX(20);
        s.setY(520);
        s.setRotation(0);
        s.setOwsContext("/home/sylvain/OrbisGIS/maps/MyMap.ows");
        map.put(s, new CompositionJPanel(s));
        CompositionArea.getInstance().addGE(getPanel(s));
        map.get(s).setPanel(GEManager.getInstance().render(s.getClass()).render(s));
        // orientation example
        Orientation o = new Orientation();
        o.setHeight(50);
        o.setWidth(50);
        o.setX(420);
        o.setY(120);
        o.setRotation(0);
        o.setOwsContext("/home/sylvain/OrbisGIS/maps/MyMap.ows");
        o.setIconPath("/home/sylvain/OrbisGIS/maps/arrow.png");
        map.put(o, new CompositionJPanel(o));
        CompositionArea.getInstance().addGE(getPanel(o));
        map.get(o).setPanel(GEManager.getInstance().render(o.getClass()).render(o));
        // Key example
        Key k = new Key();
        k.setHeight(200);
        k.setWidth(80);
        k.setX(430);
        k.setY(320);
        k.setRotation(0);
        k.setOwsContext("/home/sylvain/OrbisGIS/maps/MyMap.ows");
        map.put(k, new CompositionJPanel(k));
        CompositionArea.getInstance().addGE(getPanel(k));
        map.get(k).setPanel(GEManager.getInstance().render(k.getClass()).render(k));
        // scale example
        TextElement t = new TextElement();
        t.setHeight(20);
        t.setWidth(200);
        t.setX(20);
        t.setY(100);
        t.setRotation(0);
        t.setText("Map Composer");
        t.setFontSize(8);
        map.put(t, new CompositionJPanel(t));
        CompositionArea.getInstance().addGE(getPanel(t));
        map.get(t).setPanel(GEManager.getInstance().render(t.getClass()).render(t));
        // Image example
        Image i = new Image();
        i.setHeight(200);
        i.setWidth(400);
        i.setX(470);
        i.setY(20);
        i.setRotation(0);
        i.setPath("/home/sylvain/OrbisGIS/maps/logo.png");
        map.put(i, new CompositionJPanel(i));
        CompositionArea.getInstance().addGE(getPanel(i));
        map.get(i).setPanel(GEManager.getInstance().render(i.getClass()).render(i));
    }
    
    /**
     * Returns the CompositionPanel corresponding to a GraphicalElement.
     * @param ge GraphicalElement.
     * @return The CompositionPanel corresponding to the GraphicalElement.
     */
    public static CompositionJPanel getPanel(GraphicalElement ge){
        return map.get(ge);
    }
    
    /**
     * Returns the unique instance of the class.
     * @return The unique instance of the class.
     */
    public static UIController getInstance(){
        if(INSTANCE==null){
            INSTANCE=new UIController();
        }
        return INSTANCE;
    }
    
    /**
     * Selects a GraphicalElement.
     * @param ge GraphicalElement to select.
     */
    public void selectGE(GraphicalElement ge){
        this.listGE.add(ge);
        ConfigurationShutter.getInstance().dispalyConfiguration(getCommonAttributes());
    }
    
    /**
     * Unselect a GraphicalElement.
     * @param ge GraphicalElement to select.
     */
    public void unselectGE(GraphicalElement ge){
        this.listGE.remove(ge);
        ConfigurationShutter.getInstance().eraseConfiguration();
    }

    /**
     * Read a List of JPanels to set the GraphicalElement ConfigurationAttribute.
     * This action done when the button validate of the ConfigurationShutter is clicked. 
     * @param panels List of panel to read.
     */
    public void validate(List<JPanel> panels) {
        for(GraphicalElement ge : listGE){
            ConfigurationAttribute ca=null;
            for(int i =0; i<panels.size(); i++){
                ca=ge.getAllAttributes().get(i);
                CAManager.getInstance().getRenderer(ca).extractValue(panels.get(i), ca);
                if(ca instanceof CARefresh){
                    ((CARefresh)ca).refresh();
                }
            }
            ConfigurationShutter.getInstance().close();
            if(ge instanceof GERefresh){
                ((GERefresh)ge).refresh();
            }
            map.get(ge).setPanel(GEManager.getInstance().render(ge.getClass()).render(ge));
        }
    }
    
    /**
     * Returns the list of ConfigurationAttributes that all the selected GraphicalElements have in common.
     * @return List of common ConfigurationAttributes.
     */
    private List<ConfigurationAttribute> getCommonAttributes(){
        try {
            Class<? extends GraphicalElement> c = listGE.get(0).getClass();
            for (GraphicalElement listGE1 : listGE) {
                c = listGE1.getCommonClass(c);
            }
            return c.getConstructor(c).newInstance(listGE.get(0)).getAllAttributes();
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(UIController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
