import java.util.Random;

public class Rand {
    private static long seed = new Random().nextLong();
    public static Random random = new Random(seed);

    public static int randomInt(int max, int min) {
        int upperBound = max - min + 1;
        return min + Rand.random.nextInt(upperBound);
    }

    public static long getSeed() {
        return seed;
    }
    
    public static void setSeed(Long seed) {
        Rand.seed = seed;
        random.setSeed(seed);
    }

    public static void newSeed() {
        random.setSeed(new Random().nextLong());
    }
}
