package adv23s._3_1615.dudt05_dudzich.gui;

import adv23s._3_1615.dudt05_dudzich.api.IGame;
import adv23s._3_1615.dudt05_dudzich.logic.Bag;
import adv23s._3_1615.dudt05_dudzich.logic.Game;
import adv23s._3_1615.dudt05_dudzich.util.Observer;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.Set;

public class BagPannel implements Observer {
    private final VBox vbox = new VBox();
    private final VBox itemPanel = new VBox();
    private final Bag bag;
    private final IGame game = Game.getInstance();
    private final TextArea textArea;

    public BagPannel(IGame game, TextArea textArea) {
        this.textArea = textArea;
        bag = (Bag) game.bag();
        init();
        bag.registerObserver(this);
    }

    private void init() {
        vbox.setPrefWidth(145);
        Label label = new Label("Items in bag:");
        itemPanel.setSpacing(2);
        vbox.getChildren().addAll(label, itemPanel);
        updateItemsImage();
    }

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

    @Override
    public void update() {
        updateItemsImage();
    }

    public VBox getPannel() {
        return vbox;
    }
}
