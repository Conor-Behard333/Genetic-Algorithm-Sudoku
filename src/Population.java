import java.util.ArrayList;
import java.util.Arrays;

public class Population {
    private static int generation = 0;
    private Individual[] individuals;
    private int[][] startingBoard;
    private ArrayList<Individual> parents = new ArrayList<>();
    private final double KILL_PERCENTAGE;
    private final int MUTATION_PROB;

    /**
     * Initializes a population 
     * 
     * @param size: The size of the population
     * @param startingBoard: The starting board for this population
     * @param killPercentage: The percentage of individuals removed from the population
     * @param mutateProb: The probability that an individual will mutate
     */
    Population(int size, int[][] startingBoard, double killPercentage, int mutateProb) {
        this.startingBoard = startingBoard;
        KILL_PERCENTAGE = killPercentage;
        MUTATION_PROB = mutateProb;

        // Initialize individuals
        individuals = new Individual[size];
        for (int i = 0; i < individuals.length; i++) {
            individuals[i] = new Individual(startingBoard, false);
        }

        sortIndividuals();
    }

    
    /**
     * Sorts the individuals by their fitness score
     */
    public void sortIndividuals() {
        Arrays.sort(individuals, Individual::compareFitnessScore);
    }

    /*
     * Select parents to create the next generation.
     * First removes a percentage of the population.
     * Of the remaining population the individuals are added to the parent list
     * A very small amount of parents are kept in the population to ensure a good
     * individual is in the next generation
     * 
     */
    public void selectParents() {
        int popSize = individuals.length;
        int remove = (int) (popSize * ((double) KILL_PERCENTAGE));

        // Kill a part of the population
        kill(popSize, remove);

        // Picks the best individuals to be parents
        int count = 0;
        for (int i = 0; i < individuals.length; i++) {
            // 0.001% of the population is kept to continue on to the next generation
            if (count < popSize * 0.001) {
                if (individuals[i] != null) {
                    parents.add(individuals[i]);
                    count++;
                }
            } else {
                if (individuals[i] != null) {
                    parents.add(individuals[i]);
                    individuals[i] = null; // Removes parent from the population
                }
            }
        }
    }

    /**
     * Removes a number of individuals from the population
     * 
     * @param popSize: The population size
     * @param remove: The number of individuals to remove
     */
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
            // int[][] parentsChromosomes = { parents.get(parent1Index).getChromosome().getChromosomeAsArray(),
            //                     parents.get(parent2Index).getChromosome().getChromosomeAsArray()
            // };
            int[] p1Chromosome = parents.get(parent1Index).getChromosome().getChromosomeAsArray();
            int[] p2Chromosome = parents.get(parent2Index).getChromosome().getChromosomeAsArray();

            Individual child1 = new Individual(startingBoard, true);
            Individual child2 = new Individual(startingBoard, true);

            int[] child1Chromosome = null;
            int[] child2Chromosome = null;

            int randNum = Rand.randomInt(3, 1);
            if (randNum == 1) {
                child1Chromosome = Crossover.crossoverRandomRow(child1, p1Chromosome, p2Chromosome, true);
                child2Chromosome = Crossover.crossoverRandomRow(child2, p1Chromosome, p2Chromosome, false);
            } else if (randNum == 2) {
                child1Chromosome = Crossover.crossoverRowChunks(child1, p1Chromosome, p2Chromosome, true);
                child2Chromosome = Crossover.crossoverRowChunks(child2, p1Chromosome, p2Chromosome, false);
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

    /**
     * 
     * @param child
     */
    private void addChildToPop(Individual child) {
        int nullIndex = getNullIndex();
        if (nullIndex != -1) {
            individuals[nullIndex] = child;
        }
    }

    /**
     * 
     * @param child
     * @param childGene
     */
    private void setChildGenes(Individual child, int[] childGene) {
        child.getChromosome().setChromosome(childGene);
        child.setFitness();
    }

    /**
     * 
     * @return
     */
    private int getNullIndex() {
        for (int i = 0; i < individuals.length; i++) {
            if (individuals[i] == null) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 
     * @param childGenes
     * @param mutationProb
     * @param child
     * @param numOfMutations
     */
    private void mutate(int[] childGenes, int mutationProb, Individual child, int numOfMutations) {
        if (Rand.randomInt(99, 0) + 1 < mutationProb) {
            int randRow = Rand.randomInt(8, 0) * 9;
            int randIndex1 = Rand.randomInt(8, 0) + randRow;
            int randIndex2 = Rand.randomInt(8, 0) + randRow;
            while (!child.getChromosome().isAllowedToChange(randIndex1)
                    || !child.getChromosome().isAllowedToChange(randIndex2)) {
                randIndex1 = Rand.randomInt(8, 0) + randRow;
                randIndex2 = Rand.randomInt(8, 0) + randRow;
            }

            int temp = childGenes[randIndex1];
            childGenes[randIndex1] = childGenes[randIndex2];
            childGenes[randIndex2] = temp;

        }
    }

    /**
     * 
     */
    public void displayBest() {
        System.out.println(individuals[0]);
    }

    /**
     * 
     */
    public void displayWorst() {
        System.out.println(individuals[individuals.length - 1]);
    }

    /**
     * 
     * @return
     */
    public int getBestScore() {
        return individuals[0].getFitnessScore();
    }

    /**
     * 
     * @return
     */
    public int getGeneration() {
        return generation;
    }

    /**
     * 
     */
    public void resetGeneration() {
        generation = 0;
    }

    /**
     * 
     * @return
     */
    public int getWorstScore() {
        return individuals[individuals.length - 1].getFitnessScore();
    }

    /**
     * 
     * @return
     */
    public Individual getBestIndividual() {
        return individuals[0];
    }
}
