package com.mapcomposer.model.graphicalelement.element;

import com.mapcomposer.model.graphicalelement.interfaces.AlwaysOnBack;

public class Document extends SimpleDocumentGE implements AlwaysOnBack{
    
    /**Main constructor.*/
    public Document() {
        super();
    }
    
    /**
     * Copy constructor
     * @param sdge SimpleDocumentGE to copy
     */
    public Document(SimpleDocumentGE sdge) {
        super(sdge);
    }
}
