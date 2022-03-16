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
     * @param populationSize
     * @param startingBoard
     * @param killPercentage
     * @param mutateProb
     * @param genTermination
     * @param run
     * @param terminated
     * @param bestFitness
     * @param worstFitness
     * @param generationSolved
     */
    protected void saveData(int populationSize, int[][] startingBoard, double killPercentage, int mutateProb,
            int genTermination, int run, boolean terminated, int bestFitness, int worstFitness, int generationSolved, Individual solution) {
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

        if (terminated) {
            sb.append("Terminated at generation: " + genTermination + "\n");
        } else {
            sb.append("solved Board: \n" + solution);
            sb.append("Solved at generation: " + generationSolved + "\n");
        }

        sb.append("Best Fitness Score: " + bestFitness + "\n");
        sb.append("Worst Fitness Score: " + worstFitness + "\n");
        sb.append("Population size: " + populationSize + "\n");
        sb.append("Kill percentage: " + killPercentage + "\n");
        sb.append("Mutation probability: " + mutateProb + "\n");
        return sb.toString();
    }
    
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

    /**
     * @return the populationSize
     */
    public int getPopulationSize() {
        return populationSize;
    }

    /**
     * @return the startingBoard
     */
    public int[][] getStartingBoard() {
        return startingBoard;
    }

    /**
     * @return the killPercentage
     */
    public double getKillPercentage() {
        return killPercentage;
    }

    /**
     * @return the mutateProb
     */
    public int getMutateProb() {
        return mutateProb;
    }

    /**
     * @return the genTermination
     */
    public int getGenTermination() {
        return genTermination;
    }

    /**
     * @return the run
     */
    public int getRun() {
        return run;
    }

    /**
     * @return the terminated
     */
    public boolean terminated() {
        return terminated;
    }

    /**
     * @return the bestFitness
     */
    public int getBestFitness() {
        return bestFitness;
    }

    /**
     * @return the worstFitness
     */
    public int getWorstFitness() {
        return worstFitness;
    }

    /**
     * @return the generationSolved
     */
    public int getGenerationSolved() {
        return generationSolved;
    }

}
