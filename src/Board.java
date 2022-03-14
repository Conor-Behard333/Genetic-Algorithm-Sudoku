import java.util.ArrayList;
import java.util.Arrays;

public class Board {

  private ArrayList<Integer> allowedToChange = new ArrayList<>();
  private int[] startingBoard;
  private int[] chromosome;

  Board(int[][] startingBoard, boolean child) {
    this.startingBoard = flatten(startingBoard);

    //copy starting board into board
    chromosome = Arrays.copyOf(this.startingBoard, this.startingBoard.length);

    for (int i = 0; i < chromosome.length; i++) {
      if (chromosome[i] == 0) {
        allowedToChange.add(i);
      }
    }

    for (int i = 0; i < chromosome.length; i++) {
      if (chromosome[i] == 0) {
        if (child == false) {
          int randomNum = Rand.randomInt(9, 1);
          while (randomNumExists(chromosome, randomNum, i)) {
            randomNum = Rand.randomInt(9, 1);
          }
          chromosome[i] = randomNum;
        }
      }
    }
  }

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
    * @return the board
    */
  public int[] getChromosome() {
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

  public boolean isAllowedToChange(int index) {
    return allowedToChange.contains(index);
  }

  // public void reset() {
    // chromosome = Arrays.stream(startingBoard).map(a -> Arrays.copyOf(a, a.length)).toArray(int[][]::new);
  // }
  
}
