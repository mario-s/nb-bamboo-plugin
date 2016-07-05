package org.netbeans.modules.bamboo.ui.wizard;

import org.netbeans.modules.bamboo.glue.InstanceManageable;

import org.openide.NotificationLineSupport;

import static org.openide.util.Lookup.getDefault;
import org.openide.util.NbBundle;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.JPanel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;


class InstancePropertiesForm extends JPanel implements DocumentListener {
    /** Use serialVersionUID for interoperability. */
    private static final long serialVersionUID = 1L;

    private AbstractAction applyAction;

    private NotificationLineSupport notificationSupport;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox chkRefresh;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JLabel lblServer;
    private javax.swing.JLabel lblTime;
    private javax.swing.JLabel lblUser;
    private javax.swing.JPasswordField password;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JSpinner spinTime;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtServer;
    private javax.swing.JTextField txtUser;
    // End of variables declaration//GEN-END:variables

    /**
     * Creates new form InstancePropertiesForm.
     */
    InstancePropertiesForm() {
        initComponents();
        addDocumentListener();
        progressBar.setVisible(false);
    }

    private void addDocumentListener() {
        txtName.getDocument().addDocumentListener(this);
        txtServer.getDocument().addDocumentListener(this);
        txtUser.getDocument().addDocumentListener(this);
        password.getDocument().addDocumentListener(this);
    }

    String getInstanceUrl() {
        return txtServer.getText();
    }

    String getInstanceName() {
        return txtName.getText();
    }

    String getUsername() {
        return txtUser.getText();
    }

    char[] getPassword() {
        return password.getPassword();
    }

    int getSyncTime() {
        return chkRefresh.isSelected() ? (Integer) spinTime.getValue() : 0;
    }
    
    void block() {
        progressBar.setVisible(true);
        txtName.setEnabled(false);
        txtServer.setEnabled(false);
        txtUser.setEnabled(false);
        password.setEnabled(false);
        spinTime.setEnabled(false);
        chkRefresh.setEnabled(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblServer = new javax.swing.JLabel();
        txtServer = new javax.swing.JTextField();
        lblName = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        chkRefresh = new javax.swing.JCheckBox();
        spinTime = new javax.swing.JSpinner();
        lblTime = new javax.swing.JLabel();
        lblUser = new javax.swing.JLabel();
        txtUser = new javax.swing.JTextField();
        lblPassword = new javax.swing.JLabel();
        password = new javax.swing.JPasswordField();
        progressBar = new javax.swing.JProgressBar();

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/netbeans/modules/bamboo/ui/wizard/Bundle"); // NOI18N
        lblServer.setText(bundle.getString("LBL_SERVER")); // NOI18N

        txtServer.setText("http://");

        lblName.setText(org.openide.util.NbBundle.getMessage(InstancePropertiesForm.class, "TXT_NAME")); // NOI18N

        chkRefresh.setSelected(true);
        chkRefresh.setText("Auto refresh every");
        chkRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkRefreshActionPerformed(evt);
            }
        });

        spinTime.setEnabled(false);
        spinTime.setValue(5);

        lblTime.setText("minutes");

        lblUser.setText("Username:");

        lblPassword.setText("Password:");

        progressBar.setIndeterminate(true);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(progressBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblServer)
                            .addComponent(lblName))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtServer)
                            .addComponent(txtName)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(lblUser)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblPassword)
                                        .addGap(12, 12, 12)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(password, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
                                    .addComponent(txtUser)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(chkRefresh)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(spinTime, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblTime)))
                        .addGap(0, 182, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblName))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtServer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblServer))
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblUser))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPassword)
                    .addComponent(password, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chkRefresh)
                    .addComponent(spinTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTime))
                .addGap(18, 18, 18)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void chkRefreshActionPerformed(final java.awt.event.ActionEvent evt) {
        AbstractButton abstractButton = (AbstractButton) evt.getSource();
        boolean selected = abstractButton.getModel().isSelected();
        spinTime.setEnabled(selected);
    }

    @Override
    public void insertUpdate(final DocumentEvent e) {
        validateInput();
    }

    @Override
    public void removeUpdate(final DocumentEvent e) {
        validateInput();
    }

    @Override
    public void changedUpdate(final DocumentEvent e) {
    }

    private void validateInput() {
        applyAction.setEnabled(false);

        String name = getDisplayName();
        String url = getUrl();

        if (name.isEmpty()) {
            notificationSupport.setInformationMessage(getMessage("MSG_EmptyName"));

            return;
        }

        if (url.isEmpty() || url.endsWith("//")) {
            notificationSupport.setInformationMessage(getMessage("MSG_EmptyUrl"));

            return;
        }

        if (getUsername().isEmpty()) {
            notificationSupport.setInformationMessage(getMessage("MSG_EmptyUserName"));

            return;
        }

        if (getPassword().length == 0) {
            notificationSupport.setInformationMessage(getMessage("MSG_EmptyPassword"));

            return;
        }

        InstanceManageable manager = getDefault().lookup(InstanceManageable.class);

        if (manager.existsInstance(name)) {
            notificationSupport.setErrorMessage(getMessage("MSG_ExistName"));

            return;
        }

        notificationSupport.clearMessages();
        applyAction.setEnabled(true);
    }

    private String getMessage(final String key) {
        return NbBundle.getMessage(getClass(), key);
    }

    private String getDisplayName() {
        return getInstanceName().trim();
    }

    private String getUrl() {
        return getInstanceUrl().trim();
    }

    void setApplyAction(final AbstractAction applyAction) {
        this.applyAction = applyAction;
    }

    void setNotificationSupport(final NotificationLineSupport support) {
        this.notificationSupport = support;
    }
}
