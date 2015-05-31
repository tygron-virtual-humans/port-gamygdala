package agent;

import java.util.ArrayList;

import data.Emotion;
import gamygdala.Engine;

/**
 * The internal state of an Agent. Contains all it's emotions.
 */
public class AgentInternalState extends ArrayList<Emotion> {

    /**
     * ID for serialization.
     */
    private static final long serialVersionUID = 2896244475677504657L;

    /**
     * Updates the emotional state of this agent using a single emotion.
     *
     * @param emotion The emotion with which this Agent should be updated.
     */
    public void updateEmotionalState(Emotion emotion) {
        Engine.debug("      updating emotion: " + emotion);

        for (Emotion temp : this) {
            if (temp.name.equals(emotion.name)) {
                // Appraisals simply add to the old value of the emotion.
                // Repeated appraisals without decay will result in the sum of
                // the appraisals over time. To decay the emotional state, call
                // Gamygdala.decay(decayFunction).
                temp.intensity += emotion.intensity;
                Engine.debug("         new emotion: " + temp);
                return;
            }
        }
        Engine.debug("         new emotion: " + emotion);

        // copy on keep, we need to maintain a list of current emotions for the
        // state, not a list references to the appraisal engine
        this.add(new Emotion(emotion.name, emotion.intensity));
    }

    /**
     * This function returns either the state as is (gain=false) or a state
     * based on gained limiter (limited between 0 and 1), of which the gain can
     * be set by using setGain(gain). A high gain factor works well when
     * appraisals are small and rare, and you want to see the effect of these
     * appraisals. A low gain factor (close to 0 but in any case below 1) works
     * well for high frequency and/or large appraisals, so that the effect of
     * these is dampened.
     *
     * @param gain The gain factor. Leave blank (null) to ignore gain.
     * @return An array of emotions.
     */
    public AgentInternalState getEmotionalState(Double gain) {
        if (gain == null) {
            return this;
        }

        AgentInternalState gainState = new AgentInternalState();
        for (Emotion emotion : this) {
            if (emotion != null) {
                double gainEmo = (gain * emotion.intensity) / (gain * emotion.intensity + 1);
                gainState.add(new Emotion(emotion.name, gainEmo));
            }
        }
        return gainState;
    }

    /**
     * This function prints to the console either the state as is (gain=false)
     * or a state based on gained limiter (limited between 0 and 1), of which
     * the gain can be set by using setGain(gain). A high gain factor works well
     * when appraisals are small and rare, and you want to see the effect of
     * these appraisals A low gain factor (close to 0 but in any case below 1)
     * works well for high frequency and/or large appraisals, so that the effect
     * of these is dampened.
     *
     * @param gain The gain factor. Leave blank (null) to ignore gain.
     */
    public String printEmotionalState(Double gain) {
        String output = "";
        for (Emotion emotion : this.getEmotionalState(gain)) {
            output += emotion.name + ": " + emotion.intensity + ", ";
        }
        return output;
    }

}
