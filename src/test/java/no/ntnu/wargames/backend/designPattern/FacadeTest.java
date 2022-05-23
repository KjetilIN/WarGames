package no.ntnu.wargames.backend.designPattern;

import no.ntnu.wargames.backend.designPattern.Facade;
import no.ntnu.wargames.backend.units.Army;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FacadeTest {

    @Test
    @DisplayName("Test Facade constructor")
    void testFacadeConstructor(){
        //Arrange
        Army army1 = Facade.getInstance().getArmyOne();
        Army army2 = Facade.getInstance().getArmyTwo();


        //Assert
        assertEquals("NONE",army1.getName());
        assertEquals(0,army1.getAllUnits().size());

        assertEquals("NONE",army2.getName());
        assertEquals(0,army2.getAllUnits().size());
    }


    @Test
    @DisplayName("Test setArmy() and resetAll() in Facade")
    void testResetAndSet(){
        //Arrange
        Facade.getInstance().setArmyOne(new Army("NewName"));
        Facade.getInstance().setArmyTwo(new Army("ADiffrentName"));

        //Assert
        assertEquals("NewName",Facade.getInstance().getArmyOne().getName());
        assertEquals("ADiffrentName",Facade.getInstance().getArmyTwo().getName());


        //Act: Reset army
        Facade.getInstance().resetAll();

        Army army1 = Facade.getInstance().getArmyOne();
        Army army2 = Facade.getInstance().getArmyTwo();


        //Assert
        assertEquals("NONE",army1.getName());
        assertEquals(0,army1.getAllUnits().size());

        assertEquals("NONE",army2.getName());
        assertEquals(0,army2.getAllUnits().size());

    }
}
