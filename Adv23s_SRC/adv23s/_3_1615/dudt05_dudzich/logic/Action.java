package adv23s._3_1615.dudt05_dudzich.logic;

import adv23s._3_1615.dudt05_dudzich.api.IAction;
import adv23s._3_1615.dudt05_dudzich.api.INamed;

import java.util.*;
import java.util.function.Function;

import static adv23s._3_1615.dudt05_dudzich.logic.Scenarios.*;
import static java.util.Map.entry;

public class Action extends Named implements IAction {
    /* Stručná charakteristika dané akce. */
    private final String description;
    private static final Map<String, Action> NAME_TO_ACTION;
    private static final Map<String, Object> NAME_TO_FLAG;
    private static final Map<String, ITest> NAME_TO_TEST;
    static final Map<String, Object> CONDITIONS;
    static final Map<String, ITest> TESTS;
    private static boolean isAlive = false;

    public static boolean isAlive() {
        return isAlive;
    }

    static Collection<Action> allActions() {
        return Collections.unmodifiableCollection(NAME_TO_ACTION.values());
    }

    public static String executeCommand(String command) {
        command = command.trim().toLowerCase();
        if (command.isEmpty()) { return executeEmptyCommand(); }
        else { return executeStandartCommand(command);}
    }

    private static String executeEmptyCommand() {
        if(isAlive) {
            return EMPTY_ERR;
        }
        isAlive = true;
        initialize();
        return START;
    }

    private static String executeStandartCommand(String command) {
        if (!isAlive) {
            return "To start game you need to enter empty command";
        }
        String[] words = command
                .trim()
                .toLowerCase()
                .split("\\s+");
        String actionName = words[0];
        Action action = NAME_TO_ACTION.get(actionName);
        if (action == null) {
            return "There is no command:" +
                    actionName.substring(0, 1).toUpperCase() +
                    actionName.substring(1);
        }
        String result = action.execute(words);
        if (result == null) {
            return "Missing arguments for command: " + actionName;
        }
        return result;
    }

    private static void initialize() {
        World.getInstance().initialize();
        Bag.getInstance().initialize();
        NAME_TO_FLAG.put("altar.activated",false);
        NAME_TO_FLAG.put("usable.areas",List.of("jungle","inside_of_the_cave"));
        NAME_TO_FLAG.put("flashlight.lit", false);
        NAME_TO_FLAG.put("chest.opened", false);
        NAME_TO_FLAG.put("vines.cleared", false);
        NAME_TO_FLAG.put("bats.killed", false);
        NAME_TO_FLAG.put("key.excavated", false);
        NAME_TO_FLAG.put("treasure.excavated", false);
        NAME_TO_FLAG.put("usable", List.of("lighter", "machete", "flashlight"));
        NAME_TO_FLAG.put("excavatable.areas",
                List.of("inside_of_the_cave", "riverside"));
        NAME_TO_FLAG.put("unmovable.objects", List.of("rocks_on_the_floor",
                "chest", "opened_chest", "vines", "trees",
                "ancient_altar", "sharp_Rocks", "mans_Corpse"));
    }

    public static void stop() {
        isAlive = false;
    }

    /***************************************************************************
     * Vrátí popis příkazu s vysvětlením jeho funkce,
     * významu jednotlivých parametrů
     * a možností (resp. účelu) použití daného příkazu.
     * Tento popis tak může sloužit jako nápověda k použití daného příkazu.
     *
     * @return Popis příkazu
     */
    @Override
    public String description() {
        return description;
    }

    /***************************************************************************
     * Metoda realizující reakci hry na zadání daného příkazu.
     * Předávané pole je vždy neprázdné,
     * protože jeho nultý prvek je zadaný název vyvolaného příkazu.
     * Počet argumentů je závislý na konkrétním příkazu,
     * např. příkazy <i>konec</i> a <i>nápověda</i> nemají parametry,
     * příkazy <i>jdi</i> a <i>seber</i> očekávají zadání jednoho argumentu,
     * příkaz <i>použij</i> muže mít dva argumenty atd.
     *
     * @param arguments Parametry příkazu;
     *                  jejich celkový počet muže byt pro každý příkaz jiný,
     *                  ale nultý prvek vždy obsahuje název příkazu
     * @return Text zprávy vypsané po provedeni příkazu
     */
    private final Function<String[], String> action;
    public Action(String name, String description,
                  Function<String[], String> action)
    {
        super(name);
        this.description = description;
        this.action = action;
    }

    @Override
    public String execute(String... arguments) {
        return this.action.apply(arguments);
    }

