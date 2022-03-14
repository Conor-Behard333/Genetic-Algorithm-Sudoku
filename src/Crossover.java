public class Crossover {

    protected static int[] crossoverGrid(Individual child, int[] p1Chromosome, int[] p2Chromosome,
            boolean p1) {
        int[] childChromosome = child.getBoard().getChromosome();
        int index = 0;
        for (int start = 0; start < 81; start += 3) {
            if (start % 9 == 0) {
                p1 = true;
            }
            for (int i = start; i < start + 3; i++) {
                if (p1) {
                    childChromosome[index] = p1Chromosome[i];
                } else {
                    childChromosome[index] = p2Chromosome[i];
                }
                index++;
            }
            p1 = !p1;
        }
        return childChromosome;
    }

     protected static int[] crossoverColumn(Individual child, int[] p1Chromosome, int[] p2Chromosome,
            boolean p1) {
        int[] childChromosome = child.getBoard().getChromosome();
        int index = 0;
        for (int i = 0; i < 81; i++) {
            if (i % 2 == 0) {
                childChromosome[index] = p1Chromosome[i];
            } else {
                childChromosome[index] = p2Chromosome[i];
            }
            index++;
        }
        return childChromosome;
    }

    public String toString(int[] arr) {
      StringBuilder result = new StringBuilder();
      for (int i = 0; i < arr.length; i++) {
          result.append(arr[i] + ", ");
          if ((i + 1) % 9 == 0) {
              result.append("\n");
          }
      }
      return result.toString();
  }
    
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
