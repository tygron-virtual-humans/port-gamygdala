package data.map;

/**
 * Created by svenpopping on 06/05/15.
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
}
