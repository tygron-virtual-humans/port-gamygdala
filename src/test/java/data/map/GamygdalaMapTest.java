package data.map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Nordin on 13-5-2015.
 */
public class GamygdalaMapTest {

    GamygdalaMap gamygdalaMap;
    AgentMap agentMap;
    GoalMap goalMap;

    @Before
    public void setUp() throws Exception {
        agentMap = new AgentMap();
        goalMap = new GoalMap();

        gamygdalaMap = new GamygdalaMap();
    }

    @After
    public void tearDown() throws Exception {
        agentMap = null;
        goalMap = null;
        gamygdalaMap = null;
    }

    @Test
    public void testGetAgentMap() throws Exception {
        assertNotEquals(null, gamygdalaMap.getAgentMap());
    }

    @Test
    public void testSetAgentMap() throws Exception {
        gamygdalaMap.setAgentMap(agentMap);

        assertEquals(agentMap, gamygdalaMap.getAgentMap());
    }

    @Test
    public void testGetGoalMap() throws Exception {
        assertNotEquals(null, gamygdalaMap.getAgentMap());
    }

    @Test
    public void testSetGoalMap() throws Exception {
        gamygdalaMap.setGoalMap(goalMap);

        assertEquals(goalMap, gamygdalaMap.getGoalMap());
    }
}