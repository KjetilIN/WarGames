package no.ntnu.wargames.backend;

import no.ntnu.wargames.backend.units.Army;
import no.ntnu.wargames.backend.units.Unit;
import java.util.Random;

/**
 * The simulation class for the battle.
 * Takes to army and has the simulation class.
 *
 * @author Kjetil Indrehus
 * @version 1.0-SNAPSHOT 18.02.2022
 */

public class Battle {

    private Army armyOne;
    private Army armyTwo;
    private Random random;

    /**
     * Constructor for the Battle class.
     * Takes two army as argument.
     *
     * @param armyOne the first army.
     * @param armyTwo the second army.
     */

    public Battle(Army armyOne, Army armyTwo){
        if(armyOne == null || armyTwo == null){
            throw new IllegalArgumentException("Army(s) given are empty.");
        }
        this.armyOne = armyOne;
        this.armyTwo = armyTwo;
        this.random = new Random();

    }

    /**
     * The method to simulate an attack and get information back.
     * Uses attacker and defender are chosen in the {@link class#simulateStep()} method.
     *
     * @param defendUnit the unit that defends.
     * @param attackUnit the unit that attacks.
     * @param attackerArmy army object of the attacker unit.
     * @param defenderArmy army object of the defender unit.
     * @return returns a string of information from the attack.
     */

    private String simulateAttack(Unit defendUnit, Unit attackUnit, Army attackerArmy, Army defenderArmy) {
        String res = "";
        attackUnit.attack(defendUnit);
        res += attackerArmy.getName()+ "'s army attacked with "+attackUnit.getName() +" !";
        if(!defendUnit.isAlive()){
            defenderArmy.remove(defendUnit);
            res +=" The attack killed " + defendUnit.getName()+"!";
        }else{
            res +=" The attack left the unit " + defendUnit.getName() +" with "+ defendUnit.getHealth() +" HP!";
        }
        return res;
    }



    /**
     * Set the terrain for all the unit in the battle.
     * Goes through all the units.
     *
     * @param terrain of the units.
     */
    public void setTerrainForALL(String terrain){
        for(Unit unit: this.armyOne.getAllUnits()){
            unit.setTerrain(terrain);
        }
        for (Unit unit: this.armyTwo.getAllUnits()){
            unit.setTerrain(terrain);
        }
    }

    /**
     * Simulates one attack.
     * Chooses a side that attack.
     * Uses the {@link class#simulateAttack(Unit, Unit, Army, Army)} method to get information from the attack.
     *
     * @return returns a string of information
     */
    public String simulateStep(){
        String res; //String to be given

        //Pick two random units from each
        Unit unitFromArmyOne = armyOne.getRandomUnit();
        Unit unitFromArmyTwo = armyTwo.getRandomUnit();


        int chooseAttacker = random.nextInt(2);
        if(chooseAttacker == 0){
            res = simulateAttack(unitFromArmyTwo, unitFromArmyOne, armyOne, armyTwo);
        }else{
            res = simulateAttack(unitFromArmyOne, unitFromArmyTwo, armyTwo, armyOne);
        }
        return res;
    }

    /**
     * The class that simulates the battle and return a winner from the battle.
     *
     * @return return the winning army.
     */
    public Army simulateBattle(){
        //Battles until one army is empty
        random = new Random();
        while(armyOne.hasUnits() && armyTwo.hasUnits()){
            simulateStep();
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
