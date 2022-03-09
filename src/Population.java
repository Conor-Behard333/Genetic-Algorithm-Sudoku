import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Population {
    private Individual[] individuals;
    private int[][] startingBoard;
    private ArrayList<Individual> parents = new ArrayList<>();

    Population(int size, int[][] startingBoard) {
        this.startingBoard = startingBoard;
        individuals = new Individual[size];
        for (int i = 0; i < individuals.length; i++) {
            individuals[i] = new Individual(startingBoard, false);
        }
        sortIndividuals();
    }

    public void displayPopulation() {
        for (int i = 0; i < individuals.length; i++) {
            System.out.println(individuals[i]);
        }
    }

    public void sortIndividuals() {
        Arrays.sort(individuals, Individual::compareFitnessScore);
    }

    /*
     * Selection techniques:
     * - Roulette Wheel Selection
     * - Rank Selection
     * - Steadt State Selection 
     * - Tournament Selection
     * - Elitism Selection
     * - Boltzmann Selection
     * - https://en.wikipedia.org/wiki/Selection_(genetic_algorithm)
     * 
     * This is Rank Selection
     */
    public void selectParents() {
        int parentsNeeded = kill(0.3); // Kill 60 percent of the population returns the number of individuals killed

        //currently picks best parents
        for (int i = 0; i < parentsNeeded; i++) {
            if (individuals[i] != null) {
                parents.add(individuals[i]);                
            }
        }
    }

    private int kill(double percentage) {
        int popSize = individuals.length;
        int remove = (int) (popSize * ((double) percentage));
        for (int i = individuals.length - 1; i >= individuals.length - remove; i--) {
            individuals[i] = null;
        }
        return remove;
    }

    /**
     * K-point crossover:
     * https://en.wikipedia.org/wiki/Crossover_(genetic_algorithm)
     */
    public void crossover(int k) {
        
        for (int i = 0; i < parents.size(); i += 2) {
            int[] p1Gene = parents.get(i).getBoard().getGene();
            int[] p2Gene = parents.get(i + 1).getBoard().getGene();
            int[] crossoverPoints = getCrossoverPoints(k, p1Gene.length);

            Individual child1 = new Individual(startingBoard, true);
            Individual child2 = new Individual(startingBoard, true);

            int[] child1Gene = getChildGenes(child1, crossoverPoints, p1Gene, p2Gene);
            // int[] child2Gene = getChildGenes(child2, crossoverPoints, p1Gene, p2Gene);

            setChildGenes(child1, child1Gene);
            // setChildGenes(child2, child2Gene);
            
            addChildToPop(child1);
            // addChildToPop(child2);   
        }
        sortIndividuals();
    }

    private int[] getCrossoverPoints(int k, int geneLength) {
        Random rand = new Random();
        int[] crossoverPoints = new int[k];
        for (int i = 0; i < crossoverPoints.length; i++) {
            int randNum = rand.nextInt((geneLength - 2) + 1) + 1;
            if (contains(crossoverPoints, randNum)) {
                i--;
                continue;
            }
            crossoverPoints[i] = rand.nextInt((geneLength - 2) + 1) + 1;
        }
        Arrays.sort(crossoverPoints);
        return crossoverPoints;
    }

    private boolean contains(int[] crossoverPoints, int randNum) {
        for (int i = 0; i < crossoverPoints.length; i++) {
            if (crossoverPoints[i] == randNum) {
                return true;
            }
        }
        return false;
    }

    private int[] getChildGenes(Individual child, int[] crossoverPoint, int[] p1Gene, int[] p2Gene) {
        boolean p1 = true;
        int[] childGene = child.getBoard().getGene();
        for (int i = 0; i < crossoverPoint.length; i++) {
            if (i == 0) {
                for (int j = 0; j < crossoverPoint[i] + 1; j++) {
                    childGene[j] = p1Gene[j];
                    p1 = false;
                }
            } else {
                for (int j = crossoverPoint[i - 1]; j < crossoverPoint[i]; j++) {
                    if (p1) {
                        childGene[j] = p2Gene[j];                        
                    } else {
                        childGene[j] = p1Gene[j];
                    }
                }
            }
            p1 = !p1;
        }
        p1 = !p1;
        for (int i = crossoverPoint[crossoverPoint.length - 1]; i < childGene.length; i++) {
            if (p1) {
                childGene[i] = p1Gene[i];
            } else {
                childGene[i] = p2Gene[i];
            }
        }
        return childGene;
    }
    
    private void addChildToPop(Individual child) {
        int nullIndex = getNullIndex();
        if (nullIndex != -1) {
            individuals[nullIndex] = child;            
        }
    }

    private void setChildGenes(Individual child, int[] childGene) {
        child.getBoard().setGene(childGene);
        child.setFitness();
    }

    private int getNullIndex() {
        for (int i = 0; i < individuals.length; i++) {
            if (individuals[i] == null) {
                return i;
            }
        }
        return -1;
    }

    public void mutate() {
    }
    
    private int getFitnessSum() {
        int sum = 0;
        for (int i = 0; i < individuals.length; i++) {
            if (individuals[i] == null) {
                break;
            }
            sum += individuals[i].getFitnessScore();
        }
        return sum;
    }

    public void displayBest() {
        System.out.println(individuals[0]);
    }
}
