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

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.SwingConstants;

/**
 * This button looks and feels like a hyper link.
 *
 * @author Mario Schroeder
 */
public class LinkButton extends JButton {
    
    protected static final String HREF = "<html><a href=\"#\">";

    public LinkButton(String text) {
        super(HREF + text);
        init();
    }

    private void init() {
        setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        setBorderPainted(false);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setFocusable(false);
        setHorizontalAlignment(SwingConstants.LEFT);
    }

}
