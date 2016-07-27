package org.netbeans.modules.bamboo;

import org.netbeans.modules.bamboo.glue.BambooInstance;
import org.netbeans.modules.bamboo.glue.InstanceManageable;
import org.netbeans.modules.bamboo.rest.BambooServiceAccessable;
import org.netbeans.modules.bamboo.rest.DefaultBambooInstance;
import org.netbeans.modules.bamboo.rest.model.Plans;
import org.netbeans.modules.bamboo.rest.model.PlansResponse;

import static org.openide.util.Lookup.getDefault;

import org.openide.windows.OnShowing;

import java.util.Collection;


@OnShowing
public final class Installer implements Runnable {
    @Override
    public void run() {
        InstanceManageable manager = getDefault().lookup(InstanceManageable.class);
        Collection<BambooInstance> instances = manager.loadInstances();

        if (!instances.isEmpty()) {
            BambooServiceAccessable client = getDefault().lookup(BambooServiceAccessable.class);

            instances.forEach(instance -> {
                    PlansResponse all = client.getAllPlans(instance);
                    Plans plans = all.getPlans();

                    if (plans != null) {
                        //TODO use a dto in the glue and add setter to interface
                        ((DefaultBambooInstance) instance).setPlans(plans.getPlan());
                    }

                    manager.addInstance(instance);
                });
        }
    }
}
