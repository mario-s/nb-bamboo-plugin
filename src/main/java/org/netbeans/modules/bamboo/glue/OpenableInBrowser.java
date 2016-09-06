package org.netbeans.modules.bamboo.glue;

/**
 * Interface to open a the object behind a node in a browser.
 * @author spindizzy
 */
public interface OpenableInBrowser {
    String BROWSE = "%s/browse/%s";
    
    /**
     * URL of the instance
     *
     * @return instance url
     */
    String getUrl();
}
