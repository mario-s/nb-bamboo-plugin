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

import java.time.LocalDateTime;
import java.util.Collection;

import static java.util.Collections.emptyList;
import java.util.Objects;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 *
 * @author Mario Schroeder
 */
public class ChangeVo {

    private String changesetId;

    private String author;

    private String userName;

    private String fullName;

    private String comment;

    private String commitUrl;

    private LocalDateTime date;

    private Collection<FileVo> files;
    
    public Collection<FileVo> getFiles() {
        return (files != null) ? files : emptyList();
    }

    public void setFiles(Collection<FileVo> files) {
        this.files = files;
    }

    public String getChangesetId() {
        return changesetId;
    }

    public void setChangesetId(String changesetId) {
        this.changesetId = changesetId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCommitUrl() {
        return commitUrl;
    }

    public void setCommitUrl(String commitUrl) {
        this.commitUrl = commitUrl;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public int hashCode() {
        return 37 * 5 + Objects.hashCode(this.changesetId);
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
        final ChangeVo other = (ChangeVo) obj;
        return Objects.equals(this.changesetId, other.changesetId);
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
    
    
}
