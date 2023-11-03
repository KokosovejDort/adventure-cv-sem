package adv23s._3_1615.dudt05_dudzich.gui;

import adv23s._3_1615.dudt05_dudzich.api.IGame;
import adv23s._3_1615.dudt05_dudzich.logic.World;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.web.*;

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

    public HBox getMapAndPanels(IGame game) {
        GameMap gameMap = new GameMap();
        HBox mapAndPanels = new HBox();

        Region leftSpacer = new Region();
        Region rightSpacer = new Region();

        HBox.setHgrow(leftSpacer, Priority.ALWAYS);
        HBox.setHgrow(rightSpacer, Priority.ALWAYS);

        mapAndPanels.getChildren().addAll(getCombinedPanel(game),
                leftSpacer,
                gameMap.getAnchorPane(),
                rightSpacer,
                getNeighboursPanel(game));

        return mapAndPanels;
    }

    public VBox getNeighboursPanel(IGame game) {
        NeighbourPanel neighbourPanel = new NeighbourPanel();
        return neighbourPanel.getPannel();
    }

    public MenuBar getMenuBar(IGame game) {
        MenuBar menuBar = new MenuBar();
        Menu gameMenu = new Menu("Game");
        Menu helpMenu = new Menu("Help");
        menuBar.getMenus().addAll(gameMenu, helpMenu);

        MenuItem newGame = new MenuItem("New game");
        MenuItem exit = new MenuItem("Exit");
        MenuItem help = new MenuItem("Guidance");
        MenuItem aboutApp = new MenuItem("About app");

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About app");
        alert.setHeaderText("Adventura");
        alert.setContentText("Welcome to Adventura, an enthralling text-based adventure game developed as a part of the semester project for the course 4IT115.\n" +
                "Author: Tsimafei Dudzich\n" +
                "Version: 1.0");

        WebView webview = new WebView();


        newGame.setOnAction(actionEvent -> {
            game.stop();
            game.executeCommand("");
        });
        exit.setOnAction(actionEvent -> System.exit(0));
        aboutApp.setOnAction(actionEvent ->{
            alert.showAndWait();
        });


        gameMenu.getItems().addAll(newGame,new SeparatorMenuItem(),exit);
        helpMenu.getItems().addAll(help,new SeparatorMenuItem(),aboutApp);
        return menuBar;
    }

    public VBox menuAndPanels(IGame game) {
        HBox mapAndPanels = getMapAndPanels(game);
        VBox menuAndPanels = new VBox();
        menuAndPanels.getChildren().addAll(getMenuBar(game), mapAndPanels);
        return menuAndPanels;
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
