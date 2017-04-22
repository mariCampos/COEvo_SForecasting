package COEvo;
/**
 * Name: Individual
 * Goal: abstract class to represent a gene (individual) in a population. This
 *       class ensures compatibility with the genetic algorithm
 * Attributes:
 *      - dna: dimensional matrix of doubles to represent  genome
 *      - fitness: indicates the gene's fitness value
 * Methods:
 *      - mutate: enables a gene to mutate
 *      - crossover: enables a gene to cross its DNA with another
 *      - makeRandom: generates a random dna
 * @author Numa Trezzini
 */
public class IndividualLag {
	private double[][] dna;
	private double fitness;
	
	public double[][] getDna() {
		return dna;
	}
	public void setDna(double[][] dna) {
		this.dna = dna;
	}
	public double getFitness() {
		return fitness;
	}
	public void setFitness(double fitness) {
		this.fitness = fitness;
	}
	
	
}
