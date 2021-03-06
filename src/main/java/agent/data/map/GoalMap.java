package agent.data.map;

import java.util.HashMap;

import agent.data.Goal;
import debug.Debug;

/**
 * HashMap to store Goals. Extended with add / remove / has goal methods for
 * quick and concise access.
 */
public class GoalMap extends HashMap<String, Goal> {

    /**
     * ID for serialization.
     */
    private static final long serialVersionUID = 8089487457530836847L;

    /**
     * Remove Goal.
     *
     * @param goal Goal to remove.
     * @return True if goal was removed successfully, false if not.
     */
    public boolean removeGoal(Goal goal) {
        return goal != null && this.remove(goal.getName()) != null;
    }

    /**
     * Check if this GoalMap has a specific Goal (by object).
     *
     * @param goal Goal to check for.
     * @return True if Agent has goal, false if not.
     */
    public boolean hasGoal(Goal goal) {
        return goal != null && this.hasGoal(goal.getName());
    }

    /**
     * Check if this GoalMap contains a specific Goal (by name).
     *
     * @param goalName Goal name to check for.
     * @return True if Agent has goal, false if not.
     */
    private boolean hasGoal(String goalName) {
        return this.containsKey(goalName) && this.get(goalName) != null;
    }

    /**
     * If this GoalMap contains a goal named goalName, this method returns that
     * goal.
     *
     * @param goalName The name of the goal to be found.
     * @return the reference to the goal.
     */
    public Goal getGoalByName(String goalName) {
        if (containsKey(goalName)) {
            return get(goalName);
        }
        Debug.debug("Warning: goal \"" + goalName + "\" not found.");
        return null;
    }

    /**
     * Add element to the GoalMap.
     * @param name Name of the Goal
     * @param goal The New Goal
     * @return Goal created Goal
     */
    @Override
    public Goal put(String name, Goal goal) {
        // Check for empty Goal
        if (goal == null) {
            Debug.debug("[GoalMap.out] Goal is null ");
            return null;
        }

        if (!this.containsKey(name)) {
            return super.put(name, goal);
        } else {
            Debug.debug("Warning: failed adding a second goal with the same name: " + name);
        }
        return null;
    }

}
