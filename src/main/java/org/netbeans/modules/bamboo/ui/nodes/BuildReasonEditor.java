package org.netbeans.modules.bamboo.ui.nodes;

import java.awt.Component;
import java.beans.PropertyEditorSupport;
import org.netbeans.modules.bamboo.glue.LinkComponentProduceable;
import org.openide.explorer.propertysheet.ExPropertyEditor;
import org.openide.explorer.propertysheet.PropertyEnv;

import static org.openide.util.Lookup.getDefault;


/**
 *
 * @author spindizzy
 */
public class BuildReasonEditor extends PropertyEditorSupport {
    
    private final LinkComponentProduceable linkComponentProducer;

    public BuildReasonEditor() {
        this.linkComponentProducer = getDefault().lookup(LinkComponentProduceable.class);
    }
    
    @Override
    public boolean supportsCustomEditor() {
        return true;
    }

    @Override
    public Component getCustomEditor() {
        String txt = getAsText();
        return linkComponentProducer.create(txt);
    }
    
    
}
