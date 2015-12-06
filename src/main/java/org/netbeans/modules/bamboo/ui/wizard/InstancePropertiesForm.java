package org.netbeans.modules.bamboo.ui.wizard;

import javax.swing.AbstractAction;
import javax.swing.JPanel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import org.openide.NotificationLineSupport;
import org.openide.util.NbBundle;

final class InstancePropertiesForm extends JPanel implements DocumentListener {

    private AbstractAction applyAction;

    private NotificationLineSupport notificationSupport;

    /**
     * Creates new form InstancePropertiesForm
     */
    InstancePropertiesForm() {
        initComponents();
        addDocumentListener();
    }

    private void addDocumentListener() {
        nameTextField.getDocument().addDocumentListener(this);
        serverTextField.getDocument().addDocumentListener(this);
    }

    public String getInstanceUrl() {
        return serverTextField.getText();
    }

    public void setInstanceUrl(final String url) {
        serverTextField.setText(url);
    }

    public String getInstanceName() {
        return nameTextField.getText();
    }

    public void setInstanceName(final String name) {
        nameTextField.setText(name);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        serverLabel = new javax.swing.JLabel();
        serverTextField = new javax.swing.JTextField();
        nameLabel = new javax.swing.JLabel();
        nameTextField = new javax.swing.JTextField();

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/netbeans/modules/bamboo/ui/wizard/Bundle"); // NOI18N
        serverLabel.setText(bundle.getString("LBL_SERVER")); // NOI18N

        serverTextField.setText("http://");

        nameLabel.setText(org.openide.util.NbBundle.getMessage(InstancePropertiesForm.class, "TXT_NAME")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(serverLabel)
                    .addComponent(nameLabel))
                .addGap(4, 4, 4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nameTextField)
                    .addComponent(serverTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 344, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nameLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(serverTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(serverLabel))
                .addContainerGap(169, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel nameLabel;
    private javax.swing.JTextField nameTextField;
    private javax.swing.JLabel serverLabel;
    private javax.swing.JTextField serverTextField;
    // End of variables declaration//GEN-END:variables

    @Override
    public void insertUpdate(DocumentEvent e) {
        validateInput();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        validateInput();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
    }

    private void validateInput() {
        applyAction.setEnabled(false);
        String name = getDisplayName();
        String url = getUrl();
        if (name.isEmpty()) {
            notificationSupport.setInformationMessage(NbBundle.getMessage(getClass(), "MSG_EmptyName"));
            return;
        }
        if (url.isEmpty() || url.endsWith("//")) {
            notificationSupport.setInformationMessage(NbBundle.getMessage(getClass(), "MSG_EmptyUrl"));
            return;
        }
        
        notificationSupport.clearMessages();
        applyAction.setEnabled(true);
    }

    private String getDisplayName() {
        return getInstanceName().trim();
    }

    private String getUrl() {
        return getInstanceUrl().trim();
    }

    void setApplyAction(AbstractAction applyAction) {
        this.applyAction = applyAction;
    }

    void setNotificationSupport(NotificationLineSupport support) {
        this.notificationSupport = support;
    }

}
