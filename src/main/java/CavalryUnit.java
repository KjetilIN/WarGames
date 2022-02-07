/**
 * The cavalry unit class.
 * Extends the unit class.
 *
 * @author Kjetil Indrehus
 * @version 1.0-SNAPSHOT 07.02.22
 */

public class CavalryUnit extends Unit {

    // TODO: 07.02.2022 Ask Ivar if the attack and armor should be static final(?).

    /**
     * Constructor for the cavalry unit.
     *
     * @param name name of the calvary unit.
     * @param health health of the calvary unit.
     */

    protected CavalryUnit(String name, int health){
        super(name,health,20,12);
    }

    /**
     * The attack bonus for the cavalry unit.
     * For the first attack, will the cavalry return a high attack value.
     * After the first attack the cavalry will be weaker, and therefor return a lower value.
     *
     * @return returns the attack bonus for the cavalry unit.
     */

    @Override
    public int getAttackBonus() {
        return getAttackCount() == 0 ? 6 : 4;
    }

    /**
     * The resist bonus for the cavalry.
     * The cavalry has an advantage in numbers, and will therefor have a high calvary bonus.
     *
     * @return returns the resist bonus for the cavalry.
     */

    @Override
    public int getResistBonus() {
        return 3;
    }
}
