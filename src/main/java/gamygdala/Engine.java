package gamygdala;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import agent.Agent;
import data.Belief;
import data.Goal;
import decayfunction.DecayFunction;

/**
 * Gaming Engine adapter for Gamygdala.
 */
public class Engine {

    /**
     * Debug flag.
     */
    public static final boolean DEBUG = true;

    /**
     * Gamygdala instance.
     */
    private Gamygdala gamygdala;

    /**
     * Timestamp of last emotion calculation.
     */
    private long lastMillis;

    public Engine(Gamygdala gamygdala) {

        // Store Gamygdala instance
        this.gamygdala = gamygdala;

        // Record current time on creation
        this.lastMillis = System.currentTimeMillis();

    }

    /**
     * Create and add an Agent to the Engine.
     *
     * @param name The name of the Agent.
     */
    public Agent createAgent(String name) {
        Agent agent = new Agent(name);
        gamygdala.gamydgalaMap.registerAgent(agent);
        return agent;
    }

    /**
     * Add a Goal to an Agent and register with the Engine.
     * 
     * @param agent The Agent to register the Goal with.
     * @param goalName The name of the new Goal.
     * @param goalUtility The utility of the new Goal.
     * @param isMaintenanceGoal Whether or not this Goal is a maintenance goal.
     * @return The newly created Goal.
     */
    public Goal createGoalForAgent(Agent agent, String goalName, double goalUtility, boolean isMaintenanceGoal) {
        Goal goal = new Goal(goalName, goalUtility, isMaintenanceGoal);

        // Add Goal to Agent
        agent.addGoal(goal);

        // Register Goal with Engine
        gamygdala.gamydgalaMap.registerGoal(goal);

        return goal;

    }

    /**
     * Create a relation with between two Agents.
     * 
     * @param source The source Agent.
     * @param target The target Agent.
     * @param relation The intensity of the relation.
     */
    public void createRelation(Agent source, Agent target, double relation) {
        source.updateRelation(target, relation);
    }

    /**
     * This method decays for all registered agents the emotional state and
     * relations. It performs the decay according to the time passed, so longer
     * intervals between consecutive calls result in bigger clunky steps.
     * Typically this is called automatically when you use startDecay(), but you
     * can use it yourself if you want to manage the timing. This function is
     * keeping track of the millis passed since the last call, and will (try to)
     * keep the decay close to the desired decay factor, regardless the time
     * passed So you can call this any time you want (or, e.g., have the game
     * loop call it, or have e.g., Phaser call it in the plugin update, which is
     * default now). Further, if you want to tweak the emotional intensity decay
     * of individual agents, you should tweak the decayFactor per agent not the
     * "frame rate" of the decay (as this doesn't change the rate).
     */
    public void decayAll() {

        // Record current time
        long now = System.currentTimeMillis();

        // Decay all emotions.
        gamygdala.decayAll(this.lastMillis, now);

        // Store time of last decay.
        this.lastMillis = now;

    }

    public void appraise(Belief belief) {
        gamygdala.appraise(belief, null);
    }

    public void appraise(Belief belief, Agent agent) {
        gamygdala.appraise(belief, agent);
    }

    /**
     * Facilitator to set the gain for the whole set of agents known to
     * TUDelft.Gamygdala. For more realistic, complex games, you would typically
     * set the gain for each agent type separately, to finetune the intensity of
     * the response.
     *
     * @param gain The gain value [0 and 20].
     */
    public void setGain(double gain) {

        if (gain <= 0 || gain > 20) {
            Engine.debug("[Gamygdala.setGain] Error: " + "gain factor for appraisal integration must be between 0 and 20.");
            return;
        }

        Iterator<Entry<String, Agent>> it = gamygdala.gamydgalaMap.getAgentIterator();
        Agent temp;
        while (it.hasNext()) {
            Map.Entry<String, Agent> pair = it.next();

            temp = pair.getValue();
            if (temp != null) {
                temp.setGain(gain);
            } else {
                it.remove();
            }

        }

    }

    /**
     * Sets the decay factor and function for emotional decay. It sets the decay
     * factor and type for emotional decay, so that an emotion will slowly get
     * lower in intensity. Whenever decayAll is called, all emotions for all
     * agents are decayed according to the factor and function set here.
     *
     * @param decayFactor The decay factor. A factor of 1 means no decay.
     * @param decayFunction The decay function to be used.
     */
    public void setDecay(double decayFactor, DecayFunction decayFunction) {

        gamygdala.decayFactor = decayFactor;

        if (decayFunction != null) {
            gamygdala.decayFunction = decayFunction;
        } else {
            Engine.debug("[Engine.setDecay] DecayFunction is null.");
        }

    }

    /**
     * Facilitator method to print all emotional states to the console.
     *
     * @param gain Whether you want to print the gained (true) emotional states
     *            or non-gained (false).
     */
    public void printAllEmotions(boolean gain) {

        Iterator<Entry<String, Agent>> it = gamygdala.gamydgalaMap.getAgentIterator();
        Agent agent;
        while (it.hasNext()) {
            Map.Entry<String, Agent> pair = it.next();
            agent = pair.getValue();

            agent.printEmotionalState(gain);
            agent.printRelations(null);
        }
    }

    /**
     * Print debug information to console if the debug flag is set to true.
     *
     * @param what Object to print to console.
     */
    public static void debug(Object what) {
        if (Engine.DEBUG) {
            System.out.println(what);
        }
    }

}
