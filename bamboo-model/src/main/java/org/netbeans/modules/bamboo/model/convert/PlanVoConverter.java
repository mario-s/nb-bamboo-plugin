package org.netbeans.modules.bamboo.model.convert;

import org.netbeans.modules.bamboo.model.rcp.PlanVo;
import org.netbeans.modules.bamboo.model.rest.Plan;

/**
 *
 * @author spindizzy
 */
public class PlanVoConverter implements VoConverter<Plan, PlanVo> {
    
    private final String serverUrl;

    public PlanVoConverter(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    @Override
    public PlanVo convert(Plan src) {
        PlanVo target = new PlanVo(src.getKey(), src.getName());
        target.setShortKey(src.getShortKey());
        target.setShortName(src.getShortName());
        target.setEnabled(src.isEnabled());
        target.setType(src.getType());
        target.setServerUrl(serverUrl);
        return target;
    }
    
}
