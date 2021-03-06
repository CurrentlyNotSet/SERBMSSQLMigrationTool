package com;

import com.sceneControllers.MainWindowSceneController;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        System.setProperty("java.net.preferIPv4Stack" , "true");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/MainWindowScene.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        MainWindowSceneController controller = fxmlLoader.<MainWindowSceneController>getController();
        controller.setDefaults(stage, controller);

        Scene scene = new Scene(root);
        stage.setTitle("Database Migration Tool");
        stage.getIcons().add(new Image("/image/database-5-xxl.png"));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.initStyle(StageStyle.DECORATED);
        stage.show();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
