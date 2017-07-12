package gradeBookClient.edu.asu.cse564.assign2.crud.gradebook.arao23.cli.ui;
import gradeBookClient.edu.asu.cse564.assign2.crud.gradebook.arao23.cli.util.Converter;
import java.net.URL;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;
import com.sun.jersey.api.client.ClientResponse;
import gradeBookClient.edu.asu.cse564.assign2.crud.gradebook.arao23.cli.model.GradeItem;
import javax.ws.rs.core.Response;
import javax.swing.JFrame;
import javax.xml.bind.JAXBException;
import java.net.URI;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

/**
 *
 * @author Abhishek
 */
public class ProfessorUI extends javax.swing.JFrame {
    private GradeBookClient cliGradeBook;
    private URI resourceURI;
    private boolean ifButtonIsDel = false;

    private void errDial(String errorMessage) {
        JOptionPane op = new JOptionPane(errorMessage, JOptionPane.ERROR_MESSAGE);
        JDialog box = op.createDialog("Handle Errors");
        box.setAlwaysOnTop(true);
        box.setVisible(true);
    }

    private void formClear() {
        ID_STU_TEXT.setText("");
        FEEDBACK_GOT.setText("");
        NAME_GRADEITEM.setText("");
        MARKS_OBTAINED.setText("");
        MAX_WEIGHTAGE.setText("");
        APPEAL_TEXT.setText("");
        MEDIA_TYPE_TEXT.setText("");
        URI_LOCATION_LABEL.setText("");
        RESPONSE_TEXT.setText("");
    }

    private void screenStartUp() {
        if (ID_STU_TEXT.getText().equals("")) {
            ID_STU_TEXT.setText("0");
        }
        if (FEEDBACK_GOT.getText().equals("")) {
            FEEDBACK_GOT.setText("");
        }
        if (NAME_GRADEITEM.getText().equals("")) {
            NAME_GRADEITEM.setText("");
        }
        if (MARKS_OBTAINED.getText().equals("")) {
            MARKS_OBTAINED.setText("0");
        }
        if (MAX_WEIGHTAGE.getText().equals("")) {
            MAX_WEIGHTAGE.setText("0");
        }
        if (APPEAL_TEXT.getText().equals("")) {
            APPEAL_TEXT.setText("");
        }
    }

    public ProfessorUI() {
        initComponents();
        RADIO1_CREATE.setSelected(true);
        firstButtonClick();
        cliGradeBook = new GradeBookClient();
        APPEAL_TEXT.disable();

    }

    public static boolean checkNumber(String val) {
        try {
            int num = Integer.parseInt(val);
            return true;
        } catch (NumberFormatException exx) {
            return false;
        }
    }

    private GradeItem generatePOJO() {

        screenStartUp();
        GradeItem gra = new GradeItem();
        gra.setFeedback(FEEDBACK_GOT.getText());
        gra.setGradeItemName(NAME_GRADEITEM.getText());
        gra.setPercentageAllocation(Integer.parseInt(MAX_WEIGHTAGE.getText()));
        gra.setMarksObtained(Integer.parseInt(MARKS_OBTAINED.getText()));
        return gra;
    }
    private void populateForm(ClientResponse clientResponse) {

        if (ifButtonIsDel) {
            try {
                RESPONSE_TEXT.setText(Integer.toString(clientResponse.getStatus()));
                MEDIA_TYPE_TEXT.setText(clientResponse.getType().toString());
                return;
            } catch (Exception e) {
                MEDIA_TYPE_TEXT.setText("application/xml");
                return;
            }
        }
        if (!ifButtonIsDel) {
            String ent = clientResponse.getEntity(String.class);

            try {
                if ((clientResponse.getStatus() == Response.Status.OK.getStatusCode()) || (clientResponse.getStatus() == Response.Status.CREATED.getStatusCode())) {
                    GradeItem gra = (GradeItem) Converter.convertFromXmlToObject(ent, GradeItem.class);
                    //Student ID
                    NAME_GRADEITEM.setText(gra.getGradeItemName());
                    FEEDBACK_GOT.setText(gra.getFeedback());
                    APPEAL_TEXT.setText(gra.getAppeal());
                    MARKS_OBTAINED.setText(String.valueOf(gra.getMarksObtained()));
                    MAX_WEIGHTAGE.setText(String.valueOf(gra.getPercentageAllocation()));

                } else {
                    ID_STU_TEXT.setText("");
                    NAME_GRADEITEM.setText("");
                    FEEDBACK_GOT.setText("");
                    MARKS_OBTAINED.setText("");
                    MAX_WEIGHTAGE.setText("");
                    APPEAL_TEXT.setText("");
                }
            } catch (JAXBException e) {
                e.printStackTrace();
            }
        }
        RESPONSE_TEXT.setText(Integer.toString(clientResponse.getStatus()));
        MEDIA_TYPE_TEXT.setText(clientResponse.getType().getType() + "/" + clientResponse.getType().getSubtype());
        
        if (clientResponse.getStatus() == Response.Status.CREATED.getStatusCode()) {
            URI_LOCATION_LABEL.setText(clientResponse.getLocation().toString());
        } else {
            URI_LOCATION_LABEL.setText("");
        }
    }

