package agent;

import java.util.ArrayList;
import java.util.List;

import agent.data.Emotion;
import decayfunction.DecayFunction;

/**
 * Describes a Relation between two Agents.
 */
public class Relation {

    /**
     *
     */
    private final Agent agent;

    /**
     *
     */
    private double like;

    /**
     *
     */
    private final List<Emotion> emotions;

    /**
     * This is the class that represents a relation one agent has with other
     * agents. It's main role is to store and manage the emotions felt for a
     * target agent (e.g angry at, or pity for). Each agent maintains a list of
     * relations, one relation for each target agent.
     *
     * @param target The agent who is the target of the relation.
     * @param targetLike The relation [-1 and 1].
     */
    public Relation(Agent target, double targetLike) {
        this.agent = target;
        this.like = targetLike;
        this.emotions = new ArrayList<Emotion>();
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
     * @param targetLike Intensity of the relation
     */
    public void setLike(double targetLike) {
        this.like = targetLike;
    }

    /**
     * Get the ArrayList<Emotion> of the Relation.
     *
     * @return ArrayList<Emotion> of this Relation
     */
    public List<Emotion> getEmotions() {
        return this.emotions;
    }

    /**
     * Add an emotion to this relation.
     *
     * @param emotion The emotion to add.
     */
    public void addEmotion(Emotion emotion) {
        if (emotion != null) {
            boolean added = false;
            for (Emotion temp : this.emotions) {
                if (temp.getName().equals(emotion.getName())) {
                    temp.setIntensity(temp.getIntensity() + emotion.getIntensity());
                    added = true;
                }
            }
            if (!added) {
                this.emotions.add(new Emotion(emotion.getName(), emotion.getIntensity()));
            }
        }
    }

    /**
     * Decay all emotions in this relation.
     * @param decayFunction The Decay Function used to decay this relation.
     * @param millisPassed The time passed (in milliseconds) since the last
     *            decay.
     */
    public void decay(DecayFunction decayFunction, long millisPassed) {
        for (int i = 0; i < this.emotions.size(); i++) {

            Emotion emotion = this.emotions.get(i);
            double newIntensity = decayFunction.decay(emotion.getIntensity(), millisPassed);

            if (newIntensity < 0) {
                this.emotions.remove(i);
            } else {
                emotion.setIntensity(newIntensity);
                this.emotions.set(i, emotion);
            }
        }
    }

    /**
     * Creates a toString of the emotion ArrayList.
     * @return String emotion ArrayList.
     */
    public String getRelationString() {
        StringBuilder output = new StringBuilder();

        for (int j = 0; j < this.emotions.size(); j++) {
            output.append(this.emotions.get(j).getName())
                    .append("(")
                    .append(this.emotions.get(j).getIntensity())
                    .append(")");

            if (j < this.emotions.size() - 1) {
                output.append(", and ");
            }
        }
        return output.toString();
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
                && rel.emotions.equals(this.emotions);
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
        result = 31 * result + (emotions.hashCode());
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
