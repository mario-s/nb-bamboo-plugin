package org.netbeans.modules.bamboo;

import org.openide.windows.OnShowing;

@OnShowing
public class Installer implements Runnable{

    @Override
    public void run() {
        BambooManager.loadInstances();
    }

}