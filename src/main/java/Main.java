import gamygdala.Gamygdala;

import java.util.ArrayList;

import agent.Agent;
import agent.AgentFactory;
import data.Belief;
import data.Goal;

/**
 * Function test case during development.
 */
public class Main {

  /**
   * Run test.
   * @throws InterruptedException 
   */
  public static void main(String[] args) throws InterruptedException {

    // Create new Gamygdala engine
    Gamygdala engine = new Gamygdala();

    // Create new Agents
    Agent mario = AgentFactory.createAgent("mario");
    Agent bowser = AgentFactory.createAgent("bowser");

    // Register agents with engine
    engine.registerAgent(mario);
    engine.registerAgent(bowser);

    // Create goals for Mario
    Goal rescuePeachGoal = new Goal("rescue-peach", 1, false);
    Goal surviveGoal = new Goal("survive", .8, true);
    
    mario.addGoal(rescuePeachGoal);
    mario.addGoal(surviveGoal);
    
    engine.registerGoal(rescuePeachGoal);
    engine.registerGoal(surviveGoal);

    // Create goals for Bowser
    Goal killMarioGoal = new Goal("kill-mario", 1, false);
    
    bowser.addGoal(killMarioGoal);
    engine.registerGoal(killMarioGoal);

    // ---
    // Display initial state
    engine.printAllEmotions(false);
    System.out.println("===\n");
    // ---

    // Init commonly used variables
    ArrayList<Goal> affectedGoals = new ArrayList<Goal>();
    ArrayList<Double> goalCongruences = new ArrayList<Double>();

    // First, mario can't find peach
    affectedGoals.clear();
    goalCongruences.clear();

    affectedGoals.add(rescuePeachGoal);
    goalCongruences.add(0.25);

    engine.appraise(new Belief(1, mario, affectedGoals, goalCongruences, true), null);
    
    // Then, he finds her!
    
    
    // ---
    // Display emotions
    engine.printAllEmotions(false);
    engine.printAllEmotions(true);
    System.out.println("===\n");
    // ---

  }

}
