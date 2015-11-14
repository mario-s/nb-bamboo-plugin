package org.netbeans.modules.bamboo.model;

/**
 *
 * @author spindizzy
 */
public final class DefaultBambooInstance implements BambooInstance{

    private String name;

    private String url;

    public DefaultBambooInstance() {
    }

    public DefaultBambooInstance(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    
    
}
