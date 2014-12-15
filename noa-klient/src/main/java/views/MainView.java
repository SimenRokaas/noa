package views;

import javafx.geometry.Insets;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * Created by arne-richard.hofsoy on 12/12/14.
 */
public class MainView extends StackPane {

    public MainView(BorderPane layout) {
        Image img = new Image(
                getClass().getResourceAsStream("/test.jpg")
        );
        ImageView imgView = new ImageView(img);
        this.getChildren().addAll(imgView);
        layout.setCenter(this);
        layout.setLeft(setupVenstreMeny());
    }

    private VBox setupVenstreMeny() {
        VBox venstreMeny = new VBox();
        venstreMeny.setPadding(new Insets(5, 20, 5, 20));
        final ToggleGroup group = new ToggleGroup();
        ToggleButton button1, button2, button3, button4;
        button1 = new ToggleButton("test1");
        button2 = new ToggleButton("test2");
        button3 = new ToggleButton("test3");
        button4 = new ToggleButton("test4");
        button1.setToggleGroup(group);
        button2.setToggleGroup(group);
        button3.setToggleGroup(group);
        button4.setToggleGroup(group);

        venstreMeny.getChildren().addAll(button1, button2, button3, button4);
        venstreMeny.getStyleClass().add("venstreMeny");
        return venstreMeny;
    }
}
