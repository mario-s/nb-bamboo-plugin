package org.netbeans.modules.bamboo.ui;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.SwingConstants;

/**
 * This button looks and feels like a hyper link.
 *
 * @author spindizzy
 */
public class LinkButton extends JButton {
    
    protected static final String HREF = "<html><a href=\"#\">";

    public LinkButton(String text) {
        super(HREF + text);
        init();
    }

    private void init() {
        setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        setBorderPainted(false);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setFocusable(false);
        setHorizontalAlignment(SwingConstants.LEFT);
    }

}
