package observer;

import data.Goal;

/**
 * Observer of a Subject class.
 */
public interface AgentObserver {

    public void update(Goal goal);
    
}
