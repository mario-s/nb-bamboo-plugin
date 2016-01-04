package org.netbeans.modules.bamboo.rest;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Link {

    private String href;
    private String rel;

    public String getHref() {
        return href;
    }

    public String getRel() {
        return rel;
    }
}
