package no.ntnu.wargames.backend.units;

/**
 * This is the unit factory class.
 * This class uses the "factory" design pattern.
 *
 * @author Kjetil Indrehus
 * @version 1.0-SNAPSHOT 31.03.22
 */

public class UnitFactory {

    /**
     * Factory method that returns a new unit.
     *
     * @param unitType unit type as string
     * @param unitName unit name
     * @param unitHealth health as integer
     * @return returns a new unit based on the input, else return null
     */
    public Unit createUnit(String unitType,String unitName, int unitHealth){
        if(unitHealth <= 0 || unitName.isEmpty()){return null;}
        return switch (unitType) {
            case "Ranged" -> new RangedUnit(unitName, unitHealth);
            case "Infantry" -> new InfantryUnit(unitName, unitHealth);
            case "Commander" -> new CommanderUnit(unitName, unitHealth);
            case "Cavalry" -> new CavalryUnit(unitName, unitHealth);
            default -> null;
        };
    }
}
