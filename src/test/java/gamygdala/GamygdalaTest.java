package gamygdala;

import org.junit.After;
import org.junit.Before;

/**
 * Test main Gamygdala engine.
 */
public class GamygdalaTest {

  Gamygdala gamyg;

  @Before
  public void setUp() {
    gamyg = new Gamygdala();
  }

  @After
  public void cleanUp() {
    gamyg = null;
  }
  
  // TODO(Sven) Write Gamygdala tests for new implementation.

}
