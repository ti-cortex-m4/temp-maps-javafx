package demo2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.ResourceBundle;
//import org.modernclients.raspberrypi.gps.view.UIView;


public class Application2 extends Application {

//    private UIPresenter controller;

    @Override
    public void start(Stage stage) throws Exception {
        Rectangle2D bounds = Screen.getPrimary().getBounds();

//        UIView ui = new UIView();
//        controller = (UIPresenter) ui.getPresenter();
        ResourceBundle bundle = ResourceBundle.getBundle("ui");
        Parent root = FXMLLoader.load(getClass().getResource("/ui.fxml"), bundle);

        Scene scene = new Scene(root, bounds.getWidth(), bounds.getHeight());
        stage.setTitle("Embedded Maps");
        stage.setScene(scene);
        stage.show();
    }
    
    @Override
    public void stop() throws Exception {
//        controller.stop();
//        Injector.forgetAll();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
