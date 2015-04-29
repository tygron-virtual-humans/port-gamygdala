import java.util.ArrayList;
import java.util.HashMap;

/**
 * This is the main appraisal engine class taking care of interpreting a situation emotionally.
 * Typically you create one instance of this class and then register all agents (emotional entities) to it, as well as all goals.
 */
public class Gamygdala {

	/**
	 * Debug flag.
	 */
	public static final boolean debug = true;

	/**
	 * The collection of agents in this Gamygdala instance.
	 */
	public HashMap<String, Agent> agents;

	/**
	 * The collection of goals in this Gamygdala instance.
	 */
	public HashMap<String, Goal> goals;

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
	private long lastMillis;

	/**
	 * Seconds since last emotion calculation.
	 */
	private long millisPassed;

	public Gamygdala() {

		// Init agent and goal maps
		this.agents = new HashMap<String, Agent>();
		this.goals = new HashMap<String, Goal>();

		// Set default decay factor
		this.decayFactor = .8;

		// Set default decay function
		this.decayFunction = new LinearDecay(this.decayFactor);

		// Record current time
		this.lastMillis = System.currentTimeMillis();
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
		this.agents.put(agent.name, agent);
	}

	/**
	 * A facilitator method to create a goal for a particular agent, that also registers the goal to the agent and Gamygdala.
	 * This method is thus handy if you want to keep all Gamygdala logic internal to Gamygdala.
	 * However, if you want to do more sophisticated stuff (e.g., goals for multiple agents, keep track of your own list of goals to also remove them, appraise events per agent without the need for Gamygdala to keep track of goals, etc...) this method will probably be doing too much.
	 * 
	 * @param agentName The agent's name to which the newly created goal has to be added.
	 * @param goalName The goal's name.
	 * @param goalUtility The goal's utility.
	 * @return Goal - a goal reference to the newly created goal.
	 */
	public Goal createGoalForAgent(Agent agent, String goalName, double goalUtility) {
		return this.createGoalForAgent(agent, goalName, goalUtility, false);
	}

	/**
	 * A facilitator method to create a goal for a particular agent, that also registers the goal to the agent and Gamygdala.
	 * This method is thus handy if you want to keep all Gamygdala logic internal to Gamygdala.
	 * However, if you want to do more sophisticated stuff (e.g., goals for multiple agents, keep track of your own list of goals to also remove them, appraise events per agent without the need for Gamygdala to keep track of goals, etc...) this method will probably be doing too much.
	 * 
	 * @param agentName The agent's name to which the newly created goal has to be added.
	 * @param goalName The goal's name.
	 * @param goalUtility The goal's utility.
	 * @return Goal - a goal reference to the newly created goal.
	 */
	public Goal createGoalForAgent(Agent agent, String goalName, double goalUtility, boolean isMaintenanceGoal) {

		if (this.agents.containsKey(agent.name)) {
			Goal goal = this.getGoalByName(goalName);

			// Check if goal already exists
			if (goal != null) {

				if (isMaintenanceGoal) {
					goal.isMaintenanceGoal = isMaintenanceGoal;
				}

				Gamygdala.debug("Warning: I cannot make a new goal with the same name " + goalName + " as one is registered already. I assume the goal is a common goal and will add the already known goal with that name to the agent " + agent);
			} else {
				// .. else, create new goal and add to this Gamygdala instance
				goal = new Goal(goalName, goalUtility, isMaintenanceGoal);
				this.registerGoal(goal);
			}

			// Register goal with agent
			agent.addGoal(goal);

			return goal;
		} else {
			Gamygdala.debug("Error: agent " + agent + " does not exist, so I cannot create a goal for it.");
			return null;
		}

	}

	/**
	 * For every goal that NPC's or player characters can have you have to first create a Goal object and then register it using this method.
	 * Registering the goals makes sure that Gamygdala will be able to find the correct goal references when a Beliefs about the game state comes in.
	 * 
	 * @param goal The goal to be registered.
	 */
	public void registerGoal(Goal goal) {
		if (!this.goals.containsKey(goal.name))
			this.goals.put(goal.name, goal);
		else {
			Gamygdala.debug("Warning: failed adding a second goal with the same name: " + goal.name);
		}
	}

	/**
	 * A facilitator method to create a relation between two agents. Both source and target have to exist and be registered with this Gamygdala instance.
	 * This method is thus handy if you want to keep all gamygdala logic internal to Gamygdala.
	 * 
	 * @param sourceName The agent who has the relation (the source)
	 * @param targetName The agent who is the target of the relation (the target)
	 * @param relation The relation (between -1 and 1).
	 */
	public void createRelation(String sourceName, String targetName, double relation) {
		Agent source = this.getAgentByName(sourceName);
		Agent target = this.getAgentByName(targetName);
		if (source != null && target != null && relation >= -1 && relation <= 1) {
			source.updateRelation(targetName, relation);
		} else {
			debug("Error: cannot relate " + source + "  to " + target + " with intensity " + relation);
		}
	}

	/**
	 * Simple agent getter by name.
	 * 
	 * @param agentName The name of the agent to be found.
	 * @return Agent null or an agent reference that has the name property equal to the agentName argument
	 */
	public Agent getAgentByName(String agentName) {
		if (this.agents.containsKey(agentName)) {
			return this.agents.get(agentName);
		}
		debug("Warning: agent \"" + agentName + "\" not found.");
		return null;
	}

	/**
	 * Simple goal getter by name.
	 * 
	 * @param goalName The name of the goal to be found.
	 * @return Goal null or a goal reference that has the name property equal to the goalName argument.
	 */
	public Goal getGoalByName(String goalName) {
		if (this.goals.containsKey(goalName)) {
			return this.goals.get(goalName);
		}
		debug("Warning: goal \"" + goalName + "\" not found.");
		return null;
	}

	/**
	 * Get the amount of milliseconds that has passed since the last decay function was called.
	 * 
	 * @return long Milliseconds passed.
	 */
	public long getMillisPassed() {
		return millisPassed;
	}

	/**
	 * Print debug information to console if the debug flag is set to true.
	 * 
	 * @param what Object to print to console.
	 */
	public static void debug(Object what) {
		if (Gamygdala.debug) {
			System.out.println(what);
		}
	}
}
