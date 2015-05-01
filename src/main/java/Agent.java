import java.util.ArrayList;
import java.util.HashMap;

public class Agent {

  /**
   * The name of this Agent.
   */
  public String name;

  /**
   * Collection of goals for this Agent.
   */
  public GoalMap goals;

  /**
   * Collection of relations for this Agent.
   */
  public ArrayList<Relation> currentRelations;

  /**
   * Collection of emotions for this Agent.
   */
  public ArrayList<Emotion> internalState;

  /**
   * The gain for this agent. Must be between 0 and 20 inclusive.
   */
  public double gain;

  /**
   * The Gamygdala instance to which this Agent is linked.
   */
  private Gamygdala gamygdalaInstance;

  /**
   * Pleasure Arousal Dominance mapping.
   */
  private MapPad mapPad;

  public static final double DEFAULT_GAIN = 1;

  /**
   * Create new Agent.
   * 
   * @param name Agent name.
   */
  public Agent(String name) {
    this.name = name;

    // Init goal map.
    this.goals = new GoalMap();

    // Init relation and emotion collections
    this.currentRelations = new ArrayList<Relation>();
    this.internalState = new ArrayList<Emotion>();

    // Set gain
    this.gain = Agent.DEFAULT_GAIN;

    // Initialize PAD map
    this.mapPad = new MapPad();
  }
  
  /**
   * Add Goal.
   * 
   * @param goal Goal to add.
   * @return True if goal was added successfully, false if not.
   */
  public boolean addGoal(Goal goal) {
    return this.goals.addGoal(goal);
  }

  /**
   * Remove Goal.
   * 
   * @param g Goal to remove.
   * @return True if goal was removed successfully, false if not.
   */
  public boolean removeGoal(Goal goal) {
    return this.goals.removeGoal(goal);
  }

  /**
   * Check if this GoalMap has a specific Goal (by object).
   * 
   * @param g Goal to check for.
   * @return True if Agent has goal, false if not.
   */
  public boolean hasGoal(Goal goal) {
    return this.goals.hasGoal(goal);
  }

  /**
   * Check if this GoalMap contains a specific Goal (by name).
   * 
   * @param g Goal to check for.
   * @return True if Agent has goal, false if not.
   */
  public boolean hasGoal(String goalName) {
    return this.goals.hasGoal(goalName);
  }

  /**
   * If this agent has a goal named goalName, this method returns that goal.
   * 
   * @param goalName The name of the goal to be found.
   * @return the reference to the goal.
   */
  public Goal getGoalByName(String goalName) {
    return this.goals.getGoalByName(goalName);
  }

  /**
   * Sets the gain for this agent.
   * 
   * @param gain The gain value [0 and 20].
   */
  public void setGain(double gain) {

    // Gain has to be between 0 and 20.
    if (gain <= 0 || gain > 20) {
      Gamygdala.debug("Error: gain factor for appraisal integration must be between 0 and 20.");
    } else {
      this.gain = gain;
    }
  }

  /**
   * A facilitating method to be able to appraise one event only from the perspective of the current
   * agent (this).
   * Needs an instantiated Gamygdala object (automatic when the agent is registered with
   * Gamygdala.registerAgent(agent) to a Gamygdala instance).
   * 
   * @param belief The belief to be appraised.
   */
  public void appraise(Belief belief) {
    this.gamygdalaInstance.appraise(belief, this);
  }

  /**
   * Updates the emotional state of this agent using a single emotion.
   * 
   * @param emotion The emotion with which this Agent should be updated.
   */
  public void updateEmotionalState(Emotion emotion) {
    Emotion temp;
    for (int i = 0; i < this.internalState.size(); i++) {
      temp = this.internalState.get(i);
      if (temp.name == emotion.name) {
        // Appraisals simply add to the old value of the emotion
        // So repeated appraisals without decay will result in the sum of the appraisals over time
        // To decay the emotional state, call .decay(decayFunction), or simply use the facilitating
        // function in Gamygdala setDecay(timeMS).
        temp.intensity += emotion.intensity;

        // Test if this works without updating object, else:
        // this.internalState.set(i, e);
        return;
      }
    }

    // copy on keep, we need to maintain a list of current emotions for the state, not a list
    // references to the appraisal engine
    this.internalState.add(new Emotion(emotion.name, emotion.intensity));
  }

  /**
   * This function returns either the state as is (gain=false) or a state based on gained limiter
   * (limited between 0 and 1), of which the gain can be set by using setGain(gain).
   * A high gain factor works well when appraisals are small and rare, and you want to see the
   * effect of these appraisals
   * A low gain factor (close to 0 but in any case below 1) works well for high frequency and/or
   * large appraisals, so that the effect of these is dampened.
   * 
   * @param useGain Whether to use the gain function or not.
   * @return An array of emotions.
   */
  public ArrayList<Emotion> getEmotionalState(boolean useGain) {

    if (useGain) {
      ArrayList<Emotion> gainState = new ArrayList<Emotion>();
      Emotion emotion;
      for (int i = 0; i < this.internalState.size(); i++) {
        emotion = this.internalState.get(i);

        if (emotion != null) {
          double gainEmo = (this.gain * emotion.intensity) / (this.gain * emotion.intensity + 1);
          gainState.add(new Emotion(emotion.name, gainEmo));
        }
      }

      return gainState;
    }

    return this.internalState;
  }

