package RBFnetwork;

import java.util.Random;

public class RBFNetwork {
	
	private static double bias1[];
	private static double bias2[];

	private static double antigodeltabs1[];
	private static double antigodeltabs2[];
	private static double deltabs1[];
	private static double deltabs2[];

	private static double [][] pesos1;
	private static double [][] pesos2;

	private static double [][] antigodeltapesos1;
	private static double [][] antigodeltapesos2;

	private static double [][] deltapesos1;
	private static double [][] deltapesos2;

	private static double net1[];
	private static double net2[][];
	private static double netout[][];
	private static double erro1[][];
	private static double erro2[][];

	private static double sensibilidade1[];

	private static double fnet1[];

	private static double [][] entradas;
	private static double [][] saidas;

	private static int numHidden;
	private static int numInputs;
	private static int numOutputs;
	private static int ciclos;
	private static int cicloAtual;
	private static int numTraining;
	//Atributo que indica o valor de Lcoef (taxa de aprendizagem).
	private static double alfa;
	//Atributo que indica o valor de Motum (Momentum).
	private static double beta;
	
	public static void randomize(){
		Random random = new Random();
		for (int j=0; j<getNumHidden(); j++) {
			setBias1(j,-1+random.nextInt(8192)/8192);
			setAntigodeltabs1(j, 0.0d);
			setDeltabs1(j, 0.0d);
			
			for (int i=0; i<getNumInputs(); i++) {
				setPesos1(j, i, random.nextInt(8192)/8192-0.5d);
				setAntigodeltapesos1(j, i, 0.0d);
				setDeltapesos1(j, i, 0.0d);
			}
		}
		
		for (int j=0; j<getNumOutputs(); j++) {
			setBias2(j,-0.1d + random.nextInt(8192)/8192);
			setAntigodeltabs2(j, 0.0d);
			for (int i=0;i<getNumHidden(); i++) {
				setPesos2(j, i, 0.1d * random.nextInt(8192)/8192- 0.05);
				setAntigodeltapesos2(j, i, 0.0d);
				setDeltapesos2(j, i, 0.0d);
			}
		}
	}
	
