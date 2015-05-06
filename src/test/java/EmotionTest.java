import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class EmotionTest {

  @Test
  public void testEmotion() {

    Emotion emo = new Emotion("Emotion", .5);
    assertEquals("Emotion", emo.name);
    assertEquals(.5, emo.intensity, 10E-15);

  }

  @Test
  public void testToString() {
    Emotion emo = new Emotion("Test", .75);
    assertEquals("<Emotion[Test, 0.75]>", emo.toString());
  }

  @Test
  public void testEquals() {
    Emotion emo1;
    Emotion emo2;

    emo1 = new Emotion("Test", .75);
    emo2 = new Emotion("Test", .75);
    assertEquals(emo1, emo2);

    emo1 = new Emotion("Test", 0);
    emo2 = new Emotion("Test", .75);
    assertNotEquals(emo1, emo2);

    emo1 = new Emotion("Emotion", 0);
    emo2 = new Emotion("Test", .75);
    assertNotEquals(emo1, emo2);

    emo1 = new Emotion("Emotion", .75);
    emo2 = new Emotion("Test", .75);
    assertNotEquals(emo1, emo2);

    String str = new String("Test");
    emo2 = new Emotion("Test", .75);
    assertFalse(emo2.equals(str));
  }

}
