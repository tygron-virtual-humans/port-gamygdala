var Mapper = function(scenario){
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
    var emotionEngine=new TUDelft.Gamygdala();
    this.emotionEngine = emotionEngine
    var agentSelf = emotionEngine.createAgent('self');
    var agentAffected = emotionEngine.createAgent('affected');
    var agentCausal = emotionEngine.createAgent('causal');
    if (affectedAgent === 'self'){
        emotionEngine.createGoalForAgent('self','goal', utility)
    } else if(affectedAgent === 'other'){
        emotionEngine.createGoalForAgent('affected','goal', utility)
        emotionEngine.createRelation('self', 'affected',relationWithAffected);
    }
    if(objectAttractiveness != 0){
        //Gamygdala has no objects so it can't represent scenarios which have objects in it!
        this.mappable = false;   
    }
    //Now we need to make sure the agents know about the goal and add it to the emotion engine as well.
    if(causalAgent === 'none'){
        this.belief = new TUDelft.Gamygdala.Belief(eventLikelihood, 'none', ['goal'], [congruence], false);
    }
    if (causalAgent === 'self'){
        //If the causal Agent is self AND the affected agent is self... then Gamygdala does not work. It has no case like that.
        if(affectedAgent === 'self'){
            this.mappable = false;
            }else {
                if(standardAdherence > 0 && utility * congruence < 0){
                this.mappable = false;
            }
            if(standardAdherence < 0 && utility * congruence > 0){
                this.mappable = false;   
            }
            this.belief = new TUDelft.Gamygdala.Belief(eventLikelihood, agentSelf.name, ['goal'], [congruence], false);            
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
        this.belief = new TUDelft.Gamygdala.Belief(eventLikelihood, agentCausal.name, ['goal'], [congruence], false);         
    } 
};

Mapper.prototype.getMappable = function(){
    return this.mappable;   
};

Mapper.prototype.evaluateEmotion = function(){
    this.emotionEngine.appraise(this.belief, null);
    //We are ALWAYS interested in the emotions of SELF. No other!!! 
    var agent = this.emotionEngine.getAgentByName('self');
    //console.log(agent);
    var emotionalState = agent.getEmotionalState();
    var emotions = [];
    for(var i = 0; i < emotionalState.length; i++){
        emotions.push(emotionalState[i].name);   
    }
    if(emotions.length === 0){
    }
    var relation;
    if(agent.currentRelations.length > 0){
        if(agent.hasRelationWith('affected')){
            relation = agent.getRelation('affected');
            for(var i = 0; i < relation.emotionList.length;i++){
                emotions.push(relation.emotionList[i].name);   
            }
        }
        if(agent.hasRelationWith('causal')){
            relation = agent.getRelation('causal');
            for(var i = 0; i < relation.emotionList.length;i++){
                emotions.push(relation.emotionList[i].name);   
            }   
        }
    }  
    return emotions;
};

Mapper.prototype.evaluateIntensity = function(){
    this.emotionEngine.appraise(this.belief, null);
    //We are ALWAYS interested in the emotions of SELF. No other!!! 
    var agent = this.emotionEngine.getAgentByName('self');
    //console.log(agent);
    var emotionalState = agent.getEmotionalState();
    for(var i = 0; i < emotionalState.length; i++){
        return emotionalState[i].intensity;   
    }
    var relation;
    if(agent.currentRelations.length > 0){
        if(agent.containsRelation('affected')){
            relation = agent.getRelation('affected');
            for(var i = 0; i < relation.emotionList.length;i++){
                return relation.emotionList[i].intensity;   
            }
        }
        if(agent.containsRelation('causal')){
            relation = agent.getRelation('causal');
            for(var i = 0; i < relation.emotionList.length;i++){
                return relation.emotionList[i].intensity;   
            }   
        }
    }  
    return intensities;
};