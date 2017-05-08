package RBFnetwork;

public abstract class Function {
	private String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public abstract double activationFunction(double value);
	
	public abstract double derivate(double value);
}
