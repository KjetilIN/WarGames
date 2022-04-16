package no.ntnu.wargames.backend.designPattern;

import no.ntnu.wargames.backend.Battle;
import no.ntnu.wargames.backend.units.Army;


/**
 * Facade class that is used to send information,between different windows.
 * The Facade class can only hold one instance of itself.
 * It contains the Battle class
 *
 * For more information on the design pattern:
 * @see <a href="https://refactoring.guru/design-patterns/facade">Facade Design Pattern Theory</a>
 */
public class Facade {

    private static volatile Facade instance;
    private Battle battle;
    private Army armyOne;
    private Army armyTwo;

    private Facade(){
        armyOne = new Army("NONE");
        armyTwo = new Army("NONE");
        battle = new Battle(armyOne,armyTwo);

    }

    /**
     * Method that gets the instance of the facade class.
     * If the facade has not been called before, it creates a new instance.
     *
     * @return returns the instance of the facade class.
     */
    public static Facade getInstance() {
        if(instance == null){
            synchronized (Facade.class){
                instance = new Facade();
            }
        }
        return instance;
    }

    public void setBattle(Battle battle) {
        this.battle = battle;
    }

    public Battle getBattle() {
        return battle;
    }

    public Army getArmyOne() {
        return this.armyOne;
    }

    public Army getArmyTwo() {
        return this.armyTwo;
    }

    public void setArmyOne(Army armyOne) {
        this.armyOne = armyOne;
    }

    public void setArmyTwo(Army armyTwo) {
        this.armyTwo = armyTwo;
    }

    /**
     * Method reset both army and battle class.
     */
    public void resetAll(){
        setArmyOne(new Army("NONE"));
        setArmyTwo(new Army("NONE"));
        setBattle(new Battle(armyOne,armyTwo));
    }

}
