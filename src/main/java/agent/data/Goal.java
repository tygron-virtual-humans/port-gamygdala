package agent.data;

/**
 * Goal for an Agent.
 */
public class Goal {

    /**
     * Name of the Goal.
     */
    private String name;

    /**
     * Utility of the goal (usefulness when achieved).
     */
    private double utility;

    /**
     * Likelihood of achieving the goal.
     */
    private double likelihood;

    /**
     * Whether or not this goal is a maintenance goal. Maintenance goals are
     * kept when achieved, so whenever events cause an Agent to forfeit that
     * particular goal, it'll want to achieve it again after.
     */
    private boolean isMaintenanceGoal;

    /**
     * Construct new Goal.
     *
     * @param conName Name of the goal.
     * @param conUtility Goal utility.
     * @param conIsMaintenanceGoal Whether or not this goal is a maintenance goal.
     */
    public Goal(String conName, double conUtility, boolean conIsMaintenanceGoal) {

        this.name = conName;
        this.utility = conUtility;

        // Initialize the likelihood in the middle between disconfirmed (0) and
        // confirmed (1).
        this.likelihood = 0.5;

        this.isMaintenanceGoal = conIsMaintenanceGoal;

    }

    /**
     * Create new Goal object from other goal.
     *
     * @param goal Other Goal object.
     */
    public Goal(Goal goal) {
        this.name = goal.getName();
        this.utility = goal.getUtility();
        this.likelihood = goal.getLikelihood();
        this.isMaintenanceGoal = goal.isMaintenanceGoal();
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
     * @param newName the name to set
     */
    public void setName(String newName) {
        this.name = newName;
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
     * @param newUtility the utility to set
     */
    public void setUtility(double newUtility) {
        this.utility = newUtility;
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
     * @param newLikelihood the likelihood to set
     */
    public void setLikelihood(double newLikelihood) {
        this.likelihood = newLikelihood;
    }

    /**
     * Whether or not the goal is a maintenance goal.
     *
     * @return the isMaintenanceGoal
     */
    public boolean isMaintenanceGoal() {
        return isMaintenanceGoal;
    }

    /**
     * Set whether or not the goal is a maintenance goal.
     *
     * @param newIsMaintenanceGoal the isMaintenanceGoal to set
     */
    public void setMaintenanceGoal(boolean newIsMaintenanceGoal) {
        this.isMaintenanceGoal = newIsMaintenanceGoal;
    }

    /**
     * String representation of Goal.
     */
    @Override
    public String toString() {
        return "<Goal[" + getName() + ", " + getLikelihood() + ", " + Boolean.toString(isMaintenanceGoal()) + "]>";
    }

    /**
     * Compare two goals based on their individual properties.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Goal)) return false;

        Goal goal = (Goal) o;

        if (Double.compare(goal.getUtility(), getUtility()) != 0) return false;
        if (Double.compare(goal.getLikelihood(), getLikelihood()) != 0) return false;
        if (isMaintenanceGoal() != goal.isMaintenanceGoal()) return false;
        return !(getName() != null ? !getName().equals(goal.getName()) : goal.getName() != null);

    }

    /**
     * Generate hashCode for an Object.
     */
    @Override
    public int hashCode() {
        int result;
        long temp;
        result = getName() != null ? getName().hashCode() : 0;
        temp = Double.doubleToLongBits(getUtility());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(getLikelihood());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (isMaintenanceGoal() ? 1 : 0);
        return result;
    }

}
