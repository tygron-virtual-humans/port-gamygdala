var OccScenario = function (congruence, utility, affectedAgent, relationWithAffected, newEvent, eventLikelihood, causalAgent, standardAdherence, objectAttractiveness){
    this.congruence = congruence;
    this.utility = utility;
    this.affectedAgent = affectedAgent;
    this.relationWithAffected = relationWithAffected;
    this.newEvent = newEvent;
    this.eventLikelihood = eventLikelihood;
    this.causalAgent = causalAgent;
    this.standardAdherence = standardAdherence;
    this.objectAttractiveness = objectAttractiveness;
       
};
OccScenario.prototype.getCongruence = function(){
    return this.congruence;  
};

OccScenario.prototype.setCongruence = function(congruence){
    this.congruence = congruence;  
};

OccScenario.prototype.getUtility = function(){
    return this.utility;  
};

OccScenario.prototype.setUtility = function(utility){
    this.utility = utility;  
};

OccScenario.prototype.getAffectedAgent = function(){
    return this.affectedAgent;  
};

OccScenario.prototype.setAffectedAgent = function(agent){
    this.affectedAgent = agent;   
};

OccScenario.prototype.getRelationWithAffected = function(){
    return this.relationWithAffected;  
};

OccScenario.prototype.setRelationWithAffected = function(relation){
    this.relationWithAffected = relation;   
};

OccScenario.prototype.getEventLikelihood = function(){
    return this.eventLikelihood;  
};

OccScenario.prototype.setEventLikelihood = function(eventLikelihood){
    this.eventLikelihood = eventLikelihood;  
};

OccScenario.prototype.getNewEvent = function(){
    return this.newEvent;  
};

OccScenario.prototype.setNewEvent = function(newEvent){
    this.newEvent = newEvent;  
};


OccScenario.prototype.getCausalAgent = function(){
    return this.causalAgent;
};

OccScenario.prototype.setCausalAgent = function(agent){
    this.causalAgent = agent;  
};

OccScenario.prototype.getStandardAdherence = function(){
    return this.standardAdherence;  
};

OccScenario.prototype.setStandardAdherence = function(standardAdherence){
    this.standardAdherence = standardAdherence;   
};

OccScenario.prototype.getObjectAttractiveness = function(){
    return this.objectAttractiveness;  
};

OccScenario.prototype.setObjectAttractiveness = function(objectAttractiveness){
    this.objectAttractiveness = objectAttractiveness;
};

OccScenario.prototype.evaluateScenario = function(){
    //First we evaluate the internal emotion. The internal emotion will be 0 if the event
    //did not affect goal with utilitiy or if the delta likelihood is 0. If positive is
    //null then that means the intensity is zero.
    var outputEmotions = [];
    var positive = null;
    if (this.congruence * this.utility> 0){
        positive = true;   
    } else {
        positive = false;
    }
    var joyAdded = false;
    var distressAdded = false;
    if(this.congruence * this.utility != 0){
        //We evaluate the event consequence branch only if the desirability is not zero!
        if(this.affectedAgent === 'other' && this.eventLikelihood === 1){
            //Other agent than self is affected by the consequences of the event.
            if(this.relationWithAffected != 0){
                //We evaluate the social emotion to affected agent only if the agent has a relationship with him!
                if(this.relationWithAffected > 0){
                    //The agent has a positive relationship with the affected agent
                    if(positive){
                        //The event is positive
                        outputEmotions.push('happy-for');
                    }else {
                        outputEmotions.push('pity');
                    }
                }
                else {
                    //The agent has a negative relationship with the affected agent  
                    if(positive){
                        //The event is positive
                        outputEmotions.push('resentment');
                    }else {
                        outputEmotions.push('gloating');
                    }
                }
            }
        }
        else if(this.affectedAgent === 'self'){
            //Self is affected by the consequences of the event. 
            if(this.eventLikelihood != 0 && this.eventLikelihood != 1){
                //The consequences of the event are probabilistic!
                if(positive){
                    outputEmotions.push('hope');   
                }else {
                    outputEmotions.push('fear');
                }
            }
            if(this.eventLikelihood === 1 && this.newEvent === true){
                //The consequences of the event are real!
                //This is new event so it does not have any prospect related emotions!
                if(positive){
                    outputEmotions.push('joy'); 
                    joyAdded = true;
                }else {
                    outputEmotions.push('distress');
                    distressAdded = true;
                }       
            }
            if(this.eventLikelihood === 1 && this.newEvent === false ){
                //The consequences of the event are real!
                //This is new event so it does have prospect related emotions!
                if(positive){
                    outputEmotions.push('joy');
                    outputEmotions.push('satisfaction'); 
                    joyAdded = true;
                }else {
                    outputEmotions.push('distress');
                    outputEmotions.push('fears-confirmed');
                    distressAdded = true;
                } 
                
            }
            if(this.eventLikelihood === 0 && this.newEvent === false ){
                //The consequences of the event did not happen!
                //This is new event so it does have prospect related emotions!
                if(positive){
                    outputEmotions.push('distress');
                    outputEmotions.push('disappointment');   
                    joyAdded = true;
                }else {
                    outputEmotions.push('joy');
                    outputEmotions.push('relief');
                    distressAdded = true;
                }     
            }
        }
    }
    if(this.causalAgent != 'none'){
        //We evaluate the causal branch only if the event has some causal agent!
        if(this.standardAdherence != 0){
            if(this.causalAgent === 'self'){
                if(this.standardAdherence > 0){
                    outputEmotions.push('pride');
                    if(joyAdded){
                        outputEmotions.push('gratification');   
                    }
                }else {
                    outputEmotions.push('shame');
                    if(distressAdded){
                        outputEmotions.push('remorse');   
                    }
                }
            }
            if(this.causalAgent === 'other'){
                if(this.standardAdherence > 0){
                    outputEmotions.push('admiration');
                    if(joyAdded){
                        outputEmotions.push('gratitude');   
                    }
                }else {
                    outputEmotions.push('reproach');
                    if(distressAdded){
                        outputEmotions.push('anger');   
                    }
                }
            }
        }
    }
    if(outputEmotions.length === 3 && this.newEvent === true){
        //The only way the output emotions are of length 3 is if the combination
        //of consequnce and action added gratification/remorse/admiration/anger.
        //We want to remove the first two emotions from the list!!
        outputEmotions.splice(0,2);
    }
    if(outputEmotions.length === 4 && this.newEvent === false){
        //The only way the output emotions are of length 4 is if the combination
        //of consequnce and action added gratification/remorse/admiration/anger.
        //We also have one of the prospect relevant emotions in there... we want to keep that one.
        //We want to remove the first and third emotion from the list!
        outputEmotions.splice(2,1);
        outputEmotions.splice(0,1);     
    }
    if(this.objectAttractiveness != 0){
        //We evaluate the causal branch only if the object has some appealingness!   
        if(this.objectAttractiveness > 0){
            outputEmotions.push('love');   
        }else {
            outputEmotions.push('hate');   
        }
    }
    return outputEmotions;
};