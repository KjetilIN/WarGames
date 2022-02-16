import units.Army;
/**
 * The simulation class for the battle.
 * Takes to army and has the simulation class.
 *
 * @author Kjetil Indrehus
 * @version 1.0-SNAPSHOT 09.02.2022
 */

public class Battle {
    private final Army armyOne;
    private final Army armyTwo;

    /**
     * Constructor for the Battle class.
     * Takes two army as argument.
     *
     * @param armyOne the first army.
     * @param armyTwo the second army.
     */

    public Battle(Army armyOne, Army armyTwo){
        if(armyOne == null || armyTwo == null || !armyOne.hasUnits() || !armyTwo.hasUnits()){
            throw new IllegalArgumentException("Army(s) given are empty.");
        }
        this.armyOne = armyOne;
        this.armyTwo = armyTwo;

    }

    /**
     * The class that simulates the battle.
     *
     * @return return the winning army.
     */
    public Army simulate(){
        while(armyOne.hasUnits() && armyTwo.hasUnits()){
            

        }

    }


    /**
     * The method that gives information of the battle between the two armies.
     *
     * @return returns a string of information for the battle.
     */
    @Override
    public String toString() {
        return "Battle{" +
                "armyOne=" + armyOne.toString() +
                ", armyTwo=" + armyTwo.toString() +
                '}';
    }
}
