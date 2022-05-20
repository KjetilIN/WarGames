package no.ntnu.wargames.backend.units;

import javafx.util.Pair;

/**
 * The cavalry unit class.
 * Extends the unit class.
 *
 * @author Kjetil Indrehus
 * @version 1.0-SNAPSHOT
 */

public class CavalryUnit extends Unit {

    /**
     * The static final fields. These are constant to the class.
     */
    private static final int ATTACK = 20;
    private static final int ARMOR = 12;
    private static final String UNIT_TYPE = "Cavalry";

    /**
     * Cavalry Unit with all fields available for change.
     *
     * @param name   name of the cavalry unit.
     * @param health health of the cavalry unit.
     * @param attack attack of the cavalry unit.
     * @param armor  armor of the cavalry unit.
     */
    public CavalryUnit(String name, int health, int attack, int armor) {
        super(name, health, attack, armor);
    }

    /**
     * Constructor for the cavalry unit.
     * The attack and armor with this constructor, are constant.
     *
     * @param name   name of the calvary unit.
     * @param health health of the calvary unit.
     */

    public CavalryUnit(String name, int health) {
        super(name, health, ATTACK, ARMOR);
    }

    /**
     * The attack bonus for the cavalry unit.
     * For the first attack, will the cavalry return a high attack value.
     * After the first attack the cavalry will be weaker, and therefore return a lower value.
     *
     * @return returns the attack bonus for the cavalry unit.
     */

    @Override
    public int getAttackBonus() {
        int attackBonus = getAttackCount() == 0 ? 6 : 4;
        try{
            attackBonus += getTerrainBonusAttackDefence().getKey();
        }catch (IllegalArgumentException ignored){ /* Ignored */ }

        return attackBonus;
    }

    /**
     * The resist bonus for the cavalry.
     * The cavalry has an advantage in numbers, and will therefore have a high calvary bonus.
     *
     * @return returns the resist bonus for the cavalry.
     */

    @Override
    public int getResistBonus() {
        int resistBonus = 3;
        try{
            resistBonus+= getTerrainBonusAttackDefence().getValue();
        }catch (IllegalArgumentException e){
            return 0;
        }

        return resistBonus;
    }

    @Override
    public String getUnitType() {
        return UNIT_TYPE;
    }


    /**
     * The terrain bonus method for cavalry unit.
     *
     * The cavalry unit is good in plains terrain. Therefore, it receives an attack bonus of 3.
     * However, in a forest the unit struggle to defend itself, and looses all defence bonus.
     * For the other terrains, no bonus is added.
     *
     * @return returns a pair object of attack and defence bonus.
     * @throws IllegalArgumentException throws exception if cavalry unit looses all defence bonus.
     */

    @Override
    public Pair<Integer,Integer> getTerrainBonusAttackDefence() throws IllegalArgumentException{
        int attack = 0;
        if(getTerrain().equalsIgnoreCase("Plains")){
            attack = 3;
        }else if(getTerrain().equalsIgnoreCase("Forest")){
            /*Unit looses all defence bonus*/
            throw new IllegalArgumentException("defence");
        }
        return new Pair<>(attack,0);
    }
}