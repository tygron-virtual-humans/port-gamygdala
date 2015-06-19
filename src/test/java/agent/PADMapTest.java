package agent;

import java.util.List;

import agent.data.Emotion;
import agent.strategy.DetermineStrategy;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.mockito.Mockito.mock;

/**
 * Created by svenpopping on 19/06/15.
 */
public class PADMapTest {

    DetermineStrategy determineStrategy;
    PADMap padMap;

    @Before
    public void setUp() throws Exception {
        determineStrategy = mock(DetermineStrategy.class);
        padMap = PADMap.getInstance();
    }

    @After
    public void tearDown() throws Exception {
        determineStrategy = null;
    }

    @Test
    public void testGetInstance() throws Exception {
        assertNotNull(PADMap.getInstance());

        assertEquals(padMap, PADMap.getInstance());
    }

    @Test
    public void testGetMapPad() throws Exception {

    }

    @Test
    public void testGetPadState() throws Exception {

    }

    @Test (expected = IllegalArgumentException.class)
    public void testGetPadState1() throws Exception {
//        PADMap.getPadState(null, 0.1);
    }

    @Test
    public void testDetermineEmotionOne() {
        List<Emotion> emotionList = PADMap.determineEmotions(1, 1, 0);
    }

    @Test
    public void testDetermineEmotionZero() {
        List<Emotion> emotionList = PADMap.determineEmotions(1, 1, 0);
    }

    @Test
    public void testDetermineEmotionBetween() {
        List<Emotion> emotionList = PADMap.determineEmotions(1, 1, 0.5);
    }
}