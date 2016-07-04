package org.netbeans.modules.bamboo.rest;

/**
 * @author spindizzy
 */
abstract class AbstractAllResponse {
    private String expand;
    private Link link;

    public String getExpand() {
        return expand;
    }

    public Link getLink() {
        return link;
    }

    public void setExpand(final String expand) {
        this.expand = expand;
    }

    public void setLink(final Link link) {
        this.link = link;
    }
}
