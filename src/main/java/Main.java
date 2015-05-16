import java.util.ArrayList;

import agent.Agent;
import data.Belief;
import data.Goal;
import decayfunction.ExponentialDecay;
import gamygdala.Engine;
import gamygdala.Gamygdala;

/**
 * Function test case during development.
 */
public class Main {

    /**
     * Run test.
     * 
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {

        // Create new Gamygdala engine
        Engine engine = new Engine(new Gamygdala());

        // Create new Agents
        Agent mario = engine.createAgent("mario");
        Agent bowser = engine.createAgent("bowser");

        // Create goals for Mario
        Goal rescuePeachGoal = engine.createGoalForAgent(mario, "rescue-peach", 1, false);
        Goal surviveGoal = engine.createGoalForAgent(mario, "survive", .8, false);

        // Create goals for Bowser
        Goal killMarioGoal = engine.createGoalForAgent(bowser, "kill-mario", 1, false);

        double decayFactor = 0.6;
        double gain = 15;

        engine.setDecay(decayFactor, new ExponentialDecay(decayFactor));
        engine.setGain(gain);

        ArrayList<Goal> affectedGoals = new ArrayList<Goal>();
        ArrayList<Double> goalCongruences = new ArrayList<Double>();

        // First, mario can't find peach
        affectedGoals.clear();
        goalCongruences.clear();

        affectedGoals.add(rescuePeachGoal);
        affectedGoals.add(killMarioGoal);
        goalCongruences.add(0.3);
        goalCongruences.add(0.8);

        Belief b = new Belief(1, bowser, affectedGoals, goalCongruences, true);

        engine.appraise(b);

        Thread n = new Thread() {
            public void run() {
                System.out.println("Waiting...");
                try {
                    sleep(1000);
                    System.out.println("Go!");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        n.start();
        n.join();

        engine.decayAll();

        engine.printAllEmotions(true);
        /*
         * // --- // Display initial state engine.printAllEmotions(false);
         * System.out.println("===\n"); // ---
         * 
         * // Init commonly used variables ArrayList<Goal> affectedGoals = new
         * ArrayList<Goal>(); ArrayList<Double> goalCongruences = new
         * ArrayList<Double>();
         * 
         * // First, mario can't find peach affectedGoals.clear();
         * goalCongruences.clear();
         * 
         * affectedGoals.add(rescuePeachGoal); goalCongruences.add(0.25);
         * 
         * engine.appraise(new Belief(1, mario, affectedGoals, goalCongruences,
         * true), null);
         * 
         * // Then, he finds her! engine.decayAll();
         * 
         * // --- // Display emotions engine.printAllEmotions(false);
         * engine.printAllEmotions(true); System.out.println("===\n"); // ---
         */

    }

}
