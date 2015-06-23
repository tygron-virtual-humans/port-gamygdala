package agent.strategy;

import java.util.List;

import agent.data.Emotion;

/**
 * Determine Strategy interface.
 */
public interface DetermineStrategy {

    /**
     * Determine emotions based on three Goal parameters.
     *
     * @param utility         The goal utility.
     * @param deltaLikelihood The goal delta likelihood.
     * @param intensity       The goal intensity.
     * @return List of emotion names.
     */
    List<Emotion> getEmotion(double utility, double deltaLikelihood, double intensity);

}
