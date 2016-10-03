package org.netbeans.modules.bamboo.glue;

import javax.swing.JComponent;

/**
 * this interfaces describes a way to create a {@link JComponent} with support for Hyperlinks.
 * @author spindizzy
 */
public interface LinkComponentProduceable {
    /**
     * This method creates a component which supports a click event on links.
     * 
     * @param text the text which may contain a HTML link
     * @return a component with or without support for HTML links.
     */
    JComponent create(String text);
    
    /**
     * This method returns true if the text contains a link otherwhise false.
     * @param text the text which may contain a HTML link
     * @return true if the text contains a link otherwhise false.
     */
    boolean containsLink(String text);
}
