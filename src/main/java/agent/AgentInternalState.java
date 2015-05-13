package agent;

import java.util.ArrayList;

import data.Emotion;

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
        Emotion temp;
        for (int i = 0; i < size(); i++) {
            temp = get(i);

            if (temp.name == emotion.name) {

                // Appraisals simply add to the old value of the emotion.
                // Repeated appraisals without decay will result in the sum of
                // the appraisals over time. To decay the emotional state, call
                // Gamygdala.decay(decayFunction).
                temp.intensity += emotion.intensity;

                return;
            }
        }

        // copy on keep, we need to maintain a list of current emotions for the
        // state, not a list references to the appraisal engine
        add(new Emotion(emotion.name, emotion.intensity));
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

        if (gain != null) {
            AgentInternalState gainState = new AgentInternalState();
            Emotion emotion;
            for (int i = 0; i < size(); i++) {
                emotion = get(i);

                if (emotion != null) {
                    double gainEmo = (gain * emotion.intensity) / (gain * emotion.intensity + 1);
                    gainState.add(new Emotion(emotion.name, gainEmo));
                }
            }

            return gainState;
        }

        return this;
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

        AgentInternalState emotionalState = this.getEmotionalState(gain);

        Emotion emotion;
        for (int i = 0; i < emotionalState.size(); i++) {
            emotion = emotionalState.get(i);
            output += emotion.name + ": " + emotion.intensity + ", ";
        }

        return output;
    }

}
