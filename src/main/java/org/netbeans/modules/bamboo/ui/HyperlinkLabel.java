package org.netbeans.modules.bamboo.ui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.JLabel;

/**
 * A {@link JLabel} behaving like an HTML hyperlink usually found on web applications. By default, the style of the link
 * is mimicking that found by default in web browsers.
 */
public class HyperlinkLabel extends JLabel {

    private String text;
    private URI uri;

    private boolean mouseEntered = false;
    private boolean underlinedWhenHovered = true;
    private boolean handCursorWhenHovered = true;
    private Color colorBeforeClick = Color.blue;
    private Color colorAfterClick = new Color(128, 0, 128); // purple

    /**
     * Constructor.
     *
     * @param text text/label for the link
     * @param uri {@link URI} to which the link points to
     */
    public HyperlinkLabel(String text, URI uri) {
        super();
        setup(text, uri);
    }

    /**
     * Constructor.
     * <p>
     * Constructs a {@link URI} object from the <code>uri</code> argument. Any {@link URISyntaxException} thrown is
     * converted to {@link RuntimeException} if you cannot be sure at compile time that your uri is valid, construct
     * your uri manually and use the other constructor.
     *
     * @param text text/label of the link
     * @param uri {@link URI} to which the link points to
     */
    public HyperlinkLabel(String text, String uri) {
        super();
        URI oURI;
        try {
            oURI = new URI(uri);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        setup(text, oURI);
    }

    /**
     * Sets the colour before the link has been clicked.
     *
     * @param color {@link Color}
     */
    public void setColorBeforeClick(Color color) {
        this.colorBeforeClick = color;
        setForeground(color);
    }

    /**
     * Sets the colour once the link has been clicked.
     *
     * @param color {@link Color}
     */
    public void setColorAfterClick(Color color) {
        this.colorAfterClick = color;
    }

    /**
     * Sets up the link style, colour and mouse behaviour
     *
     * @param text text/label of the link
     * @param uri {@link URI} to which the link points to
     */
    private void setup(final String text, final URI uri) {

        this.text = text;
        this.uri = uri;

        setText(text);
        setToolTipText(uri.toString());
        setForeground(colorBeforeClick);

        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                open(uri);
                setForeground(colorAfterClick);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setText(text);
                mouseEntered = true;
                repaint();
                if (handCursorWhenHovered) {
                    setCursor(new Cursor(Cursor.HAND_CURSOR));
                }

            }

            @Override
            public void mouseExited(MouseEvent e) {
                setText(text);
                mouseEntered = false;
                repaint();
                if (handCursorWhenHovered) {
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }

            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void paint(Graphics g) {

        super.paint(g);

        if (underlinedWhenHovered && mouseEntered) {

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
    private void open(URI uri) {
        throw new UnsupportedOperationException("not yet implemented");
    }

}
