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
package org.netbeans.modules.bamboo.client.glue;

import org.netbeans.modules.bamboo.model.rcp.VersionInfo;
import java.util.Collection;
import org.netbeans.modules.bamboo.model.rcp.ProjectVo;

/**
 * This interface defines methods to access the Bamboo server.
 *
 * @author Mario Schroeder
 */
public interface BambooClient {

    /**
     * Return a collection of available {@link ProjectVo}.
     *
     * @return the projects.
     */
    Collection<ProjectVo> getProjects();

    /**
     * This method returns information about the Bamboo server.
     *
     * @return information about the version.
     */
    VersionInfo getVersionInfo();

}
