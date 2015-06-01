import java.util.ArrayList;

import agent.Agent;
import data.Belief;
import data.Goal;
import decayfunction.ExponentialDecay;
import exception.GoalCongruenceMapException;
import gamygdala.Engine;

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
        Main main = new Main();

        main.runMarioGame();
    }

    private void runMarioGame() throws GoalCongruenceMapException, InterruptedException {
        // Create new Gamygdala engine
        Engine engine = Engine.getInstance();

        // Create new Agents
        Agent mario = engine.createAgent("mario");
        Agent bowser = engine.createAgent("bowser");
        Agent peach = engine.createAgent("peach");

        // Create goals for Mario
        Goal rescuePeachGoal = engine.createGoalForAgent(mario, "rescue-peach", 0.7, false);
        Goal surviveMarioGoal = engine.createGoalForAgent(mario, "survive-mario", 1.0, false);
        Goal collectCoinsGoal = engine.createGoalForAgent(mario, "collect-coins", 0.2, true);

        // Create goals for Bowser
        Goal killMarioGoal = engine.createGoalForAgent(bowser, "kill-mario", 1.0, false);
        Goal surviveBowserGoal = engine.createGoalForAgent(bowser, "lock-peach", 0.6, true);

        // Peach doesn't have goals, she only reacts to events by emotion.
        engine.createRelation(peach, mario, 0.6);
        engine.createRelation(bowser, mario, -0.9);

        // Set up the engine values for Super Mario game
        double decayFactor = 0.4;
        double gain = 10;

        engine.setDecay(decayFactor, new ExponentialDecay(decayFactor));
        engine.setGain(gain);

        ArrayList<Goal> affectedGoals = null;
        ArrayList<Double> goalCongruences = null;

        // Demo gameplay

        // Event 1: Mario collects a coin
        print_event(1, "Mario collects a coin");

        affectedGoals = new ArrayList<Goal>();
        goalCongruences = new ArrayList<Double>();

        affectedGoals.add(collectCoinsGoal);
        goalCongruences.add(0.1);

        engine.appraise(new Belief(1, mario, affectedGoals, goalCongruences, true));

        engine.printAllEmotions(true);

        // Event 2: Mario collects another coin after 1 second
        Thread.sleep(1000L);
        engine.decayAll();

        print_event(2, "Mario collects another coin");

        affectedGoals = new ArrayList<Goal>();
        goalCongruences = new ArrayList<Double>();

        affectedGoals.add(collectCoinsGoal);
        goalCongruences.add(0.1);

        engine.appraise(new Belief(1, mario, affectedGoals, goalCongruences, true));

        engine.printAllEmotions(true);

        // Event 3: Mario collects a mushroom after another second
        Thread.sleep(1000L);
        engine.decayAll();

        print_event(3, "Mario collects a mushroom");

        affectedGoals = new ArrayList<Goal>();
        goalCongruences = new ArrayList<Double>();

        affectedGoals.add(rescuePeachGoal);
        goalCongruences.add(0.3);
        affectedGoals.add(surviveMarioGoal);
        goalCongruences.add(0.6);
        affectedGoals.add(killMarioGoal);
        goalCongruences.add(-0.7);
        affectedGoals.add(surviveBowserGoal);
        goalCongruences.add(-0.3);

        engine.appraise(new Belief(1, mario, affectedGoals, goalCongruences, true));
        engine.printAllEmotions(true);

        // Event 4: Mario hits a turtle and losses the abilities of the mushroom
        Thread.sleep(1000L);
        engine.decayAll();

        print_event(4, "Mario hits a turtle");

        affectedGoals = new ArrayList<Goal>();
        goalCongruences = new ArrayList<Double>();

        affectedGoals.add(rescuePeachGoal);
        goalCongruences.add(-0.3);
        affectedGoals.add(surviveMarioGoal);
        goalCongruences.add(-0.6);
        affectedGoals.add(killMarioGoal);
        goalCongruences.add(0.7);
        affectedGoals.add(surviveBowserGoal);
        goalCongruences.add(0.3);

        engine.appraise(new Belief(1, mario, affectedGoals, goalCongruences, true));

        engine.printAllEmotions(true);

        // Event 5: Nothing happens while mario is walking through the world for 4 seconds
        Thread.sleep(4000L);
        engine.decayAll();

        print_event(5, "Nothing happens");

        engine.printAllEmotions(true);

        // Event 6: Mario kills bowser and saves peach
        Thread.sleep(2000L);
        engine.decayAll();

        print_event(6, "Bowser has been killend and Peach has been rescued by Mario!");

        affectedGoals = new ArrayList<Goal>();
        goalCongruences = new ArrayList<Double>();

        affectedGoals.add(surviveBowserGoal);
        goalCongruences.add(-1.0);
        affectedGoals.add(rescuePeachGoal);
        goalCongruences.add(1.0);
        affectedGoals.add(killMarioGoal);
        goalCongruences.add(-0.8);

        engine.appraise(new Belief(1, mario, affectedGoals, goalCongruences, true));

        engine.printAllEmotions(true);
    }

    private void print_event(int number, String event) {
        System.out.println("========");
        System.out.println("Event " + number);
        System.out.println(event);
        System.out.println("========");
    }

}
