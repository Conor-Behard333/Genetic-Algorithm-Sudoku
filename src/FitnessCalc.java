import java.util.HashMap;

public class FitnessCalc {

  FitnessCalc() {}

  public static int calculateFitness(Individual individual) {
    Board board = individual.getBoard();
    int[][] boardArr = board.getBoard();

    int totalRowFitness = getTotalRowFiteness(boardArr);
    int totalColFitness = getTotalColumnFiteness(boardArr);
    int totalGridFitness = getTotalGridFitness(boardArr);
    return totalRowFitness + totalColFitness + totalGridFitness;
  }

  private static int getTotalRowFiteness(int[][] boardArr) {
    int total = 0;
    for (int i = 0; i < boardArr.length; i++) {
      total += getRowFiteness(boardArr, i);
    }
    return total;
  }

  private static int getTotalColumnFiteness(int[][] boardArr) {
    int total = 0;
    for (int i = 0; i < boardArr.length; i++) {
      total += getColumnFiteness(boardArr, i);
    }
    return total;
  }

  private static int getTotalGridFitness(int[][] boardArr) {
    int total = 0;
    for (int i = 0; i < boardArr.length; i++) {
      total += getGridFiteness(boardArr, i);
    }
    return total;
  }

  private static int getRowFiteness(int[][] board, int row) {
    HashMap<Integer, Integer> rowCount = new HashMap<>();
    for (int col = 0; col < board.length; col++) {
      if (rowCount.get(board[row][col]) == null) {
        rowCount.put(board[row][col], 1);
      } else {
        rowCount.put(board[row][col], rowCount.get(board[row][col]) + 1);
      }
    }
    return getCount(rowCount);
  }

  private static int getColumnFiteness(int[][] board, int col) {
    HashMap<Integer, Integer> colCount = new HashMap<>();

    for (int row = 0; row < board.length; row++) {
      if (colCount.get(board[row][col]) == null) {
        colCount.put(board[row][col], 1);
      } else {
        colCount.put(board[row][col], colCount.get(board[row][col]) + 1);
      }
    }
    return getCount(colCount);
  }

  private static int getGridFiteness(int[][] board, int grid) {
    HashMap<Integer, Integer> gridCount = new HashMap<>();
    int start = (grid % 3) * 3;
    if (grid <= 2) {
      for (int row = 0; row < 3; row++) {
        for (int col = start; col < start + 3; col++) {
          if (gridCount.get(board[row][col]) == null) {
            gridCount.put(board[row][col], 1);
          } else {
            gridCount.put(board[row][col], gridCount.get(board[row][col]) + 1);
          }
        }
      }
    } else if (grid >= 3 && grid <= 5) {
      for (int row = 3; row < 6; row++) {
        for (int col = start; col < start + 3; col++) {
          if (gridCount.get(board[row][col]) == null) {
            gridCount.put(board[row][col], 1);
          } else {
            gridCount.put(board[row][col], gridCount.get(board[row][col]) + 1);
          }
        }
      }
    } else {
      for (int row = 6; row < 9; row++) {
        for (int col = start; col < start + 3; col++) {
          if (gridCount.get(board[row][col]) == null) {
            gridCount.put(board[row][col], 1);
          } else {
            gridCount.put(board[row][col], gridCount.get(board[row][col]) + 1);
          }
        }
      }
    }
    return getCount(gridCount);
  }

  private static int getCount(HashMap<Integer, Integer> countMap) {
    int count = 0;
    for (int value : countMap.values()) {
      if (value == 1) {
        count++;
      }
    }
    return count;
  }
}
