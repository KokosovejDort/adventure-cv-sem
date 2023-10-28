package adv23s._3_1615.dudt05_dudzich.logic;

import adv23s._3_1615.dudt05_dudzich.api.INamed;

public abstract class Named implements INamed {
    private final String name;

    public Named(String name) {
        if ((name == null) || name.isEmpty()) {
            throw new IllegalArgumentException(
                    "You can't use empty string" +
                    "as the name of the object"
            );
        }
         if ((! name.equals(name.trim())  )  ||
                   (name.split("\\s").length > 1)  ){
            throw new IllegalArgumentException(
                    "You can use only one word as" +
                    " a name of the object"
            );
        }
        this.name = name;
    }

    /***************************************************************************
     * Vrátí název dané instance.
     *
     * @return Název instance
     */
    @Override
    public String name() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
