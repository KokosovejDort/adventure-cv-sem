package adv23s._3_1615.dudt05_dudzich.main;

import adv23s._3_1615.dudt05_dudzich.api.IGame;
import adv23s._3_1615.dudt05_dudzich.logic.Game;
import adv23s._3_1615.dudt05_dudzich.uiText.TextInterface;

public class Portal
{
    public static void main(String[] args)
    {
        IGame game = Game.getInstance();
        TextInterface ui = new TextInterface(game);
        ui.play();
    }
}
