package org.netbeans.modules.bamboo.ui.nodes;

import java.awt.Component;
import java.beans.PropertyEditorSupport;
import org.netbeans.modules.bamboo.glue.LinkComponentProduceable;
import org.netbeans.modules.bamboo.glue.TextExtractable;

import static org.openide.util.Lookup.getDefault;

/**
 *
 * @author spindizzy
 */
public class BuildReasonEditor extends PropertyEditorSupport {

    private final TextExtractable textExtractor;

    private final LinkComponentProduceable linkComponentProducer;

    public BuildReasonEditor() {
        this.textExtractor = getDefault().lookup(TextExtractable.class);
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
            String link = textExtractor.extractLink(text);
            String prefix = textExtractor.substring(text, link);
            String suffix = textExtractor.extractNormalText(link);
            text = prefix + suffix;
        }
        return text;
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
