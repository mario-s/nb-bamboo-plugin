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
package org.netbeans.modules.bamboo.ui.wizard;

import java.awt.Cursor;
import java.util.HashMap;
import java.util.Map;
import static java.util.Optional.ofNullable;
import org.netbeans.modules.bamboo.client.glue.InstanceManageable;

import org.openide.NotificationLineSupport;

import static org.openide.util.Lookup.getDefault;

import org.openide.util.NbBundle;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

class InstancePropertiesForm extends JPanel implements DocumentListener {

    /**
     * Use serialVersionUID for interoperability.
     */
    private static final long serialVersionUID = 1L;
    
    private final Map<Integer, JComponent> focusMap;

    private AbstractAction applyAction;

    private NotificationLineSupport notificationSupport;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup authBtnGroup;
    private javax.swing.JCheckBox chkRefresh;
    private javax.swing.JLabel lblAuthMethod;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JLabel lblServer;
    private javax.swing.JLabel lblTime;
    private javax.swing.JLabel lblToken;
    private javax.swing.JLabel lblUser;
    private javax.swing.JPasswordField password;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JRadioButton radBtnToken;
    private javax.swing.JRadioButton radBtnUsername;
    private javax.swing.JSpinner spinTime;
    private javax.swing.JPasswordField token;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtServer;
    private javax.swing.JTextField txtUser;
    // End of variables declaration//GEN-END:variables

    /**
     * Creates new form InstancePropertiesForm.
     */
    InstancePropertiesForm() {
        focusMap = new HashMap<>();
        initComponents();
        onPostInit();
    }

    private void onPostInit() {
        addDocumentListener();
        authBtnGroup.add(radBtnToken);
        authBtnGroup.add(radBtnUsername);
        progressBar.setVisible(false);

        focusMap.put(0, txtName);
        focusMap.put(1, txtServer);
        focusMap.put(2, txtUser);
        focusMap.put(3, password);
        focusMap.put(4, radBtnToken);
        focusMap.put(5, radBtnUsername);
        focusMap.put(6, token);
        focusMap.put(7, chkRefresh);
        focusMap.put(8, spinTime);
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
    
    /**
     * Returns <code>true</code> if the user selected authentication by token, else <code>false</code>.
     * @return boolean
     */
    boolean isTokenSelected() {
        return radBtnToken.isSelected();
    }
    
    char[] getToken() {
        return token.getPassword();
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
        setCursor(Cursor.WAIT_CURSOR);
        blocking(true);
    }

    void unblock() {
        setCursor(Cursor.DEFAULT_CURSOR);
        blocking(false);
    }

    private void setCursor(int type) {
        var comp = ofNullable(getParent()).orElse(this);
        comp.setCursor(Cursor.getPredefinedCursor(type));
    }

    /**
     * Set the focus in the field specified by the index;
     *
     * @param index
     */
    void setFocus(int index) {
        JComponent component = focusMap.get(index);
        if (component != null) {
            component.requestFocus();
        }
    }

    private void blocking(boolean block) {
        progressBar.setVisible(block);
        txtName.setEnabled(!block);
        txtServer.setEnabled(!block);
        txtUser.setEnabled(!block);
        password.setEnabled(!block);
        token.setEnabled(!block);
        spinTime.setEnabled(!block);
        chkRefresh.setEnabled(!block);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        authBtnGroup = new javax.swing.ButtonGroup();
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
        lblToken = new javax.swing.JLabel();
        token = new javax.swing.JPasswordField();
        lblAuthMethod = new javax.swing.JLabel();
        radBtnToken = new javax.swing.JRadioButton();
        radBtnUsername = new javax.swing.JRadioButton();

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

        spinTime.setModel(new javax.swing.SpinnerNumberModel(5, 1, 10, 1));
        spinTime.setValue(5);

        lblTime.setText("minutes");

        lblUser.setText("Username:");

        txtUser.setEnabled(false);

        lblPassword.setText("Password:");

        password.setEnabled(false);

        progressBar.setIndeterminate(true);
        progressBar.setString("Please wait...");
        progressBar.setStringPainted(true);

        lblToken.setText("Token");

        lblAuthMethod.setText("Authentication Method");

        radBtnToken.setSelected(true);
        radBtnToken.setText("Token");
        radBtnToken.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radBtnTokenActionPerformed(evt);
            }
        });

        radBtnUsername.setText("Username");
        radBtnUsername.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radBtnUsernameActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblServer)
                            .addComponent(lblName))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtServer)
                            .addComponent(txtName)))
                    .addComponent(progressBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblAuthMethod)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                    .addComponent(lblUser)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                                                .addGroup(layout.createSequentialGroup()
                                                    .addComponent(lblPassword)
                                                    .addGap(12, 12, 12)))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(lblToken)
                                                .addGap(39, 39, 39)))
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(password)
                                            .addComponent(txtUser)
                                            .addComponent(token, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(radBtnToken)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(radBtnUsername))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(chkRefresh)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(spinTime, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(lblTime)))))
                        .addGap(0, 164, Short.MAX_VALUE)))
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
                .addGap(18, 18, 18)
                .addComponent(lblAuthMethod)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(radBtnToken)
                    .addComponent(radBtnUsername))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblToken)
                    .addComponent(token, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblUser))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPassword)
                    .addComponent(password, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chkRefresh)
                    .addComponent(spinTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTime))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void radBtnTokenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radBtnTokenActionPerformed
        toggleAuth(isTokenSelected());
    }//GEN-LAST:event_radBtnTokenActionPerformed

    private void radBtnUsernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radBtnUsernameActionPerformed
        toggleAuth(isTokenSelected());
    }//GEN-LAST:event_radBtnUsernameActionPerformed

    private void toggleAuth(boolean useToken) {
        token.setEnabled(useToken);
        txtUser.setEnabled(!useToken);
        password.setEnabled(!useToken);
    }

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
            inform(getMessage("MSG_EmptyName"));
            return;
        }

        InstanceManageable manager = getDefault().lookup(InstanceManageable.class);
        if (manager.existsInstanceName(name)) {
            error(getMessage("MSG_ExistName"));
            return;
        }

        if (url.isEmpty() || url.endsWith("//")) {
            inform(getMessage("MSG_EmptyUrl"));
            return;
        }

        if (manager.existsInstanceUrl(url)) {
            error(getMessage("MSG_ExistUrl"));
            return;
        }

        if (!isTokenSelected()) {
            if (getUsername().isEmpty()) {
                inform(getMessage("MSG_EmptyUserName"));
                return;
            }

            if (getPassword().length == 0) {
                inform(getMessage("MSG_EmptyPassword"));
                return;
            }
        } else if (getToken().length == 0){
            inform(getMessage("MSG_EmptyToken"));
            return;
        }

        clearMessages();
        applyAction.setEnabled(true);
    }

    void inform(String message) {
        notificationSupport.setInformationMessage(message);
    }

    void error(String message) {
        notificationSupport.setErrorMessage(message);
    }

    void clearMessages() {
        notificationSupport.clearMessages();
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

    JProgressBar getProgressBar() {
        return progressBar;
    }

    JTextField getTxtName() {
        return txtName;
    }

}
