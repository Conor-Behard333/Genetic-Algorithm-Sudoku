class Run {

  public static void main(String[] args) {
     int[][] startingBoard = {
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
    // Creates a population with random candidate solutions
    // Also evaluates each candidate
    Population pop = new Population(1000, startingBoard);
    // pop.displayPopulation();
    while (true) {
      // SELECT parents
      pop.selectParents();
      // CROSSOVER pairs of parents
      pop.crossover(2);
      // MUTATE the resulting offspring
      pop.displayBest();
      pop.mutate();
      // EVALUATE new candidates
      // SELECT individuals for the next generation
      // break;
    }
  }
}
