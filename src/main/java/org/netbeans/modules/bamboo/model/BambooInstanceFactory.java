package org.netbeans.modules.bamboo.model;

import org.netbeans.modules.bamboo.glue.BambooInstance;
import org.netbeans.modules.bamboo.glue.PreferenceWrapper;
import org.netbeans.modules.bamboo.glue.InstanceValues;
import java.util.prefs.Preferences;

/**
 *
 */
class BambooInstanceFactory implements BambooInstanceProduceable{
    
    
    @Override
    public BambooInstance create(InstanceValues values) {
        DefaultBambooInstance instance = new DefaultBambooInstance(values);
        BambooInstanceProperties props = new BambooInstanceProperties(instancesPrefs());
        props.copyProperties(instance);
        instance.setProperties(props);
        return instance;
    }
    
    Preferences instancesPrefs() {
        return PreferenceWrapper.instancesPrefs();
    }
}