	public static void Forward () {
		for(int kl=0; kl<getCiclos(); kl++) {
			setCicloAtual(getCicloAtual()+ 1);
			
			for(int itr=0; itr<getNumTraining(); itr++) {

				double ea,eb;
				for (int j=0;j<getNumHidden();j++) {
					setNet1(j, getBias1(j));
					for(int i=0;i<getNumInputs();i++){
						setNet1(j,getNet1(j)+ (getPesos1(j, i)* getEntradas(itr,i)));
					}

					ea=(double)(Math.exp((double)((-1.0d)*(getNet1(j)))));

					setFnet1(j,(double) (1.0)/(1.0 + (ea)));
				}
	
				for(int j=0;j<getNumOutputs();j++)
				{
					setNet2(itr,j, getBias2(j));
					for(int i=0;i<getNumHidden();i++){
						setNet2(itr,j,getNet2(itr,j)+(getPesos2(j, i)*getFnet1(i)));
					}
					eb=(double)(Math.exp((double)((-1.0d)*(getNet2(itr,j)))));
					
					setNetout(itr,j, (double) (1.0/(1.0+eb)));
				}

				//Reajustando os pesos
				for(int j=0;j<getNumOutputs();j++) {
					setErro2(itr,j, (getSaidas(itr,j) - getNetout(itr,j)));
					//impressão dos dados de saída
					System.out.println("Ciclo:"+ " "+ getCicloAtual() + "  "+	"Exemplo:" +" "+ (itr+1));

					System.out.println("Saída desejada:"+" "  +  getSaidas(itr,j)+ "  "+ "Saída calculada:" 
					+" " +getNetout(itr,j));
					System.out.println("Erro:" +" "+ getErro2(itr,j));
					
									
					setDeltabs2(j,( getAlfa() * getErro2(itr,j))*getNetout(itr,j)*
					(1.0-getNetout(itr,j))+(getBeta() * getAntigodeltabs2(j)));

					for(int i=0;i<getNumHidden();i++){
						setDeltapesos2(j, i,( getAlfa()*getErro2(itr,j)*
								getNetout(itr,j)*(1.0-getNetout(itr,j))*getFnet1(i))+
								(getBeta()*getAntigodeltapesos2(j, i)));
					}
				}

				for(int j=0;j<getNumHidden();j++) {
					setSensibilidade1(j, 0.0d);
					for(int i=0;i<getNumOutputs();i++) {
						setSensibilidade1(j, getSensibilidade1(j)+(getErro2(itr,i)*getPesos2(i,j)));
					}

					setErro1(itr,j, (getFnet1(j))*(1.0d-getFnet1(j))*getSensibilidade1(j));
					setDeltabs1(j,( getAlfa() * getErro1(itr,j))+
							(getBeta() * getAntigodeltabs1(j)));

					for(int ii=0;ii<getNumInputs();ii++) {
						setDeltapesos1(j,ii, (getAlfa() * getErro1(itr,j))*(getEntradas(itr,ii))+
								(getBeta() * getAntigodeltapesos1(j,ii)));
					}
				}

				for(int j=0;j<getNumHidden();j++) {
					setBias1(j, getDeltabs1(j) + getBias1(j));
					setAntigodeltabs1(j, getDeltabs1(j));
					System.out.println("bias:"+" " + (j+1) +"     "+ getBias1(j)+ "  ");

					for(int ii=0;ii<getNumInputs();ii++) {

						setPesos1(j,ii, getPesos1(j,ii)+(getDeltapesos1(j,ii)));
						System.out.println("Peso:" +" "+ (j+1)+" " + (ii+1) + "    " +getPesos1(j,ii));

						
						setAntigodeltapesos1(j,ii, getDeltapesos1(j,ii));
					}
				}

				for(int j=0;j<getNumOutputs();j++) {
					setBias2(j, getDeltabs2(j) + getBias2(j));
					setAntigodeltabs2(j, getDeltabs2(j));
					System.out.println("bias:"+" " + (j+1) +"     "+ getBias2(j)+ "  ");

					for(int i=0;i<getNumHidden();i++) {
						setPesos2(j,i, getPesos2(j, i)+ (getDeltapesos2(j,i)));
						setAntigodeltapesos2(j, i, getDeltapesos2(j, i));
						System.out.println("Peso:" +" "+ (j+1)+" " + (i+1) + "    " +getPesos2(j,i));

						
					}
				}
			}
		}
	}
	
	public static void TreinamentoSaida(){
		
	}
	
	public static void TreinamentoHidden(){
		
	}

	
	/**
	 * Método principal
	 * @param args
	 */
	public static void main(String[] args){
		
		//-----------------------------------------
		setCiclos(1000);
		setCicloAtual(0);
		setNumInputs(3);
		setNumHidden(4);
		setNumOutputs(1);
		setNumTraining(7);
		setAlfa(0.1);
		setBeta(0.6);
		//-----------------------------------------
		
		//Inicializar o tamanho das matrizes.
		initPesos1(getNumHidden(),getNumInputs());
		initAntigodeltapesos1(getNumHidden(),getNumInputs());
		initDeltapesos1(getNumHidden(),getNumInputs());
		
		initPesos2(getNumOutputs(),getNumHidden());
		initAntigodeltapesos2(getNumOutputs(),getNumHidden());
		initDeltapesos2(getNumOutputs(),getNumHidden());

		initNet2(getNumTraining(), getNumOutputs());
		initNetout(getNumTraining(), getNumOutputs());
		initErro2(getNumTraining(), getNumOutputs());
		
		initErro1(getNumTraining(), getNumHidden());

		//--------------------------------------------
		
		
		//Inicia os pesos aleatórios.
		randomize();
		
		double [][] entradasIniciais = {{1,2,3}, {2,3,4}};
		entradas = entradasIniciais;

		double [][] saidasIniciais = {{4}, {5}};
		saidas = saidasIniciais;
		
		Forward();

	}
	
	/**
	 * Retorna o valor de beta
	 * @return
	 */
	public static double getBeta(){
		return beta;
	}

	/**
	 * Atribui o valor de beta.
	 * @param value
	 */
	public static void setBeta(double value){
		beta = value;
	}

	/**
	 * Retorna o valor de alfa
	 * @return
	 */
	public static double getAlfa(){
		return alfa;
	}

	/**
	 * Atribui o valor de alfa.
	 * @param value
	 */
	public static void setAlfa(double value){
		alfa = value;
	}

