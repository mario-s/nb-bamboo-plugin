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

import org.netbeans.modules.bamboo.model.rcp.VersionInfo;
import org.netbeans.modules.bamboo.model.rest.Info;

/**
 *
 * @author Mario Schroeder
 */
public class VersionInfoConverter extends AbstractVoConverter<Info, VersionInfo> {
    
    @Override
    public VersionInfo convert(Info src) {
        VersionInfo target = VersionInfo.builder().version(src.getVersion()).buildNumber(src.getBuildNumber()).build();
        final String buildDate = src.getBuildDate();
        toDate(buildDate).ifPresent((date) -> target.setBuildDate(date));
        return target;
    }
    
}
