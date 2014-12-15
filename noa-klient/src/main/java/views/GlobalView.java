package views;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * Created by arne-richard.hofsoy on 12/12/14.
 */
public class GlobalView extends BorderPane {

    public GlobalView(int width, int height) {
        this.setPrefWidth(width);
        this.setPrefHeight(height);

        VBox toppInnhold = new VBox();
        HBox logoPane = setupLogo();
        createHandlers();

        HBox toppMeny = setupToppMeny();
        toppInnhold.getChildren().addAll(logoPane, toppMeny);
        toppInnhold.getStyleClass().addAll("toppInnhold");

        this.setTop(toppInnhold);
        this.setCenter(setupSenter());
    }

    private EventHandler<ActionEvent> testButtonEventHandler;
    private EventHandler<ActionEvent> homeButtonEventHandler;

    private Pane setupSenter() {
        MainView senter = new MainView(this);
        return senter;
    }

    private HBox setupLogo() {
        HBox logoPane = new HBox();
        logoPane.setPadding(new Insets(5, 10, 5 ,10));
        Image img = new Image(getClass().getResourceAsStream("/test.jpg"));
        ImageView imgView = new ImageView(img);
        imgView.setFitHeight(75);
        logoPane.getChildren().addAll(imgView);
        return logoPane;
    }

    private HBox setupToppMeny() {
        HBox topMenu = new HBox();
        topMenu.setPadding(new Insets(5, 20, 5, 20));
        final ToggleGroup group = new ToggleGroup();
        ToggleButton button1, button2, button3, button4;
        button1 = new ToggleButton("test1");
        button1.setOnAction(homeButtonEventHandler);

        button2 = new ToggleButton("test2");
        button2.setOnAction(testButtonEventHandler);

        button3 = new ToggleButton("test3");
        button4 = new ToggleButton("test4");
        button1.setToggleGroup(group);
        button2.setToggleGroup(group);
        button3.setToggleGroup(group);
        button4.setToggleGroup(group);
        topMenu.getChildren().addAll(button1, button2, button3, button4);
        topMenu.getStyleClass().add("toppMeny");
        return topMenu;
    }

    private void createHandlers() {
        testButtonEventHandler = actionEvent -> {
            this.setCenter(new SecView(this));
        };
        homeButtonEventHandler = actionEvent -> {
            this.setCenter(new MainView(this));
        };
    }
}