	/**
	 * Retorna o número de exemplos de treinamento.
	 * @return
	 */
	public static int getNumTraining(){
		return numTraining;
	}

	/**
	 * Atribui o número de exemplos de treinamento.
	 * @param value
	 */
	public static void setNumTraining(int value){
		numTraining = value;
	}

	/**
	 * Retorna o número de ciclos
	 * @return
	 */
	public static int getCiclos(){
		return ciclos;
	}

	/**
	 * Atribui o número de ciclos.
	 * @param value
	 */

	/**
	 * 
	 * 	/**
	 * Retorna  o ciclo atual
	 * @return
	 */
	public static int getCicloAtual(){
		return cicloAtual;
	}

	/**
	 * Atribui o ciclo atual.
	 * @param value
	 */
	public static void setCicloAtual(int value){
		cicloAtual = value;
	}
	
	
	
	public static void setCiclos(int value){
		ciclos = value;
	}

	/**
	 * Retorna o número de saídas.
	 * @return
	 */
	public static int getNumOutputs(){
		return numOutputs;
	}

	/**
	 * Atribui o número de saídas.
	 * @param value
	 */
	public static void setNumOutputs(int value){
		numOutputs = value;

		//inicializa os vetores para bias2 e prdlbs2
		bias2 = new double[getNumOutputs()];
		antigodeltabs2 = new double[getNumOutputs()];
		deltabs2 = new double[getNumOutputs()];
	}

	/**
	 * Retorna o número de entradas.
	 * @return
	 */
	public static int getNumInputs(){
		return numInputs;
	}

	/**
	 * Atribui o número de entradas.
	 * @param value
	 */
	public static void setNumInputs(int value){
		numInputs = value;

	}

	/**
	 * Retorna o número de neurônios escondidos.
	 * @return
	 */
	public static int getNumHidden(){
		return numHidden;
	}

	/**
	 * Atribui o número de neurônios escondidos.
	 * @param value
	 */
	public static void setNumHidden(int value){
		numHidden = value;

		//Inicializa o vetor para bias1, netin1, sum1, prdlbs1, delbs1.
		bias1 = new double[getNumHidden()];
		net1 = new double[getNumHidden()];
		sensibilidade1 = new double[getNumHidden()];
		fnet1 = new double[getNumHidden()];
		antigodeltabs1 = new double [getNumHidden()];
		deltabs1 = new double [getNumHidden()];
	}

	/**
	 * Atribui o valor do bias1 no índice indicado.
	 * @param index: índice do vetor.
	 * @param value: valor que será atribuído.
	 */
	public static void setBias1(int index, double value){
		bias1[index]=value;
	}

	/**
	 * Retorna o valor do bias1 no índice indicado.
	 * @param index: índice do vetor.
	 */
	public static double getBias1(int index){
		return bias1[index];
	}


	/**
	 * Atribui o valor do bias2 no índice indicado.
	 * @param index: índice do vetor.
	 * @param value: valor que será atribuído.
	 */
	/*public static void setBias2(int index,double value){
		bias2[index] = value;
	}*/

	/**
	 * Retorna o valor do bias2 no índice indicado.
	 * @param index: índice do vetor.
	 */
	public static double getBias2(int index){
		return bias2[index];
	}


	/**
	 * Atribui o valor do netin1 no índice indicado.
	 * @param index: índice do vetor.
	 * @param value: valor que será atribuído.
	 */
	public static void setNet1(int index, double value){
		net1[index]=value;
	}

	/**
	 * Retorna o valor do netin1 no índice indicado.
	 * @param index: índice do vetor.
	 */
	public static double getNet1(int index){
		return net1[index];
	}

	/**
	 * Atribui o valor do sum1 no índice indicado.
	 * @param index: índice do vetor.
	 * @param value: valor que será atribuído.
	 */
	public static void setSensibilidade1(int index, double value){
		sensibilidade1[index]=value;
	}

	/**
	 * Retorna o valor da sensibilidade no índice indicado.
	 * @param index: índice do vetor.
	 */
	public static double getSensibilidade1(int index){
		return sensibilidade1[index];
	}


	/**
	 * Retorna o valor do fnet1 no índice indicado.
	 * @param index: índice do vetor.
	 */
	public static double getFnet1(int index){
		return fnet1[index];
	}

