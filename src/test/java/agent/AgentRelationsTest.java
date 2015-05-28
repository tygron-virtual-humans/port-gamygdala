package agent;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import data.Emotion;

/**
 * Unit tests for AgentRelation.
 */
public class AgentRelationsTest {

    AgentRelations ar;
    Agent testAgent;
    
    @Before
    public void setUp() {
        ar = new AgentRelations();
        testAgent = new Agent("TestAgent");
    }
    
    @After
    public void tearDown() {
        ar = null;
        testAgent = null;
    }
    
    @Test
    public void testUpdateRelationEmpty() {
        
        assertEquals(0, ar.size());
        
        // Create new relation
        ar.updateRelation(testAgent, 1);
        assertEquals(1, ar.size());
    }
    
    @Test
    public void testUpdateRelationExisting() {
        
        ar.updateRelation(testAgent, 0);
        
        // Update existing relation
        ar.updateRelation(testAgent, 59);
        assertEquals(59, ar.get(0).like, 10E-15);

        
    }

    @Test
    public void testHasRelationWith() {
        
        assertFalse(ar.hasRelationWith(testAgent));
        
        ar.updateRelation(testAgent, 10);
        
        assertTrue(ar.hasRelationWith(testAgent));
        
    }

    @Test
    public void testGetRelation() {
        
        assertNull(ar.getRelation(testAgent));
        
        ar.updateRelation(testAgent, 10);
        assertNotNull(ar.getRelation(testAgent));
        assertEquals(testAgent, ar.getRelation(testAgent).getAgent());
        assertEquals(10d, ar.getRelation(testAgent).getLike(), 10E-15);
        
    }

    @Test
    public void testPrintRelations() {
        
        // Empty Agent, empty Relations
        assertEquals("", ar.printRelations(null));
        
        // Non-empty Agent, empty Relations
        assertEquals("", ar.printRelations(testAgent));
        
        // Non-empty Agent, non-empty Relations
        ar.updateRelation(testAgent, 1);
        
        Emotion testEmotion = new Emotion("TestEmotion", 10);
        ar.getRelation(testAgent).addEmotion(testEmotion);
        
        String expected = "TestEmotion(10.0) for <Agent[TestAgent]>";
        assertEquals(expected, ar.printRelations(testAgent));
        
        // Multiple relations
        ar.getRelation(testAgent).addEmotion(new Emotion("TestEmotion2", 2));
        expected = "TestEmotion(10.0), and TestEmotion2(2.0) for <Agent[TestAgent]>";
        assertEquals(expected, ar.printRelations(testAgent));
    }

}
