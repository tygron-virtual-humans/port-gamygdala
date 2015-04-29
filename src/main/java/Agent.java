import java.util.ArrayList;
import java.util.HashMap;

public class Agent {

	/**
	 * The name of this Agent.
	 */
	public String name;

	/**
	 * Collection of goals for this Agent.
	 */
	public HashMap<String, Goal> goals;

	/**
	 * Collection of relations for this Agent.
	 */
	public ArrayList<Relation> currentRelations;

	/**
	 * Collection of emotions for this Agent.
	 */
	public ArrayList<Emotion> internalState;

	/**
	 * The gain for this agent. Must be between 0 and 20 inclusive.
	 */
	public double gain;

	/**
	 * The Gamygdala instance to which this Agent is linked.
	 */
	private Gamygdala gamygdalaInstance;

	/**
	 * Pleasure Arousal Dominance mapping.
	 */
	private HashMap<String, double[]> mapPAD;

	/**
	 * Create new Agent.
	 * 
	 * @param name Agent name.
	 */
	public Agent(String name) {
		this.name = name;

		// Init goal map.
		this.goals = new HashMap<String, Goal>();

		// Init relation and emotion collections
		this.currentRelations = new ArrayList<Relation>();
		this.internalState = new ArrayList<Emotion>();

		// Set gain
		this.gain = 1;

		// Initialize PAD map
		this.mapPAD = new HashMap<String, double[]>(16, 1);
		this.mapPAD.put("distress", new double[] { -0.61, 0.28, -0.36 });
		this.mapPAD.put("fear", new double[] { -0.64, 0.6, -0.43 });
		this.mapPAD.put("hope", new double[] { 0.51, 0.23, 0.14 });
		this.mapPAD.put("joy", new double[] { 0.76, .48, 0.35 });
		this.mapPAD.put("satisfaction", new double[] { 0.87, 0.2, 0.62 });
		this.mapPAD.put("fear-confirmed", new double[] { -0.61, 0.06, -0.32 }); // defeated
		this.mapPAD.put("disappointment", new double[] { -0.61, -0.15, -0.29 });
		this.mapPAD.put("relief", new double[] { 0.29, -0.19, -0.28 });
		this.mapPAD.put("happy-for", new double[] { 0.64, 0.35, 0.25 });
		this.mapPAD.put("resentment", new double[] { -0.35, 0.35, 0.29 });
		this.mapPAD.put("pity", new double[] { -0.52, 0.02, -0.21 }); // regretful
		this.mapPAD.put("gloating", new double[] { -0.45, 0.48, 0.42 }); // cruel
		this.mapPAD.put("gratitude", new double[] { 0.64, 0.16, -0.21 }); // grateful
		this.mapPAD.put("anger", new double[] { -0.51, 0.59, 0.25 });
		this.mapPAD.put("gratification", new double[] { 0.69, 0.57, 0.63 }); // triumphant
		this.mapPAD.put("remorse", new double[] { -0.57, 0.28, -0.34 }); // guilty
	}

	/**
	 * Add Goal.
	 * 
	 * @param g Goal to add.
	 * @return True if goal was added successfully, false if not.
	 */
	public boolean addGoal(Goal g) {
		if (g != null && !this.goals.containsKey(g.name)) {
			this.goals.put(g.name, g);
			return true;
		}
		return false;
	}

	/**
	 * Remove Goal.
	 * 
	 * @param g Goal to remove.
	 * @return True if goal was removed successfully, false if not.
	 */
	public boolean removeGoal(Goal g) {
		return this.goals.remove(g.name) != null;
	}

	/**
	 * Check if this Agent has a specific Goal.
	 * 
	 * @param g Goal to check for.
	 * @return True if Agent has goal, false if not.
	 */
	public boolean hasGoal(Goal g) {
		return this.goals.containsKey(g.name);
	}

	public void getGoalByName() {
	}

	public void setGain() {
	}

	public void appraise() {
	}

	public void updateEmotionalState() {
	}

	public void getEmotionalState() {
	}

	public void getPADState() {
	}

	public void printEmotionalState() {
	}

