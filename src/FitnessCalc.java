import java.util.HashMap;

public class FitnessCalc {

  /**
   * Calculates the total fitness of a board by using hashmaps
   * the length of a hashmap will find the fitness value for a single row, 
   * column or sub-grid, the sum of all three for the entire board equals 
   * the total fitness score. 
   * 243 is the maximum fitness value possible
   * 
   * @param chromosome: the chromosome being evaluated 
   * @return the fitness value
   */
  public static int calculateFitness(Chromosome chromosome) {
    int[][] array2d = chromosome.getTwoDChromosome();

    // Calculate the fitness of the rows
    int totalRowFitness = getTotalFitness(array2d, 1);

    // Calculate the fitness of the columns
    int totalColFitness = getTotalFitness(array2d, 2);

    // Calculate the fitness of the grids
    int totalGridFitness = getTotalFitness(array2d, 3);

    // Return the sum of the row, column, and grid fitnesses
    return totalRowFitness + totalColFitness + totalGridFitness;
  }

  /**
   * Calculate the fitness for either the rows, columns or grids
   * 
   * @param chromosome: The chromosome being evaluated
   * @param num: 1 if calculating row fitness, 2 for column fitness and 3 for grid fitness
   * @return The total fitness for either row, column or grid 
   */
  private static int getTotalFitness(int[][] chromosome, int num) {
    int total = 0;
    for (int i = 0; i < chromosome.length; i++) {
      if (num == 1) {
        total += getRowFitness(chromosome, i);
      } else if (num == 2) {
        total += getColumnFitness(chromosome, i);
      } else {
        total += getGridFitness(chromosome, i);
      }
    }
    return total;
  }

  /**
   * Calculate fitness for a single row
   * 
   * @param chromosome: The chromosome being evaluated
   * @param row: The individual row
   * @return
   */
  private static int getRowFitness(int[][] chromosome, int row) {
    HashMap<Integer, Integer> rowCount = new HashMap<>();
    for (int col = 0; col < chromosome.length; col++) {
      if (rowCount.get(chromosome[row][col]) == null) {
        rowCount.put(chromosome[row][col], 1);
      } else {
        rowCount.put(chromosome[row][col], rowCount.get(chromosome[row][col]) + 1);
      }
    }
    return rowCount.size();
  }

  /**
   * Calculate fitness for a single column
   * 
   * @param chromosome: The chromosome being evaluated
   * @param col: The individual column
   * @return
   */
  private static int getColumnFitness(int[][] chromosome, int col) {
    HashMap<Integer, Integer> colCount = new HashMap<>();

    for (int row = 0; row < chromosome.length; row++) {
      if (colCount.get(chromosome[row][col]) == null) {
        colCount.put(chromosome[row][col], 1);
      } else {
        colCount.put(chromosome[row][col], colCount.get(chromosome[row][col]) + 1);
      }
    }
    return colCount.size();
  }

  /**
   * Calculate fitness for a single sub-grid
   * 
   * @param chromosome: The chromosome being evaluated
   * @param grid: The individual sub-grid
   * @return
   */
  private static int getGridFitness(int[][] chromosome, int grid) {
    HashMap<Integer, Integer> gridCount = new HashMap<>();
    int start = (grid % 3) * 3;
    if (grid <= 2) {
      addRowOfGrid(chromosome, gridCount, start, 0, 3);
    } else if (grid >= 3 && grid <= 5) {
      addRowOfGrid(chromosome, gridCount, start, 3, 6);
    } else {
      addRowOfGrid(chromosome, gridCount, start, 6, 9);
    }
    return gridCount.size();
  }

  /**
   * Add the row of a sub-grid to the unique hashmap
   * 
   * @param chromosome: The chromosome being evaluated
   * @param gridCount: The hashmap containing unique values
   * @param start: The start index for the column
   * @param bound1: The start index for the row
   * @param bound2: The end index for the row 
   */
  private static void addRowOfGrid(int[][] chromosome, HashMap<Integer, Integer> gridCount, int start, int bound1, int bound2) {
    for (int row = 6; row < 9; row++) {
      for (int col = start; col < start + 3; col++) {
        if (gridCount.get(chromosome[row][col]) == null) {
          gridCount.put(chromosome[row][col], 1);
        } else {
          gridCount.put(chromosome[row][col], gridCount.get(chromosome[row][col]) + 1);
        }
      }
    }
  }
}
