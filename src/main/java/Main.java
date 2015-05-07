import agent.Agent;
import agent.AgentFactory;
import gamygdala.Gamygdala;

/**
 * Function test case during development.
 */
public class Main {

    /**
     * Run test.
     */
    public static void main(String[] args) {

        // Create new Gamygdala engine
        Gamygdala engine = new Gamygdala();

        // Create new Agents
        Agent mario = AgentFactory.createAgent("mario");
        Agent bowser = AgentFactory.createAgent("bowser");

        // Create goals for Mario
        engine.createGoalForAgent(mario, "rescue-peach", 1, false);
        engine.createGoalForAgent(mario, "survive", .8, true);

        // Create goals for Bowser
        engine.createGoalForAgent(bowser, "kill-mario", 1, false);


        // First, mario can't find peach


    }

}
