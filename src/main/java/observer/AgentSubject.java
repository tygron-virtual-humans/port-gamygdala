package observer;

import data.Goal;


/**
 * Subject of a Observer / Listener class.
 */
public interface AgentSubject {
    
    public void attach(AgentObserver ob);
    public void remove(AgentObserver ob);
    public void notifyObservers(Goal goal);
    
}
