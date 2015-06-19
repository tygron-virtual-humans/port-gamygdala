package agent;

import java.util.ArrayList;

import agent.data.Emotion;
import debug.Debug;
import decayfunction.DecayFunction;

/**
 * The collection of Agent Relations.
 */
public class AgentRelations extends ArrayList<Relation> {

    /**
     * ID for serialization.
     */
    private static final long serialVersionUID = 5959244595675904659L;

    /**
     * Sets the relation this agent has with the agent defined by agentName. If
     * the relation does not exist, it will be created, otherwise it will be
     * updated.
     *
     * @param agent The agent who is the target of the relation.
     * @param like The relation (between -1 and 1).
     * @return Relation Relation between this agent and an other one.
     */
    public Relation updateRelation(Agent agent, double like) {
        Relation relation;
        if (this.hasRelationWith(agent)) {
            relation = this.getRelation(agent);
            relation.setLike(like);
        } else {
            relation = new Relation(agent, like);
            this.add(relation);
        }
        return relation;
    }

    /**
     * Checks if this agent has a relation with the agent defined by agentName.
     *
     * @param agent The agent who is the target of the relation.
     * @return True if the relation exists, otherwise false.
     */
    public boolean hasRelationWith(Agent agent) {
        return (this.getRelation(agent) != null);
    }

    /**
     * Returns the relation object this agent has with the agent defined by
     * agentName.
     *
     * @param agent The agent who is the target of the relation.
     * @return Relation The relation object or null if non existing.
     */
    public Relation getRelation(Agent agent) {
        for (Relation relation : this) {
            if (relation.getAgent().equals(agent)) {
                return relation;
            }
        }
        return null;
    }

    /**
     * Decay the relations iin this ArrayList.
     * @param function the decay function
     * @param millisPassed the milliseconds passed
     */
    public void decay(DecayFunction function, long millisPassed) {
        for (Relation relation : this) {
            relation.decay(function, millisPassed);
        }
    }

    /**
     * Prints the relations this agent has with the agent defined by agentName.
     *
     * @param agent The agent who is the target of the relation. When omitted,
     *            all relations are printed.
     * @return String List of Relations with an Agent.
     */
    public String getRelationsString(Agent agent) {
        StringBuilder output = new StringBuilder();

        for (int i = 0; i < this.size(); i++) {
            if (agent == null || this.get(i).getAgent().equals(agent)) {
                output.append(this.get(i).getRelationString());
            }
            output.append(" for ").append(this.get(i).getAgent());

            if (i < this.size() - 1) {
                output.append(", and\n");
            }
        }
        return output.toString();
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
     * @return Emotion the evaluated social emotion
     */
    public Emotion evaluateSocialEmotion(Relation relation, double desirability) {
        Emotion emotion = new Emotion(null, 0);

        if (desirability >= 0) {
            if (relation.getLike() >= 0) {
                emotion.setName("happy-for");
            } else {
                emotion.setName("resentment");
            }
        } else {
            if (relation.getLike() >= 0) {
                emotion.setName("pity");
            } else {
                emotion.setName("gloating");
            }
        }

        emotion.setIntensity(Math.abs(desirability * relation.getLike()));

        if (emotion.getIntensity() != 0) {
            relation.addEmotion(emotion);
        }
        return emotion;
    }

    /**
     * Update Agent's emotion based on actions by other Agents,
     * when the agent is the affected agent.
     * @param desirability How much the current Agent desires the Goal subject
     *                      to the action.
     * @return The Emotion arising from the action
     */
    public Emotion updateEmotionAsCausalAgent(Agent causalAgent, double desirability) {
        Debug.debug("      Entering CASE 1.");
        Emotion emotion = desirability >= 0
                ? new Emotion("gratitude", Math.abs(desirability))
                : new Emotion("anger", Math.abs(desirability));

        Debug.debug("      Emotion: " + emotion);
        Relation relation = this.getRelation(causalAgent);
        if (relation == null) {
            relation = this.updateRelation(causalAgent, .0);
        }

        if (relation != null) {
            relation.addEmotion(emotion);
        }

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
    public Emotion updateEmotionAsAffectedAgent(Agent affectedAgent, double desirability) {
        Debug.debug("      Entering CASE 3.");
        Relation relation = this.getRelation(affectedAgent);
        if (relation == null) {
            return null;
        }
        Emotion emotion = null;

        if (relation.getLike() >= 0) {
            if (desirability >= 0) {
                emotion = new Emotion("gratification",
                        Math.abs(desirability * relation.getLike()));
            } else {
                emotion = new Emotion("remorse",
                        Math.abs(desirability * relation.getLike()));
            }
        }

        relation.addEmotion(emotion);

        return emotion;
    }
}
