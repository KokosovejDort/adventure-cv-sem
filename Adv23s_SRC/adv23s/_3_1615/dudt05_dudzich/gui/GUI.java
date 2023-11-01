package adv23s._3_1615.dudt05_dudzich.gui;

import adv23s._3_1615.dudt05_dudzich.api.IGame;
import adv23s._3_1615.dudt05_dudzich.logic.World;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class GUI {
    public TextArea getReadyStartScreen() {
        TextArea textArea = new TextArea();
        textArea.setEditable(false);
        return textArea;
    }

    public HBox getReadyUserInputLabel(TextField inputField) {
        Label label = new Label("Enter command: ");
        HBox inputArea = new HBox();
        inputArea.setPadding(new Insets(5, 0, 5, 0));
        inputArea.getChildren().addAll(label, inputField);
        inputArea.setAlignment(Pos.CENTER);
        inputArea.setSpacing(10);
        return inputArea;
    }

    public VBox getBagPanel(IGame game) {
        BagPannel bagPannel = new BagPannel(game);
        return bagPannel.getPannel();
    }

    public VBox getItemsPanel(IGame game) {
        PlaceItems placeItems = new PlaceItems((World) game.world());
        return placeItems.getPannel();
    }

    public VBox getCombinedPanel(IGame game) {
        VBox combinedPanel = new VBox();
        VBox bagPanelVBox = getBagPanel(game);
        VBox itemsPanelVBox = getItemsPanel(game);
        bagPanelVBox.setSpacing(5);
        itemsPanelVBox.setSpacing(5);

        combinedPanel.getChildren().addAll(itemsPanelVBox, bagPanelVBox);
        combinedPanel.setSpacing(50);
        combinedPanel.setPadding(new Insets(10, 10, 10, 10));

        combinedPanel.setPrefWidth(150);
        return combinedPanel;
    }

    public VBox getNeighboursPanel(IGame game) {
        NeighbourPanel neighbourPanel = new NeighbourPanel();
        return neighbourPanel.getPannel();
    }

    public void arrangeItemsInBorderPane(Node bottom, Node left,
                                         Node center, Node right,
                                         Node top, BorderPane borderPane) {
        borderPane.setBottom(bottom != null ? bottom : borderPane.getBottom());
        borderPane.setLeft(left != null ? left : borderPane.getLeft());
        borderPane.setCenter(center != null ? center : borderPane.getCenter());
        borderPane.setRight(right != null ? right : borderPane.getRight());
        borderPane.setTop(top != null ? top : borderPane.getTop());
    }
}
