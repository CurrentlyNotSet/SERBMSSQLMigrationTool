/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sceneControllers;


import com.Migration.CMDSMigration;
import com.Migration.CSCMigration;
import com.Migration.ContactsMigration;
import com.Migration.MEDMigration;
import com.Migration.REPMigration;
import com.Migration.ULPMigration;
import com.model.migrationStatusModel;
import com.sql.sqlMigrationStatus;
import com.util.Global;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //TODO
    }    
    
    public void setDefaults(Stage stage, MainWindowSceneController controller){
        mainstage = stage;
        control = controller;
        checkButtonStatus();
    }
    
    @FXML
    private void MenuExit() {
        System.exit(0);
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
    private void migrateCSCButton() {
        CSCMigration.migrateCSCData(control);
    }
    
    @FXML
    private void migrateCMDSButton() {
        CMDSMigration.migrateCMDSData(control);
    }
    
    @FXML
    private void migrateContactsButton() {
        ContactsMigration.migrateContacts(control);
    }
    
    public void setProgressBarIndeterminate(final String text) {
        Platform.runLater(() -> {
            progressBarLabel.setText("Gathering Records for: " + text);
            progressbar.setProgress(ProgressBar.INDETERMINATE_PROGRESS);
            disableAllButtons();
        });
    }
    
    public void updateProgressBar(final double currentValue, final double maxValue) {
        Platform.runLater(() -> {
            progressBarLabel.setText("Processing Record: " + (int) currentValue + "/" + (int) maxValue);
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

            MigrateCMDSCaseButton.setDisable(item.getMigrateCMDSCases() != null);
            MigrateContactsButton.setDisable(item.getMigrateContacts() != null);
            MigrateCSCCaseButton.setDisable(item.getMigrateCSCCases() != null);
            MigrateREPCaseButton.setDisable(item.getMigrateREPCases() != null);
            MigrateMEDCaseButton.setDisable(item.getMigrateMEDCases() != null);
            MigrateULPCaseButton.setDisable(item.getMigrateULPCases() != null);
  
    }

    private void disableAllButtons() {
            MigrateCMDSCaseButton.setDisable(true);
            MigrateContactsButton.setDisable(true);
            MigrateCSCCaseButton.setDisable(true);
            MigrateREPCaseButton.setDisable(true);
            MigrateMEDCaseButton.setDisable(true);
            MigrateULPCaseButton.setDisable(true);
      
    }

}
