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
import java.beans.PropertyChangeSupport;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.netbeans.api.annotations.common.NonNull;

/**
 *
 * @author Mario Schroeder
 */
@EqualsAndHashCode(of = "key")
public abstract class AbstractVo {
    
    @Getter
    private final String key;
    
    private final PropertyChangeSupport changeSupport;

    public AbstractVo(@NonNull String key) {
        this.key = key;
        this.changeSupport = new PropertyChangeSupport(this);
    }
    
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }
    
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }

    public void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
        changeSupport.firePropertyChange(propertyName, oldValue, newValue);
    }     
}
