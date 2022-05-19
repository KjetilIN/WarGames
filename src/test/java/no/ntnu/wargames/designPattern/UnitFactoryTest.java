package no.ntnu.wargames.designPattern;

import no.ntnu.wargames.backend.units.Unit;
import no.ntnu.wargames.backend.designPattern.UnitFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class UnitFactoryTest {

    @Test
    @DisplayName("Test creating multiple units for the Factory class")
    void testGenrateList(){
        //Arrange
        List<Unit> units;

        //Act
        units = UnitFactory.createListOfUnit(30,"Ranged","Cool",20);

        //Assert

        assertEquals(30,units.size());
        assertEquals("Ranged",units.get(0).getUnitType());
        assertEquals("Cool",units.get(0).getName());
    }
}
