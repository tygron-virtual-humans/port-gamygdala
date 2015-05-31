package agent;

import java.util.ArrayList;

import data.Emotion;
import decayfunction.DecayFunction;

/**
 * Describes a Relation between two Agents.
 */
public class Relation {

    public final Agent agent;
    public double like;
    public final ArrayList<Emotion> emotionList;

    /**
     * This is the class that represents a relation one agent has with other
     * agents. It's main role is to store and manage the emotions felt for a
     * target agent (e.g angry at, or pity for). Each agent maintains a list of
     * relations, one relation for each target agent.
     *
     * @param target The agent who is the target of the relation.
     * @param like The relation [-1 and 1].
     */
    public Relation(Agent target, double like) {
        this.agent = target;
        this.like = like;
        this.emotionList = new ArrayList<Emotion>();
    }

    /**
     * Get the target Agent of this relation.
     * 
     * @return Target Agent.
     */
    public Agent getAgent() {
        return agent;
    }

    /**
     * Get the intensity of the relation.
     * 
     * @return Intensity of the relation.
     */
    public double getLike() {
        return like;
    }

    /**
     * Set the intensity of the relation.
     *
     * @param like Intensity of the relation
     */
    public void setLike(double like) {
        this.like = like;
    }

    /**
     * Add an emotion to this relation.
     *
     * @param emotion The emotion to add.
     */
    public void addEmotion(Emotion emotion) {
        boolean added = false;
        for (Emotion temp : this.emotionList) {
            if (temp.getName().equals(emotion.getName())) {
                temp.setIntensity(temp.getIntensity() + emotion.getIntensity());
                // Check if this works just by ref, else:
                // this.emotionList.set(i, e);
                added = true;
            }
        }
        if (!added) {
            // copy on keep, we need to maintain a list of current emotions for
            // the relation, not a list
            // refs to the appraisal engine
            this.emotionList.add(new Emotion(emotion.getName(), emotion.getIntensity()));
        }
    }

    /**
     * Decay all emotions in this relation.
     * @param dfunc The Decay Function used to decay this relation.
     * @param millisPassed The time passed (in milliseconds) since the last
     *            decay.
     */
    public void decay(DecayFunction dfunc, long millisPassed) {
        for (int i = 0; i < this.emotionList.size(); i++) {

            Emotion emotion = this.emotionList.get(i);
            double newIntensity = dfunc.decay(emotion.getIntensity(), millisPassed);

            if (newIntensity < 0) {
                // This emotion has decayed below zero, we need to remove it.
                this.emotionList.remove(i);
            } else {
                // Update intensity
                emotion.setIntensity(newIntensity);
                this.emotionList.set(i, emotion);
            }
        }
    }

    /**
     * Compare two Relation objects.
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Relation)) {
            return false;
        }
        Relation rel = (Relation) obj;
        return rel.agent.equals(this.agent) && Double.compare(this.like, rel.like) == 0
                && rel.emotionList.equals(this.emotionList);
    }

    /**
     * Return the hash code of this Object.
     * @return int hash code
     */
    @Override
    public int hashCode() {
        int result;
        long temp;
        result = agent != null ? agent.hashCode() : 0;
        temp = Double.doubleToLongBits(like);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (emotionList.hashCode());
        return result;
    }

    /**
     * String representation of Relation.
     */
    @Override
    public String toString() {
        return "<Relation[causalAgent=" + this.agent + ", like=" + this.like + "]>";
    }

}
