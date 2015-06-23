package gamygdala;

import agent.Agent;
import agent.data.Belief;
import agent.data.Goal;
import debug.Debug;

/**
 * Gaming Engine adapter for Gamygdala.
 */
public final class Engine {

    /**
     * Singleton Engine object.
     */
    private static Engine engineInstance;

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
     * @param newGamygdala The Gamygdala instance.
     */
    public void setGamygdala(Gamygdala newGamygdala) {
        this.gamygdala = newGamygdala;
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
    public static synchronized Engine resetEngine() {
        if (engineInstance != null) {
            engineInstance = new Engine();
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
        return this.gamygdala.createAgent(name);
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
        return this.gamygdala.createGoalForAgent(agent, goalName, goalUtility, isMaintenanceGoal);
    }

    /**
     * Create a relation with between two Agents.
     *
     * @param source   The source Agent.
     * @param target   The target Agent.
     * @param relation The intensity of the relation.
     */
    public void createRelation(Agent source, Agent target, double relation) {
        this.gamygdala.createRelation(source, target, relation);
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
     * loop call it, or have e.g., Phaser call it in the plugin updateState, which is
     * default now). Further, if you want to tweak the emotional intensity decay
     * of individual agents, you should tweak the decayFactor per agent not the
     * "frame rate" of the decay (as this doesn't change the rate).
     */
    public void decayAll() {
        Debug.debug("\n=====\nDecaying all emotions\n=====\n");

        // Record current time
        long now = System.currentTimeMillis();

        // Decay all emotions.
        this.gamygdala.decayAll(this.lastMillis, now);

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
        this.gamygdala.appraise(belief, null);
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
        this.gamygdala.appraise(belief, agent);
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
            Debug.debug("[Engine.setGain] Error: "
                    + "gain factor for appraisal integration must be between 0 and 20.");
            return false;
        }
        this.gamygdala.setAgentsGain(gain);
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Engine)) return false;

        Engine engine = (Engine) o;

        if (lastMillis != engine.lastMillis) return false;
        return !(getGamygdala() != null ? !getGamygdala().equals(engine.getGamygdala()) : engine.getGamygdala() != null);

    }

    @Override
    public int hashCode() {
        int result = getGamygdala() != null ? getGamygdala().hashCode() : 0;
        result = 31 * result + (int) (lastMillis ^ (lastMillis >>> 32));
        return result;
    }
}
