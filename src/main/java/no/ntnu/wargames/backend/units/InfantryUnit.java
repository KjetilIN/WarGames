package no.ntnu.wargames.backend.units;

import javafx.util.Pair;

/**
 * Class to represent the infantry unit. Inference from the unit class.
 * @author Kjetil Indrehus
 * @version 1.0-SNAPSHOT 07.02.22
 */
public class InfantryUnit extends Unit {
    /**
     * The static final fields. These are constant to the class.
     */
    private static final int ATTACK = 12;
    private static final int ARMOR = 10;
    private String unitType = "Infantry";

    /**
     * This is the infantry unit constructor all fields.
     *
     * @param name name of the infantry unit.
     * @param health health of the infantry unit.
     * @param attack attack of the infantry unit.
     * @param armor armor of the infantry unit.
     */
    public InfantryUnit(String name, int health, int attack, int armor){
        super(name, health, attack, armor);
    }


    /**
     * Constructor for the infantry unit. Uses "super" to inference methods.
     *
     * @param name name of the infantry
     * @param health health of the infantry
     */
    public InfantryUnit(String name, int health){
        super(name,health,ATTACK,ARMOR);
    }

    /**
     * The attack bonus for the infantry.
     * It is high, because infantry has good attack up close.
     *
     * @return returns the attack bonus.
     */
    @Override
    public int getAttackBonus() {
        return 3 + getTerrainBonusAttackDefence().getKey();
    }

    /**
     * The resistant bonus for the infantry class.
     * It is low, because infantry is vulnerable up close.
     *
     * @return return the resist bonus.
     */
    @Override
    public int getResistBonus() {
        return 1 + getTerrainBonusAttackDefence().getValue();
    }

    @Override
    public String getUnitType() {
        return this.unitType;
    }

    /**
     * The terrain bonus method for infantry unit.
     *
     * The infantry unit has a huge advantage in a forest.
     * Therefore, it receives both an extra attack and defence bonus.
     *
     * @return returns a pair object of attack and defence bonus.
     */

    @Override
    public Pair<Integer,Integer> getTerrainBonusAttackDefence(){
        int attack = 0;
        int defence = 0;
        if(getTerrain().equalsIgnoreCase("Forest")){
            attack = 3;
            defence = 3;
        }
        return new Pair<>(attack,defence);
    }

}
