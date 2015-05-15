package gamygdala;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import agent.Agent;
import agent.Relation;
import data.Belief;
import data.Emotion;
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
     * Debug flag.
     */
    public static final boolean DEBUG = true;

    /**
     * The collection of agents in this Gamygdala instance.
     */
    public GamygdalaMap gamydgalaMap;

    /**
     * The decay function used to calculate emotion intensity.
     */
    public DecayFunction decayFunction;

    /**
     * The decay factor used in the DecayFunction.
     */
    public double decayFactor;

    /**
     * Timestamp of last emotion calculation.
     */
    private long lastMillis;

    /**
     * Seconds since last emotion calculation.
     */
    private long millisPassed;

    /**
     * Constructor for Gamygdala Emotion Engine.
     */
    public Gamygdala() {

        // Init agent and goal maps
        this.gamydgalaMap = new GamygdalaMap();

        // Set default decay factor
        this.decayFactor = .8;

        // Set default decay function
        this.decayFunction = new LinearDecay(this.decayFactor);

        // Record current time
        this.lastMillis = System.currentTimeMillis();
    }

    /**
     * Add an agent to this Gamygdala instance.
     *
     * @param agent The agent to be registered.
     */
    public void registerAgent(Agent agent) {
        // agent.setGamygdalaInstance(this);
        this.gamydgalaMap.getAgentMap().put(agent.name, agent);
    }

    /**
     * For every goal that NPC's or player characters can have you have to first
     * create a Goal object and then register it using this method. Registering
     * the goals makes sure that Gamygdala will be able to find the correct goal
     * references when a Beliefs about the game state comes in.
     *
     * @param goal The goal to be registered.
     */
    public void registerGoal(Goal goal) {
        this.gamydgalaMap.getGoalMap().put(goal.getName(), goal);
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
    public void appraise(Belief belief, Agent affectedAgent) {

        // Check belief
        if (belief == null) {
            Gamygdala.debug("Error: belief is null.");
            return;
        }

        // Check goal list size
        if (this.gamydgalaMap.getGoalMap().size() == 0) {
            Gamygdala.debug("Warning: no goals registered to Gamygdala.");
            return;
        }

        Goal currentGoal;
        Double currentCongruence;

        // Loop over all goals.
        for (Map.Entry<Goal, Double> goalPair : belief.getGoalCongruenceMap().entrySet()) {
            currentGoal = goalPair.getKey();
            currentCongruence = goalPair.getValue();

            // If current goal is really a goal
            if (currentGoal == null) {
                return;
            }

            // Calculate goal values
            double deltaLikelihood = this.calculateDeltaLikelihood(currentGoal, currentCongruence, belief.getLikelihood(), belief.isIncremental());

            // if affectedAgent is null, than calculate all agent emotions.
            if (affectedAgent == null) {
                this.appraiseByAllAgents(currentGoal, belief, deltaLikelihood);
            } else {
                this.evaluateAgentEmotions(affectedAgent, currentGoal, belief, deltaLikelihood);
            }
        }
    }

    public void appraiseAll(Belief belief) {
        // belief.calculate()
    }

    public void appraiseOne(Belief belief) {
        // belief.calculate()
    }

    /**
     * Appraise a belief by all agents in the engine.
     *
     * @param currentGoal The goal to be considered.
     * @param belief The belief to be appraised.
     * @param deltaLikelihood The delta likelihood of the goal.
     */
    private void appraiseByAllAgents(Goal currentGoal, Belief belief, double deltaLikelihood) {

        // Debug
        Gamygdala.debug("Evaluated goal: " + currentGoal.getName() + "(" + currentGoal.getUtility() + ", " + deltaLikelihood + ")");

        Agent owner;

        // Find the owners and update their emotional states
        Iterator<Entry<String, Agent>> it = this.gamydgalaMap.getAgentMap().getIterator();
        while (it.hasNext()) {

            Map.Entry<String, Agent> pair = it.next();
            owner = pair.getValue();

            if (owner != null && owner.hasGoal(currentGoal)) {

                Gamygdala.debug("....owned by " + owner.name);

                this.evaluateAgentEmotions(owner, currentGoal, belief, deltaLikelihood);
            }
        }

    }

    private void evaluateAgentEmotions(Agent owner, Goal currentGoal, Belief belief, double deltaLikelihood) {
        System.out.println("EvaluateAgentEmotions!");

        Iterator<Entry<String, Agent>> it = this.gamydgalaMap.getAgentMap().getIterator();
        Agent temp;
        Relation relation;

        this.evaluateInternalEmotion(currentGoal.getUtility(), deltaLikelihood, currentGoal.getLikelihood(), owner);
        owner.agentActions(belief.getCausalAgent(), owner, currentGoal.getUtility() * deltaLikelihood);

        // now check if anyone has a relation to this goal owner, and update the
        // social emotions accordingly.
        while (it.hasNext()) {

            Map.Entry<String, Agent> pair = it.next();
            temp = pair.getValue();

            relation = temp.getRelation(owner);
            if (relation != null) {

                Gamygdala.debug(temp.name + " has a relationship with " + owner.name);
                Gamygdala.debug(relation);

                // The agent has relationship with the goal owner which has
                // nonzero utility, add relational effects to the relations for
                // agent[k].
                temp.evaluateSocialEmotion(currentGoal.getUtility() * deltaLikelihood, relation);

                // also add remorse and gratification if conditions are met
                // within (i.e., agent[k] did something bad/good for owner)
                owner.agentActions(belief.getCausalAgent(), temp, currentGoal.getUtility() * deltaLikelihood);

            } else {
                Gamygdala.debug(temp.name + " has NO relationship with " + owner.name);
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
    private double calculateDeltaLikelihood(Goal goal, double congruence, double likelihood, boolean isIncremental) {

        Double oldLikelihood = goal.getLikelihood();
        double newLikelihood;

        if (!goal.isMaintenanceGoal() && (oldLikelihood >= 1 | oldLikelihood <= -1)) {
            return 0;
        }

        if (isIncremental) {
            newLikelihood = oldLikelihood + likelihood * congruence;
            newLikelihood = Math.max(Math.min(newLikelihood, 1), -1);
        } else {
            newLikelihood = (congruence * likelihood + 1.0) / 2.0;
        }

        goal.setLikelihood(newLikelihood);

        return newLikelihood - oldLikelihood;
    }

    /**
     * This method evaluates the event in terms of internal emotions that do not
     * need relations to exist, such as hope, fear, etc..
     *
     * @param utility the utility.
     * @param deltaLikelh the delta likelihood.
     * @param likelihood the likelihood.
     * @param agent the agent for which to evaluate the internal emotion.
     */
    private void evaluateInternalEmotion(double utility, double deltaLikelh, double likelihood, Agent agent) {

        if (agent == null) {
            Gamygdala.debug("Error: Gamygdala.evaluateInternalEmotion has been passed an empty Agent object.");
            return;
        }

        boolean positive = false;
        if (utility >= 0 && deltaLikelh >= 0 || utility < 0 && deltaLikelh < 0) {
            positive = true;
        }

        ArrayList<String> emotion = this.determineEmotions(utility, deltaLikelh, likelihood, positive);

        double intensity = Math.abs(utility * deltaLikelh);
        if (intensity != 0) {
            for (String emo : emotion) {
                agent.updateEmotionalState(new Emotion(emo, intensity));
            }
        }
    }

    private ArrayList<String> determineEmotions(double utility, double deltaLikelihood, double likelihood, boolean positive) {

        ArrayList<String> emotion = new ArrayList<String>();

        if (likelihood > 0 && likelihood < 1) {

            if (positive) {
                emotion.add("hope");
            } else {
                emotion.add("fear");
            }

        } else if (likelihood == 1) {

            if (utility >= 0) {
                if (deltaLikelihood < 0.5) {
                    emotion.add("satisfaction");
                }
                emotion.add("joy");
            } else {
                if (deltaLikelihood < 0.5) {
                    emotion.add("fear-confirmed");
                }
                emotion.add("distress");
            }

        } else if (likelihood == 0) {

            if (utility >= 0) {
                if (deltaLikelihood > 0.5) {
                    emotion.add("disappointment");
                }
                emotion.add("distress");
            } else {
                if (deltaLikelihood > 0.5) {
                    emotion.add("relief");
                }
                emotion.add("joy");
            }

        }

        return emotion;
    }

    /**
     * Facilitator to set the gain for the whole set of agents known to
     * TUDelft.Gamygdala. For more realistic, complex games, you would typically
     * set the gain for each agent type separately, to finetune the intensity of
     * the response.
     *
     * @param gain The gain value [0 and 20].
     */
    public void setGain(double gain) {

        if (gain <= 0 || gain > 20) {
            Gamygdala.debug("[Gamygdala.setGain] Error: " + "gain factor for appraisal integration must be between 0 and 20.");
            return;
        }

        Iterator<Entry<String, Agent>> it = this.gamydgalaMap.getAgentMap().getIterator();
        Agent temp;
        while (it.hasNext()) {
            Map.Entry<String, Agent> pair = it.next();

            temp = pair.getValue();
            if (temp != null) {
                temp.setGain(gain);
            } else {
                it.remove();
            }

        }

    }

    /**
     * This method decays for all registered agents the emotional state and
     * relations. It performs the decay according to the time passed, so longer
     * intervals between consecutive calls result in bigger clunky steps.
     * Typically this is called automatically when you use startDecay(), but you
     * can use it yourself if you want to manage the timing. This function is
     * keeping track of the millis passed since the last call, and will (try to)
     * keep the decay close to the desired decay factor, regardless the time
     * passed So you can call this any time you want (or, e.g., have the game
     * loop call it, or have e.g., Phaser call it in the plugin update, which is
     * default now). Further, if you want to tweak the emotional intensity decay
     * of individual agents, you should tweak the decayFactor per agent not the
     * "frame rate" of the decay (as this doesn't change the rate).
     */
    public void decayAll() {

        long now = System.currentTimeMillis();

        this.millisPassed = now - this.lastMillis;
        this.lastMillis = now;

        Iterator<Entry<String, Agent>> it = this.gamydgalaMap.getAgentMap().getIterator();
        Agent agent;
        while (it.hasNext()) {
            Map.Entry<String, Agent> pair = it.next();
            agent = pair.getValue();

            if (agent != null) {
                agent.decay(decayFunction, getMillisPassed());
            }
        }
    }

    /**
     * Sets the decay factor and function for emotional decay. It sets the decay
     * factor and type for emotional decay, so that an emotion will slowly get
     * lower in intensity. Whenever decayAll is called, all emotions for all
     * agents are decayed according to the factor and function set here.
     *
     * @param decayFactor The decayfactor used. A factor of 1 means no decay, a
     *            factor
     * @param decayFunction The decay function tobe used. choose between
     *            linearDecay or exponentialDecay (see the corresponding
     *            methods)
     */
    public void setDecay(double decayFactor, DecayFunction decayFunction) {

        if (decayFunction != null) {
            this.decayFunction = decayFunction;
        } else {
            Gamygdala.debug("[Gamygdala.setDecay] DecayFunction is null.");
        }

        this.decayFactor = decayFactor;
    }

    /**
     * Get the amount of milliseconds that has passed since the last decay
     * function was called.
     *
     * @return long Milliseconds passed.
     */
    public long getMillisPassed() {
        return millisPassed;
    }

    /**
     * Facilitator method to print all emotional states to the console.
     *
     * @param gain Whether you want to print the gained (true) emotional states
     *            or non-gained (false).
     */
    public void printAllEmotions(boolean gain) {

        Iterator<Entry<String, Agent>> it = this.gamydgalaMap.getAgentMap().getIterator();
        Agent agent;
        while (it.hasNext()) {
            Map.Entry<String, Agent> pair = it.next();
            agent = pair.getValue();

            agent.printEmotionalState(gain);
            agent.printRelations(null);
        }
    }

    /**
     * Print debug information to console if the debug flag is set to true.
     *
     * @param what Object to print to console.
     */
    public static void debug(Object what) {
        if (Gamygdala.DEBUG) {
            System.out.println(what);
        }
    }

}
