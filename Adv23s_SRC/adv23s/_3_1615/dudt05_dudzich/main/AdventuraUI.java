package adv23s._3_1615.dudt05_dudzich.main;

import adv23s._3_1615.dudt05_dudzich.logic.Game;
import adv23s._3_1615.dudt05_dudzich.uiText.TextInterface;
import adv23s._3_1615.dudt05_dudzich.api.IGame;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
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
        borderPane.setCenter(new Label("Hello World!"));

        Scene scene = new Scene(borderPane,300, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Adventura");
        primaryStage.show();
    }
}
