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

	public static final double DEFAULT_GAIN = 1;

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
		this.gain = Agent.DEFAULT_GAIN;

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
	 * @param g
	 *            Goal to add.
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
	 * @param g
	 *            Goal to remove.
	 * @return True if goal was removed successfully, false if not.
	 */
	public boolean removeGoal(Goal g) {
		return g != null && this.goals.remove(g.name) != null;
	}

	/**
	 * Check if this Agent has a specific Goal.
	 * 
	 * @param g Goal to check for.
	 * @return True if Agent has goal, false if not.
	 */
	public boolean hasGoal(Goal g) {
		return g != null && this.hasGoal(g.name);
	}

	/**
	 * Check if this Agent has a specific Goal.
	 * 
	 * @param g Goal to check for.
	 * @return True if Agent has goal, false if not.
	 */
	public boolean hasGoal(String goalName) {
		return this.goals.containsKey(goalName) && this.goals.get(goalName) != null;
	}

	/**
	 * If this agent has a goal with name goalName, this method returns that goal.
	 * 
	 * @param goalName The name of the goal to be found.
	 * @return the reference to the goal.
	 */
	public Goal getGoalByName(String goalName) {
		if (this.goals.containsKey(goalName)) {
			return this.goals.get(goalName);
		}
		return null;
	}

	/**
	 * Sets the gain for this agent.
	 * 
	 * @param gain The gain value [0 and 20].
	 */
	public void setGain(double gain) {

		// Gain has to be between 0 and 20.
		if (gain <= 0 || gain > 20) {
			Gamygdala.debug("Error: gain factor for appraisal integration must be between 0 and 20.");
		} else {
			this.gain = gain;
		}
	}

	/**
	 * A facilitating method to be able to appraise one event only from the perspective of the current agent (this).
	 * Needs an instantiated Gamygdala object (automatic when the agent is registered with Gamygdala.registerAgent(agent) to a Gamygdala instance).
	 * 
	 * @param belief The belief to be appraised.
	 */
	public void appraise(Belief belief) {
		this.gamygdalaInstance.appraise(belief, this);
	}

	public void updateEmotionalState(Emotion emotion) {
		Emotion e;
		for (int i = 0; i < this.internalState.size(); i++) {
			e = this.internalState.get(i);
			if (e.name == emotion.name) {
				// Appraisals simply add to the old value of the emotion
				// So repeated appraisals without decay will result in the sum of the appraisals over time
				// To decay the emotional state, call .decay(decayFunction), or simply use the facilitating function in Gamygdala setDecay(timeMS).
				e.intensity += emotion.intensity;

				// Test if this works without updating object, else:
				// this.internalState.set(i, e);
				return;
			}
		}

		// copy on keep, we need to maintain a list of current emotions for the state, not a list references to the appraisal engine
		this.internalState.add(new Emotion(emotion.name, emotion.intensity));
	}

	/**
	 * This function returns either the state as is (gain=false) or a state based on gained limiter (limited between 0 and 1), of which the gain can be set by using setGain(gain).
	 * A high gain factor works well when appraisals are small and rare, and you want to see the effect of these appraisals
	 * A low gain factor (close to 0 but in any case below 1) works well for high frequency and/or large appraisals, so that the effect of these is dampened.
	 * 
	 * @param useGain Whether to use the gain function or not.
	 * @return An array of emotions.
	 */
	public ArrayList<Emotion> getEmotionalState(boolean useGain) {

		if (useGain) {
			ArrayList<Emotion> gainState = new ArrayList<Emotion>();
			Emotion e;
			for (int i = 0; i < this.internalState.size(); i++) {
				e = this.internalState.get(i);

				if (e != null) {
					double gainEmo = (this.gain * e.intensity) / (this.gain * e.intensity + 1);
					gainState.add(new Emotion(e.name, gainEmo));
				}
			}

			return gainState;
		}

		return this.internalState;
	}

	/**
	 * This function returns a summation-based Pleasure Arousal Dominance mapping of the emotional state as is (gain=false), or a PAD mapping based on a gained limiter (limited between 0 and 1), of which the gain can be set by using setGain(gain).
	 * It sums over all emotions the equivalent PAD values of each emotion (i.e., [P,A,D]=SUM(Emotion_i([P,A,D])))), which is then gained or not.
	 * A high gain factor works well when appraisals are small and rare, and you want to see the effect of these appraisals.
	 * A low gain factor (close to 0 but in any case below 1) works well for high frequency and/or large appraisals, so that the effect of these is dampened.
	 * 
	 * @param useGain Whether to use the gain function or not.
	 * @return An array of doubles with Pleasure at index 0, Arousal at index [1] and Dominance at index [2].
	 */
	public double[] getPADState(boolean useGain) {
		double[] PAD = new double[3];
		PAD[0] = 0;
		PAD[1] = 0;
		PAD[2] = 0;

		Emotion e;
		for (int i = 0; i < this.internalState.size(); i++) {
			e = this.internalState.get(i);
			PAD[0] += (e.intensity * this.mapPAD.get(e.name)[0]);
			PAD[1] += (e.intensity * this.mapPAD.get(e.name)[1]);
			PAD[2] += (e.intensity * this.mapPAD.get(e.name)[2]);
		}

		if (useGain) {
			PAD[0] = (PAD[0] >= 0 ? this.gain * PAD[0] / (this.gain * PAD[0] + 1) : -this.gain * PAD[0] / (this.gain * PAD[0] - 1));
			PAD[1] = (PAD[1] >= 0 ? this.gain * PAD[1] / (this.gain * PAD[1] + 1) : -this.gain * PAD[1] / (this.gain * PAD[1] - 1));
			PAD[2] = (PAD[2] >= 0 ? this.gain * PAD[2] / (this.gain * PAD[2] + 1) : -this.gain * PAD[2] / (this.gain * PAD[2] - 1));
			return PAD;
		}

		return PAD;
	}

	/**
	 * This function prints to the console either the state as is (gain=false) or a state based on gained limiter (limited between 0 and 1), of which the gain can be set by using setGain(gain).
	 * A high gain factor works well when appraisals are small and rare, and you want to see the effect of these appraisals
	 * A low gain factor (close to 0 but in any case below 1) works well for high frequency and/or large appraisals, so that the effect of these is dampened.
	 * 
	 * @param useGain Whether to use the gain function or not.
	 */
	public void printEmotionalState(boolean useGain) {
		String output = this.name + " feels ";
		int i;
		ArrayList<Emotion> emotionalState = this.getEmotionalState(useGain);

		Emotion e;
		for (i = 0; i < emotionalState.size(); i++) {
			e = emotionalState.get(i);
			output += e.name + ": " + e.intensity + ", ";
		}

		System.out.println(output);
	}

	/**
	 * Sets the relation this agent has with the agent defined by agentName. If
	 * the relation does not exist, it will be created, otherwise it will be
	 * updated.
	 * 
	 * @param agentName The agent who is the target of the relation.
	 * @param like The relation (between -1 and 1).
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
	 * Returns the relation object this agent has with the agent defined by
	 * agentName.
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
	 * This method decays the emotional state and relations according to the
	 * decay factor and function defined in gamygdala. Typically this is called
	 * automatically when you use startDecay() in Gamygdala, but you can use it
	 * yourself if you want to manage the timing. This function is keeping track
	 * of the millis passed since the last call, and will (try to) keep the
	 * decay close to the desired decay factor, regardless the time passed So
	 * you can call this any time you want (or, e.g., have the game loop call
	 * it, or have e.g., Phaser call it in the plugin update, which is default
	 * now). Further, if you want to tweak the emotional intensity decay of
	 * individual agents, you should tweak the decayFactor per agent not the
	 * "frame rate" of the decay (as this doesn't change the rate).
	 * 
	 * @param gamygdalaInstance A reference to the correct Gamygdala instance that
	 *            contains the decayFunction property to be used (so you could use
	 *            different gamygdala instances to manage different groups of agents)
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

	/**
	 * String representation of Agent.
	 */
	@Override
	public String toString() {
		return "<Agent[" + this.name + "]>";
	}

}
