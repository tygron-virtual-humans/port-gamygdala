package exception;

/**
 * GoalCongruenceMapException.
 */
public class GoalCongruenceMapException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 1706142455682560064L;
    
    public GoalCongruenceMapException() {
        super("Error: the congruence list does not have the same size as the affected goal list.");
    }
    
}
