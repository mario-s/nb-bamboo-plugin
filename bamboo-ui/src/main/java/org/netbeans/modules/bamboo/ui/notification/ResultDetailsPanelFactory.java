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
package org.netbeans.modules.bamboo.ui.notification;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.FlowLayout;
import javax.swing.AbstractButton;
import javax.swing.JPanel;
import org.netbeans.modules.bamboo.model.rcp.PlanVo;
import org.netbeans.modules.bamboo.ui.HtmlPane;

/**
 * Factory for {@link ResultDetailsPanel}
 * @author Mario Schroeder
 */
class ResultDetailsPanelFactory {
    
    ResultDetailsPanel create(String summary, String details) {
        HtmlPane detailsComp = new HtmlPane();
        detailsComp.setOpaque(true);
        detailsComp.setText(details);
        return new ResultDetailsPanel(summary, detailsComp);
    }
    
    
    ResultDetailsPanel create(String summary, BuildResult result) {
        String reason = result.getNewResult().getBuildReason();
        PlanVo plan = result.getPlan();
        
        ResultDetailsPanel panel = create(summary, reason);
        
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnPanel.setOpaque(false);
        
        Cursor cursor = new Cursor(Cursor.HAND_CURSOR);
        
        
        AbstractButton ignoreButton = new IgnoreButton(plan);
        ignoreButton.setCursor(cursor);
        btnPanel.add(ignoreButton);
        
        AbstractButton selectButton = new SelectNodeButton(plan);
        selectButton.setCursor(cursor);
        btnPanel.add(selectButton);
        
        panel.getDetailsPanel().add(btnPanel, BorderLayout.SOUTH);
        
        return panel;
    }
}
