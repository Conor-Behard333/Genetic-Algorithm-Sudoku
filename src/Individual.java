public class Individual {
  private int fitnessScore;
  private Chromosome chromosome;

  Individual(int[][] startingBoard, boolean child) {
    chromosome = new Chromosome(startingBoard, child);
    setFitness();
  }

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
   * @return the board
   */
  public Chromosome getBoard() {
    return chromosome;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(chromosome.toString());
    sb.append(fitnessScore + "\n");
    return sb.toString();
  }

  public static int compareFitnessScore(Individual a, Individual b) {
    if (a == null || b == null) {
      return 1;
    } else {
      return b.getFitnessScore() - a.getFitnessScore();
    }
  }
}
