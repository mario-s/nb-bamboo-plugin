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
package org.netbeans.modules.bamboo.client.rest;

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import org.netbeans.modules.bamboo.client.glue.InstanceManageable;
import org.openide.util.NbPreferences;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
class PreferenceWrapper {
    
    private static final Logger LOG = LoggerFactory.getLogger(PreferenceWrapper.class);
               
    private PreferenceWrapper(){}
    
    static Preferences instancesPrefs() {
        Preferences prefs = NbPreferences.forModule(InstanceManageable.class).node("instances");
        try {
            prefs.sync();
        } catch (BackingStoreException ex) {
            LOG.info(ex.getMessage());
        }
        return prefs;
    }
}
