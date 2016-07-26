package org.netbeans.modules.bamboo.rest;

import org.netbeans.modules.bamboo.glue.InstanceValues;
import org.netbeans.modules.bamboo.glue.ResultsProvideable;
import org.netbeans.modules.bamboo.rest.model.Plans;
import org.netbeans.modules.bamboo.rest.model.PlansResponse;

import org.openide.util.Lookup;
import org.openide.util.lookup.ServiceProvider;

import java.util.Collection;
import java.util.prefs.Preferences;


/**
 */
@ServiceProvider(service = BambooInstanceProduceable.class)
public class BambooInstanceFactory implements BambooInstanceProduceable {
    private BambooServiceAccessable instanceAccessor;

    public BambooInstanceFactory() {
        initClient();
    }

    private void initClient() {
        Collection<? extends BambooServiceAccessable> services = Lookup.getDefault().lookupAll(
                BambooServiceAccessable.class);

        // simply take the first one, in test environment it is the mock client
        this.instanceAccessor = services.iterator().next();
    }

    @Override
    public ResultsProvideable create(final InstanceValues values) {
        DefaultBambooInstance instance = new DefaultBambooInstance(values);
        BambooInstanceProperties props = new BambooInstanceProperties(instancesPrefs());
        props.copyProperties(instance);

        //TODO use results
        PlansResponse all = instanceAccessor.getAllPlans(instance);
        Plans plans = all.getPlans();

        if (plans != null) {
            instance.setPlans(plans.getPlan());
        }

        return instance;
    }

    Preferences instancesPrefs() {
        return PreferenceWrapper.instancesPrefs();
    }
}
