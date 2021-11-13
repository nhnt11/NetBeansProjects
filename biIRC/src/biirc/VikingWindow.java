/*
 * biIRC - biIRC is an IRC Remote Control
 * 
 * Copyright (C) 2012 Nihanth Subramanya
 * 
 * biIRC is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * biIRC is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 *  You should have received a copy of the GNU General Public License
 *  along with biIRC.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */

package biirc;

public class VikingWindow extends javax.swing.JFrame {

    private Viking mViking;

    /** Creates new form VikingWindow */
    public VikingWindow(Viking viking) {
        mViking = viking;
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jLabel1 = new javax.swing.JLabel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        mConsoleLabel = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        mLogLabel = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        configBtn = new javax.swing.JButton();
        macrosBtn = new javax.swing.JButton();
        reconnectBtn = new javax.swing.JButton();
        quitBtn = new javax.swing.JButton();

        org.jdesktop.layout.GroupLayout jPanel3Layout = new org.jdesktop.layout.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 100, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 100, Short.MAX_VALUE)
        );

        org.jdesktop.layout.GroupLayout jPanel4Layout = new org.jdesktop.layout.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 100, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 100, Short.MAX_VALUE)
        );

        org.jdesktop.layout.GroupLayout jPanel5Layout = new org.jdesktop.layout.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 100, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 100, Short.MAX_VALUE)
        );

        org.jdesktop.layout.GroupLayout jPanel7Layout = new org.jdesktop.layout.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 100, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 100, Short.MAX_VALUE)
        );

        jButton1.setText("jButton1");

        jLabel1.setText("jLabel1");
        jScrollPane1.setViewportView(jLabel1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTabbedPane2.setPreferredSize(new java.awt.Dimension(400, 300));

        jPanel8.setLayout(new java.awt.BorderLayout());

        mConsoleLabel.setBackground(new java.awt.Color(255, 255, 255));
        mConsoleLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        mConsoleLabel.setText("<html>");
        mConsoleLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        mConsoleLabel.setOpaque(true);
        jScrollPane2.setViewportView(mConsoleLabel);

        jPanel8.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jTabbedPane2.addTab("Console", jPanel8);

        jPanel2.setLayout(new java.awt.BorderLayout());

        mLogLabel.setBackground(new java.awt.Color(255, 255, 255));
        mLogLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        mLogLabel.setText("<html>");
        mLogLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        mLogLabel.setOpaque(true);
        jScrollPane3.setViewportView(mLogLabel);

        jPanel2.add(jScrollPane3, java.awt.BorderLayout.CENTER);

        jTabbedPane2.addTab("IRC Log", jPanel2);

        getContentPane().add(jTabbedPane2, java.awt.BorderLayout.CENTER);

        jPanel10.setLayout(new java.awt.GridLayout(1, 4));

        configBtn.setText("Config");
        configBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                configBtnActionPerformed(evt);
            }
        });
        jPanel10.add(configBtn);

        macrosBtn.setText("Macros");
        macrosBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                macrosBtnActionPerformed(evt);
            }
        });
        jPanel10.add(macrosBtn);

        reconnectBtn.setText("Reconnect");
        reconnectBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reconnectBtnActionPerformed(evt);
            }
        });
        jPanel10.add(reconnectBtn);

        quitBtn.setText("Quit");
        quitBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quitBtnActionPerformed(evt);
            }
        });
        jPanel10.add(quitBtn);

        getContentPane().add(jPanel10, java.awt.BorderLayout.PAGE_END);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void configBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_configBtnActionPerformed
        new ConfigDialog(this, mViking, mViking.getProperties()).setVisible(true);
    }//GEN-LAST:event_configBtnActionPerformed

    private void quitBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_quitBtnActionPerformed
        mViking.quit();
        System.exit(0);
    }//GEN-LAST:event_quitBtnActionPerformed

    private void reconnectBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reconnectBtnActionPerformed
        // TODO add your handling code here:
        mViking.quit();
        try {
            mViking.restart();
        } catch (Exception e) {
            appendErrorToConsole("Disconnected but unable to restart. Please manually reopen biIRC.");
        }
    }//GEN-LAST:event_reconnectBtnActionPerformed

    private void macrosBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_macrosBtnActionPerformed
        new MacroDialog(this, true).setVisible(true);
    }//GEN-LAST:event_macrosBtnActionPerformed

    void appendToConsole(String text) {
        mConsoleLabel.setText(mConsoleLabel.getText() + text + "<br />");
    }

    void appendErrorToConsole(String text) {
        mConsoleLabel.setText(
                mConsoleLabel.getText() + "<span style='color:red'>" + text + "</span><br />");
    }

    void appendToLog(String text) {
        mLogLabel.setText(mLogLabel.getText() + text + "<br />");
    }

    void appendIncomingToLog(String text) {
        mLogLabel.setText(
                mLogLabel.getText() + "<span style='color:green'>" + text + "</span><br />");
    }

    void appendOutgoingToLog(String text) {
        mLogLabel.setText(mLogLabel.getText() + "<span style='color:blue'>" + text + "</span><br />");
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton configBtn;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JLabel mConsoleLabel;
    private javax.swing.JLabel mLogLabel;
    private javax.swing.JButton macrosBtn;
    private javax.swing.JButton quitBtn;
    private javax.swing.JButton reconnectBtn;
    // End of variables declaration//GEN-END:variables
}
