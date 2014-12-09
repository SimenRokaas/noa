import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by arne-richard.hofsoy on 08/12/14.
 */

@Controller
@EnableAutoConfiguration
public class ImportApplikasjon extends Application {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(ImportApplikasjon.class, args);
        launch(args);
    }

    Button velgFiler;
    Button importerFiler;
    Button reset;
    Stage hovedScene;
    List<File> filer;
    BorderPane grensePane = null;
    VBox vertikalBoks;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.hovedScene = primaryStage;
        Group root = new Group();
        grensePane = new BorderPane();
        filer = new LinkedList<>();

        final FileChooser fileChooser = new FileChooser();
        velgFiler = new Button("Velg filer for import");

        velgFiler.setOnAction(
                e -> {
                    configureFileChooser(fileChooser);
                    List<File> list =
                            fileChooser.showOpenMultipleDialog(primaryStage);
                    if (list != null) {
                        addFiles(list);
                    }
                });
        importerFiler = new Button("Importer filer");
        importerFiler.setDisable(true);
        reset = new Button("Reset");

        reset.setOnAction(event -> {
            filer.clear();
            this.vertikalBoks.getChildren().clear();
            importerFiler.setDisable(true);
        });

        VBox vBox = new VBox();
        vBox.getChildren().addAll(velgFiler);

        grensePane.setTop(velgFiler);
        HBox bottomBox = new HBox();
        bottomBox.getChildren().addAll(importerFiler, reset);
        grensePane.setBottom(bottomBox);
        root.getChildren().addAll(grensePane);

        ScrollPane filInfoOmraade = new ScrollPane();
        filInfoOmraade.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        filInfoOmraade.setVmax(440);
        filInfoOmraade.setPrefSize(750, 440);

        this.vertikalBoks = new VBox();
        filInfoOmraade.setContent(this.vertikalBoks);
        grensePane.setCenter(filInfoOmraade);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void addFiles(List<File> files) {
        oppdaterFilLabel(files);
        importerFiler.setDisable(false);
    }

    private void oppdaterFilLabel(List<File> files) {
        for (File f : files) {
            Label file = new Label(f.getName());
            this.vertikalBoks.getChildren().addAll(file);
        }
        this.filer.addAll(files);
    }

    private static void configureFileChooser(
            final FileChooser filVelger) {
        filVelger.setTitle("Velg filer");
        filVelger.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );

        filVelger.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Excel", "*.xls", "*.xlsx")
        );
    }
}
