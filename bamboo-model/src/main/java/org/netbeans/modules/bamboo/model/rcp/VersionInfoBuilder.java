/*
 * Copyright 2019 NetBeans.
 *
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
import org.apache.commons.lang3.builder.Builder;

/**
 * Builder for {@link VersionInfo}.
 */
public class VersionInfoBuilder implements Builder<VersionInfo> {

    private String version;
    private int buildNumber;
    private LocalDateTime buildDate;
        
    public VersionInfoBuilder version(String version) {
        this.version = version;
        return this;
    }
    
    public VersionInfoBuilder buildNumber(int buildNumber) {
        this.buildNumber = buildNumber;
        return this;
    }
    
    public VersionInfoBuilder buildDate(LocalDateTime buildDate) {
        this.buildDate = buildDate;
        return this;
    }

    @Override
    public VersionInfo build() {
        return new VersionInfo(version, buildNumber, buildDate);
    }

}
