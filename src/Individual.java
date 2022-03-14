public class Individual {
  private int fitnessScore;
  private Chromosome board;

  Individual(int[][] startingBoard, boolean child) {
    board = new Chromosome(startingBoard, child);
    setFitness();
  }

  public void setFitness() {
    fitnessScore = FitnessCalc.calculateFitness(board);
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
    return board;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(board.toString());
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
