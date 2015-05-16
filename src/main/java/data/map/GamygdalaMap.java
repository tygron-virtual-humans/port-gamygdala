package data.map;

import java.util.Iterator;
import java.util.Map;

import agent.Agent;
import data.Goal;

/**
 * A collection of Agents and Goals for a Gamygdala instance.
 */
public class GamygdalaMap {

    /**
     *
     */
    private AgentMap agentMap;

    /**
     *
     */
    private GoalMap goalMap;

    /**
     *
     */
    public GamygdalaMap() {
        this.agentMap = new AgentMap();
        this.goalMap = new GoalMap();
    }

    /**
     * @return
     */
    public AgentMap getAgentMap() {
        return agentMap;
    }

    /**
     * @param agentMap
     */
    public void setAgentMap(AgentMap agentMap) {
        this.agentMap = agentMap;
    }

    /**
     * @return
     */
    public GoalMap getGoalMap() {
        return goalMap;
    }

    /**
     * @param goalMap
     */
    public void setGoalMap(GoalMap goalMap) {
        this.goalMap = goalMap;
    }

    /**
     * Adds an Agent to this GamygdalaMap.
     * 
     * @param agent The agent to add.
     */
    public void registerAgent(Agent agent) {
        getAgentMap().put(agent.name, agent);
    }
    
    /**
     * Adds a Goal to this GamygdalaMap.
     * 
     * @param goal The goal to add.
     */
    public void registerGoal(Goal goal) {
        getGoalMap().put(goal.getName(), goal);
    }
    
    public Iterator<Map.Entry<String, Agent>> getAgentIterator() {
        return getAgentMap().getIterator();
    }
}
