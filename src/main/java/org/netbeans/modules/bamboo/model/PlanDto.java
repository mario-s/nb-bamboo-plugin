package org.netbeans.modules.bamboo.model;

import lombok.Data;

/**
 * This class is the plan related to a project.
 * @author spindizzy
 */
@Data
public class PlanDto {
    
    private String key;
    private String name;
    private String shortKey;
    private String shortName;
    private boolean enabled;
    
    private ResultVo result;

    private transient PlanType type;
    
}
