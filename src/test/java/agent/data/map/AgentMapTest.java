package agent.data.map;

import agent.Agent;
import junit.framework.TestCase;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by svenpopping on 19/06/15.
 */
public class AgentMapTest extends TestCase {

    AgentMap agentMap;
    Agent agent;

    /**
     * Setup: Create a AgentMap and a Mock of Agent.
     * @throws Exception
     */
    public void setUp() throws Exception {
        super.setUp();
        agentMap = new AgentMap();
        agent = mock(Agent.class);

        when(agent.getName()).thenReturn("TestAgent");
        agentMap.put(agent.getName(), agent);
    }

    /**
     * Teardown.
     * @throws Exception
     */
    public void tearDown() throws Exception {
        agent = null;
        agentMap = null;
    }

    /**
     * GetAgentByName when name exist.
     * @throws Exception
     */
    public void testGetAgentByName() throws Exception {
        assertEquals(agent, agentMap.getAgentByName("TestAgent"));
    }

    /**
     * GetAgentByName when name doesn't exist.
     * @throws Exception
     */
    public void testGetAgentByNameFalse() throws Exception {
        assertEquals(null, agentMap.getAgentByName("TestAgent1"));
    }

    /**
     * ToStringAllEmotions with mock of Agent
     * @throws Exception
     */
    public void testToStringAllEmotions() throws Exception {
        assertEquals("null null", agentMap.toStringAllEmotions(true));
    }

    /**
     * Add Agent to the Map twice
     * @throws Exception
     */
    public void testPut() throws Exception {
        assertEquals(null, agentMap.put(agent.getName(), agent));
    }
}