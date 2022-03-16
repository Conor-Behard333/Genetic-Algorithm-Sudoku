import java.util.ArrayList;
import java.util.Arrays;

public class Population {
    private static int generation = 0;
    private Individual[] individuals;
    private int[][] startingBoard;
    private ArrayList<Individual> parents = new ArrayList<>();

    private final double KILL_PERCENTAGE;
    private final int MUTATION_PROB;

    Population(int size, int[][] startingBoard, double killPercentage, int mutateProb) {
        this.startingBoard = startingBoard;
        KILL_PERCENTAGE = killPercentage;
        MUTATION_PROB = mutateProb;
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
     * This is random Selection
     */
    public void selectParents() {
        int popSize = individuals.length;
        int remove = (int) (popSize * ((double) KILL_PERCENTAGE));

        // Kill a part of the population
        kill(popSize, remove);

        // currently picks best parents
        int count = 0;
        for (int i = 0; i < individuals.length; i++) {
            if (count < popSize * 0.001) {
                if (individuals[i] != null) {
                    parents.add(individuals[i]);
                    count++;
                }
            } else {
                if (individuals[i] != null) {
                    parents.add(individuals[i]);
                    individuals[i] = null;
                }
            }
        }
    }

    private void kill(int popSize, int remove) {
        for (int i = individuals.length - 1; i >= individuals.length - remove; i--) {
            individuals[i] = null;
        }
    }

    /**
     * row crossover
     */
    public void crossover() {
        while (getNullIndex() != -1) {
            int parent1Index = Rand.randomInt(parents.size() - 1, 0);
            int parent2Index = Rand.randomInt(parents.size() - 1, 0);
            while (parent1Index == parent2Index) {
                parent1Index = Rand.randomInt(parents.size() - 1, 0);
            }

            int[] p1Chromosome = parents.get(parent1Index).getBoard().getChromosome();
            int[] p2Chromosome = parents.get(parent2Index).getBoard().getChromosome();

            Individual child1 = new Individual(startingBoard, true);
            Individual child2 = new Individual(startingBoard, true);

            int[] child1Chromosome = null;
            int[] child2Chromosome = null;

            int randNum = Rand.randomInt(3, 1);
            if (randNum == 1) {
                child1Chromosome = Crossover.crossoverRandomRow(child1, p1Chromosome, p2Chromosome, true);
                child2Chromosome = Crossover.crossoverRandomRow(child2, p1Chromosome, p2Chromosome, false);
            } else if (randNum == 2) {
                child1Chromosome = Crossover.crossoverRow3(child1, p1Chromosome, p2Chromosome, true);
                child2Chromosome = Crossover.crossoverRow3(child2, p1Chromosome, p2Chromosome, false);
            } else {
                child1Chromosome = Crossover.crossoverMultipleRows(child1, p1Chromosome, p2Chromosome, true);
                child2Chromosome = Crossover.crossoverMultipleRows(child2, p1Chromosome, p2Chromosome, false);
            }
            
            mutate(child1Chromosome, MUTATION_PROB, child1, 1);
            mutate(child2Chromosome, MUTATION_PROB, child2, 1);

            setChildGenes(child1, child1Chromosome);
            setChildGenes(child2, child2Chromosome);

            addChildToPop(child1);
            addChildToPop(child2);
        }
        sortIndividuals();
        Population.generation++;
    }
    
    private void addChildToPop(Individual child) {
        int nullIndex = getNullIndex();
        if (nullIndex != -1) {
            individuals[nullIndex] = child;            
        }
    }

    private void setChildGenes(Individual child, int[] childGene) {
        child.getBoard().setChromosome(childGene);
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


    private void mutate(int[] childGenes, int mutationProb, Individual child, int numOfMutations) {
        if (Rand.randomInt(99, 0) + 1 < mutationProb) {
            int randRow = Rand.randomInt(8, 0) * 9;
            int randIndex1 = Rand.randomInt(8, 0) + randRow;
            int randIndex2 = Rand.randomInt(8, 0)+ randRow;
            while (!child.getBoard().isAllowedToChange(randIndex1) || !child.getBoard().isAllowedToChange(randIndex2)) {
                randIndex1 = Rand.randomInt(8, 0) + randRow;
                randIndex2 = Rand.randomInt(8, 0) + randRow;
            }
            
            int temp = childGenes[randIndex1];
            childGenes[randIndex1] = childGenes[randIndex2];
            childGenes[randIndex2] = temp;

        }
    }

    public void displayBest() {
        System.out.println(individuals[0]);
    }

    public void displayWorst() {
        System.out.println(individuals[individuals.length - 1]);
    }

    public int getBestScore() {
        return individuals[0].getFitnessScore();
    }

    public int getGeneration() {
        return generation;
    }

    public void resetGeneration() {
        generation = 0;
    }

    public int getWorstScore() {
        return individuals[individuals.length - 1].getFitnessScore();
    }
}
