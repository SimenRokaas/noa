import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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


    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        Group root = new Group();

        final FileChooser fileChooser = new FileChooser();
        selectFiles = new Button("Velg filer for import");

        selectFiles.setOnAction(
                e -> {
                    configureFileChooser(fileChooser);
                    List<File> list =
                            fileChooser.showOpenMultipleDialog(primaryStage);
                    if (list != null) {
                        for (File file : list) {
                            openFile(file);
                        }
                    }
                });
        importFiles = new Button("Importer filer");
        importFiles.setDisable(true);
        VBox vBox = new VBox();
        vBox.getChildren().addAll(selectFiles, importFiles);
        root.getChildren().addAll(vBox);


        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void openFile(File file) {

    }

    private static void configureFileChooser(
            final FileChooser fileChooser) {
        fileChooser.setTitle("Velg filer");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("XLS", "*.xls"),
                new FileChooser.ExtensionFilter("XLSX", "*.xlsx")
        );
    }
}
