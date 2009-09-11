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

public class WGPLNF extends javax.swing.JPanel {

    public WGPLNF(String path,String name, BODY bodyPanel,MAIN mainClass) {
        initComponents();
        workingPath = path;
        caseID = name;
        this.bodyPanel = bodyPanel;
        this.mainClass = mainClass;
    }


    public void setBODYinput(ArrayList FLTCON, ArrayList OPTINS, ArrayList SYNTHS, String BODY){
        System.out.println("ARRAY LIST's STORED IN WGPLNF");

        this.FLTCON = FLTCON;
        System.out.println("The FLTCON ARRAYLIST is: " + FLTCON.toString());

        this.OPTINS = OPTINS;
        System.out.println("The OPTINS ARRAYLIST is: " + OPTINS.toString());

        this.SYNTHS = SYNTHS;
        System.out.println("The SYNTHS ARRAYLIST is: " + SYNTHS.toString());

        this.BODY = BODY;
        System.out.println("The BODY TextInput is: " + BODY);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jTextField8 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jTextField9 = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jTextField10 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jTextField11 = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jTextField12 = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jTextField13 = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jTextField14 = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();

        setName("Form"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(datcom2.DATCOM2App.class).getContext().getResourceMap(WGPLNF.class);
        jLabel2.setFont(resourceMap.getFont("jLabel2.font")); // NOI18N
        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        jButton1.setText(resourceMap.getString("jButton1.text")); // NOI18N
        jButton1.setName("jButton1"); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setToolTipText(resourceMap.getString("jLabel1.toolTipText")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jTextField1.setText(resourceMap.getString("chrdtpValue.text")); // NOI18N
        jTextField1.setName("chrdtpValue"); // NOI18N

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setToolTipText(resourceMap.getString("jLabel3.toolTipText")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setToolTipText(resourceMap.getString("jLabel4.toolTipText")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        jTextField3.setText(resourceMap.getString("sspneValue.text")); // NOI18N
        jTextField3.setName("sspneValue"); // NOI18N

        jTextField2.setText(resourceMap.getString("sspnopValue.text")); // NOI18N
        jTextField2.setName("sspnopValue"); // NOI18N

        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setToolTipText(resourceMap.getString("jLabel5.toolTipText")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N

        jTextField4.setText(resourceMap.getString("sspnValue.text")); // NOI18N
        jTextField4.setName("sspnValue"); // NOI18N

        jLabel6.setText(resourceMap.getString("jLabel6.text")); // NOI18N
        jLabel6.setToolTipText(resourceMap.getString("jLabel6.toolTipText")); // NOI18N
        jLabel6.setName("jLabel6"); // NOI18N

        jLabel7.setText(resourceMap.getString("jLabel7.text")); // NOI18N
        jLabel7.setToolTipText(resourceMap.getString("jLabel7.toolTipText")); // NOI18N
        jLabel7.setName("jLabel7"); // NOI18N

        jLabel8.setText(resourceMap.getString("jLabel8.text")); // NOI18N
        jLabel8.setToolTipText(resourceMap.getString("jLabel8.toolTipText")); // NOI18N
        jLabel8.setName("jLabel8"); // NOI18N

        jTextField7.setText(resourceMap.getString("savsiValue.text")); // NOI18N
        jTextField7.setName("savsiValue"); // NOI18N

        jLabel9.setText(resourceMap.getString("jLabel9.text")); // NOI18N
        jLabel9.setToolTipText(resourceMap.getString("jLabel9.toolTipText")); // NOI18N
        jLabel9.setName("jLabel9"); // NOI18N

        jTextField8.setText(resourceMap.getString("savsoValue.text")); // NOI18N
        jTextField8.setName("savsoValue"); // NOI18N

        jLabel10.setText(resourceMap.getString("jLabel10.text")); // NOI18N
        jLabel10.setToolTipText(resourceMap.getString("jLabel10.toolTipText")); // NOI18N
        jLabel10.setName("jLabel10"); // NOI18N

        jTextField9.setText(resourceMap.getString("twistaValue.text")); // NOI18N
        jTextField9.setName("twistaValue"); // NOI18N

        jLabel11.setText(resourceMap.getString("jLabel11.text")); // NOI18N
        jLabel11.setToolTipText(resourceMap.getString("jLabel11.toolTipText")); // NOI18N
        jLabel11.setName("jLabel11"); // NOI18N

        jTextField5.setText(resourceMap.getString("jTextField5.text")); // NOI18N
        jTextField5.setName("jTextField5"); // NOI18N

        jTextField6.setText(resourceMap.getString("jTextField6.text")); // NOI18N
        jTextField6.setName("jTextField6"); // NOI18N

        jTextField10.setText(resourceMap.getString("jTextField10.text")); // NOI18N
        jTextField10.setName("jTextField10"); // NOI18N

        jLabel12.setText(resourceMap.getString("jLabel12.text")); // NOI18N
        jLabel12.setToolTipText(resourceMap.getString("jLabel12.toolTipText")); // NOI18N
        jLabel12.setName("jLabel12"); // NOI18N

        jTextField11.setText(resourceMap.getString("jTextField11.text")); // NOI18N
        jTextField11.setName("jTextField11"); // NOI18N

        jLabel13.setText(resourceMap.getString("jLabel13.text")); // NOI18N
        jLabel13.setToolTipText(resourceMap.getString("jLabel13.toolTipText")); // NOI18N
        jLabel13.setName("jLabel13"); // NOI18N

        jTextField12.setText(resourceMap.getString("jTextField12.text")); // NOI18N
        jTextField12.setName("jTextField12"); // NOI18N

        jLabel14.setIcon(resourceMap.getIcon("jLabel14.icon")); // NOI18N
        jLabel14.setText(resourceMap.getString("jLabel14.text")); // NOI18N
        jLabel14.setName("jLabel14"); // NOI18N

        jLabel15.setText(resourceMap.getString("jLabel15.text")); // NOI18N
        jLabel15.setToolTipText(resourceMap.getString("jLabel15.toolTipText")); // NOI18N
        jLabel15.setName("jLabel15"); // NOI18N

        jTextField13.setText(resourceMap.getString("jTextField13.text")); // NOI18N
        jTextField13.setToolTipText(resourceMap.getString("jTextField13.toolTipText")); // NOI18N
        jTextField13.setName("jTextField13"); // NOI18N

        jLabel16.setText(resourceMap.getString("jLabel16.text")); // NOI18N
        jLabel16.setToolTipText(resourceMap.getString("jLabel16.toolTipText")); // NOI18N
        jLabel16.setName("jLabel16"); // NOI18N

        jTextField14.setText(resourceMap.getString("jTextField14.text")); // NOI18N
        jTextField14.setName("jTextField14"); // NOI18N

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
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(48, 48, 48)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel1)
                                            .addComponent(jLabel3)
                                            .addComponent(jLabel4)
                                            .addComponent(jLabel5))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jTextField4)
                                            .addComponent(jTextField2, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jTextField3)
                                            .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel6)
                                            .addComponent(jLabel7))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jTextField6, javax.swing.GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE)
                                            .addComponent(jTextField5))))
                                .addGap(36, 36, 36)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel9)
                                            .addComponent(jLabel10)
                                            .addComponent(jLabel11)
                                            .addComponent(jLabel8)
                                            .addComponent(jLabel12)
                                            .addComponent(jLabel16)
                                            .addComponent(jLabel13))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(jTextField11, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jTextField14, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jTextField12, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jTextField9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 86, Short.MAX_VALUE)
                                            .addComponent(jTextField8, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jTextField7, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jTextField10, javax.swing.GroupLayout.Alignment.LEADING)))
                                    .addComponent(jTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel14))
                    .addComponent(jLabel15))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8)
                            .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9)
                            .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10)
                            .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11)
                            .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12)
                            .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16)
                            .addComponent(jTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13))
                        .addGap(44, 44, 44)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(jTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1)
                            .addComponent(jButton2)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addComponent(jLabel14)))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        //If NEXT button is pressed collect and pass along all the input
        getInput();
        wgplnfPanel = this;
        if (htplnfPanel == null){
            htplnfPanel = new HTPLNF(workingPath,caseID, wgplnfPanel, mainClass);
        }
        htplnfPanel.setWGPLNFinput(FLTCON, OPTINS, SYNTHS, BODY, WGPLNFfiltered);

        //switch panels to HTPLNF
        setVisible(false);
        mainClass.setComponent(htplnfPanel);
        htplnfPanel.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    public void setHTPLNF(HTPLNF htplnfPanel){
        //sets the BODY panel, so if the user selects BACK the
        //data already inputed is saved

        this.htplnfPanel = htplnfPanel;
    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // if the user presses the BACK button, the pervious frame is shown
        setVisible(false);
        bodyPanel.setWGPLNF(this);
        bodyPanel.setVisible(true);
        mainClass.setComponent(bodyPanel);
        //DATCOM2App.getInstance().show(view);
    }//GEN-LAST:event_jButton2ActionPerformed
 public void getInput(){
        CHRDTPW = "CHRDTP=" + jTextField1.getText();
        SSPNOPW = "SSPNOP=" + jTextField2.getText();
        SSPNEW  = "SSPNE=" + jTextField3.getText();
        SSPNW  = "SSPN=" + jTextField4.getText();
        CHRDBPW = "CHRDBP=" + jTextField5.getText();
        CHRDRW = "CHRDR=" + jTextField6.getText();
        SAVSIW = "SAVSI=" + jTextField7.getText();
        SAVSOW = "SAVSO=" + jTextField8.getText();
        TWISTAW = "TWISTA=" + jTextField9.getText();
        CHSTATW = "CHSTAT=" + jTextField10.getText();
        DHDADIW = "DHDADI=" + jTextField11.getText();
        DHDADOW = "DHDADO=" + jTextField14.getText();
        TYPEW = "TYPE=" + jTextField12.getText();
        NACAW = jTextField13.getText();

        WGPLNF = new ArrayList<String>();
        WGPLNF.add(CHRDTPW);
        WGPLNF.add(SSPNOPW);
        WGPLNF.add(SSPNEW);
        WGPLNF.add(SSPNW);
        WGPLNF.add(CHRDBPW);
        WGPLNF.add(CHRDRW);
        WGPLNF.add(SAVSIW);
        WGPLNF.add(SAVSOW);
        WGPLNF.add(TWISTAW);
        WGPLNF.add(CHSTATW);
        WGPLNF.add(DHDADIW);
        WGPLNF.add(DHDADOW);
        WGPLNF.add(TYPEW);
        WGPLNF.add(NACAW);

        WGPLNFvalues = new ArrayList<String>();
        WGPLNFvalues.add(jTextField1.getText());
        WGPLNFvalues.add(jTextField2.getText());
        WGPLNFvalues.add(jTextField3.getText());
        WGPLNFvalues.add(jTextField4.getText());
        WGPLNFvalues.add(jTextField5.getText());
        WGPLNFvalues.add(jTextField6.getText());
        WGPLNFvalues.add(jTextField7.getText());
        WGPLNFvalues.add(jTextField8.getText());
        WGPLNFvalues.add(jTextField9.getText());
        WGPLNFvalues.add(jTextField10.getText());
        WGPLNFvalues.add(jTextField11.getText());
        WGPLNFvalues.add(jTextField14.getText());
        WGPLNFvalues.add(jTextField12.getText());
        WGPLNFvalues.add(jTextField13.getText());

        WGPLNFfiltered = new ArrayList<String>();

        //filter out the null values from the array
        for(int i = 0; i < WGPLNF.size(); i ++){
            if (WGPLNFvalues.get(i).equals("")){}
            else{
                System.out.println(WGPLNF.get(i));
                WGPLNFfiltered.add(WGPLNF.get(i));
            }
        }
        System.out.println("End of output");
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextField13;
    private javax.swing.JTextField jTextField14;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    // End of variables declaration//GEN-END:variables


    private String CHRDTPW;
    private String SSPNOPW;
    private String SSPNEW;
    private String SSPNW;
    private String CHRDBPW;
    private String CHRDRW;
    private String SAVSIW;
    private String SAVSOW;
    private String TWISTAW;
    private String CHSTATW;
    private String DHDADIW;
    private String DHDADOW;
    private String TYPEW;
    private String NACAW;

    private ArrayList<String> SYNTHS;
    private ArrayList<String> OPTINS;
    private ArrayList<String> FLTCON;

    private ArrayList<String> WGPLNF;
    private ArrayList<String> WGPLNFfiltered;
    private ArrayList<String> WGPLNFvalues;
    private String workingPath;
    private String caseID;
    private String BODY;

    private HTPLNF htplnfPanel;
    private BODY bodyPanel;
    private WGPLNF wgplnfPanel;
    private MAIN mainClass;
}
