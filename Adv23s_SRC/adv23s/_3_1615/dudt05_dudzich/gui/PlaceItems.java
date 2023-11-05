package adv23s._3_1615.dudt05_dudzich.gui;

import adv23s._3_1615.dudt05_dudzich.api.IGame;
import adv23s._3_1615.dudt05_dudzich.logic.Game;
import adv23s._3_1615.dudt05_dudzich.logic.Place;
import adv23s._3_1615.dudt05_dudzich.logic.World;
import adv23s._3_1615.dudt05_dudzich.util.Observer;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

import java.util.List;

/**
 * The {@code PlaceItems} class represents a UI component that displays the items available in the current place of the game world.
 * It observes changes in the current place and updates the displayed items accordingly.
 */
public class PlaceItems implements Observer {
    private final VBox vbox = new VBox();
    private final VBox itemPanel = new VBox();
    private final World world = World.getInstance();
    private final IGame game = Game.getInstance();
    private final TextArea textArea;
    private Place currentPlace;

    /**
     * Constructs a {@code PlaceItems} panel with references to the game world and a {@code TextArea} for game output.
     *
     * @param world     The game world instance.
     * @param textArea  The {@code TextArea} where game output is displayed.
     */
    public PlaceItems(World world, TextArea textArea) {
        this.textArea = textArea;
        currentPlace = world.currentPlace();
        currentPlace.registerObserver(this);
        world.registerObserver(this);
        init();
    }

    /**
     * Initializes the panel with the items available in the current place.
     */
    private void init() {
        vbox.setPrefWidth(145);
        Label label = new Label("Loot:");
        itemPanel.setSpacing(2);
        vbox.getChildren().addAll(label, itemPanel);
        updateItemsImage();
    }

    /**
     * Updates the panel to display the items available in the current place.
     * Each item is represented by a clickable label that, when clicked, executes the "take" command to pick up the item.
     */
    private void updateItemsImage() {
        itemPanel.getChildren().clear();
        List<String> items = currentPlace.getItemNames();
        for (String item : items) {
            Label label = new Label(item);
            label.setOnMouseClicked(event -> {
                String gameOutput = game.executeCommand("take " + label.getText());
                textArea.appendText("\n" + gameOutput);
            });
            itemPanel.getChildren().add(label);
        }
    }

    /**
     * Called when the observed object is changed. It updates the current place and refreshes the items displayed.
     */
    @Override
    public void update() {
        currentPlace.unregisterObserver(this);
        currentPlace = world.currentPlace();
        currentPlace.registerObserver(this);
        updateItemsImage();
    }

    /**
     * Retrieves the panel containing the item labels.
     *
     * @return A {@code VBox} containing the item labels.
     */
    public VBox getPannel() {
        return vbox;
    }
}
