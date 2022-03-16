import java.util.Random;

/**
 * Class to generate random numbers  
 */
public class Rand {
    private static long seed = new Random().nextLong();
    public static Random random = new Random(seed);

    /**
     * Generates a random integer in the range of max and min (inclusive)
     * 
     * @param max: maximum value
     * @param min: minimum value
     * @return random integer in the range of max and min
     */
    public static int randomInt(int max, int min) {
        int upperBound = max - min + 1;
        return min + Rand.random.nextInt(upperBound);
    }

    /**
     * Creates a new seed for the random number generator
     */
    public static void newSeed() {
        random.setSeed(new Random().nextLong());
    }
}
