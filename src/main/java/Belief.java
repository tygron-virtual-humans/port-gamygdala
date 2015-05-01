import java.util.ArrayList;

public class Belief {

	public double likelihood;
	public String causalAgentName;
	public ArrayList<String> affectedGoalNames;
	public ArrayList<Double> goalCongruences;
	public boolean isIncremental;

	/**
	 * This class is a data structure to store one Belief for an agent
	 * A belief is created and fed into a Gamygdala instance (method Gamygdala.appraise()) for evaluation
	 * 
	 * @param {double} likelihood The likelihood of this belief to be true.
	 * @param {String} causalAgentName The agent's name of the causal agent of this belief.
	 * @param {String[]} affectedGoalNames An array of affected goals' names.
	 * @param {double[]} goalCongruences An array of the affected goals' congruences (i.e., the extend to which this event is good or bad for a goal [-1,1]).
	 * @param {boolean} [isIncremental] Incremental evidence enforces gamygdala to see this event as incremental evidence for (or against) the list of goals provided, i.e, it will add or subtract this belief's likelihood*congruence from the goal likelihood instead of using the belief as "state" defining the absolute likelihood
	 */
	public Belief(double likelihood, String causalAgentName, ArrayList<String> affectedGoalNames, ArrayList<Double> goalCongruences, boolean isIncremental) {
		if (isIncremental) {
			this.isIncremental = isIncremental;// incremental evidence enforces Gamygdala to use the likelihood as delta, i.e, it will add or subtract this belief's likelihood from the goal likelihood instead of using the belief as "state" defining the absolute likelihood.
		} else {
			this.isIncremental = false;
		}

		this.likelihood = Math.min(1, Math.max(-1, likelihood));
		this.causalAgentName = causalAgentName;
		this.affectedGoalNames = new ArrayList<String>();
		this.goalCongruences = new ArrayList<Double>();

		// copy on keep
		for (int i = 0; i < affectedGoalNames.size(); i++) {
			this.affectedGoalNames.add(affectedGoalNames.get(i));
		}
		
		for (int i = 0; i < goalCongruences.size(); i++) {
			this.goalCongruences.add(Math.min(1, Math.max(-1, goalCongruences.get(i))));
		}
	}

}
