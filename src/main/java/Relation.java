import java.util.ArrayList;


public class Relation {
	
	public String agentName;
    public double like;
    public ArrayList<Emotion> emotionList;
	
	/**
	 * This is the class that represents a relation one agent has with other agents.
	 * It's main role is to store and manage the emotions felt for a target agent (e.g angry at, or pity for).
	 * Each agent maintains a list of relations, one relation for each target agent.
	 * @class TUDelft.Gamygdala.Relation
	 * @constructor 
	 * @param {String} targetName The agent who is the target of the relation.
	 * @param {double} relation The relation [-1 and 1].
	 */
	public Relation(String targetName, double like) {
	    this.agentName = targetName;
	    this.like = like;
	    this.emotionList = new ArrayList<Emotion>();
	}

	public void addEmotion(Emotion emotion) {
	    boolean added = false;
	    for (int i = 0; i < this.emotionList.size(); i++) {
	    	
	    	Emotion e;
	    	e = this.emotionList.get(i);
	    	
	        if (e.name == emotion.name) {
	        	e.intensity += emotion.intensity;
	            // Check if this works just by ref, else: 
	        	// this.emotionList.set(i, e);
	            added = true;
	        }
	    }
	    if (added == false) {
	        //copy on keep, we need to maintain a list of current emotions for the relation, not a list refs to the appraisal engine
	        this.emotionList.add(new Emotion(emotion.name, emotion.intensity));
	    }
	}

	public void decay(Gamygdala gamygdalaInstance) {
	    for (int i = 0; i < this.emotionList.size(); i++) {
	    	
	    	Emotion e = this.emotionList.get(i);
	    	
	        double newIntensity = gamygdalaInstance.decayFunction.decay(e.intensity, gamygdalaInstance.getMillisPassed());

	        if (newIntensity < 0) {
	            //This emotion has decayed below zero, we need to remove it.
	            this.emotionList.remove(i);
	        }
	        else {
	        	
	        	e.intensity = newIntensity;
	        	// Check if this works just by ref, else: 
	            // this.emotionList.set(i, e);
	        }
	    }
	}
	
}
