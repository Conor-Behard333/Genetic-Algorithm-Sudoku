import java.util.ArrayList;
import java.util.Arrays;

public class Chromosome {

  private ArrayList<Integer> allowedToChange = new ArrayList<>();
  private int[] startingBoard;
  private int[] chromosome;

  /**
   * constructor that builds the chromosome
   * 
   * @param startingBoard: the starting board configuration
   * @param child: true if the chromosome being created is for a child false if not
   */
  Chromosome(int[][] startingBoard, boolean child) {
    // Converts the 2d array starting board into a 1d array
    this.startingBoard = flatten(startingBoard);

    //copy starting board into board
    chromosome = Arrays.copyOf(this.startingBoard, this.startingBoard.length);

    // Add the index's of values that are allowed to change
    for (int i = 0; i < chromosome.length; i++) {
      if (chromosome[i] == 0) {
        allowedToChange.add(i);
      }
    }

    /* 
    If creating a chromosome for the beginning population fill the board so that
    each row is unique    
    */
    if (child == false) {
      for (int i = 0; i < chromosome.length; i++) {
        if (chromosome[i] == 0) {
          int randomNum = Rand.randomInt(9, 1);
          while (randomNumExists(chromosome, randomNum, i)) {
            randomNum = Rand.randomInt(9, 1);
          }
          chromosome[i] = randomNum;
        }
      }
    }
  }

  /**
   * Checks to see if the random number given is already in the row
   * 
   * @param chromosome: the chromosome
   * @param randomNum: random number 
   * @param index: index of the row
   * @return true if the random number already exists, false if it doesn't
   */
  private boolean randomNumExists(int[] chromosome, int randomNum, int index) {
    int start = 9 * (index / 9);
    int end = start + 9;
    for (int i = start; i < end; i++) {
      if (chromosome[i] == randomNum) {
        return true;
      }
    }
    return false;
  }


  /**
  * Converts a 2d array into a 1d array
  * 
  * @param arr: Array to be flattened 
  * @return the 1d version of the given 2d array
  */
  private int[] flatten(int[][] arr) {
    int[] flat = new int[arr.length * arr[0].length];
    int index = 0;
    for (int i = 0; i < arr.length; i++) {
      for (int j = 0; j < arr.length; j++) {
        flat[index] = arr[i][j];
        index++;
      }
    }
    return flat;
  }
  
    
  /**
   * Converts a 1 dimensional array into a 2 dimensional array
   * 
   * @return the 2 dimensional array
   */
  public int[][] getTwoDChromosome() {
      int[][] twoD = new int[9][9];
      int index = 0;
      for (int i = 0; i < twoD.length; i++) {
          for (int j = 0; j < twoD.length; j++) {
              twoD[i][j] = chromosome[index];
              index++;
          }
      }
      return twoD;
  }

  /**
    * @return the chromosome
    */
  public int[] getChromosomeAsArray() {
    return chromosome;
  }

  /**
    * @param Chromosome the Chromosome to set
    */
  public void setChromosome(int[] Chromosome) {
    this.chromosome = Chromosome;
  }

  @Override
  public String toString() {
    StringBuilder result = new StringBuilder();
    for (int i = 0; i < chromosome.length; i++) {
      result.append(chromosome[i] + ", ");
      if ((i + 1) % 9 == 0) {
        result.append("\n");
      }
    }
    return result.toString();
  }

  /**
   * Checks to see if an index in the chromosome is allowed to be changed 
   * 
   * @param index: an index of the chromosome
   * @return true if it can be changed, false if it can't
   */
  public boolean isAllowedToChange(int index) {
    return allowedToChange.contains(index);
  }
  
}
