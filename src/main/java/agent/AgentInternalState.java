package agent;

import java.util.ArrayList;

import agent.data.Emotion;
import debug.Debug;
import decayfunction.DecayFunction;

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
     * Appraisals simply add to the old value of the emotion.
     * Repeated appraisals without decay will result in the sum of
     * the appraisals over time. To decay the emotional state, call
     * Gamygdala.decay(decayFunction).
     *
     * @param emotion The emotion with which this Agent should be updated.
     */
    public void updateState(Emotion emotion) {
        if (emotion != null) {
            Debug.debug("      updating emotion: " + emotion);

            for (Emotion temp : this) {
                if (temp.getName().equals(emotion.getName())) {
                    temp.setIntensity(temp.getIntensity() + emotion.getIntensity());
                    Debug.debug("         new emotion: " + temp);
                    return;
                }
            }
            Debug.debug("         new emotion: " + emotion);

            // copy on keep, we need to maintain a list of current emotions for the
            // state, not a list references to the appraisal engine
            this.add(new Emotion(emotion.getName(), emotion.getIntensity()));
        }
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
    public AgentInternalState getState(Double gain) {
        if (gain == null) {
            return this;
        }

        AgentInternalState gainState = new AgentInternalState();
        for (Emotion emotion : this) {
            if (emotion != null) {
                double gainEmo = (gain * emotion.getIntensity()) / (gain * emotion.getIntensity() + 1);
                gainState.add(new Emotion(emotion.getName(), gainEmo));
            }
        }
        return gainState;
    }

    /**
     * Decay all the emotions in its internal state.
     * @param function the decay function
     * @param millisPassed the milliseconds passed
     */
    public void decay(DecayFunction function, long millisPassed) {
        for (int i = 0; i < size(); i++) {

            double newIntensity = function.decay(get(i).getIntensity(), millisPassed);

            if (newIntensity < 0) {
                remove(i);
            } else {
                get(i).setIntensity(newIntensity);
            }
        }
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
     * @return String List of emotions.
     */
    public String toString(Double gain) {
        StringBuilder output = new StringBuilder();
        for (Emotion emotion : this.getState(gain)) {
            output.append(emotion.getName()).append(": ").append(emotion.getIntensity()).append(", ");
        }
        return output.toString();
    }
}
