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

import java.util.Objects;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 *
 * @author Mario Schroeder
 */
public class FileVo {
    private String name;
    private String revision;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRevision() {
        return revision;
    }

    public void setRevision(String revision) {
        this.revision = revision;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return 31 * hash + Objects.hashCode(this.revision);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final FileVo other = (FileVo) obj;
        return Objects.equals(this.revision, other.revision);
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
    
}
