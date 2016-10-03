package org.netbeans.modules.bamboo.ui.ext;

import java.awt.GridLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.netbeans.modules.bamboo.glue.ComponentProduceable;
import org.openide.util.lookup.ServiceProvider;

/**
 * Factory class that creates a new {@link JComponent}.
 *
 * @author spindizzy
 */
@ServiceProvider(service = ComponentProduceable.class)
public class ComponentFactory implements ComponentProduceable {

    private final TextExtractor textExtractor;

    public ComponentFactory() {
        this.textExtractor = new TextExtractor();
    }

    /**
     * This method creates a subclass of JComponent depending on the given text.
     *
     * @param text text with possible HTML markup.
     * @return a new {@link JComponent}.
     */
    @Override
    public JComponent create(String text) {
        String link = textExtractor.extractLink(text);
        if (!link.isEmpty()) {
            int pos = text.indexOf(link);
            String lblTxt = text.substring(0, pos);
            String txt = textExtractor.extractNormalText(link);
            String url = textExtractor.extractUrl(link);
            
            JPanel panel = newPanel();
            panel.add(newLabel(lblTxt));
            panel.add(new LinkLabel(txt, url));
            
            return panel;
        }
        return newLabel(text);
    }

    private JPanel newPanel() {
        return new JPanel(new GridLayout(1, 2));
    }

    private JLabel newLabel(String text) {
        return new JLabel(text);
    }
}
