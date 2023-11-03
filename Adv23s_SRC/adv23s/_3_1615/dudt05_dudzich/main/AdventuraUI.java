package adv23s._3_1615.dudt05_dudzich.main;

import adv23s._3_1615.dudt05_dudzich.gui.GUI;
import adv23s._3_1615.dudt05_dudzich.gui.GameMap;
import adv23s._3_1615.dudt05_dudzich.logic.Game;
import adv23s._3_1615.dudt05_dudzich.uiText.TextInterface;
import adv23s._3_1615.dudt05_dudzich.api.IGame;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AdventuraUI extends Application {
    private static final IGame game = Game.getInstance();

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

    @Override
    public void start(Stage primaryStage) {
        BorderPane borderPane = new BorderPane();
        GUI gui = new GUI();

        TextArea textArea = gui.getReadyStartScreen();
        textArea.setText(game.executeCommand(""));
        textArea.setEditable(false);

        VBox menuAndPanels = gui.menuAndPanels(game);

        TextField inputField = new TextField();
        HBox userInput = gui.getReadyUserInputLabel(inputField);
        inputField.setOnAction(actionEvent -> {
            String command = inputField.getText();
            String gameOutput = game.executeCommand(command);
            textArea.appendText("\n"+gameOutput);

            inputField.clear();
        });

        gui.arrangeItemsInBorderPane(userInput ,null, textArea,
                null, menuAndPanels, borderPane);
        Scene scene = new Scene(borderPane, 1050, 675);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Adventura");
        primaryStage.show();
    }
}
