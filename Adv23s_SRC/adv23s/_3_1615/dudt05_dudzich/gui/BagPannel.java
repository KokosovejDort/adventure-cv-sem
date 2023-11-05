package adv23s._3_1615.dudt05_dudzich.gui;

import adv23s._3_1615.dudt05_dudzich.api.IGame;
import adv23s._3_1615.dudt05_dudzich.logic.Bag;
import adv23s._3_1615.dudt05_dudzich.logic.Game;
import adv23s._3_1615.dudt05_dudzich.util.Observer;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

import java.util.List;

/**
 * The {@code BagPannel} class represents the GUI panel that displays the items
 * currently in the player's bag. It observes the bag for any changes and updates
 * the display accordingly.
 */
public class BagPannel implements Observer {
    private final VBox vbox = new VBox();
    private final VBox itemPanel = new VBox();
    private final Bag bag;
    private final IGame game = Game.getInstance();
    private final TextArea textArea;

    /**
     * Constructs a new BagPanel with a reference to the game and the text area
     * where game output is displayed.
     *
     * @param game      The game instance this panel is associated with.
     * @param textArea  The text area where game output will be appended.
     */
    public BagPannel(IGame game, TextArea textArea) {
        this.textArea = textArea;
        bag = (Bag) game.bag();
        init();
        bag.registerObserver(this);
    }

    /**
     * Initializes the panel with a predefined width and a label indicating
     * the items in the bag. It also sets up the item panel where item labels
     * will be displayed.
     */
    private void init() {
        vbox.setPrefWidth(145);
        Label label = new Label("Items in bag:");
        itemPanel.setSpacing(2);
        vbox.getChildren().addAll(label, itemPanel);
        updateItemsImage();
    }

    /**
     * Updates the item panel with the current items in the bag. Each item is
     * represented by a label which, when clicked, will execute the "put" command
     * for that item and append the game's response to the text area.
     */
    private void updateItemsImage() {
        itemPanel.getChildren().clear();
        List<String> items = bag.getItemNames();
        for (String item: items) {
            Label label = new Label(item);
            label.setOnMouseClicked(event -> {
                String gameOutput = game.executeCommand("put " + label.getText());
                textArea.appendText("\n"+gameOutput);
            });
            itemPanel.getChildren().add(label);
        }
    }

    /**
     * Called when the observed bag object notifies its observers of a change.
     * It triggers an update of the items displayed in the item panel.
     */
    @Override
    public void update() {
        updateItemsImage();
    }

    /**
     * Gets the VBox containing the entire bag panel.
     *
     * @return The VBox component representing the bag panel.
     */
    public VBox getPannel() {
        return vbox;
    }
}
