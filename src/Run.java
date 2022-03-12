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

    //solve starting board 1 (10000, 0.85, 20, 7)
    run(100, startingBoard1, 0.25, 20, 7); //solves starting board 1

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
    //   run(1000, startingBoard, 0.85, 100, 7);    
    // }
  }
  
  private static void run(int populationSize, int[][] startingBoard, double killPercentage, int mutateProb, int numberOfCrossovers) {
    // Creates a population with random candidate solutions
    // Also evaluates each candidate
    Population pop = new Population(populationSize, startingBoard, killPercentage, mutateProb);
    int bestScore = 0;
    int generation = 0;
    // 243 is the score when a solution is found
    while (pop.getBestScore() != 243) {
      // SELECT parents 
      pop.selectParents();
      // CROSSOVER pairs of parents AND Mutate
      pop.crossover(numberOfCrossovers);
      if (bestScore != pop.getBestScore()) {
        System.out.println("GEN: " + generation +" Best Score: " + pop.getBestScore());
        bestScore = pop.getBestScore();
      }
      generation++;
    }

    System.out.println("SOLUTION FOUND");
    System.out.println(generation);
    pop.displayBest();
  }
}
