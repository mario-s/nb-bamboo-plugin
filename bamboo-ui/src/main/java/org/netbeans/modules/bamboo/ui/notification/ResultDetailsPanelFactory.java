package org.netbeans.modules.bamboo.ui.notification;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import org.netbeans.modules.bamboo.ui.HtmlPane;
import org.netbeans.modules.bamboo.ui.IgnoreButton;

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
        ResultDetailsPanel panel = create(summary, reason);
        
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnPanel.setOpaque(false);
        JButton ignoreButton = new IgnoreButton(result.getPlan());
        ignoreButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnPanel.add(ignoreButton);
        
        panel.getDetailsPanel().add(btnPanel, BorderLayout.SOUTH);
        
        return panel;
    }
}
