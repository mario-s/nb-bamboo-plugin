package org.netbeans.modules.bamboo.ui.nodes;

import java.awt.Component;
import java.beans.PropertyEditorSupport;
import org.netbeans.modules.bamboo.glue.LinkComponentProduceable;
import org.netbeans.modules.bamboo.util.TextExtractor;

import static org.openide.util.Lookup.getDefault;

/**
 *
 * @author spindizzy
 */
public class BuildReasonEditor extends PropertyEditorSupport {

    private final TextExtractor textExtractor;

    private final LinkComponentProduceable linkComponentProducer;

    public BuildReasonEditor() {
        this.textExtractor = new TextExtractor();
        this.linkComponentProducer = getDefault().lookup(LinkComponentProduceable.class);
    }

    @Override
    public Component getCustomEditor() {
        String txt = super.getAsText();
        return linkComponentProducer.create(txt);
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
        String txt = super.getAsText();
        return containsLink(txt);
    }

    private boolean containsLink(String txt) {
        return textExtractor.containsLink(txt);
    }

}
