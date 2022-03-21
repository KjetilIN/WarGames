package no.ntnu.wargames.backend.units;

/**
 * The cavalry unit class.
 * Extends the unit class.
 *
 * @author Kjetil Indrehus
 * @version 1.0-SNAPSHOT 07.02.22
 */

public class CavalryUnit extends Unit {

    /**
     * The static final fields. These are constant to the class.
     */
    private static final int ATTACK = 20;
    private static final int ARMOR = 12;

    /**
     * Cavalry Unit with all fields available for change.
     *
     * @param name name of the cavalry unit.
     * @param health health of the cavalry unit.
     * @param attack attack of the cavalry unit.
     * @param armor armor of the cavalry unit.
     */
    public CavalryUnit(String name, int health, int attack, int armor){
        super(name, health, attack, armor);
    }

    /**
     * Constructor for the cavalry unit.
     * The attack and armor with this constructor, are constant.
     *
     * @param name name of the calvary unit.
     * @param health health of the calvary unit.
     */

    public CavalryUnit(String name, int health){
        super(name,health,ATTACK,ARMOR);
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
        return getAttackCount() == 0 ? 6 : 4;
    }

    /**
     * The resist bonus for the cavalry.
     * The cavalry has an advantage in numbers, and will therefore have a high calvary bonus.
     *
     * @return returns the resist bonus for the cavalry.
     */

    @Override
    public int getResistBonus() {
        return 3;
    }
}
