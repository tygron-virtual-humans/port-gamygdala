package agent;

import java.util.ArrayList;

import data.Emotion;
import data.Goal;
import data.map.GoalMap;
import decayfunction.DecayFunction;
import gamygdala.Engine;

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
    final GoalMap goals;
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

        // Init goal map.
        this.goals = new GoalMap();

        // Init relation and emotion collections
        this.currentRelations = new AgentRelations();
        this.internalState = new AgentInternalState();

        // Set gain
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
            Engine.debug("Error: gain factor for appraisal integration must be between 0 and 20.");
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
            Engine.debug("Error: cannot relate " + this + " to " + agent + " with intensity " + relation);
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
        //Check for empty Agents
        assert(causalAgent != null && affectedAgent != null);

        //Check iff only one of the agents is this.
        assert(this.equals(affectedAgent) ^ this.equals(causalAgent));

        Relation relation;
        Emotion emotion = null;
        if (this.equals(affectedAgent)) {
            Engine.debug("      Entering CASE 1.");
            emotion = new Emotion(
                    desirability >= 0 ? "gratitude" : "anger",
                    Math.abs(desirability)
            );
            Engine.debug("      Emotion: " + emotion);

            if (this.hasRelationWith(causalAgent)) {
                relation = this.getRelation(causalAgent);
            } else {
                relation = this.updateRelation(causalAgent, .0);
            }
        } else {
            //Check if the two Agent have a relation.
            assert(this.hasRelationWith(affectedAgent));

            Engine.debug("      Entering CASE 3.");
            relation = this.getRelation(affectedAgent);
            if (relation.like >= 0) {
                emotion = new Emotion(
                        desirability >= 0 ? "gratification" : "remorse",
                        Math.abs(desirability * relation.like)
                );
            }
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
        Engine.debug("   evaluateInternalEmotion: " + emotion);

        double intensity = Math.abs(utility * deltaLikelihood);
        if (intensity != 0) {
            for (String emo : emotion) {
                updateEmotionalState(new Emotion(emo, intensity));
            }
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
     * @param dfunc        The Decay Function used to decay emotions and relations.
     * @param millisPassed The time passed (in milliseconds) since the last
     *                     decay.
     */
    public void decay(DecayFunction dfunc, long millisPassed) {
        // Decay all internal emotions
        for (int i = 0; i < this.internalState.size(); i++) {

            // Decay emotion
            double newIntensity = dfunc.decay(this.internalState.get(i).getIntensity(), millisPassed);

            // If intensity is below zero, remove emotion
            if (newIntensity < 0) {
                this.internalState.remove(i);
            } else {
                this.internalState.get(i).setIntensity(newIntensity);
            }
        }

        // Decay all relations
        for (Relation currentRelation : this.currentRelations) {
            currentRelation.decay(dfunc, millisPassed);
        }
    }

    /**
     * Prints the relations this agent has with the agent defined by agentName.
     *
     * @param agent The agent who is the target of the relation. When omitted,
     *              all relations are printed.
     */
    public void printRelations(Agent agent) {
        String output = this.name + " has the following sentiments:\n   ";
        output += this.currentRelations.printRelations(agent);

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
        output += this.internalState.printEmotionalState(gained ? this.gain : null);

        System.out.println(output);
    }

    /**
     * String representation of Agent.
     */
    @Override
    public String toString() {
        return "<Agent[" + this.name + "]>";
    }
}
