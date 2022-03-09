public class Individual {
  private int fitnessScore;
  private Board board;

  Individual() {
    board = new Board();
    fitnessScore = FitnessCalc.calculateFitness(this);
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
  public Board getBoard() {
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
    if (a.getFitnessScore() >= b.getFitnessScore() ){
          return 1;
        } else {
          return -1;
        }
    }
}
