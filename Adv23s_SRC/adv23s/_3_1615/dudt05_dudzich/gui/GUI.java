package adv23s._3_1615.dudt05_dudzich.gui;

import adv23s._3_1615.dudt05_dudzich.api.IGame;
import adv23s._3_1615.dudt05_dudzich.logic.Game;
import adv23s._3_1615.dudt05_dudzich.logic.World;
import adv23s._3_1615.dudt05_dudzich.main.AdventuraUI;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.*;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 * The {@code GUI} class is responsible for creating and managing the graphical user interface components of the game.
 * It provides methods to initialize and retrieve various UI elements such as text areas, panels, and menus.
 */

public class GUI {
    private TextArea textArea;
    private AdventuraUI mainApp;
    private IGame game = Game.getInstance();

    /**
     * Constructs a GUI object with a reference to the main application.
     *
     * @param mainApp The main application of the game.
     */
    public GUI(AdventuraUI mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * Prepares the start screen text area for the game.
     *
     * @return A non-editable {@code TextArea} for game output.
     */
    public TextArea getReadyStartScreen() {
        TextArea textArea = new TextArea();
        textArea.setEditable(false);
        this.textArea = textArea;
        return textArea;
    }

    /**
     * Prepares the user input area with a label and text field.
     *
     * @param inputField The text field for user input.
     * @return An {@code HBox} containing the label and text field for user commands.
     */
    public HBox getReadyUserInputLabel(TextField inputField) {
        Label label = new Label("Enter command: ");
        HBox inputArea = new HBox();
        inputArea.setPadding(new Insets(5, 0, 5, 0));
        inputArea.getChildren().addAll(label, inputField);
        inputArea.setAlignment(Pos.CENTER);
        inputArea.setSpacing(10);
        return inputArea;
    }

    /**
     * Retrieves the bag panel for the game.
     *
     * @return A {@code VBox} containing the bag panel.
     */
    public VBox getBagPanel() {
        BagPannel bagPannel = new BagPannel(game, textArea);
        return bagPannel.getPannel();
    }

    /**
     * Retrieves the items panel for the game.
     *
     * @return A {@code VBox} containing the items panel.
     */
    public VBox getItemsPanel() {
        PlaceItems placeItems = new PlaceItems((World) game.world(), textArea);
        return placeItems.getPannel();
    }

    /**
     * Combines the bag panel and items panel into a single {@code VBox}.
     *
     * @return A {@code VBox} containing both the bag and items panels.
     */
    public VBox getCombinedPanel() {
        VBox combinedPanel = new VBox();
        VBox bagPanelVBox = getBagPanel();
        VBox itemsPanelVBox = getItemsPanel();
        bagPanelVBox.setSpacing(5);
        itemsPanelVBox.setSpacing(5);

        combinedPanel.getChildren().addAll(itemsPanelVBox, bagPanelVBox);
        combinedPanel.setSpacing(50);
        combinedPanel.setPadding(new Insets(10, 10, 10, 10));

        combinedPanel.setPrefWidth(150);
        return combinedPanel;
    }

    /**
     * Prepares the main game view by combining the map and panels.
     *
     * @return An {@code HBox} containing the game map and side panels.
     */
    public HBox getMapAndPanels() {
        GameMap gameMap = new GameMap();
        HBox mapAndPanels = new HBox();

        Region leftSpacer = new Region();
        Region rightSpacer = new Region();

        HBox.setHgrow(leftSpacer, Priority.ALWAYS);
        HBox.setHgrow(rightSpacer, Priority.ALWAYS);

        mapAndPanels.getChildren().addAll(getCombinedPanel(),
                leftSpacer,
                gameMap.getAnchorPane(),
                rightSpacer,
                getNeighboursPanel());

        return mapAndPanels;
    }

    /**
     * Retrieves the neighbors panel for the game.
     *
     * @return A {@code VBox} containing the neighbors panel.
     */
    public VBox getNeighboursPanel() {
        NeighbourPanel neighbourPanel = new NeighbourPanel(textArea);
        return neighbourPanel.getPannel();
    }

    /**
     * Prepares the menu bar for the game with game and help menus.
     *
     * @return A {@code MenuBar} with configured game and help options.
     */

    public MenuBar getMenuBar() {
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

        newGame.setOnAction(actionEvent -> {
            game.stop();
            mainApp.resetGui();
        });
        newGame.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN));

        exit.setOnAction(actionEvent -> System.exit(0));
        exit.setAccelerator(new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN));

        help.setOnAction(actionEvent -> {
            Stage stage = new Stage();
            WebView webview = new WebView();
            webview.getEngine().load(
                    getClass().getResource("/" + game.world().currentPlace().name()+".html").toExternalForm()
            );
            stage.setScene(new Scene(webview, 235, 150));
            stage.setTitle(game.world().currentPlace().name().replace("_", " "));
            stage.show();
        });
        help.setAccelerator(new KeyCodeCombination(KeyCode.H, KeyCombination.CONTROL_DOWN));

        aboutApp.setOnAction(actionEvent ->{
            alert.showAndWait();
        });
        aboutApp.setAccelerator(new KeyCodeCombination(KeyCode.I, KeyCombination.CONTROL_DOWN));


        gameMenu.getItems().addAll(newGame,new SeparatorMenuItem(),exit);
        helpMenu.getItems().addAll(help,new SeparatorMenuItem(),aboutApp);
        return menuBar;
    }

    /**
     * Combines the menu bar with the map and panels into a single {@code VBox}.
     *
     * @return A {@code VBox} containing the menu bar and the main game view.
     */
    public VBox menuAndPanels() {
        HBox mapAndPanels = getMapAndPanels();
        VBox menuAndPanels = new VBox();
        menuAndPanels.getChildren().addAll(getMenuBar(), mapAndPanels);
        return menuAndPanels;
    }

    /**
     * Arranges the given nodes within the specified {@code BorderPane}.
     *
     * @param bottom     The node to be placed at the bottom of the border pane.
     * @param left       The node to be placed on the left side of the border pane.
     * @param center     The node to be placed in the center of the border pane.
     * @param right      The node to be placed on the right side of the border pane.
     * @param top        The node to be placed at the top of the border pane.
     * @param borderPane The {@code BorderPane} to arrange the nodes in.
     */
    public void arrangeItemsInBorderPane(Node bottom, Node left,
                                         Node center, Node right,
                                         Node top, BorderPane borderPane) {
        borderPane.setBottom(bottom != null ? bottom : borderPane.getBottom());
        borderPane.setLeft(left != null ? left : borderPane.getLeft());
        borderPane.setCenter(center != null ? center : borderPane.getCenter());
        borderPane.setRight(right != null ? right : borderPane.getRight());
        borderPane.setTop(top != null ? top : borderPane.getTop());
    }

    /**
     * Clears all nodes from the specified {@code BorderPane}.
     *
     * @param borderPane The {@code BorderPane} to clear.
     */
    public void clearBorderPane(BorderPane borderPane) {
        borderPane.setTop(null);
        borderPane.setBottom(null);
        borderPane.setCenter(null);
        borderPane.setLeft(null);
        borderPane.setRight(null);
    }
}
