package data;

import agent.Agent;
import exception.GoalCongruenceMapException;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A Belief contains information about events (Goals) and the amount of positive
 * or negative influence on a particular Agent.
 */
public class Belief {

    /**
     * The likelihood of this belief to be true.
     */
    private final double likelihood;

    /**
     * The Agent object of the causal agent of this belief.
     */
    private final Agent causalAgent;

    /**
     * A Map of Goals and their congruence.
     */
    private final HashMap<Goal, Double> goalCongruenceMap;

    /**
     * Whether or not this Belief is incremental.
     */
    private final boolean isIncremental;

    /**
     * This class is a data structure to store one Belief for an agent. A belief
     * is created and fed into a Gamygdala instance (method
     * Gamygdala.appraise()) for evaluation
     *
     * @param likelihood The likelihood of this belief to be true.
     * @param agent The Agent object of the causal agent of this belief.
     * @param affectedGoals An array of affected goals.
     * @param goalCongruences An array of the affected goals' congruences (i.e.,
     *            the extend to which this event is good or bad for a goal
     *            [-1,1]).
     * @param isIncremental Incremental evidence enforces gamygdala to see this
     *            event as incremental evidence for (or against) the list of
     *            goals provided, i.e, it will add or subtract this belief's
     *            likelihood*congruence from the goal likelihood instead of
     *            using the belief as "state" defining the absolute likelihood
     * @throws GoalCongruenceMapException
     */
    public Belief(double likelihood, Agent agent, ArrayList<Goal> affectedGoals, ArrayList<Double> goalCongruences,
            boolean isIncremental) throws GoalCongruenceMapException {
        this.isIncremental = isIncremental;

        this.likelihood = Math.min(1, Math.max(-1, likelihood));
        this.causalAgent = agent;

        if (affectedGoals.size() != goalCongruences.size()) {
            throw new GoalCongruenceMapException(
                    "Error: the congruence list does not have the same size as the affected goal list.");
        }

        this.goalCongruenceMap = new HashMap<Goal, Double>();

        // Add goals and congruences to Map.
        for (int i = 0; i < affectedGoals.size(); i++) {
            double congruence = Math.min(1, Math.max(-1, goalCongruences.get(i)));
            this.goalCongruenceMap.put(affectedGoals.get(i), congruence);
        }
    }

    /**
     * Get the likelihood of this belief.
     *
     * @return the likelihood
     */
    public double getLikelihood() {
        return likelihood;
    }

    /**
     * Get the names of the goals affected and their congruences.
     *
     * @return The names of the goals affected and their congruences.
     */
    public HashMap<Goal, Double> getGoalCongruenceMap() {
        return goalCongruenceMap;
    }

    /**
     * Return whether or not belief is incremental.
     *
     * @return the isIncremental
     */
    public boolean isIncremental() {
        return isIncremental;
    }

    /**
     * Get the Agent causal to this Belief.
     * 
     * @return Causal Agent.
     */
    public Agent getCausalAgent() {
        return causalAgent;
    }

    /**
     * Return string representation of Belief.
     */
    public String toString() {
        return "<Belief[CausalAgent = " + causalAgent + ", likelihood = " + likelihood + ", incremental = "
                + isIncremental + "]>";
    }

}
