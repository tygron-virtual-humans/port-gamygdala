package decayfunction;

/**
 * Calculate exponential decay function.
 */
public class ExponentialDecay extends DecayFunction {

    /**
     * Constructor.
     * 
     * @param decayFactor The decay factor.
     */
    public ExponentialDecay(double decayFactor) {
        super(decayFactor);
    }

    /**
     * Returns the decay outcome.
     * 
     * @param initial Initial emotion intensity.
     * @param millisPassed Milliseconds since last decay function execution.
     * @return the decay outcome of the function.
     */
    @Override
    public double decay(double initial, long millisPassed) {
        return initial * Math.pow(getDecayFactor(), (double) millisPassed / 1000);
    }

}
