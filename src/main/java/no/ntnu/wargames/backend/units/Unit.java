package no.ntnu.wargames.backend.units;

/**
 * This class represents a unit.
 * @author Kjetil Indrehus
 * @version 1.0-SNAPSHOT 15.02.22
 */
public abstract class Unit {
    private final String name;
    private int health;
    private final int attack;
    private final int armor;
    private int attackCount;
    private boolean isAlive;

    /**
     * This is a constructor for the Unit class.
     * Trows an illegal argument exception if on of the values is lower than zero.
     *
     * @param name The name of the unit
     * @param health the health of the unit
     * @param attack the attack of the unit
     * @param armor the armor of a unit
     */
    protected Unit(String name, int health, int attack, int armor) {
        if(name.length()>0 && health>0 && attack > 0 && armor >=0){
            this.name = name;
            setHealth(health);
            this.attack = attack;
            this.armor = armor;
            this.attackCount = 0;
            this.isAlive = true;
        }else{
            throw new IllegalArgumentException("Unit constructor was given invalid value(s)");
        }

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

    /**
     * Boolean value that check if the hero is alive.
     *
     * @return return true if the person is alive.
     */
    public boolean isAlive(){
        return isAlive;
    }

    /**
     * Set the status of the unit.
     *
     * @param alive a boolean value. True if the unit is alive.
     */
    public void setAlive(boolean alive) {
        this.isAlive = alive;
    }

    /**
     * Method that sets the health of the unit given.
     * Throws exception if the given input is bellow zero
     *
     * @param health the new health of the unit.
     */
    public void setHealth(int health) {
        if(health <=0){
            //In case of death
            this.health = 0;
            this.isAlive = false;

        }else{
            try {
                this.health = health;
            }catch (Exception e){
                System.err.println("FAILURE(setHealth): "+e.getMessage());
            }
        }


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
     * Abstract method to get the unit type.
     * @return returns the unit type as string
     */
    public abstract String getUnitType();

    /**
     * This method return basic information about a unit in a string.
     * @return returns a string of information
     */
    @Override
    public String toString(){
        return "Name: "+name + "::HP: "+health +"::Attack: "+attack;
    }

    /**
     * Method that does an attack on a unit.
     * Changes the unit health based on bonus's and the given attack.
     * @param unit the unit that are attacked.
     */

    public void attack(Unit unit){
        if((this.attack + this.getAttackBonus()) > (unit.armor+unit.getResistBonus())){ // attack only if the damage is higher than the armor.
            unit.setHealth(unit.getHealth()-(this.attack + this.getAttackBonus())+(unit.getArmor()+unit.getResistBonus()));
            unit.attackCount += 1; //Attacking count is upped by one.
        }
    }
}
