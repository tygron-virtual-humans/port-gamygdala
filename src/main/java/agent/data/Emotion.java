package agent.data;

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
     * @param conName      Name of the emotion.
     * @param conIntensity Intensity of the emotion.
     */
    public Emotion(String conName, double conIntensity) {
        this.name = conName;
        this.intensity = conIntensity;
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
    public void setName(String newName) {
        this.name = newName;
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
    public void setIntensity(double newIntensity) {
        this.intensity = newIntensity;
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
        if (this == o) {
            return true;
        }
        if (!(o instanceof Emotion)) {
            return false;
        }
        Emotion emotion = (Emotion) o;
        return Double.compare(emotion.getIntensity(), getIntensity()) == 0
                && !(getName() != null ? !getName().equals(emotion.getName()) : emotion.getName() != null);
    }
}
