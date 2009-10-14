/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * FlightSurfaceView.java
 *
 * Created on Oct 13, 2009, 11:20:36 PM
 */

package opendatcom;

import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author -B-
 */
public class FlightSurfaceView extends javax.swing.JPanel {

    FlightSurfaceController controller;
    /** Creates new form FlightSurfaceView */
    public FlightSurfaceView(FlightSurfaceModel.SURFACE_TYPE type) {
        initComponents();
        controller = new FlightSurfaceController(this,type);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel54 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel220 = new javax.swing.JLabel();
        jSSPNE_Text = new javax.swing.JTextField();
        jLabel228 = new javax.swing.JLabel();
        jCHRDBP_Text = new javax.swing.JTextField();
        jCHRDTP_Text = new javax.swing.JTextField();
        jLabel229 = new javax.swing.JLabel();
        jLabel232 = new javax.swing.JLabel();
        jSAVSI_Text = new javax.swing.JTextField();
        jLabel235 = new javax.swing.JLabel();
        jLabel236 = new javax.swing.JLabel();
        jCHRDR_Text = new javax.swing.JTextField();
        jLabel237 = new javax.swing.JLabel();
        jSSPN_Text = new javax.swing.JTextField();
        jSSPNOP_Text = new javax.swing.JTextField();
        jPanel15 = new javax.swing.JPanel();
        jLabel211 = new javax.swing.JLabel();
        jCHSTAT_Text = new javax.swing.JTextField();
        jLabel212 = new javax.swing.JLabel();
        jDHADO_Text = new javax.swing.JTextField();
        jSAVSO_Text = new javax.swing.JTextField();
        jLabel213 = new javax.swing.JLabel();
        jLabel214 = new javax.swing.JLabel();
        jLabel216 = new javax.swing.JLabel();
        jTYPE_Text = new javax.swing.JTextField();
        jLabel217 = new javax.swing.JLabel();
        jDHDADI_Text = new javax.swing.JTextField();
        jTWISTA_Text = new javax.swing.JTextField();
        jLabel252 = new javax.swing.JLabel();
        jAirfoil_Text = new javax.swing.JTextField();
        jPanel18 = new javax.swing.JPanel();
        jPanel20 = new javax.swing.JPanel();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jRadioButton3 = new javax.swing.JRadioButton();
        jRadioButton4 = new javax.swing.JRadioButton();
        jShowReference = new javax.swing.JButton();
        WingLogo1 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        jPanel55 = new javax.swing.JPanel();
        jTitle = new javax.swing.JLabel();

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(opendatcom.OpenDatcomApp.class).getContext().getResourceMap(FlightSurfaceView.class);
        jPanel54.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("jPanel54.border.title"))); // NOI18N
        jPanel54.setName("jPanel54"); // NOI18N

        jPanel8.setName("jPanel8"); // NOI18N

        jLabel220.setText(resourceMap.getString("jLabel220.text")); // NOI18N
        jLabel220.setToolTipText(resourceMap.getString("jLabel220.toolTipText")); // NOI18N
        jLabel220.setName("jLabel220"); // NOI18N

        jSSPNE_Text.setName("jSSPNE_Text"); // NOI18N

        jLabel228.setText(resourceMap.getString("jLabel228.text")); // NOI18N
        jLabel228.setToolTipText(resourceMap.getString("jLabel228.toolTipText")); // NOI18N
        jLabel228.setName("jLabel228"); // NOI18N

        jCHRDBP_Text.setName("jCHRDBP_Text"); // NOI18N

        jCHRDTP_Text.setName("jCHRDTP_Text"); // NOI18N

        jLabel229.setText(resourceMap.getString("jLabel229.text")); // NOI18N
        jLabel229.setToolTipText(resourceMap.getString("jLabel229.toolTipText")); // NOI18N
        jLabel229.setName("jLabel229"); // NOI18N

        jLabel232.setText(resourceMap.getString("jLabel232.text")); // NOI18N
        jLabel232.setToolTipText(resourceMap.getString("jLabel232.toolTipText")); // NOI18N
        jLabel232.setName("jLabel232"); // NOI18N

        jSAVSI_Text.setName("jSAVSI_Text"); // NOI18N

        jLabel235.setText(resourceMap.getString("jLabel235.text")); // NOI18N
        jLabel235.setToolTipText(resourceMap.getString("jLabel235.toolTipText")); // NOI18N
        jLabel235.setName("jLabel235"); // NOI18N

        jLabel236.setText(resourceMap.getString("jLabel236.text")); // NOI18N
        jLabel236.setToolTipText(resourceMap.getString("jLabel236.toolTipText")); // NOI18N
        jLabel236.setName("jLabel236"); // NOI18N

        jCHRDR_Text.setName("jCHRDR_Text"); // NOI18N

        jLabel237.setText(resourceMap.getString("jLabel237.text")); // NOI18N
        jLabel237.setToolTipText(resourceMap.getString("jLabel237.toolTipText")); // NOI18N
        jLabel237.setName("jLabel237"); // NOI18N

        jSSPN_Text.setName("jSSPN_Text"); // NOI18N

        jSSPNOP_Text.setName("jSSPNOP_Text"); // NOI18N

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel232)
                    .addComponent(jLabel229)
                    .addComponent(jLabel236)
                    .addComponent(jLabel228)
                    .addComponent(jLabel220)
                    .addComponent(jLabel237)
                    .addComponent(jLabel235))
                .addGap(50, 50, 50)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jSAVSI_Text)
                    .addComponent(jSSPNOP_Text, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jCHRDTP_Text, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE)
                    .addComponent(jSSPNE_Text, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSSPN_Text, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jCHRDBP_Text, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jCHRDR_Text, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap(105, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel232)
                    .addComponent(jCHRDTP_Text, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel228)
                    .addComponent(jSSPNOP_Text, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel236)
                    .addComponent(jSSPNE_Text, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel229)
                    .addComponent(jSSPN_Text, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel220)
                    .addComponent(jCHRDBP_Text, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel237)
                    .addComponent(jCHRDR_Text, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel235)
                    .addComponent(jSAVSI_Text, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jPanel15.setName("jPanel15"); // NOI18N

        jLabel211.setText(resourceMap.getString("jLabel211.text")); // NOI18N
        jLabel211.setToolTipText(resourceMap.getString("jLabel211.toolTipText")); // NOI18N
        jLabel211.setName("jLabel211"); // NOI18N

        jCHSTAT_Text.setName("jCHSTAT_Text"); // NOI18N

        jLabel212.setText(resourceMap.getString("jLabel212.text")); // NOI18N
        jLabel212.setToolTipText(resourceMap.getString("jLabel212.toolTipText")); // NOI18N
        jLabel212.setName("jLabel212"); // NOI18N

        jDHADO_Text.setName("jDHADO_Text"); // NOI18N

        jSAVSO_Text.setName("jSAVSO_Text"); // NOI18N

        jLabel213.setText(resourceMap.getString("jLabel213.text")); // NOI18N
        jLabel213.setToolTipText(resourceMap.getString("jLabel213.toolTipText")); // NOI18N
        jLabel213.setName("jLabel213"); // NOI18N

        jLabel214.setText(resourceMap.getString("jLabel214.text")); // NOI18N
        jLabel214.setToolTipText(resourceMap.getString("jLabel214.toolTipText")); // NOI18N
        jLabel214.setName("jLabel214"); // NOI18N

        jLabel216.setText(resourceMap.getString("jLabel216.text")); // NOI18N
        jLabel216.setToolTipText(resourceMap.getString("jLabel216.toolTipText")); // NOI18N
        jLabel216.setName("jLabel216"); // NOI18N

        jTYPE_Text.setName("jTYPE_Text"); // NOI18N

        jLabel217.setText(resourceMap.getString("jLabel217.text")); // NOI18N
        jLabel217.setToolTipText(resourceMap.getString("jLabel217.toolTipText")); // NOI18N
        jLabel217.setName("jLabel217"); // NOI18N

        jDHDADI_Text.setName("jDHDADI_Text"); // NOI18N

        jTWISTA_Text.setName("jTWISTA_Text"); // NOI18N

        jLabel252.setText(resourceMap.getString("jLabel252.text")); // NOI18N
        jLabel252.setToolTipText(resourceMap.getString("jLabel252.toolTipText")); // NOI18N
        jLabel252.setName("jLabel252"); // NOI18N

        jAirfoil_Text.setName("jAirfoil_Text"); // NOI18N

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel214)
                            .addComponent(jLabel213)
                            .addComponent(jLabel216)
                            .addComponent(jLabel212)
                            .addComponent(jLabel211)
                            .addComponent(jLabel217))
                        .addGap(50, 50, 50)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTWISTA_Text, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jSAVSO_Text, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE)
                            .addComponent(jCHSTAT_Text, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jDHDADI_Text, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jDHADO_Text, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTYPE_Text, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(jLabel252)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jAirfoil_Text)))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel214)
                    .addComponent(jSAVSO_Text, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel212)
                    .addComponent(jTWISTA_Text, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel216)
                    .addComponent(jCHSTAT_Text, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel213)
                    .addComponent(jDHDADI_Text, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel211)
                    .addComponent(jDHADO_Text, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel217)
                    .addComponent(jTYPE_Text, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jAirfoil_Text, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel252))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel54Layout = new javax.swing.GroupLayout(jPanel54);
        jPanel54.setLayout(jPanel54Layout);
        jPanel54Layout.setHorizontalGroup(
            jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel54Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel54Layout.setVerticalGroup(
            jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel54Layout.createSequentialGroup()
                .addGroup(jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel18.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("jPanel18.border.title"))); // NOI18N
        jPanel18.setName("jPanel18"); // NOI18N

        jPanel20.setName("jPanel20"); // NOI18N

        jButton6.setText(resourceMap.getString("jButton6.text")); // NOI18N
        jButton6.setEnabled(false);
        jButton6.setName("jButton6"); // NOI18N

        jButton7.setText(resourceMap.getString("jButton7.text")); // NOI18N
        jButton7.setEnabled(false);
        jButton7.setName("jButton7"); // NOI18N

        jRadioButton3.setText(resourceMap.getString("jRadioButton3.text")); // NOI18N
        jRadioButton3.setEnabled(false);
        jRadioButton3.setName("jRadioButton3"); // NOI18N

        jRadioButton4.setText(resourceMap.getString("jRadioButton4.text")); // NOI18N
        jRadioButton4.setEnabled(false);
        jRadioButton4.setName("jRadioButton4"); // NOI18N

        jShowReference.setText(resourceMap.getString("jShowReference.text")); // NOI18N
        jShowReference.setName("jShowReference"); // NOI18N
        jShowReference.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jShowReferenceActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jShowReference, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE))
                .addGap(6, 6, 6)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jRadioButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jRadioButton3))
                .addGap(196, 196, 196))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton6)
                    .addComponent(jRadioButton3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton7)
                    .addComponent(jRadioButton4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jShowReference))
        );

        WingLogo1.setBackground(resourceMap.getColor("WingLogo1.background")); // NOI18N
        WingLogo1.setName("WingLogo1"); // NOI18N

        javax.swing.GroupLayout WingLogo1Layout = new javax.swing.GroupLayout(WingLogo1);
        WingLogo1.setLayout(WingLogo1Layout);
        WingLogo1Layout.setHorizontalGroup(
            WingLogo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 108, Short.MAX_VALUE)
        );
        WingLogo1Layout.setVerticalGroup(
            WingLogo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 92, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, 474, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(WingLogo1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(WingLogo1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel20, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel16.setBackground(resourceMap.getColor("jPanel16.background")); // NOI18N
        jPanel16.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel16.setName("jPanel16"); // NOI18N

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 464, Short.MAX_VALUE)
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 454, Short.MAX_VALUE)
        );

        jPanel55.setName("jPanel55"); // NOI18N

        jTitle.setFont(resourceMap.getFont("jTitle.font")); // NOI18N
        jTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jTitle.setText(resourceMap.getString("jTitle.text")); // NOI18N
        jTitle.setName("jTitle"); // NOI18N

        javax.swing.GroupLayout jPanel55Layout = new javax.swing.GroupLayout(jPanel55);
        jPanel55.setLayout(jPanel55Layout);
        jPanel55Layout.setHorizontalGroup(
            jPanel55Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel55Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 1078, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel55Layout.setVerticalGroup(
            jPanel55Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel55Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel55, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel54, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel18, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel55, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel54, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jShowReferenceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jShowReferenceActionPerformed
 
    }//GEN-LAST:event_jShowReferenceActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel WingLogo1;
    private javax.swing.JTextField jAirfoil_Text;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JTextField jCHRDBP_Text;
    private javax.swing.JTextField jCHRDR_Text;
    private javax.swing.JTextField jCHRDTP_Text;
    private javax.swing.JTextField jCHSTAT_Text;
    private javax.swing.JTextField jDHADO_Text;
    private javax.swing.JTextField jDHDADI_Text;
    private javax.swing.JLabel jLabel211;
    private javax.swing.JLabel jLabel212;
    private javax.swing.JLabel jLabel213;
    private javax.swing.JLabel jLabel214;
    private javax.swing.JLabel jLabel216;
    private javax.swing.JLabel jLabel217;
    private javax.swing.JLabel jLabel220;
    private javax.swing.JLabel jLabel228;
    private javax.swing.JLabel jLabel229;
    private javax.swing.JLabel jLabel232;
    private javax.swing.JLabel jLabel235;
    private javax.swing.JLabel jLabel236;
    private javax.swing.JLabel jLabel237;
    private javax.swing.JLabel jLabel252;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel54;
    private javax.swing.JPanel jPanel55;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JRadioButton jRadioButton4;
    private javax.swing.JTextField jSAVSI_Text;
    private javax.swing.JTextField jSAVSO_Text;
    private javax.swing.JTextField jSSPNE_Text;
    private javax.swing.JTextField jSSPNOP_Text;
    private javax.swing.JTextField jSSPN_Text;
    private javax.swing.JButton jShowReference;
    private javax.swing.JTextField jTWISTA_Text;
    private javax.swing.JTextField jTYPE_Text;
    private javax.swing.JLabel jTitle;
    // End of variables declaration//GEN-END:variables

    public JTextField getjAirfoil_Text() {
        return jAirfoil_Text;
    }

    public JTextField getjCHRDBP_Text() {
        return jCHRDBP_Text;
    }

    public JTextField getjCHRDR_Text() {
        return jCHRDR_Text;
    }

    public JTextField getjCHRDTP_Text() {
        return jCHRDTP_Text;
    }

    public JTextField getjCHSTAT_Text() {
        return jCHSTAT_Text;
    }

    public JTextField getjDHADO_Text() {
        return jDHADO_Text;
    }

    public JTextField getjDHDADI_Text() {
        return jDHDADI_Text;
    }

    public JTextField getjSAVSI_Text() {
        return jSAVSI_Text;
    }

    public JTextField getjSAVSO_Text() {
        return jSAVSO_Text;
    }

    public JTextField getjSSPNE_Text() {
        return jSSPNE_Text;
    }

    public JTextField getjSSPNOP_Text() {
        return jSSPNOP_Text;
    }

    public JTextField getjSSPN_Text() {
        return jSSPN_Text;
    }

    public JTextField getjTWISTA_Text() {
        return jTWISTA_Text;
    }

    public JTextField getjTYPE_Text() {
        return jTYPE_Text;
    }

    public void setjPanel55(JPanel jPanel55) {
        this.jPanel55 = jPanel55;
    }

    public void setjTitle(String Title) {
        this.jTitle.setText(Title);
    }

}
