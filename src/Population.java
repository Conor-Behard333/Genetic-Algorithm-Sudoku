import java.util.Arrays;

public class Population {
    Individual[] individuals;

    Population(int size) {
        individuals = new Individual[size];
        for (int i = 0; i < individuals.length; i++) {
            individuals[i] = new Individual();
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
}
