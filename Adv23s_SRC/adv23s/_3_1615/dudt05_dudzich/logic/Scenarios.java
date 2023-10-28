package adv23s._3_1615.dudt05_dudzich.logic;
/* Saved in UTF-8 codepage: Příliš žluťoučký kůň úpěl ďábelské ódy. */

import adv23s._3_1615.dudt05_dudzich.api.Scenario;
import adv23s._3_1615.dudt05_dudzich.api.ScenarioStep;
import adv23s._3_1615.dudt05_dudzich.api.TypeOfScenario;

import java.util.List;
import java.util.Map;

import static adv23s._3_1615.dudt05_dudzich.api.TypeOfScenario.*;
import static adv23s._3_1615.dudt05_dudzich.api.TypeOfStep.*;

public class Scenarios
{
public static String START,EMPTY_ERR, INSIDE,CHEST_ROOM,
        JUNGLE, RIVERSIDE, GOTO, END;
public static final List<String> AVAILABLE_COMMANDS = List.of(
       "-->Go 'Name of the location' - you move to neighbour location\n",
       "-->Take 'Name of the object you want to take' - you\n" +
               " put the object in that location into your bag.\n",
       "-->Put down 'Name of the object you want to put down' - you\n" +
               " put down the object from your bag.\n",
       "-->Use 'Name of the object you want to use' - you\n" +
                "use one of the item. Assume " +
               "which item is appropriate in the situation\n",
       "-->Excavate - you try to dig out smth with your shovel.\n",
       "-->Light 'Name of the object' - you light the object with the\n" +
               "Lighter. You can light only 'burnable' objects.\n",
       "-->Kill 'Name of the object' - you try to kill the object.\n" +
               "You can kill only killable objects.\n",
       "-->Open - you try to open the chest.\n" +
               "The chest must be in the same location as you.\n",
       "-->Activate - you activating the altar and " +
               "celebrating your victory.\n" +
               "You can use the command only after finishing your quest.\n"
);
public static final String SUBJECT = "You are treasure hunter," +
        "who searches for ancient treasure\n" +
        "to remove curse from your family\n"
        + "With the commands you entered, you control you character.\n"
        + "Start by venturing into the cave and finding a key.\n"
        + "Then, use the key to unlock the chest\n" +
        " and retrieve the treasure map.\n"
        + "Navigate through the jungle to the riverside" +
        " to claim your treasure\n" +
        "Then place the treasure on the ancient\n" +
        "altar in the riverside to remove the curse.\n"
        + "But watch out for nasty surprises in the jungle\n\n";

private static final ScenarioStep START_STEP =
        new ScenarioStep(0, tsSTART, "",
    START = "Welcome!\n" + SUBJECT + "\nIf you don't know what to do, " +
                "type ? to get help.",
        "Entrance_of_the_cave",
        new String[] { "Inside_of_the_cave" },
        new String[] { "Blueberries", "Raspberries", "Cones"},
        new String[] { "Shovel","Flashlight", "Lighter", "Machete" },
        null,
        List.of(
                "argument_present",//Argument is in the area
                "argument_killable", //Argument can be killed
                "argument_usable", //Argument can be used
                "argument_is_in_bag", //Argument is in the character's bag
                "openable_present", //There's openable object in the room
                "excavatable_area", //Character is in specified area
                "usable_area" //Character can use objects
                                // only in specified areas
        ),
        Map.ofEntries(
            Map.entry("usable.areas",List.of("jungle","inside_of_the_cave")),
                    //Areas where character can use objects
            Map.entry("flashlight.lit", false), //Flashlight isn't lit
            Map.entry("chest.opened", false), //Chest is closed
            Map.entry("vines.cleared", false), //Wines aren't burned
            Map.entry("bats.killed", false), //Bats aren't killed
            Map.entry("key.excavated", false), //Key is not excavated
            Map.entry("treasure.excavated", false),
                    //Treasure is not excavated
            Map.entry("usable", List.of("lighter", "machete", "flashlight")),
                        //Object which can be used
            Map.entry("excavatable.areas",
                    List.of("inside_of_the_cave", "riverside")),
            //Areas, where character can use command "excavate"
            Map.entry("unmovable.objects", List.of("rocks_on_the_floor",
                    "chest", "opened_chest", "vines", "trees",
                    "ancient_altar", "sharp_Rocks", "mans_Corpse"))
                //Objects which you can't put into your bag
        )
);

private static final Scenario HAPPY =
        new Scenario(TypeOfScenario.scHAPPY,
    START_STEP,

    new ScenarioStep(1,tsGOTO, "Go Inside_of_the_cave",
(GOTO = "You moved to a new location.") +
        (INSIDE = "Dark cave. You see nothing until you use flashlight"),
        "Inside_of_the_cave",
        new String[] { "Entrance_of_the_cave", "Chest_room" },
        new String[] { "Rocks_on_the_floor" },
        new String[] { "Shovel","Flashlight", "Lighter", "Machete" },
        null, //The flashlight mustn't be lit
        null, null
    ),
    new ScenarioStep(2, tsNS_1, "Use Flashlight",
        "You light the Flashlight",
        "Inside_of_the_cave",
        new String[] { "Entrance_of_the_cave", "Chest_room"  },
        new String[] { "Rocks_on_the_floor" },
        new String[] { "Shovel","Lighter", "Flashlight", "Machete" },
        null, //The flashlight mustn't be lit
        List.of("argument_usable",
                "argument_is_in_bag"), //The argument should be in the bag
        Map.of("flashlight.lit", true) //The flashlight is lit
    ),
    new ScenarioStep(3,tsNS_0, "Excavate",
        "You used your shovel to dig out the object:Rusty_key",
            "Inside_of_the_cave",
        new String[] { "Entrance_of_the_cave", "Chest_room" },
        new String[] { "Rocks_on_the_floor", "Rusty_key" },
        new String[] { "Shovel","Lighter", "Flashlight", "Machete" },
            Map.of("key.excavated", false), //The key isn't excavated yet
            List.of("excavatable_area",//'Excavate' can be used in this area
                    "argument_is_in_bag"), //The argument "Shovel" is in bag
            Map.of("flashlight.lit", true, //The flashlight is lit
                    "key.excavated", true) //The key is excavated
    ),
    new ScenarioStep(4, tsTAKE, "Take Rusty_key",
        "You put the object into your bag:Rusty_key",
            "Inside_of_the_cave",
        new String[] { "Entrance_of_the_cave", "Chest_room" },
        new String[] { "Rocks_on_the_floor" },
        new String[] { "Shovel","Lighter", "Flashlight",
                "Machete", "Rusty_key" },
            Map.of("key.excavated", true,//The key is excavated
                    "flashlight.lit", true), //The flashlight is lit
            null,
            Map.of("flashlight.lit", true) //The flashlight is lit
    ),
    new ScenarioStep(5, tsGOTO, "Go Chest_room",
            "You moved to a new location."
            + (CHEST_ROOM="You are in a room with the " +
                    "chest.\n")
            + "Use 'Rusty_key' to open it",
            "Chest_room",
            new String[] { "Inside_of_the_cave", "Jungle" },
            new String[] { "Chest" },
            new String[] { "Shovel","Lighter", "Flashlight",
                    "Machete", "Rusty_key" }
    ),
    new ScenarioStep(6,tsNS_0, "Open",
        "You used object:Rusty_key, to open the chest\n"
      + "You see Treasure_map inside the chest",
            "Chest_room",
        new String[] { "Inside_of_the_cave", "Jungle" },
        new String[] { "Opened_chest", "Treasure_map"},
        new String[] { "Shovel","Lighter", "Flashlight", "Machete"},
            Map.of("chest.opened", false),//Chest is not opened
            List.of("openable_present"),//Openable object is in the room
            Map.of("chest.opened", true)//Chest is opened
    ),
    new ScenarioStep(7,tsTAKE, "Take Treasure_map",
        "You put the object into your bag:Treasure_map\n"
            + "You see cross on the map. " +
                "It is in the Jungle, near the River\n"
            + "according to the Treasure_map",
            "Chest_room",
        new String[] { "Inside_of_the_cave", "Jungle" },
        new String[] { "Opened_chest"},
        new String[] { "Shovel","Lighter", "Flashlight",
                "Machete", "Treasure_map" },
            Map.of("chest.opened", true),//Chest is opened
            null,
            null
    ),
    new ScenarioStep(8,tsGOTO, "Go Jungle",
        "You moved to a new location."
            + (JUNGLE="You're in the jungle with vines.") + "\n"
            + "Use machete or lighter to get through them.",
        "Jungle",
        new String[] { "Chest_room", "Riverside" },
        new String[] { "Vines", "Trees" },
        new String[] { "Shovel","Lighter", "Flashlight",
                "Machete", "Treasure_map"},
            Map.of("vines.cleared",false),
            null,
            null
    ),
    new ScenarioStep(9,tsNS_1, "Use Lighter",
        "You used the object to clear your way:Lighter\n" +
            "Suddenly a flock of bats swoops down on you.\n"
            + "You need to kill them with Machete.\n" +
            "Don't try to scare them off with Lighter",
        "Jungle",
        new String[] { "Chest_room", "Riverside" },
        new String[] { "Bats", "Trees" },
        new String[] { "Shovel", "Lighter","Flashlight", "Machete", "Treasure_map"},
            Map.of("bats.killed", false,//Bats
                    "vines.cleared", false),//Vines are not cleared
            List.of("usable_area",//The character is in usable area
                    "argument_is_in_bag",//The argument "Lighter" is in bag
                    "argument_usable"), //The argument can be used
            Map.of("vines.cleared", true)//Vines are cleared
    ),
    new ScenarioStep(10, tsNS_1, "Use Machete",
        "You used object to kill the bats:Machete",
        "Jungle",
        new String[] { "Chest_room", "Riverside" },
        new String[] { "Bat_corpses", "Trees" },
        new String[] { "Shovel","Lighter", "Flashlight", "Machete", "Treasure_map" },
            Map.of("bats.killed", false), //Bats are not killed
            List.of("usable_area",//The character is in usable area
                    "argument_is_in_bag", //The arg "Machete" is in bag
                    "argument_usable"), //The argument can be used
            Map.of("bats.killed", true)//Bats are killed
    ),
    new ScenarioStep(11,tsGOTO, "Go Riverside",
        "You moved to a new location."
          + (RIVERSIDE="You are standing on a sandy riverbank.\n"
          + "Not far from you you notice a cross in the sand"),
        "Riverside",
        new String[] { "Jungle", "River" },
        new String[] { "Branches", "Ancient_altar" },
        new String[] { "Shovel","Lighter", "Flashlight",
                "Machete", "Treasure_map"}
    ),
    new ScenarioStep(12,tsNS_0, "Excavate",
    "You used your shovel to dig out the object:Ancient_treasure.\n"
        + "You have found the treasure you travelled for.\n",
    "Riverside",
    new String[] { "Jungle", "River" },
    new String[] { "Branches", "Ancient_treasure",
            "Ancient_altar"},
    new String[] { "Shovel", "Lighter","Flashlight", "Machete", "Treasure_map"},
            Map.of("treasure.excavated", false),
                //Treasure is not excavated
            List.of("excavatable_area",//'Excavate' can be used in this area
                    "argument_is_in_bag"),//The argument "Shovel" is in bag
            Map.of("treasure.excavated", true)//Treasure is excavated
    ),
    new ScenarioStep(13,tsTAKE, "Take Ancient_treasure",
    "You put the object into your bag:Ancient_Treasure",
    "Riverside",
    new String[] { "Jungle", "River" },
    new String[] {"Branches", "Ancient_altar" },
    new String[] { "Shovel", "Lighter","Flashlight", "Machete",
            "Treasure_map", "Ancient_treasure"},
    Map.of("treasure.excavated", true),//The treasure is excavated
    null,
null
    ),
    new ScenarioStep(14,tsPUT_DOWN, "Put Treasure_map",
        "You took out an object from your bag:Treasure_map",
        "Riverside",
        new String[] { "Jungle", "River" },
        new String[] {"Branches", "Ancient_altar", "Treasure_map"},
        new String[] { "Shovel","Lighter","Flashlight", "Machete",
        "Ancient_treasure"},
        null,
        List.of("argument_is_in_bag"),
            null
    ),
    new ScenarioStep(15,tsSUCCESS, "Activate",
        "You activated the Ancient_altar. "
                + "You successfully completed the game.\n"
                + "Thank you for playing.",
        "Riverside",
        new String[] { "Jungle", "River" },
        new String[] { "Branches", "Ancient_altar", "Treasure_map"},
        new String[] {"Shovel" ,"Lighter", "Flashlight","Machete",
                "Ancient_treasure"},
        null,
            List.of("argument_is_in_bag"),
        null
    )
);

////////////////////////////////////////////////////////////////////////////////
private static final Scenario BASIC = new Scenario(scBASIC,
    START_STEP,

    new ScenarioStep(1, tsTAKE, "Take Blueberries",
            "You put the object into your bag:Blueberries",
            "Entrance_of_the_cave",
            new String[] { "Inside_of_the_cave" },
            new String[] { "Raspberries", "Cones"},
            new String[] { "Shovel","Flashlight", "Lighter",
                    "Machete", "Blueberries" }
    ),

    new ScenarioStep(tsGOTO, "Go Inside_of_the_cave",
            "You moved to a new location."
            + "Dark cave. You see nothing until you use flashlight",
            "Inside_of_the_cave",
            new String[] { "Entrance_of_the_cave", "Chest_room" },
            new String[] { "Rocks_on_the_floor" },
            new String[] { "Shovel","Flashlight", "Lighter",
                    "Machete", "Blueberries" }
    ),

    new ScenarioStep(tsPUT_DOWN, "Put Blueberries",
            "You took out an object from your bag:Blueberries",
            "Inside_of_the_cave",
            new String[] { "Entrance_of_the_cave", "Chest_room" },
            new String[] { "Blueberries", "Rocks_on_the_floor" },
            new String[] { "Shovel","Flashlight", "Lighter", "Machete"}
    ),

    new ScenarioStep(tsHELP, "?",
            "This is available commands:\n" + AVAILABLE_COMMANDS +
            "Remember,in dark locations such as Inside_of_the_cave\n" +
            "you need to light flashlight to be able to excavate the key.\n" +
            "Use object Machete from your bag in order to\n" +
            "clear your way in the jungle\n",
            "Inside_of_the_cave",
            new String[] { "Entrance_of_the_cave", "Chest_room" },
            new String[] { "Blueberries", "Rocks_on_the_floor" },
            new String[] { "Shovel","Flashlight", "Lighter", "Machete"}
    ),

    new ScenarioStep(tsEND, "Exit",
            "You have exited the game.\n" +
            "Thank you for playing.",
            "Inside_of_the_cave",
            new String[] { "Entrance_of_the_cave", "Chest_room" },
            new String[] { "Blueberries", "Rocks_on_the_floor" },
            new String[] { "Shovel","Flashlight", "Lighter", "Machete"}
    )
);

////////////////////////////////////////////////////////////////////////////////

private static final Scenario MISTAKES = new Scenario(scMISTAKES,
        new ScenarioStep(-1, tsNOT_START, "Start",
                "\nTo start game you need to enter empty command",
                "",
                new String[] {},
                new String[] {},
                new String[] {}
        ),
        START_STEP,
        new ScenarioStep(1, tsEMPTY, "",
        EMPTY_ERR = "You can use empty command only to start the game",
                "Entrance_of_the_cave",
                new String[] { "Inside_of_the_cave" },
                new String[] { "Blueberries", "Raspberries", "Cones"},
                new String[] { "Shovel","Flashlight", "Lighter", "Machete" }
        ),
        new ScenarioStep(tsUNKNOWN, "Leave",
                "There is no command:Leave",
                "Entrance_of_the_cave",
                new String[] { "Inside_of_the_cave" },
                new String[] { "Blueberries", "Raspberries", "Cones"},
                new String[] { "Shovel","Flashlight", "Lighter", "Machete" }
        ),
        new ScenarioStep(tsMOVE_WA, "Go",
                "You need to enter the name of the location\n" +
                "you want to go to",
                "Entrance_of_the_cave",
                new String[] { "Inside_of_the_cave" },
                new String[] { "Blueberries", "Raspberries", "Cones"},
                new String[] { "Shovel","Flashlight", "Lighter", "Machete" }
        ),
        new ScenarioStep(tsBAD_NEIGHBOR, "Go Jungle",
                "It's not possible to go from this location\n" +
                "to location:Jungle",
                "Entrance_of_the_cave",
                new String[] { "Inside_of_the_cave" },
                new String[] { "Blueberries", "Raspberries", "Cones"},
                new String[] { "Shovel","Flashlight", "Lighter", "Machete" }
        ),
        new ScenarioStep(tsTAKE_WA, "Take",
                "You need to enter the name of the object\n" +
                    "you want to take",
                "Entrance_of_the_cave",
                new String[] { "Inside_of_the_cave" },
                new String[] { "Blueberries", "Raspberries", "Cones"},
                new String[] { "Shovel","Flashlight", "Lighter", "Machete" }
        ),
        new ScenarioStep(tsBAD_ITEM, "Take Strawberries",
                "The specified object " +
                        "is not in the location: Strawberries",
                "Entrance_of_the_cave",
                new String[] { "Inside_of_the_cave" },
                new String[] { "Blueberries", "Raspberries", "Cones"},
                new String[] { "Shovel","Flashlight", "Lighter", "Machete" }
        ),
        new ScenarioStep(tsPUT_DOWN_WA, "Put",
                "You need to enter the name of the object\n" +
                        "you want to take out from your bag",
                "Entrance_of_the_cave",
                new String[] { "Inside_of_the_cave" },
                new String[] { "Blueberries", "Raspberries", "Cones"},
                new String[] { "Shovel","Flashlight", "Lighter", "Machete" }
        ),
        new ScenarioStep(tsNOT_IN_BAG, "Put Knife",
                "The specified object is not in the bag: Knife",
                "Entrance_of_the_cave",
                new String[] { "Inside_of_the_cave" },
                new String[] { "Blueberries", "Raspberries", "Cones"},
                new String[] { "Shovel","Flashlight", "Lighter", "Machete" }
        ),
        new ScenarioStep(tsTAKE, "Take Raspberries",
                "You put the object into your bag:Raspberries",
                "Entrance_of_the_cave",
                new String[] { "Inside_of_the_cave" },
                new String[] { "Blueberries", "Cones"},
                new String[] { "Raspberries","Shovel","Flashlight",
                        "Lighter", "Machete" }
        ),
        new ScenarioStep(tsTAKE, "Take Blueberries",
                "You put the object into your bag:Blueberries",
                "Entrance_of_the_cave",
                new String[] { "Inside_of_the_cave" },
                new String[] { "Cones" },
                new String[] { "Blueberries","Raspberries","Shovel",
                        "Flashlight", "Lighter", "Machete" }
        ),
        new ScenarioStep(tsBAG_FULL, "Take Cones",
                "Your bag is full.\n" +
                "You can't put inside the object:Cones",
                "Entrance_of_the_cave",
                new String[] { "Inside_of_the_cave" },
                new String[] { "Cones" },
                new String[] { "Blueberries","Raspberries","Shovel",
                        "Flashlight", "Lighter", "Machete" }
        ),
        new ScenarioStep(tsGOTO, "Go Inside_of_the_cave",
                "You moved to a new location."
                        + "Dark cave. You see nothing until you use flashlight",
                "Inside_of_the_cave",
                new String[] { "Entrance_of_the_cave", "Chest_room" },
                new String[] { "Rocks_on_the_floor"},
                new String[] { "Blueberries","Raspberries","Shovel",
                        "Flashlight", "Lighter", "Machete" }
        ),
        new ScenarioStep(tsPUT_DOWN, "Put Blueberries",
                "You took out an object from your bag:Blueberries",
                "Inside_of_the_cave",
                new String[] { "Entrance_of_the_cave", "Chest_room" },
                new String[] { "Blueberries", "Rocks_on_the_floor" },
                new String[] { "Raspberries","Shovel",
                        "Flashlight", "Lighter", "Machete" }
        ),
        new ScenarioStep(tsUNMOVABLE, "Take Rocks_on_the_floor",
                "The specified object cannot " +
                        "be lifted: Rocks_on_the_floor",
                "Inside_of_the_cave",
                new String[] { "Entrance_of_the_cave", "Chest_room" },
                new String[] { "Blueberries", "Rocks_on_the_floor" },
                new String[] { "Raspberries","Shovel",
                        "Flashlight", "Lighter", "Machete" }
        ),
        new ScenarioStep(tsHELP, "?",
                "This is available commands:\n" + AVAILABLE_COMMANDS +
                        "Remember, that in dark locations " +
                        "such as Inside_of_the_cave\n" +
                        "you need to light your flashlight " +
                        "to be able to excavate the key.\n" +
                        "Use objects Machete or Lighter " +
                        "from your bag in order to\n" +
                        "scare off the bats or clear " +
                        "your way in the jungle\n",
                "Inside_of_the_cave",
                new String[] { "Entrance_of_the_cave", "Chest_room" },
                new String[] { "Blueberries", "Rocks_on_the_floor" },
                new String[] { "Raspberries","Shovel",
                        "Flashlight", "Lighter", "Machete" }
        ),
        new ScenarioStep(tsEND, "Exit",
                (END = "You have exited the game.\n" +
                        "Thank you for playing."),
                "Inside_of_the_cave",
                new String[] { "Entrance_of_the_cave", "Chest_room" },
                new String[] { "Blueberries", "Rocks_on_the_floor" },
                new String[] { "Raspberries","Shovel",
                        "Flashlight", "Lighter", "Machete" }
        )
);
////////////////////////////////////////////////////////////////////////////////
private static final Scenario MISTAKES_NS = new Scenario(scMISTAKES_NS,
    START_STEP,

    new ScenarioStep(HAPPY.steps().get(1)),

    new ScenarioStep(tsPUT_DOWN,"Put Flashlight",
            "You took out an object from your bag:Flashlight",
        "Inside_of_the_cave",
        new String[] { "Entrance_of_the_cave", "Chest_room" },
        new String[] { "Flashlight", "Rocks_on_the_floor" },
        new String[] { "Shovel", "Lighter", "Machete" },
        null,
        List.of("burnable_present"),
        null
    ),
    new ScenarioStep(tsNS1_WrongCond,"Use Flashlight",
        "You can't Use Flashlight without the " +
                "object in your bag:Flashlight",
        "Inside_of_the_cave",
        new String[] { "Entrance_of_the_cave", "Chest_room" },
        new String[] { "Flashlight", "Rocks_on_the_floor" },
        new String[] { "Shovel", "Lighter", "Machete" },
        null,
        List.of("argument_is_in_bag"),
        null
    ),
    new ScenarioStep(tsTAKE,"Take Flashlight",
        "You put the object into your bag:Flashlight",
        "Inside_of_the_cave",
        new String[] { "Entrance_of_the_cave", "Chest_room" },
        new String[] { "Rocks_on_the_floor" },
        new String[] { "Flashlight","Shovel", "Lighter", "Machete" }
    ),
    new ScenarioStep(tsNS0_WrongCond,"Excavate",
        "It is to dark to excavate!",
        "Inside_of_the_cave",
        new String[] { "Entrance_of_the_cave", "Chest_room" },
        new String[] { "Rocks_on_the_floor" },
        new String[] { "Flashlight","Shovel", "Lighter", "Machete" },
        Map.of("flashlight.lit", true),
        null
    ),
    new ScenarioStep(tsPUT_DOWN,"Put Shovel",
            "You took out an object from your bag:Shovel",
            "Inside_of_the_cave",
            new String[] { "Entrance_of_the_cave", "Chest_room" },
            new String[] { "Shovel", "Rocks_on_the_floor" },
            new String[] { "Lighter", "Machete", "Flashlight" }
    ),
    new ScenarioStep(tsNS0_WrongCond,"Excavate",
            "You can't use Excavate command without\n" +
                    "object in your bag:Shovel",
            "Inside_of_the_cave",
            new String[] { "Entrance_of_the_cave", "Chest_room" },
            new String[] { "Shovel", "Rocks_on_the_floor" },
            new String[] { "Lighter", "Machete", "Flashlight" },
            null,
            List.of("argument_is_in_bag"),
            null
    ),
    new ScenarioStep(tsTAKE,"Take Shovel",
            "You put the object into your bag:Shovel",
            "Inside_of_the_cave",
            new String[] { "Entrance_of_the_cave", "Chest_room" },
            new String[] { "Rocks_on_the_floor" },
            new String[] { "Flashlight","Shovel", "Lighter", "Machete" }
    ),
    new ScenarioStep(tsPUT_DOWN,"Put Lighter",
            "You took out an object from your bag:Lighter",
            "Inside_of_the_cave",
            new String[] { "Entrance_of_the_cave", "Chest_room" },
            new String[] { "Lighter", "Rocks_on_the_floor" },
            new String[] { "Shovel", "Machete", "Flashlight" }
    ),
    new ScenarioStep(tsNS1_WrongCond,"Use Lighter",
            "You can't use Lighter without the " +
                    "object in your bag:Lighter",
            "Inside_of_the_cave",
            new String[] { "Entrance_of_the_cave", "Chest_room" },
            new String[] { "Lighter", "Rocks_on_the_floor" },
            new String[] { "Shovel", "Machete", "Flashlight" },
            null,
            List.of("argument_is_in_bag"),
            null
    ),
    new ScenarioStep(tsTAKE,"Take Lighter",
            "You put the object into your bag:Lighter",
            "Inside_of_the_cave",
            new String[] { "Entrance_of_the_cave", "Chest_room" },
            new String[] { "Rocks_on_the_floor" },
            new String[] { "Flashlight","Shovel", "Lighter", "Machete" }
    ),
    new ScenarioStep(tsNS0_WrongCond, "Open",
        "There is no openable object in the location",
        "Inside_of_the_cave",
        new String[] { "Entrance_of_the_cave", "Chest_room" },
        new String[] { "Rocks_on_the_floor" },
        new String[] { "Flashlight","Shovel", "Lighter", "Machete" },
        null,
        List.of("openable_present"),
        null
    ),
    new ScenarioStep(tsNOT_SUCCESS, "Activate",
        "Can't finish the game, while object:Ancient_treasure "+
        "isn't in your bag",
        "Inside_of_the_cave",
        new String[] { "Entrance_of_the_cave", "Chest_room" },
        new String[] { "Rocks_on_the_floor" },
        new String[] { "Flashlight","Shovel", "Lighter", "Machete" },
        null,
        List.of("argument_is_in_bag"),
        null
    ),
    new ScenarioStep(HAPPY.steps().get(2)),
    new ScenarioStep(HAPPY.steps().get(3)),
    new ScenarioStep(3,tsNS0_WrongCond, "Excavate",
        "You have already excavated the object:Rusty_key",
        "Inside_of_the_cave",
        new String[] { "Entrance_of_the_cave", "Chest_room" },
        new String[] { "Rocks_on_the_floor", "Rusty_key" },
        new String[] { "Shovel","Flashlight", "Lighter", "Machete" },
        Map.of("key.excavated", false),
        null,
        null
    ),
    new ScenarioStep(HAPPY.steps().get(4)),
    new ScenarioStep(HAPPY.steps().get(5)),
    new ScenarioStep(HAPPY.steps().get(6)),
    new ScenarioStep(tsNS0_WrongCond, "Open",
            "The chest is already opened",
            "Chest_room",
            new String[] { "Inside_of_the_cave", "Jungle" },
            new String[] { "Opened_chest", "Treasure_map"},
            new String[] { "Shovel", "Flashlight","Lighter", "Machete"},
            Map.of("chest.opened", false),
            null,
            null
    ),
    new ScenarioStep(HAPPY.steps().get(7)),
    new ScenarioStep(HAPPY.steps().get(8)),
    new ScenarioStep(HAPPY.steps().get(9)),
    new ScenarioStep(tsNS1_WrongCond, "Use Lighter",
            "The vines are already cleared\n" +
                    "You can't use the object:Lighter",
            "Jungle",
            new String[] { "Chest_room", "Riverside" },
            new String[] { "Trees","Bats" },
            new String[] { "Shovel","Flashlight", "Lighter", "Machete", "Treasure_map"},
            Map.of("vines.cleared",false),
            null,
            null
    ),
    new ScenarioStep(HAPPY.steps().get(10)),
    new ScenarioStep(tsNS1_WrongCond, "Use Machete",
            "The bats are already killed\n" +
                    "You can't use the object:Machete",
            "Jungle",
            new String[] { "Chest_room", "Riverside" },
            new String[] { "Bat_corpses", "Trees" },
            new String[] { "Shovel","Flashlight", "Lighter", "Machete", "Treasure_map" },
            Map.of("bats.killed", false),
            null,
            null
    ),
    new ScenarioStep(tsNS1_0Args, "Use",
        "You need to specify object you want to use",
        "Jungle",
        new String[] { "Chest_room", "Riverside" },
        new String[] { "Bat_corpses", "Trees" },
        new String[] { "Shovel","Flashlight", "Lighter", "Machete", "Treasure_map" }
    ),
    new ScenarioStep(tsNS1_WrongCond, "Use Treasure_map",
            "The object is not usable:Treasure_map",
            "Jungle",
            new String[] { "Chest_room", "Riverside" },
            new String[] { "Bat_corpses","Trees" },
            new String[] { "Shovel","Flashlight", "Lighter", "Machete", "Treasure_map" },
            null,
            List.of("argument_usable"),
            null
    ),
    new ScenarioStep(tsNS0_WrongCond, "Excavate",
            "You can't use 'Excavate' command\n" +
            "in the current area",
            "Jungle",
            new String[] { "Chest_room", "Riverside" },
            new String[] { "Bat_corpses","Trees" },
            new String[] { "Shovel","Flashlight", "Lighter", "Machete", "Treasure_map" },
            null,
            List.of("excavatable_area"),
            null
    ),
    new ScenarioStep(HAPPY.steps().get(11)),
    new ScenarioStep(HAPPY.steps().get(12)),
    new ScenarioStep(tsNS0_WrongCond, "Excavate",
            "You have already excavated the object:Ancient_treasure",
            "Riverside",
            new String[] { "Jungle", "River" },
            new String[] { "Branches", "Ancient_treasure",
                    "Ancient_altar"},
            new String[] { "Shovel", "Flashlight","Lighter", "Machete", "Treasure_map"},
            Map.of("treasure.excavated", false),
            null,
            null
    ),
    new ScenarioStep(tsNS1_WrongCond, "Use Machete",
            "You can't use here the object:Machete",
            "Riverside",
            new String[] { "Jungle", "River" },
            new String[] { "Branches", "Ancient_treasure",
                    "Ancient_altar" },
            new String[] { "Shovel","Flashlight", "Lighter", "Machete", "Treasure_map" },
            null,
            List.of("usable_area"),
            null
    ),
    new ScenarioStep(tsEND, "Exit",
        "You have exited the game.\n" +
                "Thank you for playing.",
            "Riverside",
        new String[] { "Jungle", "River" },
        new String[] { "Branches", "Ancient_treasure",
                "Ancient_altar"},
        new String[] { "Shovel","Flashlight", "Lighter", "Machete", "Treasure_map"}
    )
);

    public static List<Scenario> scenarios()
    {
        return List.of(HAPPY,BASIC,MISTAKES,MISTAKES_NS);
    }

    public Scenarios()
    {
    }
}
