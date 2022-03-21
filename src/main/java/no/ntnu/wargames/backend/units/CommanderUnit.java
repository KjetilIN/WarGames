package no.ntnu.wargames.backend.units;

/**
 * The commander unit that extends the CavalryUnit.
 *
 * @author Kjetil Indrehus
 * @version 1.0-SNAPSHOT 07.02.22
 */
public class CommanderUnit extends CavalryUnit {


    /**
     * This is the commander unit constructor with all the fields.
     *
     * @param name name of the commander unit.
     * @param health health of the commander unit.
     * @param attack attack of the commander unit.
     * @param armor armor of the commander unit.
     */

    public CommanderUnit(String name, int health, int attack, int armor){
        super(name, health, attack, armor);
    }

    private String unitType = "Commander";

    /**
     * Constructor for the commander unit.
     *
     * @param name name of the commander.
     * @param health health of the commander.
     */
    public CommanderUnit(String name, int health){
        super(name, health);
    }

    @Override
    public String getUnitType(){
        return this.unitType;
    }

    

}
