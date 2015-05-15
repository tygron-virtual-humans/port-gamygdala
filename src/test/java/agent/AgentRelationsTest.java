package agent;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for AgentRelation.
 */
public class AgentRelationsTest {

    AgentRelations ar;
    
    @Before
    public void setUp() {
        ar = new AgentRelations();
    }
    
    @After
    public void tearDown() {
        ar = null;
    }
    
    @Test
    public void testUpdateRelationEmpty() {
        
        assertEquals(0, ar.size());
        
        // Create new relation
        ar.updateRelation(new Agent("TestAgent"), 1);
        assertEquals(1, ar.size());
    }
    
    @Test
    public void testUpdateRelationExisting() {
        
        Agent testAgent = new Agent("TestAgent");
        ar.updateRelation(testAgent, 0);
        
        // Update existing relation
        ar.updateRelation(testAgent, 59);
        assertEquals(59, ar.get(0).like, 10E-15);

        
    }

    @Test
    public void testHasRelationWith() {
        
        Agent testAgent = new Agent("TestAgent");
        assertFalse(ar.hasRelationWith(testAgent));
        
        ar.updateRelation(testAgent, 10);
        
        assertTrue(ar.hasRelationWith(testAgent));
        
    }

    @Test
    public void testGetRelation() {
        
        Agent testAgent = new Agent("TestAgent");
        assertNull(ar.getRelation(testAgent));
        
        ar.updateRelation(testAgent, 10);
        assertNotNull(ar.getRelation(testAgent));
        assertEquals(testAgent, ar.getRelation(testAgent).getAgent());
        assertEquals(10d, ar.getRelation(testAgent).getLike(), 10E-15);
        
    }

    @Test
    public void testPrintRelations() {
        fail("Not yet implemented");
    }

}
