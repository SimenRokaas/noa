import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import no.statnett.noa.excel.Importerer;
import org.slf4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 * Klassen starter en javaFx2 spring applikasjon
 * som gjør det mulig å importere excel filer
 * i forbindelse med nettavregningsområder.
 */
@EnableAutoConfiguration
public class ImportApplikasjon extends Application {

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ImportApplikasjon.class);
    private EventHandler<ActionEvent> importerFilerHandler;
    
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
    VBox loggBox;
    Importerer importerer;
    List<String> loggInnhold;
    Tab filerTab;

    @Override
    public void start(Stage primaryStage) throws Exception {
        logger.info("Starting");
        importerer = new Importerer();
        loggInnhold = new LinkedList<>();
        this.hovedScene = primaryStage;
        Group root = new Group();
        grensePane = new BorderPane();
        filer = new LinkedList<>();

        final FileChooser fileChooser = new FileChooser();
        velgFiler = new Button("Velg filer for import");

        velgFiler.setOnAction(
                e -> {
                    konfigurerFilVelger(fileChooser);
                    List<File> list =
                            fileChooser.showOpenMultipleDialog(primaryStage);
                    if (list != null) {
                        addFiles(list);
                    }
                });
        importerFiler = new Button("Importer filer");
        createHandlers();
        importerFiler.setOnAction(importerFilerHandler);
        importerFiler.setDisable(true);
        reset = new Button("Reset");

        reset.setOnAction(event -> {
            filer.clear();
            this.vertikalBoks.getChildren().clear();
            this.loggBox.getChildren().clear();
            importerFiler.setDisable(true);
            filerTab.getContent().requestFocus();
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


        TabPane tabPane = new TabPane();
        filerTab = new Tab("Filer");
        filerTab.setContent(filInfoOmraade);
        filerTab.setClosable(false);

        ScrollPane loggInfoOmraade = new ScrollPane();
        loggInfoOmraade.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        loggInfoOmraade.setVmax(440);
        loggInfoOmraade.setPrefSize(750, 440);

        Tab logTab = new Tab("Logg");
        logTab.setContent(loggInfoOmraade);
        logTab.setClosable(false);

        tabPane.getTabs().addAll(filerTab, logTab);

        this.vertikalBoks = new VBox();
        filInfoOmraade.setContent(this.vertikalBoks);
        this.loggBox = new VBox();
        loggInfoOmraade.setContent(this.loggBox);
        grensePane.setCenter(tabPane);

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

    private void oppdaterLoggLabel(List<String> logg) {
        for (String s : logg) {
            Label file = new Label(s);
            this.loggBox.getChildren().addAll(file);
        }
    }

    private static void konfigurerFilVelger(final FileChooser filVelger) {
        filVelger.setTitle("Velg filer");

        filVelger.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );

        filVelger.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Excel", "*.xls", "*.xlsx")
        );
    }

    private void createHandlers() {
        importerFilerHandler = actionEvent -> {
            for (File f : filer) {
                importerer.importer(f);
                oppdaterLoggLabel(importerer.getLogg());
            }
            this.vertikalBoks.getChildren().clear();
            filer.clear();
        };

    }
}
