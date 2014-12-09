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

    Button selectFiles;
    Button importFiles;
    Button clearForm;
    Stage primaryStage;
    List<File> fileList;
    BorderPane bp = null;
    VBox vBox;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        Group root = new Group();
        bp = new BorderPane();
        fileList = new LinkedList<>();

        final FileChooser fileChooser = new FileChooser();
        selectFiles = new Button("Velg filer for import");

        selectFiles.setOnAction(
                e -> {
                    configureFileChooser(fileChooser);
                    List<File> list =
                            fileChooser.showOpenMultipleDialog(primaryStage);
                    if (list != null) {
                        addFiles(list);
                    }
                });
        importFiles = new Button("Importer filer");
        importFiles.setDisable(true);
        clearForm = new Button("Reset");

        clearForm.setOnAction(event -> {
            fileList.clear();
            this.vBox.getChildren().clear();
            importFiles.setDisable(true);
        });

        VBox vBox = new VBox();
        vBox.getChildren().addAll(selectFiles);
        bp.setTop(selectFiles);
        HBox bottomBox = new HBox();
        bottomBox.getChildren().addAll(importFiles, clearForm);
        bp.setBottom(bottomBox);


        root.getChildren().addAll(bp);

        ScrollPane s1 = new ScrollPane();
        s1.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        s1.setVmax(440);
        s1.setPrefSize(750, 440);
        this.vBox = new VBox();
        s1.setContent(this.vBox);
        bp.setCenter(s1);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void addFiles(List<File> files) {
        updateFileflabel(files);
        importFiles.setDisable(false);
    }

    private void updateFileflabel(List<File> files) {
        for (File f : files) {
            Label file = new Label(f.getName());
            //Button delete = new Button("delete");
            this.vBox.getChildren().addAll(file);
        }
        this.fileList.addAll(files);
    }

    private static void configureFileChooser(
            final FileChooser fileChooser) {
        fileChooser.setTitle("Velg filer");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Excel", "*.xls", "*.xlsx")
        );
    }
}
