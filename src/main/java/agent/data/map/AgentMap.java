package agent.data.map;

import java.util.HashMap;
import java.util.Map;

import agent.Agent;
import debug.Debug;

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
        Debug.debug("Warning: agent \"" + agentName + "\" not found.");
        return null;
    }

    /**
     * Print all emotional states to the console.
     *
     * @param gain Whether you want to print the gained (true) emotional states or
     *             non-gained (false).
     */
    public String toStringAllEmotions(boolean gain) {
        StringBuilder output = new StringBuilder();
        for (Map.Entry<String, Agent> pair : this.entrySet()) {
            Agent agent = pair.getValue();

            output.append(agent.toStringEmotionalState(gain));
            output.append(agent.toStringRelations(null));
        }
        return output.toString();
    }

    /**
     *
     */
    @Override
    public Agent put(String name, Agent agent) {
        if (!this.containsKey(agent.getName())) {
            return super.put(agent.getName(), agent);
        } else {
            Debug.debug("Warning: failed adding a second goal with the same name: " + agent.getName());
        }
        return null;
    }
}
