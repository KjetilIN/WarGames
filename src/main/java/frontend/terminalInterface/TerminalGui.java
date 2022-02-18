package frontend.terminalInterface;
import backend.Battle;
import backend.units.*;
import frontend.terminalInterface.utility.ScannerUtility;

/**
 * The text-based application used for running the app thought the terminal.
 *
 * @author Kjetil Indrehus
 * @version 1.0-SNAPSHOT 16.02.2022
 */

public class TerminalGui {

    private ScannerUtility utility;

    private TerminalGui(){
        utility = new ScannerUtility();
    }

    /**
     * Method to tell the user what armies are ready for battle, and their unit count.
     * Ask if the user is ready for simulation.
     *
     * @param army1 fist army to present for the user.
     * @param army2 the second army to present for the user.
     * @param utility utility class to handle input.
     * @return returns true if the user is ready.
     */
    public boolean intro(Army army1, Army army2, ScannerUtility utility){
        System.out.println("====== Battle simulation ======");
        System.out.println(army1.getName() + " VS. " + army2.getName());
        System.out.println("===== STATS ======");
        System.out.println(army1.getName() + ": "+army1.getAllUnits().size() +" units.");
        System.out.println(army2.getName() + ": "+army2.getAllUnits().size() +" units.");
        System.out.println("====== Ready for simulation? ======");

        String readyStatus = utility.validateWord("enter yes or no",new String[]{"yes","no"});
        return readyStatus.equalsIgnoreCase("yes");

    }

    /**
     * Method to reset the army for a new simulation.
     * Clears the armies before adding them to the test.
     * Developer can change the units here.
     *
     * @param testArmy1 first army to prepare for battle.
     * @param testArmy2 the second army to prepare for battle.
     */
    public void addTestUnitsToArmy(Army testArmy1,Army testArmy2){
        //Removes all of the units
        testArmy1.getAllUnits().clear();
        testArmy2.getAllUnits().clear();

        //Add to the first army
        testArmy1.add(new InfantryUnit("Digsec",29 ));
        testArmy1.add(new CavalryUnit("DataIngeniør",29 ));
        testArmy1.add(new CommanderUnit("Cyber",29 ));
        testArmy1.add(new RangedUnit("Sykepleier",29 ));
        testArmy1.add(new InfantryUnit("ByggIngeniør",29 ));
        testArmy1.add(new InfantryUnit("ElektroIngeniør",29 ));
        testArmy1.add(new InfantryUnit("Lektorer",29 ));

        //Add to the second army
        testArmy2.add(new InfantryUnit("Jursit",29 ));
        testArmy2.add(new CavalryUnit("Kjemi",29 ));
        testArmy2.add(new CommanderUnit("Biologi",29 ));
        testArmy2.add(new RangedUnit("ÅrstStudenter",29 ));
        testArmy2.add(new InfantryUnit("Forelesere",29 ));
        testArmy2.add(new InfantryUnit("Sykepleier",29 ));
        testArmy2.add(new InfantryUnit("MasterStudent",29 ));
    }

    /**
     * Takes two army's and simulate multiple battles.
     * GUI method that takes input from terminal. NB! Method to be terminated or changed.
     *
     * @param testArmy1 the first army given to be battled.
     * @param testArmy2 the second army to join the battle.
     */

    public void simulateMultipleBattleOption(Army testArmy1, Army testArmy2){
        int amountOfBattles = utility.validateInt(1,2000000000," enter amount of simulations:");
        int scoreArmy1 = 0;
        int scoreArmy2 = 0;
        while(amountOfBattles > 0){
            amountOfBattles--;
            addTestUnitsToArmy(testArmy1,testArmy2); //update the army
            Battle battle = new Battle(testArmy1,testArmy2);
            Army winner = battle.simulateBattle();
            if(winner.equals(testArmy1)){
                scoreArmy1++;
            }else{
                scoreArmy2++;
            }
            System.out.println("WINNER IS: "+ winner.getName());
            System.out.println("Units left: "+ winner.getAllUnits().size());
        }
        System.out.println("==================================");
        System.out.println("Final score:");
        System.out.println(testArmy1.getName() +": " + scoreArmy1 + " wins.");
        System.out.println(testArmy2.getName() +": " + scoreArmy2 + " wins.");

    }

    /**
     * Takes two army's and simulates a single battle.
     * Shows each step in the terminal.
     * GUI method that takes input from terminal. NB! Method to be terminated or changed.
     *
     * @param testArmy1 the first army given to be battled.
     * @param testArmy2 the second army to join the battle.
     */

    public void simulateSingleBattleOption(Army testArmy1, Army testArmy2){
        Battle battle = new Battle(testArmy1,testArmy2);
        while (testArmy1.hasUnits() && testArmy2.hasUnits()){
            System.out.println(battle.simulateStep());
        }
        if(testArmy1.hasUnits()){
            System.out.println(testArmy1.getName() + " won with " + testArmy1.getAllUnits().size() +" left!");
        }else{
            System.out.println(testArmy2.getName() + " won with " + testArmy2.getAllUnits().size() +" left!");

        }

    }

    /**
     * Main method that executes the simulation.
     *
     * @param args argument given (In this case, no additional args).
     */
    public static void main(String[] args) {
        TerminalGui gui = new TerminalGui();
        Army testArmy1 = new Army("NTNU");
        Army testArmy2 = new Army("UIO");


        //Add the army
        gui.addTestUnitsToArmy(testArmy1,testArmy2);
        //Intro to the battle
        boolean ready = gui.intro(testArmy1,testArmy2, gui.utility);
        if(!ready){
            System.exit(1); // Ends the program
        }

        int battleOption = gui.utility.validateInt(0,1, "enter 1 for for battle, or 0 for multiple battle simulatios: ");
        switch (battleOption){
            case 0:
                gui.simulateMultipleBattleOption(testArmy1,testArmy2);
                break;

            case 1:
                gui.simulateSingleBattleOption(testArmy1,testArmy2);
                break;

            default:
                System.err.println("ERROR!");
        }








    }
}
