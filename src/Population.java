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
     * Applies crossover to the parents to obtain two children and mutates the children 
     */
    public void crossoverAndMutate() {
        while (getNullIndex() != -1) {
            int[][] parentsChromosomes = getParents();

            // Create children
            Individual[] children = { new Individual(startingBoard, true), new Individual(startingBoard, true) };
            int[][] childrenChromosomes = { null, null };

            crossover(parentsChromosomes, children, childrenChromosomes);

            mutateAndAddToPopulation(children, childrenChromosomes);

        }
        sortIndividuals();
        Population.generation++;
    }

    /**
     * Mutate the children then add them to the population
     * 
     * @param children: The children to be mutated/added
     * @param childrenChromosomes: The chromosomes that belong to the children
     */
    private void mutateAndAddToPopulation(Individual[] children, int[][] childrenChromosomes) {
        for (int i = 0; i < childrenChromosomes.length; i++) {
            mutate(childrenChromosomes[i], MUTATION_PROB, children[i]);
            setChildChromosomes(children[i], childrenChromosomes[i]);
            addChildToPop(children[i]);
        }
    }

    /**
     * Gets the parents that will create two children
     * Ensures that the same parent is picked twice
     * 
     * @return the parents
     */
    private int[][] getParents() {
        int parent1Index = Rand.randomInt(parents.size() - 1, 0);
        int parent2Index = Rand.randomInt(parents.size() - 1, 0);
        while (parent1Index == parent2Index) {
            parent1Index = Rand.randomInt(parents.size() - 1, 0);
        }
        int[][] parentsChromosomes = { parents.get(parent1Index).getChromosome().getChromosomeAsArray(),
                parents.get(parent2Index).getChromosome().getChromosomeAsArray()
        };
        return parentsChromosomes;
    }

    /**
     * Performs the crossover operations on the parents to obtain the children
     * Randomly performs either of three crossovers to the parents 
     * 
     * @param parentsChromosomes: The parent's chromosomes
     * @param children: The Children
     * @param childrenChromosomes: The Children's chromosomes 
     */
    private void crossover(int[][] parentsChromosomes, Individual[] children, int[][] childrenChromosomes) {
        boolean p1 = true;
        int randNum = Rand.randomInt(3, 1);
        if (randNum == 1) {
            for (int i = 0; i < childrenChromosomes.length; i++) {
                childrenChromosomes[i] = Crossover.crossoverRandomRow(children[i], parentsChromosomes[0],
                        parentsChromosomes[1], p1);
                p1 = !p1;
            }
        } else if (randNum == 2) {
            for (int i = 0; i < childrenChromosomes.length; i++) {
                childrenChromosomes[i] = Crossover.crossoverRowChunks(children[i], parentsChromosomes[0],
                        parentsChromosomes[1], p1);
                p1 = !p1;
            }
        } else {
            for (int i = 0; i < childrenChromosomes.length; i++) {
                childrenChromosomes[i] = Crossover.crossoverMultipleRows(children[i], parentsChromosomes[0],
                        parentsChromosomes[1], p1);
                p1 = !p1;
            }
        }
    }

    /**
     * Adds a child to the population 
     * 
     * @param child to be added to the population
     */
    private void addChildToPop(Individual child) {
        int nullIndex = getNullIndex();
        if (nullIndex != -1) {
            individuals[nullIndex] = child;
        }
    }

    /**
     * Sets the chromosome for a child
     * 
     * @param child The child
     * @param childChromosome The chromosome
     */
    private void setChildChromosomes(Individual child, int[] childChromosome) {
        child.getChromosome().setChromosome(childChromosome);
        child.setFitness();
    }

    /**
     * @return The index of an individual with a null value
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
     * Chance to mutate a child
     * Mutation consists of a single swap of two numbers on a random row
     * 
     * @param childGenes: the genes for the child
     * @param mutationProb: the probability that mutation occurs
     * @param child: the child being mutated
     */
    private void mutate(int[] childGenes, int mutationProb, Individual child) {
        if (Rand.randomInt(99, 0) + 1 < mutationProb) {
            int randRow = Rand.randomInt(8, 0) * 9;
            int randIndex1 = Rand.randomInt(8, 0) + randRow;
            int randIndex2 = Rand.randomInt(8, 0) + randRow;

            /*
             Ensure that the genes being swapped are allowed to be
             keep generating them until they can be swapped
            */ 
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
     * @return the best fitness score
     */
    public int getBestScore() {
        return individuals[0].getFitnessScore();
    }

    /**
     * @return the current generation number
     */
    public int getGeneration() {
        return generation;
    }

    /**
     * Reset the generation number
     */
    public void resetGeneration() {
        generation = 0;
    }

    /**
     * @return the worst score
     */
    public int getWorstScore() {
        return individuals[individuals.length - 1].getFitnessScore();
    }

    /**
     * @return the best individual
     */
    public Individual getBestIndividual() {
        return individuals[0];
    }
}