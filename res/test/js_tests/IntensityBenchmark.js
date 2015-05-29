function intensityBenchmark(){
    //There are totla 13 intensity rules that need to be satisified.
    var evaluatingChecker = false //This variable determines if the test is running on the occChecker or not.
    //These are the 9 variables that need to be set before each test!
    var congruence;
    var utility;
    var affectedAgent;
    var relationWithAffected;
    var eventLikelihood;
    var newEvent;
    var causalAgent;
    var adherence;
    var attractiveness;
    //-------------------------------------------------------------
    var scenario = new OccScenario(congruence,utility,affectedAgent,relationWithAffected,newEvent,eventLikelihood,causalAgent,adherence,attractiveness);
    var testResults = []; //Array to hold the results of each test
    var expectedEmotions; //Expected emotions
    var outputEmotions; //Output emotions
    var mapper; //The mapping to engine specific scenario
    var number = 0; //Rule number
    var numberOfRandoms = 10;
    var intensityTest;
    var results = [];
    
    //Rule number 1
    //We are testing how congruence affects the intensity. This means we will only randomize congruence!
    number++;
    intensityTest = [];
    for(var i = 0; i < numberOfRandoms; i++){
        congruence = Math.random()*2 - 1;
        utility = 0.5;
        affectedAgent = 'other';
        relationWithAffected = 0.5;
        newEvent = true;
        eventLikelihood = 1;
        causalAgent = 'none';
        adherence = 0;
        attractiveness = 0;
        scenario = new OccScenario(congruence,utility,affectedAgent,relationWithAffected,newEvent,eventLikelihood,causalAgent,adherence,attractiveness);
        mapper = new Mapper(scenario);
        intensityTest.push(new IntensityResult(congruence ,mapper.evaluateIntensity()));
    }
    results.push(evaluateIntensity(intensityTest));
    
    //Rule number 2
    //We are testing how utility affects the intensity. This means we will only randomize congruence!
    number++;
    intensityTest = [];
    for(var i = 0; i < numberOfRandoms; i++){
        congruence = 0.5
        utility = Math.random()*2 - 1;
        affectedAgent = 'other';
        relationWithAffected = 0.5;
        newEvent = true;
        eventLikelihood = 1;
        causalAgent = 'none';
        adherence = 0;
        attractiveness = 0;
        scenario = new OccScenario(congruence,utility,affectedAgent,relationWithAffected,newEvent,eventLikelihood,causalAgent,adherence,attractiveness);
        mapper = new Mapper(scenario);
        intensityTest.push(new IntensityResult(utility ,mapper.evaluateIntensity()));
    }
    results.push(evaluateIntensity(intensityTest));
    
    //Rule number 3
    //We are testing how relationship with affected affects the intensity. This means we will only randomize congruence!
    number++;
    intensityTest = [];
    for(var i = 0; i < numberOfRandoms; i++){
        congruence = 0.5
        utility = 0.5
        affectedAgent = 'other';
        relationWithAffected = Math.random()*2 - 1;
        newEvent = true;
        eventLikelihood = 1;
        causalAgent = 'none';
        adherence = 0;
        attractiveness = 0;
        scenario = new OccScenario(congruence,utility,affectedAgent,relationWithAffected,newEvent,eventLikelihood,causalAgent,adherence,attractiveness);
        mapper = new Mapper(scenario);
        intensityTest.push(new IntensityResult(relationWithAffected ,mapper.evaluateIntensity()));
    }
    results.push(evaluateIntensity(intensityTest));
    
    //Rule number 4
    //We are testing how event likelihood affects the intensity. This means we will only randomize congruence!
    number++;
    intensityTest = [];
    for(var i = 0; i < numberOfRandoms; i++){
        congruence = 0.5
        utility = 0.5
        affectedAgent = 'other';
        relationWithAffected = 0.5;
        newEvent = true;
        eventLikelihood = Math.random()*1;
        causalAgent = 'none';
        adherence = 0;
        attractiveness = 0;
        scenario = new OccScenario(congruence,utility,affectedAgent,relationWithAffected,newEvent,eventLikelihood,causalAgent,adherence,attractiveness);
        mapper = new Mapper(scenario);
        intensityTest.push(new IntensityResult(eventLikelihood ,mapper.evaluateIntensity()));
    }
    results.push(evaluateIntensity(intensityTest));
    /*
    //Rule number 5
    //We are testing how standard adherence affects the intensity. This means we will only randomize congruence!
    number++;
    intensityTest = [];
    for(var i = 0; i < numberOfRandoms; i++){
        congruence = 0;
        utility = 0;
        affectedAgent = 'self'; //This doesn't matter
        relationWithAffected = 0;
        newEvent = true;
        eventLikelihood = 1;
        causalAgent = 'self';
        adherence = Math.random()*2 -1;
        attractiveness = 0;
        scenario = new OccScenario(congruence,utility,affectedAgent,relationWithAffected,newEvent,eventLikelihood,causalAgent,adherence,attractiveness);
        mapper = new Mapper(scenario);
        intensityTest.push(new IntensityResult(adherence ,mapper.evaluateIntensity()));
    }
    results.push(evaluateIntensity(intensityTest));
    
    
    
    //Rule number 6
    //We are testing how object attractiveness affects the intensity. This means we will only randomize congruence!
    number++;
    intensityTest = [];
    for(var i = 0; i < numberOfRandoms; i++){
        congruence = 0;
        utility = 0;
        affectedAgent = 'self'; //This doesn't matter
        relationWithAffected = 0;
        newEvent = true;
        eventLikelihood = 1;
        causalAgent = 'none';
        adherence = 0;
        attractiveness = Math.random()*2 - 1;
        scenario = new OccScenario(congruence,utility,affectedAgent,relationWithAffected,newEvent,eventLikelihood,causalAgent,adherence,attractiveness);
        mapper = new Mapper(scenario);
        intensityTest.push(new IntensityResult(attractiveness ,mapper.evaluateIntensity()));
    }
    results.push(evaluateIntensity(intensityTest));
    */
    
    
    //Rule number 7
    //We are testing how event congruence*utility affects the intensity. This means we will only randomize congruence!
    number++;
    intensityTest = [];
    for(var i = 0; i < numberOfRandoms; i++){
        congruence = Math.random()*2 - 1;
        utility = Math.random()*2 - 1;
        affectedAgent = 'other';
        relationWithAffected = 0.5;
        newEvent = true;
        eventLikelihood = 1;
        causalAgent = 'none';
        adherence = 0;
        attractiveness = 0;
        scenario = new OccScenario(congruence,utility,affectedAgent,relationWithAffected,newEvent,eventLikelihood,causalAgent,adherence,attractiveness);
        mapper = new Mapper(scenario);
        intensityTest.push(new IntensityResult(congruence*utility ,mapper.evaluateIntensity()));
    }
    results.push(evaluateIntensity(intensityTest));
    
    //Rule number 8
    //We are testing how event congruence*relationship affects the intensity. This means we will only randomize congruence!
    number++;
    intensityTest = [];
    for(var i = 0; i < numberOfRandoms; i++){
        congruence = Math.random()*2 - 1;
        utility = 0.5;
        affectedAgent = 'other';
        relationWithAffected = Math.random()*2 - 1;
        newEvent = true;
        eventLikelihood = 1;
        causalAgent = 'none';
        adherence = 0;
        attractiveness = 0;
        scenario = new OccScenario(congruence,utility,affectedAgent,relationWithAffected,newEvent,eventLikelihood,causalAgent,adherence,attractiveness);
        mapper = new Mapper(scenario);
        intensityTest.push(new IntensityResult(congruence*relationWithAffected ,mapper.evaluateIntensity()));
    }
    results.push(evaluateIntensity(intensityTest));
    
    //Rule number 9
    //We are testing how event utility*relationship affects the intensity. This means we will only randomize congruence!
    number++;
    intensityTest = [];
    for(var i = 0; i < numberOfRandoms; i++){
        congruence = 0.5;
        utility = Math.random()*2 - 1;
        affectedAgent = 'other';
        relationWithAffected = Math.random()*2 - 1;
        newEvent = true;
        eventLikelihood = 1;
        causalAgent = 'none';
        adherence = 0;
        attractiveness = 0;
        scenario = new OccScenario(congruence,utility,affectedAgent,relationWithAffected,newEvent,eventLikelihood,causalAgent,adherence,attractiveness);
        mapper = new Mapper(scenario);
        intensityTest.push(new IntensityResult(utility*relationWithAffected ,mapper.evaluateIntensity()));
    }
    results.push(evaluateIntensity(intensityTest));
    
    //Rule number 10
    //We are testing how event congruence*utility*relationship affects the intensity. This means we will only randomize congruence!
    number++;
    intensityTest = [];
    for(var i = 0; i < numberOfRandoms; i++){
        congruence = Math.random()*2 -1;
        utility = Math.random()*2 - 1;
        affectedAgent = 'other';
        relationWithAffected = Math.random()*2 - 1;
        newEvent = true;
        eventLikelihood = 1;
        causalAgent = 'none';
        adherence = 0;
        attractiveness = 0;
        scenario = new OccScenario(congruence,utility,affectedAgent,relationWithAffected,newEvent,eventLikelihood,causalAgent,adherence,attractiveness);
        mapper = new Mapper(scenario);
        intensityTest.push(new IntensityResult(congruence*utility*relationWithAffected ,mapper.evaluateIntensity()));
    }
    results.push(evaluateIntensity(intensityTest));
    
    
    //Rule number 11
    //We are testing how event congruence*eventLikelihood affects the intensity. This means we will only randomize congruence!
    number++;
    intensityTest = [];
    for(var i = 0; i < numberOfRandoms; i++){
        congruence = Math.random()*2 -1;
        utility = 0.5;
        affectedAgent = 'other';
        relationWithAffected = 0.5;
        newEvent = true;
        eventLikelihood = Math.random();
        causalAgent = 'none';
        adherence = 0;
        attractiveness = 0;
        scenario = new OccScenario(congruence,utility,affectedAgent,relationWithAffected,newEvent,eventLikelihood,causalAgent,adherence,attractiveness);
        mapper = new Mapper(scenario);
        intensityTest.push(new IntensityResult(congruence*eventLikelihood ,mapper.evaluateIntensity()));
    }
    results.push(evaluateIntensity(intensityTest));
    
    //Rule number 12
    //We are testing how event utility*eventLikelihood affects the intensity. This means we will only randomize congruence!
    number++;
    intensityTest = [];
    for(var i = 0; i < numberOfRandoms; i++){
        congruence = 0.5;
        utility = Math.random()*2 -1;
        affectedAgent = 'other';
        relationWithAffected = 0.5;
        newEvent = true;
        eventLikelihood = Math.random();
        causalAgent = 'none';
        adherence = 0;
        attractiveness = 0;
        scenario = new OccScenario(congruence,utility,affectedAgent,relationWithAffected,newEvent,eventLikelihood,causalAgent,adherence,attractiveness);
        mapper = new Mapper(scenario);
        intensityTest.push(new IntensityResult(utility*eventLikelihood ,mapper.evaluateIntensity()));
    }
    results.push(evaluateIntensity(intensityTest));
    
    //Rule number 13
    //We are testing how event congruence*utility*eventLikelihood affects the intensity. This means we will only randomize congruence!
    number++;
    intensityTest = [];
    for(var i = 0; i < numberOfRandoms; i++){
        congruence = Math.random()*2 -1;
        utility = Math.random()*2 -1;
        affectedAgent = 'other';
        relationWithAffected = 0.5;
        newEvent = true;
        eventLikelihood = Math.random();
        causalAgent = 'none';
        adherence = 0;
        attractiveness = 0;
        scenario = new OccScenario(congruence,utility,affectedAgent,relationWithAffected,newEvent,eventLikelihood,causalAgent,adherence,attractiveness);
        mapper = new Mapper(scenario);
        intensityTest.push(new IntensityResult(congruence*utility*eventLikelihood ,mapper.evaluateIntensity()));
    }
    results.push(evaluateIntensity(intensityTest));
    
    console.log(results);
}

