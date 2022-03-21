package no.ntnu.wargames.backend.units;

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
        return 3;
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
            return 6;
        }
        return getAttackCount() == 1 ? 4 : 2; // else check if it is the second attack and then return 4, else returns 2.
    }
}
