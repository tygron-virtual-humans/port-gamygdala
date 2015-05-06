package agent;

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
  public Agent createAgent(String agentName) {

    Agent agent = new Agent(agentName);
    return agent;
  }

}
