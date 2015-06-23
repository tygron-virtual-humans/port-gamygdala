package agent.strategy;

import java.util.ArrayList;
import java.util.List;

import agent.data.Emotion;

/**
 * DetermineStrategy when Likelihood is between 0 and 1.
 */
public class LikelihoodBetweenStrategy implements DetermineStrategy {

    /**
     * Determine emotions based on three Goal parameters.
     *
     * @param utility         The goal utility.
     * @param deltaLikelihood The goal delta likelihood.
     * @return List of emotion names.
     */
    public List<Emotion> getEmotion(double utility, double deltaLikelihood, double intensity) {
        List<Emotion> emotion = new ArrayList<Emotion>();
        if (utility >= 0 && deltaLikelihood >= 0 || utility < 0 && deltaLikelihood < 0) {
            emotion.add(new Emotion("hope", intensity));
        } else {
            emotion.add(new Emotion("fear", intensity));
        }

        return emotion;
    }
}
