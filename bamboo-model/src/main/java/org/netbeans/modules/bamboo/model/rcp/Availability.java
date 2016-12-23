package org.netbeans.modules.bamboo.model.rcp;

/**
 * this interface defines a way to determine if a endpoint is available
 * 
 * @author spindizzy
 */
public interface Availability {

    /**
     * This method returns <code>true</code> when the URL could be reached, if not it returns <code>false</code>.
     *
     * @return <code>true</code> when server is present, otherwhise <code>false</code>
     */
    boolean isAvailable();
    
}
