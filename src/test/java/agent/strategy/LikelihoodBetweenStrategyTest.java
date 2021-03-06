package agent.strategy;

import static junit.framework.TestCase.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import agent.data.Emotion;

/**
 * Tests for strategy LikelihoodBetween.
 */
public class LikelihoodBetweenStrategyTest {

    DetermineStrategy determineStrategy;
    List<Emotion> emotionList;

    @Before
    public void setUp() throws Exception {
        determineStrategy = new LikelihoodBetweenStrategy();
        emotionList = new ArrayList<Emotion>();
    }

    @After
    public void tearDown() throws Exception {
        determineStrategy = null;
        emotionList = null;
    }

    @Test
    public void testGetEmotionFirst() throws Exception {
        emotionList.add(new Emotion("hope", 1));

        assertEquals(emotionList, determineStrategy.getEmotion(0, 0, 1));
    }

    @Test
    public void testGetEmotionSecond() throws Exception {
        emotionList.add(new Emotion("fear", 1));

        assertEquals(emotionList, determineStrategy.getEmotion(1, -1, 1));
    }

    @Test
    public void testGetEmotionThird() throws Exception {
        emotionList.add(new Emotion("hope", 1));

        assertEquals(emotionList, determineStrategy.getEmotion(-1, -1, 1));
    }

    @Test
    public void testGetEmotionForth() throws Exception {
        emotionList.add(new Emotion("fear", 1));

        assertEquals(emotionList, determineStrategy.getEmotion(-1, 1, 1));
    }

}
