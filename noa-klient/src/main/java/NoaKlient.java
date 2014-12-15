import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import views.GlobalView;

/**
 * Klassen starter en javaFx2 spring applikasjon
 * som gjør det mulig å importere excel filer
 * i forbindelse med nettavregningsområder.
 */
@EnableAutoConfiguration
public class NoaKlient extends Application {

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(NoaKlient.class);
    private GlobalView layout;

    public static void main(String[] args) throws Exception {
        SpringApplication.run(NoaKlient.class, args);
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        logger.info("Starter");
        Group root = new Group();
        layout = new GlobalView(800, 600);
        root.getChildren().addAll(layout);
        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add("styles.css");
        primaryStage.setScene(scene);
        primaryStage.show();
        //ScenicView.show(scene);
    }
}
