import java.util.ArrayList;

public class Agent {

	/**
	 * The name of this Agent.
	 */
	public String name;

	/**
	 * Collection of goals for this Agent.
	 */
	public ArrayList<Goal> goals;

	/**
	 * Collection of relations for this Agent.
	 */
	public ArrayList<Relation> currentRelations;
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
	 * Create new Agent.
	 * 
	 * @param name Agent name.
	 */
	public Agent(String name) {
		this.name = name;
	}

	/**
	 * Add Goal.
	 * 
	 * @param g Goal to add.
	 * @return True if goal was added successfully, false if not.
	 */
	public boolean addGoal(Goal g) {
		if (g != null)
			return this.goals.add(g);
		return false;
	}

	/**
	 * Remove Goal.
	 * 
	 * @param g Goal to remove.
	 * @return True if goal was removed successfully, false if not.
	 */
	public boolean removeGoal(Goal g) {
		return this.goals.remove(g);
	}

	/**
	 * Check if this Agent has a specific Goal.
	 * 
	 * @param g Goal to check for.
	 * @return True if Agent has goal, false if not.
	 */
	public boolean hasGoal(Goal g) {
		return this.goals.contains(g);
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

	public void updateRelation() {
	}

	public void hasRelationWith() {
	}

	public void getRelation() {
	}

	public void printRelations() {
	}

	public void decay() {
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
