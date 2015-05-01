import java.util.HashMap;

/**
 * HashMap to store Goals.
 * Extended with add / remove / has goal methods for quick and concise access.
 */
public class GoalMap extends HashMap<String, Goal> {

  /**
   * ID for serialization.
   */
  private static final long serialVersionUID = 8089487457530836847L;

  /**
   * Add Goal.
   * 
   * @param goal Goal to add.
   * @return True if goal was added successfully, false if not.
   */
  public boolean addGoal(Goal goal) {
    if (goal != null && !containsKey(goal.name)) {
      put(goal.name, goal);
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
  public boolean removeGoal(Goal goal) {
    return goal != null && remove(goal.name) != null;
  }

  /**
   * Check if this GoalMap has a specific Goal (by object).
   * 
   * @param g Goal to check for.
   * @return True if Agent has goal, false if not.
   */
  public boolean hasGoal(Goal goal) {
    return goal != null && hasGoal(goal.name);
  }

  /**
   * Check if this GoalMap contains a specific Goal (by name).
   * 
   * @param g Goal to check for.
   * @return True if Agent has goal, false if not.
   */
  public boolean hasGoal(String goalName) {
    return containsKey(goalName) && get(goalName) != null;
  }
  
  /**
   * If this GoalMap contains a goal named goalName, this method returns that goal.
   * 
   * @param goalName The name of the goal to be found.
   * @return the reference to the goal.
   */
  public Goal getGoalByName(String goalName) {
    if (containsKey(goalName)) {
      return get(goalName);
    }
    return null;
  }

}
