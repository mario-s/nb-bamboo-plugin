package org.netbeans.modules.bamboo.ui.ext;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.JLabel;
import org.openide.awt.HtmlBrowser.URLDisplayer;

/**
 * A {@link JLabel} behaving like an HTML hyperlink usually found on web applications. By default, the style of the link
 * is mimicking that found by default in web browsers.
 */
final class LinkLabel extends JLabel {

    private final String text;
    private final URL url;

    private boolean mouseEntered = false;
    private Color colorBeforeClick = Color.blue;
    private Color colorAfterClick = new Color(128, 0, 128); // purple

    /**
     * Constructor.
     * <p>
     * Constructs a {@link URL} object from the <code>url</code> argument. Any {@link MalformedURLException} thrown is
     * converted to {@link IllegalArgumentException} if you cannot be sure at compile time that your url is valid, construct
     * your url manually and use the other constructor.
     *
     * @param text text/label of the link
     * @param uri {@link URL} to which the link points to
     */
    LinkLabel(String text, String url) {
        try {
            this.text = text;
            this.url = new URL(url);
            init();
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * Constructor.
     *
     * @param text text/label for the link
     * @param uri {@link URL} to which the link points to
     */
    LinkLabel(String text, URL url) {
        this.text = text;
        this.url = url;
        init();
    }

    /**
     * Sets up the link style, colour and mouse behaviour
     */
    private void init() {

        setText(text);
        setToolTipText(this.url.toString());
        setForeground(colorBeforeClick);

        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                open(url);
                setForeground(colorAfterClick);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setText(text);
                mouseEntered = true;
                repaint();
                setCursor(new Cursor(Cursor.HAND_CURSOR));

            }

            @Override
            public void mouseExited(MouseEvent e) {
                setText(text);
                mouseEntered = false;
                repaint();
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void paint(Graphics g) {

        super.paint(g);

        if (mouseEntered) {

            // draw a line under the text
            Rectangle r = g.getClipBounds();
            g.drawLine(0, r.height - getFontMetrics(getFont()).getDescent(), getFontMetrics(getFont())
                    .stringWidth(getText()), r.height - getFontMetrics(getFont()).getDescent());
        }
    }

    /**
     * Opens the given {@link URI} in the default browser.
     *
     * @param uri {@link URI} to which the link points to
     */
    private void open(URL url) {
        URLDisplayer.getDefault().showURL(url);
    }

}
