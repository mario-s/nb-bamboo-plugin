package org.netbeans.modules.bamboo.ui.ext;

import javax.swing.JLabel;

/**
 * Factory class that creates a new {@link JLabel}.
 * @author spindizzy
 */
public class LabelFactory {
    
    private final TextExtractor textExtractor;

    public LabelFactory() {
        this.textExtractor = new TextExtractor();
    }
    
    /**
     * This method creates a new JLabel or subclasses, if the text contains a URL.
     * @param text text with possible HTML markup.
     * @return a new {@link JLabel}.
     */
    public JLabel create(String text) {
        throw new UnsupportedOperationException("not implemented yet");
    }
}
