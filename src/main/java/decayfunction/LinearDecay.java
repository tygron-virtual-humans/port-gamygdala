package decayfunction;

/**
 * Calculate the decay by a linear model.
 */
public class LinearDecay extends DecayFunction {

    /**
     * Constructor.
     * 
     * @param decayFactor The decay factor.
     */
    public LinearDecay(double decayFactor) {
        super(decayFactor);
    }

    /**
     * Returns the decay by linear model.
     * 
     * @param initial Initial emotion intensity.
     * @param millisPassed Milliseconds since last decay function execution.
     * @return the decay by linear model.
     */
    @Override
    public double decay(double initial, long millisPassed) {
        return initial - getDecayFactor() * (millisPassed / 1000.d);
    }

}
