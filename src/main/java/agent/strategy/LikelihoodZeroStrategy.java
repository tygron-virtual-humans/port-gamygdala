package agent.strategy;

import java.util.ArrayList;
import java.util.List;

import agent.data.Emotion;

/**
 * DetermineStrategy when Likelihood is 0.
 */
public class LikelihoodZeroStrategy implements DetermineStrategy {

    /**
     * Determine emotions based on three Goal parameters.
     *
     * @param utility         The goal utility.
     * @param deltaLikelihood The goal delta likelihood.
     * @return List of emotion names.
     */
    public List<Emotion> getEmotion(double utility, double deltaLikelihood, double intensity) {
        List<Emotion> emotions = new ArrayList<Emotion>();

        if (deltaLikelihood > 0.5) {
            emotions.add(
                    new Emotion(utility >= 0 ? "disappointment" : "relief",  intensity)
            );
        }
        emotions.add(
                new Emotion(
                        utility >= 0 ? "distress" : "joy", intensity)
        );

        return emotions;
    }
}