    private static Item getArgument(String[] arguments) {
        Place currentPlace = World.getInstance().currentPlace();
        return (Item) currentPlace.item(arguments[1].toLowerCase());
    }
    private static boolean ARGUMENT_PRESENT(String[] arguments) {
        return (getArgument(arguments) != null);
    }

    private static boolean ARGUMENT_KILLABLE(String[] arguments) {
        return arguments[1].equalsIgnoreCase("bats");
    }

    private static boolean ARGUMENT_USABLE(String[] arguments) {
        List<String> usable = (List<String>) (NAME_TO_FLAG.get("usable"));
        return usable.contains(arguments[1].toLowerCase());
    }

    private static boolean ARGUMENT_IS_IN_BAG(String[] arguments) {
        if (Arrays.toString(arguments).contains("Excavate")) {
            return Bag.getInstance().item("shovel") != null;
        }
        if (Arrays.toString(arguments).contains("Activate")) {
            return Bag.getInstance().item("ancient_treasure") != null;
        }
        return Bag.getInstance().item(arguments[arguments.length - 1].toLowerCase()) != null;
    }
    private static boolean OPENABLE_PRESENT(String[] arguments) {
        return World.getInstance().currentPlace()
                .item("chest") != null;
    }

    private static boolean EXCAVATABLE_AREA(String[] arguments) {
        List<String> excavatableAres = (List<String>) (NAME_TO_FLAG.get("excavatable.areas"));
        return excavatableAres.contains(World
                .getInstance()
                .currentPlace()
                .name()
                .toLowerCase());
    }

    private static boolean USABLE_AREA(String[] arguments) {
        List<String> usableAreas = (List<String>) (NAME_TO_FLAG.get("usable.areas"));

        return usableAreas.contains(World
                .getInstance()
                .currentPlace()
                .name()
                .toLowerCase());
    }

