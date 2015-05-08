package agent;

import gamygdala.Gamygdala;

/**
 * Create Agent objects.
 */
public class AgentFactory {

    /**
     * Create a new Agent.
     *
     * @param agentName Name of the Agent.
     * @return The newly created Agent.
     */
    public static Agent createAgent(String agentName, Gamygdala engine) {

        Agent agent = new Agent(agentName, engine.gamygdalaMap);
        return agent;
    }

}
