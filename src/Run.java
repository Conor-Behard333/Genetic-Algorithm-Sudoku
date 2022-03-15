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

    // run(10000, startingBoard1, 0.95, 80, 100);// Solves 1
       run(10000, startingBoard2, 0.98, 90, 300);// Solves 1

    // for (int i = 0; i < 5; i++) {
    //   run(10, startingBoard, 0.85, 100, 7);
    // }
    // for (int i = 0; i < 5; i++) {
    //   run(100, startingBoard, 0.85, 100, 7);
    // }
    // for (int i = 0; i < 5; i++) {
    //   run(1000, startingBoard, 0.85, 100, 7);
    // }
    // for (int i = 0; i < 5; i++) {
    //   run(10000, startingBoard, 0.85, 100, 7);    
    // }
  }
  
  private static void run(int populationSize, int[][] startingBoard, double killPercentage, int mutateProb, int genTermination) {
    
    // Creates a population with random candidate solutions
    // Also evaluates each candidate
    Population pop = new Population(populationSize, startingBoard, killPercentage, mutateProb);
    int bestScore = 0;
    // 243 is the score when a solution is found
    while (true) {
      if (pop.getGeneration() == genTermination) {
        System.out.println();
        pop = new Population(populationSize, startingBoard, killPercentage, mutateProb);
        bestScore = 0;
        pop.resetGeneration();
        Rand.newSeed();
        System.out.println(Rand.getSeed());
      }
      System.out.println("GEN: " + pop.getGeneration() + ", Worst Score: " + pop.getWorstScore() + ", Best Score: "
            + pop.getBestScore());
      // SELECT parents 
      pop.selectParents();
      // CROSSOVER pairs of parents AND Mutate
      pop.crossover();
      if (bestScore != pop.getBestScore()) {
        
        bestScore = pop.getBestScore();
      }
      if (pop.getBestScore() == 243) {
        break;
     }
    }
    if (pop.getGeneration() == genTermination) {
      System.out.println("GENERATION LIMIT EXCEEDED");
    } else {
      System.out.println("SOLUTION FOUND");
      System.out.println(pop.getGeneration());
      pop.displayBest();
    }
  }
}
