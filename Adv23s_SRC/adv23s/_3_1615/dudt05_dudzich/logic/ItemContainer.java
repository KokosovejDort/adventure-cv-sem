package adv23s._3_1615.dudt05_dudzich.logic;

import adv23s._3_1615.dudt05_dudzich.api.IItem;
import adv23s._3_1615.dudt05_dudzich.api.IItemContainer;
import adv23s._3_1615.dudt05_dudzich.util.Observable;
import adv23s._3_1615.dudt05_dudzich.util.Observer;

import java.util.*;

abstract class ItemContainer extends Named implements IItemContainer, Observable {
    /* Názvy h-objektů obsažených na počátku každé hry. */
    protected final List<String> initialNames;
    /* Názvy aktuálně obsažených h-objektů. */
    private final List<String> itemNames;
    /* Aktuálně obsažené h-objekty. */
    private final List<IItem> items;
    /* Aktuálně obsažené h-objekty. */
    private final List<IItem> itemsView;
    private final Set<Observer> observers = new HashSet<>();

    public ItemContainer(String name, String... initialNames) {
        super(name);
        this.initialNames = List.of(initialNames);
        this.itemNames = new ArrayList<>();
        this.items = new ArrayList<>();
        this.itemsView = Collections.unmodifiableList(items);
    }
    /***************************************************************************
     * Vrátí kolekci objektů nacházejících se v daném kontejneru.
     *
     * @return Kolekce objektů nacházejících se v daném kontejneru
     */
    @Override
    public Collection<IItem> items()
    {
        return itemsView;
    }
    /***************************************************************************
     * Je li v kontejneru objekt se zadaným názvem, vrátí jej,
     * není-li tam, vrátí prázdný odkaz {@code null}.
     *
     * @param  name Název hledané objektu
     * @return Hledaný objekt nebo prázdný odkaz {@code null}.
     */
    @Override
    public IItem item(String name)
    {
        int index = itemNames.indexOf(name.toLowerCase());
        if (index >= 0) {
            return items.get(index);
        }
        else {
            return null;
        }
    }
    /***************************************************************************
     * Přidá zadaný objekt do kontejneru a vrátí informaci o tom,
     * jestli se to podařilo.
     *
     * @param item Přidávaný objekt
     * @return Podařilo-li se objekt přidat, vrátí {@code true}
     */
    @Override
    public boolean addItem(IItem item)
    {
        items.add(item);
        itemNames.add(item.name().toLowerCase());
        notifyObserver();
        return true;
    }
    /***************************************************************************
     * Odebere zadaný objekt z kontejneru a vrátí informaci o tom,
     * jestli se to podařilo.
     *
     * @param item Odebíraný objekt
     * @return Podařilo-li se objekt odebrat, vrátí {@code true}
     */
    @Override
    public boolean removeItem(IItem item)
    {
        int index = itemNames.indexOf(item.name().toLowerCase());
        if (index < 0) { return false; }
        itemNames.remove(index);
        items.remove(index);
        notifyObserver();
        return true;
    }
    /***************************************************************************
     * Inicializuje kontejner na počátku hry.
     * Po inicializace bude obsahovat příslušnou výchozí sadu objektů.
     */
    @Override
    public void initializeItems()
    {
        itemNames.clear();
        items.clear();
        for (String name : initialNames) {
            itemNames.add(name.toLowerCase());
            items.add(new Item(name.toLowerCase()));
        }
        notifyObserver();
    }

    @Override
    public void registerObserver(Observer observer) {
        this.observers.add(observer);
    }

    @Override
    public void unregisterObserver(Observer observer) {
        this.observers.remove(observer);
    }

    @Override
    public void notifyObserver() {
        for (Observer observer: observers) {
            observer.update();
        }
    }

    public List<String> getItemNames() {
        return itemNames;
    }

}
