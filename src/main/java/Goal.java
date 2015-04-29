public class Goal {

	public String name;
	public double utility, likelihood;
	public boolean maintenanceGoal;
	public boolean isMaintenanceGoal;

	public Goal(String name, double utility, boolean isMaintenanceGoal) {

		this.name = name;
		this.utility = utility;
		this.likelihood = 0.5; // The likelihood is unknown at the start so it starts in the middle between disconfirmed (0) and confirmed (1)

		if (isMaintenanceGoal) {
			// There are maintenance and achievement goals.
			// When an achievement goal is reached (or not), this is definite (e.g., to a the promotion or not).
			// A maintenance goal can become true/false indefinitely (e.g., to be well-fed)
			this.maintenanceGoal = isMaintenanceGoal;
		} else {
			this.isMaintenanceGoal = false;
		}

	}
}
