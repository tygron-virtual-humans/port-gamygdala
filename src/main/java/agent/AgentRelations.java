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
     */
    public void updateRelation(Agent agent, double like) {
        if (!this.hasRelationWith(agent)) {
            // This relation does not exist, just add it.
            add(new Relation(agent, like));
        } else {
            // The relation already exists, update it.
            Relation relation;
            for (int i = 0; i < size(); i++) {
                relation = get(i);
                if (relation.agent.equals(agent)) {
                    relation.like = like;
                }
            }
        }
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
        Relation relation;
        for (int i = 0; i < size(); i++) {
            relation = get(i);
            if (relation.agent.equals(agent)) {
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

        int size = size();
        int emotionListSize = 0;
        
        for (int i = 0; i < size(); i++) {

            if (agent == null || get(i).agent.equals(agent)) {
                emotionListSize = get(i).emotionList.size();
                for (int j = 0; j < emotionListSize; j++) {
                    output += get(i).emotionList.get(j).name + "(" + get(i).emotionList.get(j).intensity + ")";
                    
                    if (j < emotionListSize - 1) {
                        output += ", and ";
                    }
                }
            }

            output += " for " + get(i).agent;

            if (i < size() - 1) {
                output += ", and\n";
            }
        }

        return output;
    }

}
