import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

/**
 * Koden hentet fra hvdc og skrevet om tilpasset NOA behov
 */
public class GuiTestHjelper {
    protected Parent nodeSomSkalTestes;

    protected Node finn(final String tekst) {
        Node node = nodeSomSkalTestes.lookup(tekst);
        return (node != null ? node : finnNodeSomMatcherTekst(nodeSomSkalTestes, tekst));
    }

    protected Node finnNodeSomMatcherTekst(final Parent startNode, final String tekst) {
        return finnNodeIListeSomMatcherTekst(startNode.getChildrenUnmodifiable(), tekst);
    }

    protected Node finnNodeIListeSomMatcherTekst(final ObservableList<Node> liste, final String tekst) {
        for (Node node : liste) {
            if (node instanceof Label) {
                if (((Label) node).getText().equals(tekst)) {
                    return node;
                }
            } else if (node instanceof ToggleButton) {
                if (((ToggleButton) node).getText().equals(tekst)) {
                    return node;
                }
            } else if (node instanceof Button) {
                if (((Button) node).getText().equals(tekst)) {
                    return node;
                }
            } else if (node instanceof BorderPane) {
                Node n = finnNodeIListeSomMatcherTekst(((BorderPane) node).getChildrenUnmodifiable(), tekst);
                if (n != null) {
                    return n;
                }
            } else if (node instanceof SplitPane) {
                Node n = finnNodeIListeSomMatcherTekst(((SplitPane) node).getItems(), tekst);
                if (n != null) {
                    return n;
                }
            } else if (node instanceof TableView) {
                Node n = finnNodeIListeSomMatcherTekst(((TableView) node).getChildrenUnmodifiable(), tekst);
                if (n != null) {
                    return n;
                }
            } else if (node instanceof Parent) {
                Node n = finnNodeSomMatcherTekst((Parent) node, tekst);
                if (n != null) {
                    return n;
                }
            }
        }
        return null;
    }

    protected TekstNode finnTekstFeltMedLabel(final String label) {
        Node node = finn(label);
        if (node != null && node instanceof Label) {
            Label lbl = (Label) node;
            Node labelFor = lbl.getLabelFor();
            if (labelFor instanceof TextField) {
                return new TekstNode((TextField) labelFor);
            }
        }
        return null;
    }

    protected class TekstNode {
        private TextField tekstFelt;

        TekstNode(TextField tekstFelt) {
            this.tekstFelt = tekstFelt;
        }

        public void setTekst(String tekst) {
            tekstFelt.setText(tekst);
        }
    }

    protected void klikk(final String tekst) {
        Node node = finn(tekst);
        if (node instanceof Button) {
            ((Button) node).fire();
        } else {
            throw new UnsupportedOperationException("'" + tekst + "' er ikke en Button - kan ikke klikke.");
        }
    }

    protected void klikk(final Button knapp) {
        knapp.arm();
        knapp.fire();
    }

    protected Tab finnTab(final String tekst) {
        return finnTabSomMatcherTekst(nodeSomSkalTestes, tekst);
    }

    protected Tab finnTabSomMatcherTekst(final Parent startNode, final String tekst) {
        return finnTabIListeSomMatcherTekst(startNode.getChildrenUnmodifiable(), tekst);
    }

    protected Tab finnTabIListeSomMatcherTekst(final ObservableList<Node> liste, final String tekst) {
        for (Node node : liste) {
            if (node instanceof TabPane) {
                TabPane pane = (TabPane) node;
                ObservableList<Tab> tabs = pane.getTabs();
                for (Tab tab : tabs) {
                    if (tab.getText().equals(tekst)) {
                        return tab;
                    }
                }
            }
        }
        return null;
    }
}
