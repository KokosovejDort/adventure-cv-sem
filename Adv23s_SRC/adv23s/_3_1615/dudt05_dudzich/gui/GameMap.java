package adv23s._3_1615.dudt05_dudzich.gui;

import adv23s._3_1615.dudt05_dudzich.logic.World;
import adv23s._3_1615.dudt05_dudzich.util.Observer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

import java.util.Objects;

public class GameMap implements Observer {
    private AnchorPane anchorPane = new AnchorPane();
    private Circle point = new Circle(10, Paint.valueOf("RED"));
    private World world = World.getInstance();
    private ImageView imageView;

    private void setPlayerPosition() {
        AnchorPane.setTopAnchor(point, world.currentPlace().getPosTop());
        AnchorPane.setLeftAnchor(point, world.currentPlace().getPosLeft());
    }

    public GameMap() {
        Image image = new Image(Objects.requireNonNull(GameMap.class.getClassLoader()
                .getResourceAsStream("gameMap.png")),
                400, 400, false, false);
        imageView = new ImageView(image);
        world.registerObserver(this);
        setPlayerPosition();
        anchorPane.getChildren().addAll(imageView, point);
    }

    public AnchorPane getAnchorPane() {
        return anchorPane;
    }

    @Override
    public void update() {
        setPlayerPosition();
    }
}
