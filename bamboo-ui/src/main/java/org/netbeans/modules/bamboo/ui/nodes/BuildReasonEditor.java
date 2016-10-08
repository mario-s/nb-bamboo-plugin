package org.netbeans.modules.bamboo.ui.nodes;

import java.awt.Component;
import java.beans.PropertyEditorSupport;
import org.netbeans.modules.bamboo.ui.HtmlPane;
import org.netbeans.modules.bamboo.util.TextExtractor;


/**
 *
 * @author spindizzy
 */
public class BuildReasonEditor extends PropertyEditorSupport {

    private final TextExtractor textExtractor;
    
    private final HtmlPane pane;

    public BuildReasonEditor() {
        textExtractor = new TextExtractor();
        pane = new HtmlPane();
    }

    @Override
    public Component getCustomEditor() {
        String txt = super.getAsText();
        pane.setText(txt);
        return pane;
    }

    @Override
    public String getAsText() {
        String text = super.getAsText();
        if(containsLink(text)){
            text = buildText(text);
        }
        return text;
    }

    private String buildText(String text) {
        String link = textExtractor.extractLink(text);
        StringBuilder builder = new StringBuilder();
        builder.append(textExtractor.substring(text, link));
        builder.append(textExtractor.extractNormalText(link));
        return textExtractor.removeTags(builder.toString());
    }

    @Override
    public boolean supportsCustomEditor() {
        return true;
    }

    private boolean containsLink(String txt) {
        return textExtractor.containsLink(txt);
    }

}
