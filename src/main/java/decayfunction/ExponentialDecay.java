package decayfunction;

/**
 * ExponentialDecay Class can calculate exponential decay function.
 */
public class ExponentialDecay extends DecayFunction {

    /**
     * Constructor.
     * @param decayFactor The decay factor.
     */
    public ExponentialDecay(double decayFactor) {
        super(decayFactor);
    }

    /**
     * Returns the decay outcome.
     * @param initial      Initial emotion intensity.
     * @param millisPassed Milliseconds since last decay function execution.
     * @return
     */
    @Override
    public double decay(double initial, long millisPassed) {
        return initial * Math.pow(getDecayFactor(), millisPassed / 1000);
    }

}
