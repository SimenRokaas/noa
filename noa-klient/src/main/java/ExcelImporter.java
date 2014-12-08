import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
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
public class ExcelImporter extends Application {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(ExcelImporter.class, args);
        launch(args);
    }

    Button selectFiles;
    Button importFiles;
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
                        openFile(list);
                    }
                });
        importFiles = new Button("Importer filer");
        importFiles.setDisable(true);

        VBox vBox = new VBox();
        vBox.getChildren().addAll(selectFiles, importFiles);
        bp.setTop(selectFiles);
        bp.setBottom(importFiles);

        root.getChildren().addAll(bp);

        ScrollPane s1 = new ScrollPane();
        this.vBox = new VBox();
        s1.setContent(this.vBox);
        bp.setCenter(s1);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void openFile(List<File> files) {
        updateVbox(files);
        importFiles.setDisable(false);
    }

    private void updateVbox(List<File> files) {
        for (File f : files) {
            Label file = new Label(f.getName());
            Button delete = new Button("delete");
            this.vBox.getChildren().addAll(file, delete);
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
                new FileChooser.ExtensionFilter("Alle filer", "*.*"),
                new FileChooser.ExtensionFilter("Excel old", "*.xls"),
                new FileChooser.ExtensionFilter("Excel", "*.xlsx")
        );
    }
}
