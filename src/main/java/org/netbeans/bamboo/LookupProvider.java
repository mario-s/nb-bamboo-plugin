package org.netbeans.bamboo;

import org.openide.util.Lookup;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;

/**
 * Glue to interact.
 * @author spindizzy
 */
public enum LookupProvider implements Lookup.Provider{
    
    Instance;
    
    private final Lookup lookup;
    private final InstanceContent content;
    
    private LookupProvider() {
        content = new InstanceContent();
        lookup = new AbstractLookup(content);
    }

    @Override
    public Lookup getLookup() {
        return lookup;
    }

    public InstanceContent getContent() {
        return content;
    }
}
