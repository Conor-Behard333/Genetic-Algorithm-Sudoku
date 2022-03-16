public class Crossover {

    /**
     * Crossover technique 1:
     * Picks a random row from one parent and swaps it with the other parent.
     * The result is the child.
     * 
     * @param child: Child being created
     * @param p1Chromosome Chromosome for parent 1
     * @param p2Chromosome: Chromosome for parent 2
     * @param p1: true if p1 is being swapped, false if p2 is being swapped
     * @return The child's chromosome
     */
    protected static int[] crossoverRandomRow(Individual child, int[] p1Chromosome, int[] p2Chromosome, boolean p1) {
        int randomRow = Rand.randomInt(8, 0) * 9;
        int[] childChromosome = child.getChromosome().getChromosomeAsArray();
        for (int i = 0; i < childChromosome.length; i++) {
            if (p1) {
                if (i >= randomRow && i <= randomRow + 9) {
                    childChromosome[i] = p2Chromosome[i];
                } else {
                    childChromosome[i] = p1Chromosome[i];
                }
            } else {
                if (i >= randomRow && i <= randomRow + 9) {
                    childChromosome[i] = p1Chromosome[i];
                } else {
                    childChromosome[i] = p2Chromosome[i];
                }
            }

        }
        return childChromosome;
    }
    
    /**
     * Crossover technique 2:
     * Picks a random row, all rows upto this row are from one parent and the rest 
     * of the rows are from the other parent.
     * The result is the child. 
     * 
     * @param child: Child being created
     * @param p1Chromosome Chromosome for parent 1
     * @param p2Chromosome: Chromosome for parent 2
     * @param p1: true if p1 is being swapped, false if p2 is being swapped
     * @return The child's chromosome
     */
    protected static int[] crossoverRowChunks(Individual child, int[] p1Chromosome, int[] p2Chromosome, boolean p1) {
        int randomRow = Rand.randomInt(8, 1) * 9;
        int[] childChromosome = child.getChromosome().getChromosomeAsArray();
        for (int i = 0; i < childChromosome.length; i++) {
            if (p1) {
                if (i >= randomRow) {
                    childChromosome[i] = p2Chromosome[i];
                } else {
                    childChromosome[i] = p1Chromosome[i];
                }
            } else {
                if (i >= randomRow) {
                    childChromosome[i] = p1Chromosome[i];
                } else {
                    childChromosome[i] = p2Chromosome[i];
                }
            }
        }

        return childChromosome;
    }

    /**
     * Crossover technique 3:
     * Goes through each row and generates a random number that is either
     * 1 or 0, if it is 1 then the row from parent 1 is used if it is 0
     * then the row from parent 2 is used.
     * The result is the child.
     * 
     * @param child: Child being created
     * @param p1Chromosome Chromosome for parent 1
     * @param p2Chromosome: Chromosome for parent 2
     * @param p1: true if p1 is being swapped, false if p2 is being swapped
     * @return The child's chromosome
     */
    protected static int[] crossoverMultipleRows(Individual child, int[] p1Chromosome, int[] p2Chromosome, boolean p1) {
        int[] childChromosome = child.getChromosome().getChromosomeAsArray();
        for (int i = 0; i < childChromosome.length; i++) {
            if (i % 9 == 0 && i != 0) {
                int randNum = Rand.randomInt(2, 1);
                // if the random number is 1 use parent 1 otherwise use parent 2
                p1 = (randNum == 1);
            }
            if (p1) {
                childChromosome[i] = p1Chromosome[i];
            } else {
                childChromosome[i] = p2Chromosome[i];
            }
        }
        return childChromosome;
    }
}
