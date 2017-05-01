package RBFnetwork;

/*
* Created by renanalencar on 23/04/17.
* Based on Carl Saldanha's Radial Basis Function Network
* Read more about it on: http://cjds.github.io/2014/05/07/RBFN/
*/
public class RBF {
	
	//Mariana
	

   class Net {

       double[][] inputs = new double[][]{{0,0},{0,1},{1,0},{1,1}};
       double[] outputs = {1,0,0,1};
       Unit[] net = new Unit[2];

       Net(){
           net[0] = new Unit(0.5,new double[]{0,0},0.5);
           net[1] = new Unit(0.5,new double[]{1,1},0.5);
       }

       void train(){

           for(int i = 0; i < inputs.length; i++) {
               double output = outputs[i];
               double predictedoutput = 0;
               for(int j = 0; j < inputs[i].length; j++) {
                   predictedoutput += net[j].phi(inputs[i]) * net[j].w;
               }
               //predictedoutput= Math.round(predictedoutput);

               for(int j = 0; j < inputs[i].length; j++) {
                   net[j].update(inputs[i], output, predictedoutput);
               }
           }
       }

       void test(double[] inputs) {

           double predictedOutput = 0;

           for(int i = 0; i < inputs.length; i++) {
               predictedOutput += net[i].phi(inputs) * net[i].w;
               System.out.print(net[i].w + "\t" + net[i].c[0] + "\t" + net[i].c[1] + "\t");
           }

           System.out.println();

           for(int i = 0; i < inputs.length; i++) {
               System.out.print(inputs[i] + "\t");
           }
           System.out.print(predictedOutput);
           System.out.println();

       }

       class Unit {
           double w;
           double c[];
           double sigma;
           double n1 = 0.1;
           double n2 = 0.1;

           Unit(double sigma,double[] center,double weight) {
               this.sigma = sigma;
               this.c = center;
               this.w = weight;

           }

           double phi(double[] input) {
               double distance = 0;
               for(int i = 0; i < c.length; i++)
                   distance += Math.pow(input[i] - c[i], 2);
               return Math.pow(Math.E,- distance / (2 * Math.pow(sigma, 2)));
           }

           void update(double[] input,double desired,double output) {
               double phi = phi(input);
               double diffOutput = desired - output;

               for(int i = 0; i < c.length; i++)
                   c[i] = c[i] + (n1 * diffOutput * w * phi * (input[i] - c[i]) / (sigma * sigma));

               w = w + (n2 * diffOutput * phi);
           }

       }

   }

   public static void main(String a[]) {

       Net n = new RBF().new Net();
       for(int i = 0; i < 100; i++){
           n.train();
       }

       n.test(new double[]{0,0});
       n.test(new double[]{0,1});
       n.test(new double[]{1,0});
       n.test(new double[]{1,1});


   }
}
