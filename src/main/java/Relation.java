import java.util.ArrayList;

public class Relation {

  public String agentName;
  public double like;
  public ArrayList<Emotion> emotionList;

  /**
   * This is the class that represents a relation one agent has with other agents.
   * It's main role is to store and manage the emotions felt for a target agent (e.g angry at, or
   * pity for).
   * Each agent maintains a list of relations, one relation for each target agent.
   * 
   * @param targetName The agent who is the target of the relation.
   * @param like The relation [-1 and 1].
   */
  public Relation(String targetName, double like) {
    this.agentName = targetName;
    this.like = like;
    this.emotionList = new ArrayList<Emotion>();
  }

  /**
   * Add an emotion to this relation.
   * 
   * @param emotion The emotion to add.
   */
  public void addEmotion(Emotion emotion) {
    boolean added = false;
    for (int i = 0; i < this.emotionList.size(); i++) {

      Emotion temp;
      temp = this.emotionList.get(i);

      if (temp.name == emotion.name) {
        temp.intensity += emotion.intensity;
        // Check if this works just by ref, else:
        // this.emotionList.set(i, e);
        added = true;
      }
    }
    if (added == false) {
      // copy on keep, we need to maintain a list of current emotions for the relation, not a list
      // refs to the appraisal engine
      this.emotionList.add(new Emotion(emotion.name, emotion.intensity));
    }
  }

  /**
   * Decay all emotions in this relation.
   * 
   * @param gamygdalaInstance The Gamygdala instance.
   */
  public void decay(Gamygdala gamygdalaInstance) {
    for (int i = 0; i < this.emotionList.size(); i++) {

      Emotion emotion = this.emotionList.get(i);

      double newIntensity = gamygdalaInstance.decayFunction.decay(emotion.intensity,
          gamygdalaInstance.getMillisPassed());

      if (newIntensity < 0) {
        // This emotion has decayed below zero, we need to remove it.
        this.emotionList.remove(i);
      } else {

        emotion.intensity = newIntensity;
        // Check if this works just by ref, else:
        // this.emotionList.set(i, e);
      }
    }
  }

  /**
   * Compare two Relation objects.
   */
  @Override
  public boolean equals(Object obj) {

    if (obj instanceof Relation) {

      Relation rel = (Relation) obj;

      return rel.agentName.equals(this.agentName) && Double.compare(this.like, rel.like) == 0
          && rel.emotionList.equals(this.emotionList);

    }

    return false;

  }

}
