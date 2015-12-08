package org.netbeans.modules.bamboo;

/**
 * A wrapper for all parameters to be used to create a new BambooInstance
 * @author spindizzy
 */
public class InstanceValues {
    
    private String name;
    
    private String url;
    
    private int syncTime;

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

    public int getSyncTime() {
        return syncTime;
    }

    public void setSyncTime(int syncTime) {
        this.syncTime = syncTime;
    }
    
    
}
