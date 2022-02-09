/**
 * This class represents a unit.
 * @author Kjetil Indrehus
 * @version 1.0-SNAPSHOT 07.02.22
 */
public abstract class Unit {
    private  String name;
    private int health;
    private int attack;
    private int armor;
    private int attackCount;

    /**
     * This is a constructor for the Unit class.
     *
     * @param name The name of the unit
     * @param health the health of the unit
     * @param attack the attack of the unit
     * @param armor the armor of a unit
     */
    protected Unit(String name, int health, int attack, int armor) {
        this.name = name;
        this.health = health;
        this.attack = attack;
        this.armor = armor;
        this.attackCount = 0;
    }

    public String getName(){
        return name;
    }

    public int getHealth() {
        return health;
    }

    public int getAttack() {
        return attack;
    }

    public int getArmor() {
        return armor;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * An abstract method for getting the attack bonus.
     * For each type of unit, you get different attack bonus.
     *
     * @return returns the given attack bonus for the unit
     */
    public abstract int getAttackBonus();

    /**
     * An abstract method for getting the resist bonus.
     * For each unit, there is different resist bonus.
     *
     * @return returns the given resist bonus for the unit
     */
    public abstract int getResistBonus();

    /**
     * A method that returns the attack count.
     *
     * @return returns the amount of attacks the unit has experienced.
     */
    public int getAttackCount(){
        return this.attackCount;
    }



    /**
     * This method return basic information about a unit in a string.
     * @return returns a string of information
     */
    @Override
    public String toString(){
        return "Name: "+name + "::HP: "+health +"::Attack: "+attack;
    }


    public void attack(Unit unit){
        if((this.attack + getAttackBonus())-(this.armor+getResistBonus()) < 0){ // attack shold
            try{
                unit.setHealth(unit.getHealth()-(this.attack + getAttackBonus())+(this.armor+getResistBonus()));
            }catch (Exception ignore){} // Note: This should be edited
        }
    }
}
