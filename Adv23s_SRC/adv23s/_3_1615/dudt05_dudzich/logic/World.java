package adv23s._3_1615.dudt05_dudzich.logic;

import adv23s._3_1615.dudt05_dudzich.api.IPlace;
import adv23s._3_1615.dudt05_dudzich.api.IWorld;
import adv23s._3_1615.dudt05_dudzich.util.Observable;
import adv23s._3_1615.dudt05_dudzich.util.Observer;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

import static adv23s._3_1615.dudt05_dudzich.logic.Scenarios.*;

public class World implements IWorld, Observable {
    /* Jediná instance herního světa. */
    private static final World WORLD = new World();
    private final HashMap
            <String, adv23s._3_1615.dudt05_dudzich.logic.Place> nameToPlace;
    private final Collection<adv23s._3_1615.dudt05_dudzich.logic.Place> allPlaces;
    private final adv23s._3_1615.dudt05_dudzich.logic.Place startPlace;
    private adv23s._3_1615.dudt05_dudzich.logic.Place currentPlace;
    private final List<Observer> observers = new CopyOnWriteArrayList<>();

    private World() {
        nameToPlace = new LinkedHashMap<>();

        nameToPlace.put("Entrance_of_the_cave", new adv23s._3_1615.dudt05_dudzich.logic.Place(70,80,
                "Entrance_of_the_cave",
                "Enterence in the dark cave.",
                new String[] { "Inside_of_the_cave" },
                "Blueberries", "Raspberries", "Cones"));

        nameToPlace.put("Inside_of_the_cave", new adv23s._3_1615.dudt05_dudzich.logic.Place(115,185,
                "Inside_of_the_cave",
                INSIDE,
                new String[] { "Entrance_of_the_cave", "Chest_room"  },
                "Rocks_on_the_floor"));

        nameToPlace.put("Chest_room", new adv23s._3_1615.dudt05_dudzich.logic.Place(205,330,
                "Chest_room",
                CHEST_ROOM,
                new String[] {"Inside_of_the_cave", "Jungle"},
                "Chest"));

        nameToPlace.put("Jungle", new adv23s._3_1615.dudt05_dudzich.logic.Place(315,310,
                "Jungle",
                JUNGLE,
                new String[] { "Chest_room", "Riverside" },
                "Vines", "Trees"));

        nameToPlace.put("Riverside", new adv23s._3_1615.dudt05_dudzich.logic.Place(350, 220,
                "Riverside",
                RIVERSIDE,
                new String[] { "Jungle", "River" },
                "Branches", "Ancient_altar"));

        nameToPlace.put("River", new adv23s._3_1615.dudt05_dudzich.logic.Place(315, 100,
                "River",
                "River with cold water " +
                        "and fast flow. Watch out!",
                new String[] { "Riverside" },
                "Fish", "Sharp_Rocks",
                "Mans_Corpse"));

        allPlaces = Collections.unmodifiableCollection(
                nameToPlace.values());
        startPlace = nameToPlace.get("Entrance_of_the_cave");
    }

    public static World getInstance()
    {
        return WORLD;
    }
    /***************************************************************************
     * Vrátí kolekci odkazů na všechny prostory vystupující ve hře.
     *
     * @return Kolekce odkazů na všechny prostory vystupující ve hře
     */
    @Override
    public Collection<adv23s._3_1615.dudt05_dudzich.logic.Place> places() {
        return allPlaces;
    }
    /***************************************************************************
     * Vrátí odkaz na aktuální prostor,
     * tj. na prostor, v němž se hráč pravé nachází.
     *
     * @return Prostor, v němž se hráč pravé nachází
     */
    @Override
    public adv23s._3_1615.dudt05_dudzich.logic.Place currentPlace() {
        return currentPlace;
    }
    /***************************************************************************
     * Je li ve světě hry prostor se zadaným názvem, vrátí jej,
     * není-li tam, vrátí prázdný odkaz {@code null}.
     *
     * @param  name Název hledané prostoru
     * @return Hledaný prostor nebo prázdný odkaz {@code null}
     */
    @Override
    public IPlace place(String name)
    {
        return nameToPlace.get(name);
    }
    /***************************************************************************
     * Nastaví zadaný prosto jako aktuální, tj. jako prostor,
     * v němž se aktuálně nachází hráč.
     *
     * @param destinationRoom Nastavovaný prostor
     */
    @Override
    public void setCurrentPlace(IPlace destinationRoom)
    {
        currentPlace = (Place) destinationRoom;
        notifyObserver();
    }
    /***************************************************************************
     * Inicializuje svět hry, tj. inicializuje propojení prostorů
     * a jejich obsah a nastaví výchozí aktuální prostor.
     */
    @Override
    public void initialize()
    {
        if (currentPlace != null) {
            currentPlace.clearObservers();
        }
        for (IPlace place: nameToPlace.values()) {
            place.initialize();
        }
        currentPlace = startPlace;
        observers.clear();
    }

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void unregisterObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObserver() {
        for (Observer observer: observers) {
            observer.update();
        }
    }
}
