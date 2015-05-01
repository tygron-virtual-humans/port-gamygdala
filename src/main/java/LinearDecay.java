public class LinearDecay extends DecayFunction {

  public LinearDecay(double decayFactor) {
    super(decayFactor);
  }

  @Override
  public double decay(double initial, long millisPassed) {
    return initial - getDecayFactor() * (millisPassed / 1000);
  }

}
