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
package org.netbeans.modules.bamboo.ui.nodes;

import java.awt.Component;
import java.beans.PropertyEditorSupport;
import org.netbeans.modules.bamboo.ui.HtmlPane;
import org.netbeans.modules.bamboo.ui.util.TextExtractor;

/**
 * This is a custom editor for the build reason of a plan.
 * 
 * @author Mario Schroeder
 */
public class BuildReasonEditor extends PropertyEditorSupport {

    private final TextExtractor textExtractor;

    private final BuildResultPanel buildResultPanel;
    
    private final HtmlPane pane;

    public BuildReasonEditor() {
        textExtractor = new TextExtractor();
        pane = new HtmlPane();
        buildResultPanel = new BuildResultPanel(pane);
    }

    @Override
    public Component getCustomEditor() {
        String txt = super.getAsText();
        pane.setText(txt);
        return buildResultPanel;
    }

    @Override
    public String getAsText() {
        return textExtractor.removeTags(super.getAsText());
    }

    @Override
    public boolean supportsCustomEditor() {
        return true;
    }
}
