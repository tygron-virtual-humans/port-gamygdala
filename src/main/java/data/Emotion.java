package data;

import java.util.ArrayList;

/**
 * Data structure to store an Emotion. Emotions have a name and intensity.
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
            return (((em.name == null && this.name == null) || em.name.equals(this.name)) && Double.compare(em.intensity, this.intensity) == 0);
        }
        return false;
    }

    /**
     * Determine emotions based on three Goal parameters.
     * 
     * @param utility The goal utility.
     * @param deltaLikelihood The goal delta likelihood.
     * @param likelihood The goal likelihood.
     * @return List of emotion names.
     */
    public static ArrayList<String> determineEmotions(double utility, double deltaLikelihood, double likelihood) {

        ArrayList<String> emotion = new ArrayList<String>();

        boolean positive = false;
        if (utility >= 0 && deltaLikelihood >= 0 || utility < 0 && deltaLikelihood < 0) {
            positive = true;
        }

        if (likelihood > 0 && likelihood < 1) {

            if (positive) {
                emotion.add("hope");
            } else {
                emotion.add("fear");
            }

        } else if (likelihood == 1) {

            if (utility >= 0) {
                if (deltaLikelihood < 0.5) {
                    emotion.add("satisfaction");
                }
                emotion.add("joy");
            } else {
                if (deltaLikelihood < 0.5) {
                    emotion.add("fear-confirmed");
                }
                emotion.add("distress");
            }

        } else if (likelihood == 0) {

            if (utility >= 0) {
                if (deltaLikelihood > 0.5) {
                    emotion.add("disappointment");
                }
                emotion.add("distress");
            } else {
                if (deltaLikelihood > 0.5) {
                    emotion.add("relief");
                }
                emotion.add("joy");
            }

        }

        return emotion;
    }
}
