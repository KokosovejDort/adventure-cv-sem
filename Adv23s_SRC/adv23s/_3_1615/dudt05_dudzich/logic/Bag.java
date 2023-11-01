package adv23s._3_1615.dudt05_dudzich.logic;

import adv23s._3_1615.dudt05_dudzich.api.IBag;
import adv23s._3_1615.dudt05_dudzich.util.Observable;
import adv23s._3_1615.dudt05_dudzich.util.Observer;

import java.util.*;

public class Bag extends ItemContainer implements IBag {
    /* Jediná instance batohu. */
    private static final Bag BAG = new Bag();
    static final int CAPACITY = 6;
    private final Collection<Item> items;
    private int remains;

    public static Bag getInstance()
    {
        return BAG;
    }

    public Bag()
    {
        super("'Bag'",
        "Shovel","Flashlight", "Lighter", "Machete");
        items = new ArrayList<>();
    }
    /* Vrátí kapacitu batohu */
    @Override
    public int capacity()
    {
        return CAPACITY;
    }
    /* Initialize batoh na počátku hry. */
    @Override
    public void initialize()
    {
        super.initializeItems();
        remains = CAPACITY;
    }
}
