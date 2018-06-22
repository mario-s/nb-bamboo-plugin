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

import org.netbeans.modules.bamboo.model.rcp.IssueVo;
import org.netbeans.modules.bamboo.model.rest.Issue;
import org.netbeans.modules.bamboo.model.rest.Link;

/**
 *
 * @author Mario Schroeder
 */
public class IssueVoConverter extends AbstractVoConverter<Issue, IssueVo> {

    @Override
    public IssueVo convert(Issue src) {
        IssueVo target = new IssueVo(src.getKey());

        target.setAssignee(src.getAssignee());
        target.setSummary(src.getSummary());
        target.setIconUrl(src.getIconUrl());

        Link link = src.getLink();
        if (link != null) {
            target.setLink(link.getHref());
        }

        return target;
    }

}
