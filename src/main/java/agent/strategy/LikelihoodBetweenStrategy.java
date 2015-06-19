package agent.strategy;

import java.util.ArrayList;
import java.util.List;

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
    public List<String> getEmotion(double utility, double deltaLikelihood) {
        List<String> emotion = new ArrayList<String>();

        if (utility >= 0 && deltaLikelihood >= 0 || utility < 0 && deltaLikelihood < 0) {
            emotion.add("hope");
        } else {
            emotion.add("fear");
        }

        return emotion;
    }

}
