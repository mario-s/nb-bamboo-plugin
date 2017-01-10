package org.netbeans.modules.bamboo.ui.notification;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.FlowLayout;
import javax.swing.AbstractButton;
import javax.swing.JPanel;
import org.netbeans.modules.bamboo.model.rcp.PlanVo;
import org.netbeans.modules.bamboo.ui.HtmlPane;
import org.netbeans.modules.bamboo.ui.IgnoreButton;
import org.netbeans.modules.bamboo.ui.SelectNodeButton;

/**
 * Factory for {@link ResultDetailsPanel}
 * @author spindizzy
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