	/**
	 * Sets the relation this agent has with the agent defined by agentName. If the relation does not exist, it will be created, otherwise it will be updated.
	 * 
	 * @param {String} agentName The agent who is the target of the relation.
	 * @param {double} like The relation (between -1 and 1).
	 */
	public void updateRelation(String agentName, double like) {
		if (!this.hasRelationWith(agentName)) {
			// This relation does not exist, just add it.
			this.currentRelations.add(new Relation(agentName, like));
		} else {
			// The relation already exists, update it.
			Relation r;
			for (int i = 0; i < this.currentRelations.size(); i++) {
				r = this.currentRelations.get(i);
				if (r.agentName == agentName) {
					r.like = like;
				}
			}
		}
	}

	/**
	 * Checks if this agent has a relation with the agent defined by agentName.
	 * 
	 * @param {String} agentName The agent who is the target of the relation.
	 * @param {boolean} True if the relation exists, otherwise false.
	 */
	public boolean hasRelationWith(String agentName) {
		return (this.getRelation(agentName) != null);
	}

	/**
	 * Returns the relation object this agent has with the agent defined by agentName.
	 * 
	 * @param agentName The agent who is the target of the relation.
	 * @param Relation The relation object or null if non existing.
	 */
	public Relation getRelation(String agentName) {
		Relation r;
		for (int i = 0; i < this.currentRelations.size(); i++) {
			r = this.currentRelations.get(i);
			if (r.agentName == agentName) {
				return r;
			}
		}
		return null;
	}

	/**
	 * Prints the relations this agent has with the agent defined by agentName.
	 * 
	 * @param agentName The agent who is the target of the relation. When omitted, all relations are printed.
	 */
	public void printRelations(String agentName) {
		String output = this.name + " has the following sentiments:\n   ";
		boolean found = false;

		for (int i = 0; i < this.currentRelations.size(); i++) {

			if (agentName == null || this.currentRelations.get(i).agentName == agentName) {
				for (int j = 0; j < this.currentRelations.get(i).emotionList.size(); j++) {
					output += this.currentRelations.get(i).emotionList.get(j).name + "(" + this.currentRelations.get(i).emotionList.get(j).intensity + ") ";
					found = true;
				}
			}

			output += " for " + this.currentRelations.get(i).agentName;

			if (i < this.currentRelations.size() - 1) {
				output += ", and\n   ";
			}
		}

		if (found) {
			System.out.println(output);
		}
	}

	/**
	 * This method decays the emotional state and relations according to the decay factor and function defined in gamygdala.
	 * Typically this is called automatically when you use startDecay() in Gamygdala, but you can use it yourself if you want to manage the timing.
	 * This function is keeping track of the millis passed since the last call, and will (try to) keep the decay close to the desired decay factor, regardless the time passed
	 * So you can call this any time you want (or, e.g., have the game loop call it, or have e.g., Phaser call it in the plugin update, which is default now).
	 * Further, if you want to tweak the emotional intensity decay of individual agents, you should tweak the decayFactor per agent not the "frame rate" of the decay (as this doesn't change the rate).
	 * 
	 * @param gamygdalaInstance A reference to the correct Gamygdala instance that contains the decayFunction property to be used )(so you could use different gamygdala instances to manage different groups of agents)
	 */
	public void decay(Gamygdala gamygdalaInstance) {
		for (int i = 0; i < this.internalState.size(); i++) {
			double newIntensity = gamygdalaInstance.decayFunction.decay(this.internalState.get(i).intensity, gamygdalaInstance.getMillisPassed());
			if (newIntensity < 0) {
				this.internalState.remove(i);
			} else {
				this.internalState.get(i).intensity = newIntensity;
			}
		}

		for (int i = 0; i < this.currentRelations.size(); i++) {
			this.currentRelations.get(i).decay(gamygdalaInstance);
		}
	}

	/**
	 * Get the Gamygdala instance to which this Agent is linked.
	 * 
	 * @return Gamygdala object.
	 */
	public Gamygdala getGamygdalaInstance() {
		return this.gamygdalaInstance;
	}

	/**
	 * Set the Gamygdala instance to which this Agent is linked.
	 * 
	 * @return Gamygdala object.
	 */
	public void setGamygdalaInstance(Gamygdala g) {
		this.gamygdalaInstance = g;
	}

}
