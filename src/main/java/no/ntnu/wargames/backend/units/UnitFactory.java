package no.ntnu.wargames.backend.units;

public class UnitFactory {

    public Unit createUnit(String unitType,String unitName, int unitHealth){
        return switch (unitType) {
            case "Ranged" -> new RangedUnit(unitName, unitHealth);
            case "Infantry" -> new InfantryUnit(unitName, unitHealth);
            case "Commander" -> new CommanderUnit(unitName, unitHealth);
            case "Cavalry" -> new CavalryUnit(unitName, unitHealth);
            default -> null;
        };
    }
}
