package frontend.terminalInterface.utility;

import java.util.Scanner;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

/**
 * This is a Scanner class with methods to handle user input for the terminal version.
 *
 * @author Kjetil Indrehus
 * @version 1.0-SNAPSHOT 16.02.2022
 */

public class ScannerUtility {
    private static Scanner sc;

    /* Constructor for the Scanner Utility class
     */
    public ScannerUtility() {
        sc = new Scanner(System.in);
    }

    /**
     * A method that validates the input from a user, then checks if the value is in range from given parameters.
     * @param min the minimum value of the integer given
     * @param max the max value of the integer given
     * @param message a string to tell the user what input to enter
     * @return return a integer given from the user
     */
    public int validateInt(int min, int max, String message){
        boolean finished = false; // flag variable
        int out =0;
        while(!finished) {
            System.out.println("Please " + message);
            try {
                out = parseInt(sc.nextLine());//try to pass the string as int, expect error if the string is not integer
                if (out >= min && out <= max) {
                    finished = true;
                }else{
                    System.err.println("Integer not in range, try again.....");
                }
            }catch (Exception e){System.out.println("Not a int, try again......");} // we tell the user that an integer was not entered
        }
        return out;
    }


    /**
     * A method to validate the given string.
     * @param message a string to tell the user what to return
     * @return return the string given by user
     */
    public String validateString(String message){
        boolean finished = false;
        String out = "";
        while(!finished) {
            System.out.println("Please " + message);
            try {
                out = sc.nextLine();
                if((out.length() > 0) && !(out.isEmpty())){finished = true;} // if the string is not empty, we end the loop

            }catch (Exception e){System.out.println("Not a string, try again......");} // error handling, if the input is not taken
        }
        return out;
    }

    /**
     * A method that waits for the user to enter a specific word, and returns that word.
     * For example: (yes, no)
     * @param message a string to tell the user what to enter
     * @param listOfWords an array of words that is looked for
     * @return return the given word, that is also in the wordlist
     */

    public String validateWord(String message,String[]listOfWords){
        boolean finished = false;
        String found="";
        while(!finished){
            found = validateString(message); // uses the "validate-string-method" to get string input
            for(int i=0; i<listOfWords.length;i++){ //goes through list of words with "i" as flag
                if(found.equalsIgnoreCase(listOfWords[i])){
                    finished = true;
                    i = listOfWords.length; //End processes by making "i" max value to end the loop
                }
            }
        }
        return found;

    }

    /**
     * A method to validate if the input is boolean
     * @param message a string to tell the user what to enter
     * @return return a boolean based on what is entered
     */

    public Boolean validateBoolean(String message){
        String given = validateWord(message,new String[]{"true","false"}); // validate true or false
        return given.equalsIgnoreCase("true");
    }


    /**
     * Validate a double.
     * @param min the minimum value of the double given
     * @param max the max value of the double given
     * @param message a string to tell the user what input to enter
     * @return return a double given from the user
     */
    public double validateDouble(double min, double max, String message){
        boolean finished = false; // flag variable
        double out =0;
        while(!finished) {
            System.out.println("Please " + message);
            try {
                out = parseDouble(sc.nextLine());//try to pass the string as int, expect error if the string is not integer
                if (out >= min && out <= max) {
                    finished = true;
                }else{
                    System.err.println("Float not in range, try again.....");
                }
            }catch (Exception e){System.out.println("Not a float, try again......");} // we tell the user that an integer was not entered
        }
        return out;
    }

}
