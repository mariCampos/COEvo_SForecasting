package RBFnetwork;

import java.util.ArrayList;

public class BackPropagation extends Thread{
	private double saidaEsperada[][];
	private double entrada[][];
	private double taxaAprendizado;
	private int numeroCamadas;	
	private long numMaxInteracoes;
	private Layer[] camadas;
	private double[][] pesoSaida;
	private double[] limiarSaida;
	private double[][] matrizConfusao;
	private ArrayList<Integer> resultado;


	long delay = 0;
	boolean die = false;
	
	public BackPropagation(int numNos[], double entrada[][], double saida[][], double taxaAprendizado, long maxInteracoes) {
		
		this.setTaxaAprendizado(taxaAprendizado);
		this.setNumeroCamadas(numNos.length);
		this.setNumMaxInteracoes(maxInteracoes);
		
		this.camadas = new Layer[this.numeroCamadas];
		
		//A primeira camada tem como entrada os valores da IRIS
		this.camadas[0] = new Layer(numNos[0], entrada[0].length);

		// determinar o numero de nos em cada camada
		for (int i = 1; i < this.numeroCamadas; i++){
			//as camadas intermediarias tem como entrada a saida da camada anterior
			this.camadas[i] = new Layer(numNos[i], numNos[i - 1]);
		}
		
		this.entrada = entrada;
		this.saidaEsperada = saida;
		this.matrizConfusao = new double[this.saidaEsperada[0].length][this.saidaEsperada[0].length];
		this.resetResultado();
		this.inicializarPesoSaida();
	}
	
	private void resetResultado(){
		this.resultado = new ArrayList<Integer>();
	}
	
	private void setTaxaAprendizado(double taxaAprendizado) {
		if (taxaAprendizado > 0)
			this.taxaAprendizado = taxaAprendizado;
		else
			throw new IllegalArgumentException("Valor invalido");
	}

	private void setNumeroCamadas(int numeroCamadas) {
		if (numeroCamadas > 0)
			this.numeroCamadas = numeroCamadas;
		else
			throw new IllegalArgumentException("Valor invalido");
	}

	private void setNumMaxInteracoes(long numMaxInteracoes) {
		if (numMaxInteracoes > 0)
			this.numMaxInteracoes = numMaxInteracoes;
		else
			throw new IllegalArgumentException("Valor invalido");
	}
	
	void inicializarPesoSaida(){
		int tamanhoSaida = this.saidaEsperada[0].length;
		this.pesoSaida = new double[tamanhoSaida][this.camadas[this.numeroCamadas - 1].neurons.length];
		this.limiarSaida = new double[tamanhoSaida];
		for(int i = 0; i < this.saidaEsperada[0].length; i++){
			for(int j = 0; j < this.camadas[this.numeroCamadas - 1].neurons.length; j++){ 
				this.pesoSaida[i][j] = Layer.gerarNumero();
			}
			this.limiarSaida[i] = Layer.gerarNumero();               
		}
	}
	
	/**
	 * calculo da sensibilidade da camada de saida
	 * Backpropagation
	 */
	private double[] calcSensibildadeCamadaSaida(double[] saidaRede, double[] erroGeral){
		double[] sensibildade = new double[saidaRede.length];
		for (int s = 0; s < saidaRede.length; s++){
			sensibildade[s] = saidaRede[s] * ( 1-saidaRede[s] ) * erroGeral[s];
		}
		return sensibildade;
	}
	
	private void atualizarPesoCamadaSaida(double[] sensibildadeSaida){
		Layer ultimaCamada = this.camadas[this.numeroCamadas - 1];
		for (int s = 0; s < sensibildadeSaida.length; s++){
			for (int p = 0; p < this.pesoSaida[s].length; p++){
				this.pesoSaida[s][p] += (this.taxaAprendizado * sensibildadeSaida[s] * ultimaCamada.neurons[p]); //verificar cálculo	
			}
			this.limiarSaida[s] += (this.taxaAprendizado * sensibildadeSaida[s]);
		}
	}
	
	public void feedForward() {
		
	}
	
	public void testar(){
		
	}
	/*
	public void treinar(){
		for (int i = 0; i < this.numMaxInteracoes; i++) {
			InformacaoRede info = new InformacaoRede();
			for (int j = 0; j < this.entrada.length; j++) {
				this.feedForward(this.entrada[j]);
				double[] saidaRede = this.feedForwardSaida();
				if(this.acertou(this.saidaEsperada[j], saidaRede)){
					info.incrementeAcerto();
				}else{
					info.incrementeErro();
				}
				
				// calculo do erro geral e de cada neuronio da camada de saida
				double totalErroGeral = 0;
				double[] erroGeral = new double[saidaRede.length];
				for (int s = 0; s < saidaRede.length; s++) {
					erroGeral[s] = (this.saidaEsperada[j][s] - saidaRede[s]);
					totalErroGeral += erroGeral[s];
				}
				info.addtValorErro(Math.abs(totalErroGeral));
				
				double[] sensibildadeSaida  = this.calcSensibildadeCamadaSaida(saidaRede, erroGeral);
				ArrayList<Double[]> sensibilidadeCamadas = this.calcSensibildadeCamadaEscondida(sensibildadeSaida);
				
				this.atualizarPesoCamadaSaida(sensibildadeSaida);
				this.atualizarPesoCamadasIntermediaria(sensibilidadeCamadas,this.entrada[j]);
			}
			this.relatorio.add(info);
		}
	}*/

}
