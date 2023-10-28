package adv23s._3_1615.dudt05_dudzich.logic;

import adv23s._3_1615.dudt05_dudzich.api.IPlace;

import java.util.*;

public class Place extends ItemContainer implements IPlace {
    /* Stručná charakteristika daného prostoru. */
    private final String description;
    /* Názvy sousedů daného prostoru po startu hry. */
    private final List<String> initialNeighborNames;
    private final Map<String, Place> nameToNeighbour;
    private final Collection <Place> exportedNeighbours;
    public Place(String name, String description,
                 String[] initialNeighborNames,
                 String... initialItemNames) {

        super(name, initialItemNames);
        this.initialNeighborNames = List.of(initialNeighborNames);
        this.description = description;

        nameToNeighbour = new HashMap<>();
        exportedNeighbours = Collections.
                unmodifiableCollection(nameToNeighbour.values());
    }
    /***************************************************************************
     * Vrátí stručný popis daného prostoru.
     *
     * @return Stručný popis daného prostoru
     */
    @Override
    public String description()
    {
        return description;
    }
    /***************************************************************************
     * Vrátí kolekci sousedů daného prostoru, tj. kolekci prostorů,
     * do nichž je možno se z tohoto prostoru přesunout příkazem typu
     * {@link TypeOfStep#tsGOTO TypeOfStep.tsGOTO}.
     *
     * @return Kolekce sousedů
     */
    @Override
    public Collection<Place> neighbors() {
        List<Place> neighborsOfCurrentLocation =
                new ArrayList<>();
        for (String neighbor : World.getInstance()
                .currentPlace().initialNeighborNames) {
            neighborsOfCurrentLocation.add((Place) World.
                    getInstance().
                    place(neighbor));
        }
        return neighborsOfCurrentLocation;
    }

    @Override
    public void initialize()
    {
        initializeNeighbours();
        super.initializeItems();
    }

    private void initializeNeighbours() {
        World world = World.getInstance();
        nameToNeighbour.clear();
        for (var name: initialNeighborNames) {
            nameToNeighbour.put(name, (Place)world.place(name));
        }
    }
}
