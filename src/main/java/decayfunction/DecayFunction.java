package decayfunction;

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
     *
     * @param conDecayFactor The decay factor used. A factor of 1 means no decay.
     */
    public DecayFunction(double conDecayFactor) {
        this.decayFactor = conDecayFactor;
    }

    /**
     * Get decay factor.
     *
     * @return Decay factor.
     */
    public double getDecayFactor() {
        return decayFactor;
    }

    /**
     * Get decay factor.
     * 
     * @param newDecayFactor The decay factor.
     */
    public void setDecayFactor(double newDecayFactor) {
        this.decayFactor = newDecayFactor;
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
