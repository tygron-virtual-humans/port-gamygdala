package agent;

import java.util.ArrayList;

import data.Emotion;
import data.Goal;
import data.map.GoalMap;
import debug.Debug;
import decayfunction.DecayFunction;
import gamygdala.Gamygdala;

/**
 * The main interacting character in the Gamygdala engine.
 */
public class Agent {

    public static final double DEFAULT_GAIN = 1;
    /**
     * The name of this Agent.
     */
    public final String name;
    /**
     * The gain for this agent. Must be between 0 and 20 inclusive.
     */
    public double gain;
    /**
     * Collection of goals for this Agent.
     */
    GoalMap goals;
    /**
     * Collection of relations for this Agent.
     */
    AgentRelations currentRelations;
    /**
     * Collection of emotions for this Agent.
     */
    AgentInternalState internalState;

    /**
     * Create new Agent.
     *
     * @param name Agent name.
     */
    public Agent(final String name) {
        this.name = name;

        this.goals = new GoalMap();

        this.currentRelations = new AgentRelations();
        this.internalState = new AgentInternalState();

        this.gain = Agent.DEFAULT_GAIN;
    }

    /**
     * Get current relations from this Agent.
     * @return AgentRelations List of Relations
     */
    public AgentRelations getCurrentRelations() {
        return this.currentRelations;
    }

    /**
     * Add Goal.
     *
     * @param goal Goal to add.
     * @return True if goal was added successfully, false if not.
     */
    public boolean addGoal(Goal goal) {
        return this.goals.addGoal(goal);
    }

    /**
     * Remove Goal.
     *
     * @param goal Goal to remove.
     * @return True if goal was removed successfully, false if not.
     */
    public boolean removeGoal(Goal goal) {
        return this.goals.removeGoal(goal);
    }

    /**
     * Check if this GoalMap has a specific Goal (by object).
     *
     * @param goal Goal to check for.
     * @return True if Agent has goal, false if not.
     */
    public boolean hasGoal(Goal goal) {
        return this.goals.hasGoal(goal);
    }

    /**
     * If this agent has a goal named goalName, this method returns that goal.
     *
     * @param goalName The name of the goal to be found.
     * @return the reference to the goal.
     */
    public Goal getGoalByName(String goalName) {
        return this.goals.getGoalByName(goalName);
    }

    /**
     * Sets the gain for this agent.
     *
     * @param gain The gain value [0 and 20].
     */
    public void setGain(double gain) {
        if (gain > 0 && gain <= 20) {
            this.gain = gain;
        } else {
            Debug.debug("Error: gain factor for appraisal integration must be between 0 and 20.");
        }
    }

    /**
     * Pass through function to AgentInternalState.
     *
     * @param emotion The emotion with which this Agent should be updated.
     */
    public void updateEmotionalState(Emotion emotion) {
        this.internalState.updateEmotionalState(emotion);
    }

    /**
     * Pass through function to AgentInternalState.
     *
     * @param gain The gain factor. Leave blank (null) to ignore gain.
     * @return An array of emotions.
     */
    public AgentInternalState getEmotionalState(Double gain) {
        return this.internalState.getEmotionalState(gain);
    }

    /**
     * Sets the relation this agent has with the agent defined by agentName. If
     * the relation does not exist, it will be created, otherwise it will be
     * updated.
     *
     * @param agent    The agent who is the target of the relation.
     * @param relation The relation (between -1 and 1).
     */
    public Relation updateRelation(Agent agent, double relation) {
        if (relation >= -1 && relation <= 1) {
            return this.currentRelations.updateRelation(agent, relation);
        } else {
            Debug.debug("Error: cannot relate " + this + " to " + agent + " with intensity " + relation);
        }
        return null;
    }

    /**
     * Checks if this agent has a relation with the agent defined by agentName.
     *
     * @param agent The agent who is the target of the relation.
     * @return Boolean if the relation exists, otherwise false.
     */
    public boolean hasRelationWith(Agent agent) {
        return this.currentRelations.hasRelationWith(agent);
    }

    /**
     * Returns the relation object this agent has with the agent defined by
     * agentName.
     *
     * @param agent The agent who is the target of the relation.
     * @return Relation The relation object or null if non existing.
     */
    public Relation getRelation(Agent agent) {
        return this.currentRelations.getRelation(agent);
    }

