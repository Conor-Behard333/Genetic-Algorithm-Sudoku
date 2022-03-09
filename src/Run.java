class Run {

  public static void main(String[] args) {
    // Creates a population with random candidate solutions
    // Also evalueates each candidate
    Population pop = new Population(100);
    pop.displayPopulation();

    while (true) {
      // SELECT parents
      // CROSSOVER pairs of parents
      // MUTATE the resulting offspring
      // EVALUATE new candidates
      // SELECT individuals for the next generation
    }
  }
}
