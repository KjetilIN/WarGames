import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The test class for all of the unit classes
 *
 * @author Kjetil Indrehus
 * @version 1.0-SNAPSHOT 09.02.22
 */

class TestUnit {
    /**
     * Method that arrange one of each class;
     */
    @BeforeEach
    @AfterEach
    void setUp(){
        Unit cavalry = new CavalryUnit("cavalry",20);
        Unit infantry = new InfantryUnit("infantry",20);
        Unit ranged = new RangedUnit("ranged",20);
        Unit commander = new CommanderUnit("commander",20);

    }

    @Test
    void getNameUnit(){
        setUp();
        //assertEquals("cavalry",cavalry.getName());

    }



}
