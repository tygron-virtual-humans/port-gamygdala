package data_map;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import agent.Agent;
import gamygdala.Gamygdala;

/**
 * HashMap containing Agents.
 */
public class AgentMap extends HashMap<String, Agent> {

    /**
     * Serialization ID.
     */
    private static final long serialVersionUID = -2321551205683477281L;

    /**
     * Simple agent getter by name.
     *
     * @param agentName The name of the agent to be found.
     * @return Agent null or an agent reference that has the name property equal
     * to the agentName argument
     */
    public Agent getAgentByName(String agentName) {
        if (this.containsKey(agentName)) {
            return this.get(agentName);
        }
        Gamygdala.debug("Warning: agent \"" + agentName + "\" not found.");
        return null;
    }
    
    /**
     * Get the EntrySet iterator.
     * @return Iterator
     */
    public Iterator<Entry<String, Agent>> getIterator() {
        return this.entrySet().iterator();
    }

    /**
     * Print all emotional states to the console.
     *
     * @param gain Whether you want to print the gained (true) emotional states or
     *             non-gained (false).
     */
    public void printAllEmotions(boolean gain) {
        Agent agent;

        for (Map.Entry<String, Agent> pair : this.entrySet()) {
            agent = pair.getValue();

            agent.printEmotionalState(gain);
            agent.printRelations(null);
        }
    }


}
