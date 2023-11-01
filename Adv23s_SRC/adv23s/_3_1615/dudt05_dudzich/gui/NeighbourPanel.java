package adv23s._3_1615.dudt05_dudzich.gui;

import adv23s._3_1615.dudt05_dudzich.logic.Place;
import adv23s._3_1615.dudt05_dudzich.logic.World;
import adv23s._3_1615.dudt05_dudzich.util.Observer;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class NeighbourPanel implements Observer {
    private final VBox neighbourPanel = new VBox();
    private final VBox vbox = new VBox();
    private World world = World.getInstance();
    private Place currentPlace;

    public NeighbourPanel() {
        currentPlace = world.currentPlace();
        init();
        world.registerObserver(this);
    }

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

    private void getCurrentNeighbours() {
        neighbourPanel.getChildren().clear();
        for (Place place: currentPlace.neighbors()) {
            Label label = new Label(place.name());
            neighbourPanel.getChildren().add(label);
        }
    }

    @Override
    public void update() {
        currentPlace = world.currentPlace();
        getCurrentNeighbours();
    }

    public VBox getPannel() {
        return vbox;
    }
}