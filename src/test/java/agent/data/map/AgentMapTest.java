package agent.data.map;

import agent.Agent;
import agent.data.Emotion;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * Tests for AgentMap.
 */
public class AgentMapTest {

    AgentMap map;
    Agent agent;

    @Before
    public void setUp() throws Exception {
        agent = new Agent("TestAgent");
        map = new AgentMap();
        map.put("Agent1", agent);
    }

    @After
    public void tearDown() throws Exception {
        agent = null;
        map = null;
    }

    @Test
    public void testGetAgentByName() throws Exception {
        assertEquals(agent, map.getAgentByName("TestAgent"));

        assertEquals(null, map.getAgentByName("Agent2"));
    }

    @Test
    public void testGetIterator() throws Exception {
        Set<Map.Entry<String, Agent>> entries = map.entrySet();

        Agent firstEntry = entries.iterator().next().getValue();
        assertEquals(agent, firstEntry);
    }

    @Test
    public void testPrintAllEmotions() throws Exception {
        agent.updateEmotionalState(new Emotion("Pity", 1.0));

        map.toStringAllEmotions(false);
    }
}
