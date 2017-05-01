package RBFnetwork;
import java.util.*;

public class Layer {
	
	private double net;
	public double[] input;
	public Neuron[] neurons;
	
	private static int MAX_RAND = 1000001;
	private static double MAX = 0.5;
	private static double MIN = -0.5;
	
	public Layer(int qtdNeurons, int numberInputs){
		this.neurons = new Neuron[qtdNeurons];
		for (int i = 0; i < qtdNeurons; i++) {
			this.neurons[i] = new Neuron(numberInputs);
		}
		this.input = new double[numberInputs];
	}
	
	public double generateWeight(){
		Random rand = new Random();
		double x = rand.nextInt(MAX_RAND);
		double valor = MIN + (x / MAX_RAND)*(MAX - (MIN));
		
		return valor;
	}
	
	public void InitializeWeight(){
		
		for(int i = 0; i < this.neurons.length; i++){
			for(int j = 0; j < this.neurons[i].weight.length; j++){
				this.neurons[i].weight[j] = generateWeight();
				this.neurons[i].diferencaPeso[j] = 0;
			}
		}
	}
	
	public double[] calculateOutput(double net){
		double[] y = new double[0];
		
		return y;
	}

	public Neuron[] getNeurons() {
		return neurons;
	}

	public void setNeurons(Neuron[] neurons) {
		this.neurons = neurons;
	}
	
	
	
	
}
