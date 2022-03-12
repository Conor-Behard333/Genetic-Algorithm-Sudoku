import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class Board {

  private ArrayList<int[]> allowedToChange = new ArrayList<>();
  private int[][] startingBoard;

  private int[][] board;

  private int[] gene;

  Board(int[][] startingBoard, boolean child) {
    this.startingBoard = startingBoard;

    // Copy starting board into board
    board = Arrays.stream(startingBoard).map(a ->  Arrays.copyOf(a, a.length)).toArray(int[][]::new);

    int count = 0;
    for (int i = 0; i < board.length; i++) {
      for (int j = 0; j < board[i].length; j++) {
        if (board[i][j] == 0) {
          allowedToChange.add(new int[] { i, j });
          count++;
        }
      }
    }
    

    Random rand = new Random();
    gene = new int[count];
    int index = 0;
    HashMap<Integer, Integer> unique = new HashMap<>();
    for (int i = 0; i < board.length; i++) {
      for (int j = 0; j < board[i].length; j++) {
        if (board[i][j] == 0) {
          if (child == true) {
            gene[index] = 0;
            index++;
          } else {
            int randomNum = 0;
            do {
              unique.remove(randomNum);
              randomNum = rand.nextInt((9 - 1) + 1) + 1;
              if (unique.get(randomNum) == null) {
                unique.put(randomNum, 1);
              } else {
                unique.put(randomNum, unique.get(randomNum) + 1);
              }
            } while (uniqueRow(unique) == false);
            gene[index] = randomNum;
            index++;
          }
        } else {
          if (unique.get(board[i][j]) == null) {
            unique.put(board[i][j], 1);
          } else {
            unique.put(board[i][j], unique.get(board[i][j]) + 1);
          }
        }
      }
      unique.clear();
    }
  }

  private boolean uniqueRow(HashMap<Integer, Integer> unique) {
      for (int value : unique.values()) {
        if (value != 1) {
          return false;
        }
      }
    return true;
  }

  /**
    * @return the board
    */
  public int[][] getBoard() {
    return board;
  }

  /**
    * @param gene the gene to set
    */
  public void setGene(int[] gene) {
    this.gene = gene;
  }
  
  /**
    * @return the gene
    */
  public int[] getGene() {
      return gene;
  }

  @Override
  public String toString() {
    create();
    StringBuilder result = new StringBuilder();
    for (int i = 0; i < board.length; i++) {
      result.append(Arrays.toString(board[i]) + "\n");
    }
    reset();
    return result.toString();
  }

  public boolean isAllowedToChange(int row, int col) {
    return allowedToChange.contains(new int[] { row, col });
  }

  public void create() {
    int index = 0;
    for (int i = 0; i < board.length; i++) {
      for (int j = 0; j < board[i].length; j++) {
        if (board[i][j] == 0) {
            board[i][j] = gene[index];
            index++;
        }
      }
    }
  }

  public void reset() {
    board = Arrays.stream(startingBoard).map(a -> Arrays.copyOf(a, a.length)).toArray(int[][]::new);
  }
  
}
