/**
 * Created by svenpopping on 29/04/15.
 */
public class Main {

  /**
   * Create Gamygdala instance and run some functional tests.
   * @param args Command line parameters.
   */
  public static void main(String[] args) {

    Gamygdala engine = new Gamygdala();

    Agent player = engine.createAgent("player");
    
    System.out.println(player);

  }

}
