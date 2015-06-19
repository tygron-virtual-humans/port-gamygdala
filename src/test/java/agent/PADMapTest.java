package agent;

import java.util.List;

import agent.data.Emotion;
import org.junit.Test;
import static junit.framework.Assert.assertNull;

/**
 * Created by svenpopping on 19/06/15.
 */
public class PADMapTest {

    List<Emotion> stringArray;

    @Test
    public void testGetInstance() throws Exception {

    }

    @Test
    public void testGetMapPad() throws Exception {

    }

    @Test
    public void testGetPadState() throws Exception {

    }

    @Test
    public void testGetPadState1() throws Exception {

    }

    @Test
    public void testDetermineEmotion() {
        assertNull(PADMap.determineEmotions(0, 0, 0));
    }

//    @Test
//    public void testDetermineEmotionsHope() {
//        List<Emotion> expected = new ArrayList<Emotion>() {{
//            add(new Emotion("hope", .010000000000000002));
//        }};
//        stringArray = PADMap.determineEmotions(-.1, -.1, 0.1);
//        assertEquals(expected, stringArray);
//    }
//
//    @Test
//    public void testDetermineEmotionsFear() {
//        List<Emotion> expected = new ArrayList<Emotion>() {{
//            add(new Emotion("fear", .001));
//        }};
//        stringArray = PADMap.determineEmotions(-.1, .01, 0.1);
//        assertEquals(expected, stringArray);
//    }
//
//    @Test
//    public void testDetermineEmotionsSatisfactionJoy() {
//        List<Emotion> expected = new ArrayList<Emotion>() {{
//            add(new Emotion("satisfaction", .294));
//            add(new Emotion("joy", .294));
//        }};
//        stringArray = PADMap.determineEmotions(.6, 0.49, 1.d);
//        assertEquals(expected, stringArray);
//    }
//
//    @Test
//    public void testDetermineEmotionsFearConfirmedDistress() {
//        List<Emotion> expected = new ArrayList<Emotion>() {{
//            add(new Emotion("fear-confirmed", .049));
//            add(new Emotion("distress", .049));
//        }};
//        stringArray = PADMap.determineEmotions(-.1, 0.49, 1.d);
//        assertEquals(expected, stringArray);
//    }
//
//    @Test
//    public void testDetermineEmotionsDisappointmentDistress() {
//        List<Emotion> expected = new ArrayList<Emotion>() {{
//            add(new Emotion("disappointment", 1));
//            add(new Emotion("distress", 1));
//        }};
//        stringArray = PADMap.determineEmotions(0.1, 0.51, -.1);
//        assertEquals(expected, stringArray);
//    }
//
//    @Test
//    public void testDetermineEmotionsReliefJoy() {
//        List<Emotion> expected = new ArrayList<Emotion>() {{
//            add(new Emotion("relief", 1));
//            add(new Emotion("joy", 1));
//        }};
//        stringArray = PADMap.determineEmotions(-.1, 0.51, -.1);
//        assertEquals(expected, stringArray);
//    }

}