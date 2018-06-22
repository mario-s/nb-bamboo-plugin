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
package org.netbeans.modules.bamboo.client.rest.call;

import java.util.Optional;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static javax.ws.rs.client.Entity.entity;

/**
 * This interface describes a way to access the API.
 *
 * @author Mario Schroeder
 */
public interface ApiCallable<T> {

    /**
     * This method creates a new target. It is empty if the required fields (url, user, password) are blank.
     *
     * @return a possible new {@link WebTarget}
     */
    Optional<WebTarget> createTarget();

    /**
     * Performs a GET request and returns the expected object of T.
     *
     * @param target the target to be called
     * @return the result
     */
    T doGet(final WebTarget target);

    /**
     * Simple doPost without any values
     *
     * @param target the target to be called
     * @return the response code
     */
    default Response doPost(WebTarget target) {
        Form form = new Form();
        Entity<Form> entity = entity(form, MediaType.WILDCARD_TYPE);
        return target.request().post(entity);
    }
}
