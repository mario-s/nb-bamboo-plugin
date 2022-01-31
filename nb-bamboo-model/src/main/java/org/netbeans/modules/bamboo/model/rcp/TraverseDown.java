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

import java.util.Collection;
import java.util.Optional;
import org.netbeans.api.annotations.common.NonNull;

/**
 * A way to get the children of the instance.
 * @author Mario Schroeder
 */
public interface TraverseDown<C> {
    
    Collection<C> getChildren();
    
    void setChildren(@NonNull Collection<C> children);
    
    /**
     * This method verifies if the given child is part of the children.
     * @param child child that might be a in the collection of children.
     * @return <code>true</code> when the parameter is a child, otherwhise <code>false</code>
     */
    default boolean isChild(C child) {
        Collection<C> children = getChildren();
        return (children != null && !children.isEmpty()) ? children.contains(child) : false;
    }
    
    /**
     * This method verifies if the given child is part of the children.
     * @param child optional child that might be a in the collection of children.
     * @return <code>true</code> when the parameter is present and is a child, otherwhise <code>false</code>
     */
    default boolean isChild(Optional<C> child) {
        return (child.isPresent()) ? isChild(child.get()) : false;
    }
    
    
  
}
