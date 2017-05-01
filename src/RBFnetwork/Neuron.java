package RBFnetwork;

public class Neuron {
	double[] weight;
	private double output;
    double center[];
    double sigma;
	
	//variavéis para cálculo da saída do neurônio
	private double limiar;
	private double erro;
	double[] diferencaPeso;
	
	public Neuron(int numberInputs){
		
	}

	public double getOutput() {
		return output;
	}

	public void setOutput(double output) {
		this.output = output;
	}

	public double getLimiar() {
		return limiar;
	}

	public void setLimiar(double limiar) {
		this.limiar = limiar;
	}

	public double getErro() {
		return erro;
	}

	public void setErro(double erro) {
		this.erro = erro;
	}

	
	
}
