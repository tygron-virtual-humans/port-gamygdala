import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import agent.Agent;
import agent.data.Belief;
import agent.data.Goal;
import decayfunction.ExponentialDecay;
import exception.GoalCongruenceMapException;
import gamygdala.Engine;
import gamygdala.Gamygdala;
import static java.lang.Math.random;

/**
 *
 */
public class Scalability {

    public static void main(String[] args) throws InterruptedException, GoalCongruenceMapException {

    }

    public static long getTime(int agentAmount, int goalAmount,
                               int relationAmount, int beliefAmount, int affectedGoalAmount)
            throws InterruptedException, GoalCongruenceMapException {
        Engine.getInstance();
        Engine engine = setUp(agentAmount, goalAmount, relationAmount);
        long startingTime = System.currentTimeMillis();

        for (int i = 0; i < beliefAmount; i++) {
            engine.appraise(createBelief(engine, affectedGoalAmount));
        }
        engine.decayAll();

        long endTime = System.currentTimeMillis();
        System.out.println("DURATION: " + (endTime - startingTime));

        return (endTime - startingTime);
    }


    public static Engine setUp(int agentAmount, int goalAmount, int relationAmount) {
        Engine engine = Engine.resetEngine();
        Gamygdala gamygdala = new Gamygdala();

        engine.setGamygdala(gamygdala);
        engine.setGain(0.5);

        addAgentsWithGoals(engine, agentAmount, goalAmount);
        createRelation(engine, relationAmount);

        // Set up the engine values for Super Mario game
        double decayFactor = 0.4;
        double gain = 10;

        engine.getGamygdala().setDecayFunction(new ExponentialDecay(decayFactor));
        engine.setGain(gain);

        return engine;
    }

    public static void addAgentsWithGoals(Engine engine, int amountAgent, int goalAmount) {
        for (int i = 0; i < amountAgent; i++) {
            Agent agent = engine.createAgent(Long.toHexString(Double.doubleToLongBits(random())));
            for (int j = 0; j < goalAmount; j++) {
                engine.createGoalForAgent(
                        agent,
                        Long.toHexString(Double.doubleToLongBits(random())),
                        0.6,
                        getRandomBoolean()
                );
            }
        }
    }

    public static void createRelation(Engine engine, int relationAmount) {
        Object[] collection = engine.getGamygdala().getAgentMap().values().toArray();

        for (int i = 0; i < relationAmount; i++) {
            Agent source = (Agent) collection[(int) (random() * (collection.length))];
            Agent affected = (Agent) collection[(int) (random() * (collection.length))];

            if (source.equals(affected)) {
                engine.getGamygdala().createRelation(source, affected, new Random().nextDouble());
            } else {
                i--;
            }
        }
    }

    public static Belief createBelief(Engine engine, int affectedGoalAmount) throws GoalCongruenceMapException {
        Object[] collection = engine.getGamygdala().getAgentMap().values().toArray();
        Object[] goals = engine.getGamygdala().getGoalMap().values().toArray();

        List<Goal> goalList = randomSelectGoals(goals, affectedGoalAmount);
        List<Double> congruences = new ArrayList<Double>();
        for (int i = 0; i < goalList.size(); i++) {
            congruences.add(0.8);
        }

        return new Belief(
                0.7,
                (Agent) collection[(int) (random() * (collection.length))],
                goalList,
                congruences,
                getRandomBoolean()
        );
    }

    public static List<Goal> randomSelectGoals(Object[] goalArray, int affectedGoalAmount) {
        List<Goal> goals = new ArrayList<Goal>();

        for (int i = 0; i <  affectedGoalAmount; i++) {
            goals.add((Goal) goalArray[(int) (random() * (goalArray.length))]);
        }
        return goals;
    }

    public static boolean getRandomBoolean() {
        return new Random().nextBoolean();
    }
}
