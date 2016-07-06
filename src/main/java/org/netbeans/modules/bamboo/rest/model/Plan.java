package org.netbeans.modules.bamboo.rest.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonRootName(value = "plan")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Plan {
    private String shortName;
    private String shortKey;
    private String type;
    private boolean enabled;
    private Link link;
    private String key;
    private String name;

}
