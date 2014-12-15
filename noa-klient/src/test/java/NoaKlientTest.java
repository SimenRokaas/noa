import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import views.GlobalView;
import views.MainView;

import static org.junit.Assert.*;
import static org.fest.assertions.Assertions.assertThat;

/**
 * This is a sample test class for java fx tests.
 */
@RunWith(JavaFxJUnit4ClassRunner.class)
public class NoaKlientTest extends GuiTestHjelper
{

    @Before
    public void setup() {
        nodeSomSkalTestes = new GlobalView(800, 600);
    }
    /**
     * Daft normal test.
     */
    @Test
    public void testNormal()
    {
        assertTrue(true);
    }

    /**
     * Test which would normally fail without running on the JavaFX thread.
     */
    @Test
    public void testNeedsJavaFX()
    {
        Scene scene = new Scene(new Group());
        assertTrue(true);
    }

    @Test
    public void testGlobalView()
    {
        ToggleButton button = (ToggleButton) finn("test1");
        assertThat(button).isNotNull();
    }
}
