package org.netbeans.modules.bamboo.rest;

import org.netbeans.modules.bamboo.rest.model.Plan;
import org.netbeans.modules.bamboo.glue.PlansProvideable;
import java.util.List;
import org.netbeans.modules.bamboo.glue.InstanceValues;

import java.util.prefs.Preferences;

/**
 * @author spindizzy
 */
public final class DefaultBambooInstance extends DefaultInstanceValues implements PlansProvideable {

    private List<Plan> plans;

    /**
     * Use serialVersionUID for interoperability.
     */
    private static final long serialVersionUID = 1L;

    private int sync;

    private BambooInstanceProperties properties;

    public DefaultBambooInstance() {
    }

    public DefaultBambooInstance(final InstanceValues values) {
        super(values);
    }

    @Override
    public Preferences getPreferences() {
        return properties.getPreferences();
    }

    public void setProperties(final BambooInstanceProperties properties) {
        this.properties = properties;
        updateFields(properties);
    }

    private void updateFields(final BambooInstanceProperties props) throws NumberFormatException {
        setName(props.get(BambooInstanceConstants.INSTANCE_NAME));
        setUrl(props.get(BambooInstanceConstants.INSTANCE_URL));

        String syncProp = props.get(BambooInstanceConstants.INSTANCE_SYNC);

        if (syncProp != null) {
            this.sync = Integer.parseInt(syncProp);
        }
    }

    @Override
    public List<Plan> getPlans() {
        return plans;
    }

    @Override
    public void setPlans(List<Plan> plans) {
        this.plans = plans;
    }
}
