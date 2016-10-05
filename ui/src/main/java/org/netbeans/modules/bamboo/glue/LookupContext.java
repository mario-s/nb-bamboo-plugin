package org.netbeans.modules.bamboo.glue;

import org.openide.util.Lookup;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;

/**
 *
 * @author spindizzy
 */
public enum LookupContext implements Lookup.Provider{
    Instance;
    
    private final InstanceContent content;
    private final Lookup lookup;
    
    private LookupContext() {
        this.content = new InstanceContent();
        this.lookup = new AbstractLookup(content);
    }

    @Override
    public Lookup getLookup() {
        return lookup;
    }
    
    public InstanceContent getContent(){
        return content;
    }
    
    public void add(Object inst) {
        content.add(inst);
    }
    
    public void remove(Object inst) {
        content.remove(inst);
    }
}
