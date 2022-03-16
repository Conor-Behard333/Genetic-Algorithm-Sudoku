public class Crossover {

    protected static int[] crossoverRandomRow(Individual child, int[] p1Chromosome, int[] p2Chromosome, boolean p1) {
        int randomRow = Rand.randomInt(8, 0) * 9;
        int[] childChromosome = child.getBoard().getChromosome();
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
    
    protected static int[] crossoverRow3(Individual child, int[] p1Chromosome, int[] p2Chromosome, boolean p1) {
        int randomRow = Rand.randomInt(8, 1) * 9;
        int[] childChromosome = child.getBoard().getChromosome();
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
    
    protected static int[] crossoverMultipleRows(Individual child, int[] p1Chromosome, int[] p2Chromosome, boolean p1) {
        int[] childChromosome = child.getBoard().getChromosome();
        for (int i = 0; i < childChromosome.length; i++) {
            if (i % 9 == 0 && i != 0) {
                int randNum = Rand.randomInt(2, 1);
                p1 = randNum == 1;
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
