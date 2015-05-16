package data.map;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.Map;

import agent.Agent;
import data.Emotion;

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
        assertEquals(agent, map.getAgentByName("Agent1"));

        assertEquals(null, map.getAgentByName("Agent2"));
    }

    @Test
    public void testGetIterator() throws Exception {
        Iterator<Map.Entry<String, Agent>> iterator = map.getIterator();

        Map.Entry<String, Agent> firstEntry = iterator.next();

        assertEquals(agent, firstEntry.getValue());
    }

    @Test
    public void testPrintAllEmotions() throws Exception {
        agent.updateEmotionalState(new Emotion("Pity", 1.0));

        map.printAllEmotions(false);
    }
}
