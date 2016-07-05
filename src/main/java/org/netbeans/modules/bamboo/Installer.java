package org.netbeans.modules.bamboo;


import org.openide.util.Lookup;

import org.openide.windows.OnShowing;


@OnShowing
public class Installer implements Runnable {
    @Override
    public void run() {
        Lookup.getDefault().lookup(InstanceManageable.class).loadInstances();
    }
}
