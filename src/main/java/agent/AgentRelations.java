package agent;

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
     * Prints the relations this agent has with the agent defined by agentName.
     *
     * @param agent The agent who is the target of the relation. When omitted,
     *            all relations are printed.
     */
    public String printRelations(Agent agent) {
        String output = "";
        int emotionListSize;

        for (int i = 0; i < this.size(); i++) {
            if (agent == null || this.get(i).agent.equals(agent)) {
                emotionListSize = this.get(i).emotionList.size();
                for (int j = 0; j < emotionListSize; j++) {
                    output += get(i).emotionList.get(j).getName()
                            + "(" + get(i).emotionList.get(j).getIntensity() + ")";

                    if (j < emotionListSize - 1) {
                        output += ", and ";
                    }
                }
            }

            output += " for " + get(i).agent;

            if (i < this.size() - 1) {
                output += ", and\n";
            }
        }

        return output;
    }

}
