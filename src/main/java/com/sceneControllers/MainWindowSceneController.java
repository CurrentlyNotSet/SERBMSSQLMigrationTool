/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sceneControllers;

import com.Migration.*;
import com.model.migrationStatusModel;
import com.sql.sqlMigrationStatus;
import com.sql.sqlServerEmailControl;
import com.sql.sqlUsers;
import com.util.Global;
import com.util.StringUtilities;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Andrew
 */
public class MainWindowSceneController implements Initializable {

    Stage mainstage;
    private double X, Y;
    MainWindowSceneController control;

    @FXML
    private MenuBar menuBar;
    @FXML
    private ProgressBar progressbar;
    @FXML
    private Label progressBarLabel;
    @FXML
    private Button MigrateULPCaseButton;
    @FXML
    private Button MigrateMEDCaseButton;
    @FXML
    private Button MigrateREPCaseButton;
    @FXML
    private Button MigrateCSCCaseButton;
    @FXML
    private Button MigrateCMDSCaseButton;
    @FXML
    private Button MigrateContactsButton;
    @FXML
    private Button MigrateORGCaseButton;
    @FXML
    private Button MigrateUsersButton;
    @FXML
    private Button MigratePublicRecordsButton;
    @FXML
    private Button MigrateDocumentsButton;
    @FXML
    private Button MigrateEmployersButton;
    @FXML
    private Button MigrateHearingsCaseButton;
    @FXML
    private Button MigrateSystemDefaultsButton;
    @FXML
    private TextField MigrateULPCaseTextField;
    @FXML
    private TextField MigrateMEDCaseTextField;
    @FXML
    private TextField MigrateREPCaseTextField;
    @FXML
    private TextField MigrateCSCCaseTextField;
    @FXML
    private TextField MigrateCMDSCaseTextField;
    @FXML
    private TextField MigrateContactsTextField;
    @FXML
    private TextField MigrateORGCaseTextField;
    @FXML
    private TextField MigrateHearingsCaseTextField;
    @FXML
    private TextField MigrateUsersTextField;
    @FXML
    private TextField MigrateDocumentsTextField;
    @FXML
    private TextField MigrateSystemDefaultsTextField;
    @FXML
    private TextField MigrateEmployersTextField;
    @FXML
    private TextField MigratePublicRecordsTextField;

    @FXML
    protected void onRectanglePressed(MouseEvent event) {
        X = mainstage.getX() - event.getScreenX();
        Y = mainstage.getY() - event.getScreenY();
    }

    @FXML
    protected void onRectangleDragged(MouseEvent event) {
        mainstage.setX(event.getScreenX() + X);
        mainstage.setY(event.getScreenY() + Y);
    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Nothing Needs Initialized here
    }

    public void setDefaults(Stage stage, MainWindowSceneController controller) {
        mainstage = stage;
        control = controller;
        sqlServerEmailControl.verifyBlankRow();
        sqlMigrationStatus.verifyBlankRow();
        checkButtonStatus();
        sqlUsers.getNewDBUsers();
    }

    @FXML
    private void MenuExit() {
        System.exit(0);
    }

    @FXML
    private void BatchPhaseOneMenuButton() {
        Thread phase1Thread = new Thread() {
            @Override
            public void run() {
                long lStartTime = System.currentTimeMillis();
                SystemDefaultsMigration.sysThread(control);
                UserMigration.userThread(control);
                DocumentMigration.docThread(control);
                ContactsMigration.contactsThread(control);
                EmployersMigration.employersThread(control);
                PublicRecordsMigration.publicRecordsThread(control);
                long lEndTime = System.currentTimeMillis();
                String finishedText = "Finished Phase 1 Migration in " + StringUtilities.convertLongToTime(lEndTime - lStartTime);
                System.out.println(finishedText);
                control.setProgressBarDisable(finishedText);
            }
        };
        phase1Thread.start();
    }

