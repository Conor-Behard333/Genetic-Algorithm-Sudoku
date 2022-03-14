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
            if (count < popSize * 0.01 ) {
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
     * https://en.wikipedia.org/wiki/Crossover_(genetic_algorithm)
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
            Individual child3 = new Individual(startingBoard, true);
            Individual child4 = new Individual(startingBoard, true);
            Individual child5 = new Individual(startingBoard, true);
            Individual child6 = new Individual(startingBoard, true);

            int[] child1Chromosome = null;
            int[] child2Chromosome = null;
            int[] child3Chromosome = null;
            int[] child4Chromosome = null;
            int[] child5Chromosome = null;
            int[] child6Chromosome = null;

            child1Chromosome = Crossover.crossoverColumn(child1, p1Chromosome, p2Chromosome, true);
            child2Chromosome = Crossover.crossoverColumn(child2, p1Chromosome, p2Chromosome, false);
            child3Chromosome = Crossover.crossoverRow(child3, p1Chromosome, p2Chromosome, true);
            child4Chromosome = Crossover.crossoverRow(child4, p1Chromosome, p2Chromosome, false);
            child5Chromosome = Crossover.crossoverGrid(child5, p1Chromosome, p2Chromosome, true);
            child6Chromosome = Crossover.crossoverGrid(child6, p1Chromosome, p2Chromosome, false);

            mutate(child1Chromosome, MUTATION_PROB, child1, 1);
            mutate(child2Chromosome, MUTATION_PROB, child2, 1);
            mutate(child3Chromosome, MUTATION_PROB, child3, 1);
            mutate(child4Chromosome, MUTATION_PROB, child4, 1);
            mutate(child5Chromosome, MUTATION_PROB, child5, 1);
            mutate(child6Chromosome, MUTATION_PROB, child6, 1);

            setChildGenes(child1, child1Chromosome);
            setChildGenes(child2, child2Chromosome);
            setChildGenes(child3, child3Chromosome);
            setChildGenes(child4, child4Chromosome);
            setChildGenes(child5, child5Chromosome);
            setChildGenes(child6, child6Chromosome);

            Individual[] children = { child1, child2, child3, child4, child5, child6 };
            Arrays.sort(children, Individual::compareFitnessScore);

            while (children[0].getFitnessScore() == children[5].getFitnessScore()) {
                mutate(children[5].getBoard().getChromosome(), 100, children[5], 2);
                setChildGenes(children[5], children[5].getBoard().getChromosome());
            }

            addChildToPop(children[0]);
            addChildToPop(children[5]);
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


    /**
     * Random Resetting:             
     */
    private void mutate(int[] childGenes, int mutationProb, Individual child, int numOfMutations) {
        if (Rand.randomInt(99, 0) + 1 < mutationProb) {
            for (int i = 0; i < numOfMutations; i++) {
                int randPos = Rand.randomInt(childGenes.length, 0);
                int randNum = Rand.randomInt(9, 1);
                while (!child.getBoard().isAllowedToChange(randPos)) {
                    randPos = Rand.randomInt(childGenes.length, 0);
                }
                childGenes[randPos] = randNum;
            }
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
}
