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
package org.netbeans.modules.bamboo.model.convert;

import java.time.LocalDateTime;
import java.util.Optional;
import static jodd.bean.BeanCopy.beans;

/**
 *
 * @author Mario Schroeder
 */
abstract class AbstractVoConverter<S, T> implements VoConverter<S, T> {

    protected Optional<LocalDateTime> toDate(String str) {
        return new LocalDateTimeConverter().convert(str);
    }
    
    /**
     * Copies properties from source to target.
     * @param source source object
     * @param target target object
     */
    protected void copyProperties(S source, T target) {
        beans(source, target).copy();
    }
   
}