    /**
     * Update Agent's emotion based on actions by other Agents.
     *
     * @param affectedAgent The Agent affected by the action.
     * @param causalAgent   The Agent causal to the action.
     * @param desirability  How much the current Agent desires the Goal subject
     *                      to the action.
     * @return The Emotion arising from the action.
     */
    public Emotion agentActions(Agent affectedAgent, Agent causalAgent, double desirability) {
        if (causalAgent == null || affectedAgent == null) {
            return null;
        }

        if (this.equals(affectedAgent) && !this.equals(causalAgent)) {
            return this.updateEmotionAsCausalAgent(causalAgent, desirability);
        } else if (this.equals(causalAgent) && !this.equals(affectedAgent)) {
            return this.updateEmotionAsAffectedAgent(affectedAgent, desirability);
        }
        return null;
    }

    /**
     * Update Agent's emotion based on actions by other Agents,
     * when the agent is the affected agent.
     * @param causalAgent The Agent causal to the action.
     * @param desirability How much the current Agent desires the Goal subject
     *                      to the action.
     * @return The Emotion arising from the action
     */
    private Emotion updateEmotionAsCausalAgent(Agent causalAgent, double desirability) {
        Debug.debug("      Entering CASE 1.");
        Emotion emotion = new Emotion(desirability >= 0 ? "gratitude" : "anger",
                Math.abs(desirability));
        Debug.debug("      Emotion: " + emotion);

        Relation relation = this.getRelation(causalAgent);
        if (relation == null) {
            relation = this.updateRelation(causalAgent, .0);
        }

        relation.addEmotion(emotion);
        this.updateEmotionalState(emotion);

        return emotion;
    }

    /**
     * Update Agent's emotion based on actions by other Agents,
     * if the agent is the affected agent.
     *
     * @param affectedAgent The Agent affected by the action.
     * @param desirability  How much the current Agent desires the Goal subject
     *                      to the action.
     * @return The Emotion arising from the action.
     */
    private Emotion updateEmotionAsAffectedAgent(Agent affectedAgent, double desirability) {
        Debug.debug("      Entering CASE 3.");
        Relation relation = this.getRelation(affectedAgent);
        if (relation == null) {
            return null;
        }
        Emotion emotion = null;
        if (relation.getLike() >= 0) {
            emotion = new Emotion(desirability >= 0 ? "gratification" : "remorse",
                    Math.abs(desirability * relation.getLike()));
        }

        relation.addEmotion(emotion);
        this.updateEmotionalState(emotion);

        return emotion;
    }

    /**
     * This function is used to evaluate happy-for, pity, gloating or
     * resentment. Emotions that arise when we evaluate events that affect goals
     * of others.
     *
     * @param desirability The desirability is the desirability from the goal
     *                     owner's perspective.
     * @param relation     A relation object between the agent being evaluated and
     *                     the goal owner of the affected goal.
     */
    public Emotion evaluateSocialEmotion(double desirability, Relation relation) {
        Emotion emotion = new Emotion(null, 0);
        if (desirability >= 0) {
            emotion.setName(relation.getLike() >= 0 ? "happy-for" : "resentment");
        } else {
            emotion.setName(relation.getLike() >= 0 ? "pity" : "gloating");
        }
        emotion.setIntensity(Math.abs(desirability * relation.getLike()));

        if (emotion.getIntensity() != 0) {
            relation.addEmotion(emotion);
            this.updateEmotionalState(emotion);
        }

        return emotion;
    }

    /**
     * This method evaluates the event in terms of internal emotions that do not
     * need relations to exist, such as hope, fear, etc..
     *
     * @param utility         the utility.
     * @param deltaLikelihood the delta likelihood.
     * @param likelihood      the likelihood.
     */
    public void evaluateInternalEmotion(double utility, double deltaLikelihood, double likelihood) {
        ArrayList<String> emotion = Emotion.determineEmotions(utility, deltaLikelihood, likelihood);
        Debug.debug("   evaluateInternalEmotion: " + emotion);

        double intensity = Math.abs(utility * deltaLikelihood);
        if (intensity != 0) {
            for (String emo : emotion) {
                updateEmotionalState(new Emotion(emo, intensity));
            }
        }
    }

