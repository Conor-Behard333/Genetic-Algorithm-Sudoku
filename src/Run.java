class Run {

  public static void main(String[] args) {
    int[][] startingBoard1 = {
        { 3, 0, 0, 0, 0, 5, 0, 4, 7 },
        { 0, 0, 6, 0, 4, 2, 0, 0, 1 },
        { 0, 0, 0, 0, 0, 7, 8, 9, 0 },
        { 0, 5, 0, 0, 1, 6, 0, 0, 2 },
        { 0, 0, 3, 0, 0, 0, 0, 0, 4 },
        { 8, 1, 0, 0, 0, 0, 7, 0, 0 },
        { 0, 0, 2, 0, 0, 0, 4, 0, 0 },
        { 5, 6, 0, 8, 7, 0, 1, 0, 0 },
        { 0, 0, 0, 3, 0, 0, 6, 0, 0 }
    };

    int[][] startingBoard2 = {
        { 0, 0, 2, 0, 0, 0, 6, 3, 4 },
        { 1, 0, 6, 0, 0, 0, 5, 8, 0 },
        { 0, 0, 7, 3, 0, 0, 2, 9, 0 },
        { 0, 8, 5, 0, 1, 0, 0, 0, 6 },
        { 0, 0, 0, 7, 5, 0, 0, 2, 3 },
        { 0, 0, 3, 0, 0, 0, 0, 5, 0 },
        { 3, 1, 4, 0, 0, 2, 0, 0, 0 },
        { 0, 0, 9, 0, 8, 0, 4, 0, 0 },
        { 7, 2, 0, 0, 4, 0, 0, 0, 9 }
    };

    int[][] startingBoard3 = {
        { 0, 0, 4, 0, 1, 0, 0, 6, 0 },
        { 9, 0, 0, 0, 0, 0, 0, 3, 0 },
        { 0, 5, 0, 7, 9, 6, 0, 0, 0 },
        { 0, 0, 2, 5, 0, 4, 9, 0, 0 },
        { 0, 0, 0, 0, 6, 0, 0, 0, 0 },
        { 0, 8, 3, 0, 0, 0, 6, 0, 7 },
        { 0, 0, 0, 9, 0, 3, 0, 7, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 6, 0, 0, 0, 0, 1, 0 }
    };

      run(10000, startingBoard1, 0.95, 80, 100);
      run(1000, startingBoard1, 0.8, 80, 1000);
      run(100, startingBoard1, 0.8, 80, 10000); 
      run(10, startingBoard1, 0.6, 80, 100000);
      
      run(10000, startingBoard2, 0.95, 80, 100);
      run(1000, startingBoard2, 0.8, 80, 1000);
      run(100, startingBoard2, 0.8, 80, 10000); 
      run(10, startingBoard2, 0.6, 80, 100000);
      
      run(10000, startingBoard3, 0.95, 80, 100);
      run(1000, startingBoard3, 0.8, 80, 1000);
      run(100, startingBoard3, 0.8, 80, 10000); 
      run(10, startingBoard3, 0.6, 80, 100000); 
  }
  
  private static void run(int populationSize, int[][] startingBoard, double killPercentage, int mutateProb, int genTermination) {
    Population pop = new Population(populationSize, startingBoard, killPercentage, mutateProb);
    int bestScore = 0;
    int run = 0;
    while (true) {
      if (pop.getGeneration() == genTermination || pop.getBestScore() == 243) {
        run++;
        Data runData = new Data();
        Individual solution = null;  
        if (pop.getBestScore() == 243) {
          solution = pop.getBestIndividual();
        }
        runData.saveData(populationSize, startingBoard, killPercentage, mutateProb, genTermination, run,
            pop.getGeneration() == genTermination, pop.getBestScore(), pop.getWorstScore(), pop.getGeneration(), solution);
        System.out.println(runData);
        // Reset entire population settings
        pop.resetGeneration();
        Rand.newSeed();
        pop = new Population(populationSize, startingBoard, killPercentage, mutateProb);
        bestScore = 0;
        if (run == 5) {
          break;
        }
      }
      
      pop.selectParents();

      pop.crossover();

      if (bestScore != pop.getBestScore()) {
        bestScore = pop.getBestScore();
      }
    }
  }

}
