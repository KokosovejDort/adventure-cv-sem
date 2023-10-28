package adv23s._3_1615.dudt05_dudzich.logic;

import adv23s._3_1615.dudt05_dudzich.api.IItem;

public class Item extends Named implements IItem {
    static final int BAG_CAPACITY = Bag.CAPACITY;
    private final int weight;

    public Item(String name) {
        super(name);
        this.weight = 1;
    }

    /***************************************************************************
     * Vrátí váhu předmětu, resp. charakteristiku jí odpovídající.
     * Objekty, které není možno zvednout,
     * mají váhu větší, než je kapacita batohu.
     *
     * @return Váha objektu
     */
    @Override
    public int weight()
    {
        return weight;
    }
}