function evaluateIntensity(intensityResult){
    for(var i = 0; i < intensityResult.length; i++){
        for(var j = 0; j <intensityResult.length; j++){
            if(i != j){
                if(Math.abs(intensityResult[i].inputIntensity) > Math.abs(intensityResult[j].inputIntensity)){
                    if(Math.abs(intensityResult[i].outputIntensity) < Math.abs(intensityResult[j].outputIntensity)){
                        return false;
                    }
                }
                if(Math.abs(intensityResult[i].inputIntensity) < Math.abs(intensityResult[j].inputIntensity)){
                    if(Math.abs(intensityResult[i].outputIntensity) > Math.abs(intensityResult[j].outputIntensity)){
                        return false;
                    }    
                }
                if(Math.abs(intensityResult[i].inputIntensity) === Math.abs(intensityResult[j].inputIntensity)){
                    if(Math.abs(intensityResult[i].outputIntensity) != Math.abs(intensityResult[j].outputIntensity)){
                        return false;
                    }    
                }
                if(Math.abs(intensityResult[i].inputIntensity) === Math.abs(intensityResult[j].inputIntensity)){
                    
                    if(Math.abs(intensityResult[i].outputIntensity) === Math.abs(intensityResult[j].outputIntensity)){
                        //The odds of this happenings is astronomical... if this happens most likely the intensity variable does not affect the result!
                        return false;
                    } 
                }
            }
        }
    }
    return true;
    
}