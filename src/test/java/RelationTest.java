import static org.junit.Assert.*;

import org.junit.Test;


public class RelationTest {

	@Test
	public void testRelation() {
		Relation r = new Relation("TestAgent2", 0);
		
		assertEquals("TestAgent2", r.agentName);
		assertEquals(0, r.like, 10E-15);
		assertNotNull(r.emotionList);
	}

	@Test
	public void testAddEmotion1() {
		Relation r = new Relation("TestAgent2", 0);
		Emotion e = new Emotion("TestEmotion", 10);
		
		r.addEmotion(e);
		assertTrue(r.emotionList.contains(e));
	}
	
	@Test
	public void testAddEmotion2() {
		Relation r = new Relation("TestAgent2", 0);
		Emotion e1 = new Emotion("TestEmotion", 10);
		Emotion e2 = new Emotion("TestEmotion", 20);
		
		r.addEmotion(e1);
		r.addEmotion(e2);
		
		assertEquals(1, r.emotionList.size());
		
		Emotion e3 = r.emotionList.get(0);
		assertEquals("TestEmotion", e3.name);
		assertEquals(30, e3.intensity, 10E-15);
		
	}

}
