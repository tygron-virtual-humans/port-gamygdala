package agent.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Data structure to store an Emotion. Emotions have a name and intensity.
 */
public class Emotion {

    /**
     * Name of the emotion.
     */
    private String name;

    /**
     * Intensity of the emotion.
     */
    private double intensity;

    /**
     * Construct a new Emotion.
     *
     * @param name      Name of the emotion.
     * @param intensity Intensity of the emotion.
     */
    public Emotion(String name, double intensity) {
        this.name = name;
        this.intensity = intensity;
    }

    /**
     * Get name of the Emotion.
     * @return String Name of the Emotion.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name of the Emotion.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get intensity of the Emotion.
     * @return double Intensity of the Emotion.
     */
    public double getIntensity() {
        return intensity;
    }

    /**
     * Sets intensity of the Emotion.
     */
    public void setIntensity(double intensity) {
        this.intensity = intensity;
    }

    /**
     * Determine emotions based on three Goal parameters.
     *
     * @param utility         The goal utility.
     * @param deltaLikelihood The goal delta likelihood.
     * @param likelihood      The goal likelihood.
     * @return List of emotion names.
     */
    public static List<String> determineEmotions(double utility, double deltaLikelihood, double likelihood) {
        ArrayList<String> emotion = new ArrayList<String>();

        if (likelihood > 0 && likelihood < 1) {
            emotion.add((utility >= 0 && deltaLikelihood >= 0 || utility < 0 && deltaLikelihood < 0) ? "hope" : "fear");
        } else if (likelihood == 1) {
            if (deltaLikelihood < 0.5) {
                emotion.add(utility >= 0 ? "satisfaction" : "fear-confirmed");
            }
            emotion.add(utility >= 0 ? "joy" : "distress");
        } else if (likelihood == 0) {
            if (deltaLikelihood > 0.5) {
                emotion.add(utility >= 0 ? "disappointment" : "relief");
            }
            emotion.add(utility >= 0 ? "distress" : "joy");
        }
        return emotion;
    }

    /**
     * String representation of Emotion.
     */
    @Override
    public String toString() {
        return "<Emotion[" + name + ", " + intensity + "]>";
    }

    /**
     * Get Object hashCode.
     * @return hashCode of Object
     */
    @Override
    public int hashCode() {
        int result;
        long temp;
        result = getName() != null ? getName().hashCode() : 0;
        temp = Double.doubleToLongBits(getIntensity());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    /**
     * Equals method.
     * @param o object to check
     * @return Boolean iff object are the same
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Emotion)) return false;

        Emotion emotion = (Emotion) o;

        if (Double.compare(emotion.getIntensity(), getIntensity()) != 0) return false;
        return !(getName() != null ? !getName().equals(emotion.getName()) : emotion.getName() != null);
    }
}
