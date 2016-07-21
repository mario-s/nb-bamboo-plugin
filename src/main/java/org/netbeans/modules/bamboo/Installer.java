package org.netbeans.modules.bamboo;


import java.util.Collection;
import org.netbeans.modules.bamboo.glue.BambooInstance;
import org.netbeans.modules.bamboo.glue.InstanceManageable;
import org.openide.util.Lookup;

import org.openide.windows.OnShowing;


@OnShowing
public final class Installer implements Runnable {
    
    private InstanceManageable manager;
    
    public Installer() {
         manager = Lookup.getDefault().lookup(InstanceManageable.class);
    }

    
    @Override
    public void run() {
        Collection<BambooInstance> instances = manager.loadInstances();
        //TODO call CI server
        instances.forEach(instance -> manager.addInstance(instance));
    }

    
}
