package org.netbeans.modules.bamboo.rest;

import org.netbeans.modules.bamboo.glue.DefaultInstanceValues;
import org.netbeans.modules.bamboo.rest.model.Plan;
import org.netbeans.modules.bamboo.glue.PlansProvideable;
import java.util.List;
import org.netbeans.modules.bamboo.glue.InstanceValues;

import java.util.prefs.Preferences;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import org.netbeans.modules.bamboo.glue.ResultsProvideable;
import org.netbeans.modules.bamboo.rest.model.Result;

/**
 * @author spindizzy
 */
@Getter
@Setter
public final class DefaultBambooInstance extends DefaultInstanceValues implements ResultsProvideable {

    private List<Plan> plans;
    
    private List<Result> results;

    /**
     * Use serialVersionUID for interoperability.
     */
    private static final long serialVersionUID = 1L;

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

    @Override
    public void remove() {
        properties.clear();
    }

    public void setProperties(final BambooInstanceProperties properties) {
        this.properties = properties;
        updateFields(properties);
    }

    private void updateFields(final BambooInstanceProperties props) throws NumberFormatException {
        setName(props.get(BambooInstanceConstants.INSTANCE_NAME));
        setUrl(props.get(BambooInstanceConstants.INSTANCE_URL));

        String syncProp = props.get(BambooInstanceConstants.INSTANCE_SYNC);

        if (isNotBlank(syncProp)) {
            setSyncInterval(Integer.parseInt(syncProp));
        }
    }
}
