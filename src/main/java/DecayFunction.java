/**
 * Interface for emotion intensity decay functions.
 */
public abstract class DecayFunction {
	
	/**
	 * Decay factor.
	 */
	private double decayFactor;
	
	/**
	 * Construct new DecayFunction object.
	 * @param decayFactor The decay factor used. A factor of 1 means no decay.
	 */
	public DecayFunction(double decayFactor) {
		this.decayFactor = decayFactor;
	}

	/**
	 * Get decay factor.
	 * @return Decay factor.
	 */
	public double getDecayFactor() {
		return decayFactor;
	}

	/**
	 * Get decay factor.
	 * @return Decay factor.
	 */
	public void setDecayFactor(double decayFactor) {
		this.decayFactor = decayFactor;
	}
	
	/**
	 * Decay function for emotion intensity.
	 * 
	 * @param initial Initial emotion intensity.
	 * @param millisPassed Milliseconds since last decay function execution.
	 * @return New emotion intensity.
	 */
	public abstract double decay(double initial, long millisPassed);
}
