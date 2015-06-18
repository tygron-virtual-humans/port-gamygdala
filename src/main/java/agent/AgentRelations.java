package agent;

import decayfunction.DecayFunction;

import java.util.ArrayList;

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
     * Decay the relations iin this ArrayList
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
     */
    public String getRelationsString(Agent agent) {
        String output = "";

        for (int i = 0; i < this.size(); i++) {
            if (agent == null || this.get(i).getAgent().equals(agent)) {
                output += this.get(i).getRelationString();
            }

            output += " for " + this.get(i).getAgent();

            if (i < this.size() - 1) {
                output += ", and\n";
            }
        }

        return output;
    }
}
