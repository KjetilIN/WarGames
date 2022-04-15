package no.ntnu.wargames.backend.units;

import javafx.util.Pair;

/**
 * The ranged unit class. A sub-class of unit.
 *
 * @author Kjetil Indrehus
 * @version 1.0-SNAPSHOT 07.02.22
 */
public class RangedUnit extends Unit{

    /**
     * The static final fields. These are constant to the class.
     */
    private static final int ATTACK = 15;
    private static final int ARMOR = 8;
    private String unitType = "Ranged";

    /**
     * The ranged unit constructor with all of UNit fields as arguments.
     *
     * @param name name of the ranged unit.
     * @param health health of the ranged unit.
     * @param attack attack of the ranged unit.
     * @param armor armor of the ranged unit.
     */
    public RangedUnit(String name, int health, int attack, int armor){
        super(name, health, attack, armor);
    }

    /**
     * Constructor of the ranged unit.
     *
     * @param name name of the ranged unit.
     * @param health health of the ranged unit
     */
    public RangedUnit(String name, int health){
        super(name,health,ATTACK,ARMOR );
    }

    
    /**
     * The attack bonus for the ranged unit.
     *
     * @return returns an attack bonus for the ranged unit.
     */
    @Override
    public int getAttackBonus() {
        return 3 + getTerrainBonusAttackDefence().getKey();
    }

    /**
     * The resistance bonus for the ranged unit.
     * The ranged unit get a large resist bonus the first time, because they are far away.
     * The second time the ranged unit is attacked, it is closer to the enemy and therefore takes more damage.
     * For the third attack the enemy is close, so the resistance bonus is a constant value.
     *
     * @return return the resistance bonus for the ranged unit based on the amount of attack received .
     */

    @Override
    public int getResistBonus() {
        if(getAttackCount() == 0){ // Returns a high value for the first attack
            return 6 + getTerrainBonusAttackDefence().getValue();
        }

        // else check if it is the second attack and then return 4, else returns 2.
        return getAttackCount() == 1 ?
                4 + getTerrainBonusAttackDefence().getValue(): 2 + getTerrainBonusAttackDefence().getValue();
    }

    @Override
    public String getUnitType() {
        return this.unitType;
    }

    /**
     * The terrain bonus method for ranged unit.
     *
     * The ranged unit as advantage on Hill, and gets attack bonus.
     * In a forest, will the unit struggle, and therefore loose attack bonus.
     * No defence bonus is lost.
     *
     * @return returns a list of attack and defence bonus.
     */

    @Override
    public Pair<Integer,Integer> getTerrainBonusAttackDefence() {
        int attack;
        if (getTerrain().equalsIgnoreCase("Hill")) {
            attack = 3;
        }else if(getTerrain().equalsIgnoreCase("Forest")){
            attack = -2;
        }else{
            attack = 0;
        }
        return new Pair<>(attack,0);
    }
}
