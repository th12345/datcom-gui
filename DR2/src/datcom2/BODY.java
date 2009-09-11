/* Aleksey Matyushev
 *
 * This program is the sole property of Aleksey
 * Matyushev. This program is designed to be a GUI
 * add-on to DATCOM with extended capabilities in
 * providing XML data for FlightGear.
 */
package datcom2;

import java.util.ArrayList;
import org.jdesktop.application.FrameView;

public class BODY extends javax.swing.JPanel {

    public BODY(String path, String name, SYNTHS synthsPanel, MAIN mainClass) {
        initComponents();
        caseID = name;
        workingPath = path;
        this.synthsPanel = synthsPanel;
        this.mainClass = mainClass;
    }

    public void getInput(){
        BODY = jTextArea1.getText();
    }

    public void setSYNTHSinput(ArrayList FLTCON, ArrayList OPTINS, ArrayList SYNTHS){
        System.out.println("ARRAY LIST's STORED IN BODY");

        this.FLTCON = FLTCON;
        System.out.println("The FLTCON ARRAYLIST is: " + FLTCON.toString());

        this.OPTINS = OPTINS;
        System.out.println("The OPTINS ARRAYLIST is: " + OPTINS.toString());

        this.SYNTHS = SYNTHS;
        System.out.println("The SYNTHS ARRAYLIST is: " + SYNTHS.toString());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setName("Form"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(datcom2.DATCOM2App.class).getContext().getResourceMap(BODY.class);
        jLabel2.setFont(resourceMap.getFont("jLabel2.font")); // NOI18N
        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jTextArea1.setName("jTextArea1"); // NOI18N
        jScrollPane1.setViewportView(jTextArea1);

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jButton1.setText(resourceMap.getString("jButton1.text")); // NOI18N
        jButton1.setName("jButton1"); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText(resourceMap.getString("jButton2.text")); // NOI18N
        jButton2.setName("jButton2"); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 873, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addContainerGap(35, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(41, 41, 41)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        //If NEXT button is pressed collect and pass along all the input
        getInput();
        bodyPanel = this;
        if (wgplnfPanel == null){
            wgplnfPanel = new WGPLNF(workingPath, caseID, bodyPanel, mainClass);
        }
        wgplnfPanel.setBODYinput(FLTCON,OPTINS,SYNTHS, BODY);

        //switch panels to WGPLNF
        setVisible(false);
        mainClass.setComponent(wgplnfPanel);
        wgplnfPanel.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    public void setWGPLNF(WGPLNF wgplnfPanel){
        //sets the BODY panel, so if the user selects BACK the
        //data already inputed is saved

        this.wgplnfPanel = wgplnfPanel;
    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // if the user presses the BACK button, the pervious frame is shown
        setVisible(false);
        synthsPanel.setBODY(this);
        synthsPanel.setVisible(true);
        mainClass.setComponent(synthsPanel);
        //DATCOM2App.getInstance().show(view);
    }//GEN-LAST:event_jButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables

    private WGPLNF wgplnfPanel;
    private SYNTHS synthsPanel;
    private BODY bodyPanel;
    private MAIN mainClass;
    private String caseID;

    private ArrayList<String> SYNTHS;
    private ArrayList<String> OPTINS;
    private ArrayList<String> FLTCON;
    private String BODY;
    private String workingPath;
}
