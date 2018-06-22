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

import javax.swing.JEditorPane;
import javax.swing.event.HyperlinkEvent;

/**
 * This component displays HTML content and supplies action for hyperlinks
 *
 * @author Mario Schroeder
 */
public class HtmlPane extends JEditorPane {

    private static final String CONT_TYPE = "text/html";

    public HtmlPane() {
        init();
    }

    private void init() {
        setContentType(CONT_TYPE);
        setEditable(false);
        setOpaque(false);

        //use component font
        putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, true);

        addHyperlinkListener((HyperlinkEvent evt) -> {
            if (HyperlinkEvent.EventType.ACTIVATED.equals(evt.getEventType())) {
                BrowserInstance.Instance.showURL(evt.getURL());
            }
        });
    }
}
