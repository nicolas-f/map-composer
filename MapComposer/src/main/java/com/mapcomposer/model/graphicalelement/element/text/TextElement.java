package com.mapcomposer.model.graphicalelement.element.text;

import com.mapcomposer.model.configurationattribute.interfaces.ConfigurationAttribute;
import java.util.List;

public final class TextElement extends SimpleTextGE {
    
    /**Main constructor.*/
    public TextElement() {
        super();
    }
    
    /**Copy constructor.*/
    public TextElement(SimpleTextGE stge) {
        super(stge);
    }
    
    @Override
    public List<ConfigurationAttribute> getAllAttributes() {
        return super.getAllAttributes();
    }
}
