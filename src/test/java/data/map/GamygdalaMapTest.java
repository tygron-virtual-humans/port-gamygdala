package data.map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotEquals;

/**
 * Tests for GamygdalaMap.
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
    public void testGetGoalMap() throws Exception {
        assertNotEquals(null, gamygdalaMap.getAgentMap());
    }

}
