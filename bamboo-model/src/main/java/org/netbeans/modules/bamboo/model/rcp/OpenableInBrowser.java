package org.netbeans.modules.bamboo.model.rcp;

/**
 * Interface to open a the object behind a node in a browser.
 * @author spindizzy
 */
public interface OpenableInBrowser extends Availability{
    String BROWSE = "%s/browse/%s";
    
    /**
     * URL of the instance
     *
     * @return instance url
     */
    String getUrl();
    
}
