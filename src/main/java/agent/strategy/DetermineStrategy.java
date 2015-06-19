package agent.strategy;

import java.util.List;

/**
 * Determine Strategy interface.
 */
public interface DetermineStrategy {

    /**
     * Determine emotions based on three Goal parameters.
     *
     * @param utility         The goal utility.
     * @param deltaLikelihood The goal delta likelihood.
     * @return List of emotion names.
     */
    List<String> getEmotion(double utility, double deltaLikelihood);

}
