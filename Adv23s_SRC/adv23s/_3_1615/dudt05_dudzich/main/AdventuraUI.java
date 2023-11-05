package adv23s._3_1615.dudt05_dudzich.main;

import adv23s._3_1615.dudt05_dudzich.api.IGame;
import adv23s._3_1615.dudt05_dudzich.gui.GUI;
import adv23s._3_1615.dudt05_dudzich.logic.Game;
import adv23s._3_1615.dudt05_dudzich.uiText.TextInterface;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * The {@code AdventuraUI} class is the main entry point for the Adventura game application.
 * It sets up the user interface and starts the game in either text-based or GUI mode based on the command-line arguments.
 */
public class AdventuraUI extends Application {
    private static final IGame game = Game.getInstance();
    private BorderPane borderPane;
    private Stage primaryStage;

    /**
     * The main method that is the entry point of the application.
     * It determines the mode of the game (text-based or GUI) based on the command-line arguments.
     *
     * @param args Command-line arguments passed to the application.
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            launch(args);
        }
        else {
            if(args[0].equals("-text")) {
                TextInterface ui = new TextInterface(game);
                ui.play();
            }
            else if(args[0].equals("-gui")) {
                launch(args);
            }
            else {
                System.out.println("Wrong parameter was entered");
            }
        }
    }

    /**
     * Starts the GUI version of the game. It sets up the primary stage and scene for the application.
     *
     * @param primaryStage The primary stage for this application, onto which the application scene can be set.
     */
    @Override
    public void start(Stage primaryStage) {
        this.borderPane = new BorderPane();
        this.primaryStage = primaryStage;
        resetGui();
        Scene scene = new Scene(borderPane, 1050, 675);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Adventura");
        primaryStage.show();
    }

    /**
     * Resets the GUI to its initial state. This method is called to initialize the GUI or to reset it when starting a new game.
     */
    public void resetGui() {
        GUI gui = new GUI(this);
        gui.clearBorderPane(borderPane);

        TextArea textArea = gui.getReadyStartScreen();
        textArea.setText(game.executeCommand(""));
        textArea.setEditable(false);

        VBox menuAndPanels = gui.menuAndPanels();

        TextField inputField = new TextField();
        HBox userInput = gui.getReadyUserInputLabel(inputField);
        inputField.setOnAction(actionEvent -> {
            String command = inputField.getText();
            String gameOutput = game.executeCommand(command);
            textArea.appendText("\n" + gameOutput);
            inputField.clear();
        });

        gui.arrangeItemsInBorderPane(userInput, null, textArea, null, menuAndPanels, borderPane);
    }
}