	/**
	 * Atribui o valor do fnet1 no índice indicado.
	 * @param index: índice do vetor.
	 * @param value: valor que será atribuído.
	 */
	public static void setFnet1(int index, double value){
		fnet1[index]=value;
	}



	/**
	 * Retorna o valor do Antigodeltabs2 no índice indicado.
	 * @param index: índice do vetor.
	 */
	public static double getAntigodeltabs2(int index){
		return antigodeltabs2[index];
	}

	/**
	 * Atribui o valor do Antigodeltabs1 no índice indicado.
	 * @param index: índice do vetor.
	 * @param value: valor que será atribuído.
	 */
	public static void setAntigodeltabs1(int index, double value){
		antigodeltabs1[index]=value;
	}

	/**
	 * Atribui o valor do Antigodeltabs2 no índice indicado.
	 * @param index: índice do vetor.
	 * @param value: valor que será atribuído.
	 */
	public static void setAntigodeltabs2(int index, double value){
		antigodeltabs2[index]=value;
	}

	/**
	 * Retorna o valor do Antigodeltabs1 no índice indicado.
	 * @param index: índice do vetor.
	 */
	public static double getAntigodeltabs1(int index){
		return antigodeltabs1[index];
	}

	/**
	 * Atribui o valor do delbs1 no índice indicado.
	 * @param index: índice do vetor.
	 * @param value: valor que será atribuído.
	 */
	public static void setDeltabs1(int index, double value){
		deltabs1[index]=value;
	}

	/**
	 * Retorna o valor do deltabs1 no índice indicado.
	 * @param index: índice do vetor.
	 */
	public static double getDeltabs1(int index){
		return deltabs1[index];
	}

	/**
	 * Atribui o valor do deltabs2 no índice indicado.
	 * @param index: índice do vetor.
	 * @param value: valor que será atribuído.
	 */
	public static void setDeltabs2(int index, double value){
		deltabs2[index]=value;
	}

	/**
	 * Retorna o valor do deltabs2 no índice indicado.
	 * @param index: índice do vetor.
	 */
	public static double getDeltabs2(int index){
		return deltabs2[index];
	}


	/**
	 * Atribui o valor do bias2 no índice indicado.
	 * @param index
	 * @param value
	 */
	public static void setBias2(int index, double value){
		bias2[index]=value;
	}

	/**
	 * Retorna o valor do Pesos1
	 * @return
	 */
	public static double getPesos1(int row, int col){
		return pesos1[row][col];
	}

	/**
	 * Atribui o valor de Pesos1.
	 * @param row
	 * @param col
	 * @param value
	 */
	public static void setPesos1(int row, int col, double value){
		pesos1[row][col] = value;
	}

	/**
	 * Retorna o valor do Pesos2
	 * @return
	 */
	public static double getPesos2(int row, int col){
		return pesos2[row][col];
	}

	/**
	 * Atribui o valor de Pesos2.
	 * @param row
	 * @param col
	 * @param value
	 */
	public static void setPesos2(int row, int col, double value){
		pesos2[row][col] = value;
	}

	/**
	 * Retorna o valor do Antigodeltapesos1
	 * @return
	 */
	public static double getAntigodeltapesos1(int row, int col){
		return antigodeltapesos1[row][col];
	}

	/**
	 * Atribui o valor de Antigodeltapesos2.
	 * @param row
	 * @param col
	 * @param value
	 */
	public static void setAntigodeltapesos2(int row, int col, double value){
		antigodeltapesos2[row][col] = value;
	}

	/**
	 * Retorna o valor do Antigodeltapesos2
	 * @return
	 */
	public static double getAntigodeltapesos2(int row, int col){
		return antigodeltapesos2[row][col];
	}

	/**
	 * Atribui o valor de Antigodeltapesos1.
	 * @param row
	 * @param col
	 * @param value
	 */
	public static void setAntigodeltapesos1(int row, int col, double value){
		antigodeltapesos1[row][col] = value;
	}

	/**
	 * Retorna o valor do Deltapesos1
	 * @return
	 */
	public static double getDeltapesos1(int row, int col){
		return deltapesos1[row][col];
	}

	/**
	 * Atribui o valor de Deltapesos1.
	 * @param row
	 * @param col
	 * @param value
	 */
	public static void setDeltapesos1(int row, int col, double value){
		deltapesos1[row][col] = value;
	}

