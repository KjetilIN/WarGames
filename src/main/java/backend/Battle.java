package backend;

import backend.units.Army;
import backend.units.Unit;

import java.util.Random;

/**
 * The simulation class for the battle.
 * Takes to army and has the simulation class.
 *
 * @author Kjetil Indrehus
 * @version 1.0-SNAPSHOT 16.02.2022
 */

public class Battle {
    private final Army armyOne;
    private final Army armyTwo;
    private Random random;

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
        //Battles until one army is empty
        random = new Random();
        while(armyOne.hasUnits() && armyTwo.hasUnits()){
            //Pick two random units from each
            Unit unitFromArmyOne = armyOne.getRandomUnit();
            Unit unitFromArmyTwo = armyTwo.getRandomUnit();


            int chooseAttacker = random.nextInt(2);
            if(chooseAttacker == 0){
                unitFromArmyOne.attack(unitFromArmyTwo);
                if(!unitFromArmyTwo.isAlive()){
                    armyTwo.remove(unitFromArmyTwo);
                }
            }else{
                unitFromArmyTwo.attack(unitFromArmyOne);
                if(!unitFromArmyOne.isAlive()){
                    armyOne.remove(unitFromArmyOne);
                }
            }
        }

        //Returns the winning army
        if(armyOne.hasUnits()){
            return armyOne;
        }
        return armyTwo;

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
