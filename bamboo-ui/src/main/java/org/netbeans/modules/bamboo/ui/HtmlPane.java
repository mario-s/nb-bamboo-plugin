package org.netbeans.modules.bamboo.ui;

import java.net.URL;
import javax.swing.JEditorPane;
import javax.swing.event.HyperlinkEvent;
import org.openide.awt.HtmlBrowser.URLDisplayer;

/**
 * This component displays HTML content and supplies action for hyperlinks
 *
 * @author spindizzy
 */
public class HtmlPane extends JEditorPane {

    private static final String CONT_TYPE = "text/html";

    public HtmlPane() {
        init();
    }

    private void init() {
        setContentType(CONT_TYPE);
        setEditable(false);
        setOpaque(false);

        //use component font
        putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, true);

        addHyperlinkListener((HyperlinkEvent evt) -> {
            if (HyperlinkEvent.EventType.ACTIVATED.equals(evt.getEventType())) {
                URL url = evt.getURL();
                URLDisplayer.getDefault().showURL(url);
            }
        });
    }
}