	/**
	 * Retorna o valor do Deltapesos2
	 * @return
	 */
	public static double getDeltapesos2(int row, int col){
		return deltapesos2[row][col];
	}

	/**
	 * Atribui o valor de Deltapesos2.
	 * @param row
	 * @param col
	 * @param value
	 */
	public static void setDeltapesos2(int row, int col, double value){
		deltapesos2[row][col] = value;
	}

	/**
	 * Inicializa o valor da matriz Pesos1.
	 * @param row
	 * @param col
	 */
	public static void initPesos1(int row, int col){
		pesos1 = new double[row][col];
	}


	/**
	 * Retorna o valor do Net2
	 * @return
	 */
	public static double getNet2(int row, int col){
		return net2[row][col];
	}

	/**
	 * Atribui o valor de Net2.
	 * @param row
	 * @param col
	 * @param value
	 */
	public static void setNet2(int row, int col, double value){
		net2[row][col] = value;
	}

	/**
	 * Retorna o valor do Erro1
	 * @return
	 */
	public static double getErro1(int row, int col){
		return erro1[row][col];
	}

	/**
	 * Atribui o valor de Erro1.
	 * @param row
	 * @param col
	 * @param value
	 */
	public static void setErro1(int row, int col, double value){
		erro1[row][col] = value;
	}


	/**
	 * Retorna o valor do Netout
	 * @return
	 */
	public static double getNetout(int row, int col){
		return netout[row][col];
	}

	/**
	 * Atribui o valor de Netout.
	 * @param row
	 * @param col
	 * @param value
	 */
	public static void setNetout(int row, int col, double value){
		netout[row][col] = value;
	}

	/**
	 * Retorna o valor do erro2
	 * @return
	 */
	public static double getErro2(int row, int col){
		return erro2[row][col];
	}

	/**
	 * Atribui o valor de erro2.
	 * @param row
	 * @param col
	 * @param value
	 */
	public static void setErro2(int row, int col, double value){
		erro2[row][col] = value;
	}

	/**
	 * Inicializa o valor da matriz Pesos2.
	 * @param row
	 * @param col
	 */
	public static void initPesos2(int row, int col){
		pesos2 = new double[row][col];
	}

	/**
	 * Inicializa o valor da matriz Prdlwt1.
	 * @param row
	 * @param col
	 */
	public static void initAntigodeltapesos1(int row, int col){
		antigodeltapesos1 = new double[row][col];
	}

	/**
	 * Inicializa o valor da matriz Antigodeltapesos2.
	 * @param row
	 * @param col
	 */
	public static void initAntigodeltapesos2(int row, int col){
		antigodeltapesos2 = new double[row][col];
	}

	/**
	 * Inicializa o valor da matriz Deltapesos1.
	 * @param row
	 * @param col
	 */
	public static void initDeltapesos1(int row, int col){
		deltapesos1 = new double[row][col];
	}

	/**
	 * Inicializa o valor da matriz Deltapesos2.
	 * @param row
	 * @param col
	 */
	public static void initDeltapesos2(int row, int col){
		deltapesos2 = new double[row][col];
	}

	/**
	 * Inicializa o valor da matriz Net2.
	 * @param row
	 * @param col
	 */
	public static void initNet2(int row, int col){
		net2 = new double[row][col];
	}

	/**
	 * Inicializa o valor da matriz Erro1.
	 * @param row
	 * @param col
	 */
	public static void initErro1(int row, int col){
		erro1 = new double[row][col];
	}

	/**
	 * Inicializa o valor da matriz Net2.
	 * @param row
	 * @param col
	 */
	public static void initNetout(int row, int col){
		netout = new double[row][col];
	}

	/**
	 * Inicializa o valor da matriz erro2.
	 * @param row
	 * @param col
	 */
	public static void initErro2(int row, int col){
		erro2 = new double[row][col];
	}

	/**
	 * Retorna o valor da matriz de entrada.
	 * @param row
	 * @param col
	 */
	public static double getEntradas(int row, int col){
		return entradas[row][col];
	}

	/**
	 * Retorna o valor da matriz de saidas.
	 * @param row
	 * @param col
	 */
	public static double getSaidas(int row, int col){
		return saidas[row][col];
	}
}
