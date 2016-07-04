package org.netbeans.modules.bamboo.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonRootName;


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

    public String getShortName() {
        return shortName;
    }

    public String getShortKey() {
        return shortKey;
    }

    public String getType() {
        return type;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public Link getLink() {
        return link;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }
}
