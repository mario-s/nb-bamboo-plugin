package org.netbeans.modules.bamboo.ui.nodes;

import java.awt.Component;
import java.awt.event.ActionListener;
import java.beans.PropertyEditor;
import java.beans.PropertyEditorSupport;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import org.netbeans.modules.bamboo.glue.LinkComponentProduceable;
import org.openide.explorer.propertysheet.ExPropertyEditor;
import org.openide.explorer.propertysheet.InplaceEditor;
import org.openide.explorer.propertysheet.PropertyEnv;
import org.openide.explorer.propertysheet.PropertyModel;

import static org.openide.util.Lookup.getDefault;

/**
 *
 * @author spindizzy
 */
public class BuildReasonEditor extends PropertyEditorSupport implements ExPropertyEditor, 
        InplaceEditor.Factory {
    
    private InplaceEditor inplaceEditor;

    private final LinkComponentProduceable linkComponentProducer;

    public BuildReasonEditor() {
        this.linkComponentProducer = getDefault().lookup(LinkComponentProduceable.class);
    }

    @Override
    public void attachEnv(PropertyEnv env) {
        env.registerInplaceEditorFactory(this);
    }

    @Override
    public Component getCustomEditor() {
        String txt = getAsText();
        return linkComponentProducer.create(txt);
    }

    @Override
    public InplaceEditor getInplaceEditor() {
        if(inplaceEditor == null){
            inplaceEditor = new Inplace(linkComponentProducer);
        }
        return inplaceEditor;
    }

    @Override
    public boolean supportsCustomEditor() {
        return true;
    }
    
    private static class Inplace implements InplaceEditor {
        
        private PropertyEditor editor;
        
        private PropertyModel model;
        
        private Object value;
        
        private final LinkComponentProduceable componentProducer;

        Inplace(LinkComponentProduceable componentProducer) {
            this.componentProducer = componentProducer;
        }

        @Override
        public void clear() {
        }

        @Override
        public void connect(PropertyEditor pe, PropertyEnv env) {
            this.editor = pe;
        }

        @Override
        public JComponent getComponent() {
            String txt = value.toString();
            return componentProducer.create(txt);
        }

        @Override
        public KeyStroke[] getKeyStrokes() {
            return new KeyStroke[0]; 
        }

        @Override
        public PropertyEditor getPropertyEditor() {
            return editor;
        }

        @Override
        public PropertyModel getPropertyModel() {
            return model;
        }

        @Override
        public void setPropertyModel(PropertyModel pm) {
            this.model = pm;
        }

        @Override
        public Object getValue() {
            return value;
        }

        @Override
        public void setValue(Object o) {
            this.value = o;
        }

        @Override
        public boolean isKnownComponent(Component c) {
           return c instanceof JComponent;
        }
        
         @Override
        public void addActionListener(ActionListener al) {
        }

        @Override
        public void removeActionListener(ActionListener al) {
        }

        @Override
        public void reset() {
        }

        @Override
        public boolean supportsTextEntry() {
            return true;
        }
    }
}
