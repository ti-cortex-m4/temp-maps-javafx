package demo.maps.javafx.demo2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.ResourceBundle;

public class Application2 extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Rectangle2D bounds = Screen.getPrimary().getBounds();

        ResourceBundle bundle = ResourceBundle.getBundle("ui");
        Parent root = FXMLLoader.load(getClass().getResource("/ui.fxml"), bundle);

        Scene scene = new Scene(root, bounds.getWidth(), bounds.getHeight());
        stage.setTitle("Gluon Maps demo");
        stage.setScene(scene);
        stage.show();
    }
}