  /**
   * This function prints to the console either the state as is (gain=false) or a state based on
   * gained limiter (limited between 0 and 1), of which the gain can be set by using setGain(gain).
   * A high gain factor works well when appraisals are small and rare, and you want to see the
   * effect of these appraisals
   * A low gain factor (close to 0 but in any case below 1) works well for high frequency and/or
   * large appraisals, so that the effect of these is dampened.
   * 
   * @param useGain Whether to use the gain function or not.
   */
  public void printEmotionalState(boolean useGain) {
    String output = this.name + " feels ";

    ArrayList<Emotion> emotionalState = this.getEmotionalState(useGain);

    Emotion emotion;
    for (int i = 0; i < emotionalState.size(); i++) {
      emotion = emotionalState.get(i);
      output += emotion.name + ": " + emotion.intensity + ", ";
    }

    System.out.println(output);
  }

  /**
   * Sets the relation this agent has with the agent defined by agentName. If
   * the relation does not exist, it will be created, otherwise it will be
   * updated.
   * 
   * @param agentName The agent who is the target of the relation.
   * @param like The relation (between -1 and 1).
   */
  public void updateRelation(String agentName, double like) {
    if (!this.hasRelationWith(agentName)) {
      // This relation does not exist, just add it.
      this.currentRelations.add(new Relation(agentName, like));
    } else {
      // The relation already exists, update it.
      Relation relation;
      for (int i = 0; i < this.currentRelations.size(); i++) {
        relation = this.currentRelations.get(i);
        if (relation.agentName == agentName) {
          relation.like = like;
        }
      }
    }
  }

  /**
   * Checks if this agent has a relation with the agent defined by agentName.
   * 
   * @param agentName The agent who is the target of the relation.
   * @param True if the relation exists, otherwise false.
   */
  public boolean hasRelationWith(String agentName) {
    return (this.getRelation(agentName) != null);
  }

  /**
   * Returns the relation object this agent has with the agent defined by
   * agentName.
   * 
   * @param agentName The agent who is the target of the relation.
   * @return Relation The relation object or null if non existing.
   */
  public Relation getRelation(String agentName) {
    Relation relation;
    for (int i = 0; i < this.currentRelations.size(); i++) {
      relation = this.currentRelations.get(i);
      if (relation.agentName == agentName) {
        return relation;
      }
    }
    return null;
  }

  /**
   * Prints the relations this agent has with the agent defined by agentName.
   * 
   * @param agentName The agent who is the target of the relation. When omitted, all relations are
   *          printed.
   */
  public void printRelations(String agentName) {
    String output = this.name + " has the following sentiments:\n   ";
    boolean found = false;

    for (int i = 0; i < this.currentRelations.size(); i++) {

      if (agentName == null || this.currentRelations.get(i).agentName == agentName) {
        for (int j = 0; j < this.currentRelations.get(i).emotionList.size(); j++) {
          output += this.currentRelations.get(i).emotionList.get(j).name + "("
              + this.currentRelations.get(i).emotionList.get(j).intensity + ") ";
          found = true;
        }
      }

      output += " for " + this.currentRelations.get(i).agentName;

      if (i < this.currentRelations.size() - 1) {
        output += ", and\n   ";
      }
    }

    if (found) {
      System.out.println(output);
    }
  }

  /**
   * This method decays the emotional state and relations according to the
   * decay factor and function defined in gamygdala. Typically this is called
   * automatically when you use startDecay() in Gamygdala, but you can use it
   * yourself if you want to manage the timing. This function is keeping track
   * of the millis passed since the last call, and will (try to) keep the
   * decay close to the desired decay factor, regardless the time passed So
   * you can call this any time you want (or, e.g., have the game loop call
   * it, or have e.g., Phaser call it in the plugin update, which is default
   * now). Further, if you want to tweak the emotional intensity decay of
   * individual agents, you should tweak the decayFactor per agent not the
   * "frame rate" of the decay (as this doesn't change the rate).
   * 
   * @param gamygdalaInstance A reference to the correct Gamygdala instance that
   *          contains the decayFunction property to be used (so you could use
   *          different gamygdala instances to manage different groups of agents)
   */
  public void decay(Gamygdala gamygdalaInstance) {
    for (int i = 0; i < this.internalState.size(); i++) {
      double newIntensity = gamygdalaInstance.decayFunction.decay(
          this.internalState.get(i).intensity, gamygdalaInstance.getMillisPassed());
      if (newIntensity < 0) {
        this.internalState.remove(i);
      } else {
        this.internalState.get(i).intensity = newIntensity;
      }
    }

    for (int i = 0; i < this.currentRelations.size(); i++) {
      this.currentRelations.get(i).decay(gamygdalaInstance);
    }
  }

  /**
   * Get the Gamygdala instance to which this Agent is linked.
   * 
   * @return Gamygdala object.
   */
  public Gamygdala getGamygdalaInstance() {
    return this.gamygdalaInstance;
  }

  /**
   * Set the Gamygdala instance to which this Agent is linked.
   * 
   * @return Gamygdala object.
   */
  public void setGamygdalaInstance(Gamygdala instance) {
    this.gamygdalaInstance = instance;
  }

  /**
   * String representation of Agent.
   */
  @Override
  public String toString() {
    return "<Agent[" + this.name + "]>";
  }

}
