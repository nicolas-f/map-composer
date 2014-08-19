package com.mapcomposer.model.graphicalelement.element.cartographic;

import com.mapcomposer.model.configurationattribute.ConfigurationAttribute;
import com.mapcomposer.model.graphicalelement.GraphicalElement;
import com.mapcomposer.model.graphicalelement.utils.GERefresh;
import java.awt.image.BufferedImage;
import java.util.List;
import org.orbisgis.coremap.map.MapTransform;
import org.orbisgis.progress.NullProgressMonitor;

/**
 * Map image generated from an OWS-Context.
 */
public class MapImage extends CartographicElement implements GERefresh{
    
    private BufferedImage image = null;

    public MapImage() {
        super();
    }
    
    /**
     * Clone constructor.
     * @param ge
     */
    public MapImage(MapImage ge){
        super(ge);
        image = ge.image;
    }
    
    @Override
    public List<ConfigurationAttribute> getAllAttributes() {
        return super.getAllAttributes();
    }

    @Override
    public void refresh() {
        MapTransform mapTransform = new MapTransform();
        mapTransform.setExtent(this.getOwsMapContext().getBoundingBox());
        image = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
        mapTransform.setImage(image);
        this.getOwsMapContext().draw(mapTransform, new NullProgressMonitor());
    }
    
    public BufferedImage getImage(){
        return image;
    }
    
    @Override
    public Class<? extends GraphicalElement> getCommonClass(Class<? extends GraphicalElement> c) {
        if(c.isAssignableFrom(this.getClass()))
            return c;
        else if(c.isAssignableFrom(CartographicElement.class))
            return CartographicElement.class;
        else
            return GraphicalElement.class;
    }
}
