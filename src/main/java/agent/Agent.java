package agent;

import java.util.List;

import agent.data.Emotion;
import agent.data.Goal;
import agent.data.map.GoalMap;
import debug.Debug;

/**
 * The main interacting character in the Gamygdala engine.
 */
public class Agent {

    /**
     * Default gain.
     */
    public static final double DEFAULT_GAIN = 1;
    /**
     * The name of this Agent.
     */
    private final String name;
    /**
     * The gain for this agent. Must be between 0 and 20 inclusive.
     */
    private double gain;
    /**
     * Collection of goals for this Agent.
     */
    private final GoalMap goals;
    /**
     * Collection of relations for this Agent.
     */
    private final AgentRelations currentRelations;
    /**
     * Collection of emotions for this Agent.
     */
    private final AgentInternalState internalState;

    /**
     * Create new Agent.
     * @param agentName Agent name.
     */
    public Agent(String agentName) {
        this.name = agentName;

        this.goals = new GoalMap();

        this.currentRelations = new AgentRelations();
        this.internalState = new AgentInternalState();

        this.gain = Agent.DEFAULT_GAIN;
    }

    /**
     * Get name of Agent.
     * @return String The name of the Agent
     */
    public String getName() {
        return name;
    }

    /**
     * Get gain of Agent.
     * @return double the gain of the Agent
     */
    public double getGain() {
        return gain;
    }

    /**
     * Get Goals of the Agent.
     * @return GoalMap map of all the goal for the Agent
     */
    public GoalMap getGoals() {
        return goals;
    }

    /**
     * Get InternalState of the Agent.
     * @return AgentInternalState List of Emotions of the Agent
     */
    public AgentInternalState getInternalState() {
        return internalState;
    }

    /**
     * Get current relations from this Agent.
     * @return AgentRelations List of Relations
     */
    public AgentRelations getCurrentRelations() {
        return this.currentRelations;
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
     * @param newGain The gain value [0 and 20].
     */
    public void setGain(double newGain) {
        if (newGain > 0 && newGain <= 20) {
            this.gain = newGain;
        } else {
            Debug.debug("Error: gain factor for appraisal integration must be between 0 and 20.");
        }
    }

    /**
     * Sets the relation this agent has with the agent defined by agentName. If
     * the relation does not exist, it will be created, otherwise it will be
     * updated.
     *
     * @param other    The agent who is the target of the relation.
     * @param relation The relation (between -1 and 1).
     * @return Relation the relation between this and other
     */
    public Relation updateRelation(Agent other, double relation) {
        if (relation >= -1 && relation <= 1) {
            return this.currentRelations.updateRelation(other, relation);
        } else {
            Debug.debug("Error: cannot relate " + this + " to " + other + " with intensity " + relation);
        }
        return null;
    }

    /**
     * Checks if this agent has a relation with the agent defined by agentName.
     *
     * @param other The agent who is the target of the relation.
     * @return Boolean if the relation exists, otherwise false.
     */
    public boolean hasRelationWith(Agent other) {
        return this.getCurrentRelations().hasRelationWith(other);
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
        Emotion emotion = this.getCurrentRelations().updateEmotionAsCausalAgent(causalAgent, desirability);

        this.getInternalState().updateState(emotion);
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
        Emotion emotion = this.getCurrentRelations().updateEmotionAsAffectedAgent(affectedAgent, desirability);

        this.getInternalState().updateState(emotion);
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
        List<Emotion> emotions = PADMap.determineEmotions(utility, deltaLikelihood, likelihood);
        Debug.debug("   evaluateInternalEmotion: " + emotions);

        for (Emotion emotion : emotions) {
            this.getInternalState().updateState(emotion);
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
        Relation relation = agent.currentRelations.getRelation(this);
        if (relation != null) {
            Debug.debug("   Processing relation: " + relation);

            Emotion emotion = agent.getCurrentRelations().evaluateSocialEmotion(relation, desirability);
            this.getInternalState().updateState(emotion);
            agent.agentActions(this, causalAgent, desirability);
        }
    }

    /**
     * Prints the relations this agent has with the agent defined by agentName.
     *
     * @param agent The agent who is the target of the relation. When omitted,
     *              all relations are printed.
     */
    public String toStringRelations(Agent agent) {
        String output = this.name + " has the following sentiments:\n   ";
        output += this.getCurrentRelations().getRelationsString(agent);

        return output;
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
    public String toStringEmotionalState(boolean gained) {
        String output = this.name + " feels ";
        output += this.getInternalState().toString(gained ? this.gain : null);

        return output;
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
        if ((o instanceof Agent)) {
            Agent agent = (Agent) o;

            return Double.compare(agent.gain, gain) == 0
                    && !(name != null ? !name.equals(agent.name) : agent.name != null)
                    && goals.equals(agent.goals)
                    && getCurrentRelations().equals(agent.getCurrentRelations())
                    && internalState.equals(agent.internalState);
        }
        return false;
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
        result = 31 * result + goals.hashCode();
        result = 31 * result + getCurrentRelations().hashCode();
        result = 31 * result + internalState.hashCode();
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
