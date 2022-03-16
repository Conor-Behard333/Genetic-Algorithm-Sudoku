class Run {

  public static void main(String[] args) {
    int[][][] startingBoards = { {
        { 3, 0, 0, 0, 0, 5, 0, 4, 7 },
        { 0, 0, 6, 0, 4, 2, 0, 0, 1 },
        { 0, 0, 0, 0, 0, 7, 8, 9, 0 },
        { 0, 5, 0, 0, 1, 6, 0, 0, 2 },
        { 0, 0, 3, 0, 0, 0, 0, 0, 4 },
        { 8, 1, 0, 0, 0, 0, 7, 0, 0 },
        { 0, 0, 2, 0, 0, 0, 4, 0, 0 },
        { 5, 6, 0, 8, 7, 0, 1, 0, 0 },
        { 0, 0, 0, 3, 0, 0, 6, 0, 0 }
    },
    {
        { 0, 0, 2, 0, 0, 0, 6, 3, 4 },
        { 1, 0, 6, 0, 0, 0, 5, 8, 0 },
        { 0, 0, 7, 3, 0, 0, 2, 9, 0 },
        { 0, 8, 5, 0, 1, 0, 0, 0, 6 },
        { 0, 0, 0, 7, 5, 0, 0, 2, 3 },
        { 0, 0, 3, 0, 0, 0, 0, 5, 0 },
        { 3, 1, 4, 0, 0, 2, 0, 0, 0 },
        { 0, 0, 9, 0, 8, 0, 4, 0, 0 },
        { 7, 2, 0, 0, 4, 0, 0, 0, 9 } 
    },
    {
        { 0, 0, 4, 0, 1, 0, 0, 6, 0 },
        { 9, 0, 0, 0, 0, 0, 0, 3, 0 },
        { 0, 5, 0, 7, 9, 6, 0, 0, 0 },
        { 0, 0, 2, 5, 0, 4, 9, 0, 0 },
        { 0, 0, 0, 0, 6, 0, 0, 0, 0 },
        { 0, 8, 3, 0, 0, 0, 6, 0, 7 },
        { 0, 0, 0, 9, 0, 3, 0, 7, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 6, 0, 0, 0, 0, 1, 0 }
    }};


    // run all configs for each board
    for (int i = 0; i < startingBoards.length; i++) {
      run(10000, startingBoards[i], 0.95, 80, 100);
      run(1000, startingBoards[i], 0.8, 80, 1000);
      run(100, startingBoards[i], 0.8, 80, 10000);
      run(10, startingBoards[i], 0.6, 80, 100000);
    }
  }

  private static void run(int populationSize, int[][] startingBoard, double killPercentage, int mutateProb,
      int genTermination) {
    Population pop = new Population(populationSize, startingBoard, killPercentage, mutateProb);
    int bestScore = 0;
    int run = 0;
    while (true) {
      // Termination criteria 
      if (pop.getGeneration() == genTermination || pop.getBestScore() == 243) {
        run++;
        outputRunData(populationSize, startingBoard, killPercentage, mutateProb, genTermination, pop, run);

        // Reset entire population settings
        pop.resetGeneration();
        Rand.newSeed();
        pop = new Population(populationSize, startingBoard, killPercentage, mutateProb);
        bestScore = 0;

        if (run == 5) {
          break;
        }
      }

      // Select parents
      pop.selectParents();

      // Crossover & mutate
      pop.crossoverAndMutate();

      // Update the best Score
      if (bestScore != pop.getBestScore()) {
        bestScore = pop.getBestScore();
      }
    }
  }

  /**
   * Function to output the current run
   * 
   * @param populationSize: the size of the population
   * @param startingBoard: the starting board
   * @param killPercentage: the percentage of individuals removed from the population
   * @param mutateProb: the probability to mutate
   * @param genTermination: the generation in which the run will terminate
   * @param pop: the population
   * @param run: the current configuration running
   */
  private static void outputRunData(int populationSize, int[][] startingBoard, double killPercentage, int mutateProb,
      int genTermination, Population pop, int run) {
    Data runData = new Data();
    Individual solution = null;
    if (pop.getBestScore() == 243) {
      solution = pop.getBestIndividual();
    }
    runData.saveData(populationSize, startingBoard, killPercentage, mutateProb, genTermination, run,
        pop.getGeneration() == genTermination, pop.getBestScore(), pop.getWorstScore(), pop.getGeneration(), solution);
    System.out.println(runData);
  }

}
