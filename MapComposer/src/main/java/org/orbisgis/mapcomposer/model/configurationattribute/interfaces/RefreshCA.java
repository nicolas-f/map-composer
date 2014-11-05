/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.orbisgis.mapcomposer.model.configurationattribute.interfaces;

import org.orbisgis.mapcomposer.controller.UIController;

/**
 * Interface for the ConfigurationAttribute to define the refresh method that will be called to refresh the value contained.
 */
public interface RefreshCA {
    /**
     * Refresh function.
     */
    public void refresh(UIController uic);
}