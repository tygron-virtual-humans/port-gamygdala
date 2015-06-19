package agent.strategy;

import java.util.ArrayList;
import java.util.List;

import agent.data.Emotion;

/**
 * DetermineStrategy when Likelihood is 1.
 */
public class LikelihoodOneStrategy implements  DetermineStrategy {

    /**
     * Determine emotions based on three Goal parameters.
     *
     * @param utility         The goal utility.
     * @param deltaLikelihood The goal delta likelihood.
     * @return List of emotion names.
     */
    public List<Emotion> getEmotion(double utility, double deltaLikelihood, double intensity) {
        List<Emotion> emotion = new ArrayList<Emotion>();

        if (deltaLikelihood < 0.5) {
            emotion.add(
                    new Emotion(utility >= 0 ? "satisfaction" : "fear-confirmed", intensity)
            );
        }
        emotion.add(
                new Emotion(utility >= 0 ? "joy" : "distress",  intensity)
        );

        return emotion;
    }
}