    /**
     * evaluateSocialEmotion; The agent has relationship with the goal owner
     * which has nonzero utility, add relational effects to the relations
     * for agent[k]. agentActions; also add remorse and gratification if
     * conditions are met (i.e., agent did something bad / good for owner).
     * @param agent the agent for which to check the relation
     * @param causalAgent the causalAgent of the belief
     * @param desirability the desirability of the goal
     */
    public void evaluateRelationWithAgent(Agent agent, Agent causalAgent, double desirability) {
        Relation relation = agent.getRelation(this);
        if (relation != null) {
            Debug.debug("   Processing relation: " + relation);

            agent.evaluateSocialEmotion(desirability, relation);
            agent.agentActions(this, causalAgent, desirability);

        }
    }

    /**
     * This method decays the emotional state and relations according to the
     * decay factor and function defined in gamygdala. Typically this is called
     * automatically when you use startDecay() in Gamygdala, but you can use it
     * yourself if you want to manage the timing. This function is keeping track
     * of the millis passed since the last call, and will (try to) keep the
     * decay close to the desired decay factor, regardless the time passed So
     * you can call this any time you want (or, e.g., have the game loop call
     * it, or have e.g., Phaser call it in the plugin update, which is default
     * now). Further, if you want to tweak the emotional intensity decay of
     * individual agents, you should tweak the decayFactor per agent not the
     * "frame rate" of the decay (as this doesn't change the rate).
     *
     * @param function        The Decay Function used to decay emotions and relations.
     * @param millisPassed The time passed (in milliseconds) since the last
     *                     decay.
     */
    public void decay(DecayFunction function, long millisPassed) {
        this.internalState.decay(function, millisPassed);
        this.currentRelations.decay(function, millisPassed);
    }

    /**
     * Prints the relations this agent has with the agent defined by agentName.
     *
     * @param agent The agent who is the target of the relation. When omitted,
     *              all relations are printed.
     */
    public void printRelations(Agent agent) {
        String output = this.name + " has the following sentiments:\n   ";
        output += this.currentRelations.getRelationsString(agent);

        System.out.println(output);
    }

    /**
     * This function prints to the console either the state as is (gain=null) or
     * a state based on gained limiter (limited between 0 and 1), of which the
     * gain can be set by using setGain(gain). A high gain factor works well
     * when appraisals are small and rare, and you want to see the effect of
     * these appraisals. A low gain factor (close to 0 but in any case below 1)
     * works well for high frequency and/or large appraisals, so that the effect
     * of these is dampened.
     *
     * @param gained Print only gained emotions or not.
     */
    public void printEmotionalState(boolean gained) {
        String output = this.name + " feels ";
        output += this.internalState.getEmotionalStateString(gained ? this.gain : null);

        System.out.println(output);
    }

    /**
     * Check is the Object is equal to this Agent Object.
     * @param o the Object to test against this Agent Object
     * @return whether the Object o is equal to this Agent Object
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Agent)) {
            return false;
        }
        Agent agent = (Agent) o;

        if (Double.compare(agent.gain, gain) != 0) {
            return false;
        }
        if (name != null ? !name.equals(agent.name) : agent.name != null) {
            return false;
        }
        if (!goals.equals(agent.goals)) {
            return false;
        }
        if (getCurrentRelations() != null) {
            if (!getCurrentRelations().equals(agent.getCurrentRelations())) {
                return false;
            }
        } else {
            if (agent.getCurrentRelations() != null) {
                return false;
            }
        }
        return !(internalState != null ? !internalState.equals(agent.internalState) : agent.internalState != null);

    }

    /**
     * generate the hashcode of this Agent.
     * @return the hashcode of the Agent
     */
    @Override
    public int hashCode() {
        int result;
        long temp;
        result = name != null ? name.hashCode() : 0;
        temp = Double.doubleToLongBits(gain);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (goals.hashCode());
        result = 31 * result + (getCurrentRelations() != null ? getCurrentRelations().hashCode() : 0);
        result = 31 * result + (internalState != null ? internalState.hashCode() : 0);
        return result;
    }

    /**
     * String representation of Agent.
     */
    @Override
    public String toString() {
        return "<Agent[" + this.name + "]>";
    }

}
