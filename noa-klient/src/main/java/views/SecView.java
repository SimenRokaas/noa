package views;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import javax.swing.border.Border;

/**
 * Created by arne-richard.hofsoy on 12/12/14.
 */
public class SecView extends StackPane {

    private EventHandler<ActionEvent> testButtonEventHandler;
    private EventHandler<ActionEvent> testButtonEventHandler2;

    private ImageView imageView;

    public SecView(BorderPane layout) {
        Image img = new Image(getClass().getResource("/money.jpg").toExternalForm());
        imageView = new ImageView(img);
        this.getChildren().addAll(imageView);
        layout.setCenter(this);
        createHandlers();
        layout.setLeft(setupVenstreMeny());
    }

    private VBox setupVenstreMeny() {
        VBox venstreMeny = new VBox();
        venstreMeny.setPadding(new Insets(5, 20, 5, 20));
        final ToggleGroup group = new ToggleGroup();
        ToggleButton button1, button2, button3, button4;
        button1 = new ToggleButton("testsec1");
        button1.setOnAction(testButtonEventHandler);
        button2 = new ToggleButton("testsec2");
        button2.setOnAction(testButtonEventHandler2);
        button3 = new ToggleButton("testsec3");
        button4 = new ToggleButton("testsec4");
        button1.setToggleGroup(group);
        button2.setToggleGroup(group);
        button3.setToggleGroup(group);
        button4.setToggleGroup(group);

        venstreMeny.getChildren().addAll(button1, button2, button3, button4);
        venstreMeny.getStyleClass().add("venstreMeny");
        return venstreMeny;
    }

    private void createHandlers() {
        testButtonEventHandler = actionEvent -> {
            Image img = new Image(getClass().getResourceAsStream("/money.jpg"));
            imageView.setImage(img);
        };

        testButtonEventHandler2 = actionEvent -> {
            Image img = new Image(getClass().getResourceAsStream("/toggle-buttons-css.png"));
            imageView.setImage(img);
        };
    }
}
