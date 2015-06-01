package agent;

import java.util.ArrayList;
import java.util.HashMap;

import data.Emotion;

/**
 * Pleasure Arousal Dominance mapping of the emotional states.
 */
public class PADMap {

    /**
     * Singleton Engine object.
     */
    private static PADMap padInstance;

    /**
     * Emotion map.
     */
    static HashMap<String, double[]> mapPad;

    /**
     * Empty constructor to prevent instantiating. Use PADMap.getInstance()
     * instead.
     */
    private PADMap() {
    }

    /**
     * Get PADMap object. If no PAD map has been instantiated before, create a
     * new one and store in this instance for future use.
     * 
     * @return PADMap instance.
     */
    public static PADMap getInstance() {
        if (PADMap.padInstance == null) {
            synchronized (PADMap.class) {
                PADMap.padInstance = new PADMap();
                PADMap.addInitialEmotions();
            }
        }
        return padInstance;
    }

    /**
     * Adds the initial / default emotions to the PAD map.
     */
    private static synchronized void addInitialEmotions() {
        mapPad = new HashMap<String, double[]>(16, 1);
        mapPad.put("distress", new double[] { -0.61, 0.28, -0.36 });
        mapPad.put("fear", new double[] { -0.64, 0.6, -0.43 });
        mapPad.put("hope", new double[] { 0.51, 0.23, 0.14 });
        mapPad.put("joy", new double[] { 0.76, .48, 0.35 });
        mapPad.put("satisfaction", new double[] { 0.87, 0.2, 0.62 });
        mapPad.put("fear-confirmed", new double[] { -0.61, 0.06, -0.32 }); // defeated
        mapPad.put("disappointment", new double[] { -0.61, -0.15, -0.29 });
        mapPad.put("relief", new double[] { 0.29, -0.19, -0.28 });
        mapPad.put("happy-for", new double[] { 0.64, 0.35, 0.25 });
        mapPad.put("resentment", new double[] { -0.35, 0.35, 0.29 });
        mapPad.put("pity", new double[] { -0.52, 0.02, -0.21 }); // regretful
        mapPad.put("gloating", new double[] { -0.45, 0.48, 0.42 }); // cruel
        mapPad.put("gratitude", new double[] { 0.64, 0.16, -0.21 }); // grateful
        mapPad.put("anger", new double[] { -0.51, 0.59, 0.25 });
        mapPad.put("gratification", new double[] { 0.69, 0.57, 0.63 }); // triumphant
        mapPad.put("remorse", new double[] { -0.57, 0.28, -0.34 }); // guilty
    }

    /**
     * This function returns a summation-based Pleasure Arousal Dominance
     * mapping of the emotional state as is (gain=false), or a PAD mapping based
     * on a gained limiter (limited between 0 and 1), of which the gain can be
     * set by using setGain(gain). It sums over all emotions the equivalent PAD
     * values of each emotion (i.e., [P,A,D]=SUM(Emotion_i([P,A,D])))), which is
     * then gained or not. A high gain factor works well when appraisals are
     * small and rare, and you want to see the effect of these appraisals. A low
     * gain factor (close to 0 but in any case below 1) works well for high
     * frequency and/or large appraisals, so that the effect of these is
     * dampened.
     *
     * @return An array of doubles with Pleasure at index [0], Arousal at index
     *         [1] and Dominance at index [2].
     */
    public static double[] getPadState(ArrayList<Emotion> emotions, Double gain) {
        if (emotions == null) {
            throw new IllegalArgumentException("Agent does not have emotional state.");
        }
        double[] pad = new double[] {.0, .0, .0};

        for (Emotion emotion : emotions) {
            for (int i = 0, padLength = pad.length; i < padLength; i++) {
                pad[i] += (emotion.getIntensity() * mapPad.get(emotion.getName())[i]);

                if (gain != null) {
                    pad[i] = (pad[i] >= 0 ? gain * pad[i] / (gain * pad[i] + 1) : -gain * pad[i] / (gain * pad[i] - 1));
                }
            }
        }

        return pad;
    }

    /**
     * Get the summation-based Pleasure Arousal Dominance mapping of the
     * emotional state of an Agent.
     * 
     * @param agent Agent to evaluate.
     * @param gain Whether or not to base the PAD mapping on a gained limiter.
     * @return An array of doubles with Pleasure at index [0], Arousal at index
     *         [1] and Dominance at index [2].
     */
    public static double[] getPadState(Agent agent, Double gain) {
        if (agent == null) {
            throw new IllegalArgumentException("Agent is null.");
        }

        return getPadState(agent.getEmotionalState(gain), gain);
    }

}
