package agent.strategy;

import java.util.ArrayList;
import java.util.List;

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
    public List<String> getEmotion(double utility, double deltaLikelihood) {
        List<String> emotion = new ArrayList<String>();

        if (deltaLikelihood < 0.5) {
            emotion.add(utility >= 0 ? "satisfaction" : "fear-confirmed");
        }
        emotion.add(utility >= 0 ? "joy" : "distress");

        return emotion;
    }
}
