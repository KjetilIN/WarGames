package no.ntnu.wargames.backend.designPattern;

import java.util.Random;

/**
 * Random singleton that returns a random singleton instance.
 * Uses the singleton design pattern.
 * Only holds a random field.
 * @see <a href="https://refactoring.guru/design-patterns/singleton/java/example">Singleton Doc</a>
 *
 * @author Kjetil Indrehus
 * @version 1.0-SNAPSHOT
 */

public class RandomSingleton {
    private static volatile RandomSingleton instance; //makes this field written to main memory. See javadoc for volatile.
    private final Random random;


    /**
     * Singleton constructor for the singleton object.
     * Creates a new random object within the class.
     * Takes no parameter(s) and are only called by {@link RandomSingleton#getInstance()} method.
     */
    private RandomSingleton(){
        this.random = new Random();
    }

    /**
     * Creates an instance if the Random singleton if none exist from before.
     *
     * @return returns instance of the {@link RandomSingleton} class.
     */
    public static RandomSingleton getInstance(){
        if(instance == null){ //check if random singleton exist.
            synchronized (RandomSingleton.class){ //Synchronize the creation of the class.
                instance = new RandomSingleton();
            }

        }
        return instance;
    }

    /**
     * Getter for the Random object within the class.
     *
     * @return returns the random object.
     */
    public Random getRandom() {
        return this.random;
    }

}
