package org.netbeans.modules.bamboo.model;

public class Link {
    private String href;
    private String rel;

    public String getHref() {
        return href;
    }

    public String getRel() {
        return rel;
    }

    public void setHref(final String href) {
        this.href = href;
    }

    public void setRel(final String rel) {
        this.rel = rel;
    }
}