    @FXML
    private void BatchPhaseTwoMenuButton() {
        Thread phase1Thread = new Thread() {
            @Override
            public void run() {
                long lStartTime = System.currentTimeMillis();
                CSCMigration.cscThread(control);
                HearingsMigration.hearingsThread(control);
                ORGMigration.orgThread(control);
                CMDSMigration.cmdsThread(control);
                ULPMigration.ulpThread(control);
                REPMigration.repThread(control);
                MEDMigration.medThread(control);
                long lEndTime = System.currentTimeMillis();
                String finishedText = "Finished Phase 2 Migration in " + StringUtilities.convertLongToTime(lEndTime - lStartTime);
                System.out.println(finishedText);
                control.setProgressBarDisable(finishedText);
            }
        };
        phase1Thread.start();
    }

    @FXML
    private void BatchPhaseTwoPartialMenuButton() {
        Thread phase1Thread = new Thread() {
            @Override
            public void run() {
                long lStartTime = System.currentTimeMillis();
                CSCMigration.cscThread(control);
                HearingsMigration.hearingsThread(control);
                ORGMigration.orgThread(control);
                CMDSMigration.cmdsThread(control);
                long lEndTime = System.currentTimeMillis();
                String finishedText = "Finished ORG, CMDS, CSC, HRG Migration in " + StringUtilities.convertLongToTime(lEndTime - lStartTime);
                System.out.println(finishedText);
                control.setProgressBarDisable(finishedText);
            }
        };
        phase1Thread.start();
    }

    @FXML
    private void MenuRenameFolders(){
        CMDSMigration.renameFolders();
    }

    @FXML
    private void migrateULPButton() {
        ULPMigration.migrateULPData(control);
    }

    @FXML
    private void migrateMEDButton() {
        MEDMigration.migrateMEDData(control);
    }

    @FXML
    private void migrateREPButton() {
        REPMigration.migrateREPData(control);
    }

    @FXML
    private void migrateORGButton() {
        ORGMigration.migrateORGData(control);
    }

    @FXML
    private void migrateCSCButton() {
        CSCMigration.migrateCSCData(control);
    }

    @FXML
    private void migrateHearingsButton() {
        HearingsMigration.migrateHearingsData(control);
    }

    @FXML
    private void migrateCMDSButton() {
        CMDSMigration.migrateCMDSData(control);
    }

    @FXML
    private void migrateContactsButton() {
        ContactsMigration.migrateContacts(control);
    }

    @FXML
    private void migrateUsersButton() {
        UserMigration.migrateUserData(control);
    }

    @FXML
    private void migratePublicRecordsButton() {
        PublicRecordsMigration.migratePublicRecordsData(control);
    }

    @FXML
    private void migrateDocumentsButton() {
        DocumentMigration.migrateDocuments(control);
    }

    @FXML
    private void migrateSystemDefaultsButton() {
        SystemDefaultsMigration.migrateSystemData(control);
    }

    @FXML
    private void migrateEmployersButton() {
        EmployersMigration.migrateEmployers(control);
    }

    public void setProgressBarIndeterminate(final String text) {
        Platform.runLater(() -> {
            progressBarLabel.setText("Gathering Records for: " + text);
            progressbar.setProgress(ProgressBar.INDETERMINATE_PROGRESS);
            disableAllButtons();
        });
    }

    public void setProgressBarIndeterminateCleaning(final String text) {
        Platform.runLater(() -> {
            progressBarLabel.setText("Cleaning : " + text + " Records");
            progressbar.setProgress(ProgressBar.INDETERMINATE_PROGRESS);
            disableAllButtons();
        });
    }

    public void updateProgressBarProcessing(final double currentValue, final double maxValue) {
        Platform.runLater(() -> {
            progressBarLabel.setText("Processing Record: " + (int) currentValue + "/" + (int) maxValue);
            progressbar.setProgress(currentValue / maxValue);
        });
    }

    public void updateProgressBarCleaning(final double currentValue, final double maxValue) {
        Platform.runLater(() -> {
            progressBarLabel.setText("Cleaning Record: " + (int) currentValue + "/" + (int) maxValue);
            progressbar.setProgress(currentValue / maxValue);
        });
    }

    public void setProgressBarDisable(final String text) {
        Platform.runLater(() -> {
            progressBarLabel.setText(text);
            progressbar.setProgress(0.0);
            checkButtonStatus();
        });
    }

