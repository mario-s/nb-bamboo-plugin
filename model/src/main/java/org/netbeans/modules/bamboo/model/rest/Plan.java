package org.netbeans.modules.bamboo.model.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;
import org.netbeans.modules.bamboo.model.PlanType;

@Data
@JsonRootName(value = "plan")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Plan {

    private String key;
    private Link link;
    private String name;
    private String shortKey;
    private String shortName;
    private transient PlanType type;
    private transient boolean enabled;
    private transient Result result;
    
}
