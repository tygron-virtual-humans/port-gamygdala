var Mapper = function(scenario) {
    var congruence = scenario.getCongruence();
    var utility = scenario.getUtility();
    var affectedAgent = scenario.getAffectedAgent();
    var relationWithAffected = scenario.getRelationWithAffected();
    var newEvent = scenario.getNewEvent();
    var eventLikelihood = scenario.getEventLikelihood();
    var causalAgent = scenario.getCausalAgent();
    var standardAdherence = scenario.getStandardAdherence();
    var objectAttractiveness = scenario.getObjectAttractiveness();

    //Affected agent can either be 'self' or 'other.
    //Causal agent is either 'none', 'self' or 'other'.
    //Every goal starts with 0.5 so every belief that has likelihood 1.0 and congruence > 0 will result in positive deltalikelihood.
    //If likelihood is 0 that means the congruence should be -1. If likelihood is 0.5 that means congruence will be same as deltaLikelihood.
    //      and if likelihood is 1 that means the congruence is 1.
    //relation with affected is only needed in cases when affected is NOT self. Then we just create relationship and put the value in it.
    this.mappable = true;
    var EngineClass = Java.type("gamygdala.Engine");
    EngineClass.getInstance();
    this.emotionEngine = EngineClass.resetEngine();
    this.BeliefClass = Java.type("data.Belief");
    this.ArrayListClass = Java.type('java.util.ArrayList');
    this.agentSelf = this.emotionEngine.createAgent('self');
    this.agentAffected = this.emotionEngine.createAgent('affected');
    this.agentCausal = this.emotionEngine.createAgent('causal');
    this.agentNone = this.emotionEngine.createAgent('none');

    if (affectedAgent === 'self') {
        this.goal = this.emotionEngine.createGoalForAgent(this.agentSelf, 'goal', utility, false)
    } else if (affectedAgent === 'other') {
        this.goal = this.emotionEngine.createGoalForAgent(this.agentAffected, 'goal', utility, false);
        this.emotionEngine.createRelation(this.agentSelf, this.agentAffected, relationWithAffected);
    }

    if(objectAttractiveness != 0){
        //Gamygdala has no objects so it can't represent scenarios which have objects in it!
        this.mappable = false;
    }

    //Now we need to make sure the agents know about the goal and add it to the emotion engine as well.
    if(causalAgent === 'none'){
        var goals = new this.ArrayListClass();
        var congruences = new this.ArrayListClass();

        goals.add(this.goal);
        congruences.add(congruence);

        this.belief = new this.BeliefClass(eventLikelihood, this.agentNone, goals, congruences, false);
    }

    if (causalAgent === 'self'){
        //If the causal Agent is self AND the affected agent is self... then Gamygdala does not work. It has no case like that.
        if(affectedAgent === 'self'){
            this.mappable = false;
        } else {
            if(standardAdherence > 0 && utility * congruence < 0) {
            this.mappable = false;
        }
        if (standardAdherence < 0 && utility * congruence > 0) {
            this.mappable = false;
        }

        var goals = new this.ArrayListClass();
        var congruences = new this.ArrayListClass();

        goals.add(this.goal);
        congruences.add(congruence);

        this.belief = new this.BeliefClass(eventLikelihood, this.agentSelf, goals, congruences, false);
        }
    }

    if (causalAgent === 'other'){
        //Now here we have a mismatch with the OCC model. If praiseworhiness does not have the same sign as utilitiy * deltaLikelihood then we are in a nope situation.
        if(standardAdherence > 0 && (utility * congruence < 0 || utility * congruence === 0)){
            this.mappable = false;
        }
        if(standardAdherence < 0 && (utility * congruence > 0 || utility * congruence === 0)){
            this.mappable = false;
        }

        var goals = new this.ArrayListClass();
        var congruences = new this.ArrayListClass();

        goals.add(this.goal);
        congruences.add(congruence);

        this.belief = new this.BeliefClass(eventLikelihood, this.agentCausal, goals, congruences, false);
    }
};

Mapper.prototype.getMappable = function(){
    return this.mappable;
};

Mapper.prototype.evaluateEmotion = function(){
    this.emotionEngine.appraise(this.belief);
    //We are ALWAYS interested in the emotions of SELF. No other!!! 
    var agent = this.agentSelf;
    //console.log(agent);
    var emotionalState = agent.getEmotionalState(false);
    var emotions = [];
    for(var i = 0; i < emotionalState.size(); i++){
        emotions.push(emotionalState.get(i).name);
    }
    if(emotions.length === 0){
    }
    var relation;
    if(agent.getCurrentRelations().size() > 0){
        if(agent.hasRelationWith(this.agentAffected)){
            relation = agent.getRelation(this.agentAffected);
            for(var i = 0; i < relation.getEmotions().size();i++){
                emotions.push(relation.getEmotions().get(i).name);
            }
        }
        if(agent.hasRelationWith(this.agentCausal)){
            relation = agent.getRelation(this.agentCausal);
            for(var i = 0; i < relation.getEmotions().size();i++){
                emotions.push(relation.getEmotions().get(i).name);
            }   
        }
    }  
    return emotions;
};

Mapper.prototype.evaluateIntensity = function(){
    this.emotionEngine.appraise(this.belief);
    //We are ALWAYS interested in the emotions of SELF. No other!!! 
    var agent = this.agentSelf;
    //console.log(agent);
    var emotionalState = agent.getEmotionalState(false);
    for(var i = 0; i < emotionalState.size(); i++){
        return emotionalState.get(i).intensity;
    }
    var relation;
    if(agent.currentRelations.length > 0){
        if(agent.hasRelationWith(this.agentAffected)) {
            relation = agent.getRelation(this.agentAffected);
            for(var i = 0; i < relation.emotionList.size();i++){
                return relation.emotionList.get(i).intensity;
            }
        }
        if(agent.hasRelationWith(this.agentCausal)) {
            relation = agent.getRelation(this.agentCausal);
            for(var i = 0; i < relation.emotionList.size();i++){
                return relation.emotionList.get(i).intensity;
            }   
        }
    }  
    return intensities;
};