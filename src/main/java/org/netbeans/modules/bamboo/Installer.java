package org.netbeans.modules.bamboo;


import org.netbeans.modules.bamboo.glue.InstanceManageable;
import org.netbeans.modules.bamboo.glue.PlansProvideable;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;

import org.openide.windows.OnShowing;


@OnShowing
public final class Installer implements Runnable, LookupListener {
    
    private InstanceManageable manager;
    private Lookup.Result<PlansProvideable> result;
    
    public Installer() {
         manager = Lookup.getDefault().lookup(InstanceManageable.class);
         initLookup();
    }

    private void initLookup() {
        result = manager.getLookup().lookupResult(PlansProvideable.class);
        result.addLookupListener(this);
    }
    
    @Override
    public void run() {
        manager.loadInstances();
    }

    @Override
    public void resultChanged(LookupEvent ev) {
        //TODO call server to creat nodes
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
}
