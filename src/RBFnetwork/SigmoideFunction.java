package RBFnetwork;

public class SigmoideFunction extends Function{
	@Override
	public double activationFunction(double value) {
		// TODO Auto-generated method stub
		return 1/(1 + Math.E-value);
	}

	@Override
	public double derivate(double value) {
		// TODO Auto-generated method stub
		return value*(1 - value);
	}
}
