package org.netbeans.modules.bamboo.model;

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

    public void setShortName(final String shortName) {
        this.shortName = shortName;
    }

    public void setShortKey(final String shortKey) {
        this.shortKey = shortKey;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }

    public void setLink(final Link link) {
        this.link = link;
    }

    public void setKey(final String key) {
        this.key = key;
    }

    public void setName(final String name) {
        this.name = name;
    }
}
