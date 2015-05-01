public class ExponentialDecay extends DecayFunction {

  public ExponentialDecay(double decayFactor) {
    super(decayFactor);
  }

  @Override
  public double decay(double initial, long millisPassed) {
    return initial * Math.pow(getDecayFactor(), millisPassed / 1000);
  }

}
