import java.util.ArrayList;

/**
 * This is the main appraisal engine class taking care of interpreting a situation emotionally.
 * Typically you create one instance of this class and then register all agents (emotional entities) to it, as well as all goals.
 */
public class Gamygdala {

	/**
	 * Debug flag.
	 */
	public static final boolean debug = false;

	/**
	 * The collection of agents in this Gamygdala instance.
	 */
	public ArrayList<Agent> agents;

	/**
	 * The collection of goals in this Gamygdala instance.
	 */
	public ArrayList<Goal> goals;

	/**
	 * The decay function used to calculate emotion intensity.
	 */
	public DecayFunction decayFunction;

	/**
	 * The decay factor used in the DecayFunction.
	 */
	public double decayFactor;

	/**
	 * Timestamp of last emotion calculation.
	 */
	public long lastMillis;

	/**
	 * Seconds since last emotion calculation.
	 */
	public long millisPassed;

	public Gamygdala() {

		this.agents = new ArrayList<Agent>();
		this.goals = new ArrayList<Goal>();
		
		// Set default decay factor
		this.decayFactor = .8;
		
		// Set default decay function
		this.decayFunction = new LinearDecay(this.decayFactor);

	}

	/**
	 * Create and register a new Agent.
	 * 
	 * @param agentName Name of the Agent.
	 * @return The newly created Agent.
	 */
	public Agent createAgent(String agentName) {

		Agent agent = new Agent(agentName);
		this.registerAgent(agent);
		return agent;
	}

	/**
	 * Add an agent to this Gamygdala instance.
	 * 
	 * @param a The agent to be registered.
	 */
	public void registerAgent(Agent agent) {
		agent.setGamygdalaInstance(this);
		this.agents.add(agent);
	}
	
	public long getMillisPassed() {
		return millisPassed;
	}
}
