public class Individual {
  private int fitnessScore;
  private Chromosome chromosome;

  /**
   * Constructor to initialize the individual
   * 
   * @param startingBoard: The starting board
   * @param child: True if a child is being initialized, false if not
   */
  Individual(int[][] startingBoard, boolean child) {
    chromosome = new Chromosome(startingBoard, child);
    setFitness();
  }

  /**
   * Sets the fitness score for the individual 
   */
  public void setFitness() {
    fitnessScore = FitnessCalc.calculateFitness(chromosome);
  }

  /**
   * @return the fitnessScore
   */
  public int getFitnessScore() {
    return fitnessScore;
  }

  /**
   * @return the chromosome
   */
  public Chromosome getChromosome() {
    return chromosome;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(chromosome.toString());
    return sb.toString();
  }

  /**
   * Comparison function to allow individuals to be sorted by their fitness score
   * @param a: individual a
   * @param b: individual b
   * 
   */
  public static int compareFitnessScore(Individual a, Individual b) {
    if (a == null || b == null) {
      return 1;
    } else {
      return b.getFitnessScore() - a.getFitnessScore();
    }
  }
}
