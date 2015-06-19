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
public class LikelihoodOneStrategyTest {

    DetermineStrategy determineStrategy;
    List<Emotion> emotionList;

    @Before
    public void setUp() throws Exception {
        determineStrategy = new LikelihoodOneStrategy();
        emotionList = new ArrayList<Emotion>();
    }

    @After
    public void tearDown() throws Exception {
        determineStrategy = null;
        emotionList = null;
    }

    @Test
    public void testDetermineEmotionsSatisfactionJoy() {
        emotionList.add(new Emotion("satisfaction", 1));
        emotionList.add(new Emotion("joy", 1));

        determineStrategy.getEmotion(.6, 0.49, 1.d);
        assertEquals(emotionList, determineStrategy.getEmotion(.6, .49, 1));
    }

    @Test
    public void testDetermineEmotionsFearConfirmedDistress() {
        emotionList.add(new Emotion("fear-confirmed", 1));
        emotionList.add(new Emotion("distress", 1));

        assertEquals(emotionList, determineStrategy.getEmotion(-.1, 0.49, 1));
    }
}