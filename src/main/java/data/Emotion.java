package data;
/**
 * This class is mainly a data structure to store an emotion with its intensity.
 */
public class Emotion {

  /**
   * Name of the emotion.
   */
  public String name;

  /**
   * Intensity of the emotion.
   */
  public double intensity;

  /**
   * Construct a new Emotion.
   * 
   * @param name Name of the emotion.
   * @param intensity Intensity of the emotion.
   */
  public Emotion(String name, double intensity) {
    this.name = name;
    this.intensity = intensity;
  }

  /**
   * String representation of Emotion.
   */
  @Override
  public String toString() {
    return "<Emotion[" + name + ", " + intensity + "]>";
  }

  /**
   * Compare two Emotion objects.
   */
  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Emotion) {
      Emotion em = (Emotion) obj;
      return (em.name.equals(this.name) && Double.compare(em.intensity, this.intensity) == 0);
    }
    return false;
  }
}
