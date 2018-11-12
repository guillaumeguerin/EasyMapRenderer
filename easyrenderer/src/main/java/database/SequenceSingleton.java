package database;

/**
 * Singleton class for that manages a unique sequence.
 */
public class SequenceSingleton {

    private static volatile SequenceSingleton singletonInstance = null;

    private static volatile Double uniqueId = null;

    /**
     * Default constructor
     */
    private SequenceSingleton() {
        //Empty on purpose.
    }

    /**
     * Retrieve the instance of the singleton
     *
     * @return the sequence singleton
     */
    public static SequenceSingleton getInstance() {
        if (singletonInstance == null) {
            singletonInstance = new SequenceSingleton();
            uniqueId = 0.;
        }
        return singletonInstance;
    }

    /**
     * Gets a unique id
     *
     * @return the unique id
     */
    public static Double getId() {
        uniqueId += 1;
        return uniqueId;
    }
}
