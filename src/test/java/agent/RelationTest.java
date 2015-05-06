package agent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import data.Emotion;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RelationTest {

  private Relation rel;
  
  @Before
  public void setUp() {
    rel = new Relation("TestAgent2", 0);
  }
  
  @After
  public void cleanUp() {
    rel = null;
  }
  
  @Test
  public void testRelation() {

    assertEquals("TestAgent2", rel.agentName);
    assertEquals(0, rel.like, 10E-15);
    assertNotNull(rel.emotionList);
  }

  @Test
  public void testAddEmotion1() {

    Emotion emo = new Emotion("TestEmotion", 10);

    rel.addEmotion(emo);
    assertTrue(rel.emotionList.contains(emo));
  }

  @Test
  public void testAddEmotion2() {

    Emotion e1 = new Emotion("TestEmotion", 10);
    Emotion e2 = new Emotion("TestEmotion", 20);

    rel.addEmotion(e1);
    rel.addEmotion(e2);

    assertEquals(1, rel.emotionList.size());

    Emotion e3 = rel.emotionList.get(0);
    assertEquals("TestEmotion", e3.name);
    assertEquals(30, e3.intensity, 10E-15);

  }

}
