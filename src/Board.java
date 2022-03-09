import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Board {
  private int[][] testBoard = {
    {5, 3, 2, 4, 7, 8, 4, 9, 3},
    {6, 7, 1, 1, 9, 5, 4, 1, 4},
    {1, 9, 8, 4, 6, 1, 1, 6, 2},
    {8, 1, 1, 2, 6, 6, 5, 5, 3},
    {4, 9, 1, 8, 9, 3, 2, 6, 1},
    {7, 5, 3, 2, 2, 2, 1, 9, 6},
    {9, 6, 7, 6, 9, 9, 2, 8, 8},
    {3, 1, 4, 4, 1, 9, 3, 6, 5},
    {3, 4, 7, 2, 8, 3, 8, 7, 9}
  };

  private ArrayList<int[]> allowedToChange = new ArrayList<>();

  private int[][] board = {
    { 5, 3, 0, 0, 7, 0, 0, 0, 0 },
    { 6, 0, 0, 1, 9, 5, 0, 0, 0 },
    { 0, 9, 8, 0, 0, 0, 0, 6, 0 },
    { 8, 0, 0, 0, 6, 0, 0, 0, 3 },
    { 4, 0, 0, 8, 0, 3, 0, 0, 1 },
    { 7, 0, 0, 0, 2, 0, 0, 0, 6 },
    { 0, 6, 0, 0, 0, 0, 2, 8, 0 },
    { 0, 0, 0, 4, 1, 9, 0, 0, 5 },
    { 0, 0, 0, 0, 8, 0, 0, 7, 9 },
  };

  Board() {
    for (int i = 0; i < board.length; i++) {
      for (int j = 0; j < board[i].length; j++) {
        if (board[i][j] == 0) {
          allowedToChange.add(new int[] { i, j });
        }
      }
    }
    

    Random rand = new Random();
    for (int i = 0; i < board.length; i++) {
      for (int j = 0; j < board[i].length; j++) {
        if (board[i][j] == 0) {
          board[i][j] = rand.nextInt((9 - 1) + 1) + 1;
        }
      }
    }
  }

 /**
  * @return the board
  */
 public int[][] getBoard() {
     return board;
 }

  /**
   * @return the testBoard
   */
  public int[][] getTestBoard() {
      return testBoard;
  }

  @Override
  public String toString() {
    StringBuilder result = new StringBuilder();
    for (int i = 0; i < testBoard.length; i++) {
      result.append(Arrays.toString(testBoard[i]) + "\n");
    }
    return result.toString();
  }

  public boolean isAllowedToChane(int row, int col) {
    return allowedToChange.contains(new int[] { row, col });
  }
}
