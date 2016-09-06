package org.netbeans.modules.bamboo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;


@Data
@JsonRootName(value = "plan")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Plan {
    private String key;
    private Link link;
    private String name;
    private String shortKey;
    private String shortName;
    private String type;
    private transient boolean enabled;
}
