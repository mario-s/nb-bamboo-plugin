package org.netbeans.modules.bamboo.rest;

import org.netbeans.modules.bamboo.rest.model.AllPlansResponse;
import java.util.Collection;
import org.netbeans.modules.bamboo.glue.PlansProvideable;
import org.netbeans.modules.bamboo.glue.InstanceValues;
import java.util.prefs.Preferences;
import org.openide.util.Lookup;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 */
@ServiceProvider(service = BambooInstanceProduceable.class)
public class BambooInstanceFactory implements BambooInstanceProduceable{
    
    private BambooServiceAccessable instanceAccessor;

    public BambooInstanceFactory() {
        initClient();
    }
    
     private void initClient() {
        Collection<? extends BambooServiceAccessable> services = Lookup.getDefault().lookupAll(BambooServiceAccessable.class);

        // simply take the first one, in test environment it is the mock client
        this.instanceAccessor = services.iterator().next();
    }
    
    @Override
    public PlansProvideable create(InstanceValues values) {
        DefaultBambooInstance instance = new DefaultBambooInstance(values);
        BambooInstanceProperties props = new BambooInstanceProperties(instancesPrefs());
        props.copyProperties(instance);
        instance.setProperties(props);
        
        AllPlansResponse all = instanceAccessor.getAllPlans(instance);
        instance.setPlans(all.getPlans().getPlan());
        
        return instance;
    }
    
    Preferences instancesPrefs() {
        return PreferenceWrapper.instancesPrefs();
    }
}
