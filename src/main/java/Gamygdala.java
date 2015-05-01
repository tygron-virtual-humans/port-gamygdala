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

				// Update isMaintenanceGoal
				// XXX: Currently, this updates this attribute for every goal with this name registered to all agents. Do we want this?
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
		if (!this.goals.containsKey(goal.name)) {
			this.goals.put(goal.name, goal);
		} else {
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
			Gamygdala.debug("Error: cannot relate " + source + "  to " + target + " with intensity " + relation);
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
		Gamygdala.debug("Warning: agent \"" + agentName + "\" not found.");
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
			return new Goal(this.goals.get(goalName));
		}
		Gamygdala.debug("Warning: goal \"" + goalName + "\" not found.");
		return null;
	}
	
	/**
	* A facilitator method to appraise an event. It takes in the same as what the new Belief(...) takes in, creates a belief and appraises it for all agents that are registered.
	* This method is thus handy if you want to keep all gamygdala logic internal to Gamygdala.
	* 
	* @param likelihood The likelihood of this belief to be true.
	* @param causalAgentName The agent's name of the causal agent of this belief.
	* @param affectedGoalNames An array of affected goals' names.
	* @param goalCongruences An array of the affected goals' congruences (i.e., the extend to which this event is good or bad for a goal [-1,1]).
	* @param isIncremental Incremental evidence enforces gamygdala to see this event as incremental evidence for (or against) the list of goals provided, i.e, it will add or subtract this belief's likelihood*congruence from the goal likelihood instead of using the belief as "state" defining the absolute likelihood
	*/
	public void appraiseBelief(double likelihood, String causalAgentName, ArrayList<String> affectedGoalNames, ArrayList<Double> goalCongruences, boolean isIncremental){
		Belief tempBelief = new Belief(likelihood, causalAgentName, affectedGoalNames, goalCongruences, isIncremental);
		this.appraise(tempBelief, null);
	}
	
	/**
	 * This method is the main emotional interpretation logic entry point. It performs the complete appraisal of a single event (belief) for all agents (affectedAgent=null) or for only one agent (affectedAgent=true)
	 * if affectedAgent is set, then the complete appraisal logic is executed including the effect on relations (possibly influencing the emotional state of other agents),
	 * but only if the affected agent (the one owning the goal) == affectedAgent
	 * this is sometimes needed for efficiency, if you as a game developer know that particular agents can never appraise an event, then you can force Gamygdala to only look at a subset of agents.
	 * Gamygdala assumes that the affectedAgent is indeed the only goal owner affected, that the belief is well-formed, and will not perform any checks, nor use Gamygdala's list of known goals to find other agents that share this goal (!!!)
	 * 
	 * @param belief The current event, in the form of a Belief object, to be appraised
	 * @param affectedAgent The reference to the agent who needs to appraise the event. If given, this is the appraisal perspective (see explanation above).
	 */
	public void appraise(Belief belief, Agent affectedAgent) {

		if (affectedAgent == null) {
			// check all
			Gamygdala.debug(belief);

			if (belief.goalCongruences.size() != belief.affectedGoalNames.size()) {
				Gamygdala.debug("Error: the congruence list was not of the same length as the affected goal list");
				return; // The congruence list must be of the same length as the affected goals list.
			}
			if (this.goals.size() == 0) {
				Gamygdala.debug("Warning: no goals registered to Gamygdala, all goals to be considered in appraisal need to be registered.");
				return; // The congruence list must be of the same length as the affected goals list.
			}

			for (int i = 0; i < belief.affectedGoalNames.size(); i++) {
				// Loop through every goal in the list of affected goals by this event.
				Goal currentGoal = this.getGoalByName(belief.affectedGoalNames.get(i));

				if (currentGoal != null) {
					// the goal exists, appraise it
					double utility = currentGoal.utility;
					double deltaLikelihood = this.calculateDeltaLikelihood(currentGoal, belief.goalCongruences.get(i), belief.likelihood, belief.isIncremental);

					double desirability = deltaLikelihood * utility;
					Gamygdala.debug("Evaluated goal: " + currentGoal.name + "(" + utility + ", " + deltaLikelihood + ")");

					// now find the owners, and update their emotional states
					for (int j = 0; j < this.agents.size(); j++) {
						Agent owner = this.agents.get(j);
						if (owner != null) {
							if (owner.hasGoal(currentGoal.name)) {
								owner = this.agents.get(j);

								Gamygdala.debug("....owned by " + owner.name);

								this.evaluateInternalEmotion(utility, deltaLikelihood, currentGoal.likelihood, owner);
								this.agentActions(owner.name, belief.causalAgentName, owner.name, desirability, utility, deltaLikelihood);
								// now check if anyone has a relation to this goal owner, and update the social emotions accordingly.
								for (int k = 0; k < this.agents.size(); k++) {
									Relation relation = this.agents.get(k).getRelation(owner.name);
									if (relation != null) {
										Gamygdala.debug(this.agents.get(k).name + " has a relationship with " + owner.name);
										Gamygdala.debug(relation);
										// The agent has relationship with the goal owner which has nonzero utility, add relational effects to the relations for agent[k].
										this.evaluateSocialEmotion(utility, desirability, deltaLikelihood, relation, this.agents.get(k));
										// also add remorse and gratification if conditions are met within (i.e., agent[k] did something bad/good for owner)
										this.agentActions(owner.name, belief.causalAgentName, this.agents.get(k).name, desirability, utility, deltaLikelihood);
									} else {
										Gamygdala.debug(this.agents.get(k).name + " has NO relationship with " + owner.name);
									}
								}
							}
						}
					}
				}
			}
		} else {
			// check only affectedAgent (which can be much faster) and does not involve console output nor checks
			for (int i = 0; i < belief.affectedGoalNames.size(); i++) {
				// Loop through every goal in the list of affected goals by this event.
				Goal currentGoal = affectedAgent.getGoalByName(belief.affectedGoalNames.get(i));
				double utility = currentGoal.utility;
				double deltaLikelihood = this.calculateDeltaLikelihood(currentGoal, belief.goalCongruences.get(i), belief.likelihood, belief.isIncremental);
				// var desirability = belief.goalCongruences.get(i) * utility;
				double desirability = deltaLikelihood * utility;
				// assume affectedAgent is the only owner to be considered in this appraisal round.

				Agent owner = affectedAgent;

				this.evaluateInternalEmotion(utility, deltaLikelihood, currentGoal.likelihood, owner);
				this.agentActions(owner.name, belief.causalAgentName, owner.name, desirability, utility, deltaLikelihood);
				// now check if anyone has a relation to this goal owner, and update the social emotions accordingly.
				for (double k = 0; k < this.agents.size(); k++) {
					Relation relation = this.agents.get(k).getRelation(owner.name);
					if (relation != null) {
						Gamygdala.debug(this.agents.get(k).name + " has a relationship with " + owner.name);
						Gamygdala.debug(relation);
						// The agent has relationship with the goal owner which has nonzero utility, add relational effects to the relations for agent[k].
						this.evaluateSocialEmotion(utility, desirability, deltaLikelihood, relation, this.agents.get(k));
						// also add remorse and gratification if conditions are met within (i.e., agent[k] did something bad/good for owner)
						this.agentActions(owner.name, belief.causalAgentName, this.agents.get(k).name, desirability, utility, deltaLikelihood);
					} else {
						Gamygdala.debug(this.agents.get(k).name + " has NO relationship with " + owner.name);
					}
				}
			}
		}

		// print the emotions to the console for debugging

		// XXX: emotionEngine does also not exist in JS code
		// if (this.debug){
		// emotionEngine.printAllEmotions(false);
		// //emotionEngine.printAllEmotions(true);
		// }
	}

	private void evaluateSocialEmotion(double utility, double desirability, double deltaLikelihood, Relation relation, Agent agent) {
		// This function is used to evaluate happy-for, pity, gloating or resentment.
		// Emotions that arise when we evaluate events that affect goals of others.
		// The desirability is the desirability from the goal owner's perspective.
		// The agent is the agent getting evaluated (the agent that gets the social emotion added to his emotional state).
		// The relation is a relation object between the agent being evaluated and the goal owner of the affected goal.
		Emotion emotion = new Emotion(null, 0);
		if (desirability >= 0) {
			if (relation.like >= 0) {
				emotion.name = "happy-for";
			} else {
				emotion.name = "resentment";
			}
		} else {
			if (relation.like >= 0) {
				emotion.name = "pity";
			} else {
				emotion.name = "gloating";
			}
		}
		emotion.intensity = Math.abs(utility * deltaLikelihood * relation.like);
		if (emotion.intensity != 0) {
			relation.addEmotion(emotion);
			agent.updateEmotionalState(emotion); // also add relation emotion the emotion to the emotional state
		}

	}

	private void agentActions(String affectedName, String causalName, String selfName, double desirability, double utility, double deltaLikelihood) {
		if (causalName != null && !causalName.equals("")) {
			// If the causal agent is null or empty, then we we assume the event was not caused by an agent.
			// There are three cases here.
			// The affected agent is SELF and causal agent is other.
			// The affected agent is SELF and causal agent is SELF.
			// The affected agent is OTHER and causal agent is SELF.
			Emotion emotion = new Emotion(null, 0);
			Relation relation;
			if (affectedName.equals(selfName) && !selfName.equals(causalName)) {
				// Case one
				if (desirability >= 0) {
					emotion.name = "gratitude";
				} else {
					emotion.name = "anger";
				}
				emotion.intensity = Math.abs(utility * deltaLikelihood);
				Agent self = this.getAgentByName(selfName);
				if (self.hasRelationWith(causalName)) {
					relation = self.getRelation(causalName);
				} else {
					self.updateRelation(causalName, 0.0);
					relation = self.getRelation(causalName);
				}
				relation.addEmotion(emotion);
				self.updateEmotionalState(emotion); // also add relation emotion the emotion to the emotional state
			} else if (affectedName.equals(selfName) && selfName.equals(causalName)) {
				// Case two
				// This case is not included in TUDelft.Gamygdala.
				// This should include pride and shame
			} else if (!affectedName.equals(selfName) && causalName.equals(selfName)) {
				// Case three
				if (this.getAgentByName(causalName).hasRelationWith(affectedName)) {
					relation = this.getAgentByName(causalName).getRelation(affectedName);
					if (desirability >= 0) {
						if (relation.like >= 0) {
							emotion.name = "gratification";
							emotion.intensity = Math.abs(utility * deltaLikelihood * relation.like);
							relation.addEmotion(emotion);
							this.getAgentByName(causalName).updateEmotionalState(emotion); // also add relation emotion the emotion to the emotional state
						}
					} else {
						if (relation.like >= 0) {
							emotion.name = "remorse";
							emotion.intensity = Math.abs(utility * deltaLikelihood * relation.like);
							relation.addEmotion(emotion);
							this.getAgentByName(causalName).updateEmotionalState(emotion); // also add relation emotion the emotion to the emotional state
						}

					}

				}
			}
		}
	}

	private double calculateDeltaLikelihood(Goal goal, double congruence, double likelihood, boolean isIncremental) {
		// Defines the change in a goal's likelihood due to the congruence and likelihood of a current event.
		// We cope with two types of beliefs: incremental and absolute beliefs. Incrementals have their likelihood added to the goal, absolute define the current likelihood of the goal
		// And two types of goals: maintenance and achievement. If an achievement goal (the default) is -1 or 1, we can't change it any more (unless externally and explicitly by changing the goal.likelihood).
		Double oldLikelihood = goal.likelihood;
		double newLikelihood;
		if (goal.isMaintenanceGoal == false && (oldLikelihood >= 1 | oldLikelihood <= -1)) {
			return 0;
		}
		if (isIncremental) {
			newLikelihood = oldLikelihood + likelihood * congruence;
			newLikelihood = Math.max(Math.min(newLikelihood, 1), -1);
		} else {
			newLikelihood = (congruence * likelihood + 1.0) / 2.0;
		}
		goal.likelihood = newLikelihood;
		if (oldLikelihood != null) {
			return newLikelihood - oldLikelihood;
		} else {
			return newLikelihood;
		}
	}

	private void evaluateInternalEmotion(double utility, double deltaLikelihood, double likelihood, Agent agent) {
		// This method evaluates the event in terms of internal emotions that do not need relations to exist, such as hope, fear, etc..
		boolean positive = false;
		double intensity = 0;
		ArrayList<String> emotion = new ArrayList<String>();

		if (utility >= 0) {
			if (deltaLikelihood >= 0) {
				positive = true;
			} else {
				positive = false;
			}
		} else if (utility < 0) {
			if (deltaLikelihood >= 0) {
				positive = false;
			} else {
				positive = true;
			}
		}
		if (likelihood > 0 && likelihood < 1) {
			if (positive == true) {
				emotion.add("hope");
			} else {
				emotion.add("fear");
			}
		} else if (likelihood == 1) {
			if (utility >= 0) {
				if (deltaLikelihood < 0.5) {
					emotion.add("satisfaction");
				}
				emotion.add("joy");
			} else {
				if (deltaLikelihood < 0.5) {
					emotion.add("fear-confirmed");
				}
				emotion.add("distress");
			}
		} else if (likelihood == 0) {
			if (utility >= 0) {
				if (deltaLikelihood > 0.5) {
					emotion.add("disappointment");
				}
				emotion.add("distress");
			} else {
				if (deltaLikelihood > 0.5) {
					emotion.add("relief");
				}
				emotion.add("joy");
			}
		}
		intensity = Math.abs(utility * deltaLikelihood);
		if (intensity != 0) {
			for (int i = 0; i < emotion.size(); i++) {
				agent.updateEmotionalState(new Emotion(emotion.get(i), intensity));
			}
		}
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
