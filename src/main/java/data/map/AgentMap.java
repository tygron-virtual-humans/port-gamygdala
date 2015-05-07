package data.map;

import agent.Agent;
import gamygdala.Gamygdala;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by svenpopping on 06/05/15.
 */
public class AgentMap extends HashMap<String, Agent> {

    public AgentMap() {

    }

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

    public Iterator getIterator() {
        return this.entrySet().iterator();
    }

    /**
     * Facilitator method to print all emotional states to the console.
     *
     * @param gain Whether you want to print the gained (true) emotional states or
     *             non-gained (false).
     */
    public void printAllEmotions(boolean gain) {
        Iterator<Entry<String, Agent>> it = this.getIterator();
        Agent agent;

        while (it.hasNext()) {
            Map.Entry<String, Agent> pair = it.next();
            agent = pair.getValue();

            agent.printEmotionalState(gain);
            agent.printRelations(null);
        }
    }


}
