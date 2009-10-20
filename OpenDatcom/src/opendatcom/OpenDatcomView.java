/*
 * OpenDatcomView.java
 */

package opendatcom;

import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.TaskMonitor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

/**
 * The application's main frame.
 */
public class OpenDatcomView extends FrameView {
    // Golbal Stuff
    instanceData g_Data = new instanceData();
    Constants globals = new Constants();
    ParserUtility util = new ParserUtility();
    JFileChooser fc;
    File currentFile;

    // Controllers
    BodyController bodyC;
    SynthesisController synthC;
    FlightConditionsController flightC;
    FlightSurfaceController wingC, hTailC, vTailC;
    ImportExportService in;

    //
    OutputView output;

    // Temp variables
    FlightSurfaceModel ht, hw;
    double temp [][];

    public OpenDatcomView(SingleFrameApplication app) {
        super(app);
        initComponents();

        // Init the file chooser
        fc = new JFileChooser();
        fc.setFileFilter(new xmlFilter());
        fc.setCurrentDirectory(new java.io.File("."));

        // Init the panels
        bodyC   =   new BodyController();
        flightC =   new FlightConditionsController();
        synthC  =   new SynthesisController();
        wingC   =   new FlightSurfaceController(FlightSurfaceModel.SURFACE_TYPE.MAIN_WING);
        hTailC  =   new FlightSurfaceController(FlightSurfaceModel.SURFACE_TYPE.HORIZONTAL_TAIL);
        vTailC  =   new FlightSurfaceController(FlightSurfaceModel.SURFACE_TYPE.VERTICAL_TAIL);

        // Register the controllers as needed
        in = new ImportExportService();
        in.RegisterController(flightC);
        in.RegisterController(synthC);
        in.RegisterController(wingC);
        in.RegisterController(hTailC);
        in.RegisterController(vTailC);
        in.RegisterController(bodyC);
        
        output = new OutputView();
        output.registerController(flightC);
        output.registerController(bodyC);
        output.registerController(synthC);
        output.registerController(wingC);
        output.registerController(hTailC);
        output.registerController(vTailC);

        // Add the views to the tabbed pane
        jFlightTab.add(flightC.getView());
        jBodyTab.add(bodyC.getView());
        jSynthTab.add(synthC.getView());
        jHWingTab.add(wingC.getView());
        jHTailTab.add(hTailC.getView());
        jVTailTab.add(vTailC.getView());
        jOutputTab.add(output);

        
        // status bar initialization - message timeout, idle icon and busy animation, etc
        ResourceMap resourceMap = getResourceMap();
        int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
        messageTimer = new Timer(messageTimeout, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                statusMessageLabel.setText("");
            }
        });
        messageTimer.setRepeats(false);
        int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
        for (int i = 0; i < busyIcons.length; i++) {
            busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
        }
        busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
                statusAnimationLabel.setIcon(busyIcons[busyIconIndex]);
            }
        });
        idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
        statusAnimationLabel.setIcon(idleIcon);
        progressBar.setVisible(false);

        // connecting action tasks to status bar via TaskMonitor
        TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
        taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            @Override
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                String propertyName = evt.getPropertyName();
                if ("started".equals(propertyName)) {
                    if (!busyIconTimer.isRunning()) {
                        statusAnimationLabel.setIcon(busyIcons[0]);
                        busyIconIndex = 0;
                        busyIconTimer.start();
                    }
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(true);
                } else if ("done".equals(propertyName)) {
                    busyIconTimer.stop();
                    statusAnimationLabel.setIcon(idleIcon);
                    progressBar.setVisible(false);
                    progressBar.setValue(0);
                } else if ("message".equals(propertyName)) {
                    String text = (String)(evt.getNewValue());
                    statusMessageLabel.setText((text == null) ? "" : text);
                    messageTimer.restart();
                } else if ("progress".equals(propertyName)) {
                    int value = (Integer)(evt.getNewValue());
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(false);
                    progressBar.setValue(value);
                }
            }
        });
    }

    @Action
    public void showAboutBox() {
        if (aboutBox == null) {
            JFrame mainFrame = OpenDatcomApp.getApplication().getMainFrame();
            aboutBox = new OpenDatcomAboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        OpenDatcomApp.getApplication().show(aboutBox);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        jTabs = new javax.swing.JTabbedPane();
        jMainTab = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jPanel27 = new javax.swing.JPanel();
        jPanel28 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jUnitsSelect = new javax.swing.JComboBox();
        jPanel29 = new javax.swing.JPanel();
        jFlightTab = new javax.swing.JPanel();
        jSynthTab = new javax.swing.JPanel();
        jHWingTab = new javax.swing.JPanel();
        jHTailTab = new javax.swing.JPanel();
        jVTailTab = new javax.swing.JPanel();
        jBodyTab = new javax.swing.JPanel();
        jOutputTab = new javax.swing.JPanel();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu jOpenMenu = new javax.swing.JMenu();
        jOpen = new javax.swing.JMenuItem();
        jLoadTemplate = new javax.swing.JMenuItem();
        JSaveMenu = new javax.swing.JMenuItem();
        jSaveAs = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JSeparator();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
        statusPanel = new javax.swing.JPanel();
        javax.swing.JSeparator statusPanelSeparator = new javax.swing.JSeparator();
        statusMessageLabel = new javax.swing.JLabel();
        statusAnimationLabel = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();

        mainPanel.setName("mainPanel"); // NOI18N

        jTabs.setName(""); // NOI18N
        jTabs.setPreferredSize(new java.awt.Dimension(1000, 1000));
        jTabs.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTabsFocusGained(evt);
            }
        });

        jMainTab.setName("jMainTab"); // NOI18N

        jPanel10.setName("jPanel10"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(opendatcom.OpenDatcomApp.class).getContext().getResourceMap(OpenDatcomView.class);
        jLabel5.setFont(resourceMap.getFont("jLabel5.font")); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 1078, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        jPanel5.setName("jPanel5"); // NOI18N

        jPanel6.setBackground(resourceMap.getColor("jPanel6.background")); // NOI18N
        jPanel6.setForeground(resourceMap.getColor("jPanel6.foreground")); // NOI18N
        jPanel6.setName("jPanel6"); // NOI18N

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 476, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 457, Short.MAX_VALUE)
        );

        jPanel14.setName("jPanel14"); // NOI18N

        jPanel27.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("jPanel27.border.title"))); // NOI18N
        jPanel27.setName("jPanel27"); // NOI18N

        jPanel28.setName("jPanel28"); // NOI18N

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        jUnitsSelect.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Imperial", "Metric" }));
        jUnitsSelect.setName("jUnitsSelect"); // NOI18N

        javax.swing.GroupLayout jPanel28Layout = new javax.swing.GroupLayout(jPanel28);
        jPanel28.setLayout(jPanel28Layout);
        jPanel28Layout.setHorizontalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jUnitsSelect, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel28Layout.setVerticalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel28Layout.createSequentialGroup()
                .addContainerGap(125, Short.MAX_VALUE)
                .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jUnitsSelect, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel29.setName("jPanel29"); // NOI18N

        javax.swing.GroupLayout jPanel29Layout = new javax.swing.GroupLayout(jPanel29);
        jPanel29.setLayout(jPanel29Layout);
        jPanel29Layout.setHorizontalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 287, Short.MAX_VALUE)
        );
        jPanel29Layout.setVerticalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 156, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel27Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jPanel28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel27Layout.createSequentialGroup()
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addContainerGap(249, Short.MAX_VALUE)
                .addComponent(jPanel27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout jMainTabLayout = new javax.swing.GroupLayout(jMainTab);
        jMainTab.setLayout(jMainTabLayout);
        jMainTabLayout.setHorizontalGroup(
            jMainTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jMainTabLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jMainTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jMainTabLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jMainTabLayout.setVerticalGroup(
            jMainTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jMainTabLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabs.addTab(resourceMap.getString("jMainTab.TabConstraints.tabTitle"), jMainTab); // NOI18N

        jFlightTab.setName("jFlightTab"); // NOI18N
        jFlightTab.setLayout(new java.awt.GridLayout(1, 0));
        jTabs.addTab(resourceMap.getString("jFlightTab.TabConstraints.tabTitle"), jFlightTab); // NOI18N

        jSynthTab.setName("jSynthTab"); // NOI18N
        jSynthTab.setLayout(new java.awt.GridLayout(1, 0));
        jTabs.addTab(resourceMap.getString("jSynthTab.TabConstraints.tabTitle"), jSynthTab); // NOI18N

        jHWingTab.setName("jHWingTab"); // NOI18N
        jHWingTab.setLayout(new java.awt.GridLayout(1, 0));
        jTabs.addTab(resourceMap.getString("jHWingTab.TabConstraints.tabTitle"), jHWingTab); // NOI18N

        jHTailTab.setName("jHTailTab"); // NOI18N
        jHTailTab.setLayout(new java.awt.GridLayout(1, 0));
        jTabs.addTab(resourceMap.getString("jHTailTab.TabConstraints.tabTitle"), jHTailTab); // NOI18N

        jVTailTab.setName("jVTailTab"); // NOI18N
        jVTailTab.setLayout(new java.awt.GridLayout(1, 0));
        jTabs.addTab(resourceMap.getString("jVTailTab.TabConstraints.tabTitle"), jVTailTab); // NOI18N

        jBodyTab.setName("jBodyTab"); // NOI18N
        jBodyTab.setLayout(new java.awt.GridLayout(1, 0));
        jTabs.addTab(resourceMap.getString("jBodyTab.TabConstraints.tabTitle"), jBodyTab); // NOI18N

        jOutputTab.setName("outputPane"); // NOI18N
        jOutputTab.setLayout(new java.awt.GridLayout(1, 0));
        jTabs.addTab(resourceMap.getString("outputPane.TabConstraints.tabTitle"), jOutputTab); // NOI18N

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabs, javax.swing.GroupLayout.DEFAULT_SIZE, 1123, Short.MAX_VALUE)
                .addContainerGap())
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabs, javax.swing.GroupLayout.DEFAULT_SIZE, 597, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        menuBar.setName("menuBar"); // NOI18N

        jOpenMenu.setText(resourceMap.getString("jOpenMenu.text")); // NOI18N
        jOpenMenu.setName("jOpenMenu"); // NOI18N
        jOpenMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jOpenMenuActionPerformed(evt);
            }
        });

        jOpen.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        jOpen.setText(resourceMap.getString("jOpen.text")); // NOI18N
        jOpen.setName("jOpen"); // NOI18N
        jOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jOpenActionPerformed(evt);
            }
        });
        jOpenMenu.add(jOpen);

        jLoadTemplate.setText(resourceMap.getString("jLoadTemplate.text")); // NOI18N
        jLoadTemplate.setName("jLoadTemplate"); // NOI18N
        jLoadTemplate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jLoadTemplateActionPerformed(evt);
            }
        });
        jOpenMenu.add(jLoadTemplate);

        JSaveMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        JSaveMenu.setText(resourceMap.getString("JSaveMenu.text")); // NOI18N
        JSaveMenu.setName("JSaveMenu"); // NOI18N
        JSaveMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JSaveMenuActionPerformed(evt);
            }
        });
        jOpenMenu.add(JSaveMenu);

        jSaveAs.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jSaveAs.setText(resourceMap.getString("jSaveAs.text")); // NOI18N
        jSaveAs.setName("jSaveAs"); // NOI18N
        jSaveAs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jSaveAsActionPerformed(evt);
            }
        });
        jOpenMenu.add(jSaveAs);

        jSeparator1.setName("jSeparator1"); // NOI18N
        jOpenMenu.add(jSeparator1);

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(opendatcom.OpenDatcomApp.class).getContext().getActionMap(OpenDatcomView.class, this);
        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        jOpenMenu.add(exitMenuItem);

        menuBar.add(jOpenMenu);

        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        statusPanel.setName("statusPanel"); // NOI18N

        statusPanelSeparator.setName("statusPanelSeparator"); // NOI18N

        statusMessageLabel.setName("statusMessageLabel"); // NOI18N

        statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

        progressBar.setName("progressBar"); // NOI18N

        javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(statusPanelSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, 1143, Short.MAX_VALUE)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(statusMessageLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 973, Short.MAX_VALUE)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusAnimationLabel)
                .addContainerGap())
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addComponent(statusPanelSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(statusMessageLabel)
                    .addComponent(statusAnimationLabel)
                    .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3))
        );

        setComponent(mainPanel);
        setMenuBar(menuBar);
        setStatusBar(statusPanel);
    }// </editor-fold>//GEN-END:initComponents

    private void jTabsFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTabsFocusGained

    }//GEN-LAST:event_jTabsFocusGained

    private void jOpenMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jOpenMenuActionPerformed
        
    }//GEN-LAST:event_jOpenMenuActionPerformed

    /**
     * Save dialog
     * @param evt
     */
    private void JSaveMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JSaveMenuActionPerformed

        fc.setDialogTitle("Save:");
        // Check if user has saved to a file before
        if(currentFile == null)
        {
            int check = fc.showSaveDialog(this.getComponent());
            if(check == JFileChooser.APPROVE_OPTION)
            {
                currentFile = fc.getSelectedFile();
                in.writeXML(currentFile);
            }
            else if(check == JFileChooser.CANCEL_OPTION)
            {
                return;
            }
        }
        // If user has already saved, don't display the select file box
        else
        {
            in.writeXML(currentFile);
        }
        
    }//GEN-LAST:event_JSaveMenuActionPerformed

    /**
     * Open/Load dialog
     * @param evt
     */
    private void jOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jOpenActionPerformed
        fc.setDialogTitle("Open Project:");
        int check = fc.showOpenDialog(this.getComponent());
        if(check == JFileChooser.APPROVE_OPTION)
        {
            currentFile = fc.getSelectedFile();
            in.importFile(currentFile);
        }
        else if(check == JFileChooser.CANCEL_OPTION)
        {
            return;
        }
    }//GEN-LAST:event_jOpenActionPerformed

    /**
     * Save As dialog
     * @param evt
     */
    private void jSaveAsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jSaveAsActionPerformed

        fc.setDialogTitle("Save As:");
        int check = fc.showSaveDialog(this.getComponent());
        if(check == JFileChooser.APPROVE_OPTION)
        {
            currentFile = fc.getSelectedFile();
            in.writeXML(currentFile);
        }
        else if(check == JFileChooser.CANCEL_OPTION)
        {
            return;
        }
    }//GEN-LAST:event_jSaveAsActionPerformed

    /**
     * Loads a file but does not set the currentFile flag which means that that
     * program will not default save to the template file, instead the user will
     * be prompted to save as a new file.
     * @param evt
     */
    private void jLoadTemplateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jLoadTemplateActionPerformed
        fc.setDialogTitle("Select Template:");
        int check = fc.showOpenDialog(this.getComponent());
        if(check == JFileChooser.APPROVE_OPTION)
        {
            File target = fc.getSelectedFile();
            in.importFile(target);
        }
        else if(check == JFileChooser.CANCEL_OPTION)
        {

        }
    }//GEN-LAST:event_jLoadTemplateActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem JSaveMenu;
    private javax.swing.JPanel jBodyTab;
    private javax.swing.JPanel jFlightTab;
    private javax.swing.JPanel jHTailTab;
    private javax.swing.JPanel jHWingTab;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JMenuItem jLoadTemplate;
    private javax.swing.JPanel jMainTab;
    private javax.swing.JMenuItem jOpen;
    private javax.swing.JPanel jOutputTab;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JMenuItem jSaveAs;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JPanel jSynthTab;
    private javax.swing.JTabbedPane jTabs;
    private javax.swing.JComboBox jUnitsSelect;
    private javax.swing.JPanel jVTailTab;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    private javax.swing.JPanel statusPanel;
    // End of variables declaration//GEN-END:variables

    private final Timer messageTimer;
    private final Timer busyIconTimer;
    private final Icon idleIcon;
    private final Icon[] busyIcons = new Icon[15];
    private int busyIconIndex = 0;

    private JDialog aboutBox;

}
