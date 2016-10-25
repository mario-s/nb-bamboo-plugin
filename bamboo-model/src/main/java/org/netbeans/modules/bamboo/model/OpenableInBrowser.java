package org.netbeans.modules.bamboo.model;

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
    
        /**
     * This method returns <code>true</code> when the url could be reached, if not it returns <code>false</code>.
     *
     * @return <code>true</code> when server is present, otherwhise <code>false</code>
     */
    boolean isAvailable();
}
