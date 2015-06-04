package gamygdala;

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
     * Singleton Engine object.
     */
    private static Engine engineInstance;

    /**
     * Debug flag.
     */
    private static final boolean DEBUG = true;

    /**
     * Gamygdala instance.
     */
    private Gamygdala gamygdala = new Gamygdala();

    /**
     * Timestamp of last emotion calculation.
     */
    private long lastMillis = System.currentTimeMillis();

    /**
     * Empty constructor to prevent instantiating.
     * Use Engine.getInstance() instead.
     */
    private Engine() {

    }

    /**
     * Get the Engine object. If no Engine has been instantiated,
     * create a new Engine with a fresh Gamygdala instance.
     *
     * @return Engine instance of Engine
     */
    public static Engine getInstance() {
        if (engineInstance == null) {
            synchronized (Engine.class) {
                if (engineInstance == null) {
                    engineInstance = new Engine();
                }
            }
        }
        return engineInstance;
    }

    /**
     * Reset Engine by creating an Engine.
     *
     * @return Engine
     */
    public static Engine resetEngine() {
        if (engineInstance != null) {
            synchronized (Engine.class) {
                if (engineInstance != null) {
                    engineInstance = new Engine();
                }
            }
        }

        return engineInstance;
    }

    /**
     * Create and add an Agent to the Engine.
     *
     * @param name The name of the Agent.
     * @return Agent created Agent
     */
    public Agent createAgent(String name) {
        Agent agent = new Agent(name);
        gamygdala.getGamygdalaMap().registerAgent(agent);

        return agent;
    }

    /**
     * Add a Goal to an Agent and register with the Engine.
     *
     * @param agent             The Agent to register the Goal with.
     * @param goalName          The name of the new Goal.
     * @param goalUtility       The utility of the new Goal.
     * @param isMaintenanceGoal Whether or not this Goal is a maintenance goal.
     * @return The newly created Goal.
     */
    public Goal createGoalForAgent(Agent agent, String goalName, double goalUtility,
                                   boolean isMaintenanceGoal) {
        Goal goal = new Goal(goalName, goalUtility, isMaintenanceGoal);

        // Add Goal to Agent
        agent.addGoal(goal);

        // Register Goal with Engine
        gamygdala.getGamygdalaMap().registerGoal(goal);

        return goal;

    }

    /**
     * Create a relation with between two Agents.
     *
     * @param source   The source Agent.
     * @param target   The target Agent.
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

        Engine.debug("\n=====\nDecaying all emotions\n=====\n");

        // Record current time
        long now = System.currentTimeMillis();

        // Decay all emotions.
        gamygdala.decayAll(this.lastMillis, now);

        // Store time of last decay.
        this.lastMillis = now;

    }

    /**
     * The main emotional interpretation logic entry point. Performs the
     * complete appraisal of a single event (belief) for all agents.
     *
     * @param belief The current event to be appraised.
     */
    public void appraise(Belief belief) {
        gamygdala.appraise(belief, null);
    }

    /**
     * The main emotional interpretation logic entry point. Performs the
     * complete appraisal of a single event (belief) for only one agent. The
     * complete appraisal logic is executed including the effect on relations
     * (possibly influencing the emotional state of other agents), but only if
     * the affected agent (the one owning the goal) == affectedAgent. This is
     * sometimes needed for efficiency. If you as a game developer know that
     * particular agents can never appraise an event, then you can force
     * Gamygdala to only look at a subset of agents. Gamygdala assumes that the
     * affectedAgent is indeed the only goal owner affected and will not perform
     * any checks, nor use Gamygdalas list of known goals to find other agents
     * that share this goal.
     *
     * @param belief The current event to be appraised.
     * @param agent  The reference to the agent who appraises the event.
     */
    public void appraise(Belief belief, Agent agent) {
        gamygdala.appraise(belief, agent);
    }

    /**
     * Set the gain for the whole set of agents known to Gamygdala. For more
     * realistic, complex games, you would typically set the gain for each agent
     * type separately, to fine-tune the intensity of the response.
     *
     * @param gain The gain value [0 and 20].
     */
    public boolean setGain(double gain) {
        if (gain <= 0 || gain > 20) {
            Engine.debug("[Engine.setGain] Error: "
                    + "gain factor for appraisal integration must be between 0 and 20.");
            return false;
        }

        for (Entry<String, Agent> stringAgentEntry : this.gamygdala.getAgentMap().entrySet()) {
            if (stringAgentEntry.getValue() != null) {
                stringAgentEntry.getValue().setGain(gain);
            } else {
                this.gamygdala.getAgentMap().remove(stringAgentEntry.getKey());
            }
        }

        return true;
    }

    /**
     * Sets the decay factor and type for emotional decay, so that an emotion
     * will slowly get lower in intensity. Whenever decayAll is called, all
     * emotions for all agents are decayed according to the factor and function
     * set here.
     *
     * @param decayFactor   The decay factor. A factor of 1 means no decay.
     * @param decayFunction The decay function to be used.
     */
    public void setDecay(double decayFactor, DecayFunction decayFunction) {
        this.gamygdala.setDecayFactor(decayFactor);

        if (decayFunction != null) {
            this.gamygdala.setDecayFunction(decayFunction);
        } else {
            Engine.debug("[Engine.setDecay] DecayFunction is null.");
        }

    }

    /**
     * Get the Gamygdala instance for this Engine.
     *
     * @return The Gamygdala instance.
     */
    public Gamygdala getGamygdala() {
        return this.gamygdala;
    }

    /**
     * Set a new Gamygdala instance for this Engine.
     *
     * @param gamygdala The Gamygdala instance.
     */
    public void setGamygdala(Gamygdala gamygdala) {
        this.gamygdala = gamygdala;
    }

    /**
     * Facilitator method to print all emotional states to the console.
     *
     * @param gain Whether you want to print the gained (true) emotional states
     *             or non-gained (false).
     */
    public void printAllEmotions(boolean gain) {
        Agent agent;
        for (Entry<String, Agent> stringAgentEntry : this.gamygdala.getGamygdalaMap().getAgentMap().entrySet()) {
            agent = stringAgentEntry.getValue();

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