    private void firstButtonClick() {
        RADIO3_UPDATE.setSelected(false);
        RADIO2_READ.setSelected(false);
        RADIO4_DELETE.setSelected(false);

        ID_STU_TEXT.setVisible(true);
        NAME_GRADEITEM.setVisible(true);
        MAX_WEIGHTAGE.setVisible(true);
        MARKS_OBTAINED.setVisible(false);
        FEEDBACK_GOT.setVisible(false);
        APPEAL_TEXT.setVisible(false);
        ID_LABEL.setVisible(true);
        GRADEITEM_LABEL.setVisible(true);
        MAX_WT_LABEL.setVisible(true);
        MARKS_OBTAINED_LABEL.setVisible(false);
        FEEDBACK_LABEL.setVisible(false);
        APEAL_TEXT_LABEL.setVisible(false);
    }
    
    private boolean chkForEmptyLastButton() {
            if ((ID_STU_TEXT.getText().equalsIgnoreCase("")) || (NAME_GRADEITEM.getText().equalsIgnoreCase(""))) {
                errDial("Student Id and Grade Item Name are mandatory !!!");
                return false;
            }
            if (!(checkNumber(ID_STU_TEXT.getText()))) {
                errDial("Student Id should be an Integer !!!");
                return false;
            }
        
        return true;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        RADIO1_CREATE = new javax.swing.JRadioButton();
        RADIO3_UPDATE = new javax.swing.JRadioButton();
        RADIO4_DELETE = new javax.swing.JRadioButton();
        ID_LABEL = new javax.swing.JLabel();
        GRADEITEM_LABEL = new javax.swing.JLabel();
        MAX_WT_LABEL = new javax.swing.JLabel();
        MARKS_OBTAINED_LABEL = new javax.swing.JLabel();
        FEEDBACK_LABEL = new javax.swing.JLabel();
        NAME_GRADEITEM = new javax.swing.JTextField();
        APPEAL_TEXT = new javax.swing.JTextField();
        ID_STU_TEXT = new javax.swing.JTextField();
        MAX_WEIGHTAGE = new javax.swing.JTextField();
        MARKS_OBTAINED = new javax.swing.JTextField();
        APEAL_TEXT_LABEL = new javax.swing.JLabel();
        RADIO2_READ = new javax.swing.JRadioButton();
        MAIN_BUTTON_SUBMIT = new javax.swing.JButton();
        MEDIA_LABEL = new javax.swing.JLabel();
        RESPONSE_LABEL = new javax.swing.JLabel();
        RESPONSE_TEXT = new javax.swing.JTextField();
        LOCATION_URI_LABEL = new javax.swing.JLabel();
        URI_LOCATION_LABEL = new javax.swing.JTextField();
        MEDIA_TYPE_TEXT = new javax.swing.JTextField();
        FEEDBACK_GOT = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator5 = new javax.swing.JSeparator();
        BUTTON1_BACK = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        RADIO1_CREATE.setText("Create ");
        RADIO1_CREATE.setBorder(new javax.swing.border.MatteBorder(null));
        RADIO1_CREATE.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RADIO1_CREATEMouseClicked(evt);
            }
        });
        RADIO1_CREATE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RADIO1_CREATEActionPerformed(evt);
            }
        });
        getContentPane().add(RADIO1_CREATE, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, -1, -1));

        RADIO3_UPDATE.setText("Update ");
        RADIO3_UPDATE.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RADIO3_UPDATEMouseClicked(evt);
            }
        });
        getContentPane().add(RADIO3_UPDATE, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, -1, -1));

        RADIO4_DELETE.setText("Delete");
        RADIO4_DELETE.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RADIO4_DELETEMouseClicked(evt);
            }
        });
        getContentPane().add(RADIO4_DELETE, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, -1, -1));

        ID_LABEL.setText("Student Id");
        getContentPane().add(ID_LABEL, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 110, -1, -1));

        GRADEITEM_LABEL.setText("Grade Item Name");
        getContentPane().add(GRADEITEM_LABEL, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 140, -1, -1));

        MAX_WT_LABEL.setText("Weightage (max wt)");
        getContentPane().add(MAX_WT_LABEL, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 170, -1, -1));

        MARKS_OBTAINED_LABEL.setText("Marks/Grades obtained");
        getContentPane().add(MARKS_OBTAINED_LABEL, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 200, -1, -1));

        FEEDBACK_LABEL.setText("Feedback");
        getContentPane().add(FEEDBACK_LABEL, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 230, -1, -1));
        getContentPane().add(NAME_GRADEITEM, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 140, 210, -1));

        APPEAL_TEXT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                APPEAL_TEXTActionPerformed(evt);
            }
        });
        getContentPane().add(APPEAL_TEXT, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 260, 210, -1));
        getContentPane().add(ID_STU_TEXT, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 110, 210, -1));
        getContentPane().add(MAX_WEIGHTAGE, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 170, 210, -1));
        getContentPane().add(MARKS_OBTAINED, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 200, 210, -1));

        APEAL_TEXT_LABEL.setText("View Appeal");
        getContentPane().add(APEAL_TEXT_LABEL, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 260, -1, -1));

        RADIO2_READ.setText("Read");
        RADIO2_READ.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RADIO2_READMouseClicked(evt);
            }
        });
        getContentPane().add(RADIO2_READ, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, -1, -1));

        MAIN_BUTTON_SUBMIT.setText("Submit");
        MAIN_BUTTON_SUBMIT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MAIN_BUTTON_SUBMITActionPerformed(evt);
            }
        });
        getContentPane().add(MAIN_BUTTON_SUBMIT, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 390, 100, 30));

        MEDIA_LABEL.setText("Media Type");
        getContentPane().add(MEDIA_LABEL, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 320, -1, -1));

        RESPONSE_LABEL.setText("Response Code");
        getContentPane().add(RESPONSE_LABEL, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 320, -1, -1));
        getContentPane().add(RESPONSE_TEXT, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 320, 170, -1));

        LOCATION_URI_LABEL.setText("Location URI");
        getContentPane().add(LOCATION_URI_LABEL, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 360, -1, -1));
        getContentPane().add(URI_LOCATION_LABEL, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 360, 490, -1));
        getContentPane().add(MEDIA_TYPE_TEXT, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 320, 200, -1));
        getContentPane().add(FEEDBACK_GOT, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 230, 210, -1));
        getContentPane().add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 570, 10));
        getContentPane().add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 300, 590, 10));
        getContentPane().add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, 570, 10));

        BUTTON1_BACK.setText("Back");
        BUTTON1_BACK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BUTTON1_BACKActionPerformed(evt);
            }
        });
        getContentPane().add(BUTTON1_BACK, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 390, 100, 30));

        setSize(new java.awt.Dimension(675, 467));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private boolean chkForEmptyFields() {
        if ((ID_STU_TEXT.getText().equalsIgnoreCase("")) || (NAME_GRADEITEM.getText().equalsIgnoreCase(""))) {
            errDial("Student Id and Grade Item Name are  mandatory !!!");
            return false;
        }
        if (!(checkNumber(ID_STU_TEXT.getText()))) {
            errDial("Student Id should be an Integer !!!");
            return false;
        }
        return true;
    }

    private void MAIN_BUTTON_SUBMITActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MAIN_BUTTON_SUBMITActionPerformed

        /* CREATE */
        if ((RADIO1_CREATE.isSelected() == true)) {
            if (!(chkForEmptyLastButton())) {
                return;
            }
            ifButtonIsDel = false;
            if (!(MAX_WEIGHTAGE.getText().equalsIgnoreCase(""))) {
                if (!(checkNumber(MAX_WEIGHTAGE.getText()))) {
                    errDial("Weightage should be an Integer !!!");
                    return;
                }
            }
            ClientResponse clientResponse = cliGradeBook.create(ID_STU_TEXT.getText(), this.generatePOJO());
            resourceURI = clientResponse.getLocation();
            this.populateForm(clientResponse);
        }
        /* READ */
        if (RADIO2_READ.isSelected() == true) {
            if (!(chkForEmptyFields())) {
                return;
            }
            ifButtonIsDel = false;
            ClientResponse clientResponse = cliGradeBook.read(ClientResponse.class, ID_STU_TEXT.getText(), NAME_GRADEITEM.getText());
            this.populateForm(clientResponse);
        }
        /* UPDATE */
        if (RADIO3_UPDATE.isSelected() == true) {
            if (!(chkForEmptyFields())) {
                return;
            }
            ifButtonIsDel = false;
            if ((MARKS_OBTAINED.getText().equalsIgnoreCase("")) && (FEEDBACK_GOT.getText().equalsIgnoreCase(""))) {
                errDial("Provide either Grades or Feedback !!!");
                return;
            }
            if (!(checkNumber(MARKS_OBTAINED.getText()))) {
                errDial("Grades should be an Integer !!!");
                return;
            }
            if ((!(MAX_WEIGHTAGE.getText().equalsIgnoreCase(""))) && (!(checkNumber(MAX_WEIGHTAGE.getText())))) {
                errDial("Weightage should be an Integer !!!");
                return;
            }

            ClientResponse clientResponse = cliGradeBook.update(ID_STU_TEXT.getText(), this.generatePOJO());
            resourceURI = clientResponse.getLocation();
            this.populateForm(clientResponse);
        }
        /* DELETE */
        if ((RADIO4_DELETE.isSelected() == true)) {
            if (!(chkForEmptyLastButton())) {
                return;
            }
            ifButtonIsDel = true;
            ClientResponse clientResponse = cliGradeBook.delete(ID_STU_TEXT.getText(), NAME_GRADEITEM.getText());
            this.populateForm(clientResponse);
        }

    }//GEN-LAST:event_MAIN_BUTTON_SUBMITActionPerformed


    private void RADIO1_CREATEMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RADIO1_CREATEMouseClicked
        formClear();
        firstButtonClick();
    }//GEN-LAST:event_RADIO1_CREATEMouseClicked

    private void RADIO3_UPDATEMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RADIO3_UPDATEMouseClicked
        formClear();
        RADIO1_CREATE.setSelected(false);
        RADIO2_READ.setSelected(false);
        RADIO4_DELETE.setSelected(false);

        ID_STU_TEXT.setVisible(true);
        NAME_GRADEITEM.setVisible(true);
        MAX_WEIGHTAGE.setVisible(true);
        MARKS_OBTAINED.setVisible(true);
        FEEDBACK_GOT.setVisible(true);
        APPEAL_TEXT.setVisible(false);

        ID_LABEL.setVisible(true);
        GRADEITEM_LABEL.setVisible(true);
        MAX_WT_LABEL.setVisible(true);
        MARKS_OBTAINED_LABEL.setVisible(true);
        FEEDBACK_LABEL.setVisible(true);
        APEAL_TEXT_LABEL.setVisible(false);


    }//GEN-LAST:event_RADIO3_UPDATEMouseClicked

    private void RADIO1_CREATEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RADIO1_CREATEActionPerformed
        // TODO add your handling code here:
        if (RADIO1_CREATE.isSelected() == true) {
            APPEAL_TEXT.setVisible(false);
        }
    }//GEN-LAST:event_RADIO1_CREATEActionPerformed

    private void RADIO4_DELETEMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RADIO4_DELETEMouseClicked
        formClear();

        RADIO3_UPDATE.setSelected(false);
        RADIO2_READ.setSelected(false);
        RADIO1_CREATE.setSelected(false);

        ID_STU_TEXT.setVisible(true);
        NAME_GRADEITEM.setVisible(true);
        MAX_WEIGHTAGE.setVisible(false);
        MARKS_OBTAINED.setVisible(false);
        FEEDBACK_GOT.setVisible(false);
        APPEAL_TEXT.setVisible(false);

        ID_LABEL.setVisible(true);
        GRADEITEM_LABEL.setVisible(true);
        MAX_WT_LABEL.setVisible(false);
        MARKS_OBTAINED_LABEL.setVisible(false);
        FEEDBACK_LABEL.setVisible(false);
        APEAL_TEXT_LABEL.setVisible(false);
    }//GEN-LAST:event_RADIO4_DELETEMouseClicked

    private void RADIO2_READMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RADIO2_READMouseClicked
        formClear();
        RADIO3_UPDATE.setSelected(false);
        RADIO4_DELETE.setSelected(false);
        RADIO1_CREATE.setSelected(false);

        ID_STU_TEXT.setVisible(true);
        NAME_GRADEITEM.setVisible(true);
        MAX_WEIGHTAGE.setVisible(true);
        MARKS_OBTAINED.setVisible(true);
        FEEDBACK_GOT.setVisible(true);
        APPEAL_TEXT.setVisible(true);

        ID_LABEL.setVisible(true);
        GRADEITEM_LABEL.setVisible(true);
        MAX_WT_LABEL.setVisible(true);
        MARKS_OBTAINED_LABEL.setVisible(true);
        FEEDBACK_LABEL.setVisible(true);
        APEAL_TEXT_LABEL.setVisible(true);

    }//GEN-LAST:event_RADIO2_READMouseClicked

    private void APPEAL_TEXTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_APPEAL_TEXTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_APPEAL_TEXTActionPerformed

    private void BUTTON1_BACKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BUTTON1_BACKActionPerformed
        MainScreenUI mainUI = new MainScreenUI();
        mainUI.setVisible(true);
        setVisible(false);
    }//GEN-LAST:event_BUTTON1_BACKActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ProfessorUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ProfessorUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ProfessorUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ProfessorUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ProfessorUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel APEAL_TEXT_LABEL;
    private javax.swing.JTextField APPEAL_TEXT;
    private javax.swing.JButton BUTTON1_BACK;
    private javax.swing.JTextField FEEDBACK_GOT;
    private javax.swing.JLabel FEEDBACK_LABEL;
    private javax.swing.JLabel GRADEITEM_LABEL;
    private javax.swing.JLabel ID_LABEL;
    private javax.swing.JTextField ID_STU_TEXT;
    private javax.swing.JLabel LOCATION_URI_LABEL;
    private javax.swing.JButton MAIN_BUTTON_SUBMIT;
    private javax.swing.JTextField MARKS_OBTAINED;
    private javax.swing.JLabel MARKS_OBTAINED_LABEL;
    private javax.swing.JTextField MAX_WEIGHTAGE;
    private javax.swing.JLabel MAX_WT_LABEL;
    private javax.swing.JLabel MEDIA_LABEL;
    private javax.swing.JTextField MEDIA_TYPE_TEXT;
    private javax.swing.JTextField NAME_GRADEITEM;
    private javax.swing.JRadioButton RADIO1_CREATE;
    private javax.swing.JRadioButton RADIO2_READ;
    private javax.swing.JRadioButton RADIO3_UPDATE;
    private javax.swing.JRadioButton RADIO4_DELETE;
    private javax.swing.JLabel RESPONSE_LABEL;
    private javax.swing.JTextField RESPONSE_TEXT;
    private javax.swing.JTextField URI_LOCATION_LABEL;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    // End of variables declaration//GEN-END:variables
}
