import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;

/**
 * Created by arne-richard.hofsoy on 08/12/14.
 */

@Controller
@EnableAutoConfiguration
public class Applikasjon extends Application {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Applikasjon.class, args);
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Group root = new Group();
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
