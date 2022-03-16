public class Data {
    private double killPercentage;
    private int populationSize;
    private int[][] startingBoard;
    private Individual solution;
    private boolean terminated;
    private int genTermination;
    private int mutateProb;
    private int run;
    private int bestFitness;
    private int worstFitness;
    private int generationSolved;

    /**
     * Constructor to initialize the data
     * 
     * @param populationSize: Size of the population used
     * @param startingBoard: Starting board used
     * @param killPercentage: The percentage of individuals killed from the population 
     * @param mutateProb: The probability for a mutation to occur
     * @param genTermination: The generation in which the run terminates at
     * @param run: The Run which is being executed
     * @param terminated: True if run terminated, false if it didn't
     * @param bestFitness The best fitness in the final population
     * @param worstFitness The worst Fitness in the final population
     * @param generationSolved: The generation that was reached at the solution
     * @param solution: The solution that was found
     */
    protected void saveData(int populationSize, int[][] startingBoard, double killPercentage, int mutateProb,
            int genTermination, int run, boolean terminated, int bestFitness, int worstFitness, int generationSolved,
            Individual solution) {
        this.populationSize = populationSize;
        this.startingBoard = startingBoard;
        this.killPercentage = killPercentage;
        this.mutateProb = mutateProb;
        this.genTermination = genTermination;
        this.run = run;
        this.terminated = terminated;
        this.bestFitness = bestFitness;
        this.worstFitness = worstFitness;
        this.generationSolved = generationSolved;
        this.solution = solution;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Run: " + run + "\n");
        sb.append("Starting Board: \n" + showBoard(startingBoard));

        // Show the solution found only if the run didn't terminate
        if (!terminated) {
            sb.append("solved Board: \n" + solution);
            sb.append("Solved at generation: " + generationSolved + "\n");
        } else {
            sb.append("Terminated at generation: " + genTermination + "\n");
        }

        sb.append("Best Fitness Score: " + bestFitness + "\n");
        sb.append("Worst Fitness Score: " + worstFitness + "\n");
        sb.append("Population size: " + populationSize + "\n");
        sb.append("Kill percentage: " + killPercentage + "\n");
        sb.append("Mutation probability: " + mutateProb + "\n");
        return sb.toString();
    }

    /**
     * Prints the board out 
     *
     * @param board
     * @return The board print out as a String
     */
    private String showBoard(int[][] board) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                result.append(board[i][j] + ", ");
                if ((j + 1) % 9 == 0) {
                    result.append("\n");
                }
            }
        }
        return result.toString();
    }
}
