package adv23s._3_1615.dudt05_dudzich.gui;

import adv23s._3_1615.dudt05_dudzich.logic.Place;
import adv23s._3_1615.dudt05_dudzich.logic.World;
import adv23s._3_1615.dudt05_dudzich.util.Observer;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.List;

public class PlaceItems implements Observer {
    private final VBox vbox = new VBox();
    private final VBox itemPanel = new VBox();
    private final World world = World.getInstance();
    private Place currentPlace;

    public PlaceItems(World world) {
        currentPlace = world.currentPlace();
        currentPlace.registerObserver(this);
        world.registerObserver(this);
        init();
    }

    private void init() {
        vbox.setPrefWidth(100);
        Label label = new Label("Loot:");
        itemPanel.setSpacing(2);
        vbox.getChildren().addAll(label, itemPanel);
        updateItemsImage();
    }

    private void updateItemsImage() {
        itemPanel.getChildren().clear();
        List<String> items = currentPlace.getItemNames();
        for (String item: items) {
            Label label = new Label(item);
            itemPanel.getChildren().add(label);
        }
    }

    @Override
    public void update() {
        currentPlace.unregisterObserver(this);
        currentPlace = world.currentPlace();

        currentPlace.registerObserver(this);
        updateItemsImage();
    }

    public VBox getPannel() {
        return vbox;
    }
}
