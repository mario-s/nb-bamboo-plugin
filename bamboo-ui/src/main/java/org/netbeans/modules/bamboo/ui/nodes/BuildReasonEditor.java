package org.netbeans.modules.bamboo.ui.nodes;

import java.awt.Component;
import java.beans.PropertyEditorSupport;
import org.netbeans.modules.bamboo.ui.HtmlPane;
import org.netbeans.modules.bamboo.util.TextExtractor;

/**
 * This is a custom editor for the build reason of a plan.
 * 
 * @author spindizzy
 */
public class BuildReasonEditor extends PropertyEditorSupport {

    private final TextExtractor textExtractor;

    private final BuildResultPanel buildResultPanel;
    
    private final HtmlPane pane;

    public BuildReasonEditor() {
        textExtractor = new TextExtractor();
        pane = new HtmlPane();
        buildResultPanel = new BuildResultPanel(pane);
    }

    @Override
    public Component getCustomEditor() {
        String txt = super.getAsText();
        pane.setText(txt);
        return buildResultPanel;
    }

    @Override
    public String getAsText() {
        return textExtractor.removeTags(super.getAsText());
    }

    @Override
    public boolean supportsCustomEditor() {
        return true;
    }
}
