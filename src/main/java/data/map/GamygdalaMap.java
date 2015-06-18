package data.map;

import agent.Agent;
import data.Goal;

import java.util.Map;
import java.util.Set;

/**
 * A collection of Agents and Goals for a Gamygdala instance.
 */
public class GamygdalaMap {

    /**
     * Collection of Agents.
     */
    private final AgentMap agentMap;

    /**
     * Collection of Goals.
     */
    private final GoalMap goalMap;

    /**
     * Construct a new GamygdalaMap.
     */
    public GamygdalaMap() {
        this.agentMap = new AgentMap();
        this.goalMap = new GoalMap();
    }

    /**
     * Get the AgentMap.
     * @return AgentMap
     */
    public AgentMap getAgentMap() {
        return agentMap;
    }

    /**
     * Get the GoalMap.
     * 
     * @return GoalMap.
     */
    public GoalMap getGoalMap() {
        return goalMap;
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

    /**
     * Get entry set from AgentMap.
     * @return Set Set of AgentMap.
     */
    public Set<Map.Entry<String, Agent>> getAgentSet() {
        return getAgentMap().entrySet();
    }

}
