public class Crossover {

    
    
    protected static int[] crossoverRow(Individual child, int[] p1Chromosome, int[] p2Chromosome, boolean p1) {
        int[] crossoverPoints = new int[] { 8, 17, 26, 35, 44, 53, 62, 71 };
        int[] childChromosome = child.getBoard().getChromosome();
        for (int i = 0; i < crossoverPoints.length; i++) {
            if (i == 0) {
                for (int j = 0; j < crossoverPoints[i] + 1; j++) {
                    if (p1) {
                        childChromosome[j] = p1Chromosome[j];
                    } else {
                        childChromosome[j] = p2Chromosome[j];
                    }
                }
                p1 = !p1;
            } else {
                for (int j = crossoverPoints[i - 1]; j < crossoverPoints[i]; j++) {
                    if (p1) {
                        childChromosome[j] = p2Chromosome[j];
                    } else {
                        childChromosome[j] = p1Chromosome[j];
                    }
                }
            }
            p1 = !p1;
        }
        for (int i = crossoverPoints[crossoverPoints.length - 1]; i < childChromosome.length; i++) {
            if (!p1) {
                childChromosome[i] = p1Chromosome[i];
            } else {
                childChromosome[i] = p2Chromosome[i];
            }
        }
        return childChromosome;
    }
    
}
