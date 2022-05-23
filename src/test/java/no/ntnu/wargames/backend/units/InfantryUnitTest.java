package no.ntnu.wargames.backend.units;

import no.ntnu.wargames.backend.units.InfantryUnit;
import no.ntnu.wargames.backend.units.Unit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Infantry unit test class.
 *
 * @author Kjetil Indrehus
 * @version 1.0-SNAPSHOT 16.02.2022
 *
 */

class InfantryUnitTest {
    /**
     * Method to test the constructor with only two parameters.
     * Attack and armor should be assigned static values.
     */

    @Test
    @DisplayName("Test default attack value for infantry, is correct.")
    void testDefaultAttackArmor(){
        /*
        ATTACK = 12
        ARMOR = 10
         */

        //Arrange
        Unit unit = new InfantryUnit("name",10);
        //Assert
        assertEquals(12,unit.getAttack());
        assertEquals(10,unit.getArmor());

        assertNotEquals(5, unit.getAttack());
        assertNotEquals(5,unit.getArmor());
    }

    /**
     * Method to test the getAttackBonus for the infantry unit.
     * After an attack, the attack bonus does not change.
     *
     */

    @Test
    @DisplayName("Test that the attack bonus changes for the infantry after attack.")
    void testGetAttackBonus() {
        //Arrange
        Unit unit = new InfantryUnit("name",10);
        Unit unit2 = new InfantryUnit("name2",10);
        //Assert
        assertEquals(3,unit.getAttackBonus());
        assertNotEquals(0, unit.getAttackBonus());

        //Act - We attack the unit once, but the bonus should not change
        unit2.attack(unit);

        //Assert
        assertEquals(1, unit.getAttackCount());
        assertEquals(3,unit.getAttackBonus());

        assertNotEquals(0,unit.getAttackCount());
        assertNotEquals(6,unit.getAttackBonus());

    }

    /**
     * Method to test the getResistBonus for the infantry unit.
     * The resist bonus should change.
     */

    @Test
    @DisplayName("Test that infantry resist bonus changes after attacked.")
    void testGetResistBonus() {
        //Arrange
        Unit unit = new InfantryUnit("name",10);
        Unit unit2 = new InfantryUnit("name2",10);
        //Assert
        assertEquals(1,unit.getResistBonus());
        assertNotEquals(0, unit.getResistBonus());

        //Act - We attack the unit once, and it should bonus should now change.
        unit2.attack(unit);

        //Assert
        assertEquals(1, unit.getAttackCount());
        assertEquals(1,unit.getResistBonus());

        assertNotEquals(0,unit.getAttackCount());
        assertNotEquals(11,unit.getResistBonus());


    }

    @Test
    @DisplayName("Test Terrain Bonus for the Infantry Unit")
    void testTerrainBonus(){
        //Arrange
        Unit noBonusUnit = new InfantryUnit("Temp1",29);
        Unit bonusUnit = new InfantryUnit("Temp2",29);

        //Act
        noBonusUnit.setTerrain("hill");
        bonusUnit.setTerrain("Forest");

        //Assert

        /*Assert Bonus when Terrain is of type Forest */
        assertEquals(3,bonusUnit.getTerrainBonusAttackDefence().getValue());
        assertEquals(3,bonusUnit.getTerrainBonusAttackDefence().getKey());

        assertEquals(3+3,bonusUnit.getAttackBonus());
        assertEquals(1+3,bonusUnit.getResistBonus());

        /*Assert no bonus added else*/
        assertEquals(0,noBonusUnit.getTerrainBonusAttackDefence().getValue());
        assertEquals(0,noBonusUnit.getTerrainBonusAttackDefence().getKey());

        assertEquals(3,noBonusUnit.getAttackBonus());
        assertEquals(1,noBonusUnit.getResistBonus());



    }
}