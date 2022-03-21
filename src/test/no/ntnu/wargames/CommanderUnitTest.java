package no.ntnu.wargames;

import no.ntnu.wargames.backend.units.CommanderUnit;
import no.ntnu.wargames.backend.units.Unit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Commander test class.
 * Only test constructor is correct.
 * The commander class inference from the cavalry-unit.
 *
 * @author Kjetil Indrehus
 * @version 1.0-SNAPSHOT 23.02.2022
 */
class CommanderUnitTest {


    @Test
    @DisplayName("Test if the commander unit constructor works.")
    void testConstructorCommander(){
        Unit unit = new CommanderUnit("names",10);
    }

}