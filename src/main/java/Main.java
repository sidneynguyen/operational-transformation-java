import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
	    System.out.println("Hello, World!");
	    launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("fxml/tester.fxml"));
        primaryStage.setTitle("Tester");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }
}
