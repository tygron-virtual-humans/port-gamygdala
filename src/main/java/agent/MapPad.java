package agent;

import data.Emotion;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Pleasure Arousal Dominance mapping of the emotional states.
 */
public class MapPad {

    HashMap<String, double[]> mapPad;

    /**
     * Construct a new mapping with default emotions.
     */
    public MapPad() {
        // Initialize PAD hashmap
        this.mapPad = new HashMap<String, double[]>(16, 1);

        // Add initial emotions.
        this.addInitialEmotions();
    }

    /**
     * Adds the initial / default emotions to the PAD map.
     */
    private void addInitialEmotions() {
        this.mapPad.put("distress", new double[]{-0.61, 0.28, -0.36});
        this.mapPad.put("fear", new double[]{-0.64, 0.6, -0.43});
        this.mapPad.put("hope", new double[]{0.51, 0.23, 0.14});
        this.mapPad.put("joy", new double[]{0.76, .48, 0.35});
        this.mapPad.put("satisfaction", new double[]{0.87, 0.2, 0.62});
        this.mapPad.put("fear-confirmed", new double[]{-0.61, 0.06, -0.32}); // defeated
        this.mapPad.put("disappointment", new double[]{-0.61, -0.15, -0.29});
        this.mapPad.put("relief", new double[]{0.29, -0.19, -0.28});
        this.mapPad.put("happy-for", new double[]{0.64, 0.35, 0.25});
        this.mapPad.put("resentment", new double[]{-0.35, 0.35, 0.29});
        this.mapPad.put("pity", new double[]{-0.52, 0.02, -0.21}); // regretful
        this.mapPad.put("gloating", new double[]{-0.45, 0.48, 0.42}); // cruel
        this.mapPad.put("gratitude", new double[]{0.64, 0.16, -0.21}); // grateful
        this.mapPad.put("anger", new double[]{-0.51, 0.59, 0.25});
        this.mapPad.put("gratification", new double[]{0.69, 0.57, 0.63}); // triumphant
        this.mapPad.put("remorse", new double[]{-0.57, 0.28, -0.34}); // guilty
    }

    /**
     * This function returns a summation-based Pleasure Arousal Dominance mapping of the emotional
     * state as is (gain=false), or a PAD mapping based on a gained limiter (limited between 0 and 1),
     * of which the gain can be set by using setGain(gain).
     * It sums over all emotions the equivalent PAD values of each emotion (i.e.,
     * [P,A,D]=SUM(Emotion_i([P,A,D])))), which is then gained or not.
     * A high gain factor works well when appraisals are small and rare, and you want to see the
     * effect of these appraisals.
     * A low gain factor (close to 0 but in any case below 1) works well for high frequency and/or
     * large appraisals, so that the effect of these is dampened.
     *
     * @param useGain Whether to use the gain function or not.
     * @return An array of doubles with Pleasure at index 0, Arousal at index [1] and Dominance at
     * index [2].
     */
    public double[] getPadState(ArrayList<Emotion> emotions, double gain, boolean useGain) {
        double[] pad = new double[] {0, 0, 0};

        Emotion emotion;
        for (Emotion emotion1 : emotions) {
            emotion = emotion1;

            for (int j = 0; j < pad.length; j++) {
                pad[j] += emotion.intensity * this.mapPad.get(emotion.name)[j];
            }
        }

        if (useGain) {
            for (int i = 0; i < pad.length; i++) {
                pad[i] = (pad[i] >= 0 ? gain * pad[i] / (gain * pad[i] + 1) : -gain * pad[i]
                        / (gain * pad[i] - 1));
            }
        }

        return pad;
    }

}
