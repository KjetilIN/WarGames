package no.ntnu.wargames;

import no.ntnu.wargames.backend.units.CavalryUnit;
import no.ntnu.wargames.backend.units.Unit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The test class for the cavalryUnit.
 *
 * @author Kjetil Indrehus
 * @version 1.0-SNAPSHOT 23.02.22
 */

class CavalryUnitTest {

    /**
     * Method to test the constructor with only two parameters.
     * Attack and armor should be assigned static values.
     */


    @Test
    @DisplayName("Test that the default Cavalry attack value is correct.")
    void testDefaultAttackArmor(){
        /*
        ATTACK = 20
        ARMOR = 12
         */

        //Arrange
        Unit unit = new CavalryUnit("name",10);
        //Assert
        assertEquals(20,unit.getAttack());
        assertEquals(12,unit.getArmor());

        assertNotEquals(10, unit.getAttack());
        assertNotEquals(10,unit.getArmor());
    }

    /**
     * Testing the getAttackBonus works. This is an abstract method to be overwritten.
     * The attack bonus should change after first attack.
     */

    @Test
    @DisplayName("Test cavalry attack bonus changes for each attack.")
    void testGetAttackBonus() {
        //Arrange
        Unit unit = new CavalryUnit("name",10);
        Unit unit2 = new CavalryUnit("name2",10);
        //Assert
        assertEquals(6,unit.getAttackBonus());
        assertNotEquals(0, unit.getAttackBonus());

        //Act - We attack the unit once so the attack-bonus should be 4 not 6.
        unit2.attack(unit);

        //Assert
        assertEquals(1, unit.getAttackCount());
        assertEquals(4,unit.getAttackBonus());

        assertNotEquals(0,unit.getAttackCount());
        assertNotEquals(6,unit.getAttackBonus());

    }

    /**
     * Testing the getResistBonus works. This is an abstract method to be overwritten.
     * The resist bonus should not change after an attack.
     */

    @Test
    @DisplayName("Test get resist bonus for cavalry change after attack.")
    void testGetResistBonus() {
        //Arrange
        Unit unit = new CavalryUnit("name",10);
        Unit unit2 = new CavalryUnit("name2",10);
        //Assert
        assertEquals(3,unit.getResistBonus());
        assertNotEquals(0, unit.getResistBonus());

        //Act - We attack the unit once but resistance bonus should not change
        unit2.attack(unit);

        //Assert
        assertEquals(1, unit.getAttackCount());
        assertEquals(3,unit.getResistBonus());

        assertNotEquals(0,unit.getAttackCount());
        assertNotEquals(6,unit.getResistBonus());
    }

    @Test
    @DisplayName("Test terrain bonus for CavalryUnit ")
    void testTerrainBonusCavalry(){
        //Arrange
        Unit bonusUnit = new CavalryUnit("Cavalry1",10);
        Unit looseAllDefenceUnit = new CavalryUnit("Cavalry1",10);
        Unit noBonusUnit1 = new CavalryUnit("Temp",10);
        Unit noBonusUnit2 = new CavalryUnit("Temp",10);

        //Act
        bonusUnit.setTerrain("Plains");
        looseAllDefenceUnit.setTerrain("Forest");
        noBonusUnit1.setTerrain("Hill");
        noBonusUnit1.setTerrain("Unknown");



        //Assert

        /*Check both defence and attack values are correct if terrain is plains */
        assertEquals(3,bonusUnit.getTerrainBonusAttackDefence().getKey());
        assertEquals(0,bonusUnit.getTerrainBonusAttackDefence().getValue());

        assertEquals(6+3,bonusUnit.getAttackBonus());
        assertEquals(3,bonusUnit.getResistBonus());


        /*Check both defence and attack for Forest. Should trow exception */
        try{
            int bonus = looseAllDefenceUnit.getTerrainBonusAttackDefence().getKey();
            fail();
        }catch (IllegalArgumentException e){
            assertEquals("defence",e.getMessage());
        }

        assertEquals(6,looseAllDefenceUnit.getAttackBonus());
        assertEquals(0,looseAllDefenceUnit.getResistBonus());

        /*Check if other Terrains gives bonus (They should not)*/
        //Hill as Terrain type
        assertEquals(0,noBonusUnit1.getTerrainBonusAttackDefence().getValue());
        assertEquals(0,noBonusUnit1.getTerrainBonusAttackDefence().getKey());
        //Unknown Terrain type
        assertEquals(0,noBonusUnit2.getTerrainBonusAttackDefence().getValue());
        assertEquals(0,noBonusUnit2.getTerrainBonusAttackDefence().getKey());

        assertEquals(6,noBonusUnit1.getAttackBonus());
        assertEquals(6,noBonusUnit2.getAttackBonus());
        assertEquals(3,noBonusUnit1.getResistBonus());
        assertEquals(3,noBonusUnit2.getResistBonus());

    }

}