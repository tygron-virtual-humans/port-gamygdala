package agent.strategy;

import java.util.ArrayList;
import java.util.List;

import agent.data.Emotion;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static junit.framework.TestCase.assertEquals;

/**
 * Created by svenpopping on 19/06/15.
 */
public class LikelihoodZeroStrategyTest {

    DetermineStrategy determineStrategy;
    List<Emotion> emotionList;

    @Before
    public void setUp() throws Exception {
        determineStrategy = new LikelihoodZeroStrategy();
        emotionList = new ArrayList<Emotion>();
    }

    @After
    public void tearDown() throws Exception {
        determineStrategy = null;
        emotionList = null;
    }

    @Test
    public void testDetermineEmotionsDisappointmentDistress() {
        emotionList.add(new Emotion("disappointment", 1));
        emotionList.add(new Emotion("distress", 1));

        assertEquals(emotionList, determineStrategy.getEmotion(0.1, 0.51, 1));
    }

    @Test
    public void testDetermineEmotionsReliefJoy() {
        emotionList.add(new Emotion("relief", 1));
        emotionList.add(new Emotion("joy", 1));

        assertEquals(emotionList, determineStrategy.getEmotion(-.1, 0.51, 1));
    }
}