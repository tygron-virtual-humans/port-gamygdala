package gamygdala;

import java.util.Map;

import agent.Agent;
import agent.Relation;
import data.Belief;
import data.Goal;
import data.map.GamygdalaMap;
import decayfunction.DecayFunction;
import decayfunction.LinearDecay;

/**
 * This is the main appraisal engine class taking care of interpreting a
 * situation emotionally. Typically you create one instance of this class and
 * then register all agents (emotional entities) to it, as well as all goals.
 */
public class Gamygdala {

    /**
     * The collection of agents in this Gamygdala instance.
     */
    private GamygdalaMap gamygdalaMap;

    /**
     * The decay function used to calculate emotion intensity.
     */
    public DecayFunction decayFunction;

    /**
     * The decay factor used in the DecayFunction.
     */
    public double decayFactor;

    /**
     * Constructor for Gamygdala Emotion Engine.
     */
    public Gamygdala() {

        // Init agent and goal maps
        this.gamygdalaMap = new GamygdalaMap();

        // Set default decay factor
        this.decayFactor = .8;

        // Set default decay function
        this.decayFunction = new LinearDecay(this.decayFactor);

    }

    /**
     * The main emotional interpretation logic entry point. Performs the
     * complete appraisal of a single event (belief) for all agents
     * (affectedAgent == null) or for only one agent. If affectedAgent is set,
     * then the complete appraisal logic is executed including the effect on
     * relations (possibly influencing the emotional state of other agents), but
     * only if the affected agent (the one owning the goal) == affectedAgent
     * this is sometimes needed for efficiency, if you as a game developer know
     * that particular agents can never appraise an event, then you can force
     * Gamygdala to only look at a subset of agents. Gamygdala assumes that the
     * affectedAgent is indeed the only goal owner affected, that the belief is
     * well-formed, and will not perform any checks, nor use Gamygdala's list of
     * known goals to find other agents that share this goal. (!!!)
     *
     * @param belief The current event to be appraised.
     * @param affectedAgent The reference to the agent who appraises the event.
     */
    public boolean appraise(Belief belief, Agent affectedAgent) {

        Engine.debug("\n=====\nStarting appraisal for:\n" + belief + "\nwith affectedAgent: " + affectedAgent + "\n=====\n");

        // Check belief
        if (belief == null) {
            Engine.debug("Error: belief is null.");
            return false;
        }

        // Check goal list size
        if (this.gamygdalaMap.getGoalMap().size() == 0) {
            Engine.debug("Warning: no goals registered to Gamygdala.");
            return true;
        }

        Goal currentGoal;
        Double currentCongruence;

        // Loop over all goals.
        for (Map.Entry<Goal, Double> goalPair : belief.getGoalCongruenceMap().entrySet()) {
            currentGoal = goalPair.getKey();
            currentCongruence = goalPair.getValue();

            // If current goal is really a goal
            if (currentGoal == null) {
                continue;
            }

            Engine.debug("Processing goal: " + currentGoal);

            // Calculate goal values
            double deltaLikelihood = this.calculateDeltaLikelihood(currentGoal, currentCongruence, belief.getLikelihood(), belief.isIncremental());

            Engine.debug("   deltaLikelihood: " + deltaLikelihood);

            // if affectedAgent is null, calculate emotions for all agents.
            if (affectedAgent == null) {

                Agent owner;

                // Loop all agents
                for (Map.Entry<String, Agent> pair : gamygdalaMap.getAgentSet()) {
                    owner = pair.getValue();

                    // Update emotional state if has goal
                    if (owner != null && owner.hasGoal(currentGoal)) {
                        this.evaluateAgentEmotions(owner, currentGoal, belief, deltaLikelihood);
                    }
                }

            } else {

                // Update emotional state for single agent
                this.evaluateAgentEmotions(affectedAgent, currentGoal, belief, deltaLikelihood);
            }
            
            // Newline
            Engine.debug("");
        }
        
        Engine.debug("\n=====\nFinished appraisal round\n=====\n");

        return true;
    }

    /**
     * Re-evaluate an Agent's emotions by processing a Goal and a Belief.
     * 
     * @param owner The Goal owner.
     * @param currentGoal
     * @param belief
     * @param deltaLikelihood
     */
    void evaluateAgentEmotions(Agent owner, Goal currentGoal, Belief belief, double deltaLikelihood) {

        double utility = currentGoal.getUtility();
        double likelihood = currentGoal.getLikelihood();
        double desirability = utility * deltaLikelihood;

        Engine.debug("   utility: " + utility + "\n   likelihood: " + likelihood);

        // Determine new emotions for Agent
        owner.evaluateInternalEmotion(utility, deltaLikelihood, likelihood);

        // also add remorse and gratification if conditions are met
        // (i.e., agent did something bad/good for owner)
        owner.agentActions(owner, belief.getCausalAgent(), utility * deltaLikelihood);

        Agent agent;
        Relation relation;

        // Now check if anyone has a relation with this goal owner, and update
        // the social emotions accordingly.
        for (Map.Entry<String, Agent> pair : gamygdalaMap.getAgentSet()) {
            agent = pair.getValue();

            relation = agent.getRelation(owner);
            if (relation != null) {
                
                Engine.debug("   Processing relation: " + relation);
                
                // The agent has relationship with the goal owner which has
                // nonzero utility, add relational effects to the relations for
                // agent[k].
                agent.evaluateSocialEmotion(desirability, relation);

                // also add remorse and gratification if conditions are met
                // (i.e., agent did something bad/good for owner)
                agent.agentActions(owner, belief.getCausalAgent(), desirability);

            }
        }
    }

    /**
     * Decay emotional state of all Agents.
     */
    public void decayAll(long lastMillis, long currentMillis) {

        long millisPassed = currentMillis - lastMillis;

        Agent agent;
        for (Map.Entry<String, Agent> pair : gamygdalaMap.getAgentSet()) {
            agent = pair.getValue();

            if (agent != null) {
                agent.decay(decayFunction, millisPassed);
            }
        }
    }

    /**
     * Defines the change in a goal's likelihood due to the congruence and
     * likelihood of a current event. We cope with two types of beliefs:
     * incremental and absolute beliefs. Incrementals have their likelihood
     * added to the goal, absolute define the current likelihood of the goal And
     * two types of goals: maintenance and achievement. If an achievement goal
     * (the default) is -1 or 1, we can't change it any more (unless externally
     * and explicitly by changing the goal.likelihood).
     *
     * @param goal the goal for which to calculate the likelihood.
     * @param congruence how much is it affecting the agent.
     * @param likelihood the likelihood.
     * @param isIncremental if the goal is incremental
     * @return the delta likelihood.
     */
    double calculateDeltaLikelihood(Goal goal, double congruence, double likelihood, boolean isIncremental) {

        Double oldLikelihood = goal.getLikelihood();
        double newLikelihood;

        if (!goal.isMaintenanceGoal() && (oldLikelihood >= 1 || oldLikelihood <= -1)) {
            return 0;
        }

        if (isIncremental) {
            newLikelihood = oldLikelihood + likelihood * congruence;
            newLikelihood = Math.max(Math.min(newLikelihood, 1), -1);
        } else {
            newLikelihood = (congruence * likelihood + 1d) / 2d;
        }

        goal.setLikelihood(newLikelihood);

        return newLikelihood - oldLikelihood;
    }

    /**
     * Get the GamygdalaMap containing all Agents and Goals.
     * @return GamygdalaMap
     */
    public GamygdalaMap getGamygdalaMap() {
        return gamygdalaMap;
    }

}
