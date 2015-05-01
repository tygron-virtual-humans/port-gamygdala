public class Goal {

	public String name;
	public double utility, likelihood;
	public boolean isMaintenanceGoal;

	/**
	 * Construct new Goal.
	 * @param name Name of the goal.
	 * @param utility Goal utility.
	 * @param isMaintenanceGoal Whether or not this goal is a maintenance goal.
	 */
	public Goal(String name, double utility, boolean isMaintenanceGoal) {

		this.name = name;
		this.utility = utility;
		this.likelihood = 0.5; // The likelihood is unknown at the start so it starts in the middle between disconfirmed (0) and confirmed (1)

		if (isMaintenanceGoal) {
			// There are maintenance and achievement goals.
			// When an achievement goal is reached (or not), this is definite (e.g., to a the promotion or not).
			// A maintenance goal can become true/false indefinitely (e.g., to be well-fed)
			this.isMaintenanceGoal = isMaintenanceGoal;
		} else {
			this.isMaintenanceGoal = false;
		}

	}
	
	/**
	 * Create new Goal object from other goal.
	 * @param g Other Goal object.
	 */
	public Goal(Goal g) {
		this.name = g.name;
		this.utility = g.utility;
		this.likelihood = g.likelihood;
		this.isMaintenanceGoal = g.isMaintenanceGoal;
	}
	
	/**
	 * Compare two goals based on their individual properties.
	 */
	@Override
	public boolean equals(Object obj) {
		
		if(obj instanceof Goal) {
			
			Goal other = (Goal) obj;
			
			return this.name.equals(other.name) &&
					Double.compare(this.utility, other.utility) == 0 &&
					Double.compare(this.likelihood, other.likelihood) == 0 &&
					this.isMaintenanceGoal == other.isMaintenanceGoal;
			
		}
		
		return false;
	}
}
