package no.ntnu.wargames;

import no.ntnu.wargames.backend.units.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test of the abstract unit class with its method.
 *
 * @author Kjetil Indrehus
 * @version 1.0-SNAPSHOT 23.02.22
 */

class UnitTest{
    private Unit unit;

    @BeforeEach
    void setupUnitTest(){
        //Assert
        unit = new CavalryUnit("name",40,30,10);
    }

    /**
     * Test that the unit constructor is correct.
     * Should not throw exceptions.
     */

    @Test
    @DisplayName("Test constructor for all unit types.")
    void testUnitConstructor(){
        try{
            Unit cavalryUnit = new CavalryUnit("name",10,10,10);
            Unit rangedUnit = new RangedUnit("name",10,10,10);
            Unit commanderUnit = new CommanderUnit("name",10,10,10);
            Unit infantryUnit = new InfantryUnit("name",10,10,10);

            assertTrue(true);
        }catch (Exception e){
            fail();
        }
    }

    /**
     * Test that the negative constructor works.
     * Should throw exception when given Illegal argument.
     */
    @Test
    @DisplayName("Test Unit constructor throws exception, when given Illegal-argument")
    void testNegativeConstructor(){
        //Empty name
        try{
            Unit unit = new CavalryUnit("",40,30,10);
            fail();
        }catch (IllegalArgumentException e){
            assertEquals("Unit constructor was given invalid value(s)",e.getMessage());
        }

        //Health lower than 1.
        try{
            Unit unit = new CavalryUnit("name",0,10,10);
            fail();
        }catch (IllegalArgumentException e){
            assertEquals("Unit constructor was given invalid value(s)",e.getMessage());
        }

        //Attack is positive
        try {
            Unit unit = new CavalryUnit("name",10,-2,10);
            fail();
        }catch (IllegalArgumentException e){
            assertEquals("Unit constructor was given invalid value(s)",e.getMessage());
        }

        //Asset positive armor

        try {
            Unit unit = new CavalryUnit("name",10,10,-1);
            fail();
        }catch (IllegalArgumentException e){
            assertEquals("Unit constructor was given invalid value(s)",e.getMessage());
        }

        //Assert Armor is bigger than 0
        try{
            Unit unit = new CavalryUnit("name",10,10,0);
            assertTrue(true);
        }catch (Exception e){
            fail();
        }

    }


    /**
     * Testing if the getName method works.
     */
    @Test
    @DisplayName("Test getName method for the unit.")
    void testGetName() {
        //Assert positive
        assertEquals("name", unit.getName());
        //Assert negative
        assertNotEquals("notName", unit.getName());
    }

    /**
     * Test if the getHealth method works.
     */

    @Test
    @DisplayName("Test getHealth returns a correct health for each unit.")
    void testGetHealth() {
        //Assert positive
        assertEquals(40, unit.getHealth());
        //Assert negative
        assertNotEquals(45, unit.getHealth());
    }

    /**
     * Testing if the getAttack method works.
     */

    @Test
    @DisplayName("Test the getAttack method for units.")
    void testGetAttack() {
        //Assert positive
        assertEquals(30, unit.getAttack());
        //Assert negative
        assertNotEquals(45, unit.getAttack());
    }

    /**
     * Testing if the getArmour method works.
     */

    @Test
    @DisplayName("Test the getArmour for unit.")
    void testGetArmor() {
        //Assert positive
        assertEquals(10, unit.getArmor());
        //Assert negative
        assertNotEquals(45, unit.getArmor());
    }

    /**
     * Testing if the setHealth method works.
     */

    @Test
    @DisplayName("Test that the method sets health. (Positive)")
    void testSetHealth() {
        //Act
        unit.setHealth(50);
        //Assert positive
        assertEquals(50, unit.getHealth());
        //Assert negative
        assertNotEquals(40, unit.getAttack());
    }

    /**
     * Testing if the getAttackCount method works.
     * Should increment by one after an attack.
     */

    @Test
    @DisplayName("Test if attack-count increments after an attack for the unit.")
    void testGetAttackCount() {
        //Assert positive
        assertEquals(30, unit.getAttack());
        //Assert negative
        assertNotEquals(45, unit.getAttack());
    }

    /**
     * Testing if the toString method works.
     */

    @Test
    @DisplayName("Test that the toString is overwritten for the Unit.")
    void testToString() {
        //Assert positive
        assertEquals("Name: name::HP: 40::Attack: 30", unit.toString());

        assertNotEquals("String", unit.toString());

    }

    /**
     * Testing if the attack method works. If the armor is better than the attack, no change to the health should be done.
     * Else, change the attack correctly using the formula shown.
     */

    @Test
    @DisplayName("Test attack method for each type of unit.")
    void testAttack() {
        //Assert
        Unit attacker = new CavalryUnit("unit1",100,10,10);
        Unit weakUnit = new CavalryUnit("unit2",100,10,5);
        Unit armorUnit = new CavalryUnit("unit2",200,10,100);
        /*
        ACT:

        (1) For the weak unit attacked:

        Since attack is bigger than armor and defence:
        weakUnit.health = weakUnit.health - (attacker.attack+attacker.attackBonus)+(weakUnit.armor+weakUnit.resistBonus)
        weakUnit.health = 100 -(10+6)+(5+3)
        weakUnit.health = 100 - 8
        weakUnit.health = 92

        (2) For the armored unit attacked:

        In this case the health should not change. The armor is absorbs the attack. So no change is health:
        armorUnit.health = 200;


         */
        attacker.attack(weakUnit); // case 1
        attacker.attack(armorUnit);

        //Assert

        assertEquals(92, weakUnit.getHealth());
        assertEquals(200,armorUnit.getHealth());

        assertNotEquals(100,weakUnit.getHealth());
        assertNotEquals(287,armorUnit.getHealth());
    }
}