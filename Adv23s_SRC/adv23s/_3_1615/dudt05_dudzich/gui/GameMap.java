package adv23s._3_1615.dudt05_dudzich.gui;

import adv23s._3_1615.dudt05_dudzich.logic.World;
import adv23s._3_1615.dudt05_dudzich.util.Observer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

import java.util.Objects;

/**
 * The {@code GameMap} class represents the GUI component that displays the game map.
 * It observes the world for changes in the player's position and updates the display accordingly.
 */
public class GameMap implements Observer {
    private AnchorPane anchorPane = new AnchorPane();
    private Circle point = new Circle(10, Paint.valueOf("RED"));
    private World world = World.getInstance();
    private ImageView imageView;

    /**
     * Sets the player's position on the map by updating the top and left anchor
     * pane constraints of the player's point based on the current place's position.
     */
    private void setPlayerPosition() {
        AnchorPane.setTopAnchor(point, world.currentPlace().getPosTop());
        AnchorPane.setLeftAnchor(point, world.currentPlace().getPosLeft());
    }

    /**
     * Constructs a new GameMap. It initializes the map image and the player's point,
     * registers itself as an observer to the world, and sets the initial player position.
     */
    public GameMap() {
        Image image = new Image(Objects.requireNonNull(GameMap.class.getClassLoader()
                .getResourceAsStream("gameMap.png")),
                400, 400, false, false);
        imageView = new ImageView(image);
        world.registerObserver(this);
        setPlayerPosition();
        anchorPane.getChildren().addAll(imageView, point);
    }

    /**
     * Gets the AnchorPane containing the game map and the player's point.
     *
     * @return The AnchorPane component representing the game map.
     */
    public AnchorPane getAnchorPane() {
        return anchorPane;
    }

    /**
     * Called when the observed world object notifies its observers of a change.
     * It triggers an update of the player's position on the map.
     */
    @Override
    public void update() {
        setPlayerPosition();
    }
}
