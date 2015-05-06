package agent;

import java.util.ArrayList;

public class AgentRelations extends ArrayList<Relation> {

  /**
   * ID for serialization.
   */
  private static final long serialVersionUID = 5959244595675904659L;

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
      add(new Relation(agentName, like));
    } else {
      // The relation already exists, update it.
      Relation relation;
      for (int i = 0; i < size(); i++) {
        relation = get(i);
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
    for (int i = 0; i < size(); i++) {
      relation = get(i);
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
  public String printRelations(String agentName) {
    boolean found = false;
    String output = "";

    for (int i = 0; i < size(); i++) {

      if (agentName == null || get(i).agentName == agentName) {
        for (int j = 0; j < get(i).emotionList.size(); j++) {
          output += get(i).emotionList.get(j).name + "(" + get(i).emotionList.get(j).intensity
              + ") ";
          found = true;
        }
      }

      output += " for " + get(i).agentName;

      if (i < size() - 1) {
        output += ", and\n   ";
      }
    }

    if (found) {
      return output;
    }

    return "";
  }

}
