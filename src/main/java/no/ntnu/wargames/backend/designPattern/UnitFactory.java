package no.ntnu.wargames.backend.designPattern;

import no.ntnu.wargames.backend.units.*;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the unit factory class.
 * This class uses the "factory" design pattern.
 *
 * @author Kjetil Indrehus
 * @version 1.0-SNAPSHOT
 */

public class UnitFactory {

    /*Unit types*/
    private static final List<String> UNIT_TYPES = List.of("Ranged","Infantry","Commander","Cavalry");

    /**
     * Private constructor that is never used.
     * This class contains purely static methods.
     * Should never be used.
     * @throws IllegalArgumentException Throws exception if used.
     */
    private UnitFactory(){
        throw new IllegalArgumentException("UnitFactory");
    }

    /**
     * Factory method that returns a new unit.
     *
     * @param unitType unit type as string
     * @param unitName unit name
     * @param unitHealth health as integer
     * @return returns a new unit based on the input, else return null
     */
    public static Unit createUnit(String unitType, String unitName, int unitHealth){
        if(unitHealth <= 0 || unitName.isEmpty()){return null;}
        return switch (unitType) {
            case "Ranged" -> new RangedUnit(unitName, unitHealth);
            case "Infantry" -> new InfantryUnit(unitName, unitHealth);
            case "Commander" -> new CommanderUnit(unitName, unitHealth);
            case "Cavalry" -> new CavalryUnit(unitName, unitHealth);
            default -> null;
        };
    }


    /**
     * Create x amount of units, and returns them in a list.
     * All the unit created have the same type,name and health
     *
     * @param amount the amount of units created
     * @param unitType the unit type of the units
     * @param unitName the name of each unit
     * @param unitHealth the health of each unit
     * @return returns a list of units
     * @throws IllegalArgumentException throws exception if a given argument is wrong. Check the exception message.
     */

    public static List<Unit> createListOfUnit(int amount, String unitType,String unitName, int unitHealth) throws IllegalArgumentException{
        List<Unit> unitList = new ArrayList<>();

        /*Check if the given parameters are correct, then throws correct exception*/

        if(!(UNIT_TYPES.contains(unitType))){throw new IllegalArgumentException("Unknown unit type given");}
        if(amount <= 0){throw new IllegalArgumentException("Amount was negative");}
        if(unitHealth <= 0){throw new IllegalArgumentException("Unit health was negative");}
        if(unitName.isEmpty()){throw new IllegalArgumentException("No name given");}

        /* Add x amount of same type of units to the return list*/
        for(int i = 0; i<amount; i++){
            unitList.add(createUnit(unitType,unitName,unitHealth));
        }
        return unitList;
    }
}