    static {
        NAME_TO_FLAG = new HashMap<>();
        CONDITIONS = Collections.unmodifiableMap(NAME_TO_FLAG);


        NAME_TO_TEST = Map.ofEntries(
                entry("argument_present", Action::ARGUMENT_PRESENT),
                entry("argument_killable", Action::ARGUMENT_KILLABLE),
                entry("argument_usable", Action::ARGUMENT_USABLE),
                entry("argument_is_in_bag", Action::ARGUMENT_IS_IN_BAG),
                entry("openable_present", Action::OPENABLE_PRESENT),
                entry("excavatable_area", Action::EXCAVATABLE_AREA),
                entry("usable_area", Action::USABLE_AREA)
        );
        TESTS = Collections.unmodifiableMap(NAME_TO_TEST);

        NAME_TO_ACTION = Map.of(
    "go", new Action("Go",
    "You move to neighbour location",
    arguments -> {
        if (arguments.length <= 1) {
            return "You need to enter the name " +
                    "of the location\n" +
                    "you want to go to";
        }
        String destinationName = arguments[1];
        World world = World.getInstance();
        Place currentPlace = world.currentPlace();
        Place destination = INamed.get(destinationName,
                currentPlace.neighbors());
        if (destination == null) {
            return "It's not possible to go " +
                    "from this location\n" +
                    "to location:" + destinationName;
        }
        if (world.currentPlace().name().equalsIgnoreCase("Jungle")) {
            if(!(boolean)NAME_TO_FLAG.get("vines.cleared")) {
                return "You need to clear the vines " +
                        "before you continue your way\n" +
                        "Use machete";
            }
            if(!(boolean)NAME_TO_FLAG.get("bats.killed")) {
                return "You need to kill the bats " +
                        "before you continue your way\n" +
                        "Use machete";
            }
        }
        world.setCurrentPlace(destination);
        if (destination.name().equalsIgnoreCase("River")) {
            isAlive = false;
            return GOTO + destination.description() +
            "\nThe flow is too strong and the water is too cold" +
            "You have no chance to survive. You have lost.";

        }
        assert destination != null;
        return GOTO + destination.description();
    }),
    "take", new Action("Take",
"Put the object in that location into your bag",
    arguments -> {
        if (arguments.length <= 1) {
            return "You need to enter " +
                    "the name of the object\n" +
                    "you want to take";
        }
        World world = World.getInstance();
        Place currentPlace = world.currentPlace();
        Bag bag = Bag.getInstance();
        if (bag.items().size() == bag.capacity()) {
            return "Your bag is full.\n" +
                    "You can't put inside " +
                    "the object:" + arguments[1];
        }
        List<String> unmovableObjects =
                (List<String>) NAME_TO_FLAG.get("unmovable.objects");
        if (unmovableObjects.contains(arguments[1])) {
            return "The specified object cannot " +
                    "be lifted: " + arguments[1];
        }
        Item item = (Item) currentPlace.item(arguments[1]);
        if (!(boolean)NAME_TO_TEST.get("argument_present").test(arguments)) {
            return "The specified object " +
                    "is not in the location: " + arguments[1];
        }
        bag.addItem(new Item(arguments[1]));
        currentPlace.removeItem(new Item(arguments[1]));
        return "You put the object into your bag:" + item;
    }),
    "put", new Action("Put",
"Put down the object from your bag",
    arguments -> {
        if (arguments.length <= 1) {
            return "You need to enter the " +
                    "name of the object\n" +
                    "you want to take " +
                    "out from your bag";
        }
        World world = World.getInstance();
        Place currentPlace = world.currentPlace();
        Bag bag = Bag.getInstance();
        if (!(boolean)NAME_TO_TEST.get("argument_is_in_bag").test(arguments)) {
            return "The specified object " +
                    "is not in the bag: " + arguments[1];
        }
        bag.removeItem(new Item(arguments[1]));
        currentPlace.addItem(new Item(arguments[1]));
        return "You took out an object from your bag:" + arguments[1];
    }),
    "?", new Action("?",
"Show the list of the actions with their descriptions",
    arguments -> "This is available commands:\n" + AVAILABLE_COMMANDS +
            "Remember,in dark locations such as Inside_of_the_cave\n" +
            "you need to light flashlight " +
            "to be able to excavate the key.\n" +
            "Use object Machete from your bag in order to\n" +
            "clear your way in the jungle\n"),
    "exit", new Action("Exit",
"Early termination of the game",
    arguments -> {
        Game.getInstance().stop();
        return END;
    }),
"excavate", new Action("Excavate",
    "You try to dig out smth with your shovel",
    arguments -> {
        String returnStatement = "";
        World world = World.getInstance();
        if (!NAME_TO_TEST.get("excavatable_area").test(new String[]{})) {
            returnStatement = "You can't use 'Excavate' command\n" +
                    "in the current area";
        }
        else if (!NAME_TO_TEST.get("argument_is_in_bag").test(new String[]{"Shovel"})) {
            returnStatement = "You can't use Excavate command without\n" +
                    "object in your bag:Shovel";
        }
        else if (World.getInstance().currentPlace().name().equalsIgnoreCase("Inside_of_the_cave")) {
            if (!(boolean)NAME_TO_FLAG.get("flashlight.lit")) {
                returnStatement = "It is to dark to excavate!";
            }
            else if ((boolean)NAME_TO_FLAG.get("key.excavated")) {
                returnStatement = "You have already excavated the object:Rusty_key";
            }
            else if((boolean)NAME_TO_FLAG.get("flashlight.lit")) {
                world.currentPlace().addItem(new Item("rusty_key"));
                NAME_TO_FLAG.put("key.excavated", true);
                returnStatement = "You used your shovel to dig out the object:Rusty_key";
            }
        }
        else if (World.getInstance().currentPlace().name().equalsIgnoreCase("Riverside")) {
            if(!(boolean)NAME_TO_FLAG.get("treasure.excavated")) {
                world.currentPlace().addItem(new Item("Ancient_treasure"));
                returnStatement =  "You used your shovel to dig out the object:Ancient_treasure.\n"
                        + "You have found the treasure you travelled for.\n";
                NAME_TO_FLAG.put("treasure.excavated", true);
            }
            else if((boolean)NAME_TO_FLAG.get("treasure.excavated")) {
                returnStatement = "You have already excavated the object:Ancient_treasure";
            }
        }
        return returnStatement;
    }),
    "use", new Action("Use",
    "Use one of the item. Assume " +
            "which item is appropriate in the situation",
        arguments -> {
            String returnStatement = "";
            World world = World.getInstance();
            if(arguments.length <= 1) {
                returnStatement = "You need to specify object you want to use";
                return returnStatement;
            }
            else if(!NAME_TO_TEST.get("argument_usable").test(arguments)) {
                returnStatement = "The object is not usable:" + arguments[1];
                return returnStatement;
            }
            else if (!NAME_TO_TEST.get("argument_is_in_bag").test(arguments)) {
                returnStatement = "You can't use " + arguments[1] + " without the " +
                        "object in your bag:" + arguments[1];
                return returnStatement;
            }
            else if(!NAME_TO_TEST.get("usable_area").test(new String[]{})) {
                returnStatement = "You can't use here the object:" + arguments[1];
                return returnStatement;
            }
            else if (NAME_TO_TEST.get("argument_is_in_bag").test(arguments)) {
                if (arguments[1].equalsIgnoreCase("flashlight")) {
                    NAME_TO_FLAG.put("flashlight.lit", true);
                    returnStatement = "You light the Flashlight";
                    return returnStatement;
                }
                if(arguments[1].equalsIgnoreCase("lighter")) {
                    if(!(boolean)NAME_TO_FLAG.get("vines.cleared")) {
                        NAME_TO_FLAG.put("vines.cleared", true);
                        returnStatement = "You used the object to clear your way:Lighter\n" +
                                "Suddenly a flock of bats swoops down on you.\n"
                                + "You need to kill them with Machete.\n" +
                                "Don't try to scare them off with Lighter";
                        world.currentPlace().addItem(new Item("bats"));
                        world.currentPlace().removeItem(new Item("vines"));
                        return returnStatement;
                    }
                    else if((boolean)NAME_TO_FLAG.get("vines.cleared")) {
                        returnStatement = "The vines are already cleared";
                        return returnStatement;
                    }
                }
                if (world.currentPlace().name().equalsIgnoreCase("jungle")) {
                    if (arguments[1].equalsIgnoreCase("machete")) {
                        if (!(boolean) NAME_TO_FLAG.get("vines.cleared")) {
                            NAME_TO_FLAG.put("vines.cleared", true);
                            returnStatement = "You used the object to clear your way:Machete\n" +
                                    "Suddenly a flock of bats swoops down on you.\n"
                                    + "You need to kill them with Machete.\n" +
                                    "Don't try to scare them off with Lighter";
                            world.currentPlace().addItem(new Item("bats"));
                            world.currentPlace().removeItem(new Item("vines"));
                            return returnStatement;
                        }

                        if ((boolean) NAME_TO_FLAG.get("vines.cleared") &&
                                !(boolean) NAME_TO_FLAG.get("bats.killed")) {
                            returnStatement = "You used object to kill the bats:" +
                                    arguments[1];
                            world.currentPlace().addItem(new Item("bat_corpses"));
                            world.currentPlace().removeItem(new Item("bats"));
                            NAME_TO_FLAG.put("bats.killed", true);
                            return returnStatement;
                        } else if ((boolean) NAME_TO_FLAG.get("vines.cleared") &&
                                (boolean) NAME_TO_FLAG.get("bats.killed")) {
                            returnStatement = "The bats are already killed\n" +
                                    "The vines are already cleared";
                            return returnStatement;
                        }
                    }
                }
            }

            return returnStatement;
        }),
    "open", new Action("Open",
    "You try to open the chest.\n" +
        "The chest must be in the same location as you",
            arguments -> {
                String returnStatement = "";
                if((boolean) NAME_TO_FLAG.get("chest.opened")) {
                    returnStatement = "The chest is already opened";
                }
                else if (!NAME_TO_TEST.get("openable_present").test(arguments)) {
                    returnStatement = "There is no openable object in the location";
                }
                else if(!(boolean)NAME_TO_FLAG.get("chest.opened")) {
                    returnStatement = "You used object:Rusty_key, to open the chest\n" +
                            "You see Treasure_map inside the chest";
                    World world = World.getInstance();
                    world.currentPlace().addItem(new Item("treasure_map"));
                    world.currentPlace().removeItem(new Item("Chest"));
                    world.currentPlace().addItem(new Item("opened_chest"));
                    Bag.getInstance().removeItem(new Item("rusty_key"));
                    NAME_TO_FLAG.put("chest.opened", true);
                }
                return returnStatement;
            }),
        "activate", new Action("Activate",
                "You try to open the chest.\n" +
                        "The chest must be in the same location as you",
                arguments -> {
                    World world = World.getInstance();
                    String returnStatement = "";
                    if (!(boolean)NAME_TO_FLAG.get("treasure.excavated")) {
                        returnStatement = "Can't finish the game, while object:Ancient_treasure "+
                                "isn't in your bag";
                    }
                    else if(!NAME_TO_TEST.get("argument_is_in_bag").test(new String[]{"Ancient_treasure"})) {
                        returnStatement = "You can't use acton \"" + arguments[0] + "\" without the " +
                                "object in your bag:Treasure_map";
                    }
                    else if((boolean)NAME_TO_FLAG.get("treasure.excavated") &&
                            NAME_TO_TEST.get("argument_is_in_bag").test(new String[]{"Ancient_treasure"}) &&
                            world.currentPlace().name().equalsIgnoreCase("riverside")){
                        returnStatement = "You activated the Ancient_altar. "
                                + "You successfully completed the game.\n"
                                + "Thank you for playing.";
                        isAlive = false;
                    }
                    return returnStatement;
            })
        );
    }
}
