package RBFnetwork;

import java.util.Random;

public class Layer {
	private double net;
	public double entrada[];
	public double saida;
	
	/**
	 * Peso que cada entrada irá possuir no neuronio 
	 * Ou sej se exisitr 4 entradas o neuronio terá um vetor 
	 * peso com quatro posicoes, um para cada entrada
	 */
	public double peso[];
	
	public double[] neurons;
	
	private static int MAX_RAND = 1000001;
	
	private static double MAX = 0.5;
	private static double MIN = -0.5;

	public Layer(int numeroDeNos, int numeroDeEntradas) {
		this.neurons = new double[numeroDeNos];

		for (int i = 0; i < numeroDeNos; i++) {
			this.peso[i] = numeroDeEntradas;
		}
		this.entrada = new double[numeroDeEntradas];
	}
	
	/**
	 * Gera um número aleatório que será utilizado para os pesos e limiar
	 * @return
	 */
	static double gerarNumero(){
		Random random = new Random();
		double x = random.nextInt(MAX_RAND);
		double valor = MIN + (x / MAX_RAND)*(MAX-(MIN));
		return valor;
	}
}
