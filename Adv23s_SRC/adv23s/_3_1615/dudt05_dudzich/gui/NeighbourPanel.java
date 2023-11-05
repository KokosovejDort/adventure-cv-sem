package adv23s._3_1615.dudt05_dudzich.gui;

import adv23s._3_1615.dudt05_dudzich.api.IGame;
import adv23s._3_1615.dudt05_dudzich.logic.Game;
import adv23s._3_1615.dudt05_dudzich.logic.Place;
import adv23s._3_1615.dudt05_dudzich.logic.World;
import adv23s._3_1615.dudt05_dudzich.util.Observer;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

/**
 * The {@code NeighbourPanel} class represents a UI component that displays the neighboring places of the current location in the game.
 * It updates dynamically as the player moves through the world.
 */
public class NeighbourPanel implements Observer {
    private final VBox neighbourPanel = new VBox();
    private final VBox vbox = new VBox();
    private World world = World.getInstance();
    private final IGame game = Game.getInstance();
    private final TextArea textArea;
    private Place currentPlace;

    /**
     * Constructs a {@code NeighbourPanel} with a reference to a {@code TextArea} for game output.
     *
     * @param textArea The {@code TextArea} where game output is displayed.
     */
    public NeighbourPanel(TextArea textArea) {
        this.textArea = textArea;
        currentPlace = world.currentPlace();
        init();
        world.registerObserver(this);
    }

    /**
     * Initializes the panel with the current neighbors of the player's location.
     */
    private void init() {
        getCurrentNeighbours();
        vbox.setPrefWidth(145);
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        Label label = new Label("Neighbours:");
        neighbourPanel.setSpacing(2);
        vbox.getChildren().addAll(label, neighbourPanel);
        getCurrentNeighbours();
    }

    /**
     * Updates the panel to display the current neighbors of the player's location.
     * Each neighbor is represented by a clickable label that, when clicked, executes the "go" command to move to that location.
     */
    private void getCurrentNeighbours() {
        neighbourPanel.getChildren().clear();
        for (Place place: currentPlace.neighbors()) {
            Label label = new Label(place.name());
            label.setOnMouseClicked(event -> {
                String gameOutput = game.executeCommand("go " + label.getText());
                textArea.appendText("\n"+gameOutput);
            });
            neighbourPanel.getChildren().add(label);
        }
    }

    /**
     * Called when the observed object is changed. It updates the current place and refreshes the neighbors displayed.
     */
    @Override
    public void update() {
        currentPlace = world.currentPlace();
        getCurrentNeighbours();
    }

    /**
     * Retrieves the panel containing the neighbor labels.
     *
     * @return A {@code VBox} containing the neighbor labels.
     */
    public VBox getPannel() {
        return vbox;
    }
}
