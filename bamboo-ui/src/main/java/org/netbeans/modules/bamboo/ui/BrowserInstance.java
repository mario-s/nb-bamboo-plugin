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
package org.netbeans.modules.bamboo.ui;

import java.net.MalformedURLException;
import java.net.URL;
import org.openide.awt.HtmlBrowser.URLDisplayer;

import static org.openide.awt.HtmlBrowser.URLDisplayer.getDefault;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Singleton for {@link URLDisplayer}.
 * 
 * @author Mario Schroeder
 */
public enum BrowserInstance {
    
    Instance;
    
    private static final Logger LOG = LoggerFactory.getLogger(BrowserInstance.class);
    
    private final URLDisplayer urlDisplayer;
    
    private BrowserInstance() {
        urlDisplayer = getDefault();
    }
    
    public void showURL(URL url) {
        urlDisplayer.showURL(url);
    }
    
    public void showURL(String url) {
        try {
            showURL(new URL(url));
        } catch (MalformedURLException ex) {
            LOG.warn(ex.getMessage());
        }
    }
}
