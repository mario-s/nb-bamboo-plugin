package org.netbeans.modules.bamboo.ui.ext;

import javax.swing.JComponent;
import javax.swing.JLabel;

/**
 * Factory class that creates a new {@link JComponent}.
 * @author spindizzy
 */
public class ComponentFactory {
    
    private final TextExtractor textExtractor;

    public ComponentFactory() {
        this.textExtractor = new TextExtractor();
    }
    
    /**
     * This method creates a subclass of JComponent depending on the given text.
     * @param text text with possible HTML markup.
     * @return a new {@link JComponent}.
     */
    public JComponent create(String text) {
        String link = textExtractor.extractLink(text);
        if(!link.isEmpty()){
            String txt = textExtractor.extractNormalText(link);
            String url = textExtractor.extractUrl(link);
            return new LinkLabel(txt, url);
        }
        return new JLabel(text);
    }
}
