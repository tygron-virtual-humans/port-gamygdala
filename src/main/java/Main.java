import agent.Agent;
import data.Belief;
import data.Goal;
import decayfunction.ExponentialDecay;
import exception.GoalCongruenceMapException;
import gamygdala.Engine;

import java.util.ArrayList;

/**
 * Function test case during development.
 */
public class Main {

    /**
     * Run test.
     * 
     * @throws InterruptedException
     * @throws GoalCongruenceMapException 
     */
    public static void main(String[] args) throws InterruptedException, GoalCongruenceMapException {

        // Create new Gamygdala engine
        Engine engine = Engine.getInstance();

        // Create new Agents
        Agent mario = engine.createAgent("mario");
        Agent bowser = engine.createAgent("bowser");

        // Create goals for Mario
        Goal rescuePeachGoal = engine.createGoalForAgent(mario, "rescue-peach", 1, false);
        Goal surviveGoal = engine.createGoalForAgent(mario, "survive", .8, false);

        // Create goals for Bowser
        Goal killMarioGoal = engine.createGoalForAgent(bowser, "kill-mario", 1, false);

        double decayFactor = 0.8;
        double gain = 15;

        engine.setDecay(decayFactor, new ExponentialDecay(decayFactor));
        engine.setGain(gain);

        ArrayList<Goal> affectedGoals = new ArrayList<Goal>();
        ArrayList<Double> goalCongruences = new ArrayList<Double>();

        affectedGoals.add(rescuePeachGoal);
        affectedGoals.add(killMarioGoal);
        goalCongruences.add(0.3);
        goalCongruences.add(0.8);

        engine.appraise(new Belief(1, bowser, affectedGoals, goalCongruences, true));
        engine.printAllEmotions(false);

        Thread.sleep(1000L);

        engine.decayAll();

        engine.printAllEmotions(false);

    }

}
