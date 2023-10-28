package adv23s._3_1615.dudt05_dudzich.uiText;

import adv23s._3_1615.dudt05_dudzich.api.IGame;

import java.util.Scanner;

public class TextInterface {
    private IGame game;

    public TextInterface(IGame game) {
        this.game = game;
    }

    public void play() {
        System.out.println(game.executeCommand(""));

        while (game.isAlive()) {
            String row = readString();
            System.out.println(game.executeCommand(row));
        }
    }

    private String readString() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("> ");
        return scanner.nextLine();
    }
}
