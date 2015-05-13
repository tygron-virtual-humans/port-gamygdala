package data;

/**
 * Goal for an Agent.
 */
public class Goal {

    private String name;
    private double utility;
    private double likelihood;
    private boolean isMaintenanceGoal;

    /**
     * Construct new Goal.
     *
     * @param name              Name of the goal.
     * @param utility           Goal utility.
     * @param isMaintenanceGoal Whether or not this goal is a maintenance goal.
     */
    public Goal(String name, double utility, boolean isMaintenanceGoal) {

        this.name = name;
        this.utility = utility;
        this.likelihood = 0.5; // The likelihood is unknown at the start so it starts in the middle
        // between disconfirmed (0) and confirmed (1)

        // There are maintenance and achievement goals.
        // When an achievement goal is reached (or not), this is definite (e.g., to a the promotion or
        // not).
        // A maintenance goal can become true/false indefinitely (e.g., to be well-fed)
        this.isMaintenanceGoal = isMaintenanceGoal;

    }

    /**
     * Create new Goal object from other goal.
     *
     * @param goal Other Goal object.
     */
    public Goal(Goal goal) {
        this.name = goal.name;
        this.utility = goal.utility;
        this.likelihood = goal.likelihood;
        this.isMaintenanceGoal = goal.isMaintenanceGoal;
    }

    /**
     * Compare two goals based on their individual properties.
     */
    @Override
    public boolean equals(Object obj) {

        if (obj instanceof Goal) {

            Goal other = (Goal) obj;

            return this.name.equals(other.name) && Double.compare(this.utility, other.utility) == 0
                    && Double.compare(this.likelihood, other.likelihood) == 0
                    && this.isMaintenanceGoal == other.isMaintenanceGoal;

        }

        return false;
    }

    /**
     * Get the name of the goal.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the goal.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the utility value of this goal.
     *
     * @return the utility
     */
    public double getUtility() {
        return utility;
    }

    /**
     * Set the utility value of this goal.
     *
     * @param utility the utility to set
     */
    public void setUtility(double utility) {
        this.utility = utility;
    }

    /**
     * Get the likelihood value of this goal.
     *
     * @return the likelihood
     */
    public double getLikelihood() {
        return likelihood;
    }

    /**
     * Set the likelihood value of this goal.
     *
     * @param likelihood the likelihood to set
     */
    public void setLikelihood(double likelihood) {
        this.likelihood = likelihood;
    }

    /**
     * Whether or not the goal is a maintainance goal.
     *
     * @return the isMaintenanceGoal
     */
    public boolean isMaintenanceGoal() {
        return isMaintenanceGoal;
    }

    /**
     * Set whether or not the goal is a maintainance goal.
     *
     * @param isMaintenanceGoal the isMaintenanceGoal to set
     */
    public void setMaintenanceGoal(boolean isMaintenanceGoal) {
        this.isMaintenanceGoal = isMaintenanceGoal;
    }


}
