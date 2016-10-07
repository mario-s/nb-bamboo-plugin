package org.netbeans.modules.bamboo.ui.ext;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.openide.util.lookup.ServiceProvider;
import org.netbeans.modules.bamboo.glue.LinkComponentProduceable;
import org.netbeans.modules.bamboo.util.TextExtractor;


/**
 * Factory class that creates a new {@link JComponent}.
 *
 * @author spindizzy
 */
@ServiceProvider(service = LinkComponentProduceable.class)
public class LinkComponentFactory implements LinkComponentProduceable {

    private final TextExtractor textExtractor;

    public LinkComponentFactory() {
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
            String lblTxt = textExtractor.substring(text, link);
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
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        return panel;
    }

    private JLabel newLabel(String text) {
        String txt = textExtractor.removeTags(text);
        return new JLabel(txt);
    }
}
