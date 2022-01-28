/* 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.netbeans.modules.bamboo.model.rcp;

import java.beans.PropertyChangeListener;

/**
 * Interface which will return the necessary values for a connection to the CI
 * server.
 * 
 * @author Mario Schroeder
 */
public interface InstanceValues extends OpenableInBrowser {

    void addPropertyChangeListener(PropertyChangeListener listener);
    /**
     * Name of the Bamboo instance.
     *
     * @return instance name
     */
    String getName();
    
    /**
     * Indicates whether the authentication method is based on token or not.
     * @return <code>true</code> when using a token, <code>false</code> when not. 
     */
    @Deprecated
    boolean isUseToken();
    
    /**
     * Get the token of the user for authentication.
     * 
     * @return token as characters
     */
    char[] getToken();

    /**
     * @return Synchronization interval in minutes.
     */
    int getSyncInterval();
    
    /**
     * Get the user's name used for authentication.
     *
     * @return user name
     */
    @Deprecated
    String getUsername();

    /**
     * Get the password of the user.
     *
     * @return password as characters
     */
    @Deprecated
    char[] getPassword();
    
    @Override
    default boolean isAvailable() {
        return false;
    }

    void removePropertyChangeListener(PropertyChangeListener listener);
}
