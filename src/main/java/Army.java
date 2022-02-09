import java.util.ArrayList;
import java.util.List;

/**
 * The Army class that holds all the units for a single class.
 * This class has all the methods for the list of units.
 *
 * @author Kjetil Indrehus
 * @version 1.0-SNAPSHOT 09.02.2022
 */

public class Army {
    private String name;
    private List<Unit> units;

    /**
     * Constructor for the army.
     * This constructor takes only a name of the army.
     * Initialise the list as ArrayList.
     *
     * @param name name of the army.
     */
    public Army(String name){
        this.name = name;
        units = new ArrayList<>();
    }

    /**
     * Constructor for the army class with two parameters.
     *
     * @param name name of the unit
     * @param units a list of units that is the total amount of units.
     */
    public Army(String name, List<Unit> units){
        this.name = name;
        this.units = units;
    }

    /**
     *
     * @return
     */
    public String getName() {
        return this.name;
    }
}