    private void checkButtonStatus() {
        migrationStatusModel item = sqlMigrationStatus.getMigrationStatus();

        MigrateCMDSCaseTextField.setText(((item.getMigrateCMDSCases() == null)
                ? "" : Global.getMmddyyyyhhmmssa().format(item.getMigrateCMDSCases())));
        MigrateCSCCaseTextField.setText(((item.getMigrateCSCCases() == null)
                ? "" : Global.getMmddyyyyhhmmssa().format(item.getMigrateCSCCases())));
        MigrateULPCaseTextField.setText(((item.getMigrateULPCases() == null)
                ? "" : Global.getMmddyyyyhhmmssa().format(item.getMigrateULPCases())));
        MigrateMEDCaseTextField.setText(((item.getMigrateMEDCases() == null)
                ? "" : Global.getMmddyyyyhhmmssa().format(item.getMigrateMEDCases())));
        MigrateREPCaseTextField.setText(((item.getMigrateREPCases() == null)
                ? "" : Global.getMmddyyyyhhmmssa().format(item.getMigrateREPCases())));
        MigrateContactsTextField.setText(((item.getMigrateContacts() == null)
                ? "" : Global.getMmddyyyyhhmmssa().format(item.getMigrateContacts())));
        MigrateORGCaseTextField.setText(((item.getMigrateORGCase() == null)
                ? "" : Global.getMmddyyyyhhmmssa().format(item.getMigrateORGCase())));
        MigrateUsersTextField.setText(((item.getMigrateUsers() == null)
                ? "" : Global.getMmddyyyyhhmmssa().format(item.getMigrateUsers())));
        MigrateDocumentsTextField.setText(((item.getMigrateDocuments() == null)
                ? "" : Global.getMmddyyyyhhmmssa().format(item.getMigrateDocuments())));
        MigrateEmployersTextField.setText(((item.getMigrateEmployers() == null)
                ? "" : Global.getMmddyyyyhhmmssa().format(item.getMigrateEmployers())));
        MigrateSystemDefaultsTextField.setText(((item.getMigrateSystemDefaults() == null)
                ? "" : Global.getMmddyyyyhhmmssa().format(item.getMigrateSystemDefaults())));
        MigrateHearingsCaseTextField.setText(((item.getMigrateHearingsCases()== null)
                ? "" : Global.getMmddyyyyhhmmssa().format(item.getMigrateHearingsCases())));
        MigratePublicRecordsTextField.setText(((item.getMigratePublicRecords()== null)
                ? "" : Global.getMmddyyyyhhmmssa().format(item.getMigratePublicRecords())));


        MigrateCMDSCaseButton.setDisable(item.getMigrateCMDSCases() != null);
        MigrateContactsButton.setDisable(item.getMigrateContacts() != null);
        MigrateCSCCaseButton.setDisable(item.getMigrateCSCCases() != null);
        MigrateREPCaseButton.setDisable(item.getMigrateREPCases() != null);
        MigrateMEDCaseButton.setDisable(item.getMigrateMEDCases() != null);
        MigrateULPCaseButton.setDisable(item.getMigrateULPCases() != null);
        MigrateORGCaseButton.setDisable(item.getMigrateORGCase() != null);
        MigrateUsersButton.setDisable(item.getMigrateUsers() != null);
        MigrateDocumentsButton.setDisable(item.getMigrateDocuments() != null);
        MigrateEmployersButton.setDisable(item.getMigrateDocuments() != null);
        MigrateSystemDefaultsButton.setDisable(item.getMigrateDocuments() != null);
        MigrateHearingsCaseButton.setDisable(item.getMigrateHearingsCases() != null);
        MigratePublicRecordsButton.setDisable(item.getMigratePublicRecords()!= null);
    }

    private void disableAllButtons() {
        MigrateCMDSCaseButton.setDisable(true);
        MigrateContactsButton.setDisable(true);
        MigrateCSCCaseButton.setDisable(true);
        MigrateREPCaseButton.setDisable(true);
        MigrateMEDCaseButton.setDisable(true);
        MigrateULPCaseButton.setDisable(true);
        MigrateORGCaseButton.setDisable(true);
        MigrateUsersButton.setDisable(true);
        MigrateDocumentsButton.setDisable(true);
        MigrateEmployersButton.setDisable(true);
        MigrateSystemDefaultsButton.setDisable(true);
        MigrateHearingsCaseButton.setDisable(true);
        MigratePublicRecordsButton.setDisable(true);
    }

}
