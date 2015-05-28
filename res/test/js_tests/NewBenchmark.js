function benchmark(){
    //There are totla 87 test cases. Those will be run one by one!
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
    var number = 0; //Test number
    
    //Test Case 1
    number++;
    congruence = 0.5;
    utility = 0.5;
    affectedAgent = 'other';
    relationWithAffected = 0.5;
    newEvent = true;
    eventLikelihood = 1;
    causalAgent = 'none';
    adherence = 0;
    attractiveness = 0;
    expectedEmotions = ['happy-for'];
    scenario = new OccScenario(congruence,utility,affectedAgent,relationWithAffected,newEvent,eventLikelihood,causalAgent,adherence,attractiveness);
    if(evaluatingChecker){
        outputEmotions = scenario.evaluateScenario();
        testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
    }else {
        mapper = new Mapper(scenario);
        if(mapper.getMappable() === true){
            outputEmotions = mapper.evaluateEmotion();
            testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
        }else {
            testResults.push(new TestResult(number, expectedEmotions, null, 'unmappable'));   
        }
    }
    
    //Test Case 2
    number++;
    congruence = -0.5;
    utility = -0.5;
    affectedAgent = 'other';
    relationWithAffected = 0.5;
    newEvent = true;
    eventLikelihood = 1;
    causalAgent = 'none';
    adherence = 0;
    attractiveness = 0;
    expectedEmotions = ['happy-for'];
    scenario = new OccScenario(congruence,utility,affectedAgent,relationWithAffected,newEvent,eventLikelihood,causalAgent,adherence,attractiveness);
    if(evaluatingChecker){
        outputEmotions = scenario.evaluateScenario();
        testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
    }else {
        mapper = new Mapper(scenario);
        if(mapper.getMappable() === true){
            outputEmotions = mapper.evaluateEmotion();
            testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
        }else {
            testResults.push(new TestResult(number, expectedEmotions, null, 'unmappable'));   
        }
    }
    
    //Test Case 3
    number++;
    congruence = -0.5;
    utility = 0.5;
    affectedAgent = 'other';
    relationWithAffected = 0.5;
    newEvent = true;
    eventLikelihood = 1;
    causalAgent = 'none';
    adherence = 0;
    attractiveness = 0;
    expectedEmotions = ['pity'];
    scenario = new OccScenario(congruence,utility,affectedAgent,relationWithAffected,newEvent,eventLikelihood,causalAgent,adherence,attractiveness);
    if(evaluatingChecker){
        outputEmotions = scenario.evaluateScenario();
        testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
    }else {
        mapper = new Mapper(scenario);
        if(mapper.getMappable() === true){
            outputEmotions = mapper.evaluateEmotion();
            testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
        }else {
            testResults.push(new TestResult(number, expectedEmotions, null, 'unmappable'));   
        }
    }
    
    //Test Case 4
    number++;
    congruence = 0.5;
    utility = -0.5;
    affectedAgent = 'other';
    relationWithAffected = 0.5;
    newEvent = true;
    eventLikelihood = 1;
    causalAgent = 'none';
    adherence = 0;
    attractiveness = 0;
    expectedEmotions = ['pity'];
    scenario = new OccScenario(congruence,utility,affectedAgent,relationWithAffected,newEvent,eventLikelihood,causalAgent,adherence,attractiveness);
    if(evaluatingChecker){
        outputEmotions = scenario.evaluateScenario();
        testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
    }else {
        mapper = new Mapper(scenario);
        if(mapper.getMappable() === true){
            outputEmotions = mapper.evaluateEmotion();
            testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
        }else {
            testResults.push(new TestResult(number, expectedEmotions, null, 'unmappable'));   
        }
    }
    
    //Test Case 5
    number++;
    congruence = 0.5;
    utility = 0.5;
    affectedAgent = 'other';
    relationWithAffected = -0.5;
    newEvent = true;
    eventLikelihood = 1;
    causalAgent = 'none';
    adherence = 0;
    attractiveness = 0;
    expectedEmotions = ['resentment'];
    scenario = new OccScenario(congruence,utility,affectedAgent,relationWithAffected,newEvent,eventLikelihood,causalAgent,adherence,attractiveness);
    if(evaluatingChecker){
        outputEmotions = scenario.evaluateScenario();
        testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
    }else {
        mapper = new Mapper(scenario);
        if(mapper.getMappable() === true){
            outputEmotions = mapper.evaluateEmotion();
            testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
        }else {
            testResults.push(new TestResult(number, expectedEmotions, null, 'unmappable'));   
        }
    }

    //Test Case 6
    number++;
    congruence = -0.5;
    utility = -0.5;
    affectedAgent = 'other';
    relationWithAffected = -0.5;
    newEvent = true;
    eventLikelihood = 1;
    causalAgent = 'none';
    adherence = 0;
    attractiveness = 0;
    expectedEmotions = ['resentment'];
    scenario = new OccScenario(congruence,utility,affectedAgent,relationWithAffected,newEvent,eventLikelihood,causalAgent,adherence,attractiveness);
    if(evaluatingChecker){
        outputEmotions = scenario.evaluateScenario();
        testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
    }else {
        mapper = new Mapper(scenario);
        if(mapper.getMappable() === true){
            outputEmotions = mapper.evaluateEmotion();
            testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
        }else {
            testResults.push(new TestResult(number, expectedEmotions, null, 'unmappable'));   
        }
    }
    
    //Test Case 7
    number++;
    congruence = -0.5;
    utility = 0.5;
    affectedAgent = 'other';
    relationWithAffected = -0.5;
    newEvent = true;
    eventLikelihood = 1;
    causalAgent = 'none';
    adherence = 0;
    attractiveness = 0;
    expectedEmotions = ['gloating'];
    scenario = new OccScenario(congruence,utility,affectedAgent,relationWithAffected,newEvent,eventLikelihood,causalAgent,adherence,attractiveness);
    if(evaluatingChecker){
        outputEmotions = scenario.evaluateScenario();
        testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
    }else {
        mapper = new Mapper(scenario);
        if(mapper.getMappable() === true){
            outputEmotions = mapper.evaluateEmotion();
            testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
        }else {
            testResults.push(new TestResult(number, expectedEmotions, null, 'unmappable'));   
        }
    }

    //Test Case 8
    number++;
    congruence = 0.5;
    utility = -0.5;
    affectedAgent = 'other';
    relationWithAffected = -0.5;
    newEvent = true;
    eventLikelihood = 1;
    causalAgent = 'none';
    adherence = 0;
    attractiveness = 0;
    expectedEmotions = ['gloating'];
    scenario = new OccScenario(congruence,utility,affectedAgent,relationWithAffected,newEvent,eventLikelihood,causalAgent,adherence,attractiveness);
    if(evaluatingChecker){
        outputEmotions = scenario.evaluateScenario();
        testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
    }else {
        mapper = new Mapper(scenario);
        if(mapper.getMappable() === true){
            outputEmotions = mapper.evaluateEmotion();
            testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
        }else {
            testResults.push(new TestResult(number, expectedEmotions, null, 'unmappable'));   
        }
    }
    
    //Test Case 9
    number++;
    congruence = 0.5;
    utility = 0.5;
    affectedAgent = 'other';
    relationWithAffected = 0.5;
    newEvent = true;
    eventLikelihood = 0.5;
    causalAgent = 'none';
    adherence = 0;
    attractiveness = 0;
    expectedEmotions = [];
    scenario = new OccScenario(congruence,utility,affectedAgent,relationWithAffected,newEvent,eventLikelihood,causalAgent,adherence,attractiveness);
    if(evaluatingChecker){
        outputEmotions = scenario.evaluateScenario();
        testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
    }else {
        mapper = new Mapper(scenario);
        if(mapper.getMappable() === true){
            outputEmotions = mapper.evaluateEmotion();
            testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
        }else {
            testResults.push(new TestResult(number, expectedEmotions, null, 'unmappable'));   
        }
    }
    
    //Test Case 10
    number++;
    congruence = 0.5;
    utility = 0.5;
    affectedAgent = 'self';
    relationWithAffected = 0;
    newEvent = true;
    eventLikelihood = 0.5;
    causalAgent = 'none';
    adherence = 0;
    attractiveness = 0;
    expectedEmotions = ['hope'];
    scenario = new OccScenario(congruence,utility,affectedAgent,relationWithAffected,newEvent,eventLikelihood,causalAgent,adherence,attractiveness);
    if(evaluatingChecker){
        outputEmotions = scenario.evaluateScenario();
        testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
    }else {
        mapper = new Mapper(scenario);
        if(mapper.getMappable() === true){
            outputEmotions = mapper.evaluateEmotion();
            testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
        }else {
            testResults.push(new TestResult(number, expectedEmotions, null, 'unmappable'));   
        }
    }
    
    //Test Case 11
    number++;
    congruence = -0.5;
    utility = -0.5;
    affectedAgent = 'self';
    relationWithAffected = 0;
    newEvent = true;
    eventLikelihood = 0.5;
    causalAgent = 'none';
    adherence = 0;
    attractiveness = 0;
    expectedEmotions = ['hope'];
    scenario = new OccScenario(congruence,utility,affectedAgent,relationWithAffected,newEvent,eventLikelihood,causalAgent,adherence,attractiveness);
    if(evaluatingChecker){
        outputEmotions = scenario.evaluateScenario();
        testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
    }else {
        mapper = new Mapper(scenario);
        if(mapper.getMappable() === true){
            outputEmotions = mapper.evaluateEmotion();
            testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
        }else {
            testResults.push(new TestResult(number, expectedEmotions, null, 'unmappable'));   
        }
    }
    
    //Test Case 12
    number++;
    congruence = -0.5;
    utility = 0.5;
    affectedAgent = 'self';
    relationWithAffected = 0;
    newEvent = true;
    eventLikelihood = 0.5;
    causalAgent = 'none';
    adherence = 0;
    attractiveness = 0;
    expectedEmotions = ['fear'];
    scenario = new OccScenario(congruence,utility,affectedAgent,relationWithAffected,newEvent,eventLikelihood,causalAgent,adherence,attractiveness);
    if(evaluatingChecker){
        outputEmotions = scenario.evaluateScenario();
        testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
    }else {
        mapper = new Mapper(scenario);
        if(mapper.getMappable() === true){
            outputEmotions = mapper.evaluateEmotion();
            testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
        }else {
            testResults.push(new TestResult(number, expectedEmotions, null, 'unmappable'));   
        }
    }
    
    //Test Case 13
    number++;
    congruence = 0.5;
    utility = -0.5;
    affectedAgent = 'self';
    relationWithAffected = 0;
    newEvent = true;
    eventLikelihood = 0.5;
    causalAgent = 'none';
    adherence = 0;
    attractiveness = 0;
    expectedEmotions = ['fear'];
    scenario = new OccScenario(congruence,utility,affectedAgent,relationWithAffected,newEvent,eventLikelihood,causalAgent,adherence,attractiveness);
    if(evaluatingChecker){
        outputEmotions = scenario.evaluateScenario();
        testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
    }else {
        mapper = new Mapper(scenario);
        if(mapper.getMappable() === true){
            outputEmotions = mapper.evaluateEmotion();
            testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
        }else {
            testResults.push(new TestResult(number, expectedEmotions, null, 'unmappable'));   
        }
    }
    
    //Test Case 14
    number++;
    congruence = 0.5;
    utility = 0.5;
    affectedAgent = 'self';
    relationWithAffected = 0;
    newEvent = true;
    eventLikelihood = 1;
    causalAgent = 'none';
    adherence = 0;
    attractiveness = 0;
    expectedEmotions = ['joy'];
    scenario = new OccScenario(congruence,utility,affectedAgent,relationWithAffected,newEvent,eventLikelihood,causalAgent,adherence,attractiveness);
    if(evaluatingChecker){
        outputEmotions = scenario.evaluateScenario();
        testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
    }else {
        mapper = new Mapper(scenario);
        if(mapper.getMappable() === true){
            outputEmotions = mapper.evaluateEmotion();
            testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
        }else {
            testResults.push(new TestResult(number, expectedEmotions, null, 'unmappable'));   
        }
    }

    //Test Case 15
    number++;
    congruence = -0.5;
    utility = -0.5;
    affectedAgent = 'self';
    relationWithAffected = 0;
    newEvent = true;
    eventLikelihood = 1;
    causalAgent = 'none';
    adherence = 0;
    attractiveness = 0;
    expectedEmotions = ['joy'];
    scenario = new OccScenario(congruence,utility,affectedAgent,relationWithAffected,newEvent,eventLikelihood,causalAgent,adherence,attractiveness);
    if(evaluatingChecker){
        outputEmotions = scenario.evaluateScenario();
        testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
    }else {
        mapper = new Mapper(scenario);
        if(mapper.getMappable() === true){
            outputEmotions = mapper.evaluateEmotion();
            testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
        }else {
            testResults.push(new TestResult(number, expectedEmotions, null, 'unmappable'));   
        }
    }
    
    //Test Case 16
    number++;
    congruence = -0.5;
    utility = 0.5;
    affectedAgent = 'self';
    relationWithAffected = 0;
    newEvent = true;
    eventLikelihood = 1;
    causalAgent = 'none';
    adherence = 0;
    attractiveness = 0;
    expectedEmotions = ['distress'];
    scenario = new OccScenario(congruence,utility,affectedAgent,relationWithAffected,newEvent,eventLikelihood,causalAgent,adherence,attractiveness);
    if(evaluatingChecker){
        outputEmotions = scenario.evaluateScenario();
        testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
    }else {
        mapper = new Mapper(scenario);
        if(mapper.getMappable() === true){
            outputEmotions = mapper.evaluateEmotion();
            testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
        }else {
            testResults.push(new TestResult(number, expectedEmotions, null, 'unmappable'));   
        }
    }

    //Test Case 17
    number++;
    congruence = 0.5;
    utility = -0.5;
    affectedAgent = 'self';
    relationWithAffected = 0;
    newEvent = true;
    eventLikelihood = 1;
    causalAgent = 'none';
    adherence = 0;
    attractiveness = 0;
    expectedEmotions = ['distress'];
    scenario = new OccScenario(congruence,utility,affectedAgent,relationWithAffected,newEvent,eventLikelihood,causalAgent,adherence,attractiveness);
    if(evaluatingChecker){
        outputEmotions = scenario.evaluateScenario();
        testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
    }else {
        mapper = new Mapper(scenario);
        if(mapper.getMappable() === true){
            outputEmotions = mapper.evaluateEmotion();
            testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
        }else {
            testResults.push(new TestResult(number, expectedEmotions, null, 'unmappable'));   
        }
    }
    
    //Test Case 18
    number++;
    congruence = 0.5;
    utility = 0.5;
    affectedAgent = 'self';
    relationWithAffected = 0;
    newEvent = false;
    eventLikelihood = 1;
    causalAgent = 'none';
    adherence = 0;
    attractiveness = 0;
    expectedEmotions = ['joy', 'satisfaction'];
    scenario = new OccScenario(congruence,utility,affectedAgent,relationWithAffected,newEvent,eventLikelihood,causalAgent,adherence,attractiveness);
    if(evaluatingChecker){
        outputEmotions = scenario.evaluateScenario();
        testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
    }else {
        mapper = new Mapper(scenario);
        if(mapper.getMappable() === true){
            outputEmotions = mapper.evaluateEmotion();
            testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
        }else {
            testResults.push(new TestResult(number, expectedEmotions, null, 'unmappable'));   
        }
    }
    
    //Test Case 19
    number++;
    congruence = -0.5;
    utility = -0.5;
    affectedAgent = 'self';
    relationWithAffected = 0;
    newEvent = false;
    eventLikelihood = 1;
    causalAgent = 'none';
    adherence = 0;
    attractiveness = 0;
    expectedEmotions = ['joy', 'satisfaction'];
    scenario = new OccScenario(congruence,utility,affectedAgent,relationWithAffected,newEvent,eventLikelihood,causalAgent,adherence,attractiveness);
    if(evaluatingChecker){
        outputEmotions = scenario.evaluateScenario();
        testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
    }else {
        mapper = new Mapper(scenario);
        if(mapper.getMappable() === true){
            outputEmotions = mapper.evaluateEmotion();
            testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
        }else {
            testResults.push(new TestResult(number, expectedEmotions, null, 'unmappable'));   
        }
    }

    //Test Case 20
    number++;
    congruence = -0.5;
    utility = 0.5;
    affectedAgent = 'self';
    relationWithAffected = 0;
    newEvent = false;
    eventLikelihood = 0;
    causalAgent = 'none';
    adherence = 0;
    attractiveness = 0;
    expectedEmotions = ['joy', 'relief'];
    scenario = new OccScenario(congruence,utility,affectedAgent,relationWithAffected,newEvent,eventLikelihood,causalAgent,adherence,attractiveness);
    if(evaluatingChecker){
        outputEmotions = scenario.evaluateScenario();
        testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
    }else {
        mapper = new Mapper(scenario);
        if(mapper.getMappable() === true){
            outputEmotions = mapper.evaluateEmotion();
            testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
        }else {
            testResults.push(new TestResult(number, expectedEmotions, null, 'unmappable'));   
        }
    }

    //Test Case 21
    number++;
    congruence = 0.5;
    utility = -0.5;
    affectedAgent = 'self';
    relationWithAffected = 0;
    newEvent = false;
    eventLikelihood = 0;
    causalAgent = 'none';
    adherence = 0;
    attractiveness = 0;
    expectedEmotions = ['joy', 'relief'];
    scenario = new OccScenario(congruence,utility,affectedAgent,relationWithAffected,newEvent,eventLikelihood,causalAgent,adherence,attractiveness);
    if(evaluatingChecker){
        outputEmotions = scenario.evaluateScenario();
        testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
    }else {
        mapper = new Mapper(scenario);
        if(mapper.getMappable() === true){
            outputEmotions = mapper.evaluateEmotion();
            testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
        }else {
            testResults.push(new TestResult(number, expectedEmotions, null, 'unmappable'));   
        }
    }

    //Test Case 22
    number++;
    congruence = 0.5;
    utility = 0.5;
    affectedAgent = 'self';
    relationWithAffected = 0;
    newEvent = false;
    eventLikelihood = 0;
    causalAgent = 'none';
    adherence = 0;
    attractiveness = 0;
    expectedEmotions = ['distress', 'disappointment'];
    scenario = new OccScenario(congruence,utility,affectedAgent,relationWithAffected,newEvent,eventLikelihood,causalAgent,adherence,attractiveness);
    if(evaluatingChecker){
        outputEmotions = scenario.evaluateScenario();
        testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
    }else {
        mapper = new Mapper(scenario);
        if(mapper.getMappable() === true){
            outputEmotions = mapper.evaluateEmotion();
            testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
        }else {
            testResults.push(new TestResult(number, expectedEmotions, null, 'unmappable'));   
        }
    }
    
    //Test Case 23
    number++;
    congruence = -0.5;
    utility = -0.5;
    affectedAgent = 'self';
    relationWithAffected = 0;
    newEvent = false;
    eventLikelihood = 0;
    causalAgent = 'none';
    adherence = 0;
    attractiveness = 0;
    expectedEmotions = ['distress', 'disappointment'];
    scenario = new OccScenario(congruence,utility,affectedAgent,relationWithAffected,newEvent,eventLikelihood,causalAgent,adherence,attractiveness);
    if(evaluatingChecker){
        outputEmotions = scenario.evaluateScenario();
        testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
    }else {
        mapper = new Mapper(scenario);
        if(mapper.getMappable() === true){
            outputEmotions = mapper.evaluateEmotion();
            testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
        }else {
            testResults.push(new TestResult(number, expectedEmotions, null, 'unmappable'));   
        }
    }
    
    //Test Case 24
    number++;
    congruence = -0.5;
    utility = 0.5;
    affectedAgent = 'self';
    relationWithAffected = 0;
    newEvent = false;
    eventLikelihood = 1;
    causalAgent = 'none';
    adherence = 0;
    attractiveness = 0;
    expectedEmotions = ['distress', 'fears-confirmed'];
    scenario = new OccScenario(congruence,utility,affectedAgent,relationWithAffected,newEvent,eventLikelihood,causalAgent,adherence,attractiveness);
    if(evaluatingChecker){
        outputEmotions = scenario.evaluateScenario();
        testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
    }else {
        mapper = new Mapper(scenario);
        if(mapper.getMappable() === true){
            outputEmotions = mapper.evaluateEmotion();
            testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
        }else {
            testResults.push(new TestResult(number, expectedEmotions, null, 'unmappable'));   
        }
    }
    
    //Test Case 25
    number++;
    congruence = 0.5;
    utility = -0.5;
    affectedAgent = 'self';
    relationWithAffected = 0;
    newEvent = false;
    eventLikelihood = 1;
    causalAgent = 'none';
    adherence = 0;
    attractiveness = 0;
    expectedEmotions = ['distress', 'fears-confirmed'];
    scenario = new OccScenario(congruence,utility,affectedAgent,relationWithAffected,newEvent,eventLikelihood,causalAgent,adherence,attractiveness);
    if(evaluatingChecker){
        outputEmotions = scenario.evaluateScenario();
        testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
    }else {
        mapper = new Mapper(scenario);
        if(mapper.getMappable() === true){
            outputEmotions = mapper.evaluateEmotion();
            testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
        }else {
            testResults.push(new TestResult(number, expectedEmotions, null, 'unmappable'));   
        }
    }
    
    //Test Case 26
    number++;
    congruence = 0;
    utility = 0;
    affectedAgent = 'self'; //This doesn't matter
    relationWithAffected = 0;
    newEvent = true;
    eventLikelihood = 1;
    causalAgent = 'self';
    adherence = 0.5;
    attractiveness = 0;
    expectedEmotions = ['pride'];
    scenario = new OccScenario(congruence,utility,affectedAgent,relationWithAffected,newEvent,eventLikelihood,causalAgent,adherence,attractiveness);
    if(evaluatingChecker){
        outputEmotions = scenario.evaluateScenario();
        testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
    }else {
        mapper = new Mapper(scenario);
        if(mapper.getMappable() === true){
            outputEmotions = mapper.evaluateEmotion();
            testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
        }else {
            testResults.push(new TestResult(number, expectedEmotions, null, 'unmappable'));   
        }
    }
    
    //Test Case 27
    number++;
    congruence = 0;
    utility = 0;
    affectedAgent = 'self'; //This doesn't matter
    relationWithAffected = 0;
    newEvent = true;
    eventLikelihood = 1;
    causalAgent = 'self';
    adherence = -0.5;
    attractiveness = 0;
    expectedEmotions = ['shame'];
    scenario = new OccScenario(congruence,utility,affectedAgent,relationWithAffected,newEvent,eventLikelihood,causalAgent,adherence,attractiveness);
    if(evaluatingChecker){
        outputEmotions = scenario.evaluateScenario();
        testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
    }else {
        mapper = new Mapper(scenario);
        if(mapper.getMappable() === true){
            outputEmotions = mapper.evaluateEmotion();
            testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
        }else {
            testResults.push(new TestResult(number, expectedEmotions, null, 'unmappable'));   
        }
    }
    
    //Test Case 28
    number++;
    congruence = 0;
    utility = 0;
    affectedAgent = 'self'; //This doesn't matter
    relationWithAffected = 0;
    newEvent = true;
    eventLikelihood = 1;
    causalAgent = 'other';
    adherence = 0.5;
    attractiveness = 0;
    expectedEmotions = ['admiration'];
    scenario = new OccScenario(congruence,utility,affectedAgent,relationWithAffected,newEvent,eventLikelihood,causalAgent,adherence,attractiveness);
    if(evaluatingChecker){
        outputEmotions = scenario.evaluateScenario();
        testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
    }else {
        mapper = new Mapper(scenario);
        if(mapper.getMappable() === true){
            outputEmotions = mapper.evaluateEmotion();
            testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
        }else {
            testResults.push(new TestResult(number, expectedEmotions, null, 'unmappable'));   
        }
    }

    //Test Case 29
    number++;
    congruence = 0;
    utility = 0;
    affectedAgent = 'self'; //This doesn't matter
    relationWithAffected = 0;
    newEvent = true;
    eventLikelihood = 1;
    causalAgent = 'other';
    adherence = -0.5;
    attractiveness = 0;
    expectedEmotions = ['reproach'];
    scenario = new OccScenario(congruence,utility,affectedAgent,relationWithAffected,newEvent,eventLikelihood,causalAgent,adherence,attractiveness);
    if(evaluatingChecker){
        outputEmotions = scenario.evaluateScenario();
        testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
    }else {
        mapper = new Mapper(scenario);
        if(mapper.getMappable() === true){
            outputEmotions = mapper.evaluateEmotion();
            testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
        }else {
            testResults.push(new TestResult(number, expectedEmotions, null, 'unmappable'));   
        }
    }
    
    //Test Case 30
    number++;
    congruence = 0;
    utility = 0;
    affectedAgent = 'self'; //This doesn't matter
    relationWithAffected = 0;
    newEvent = true;
    eventLikelihood = 1;
    causalAgent = 'none';
    adherence = 0;
    attractiveness = 0.5;
    expectedEmotions = ['love'];
    scenario = new OccScenario(congruence,utility,affectedAgent,relationWithAffected,newEvent,eventLikelihood,causalAgent,adherence,attractiveness);
    if(evaluatingChecker){
        outputEmotions = scenario.evaluateScenario();
        testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
    }else {
        mapper = new Mapper(scenario);
        if(mapper.getMappable() === true){
            outputEmotions = mapper.evaluateEmotion();
            testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
        }else {
            testResults.push(new TestResult(number, expectedEmotions, null, 'unmappable'));   
        }
    }
    
    //Test Case 31
    number++;
    congruence = 0;
    utility = 0;
    affectedAgent = 'self'; //This doesn't matter
    relationWithAffected = 0;
    newEvent = true;
    eventLikelihood = 1;
    causalAgent = 'none';
    adherence = 0;
    attractiveness = -0.5;
    expectedEmotions = ['hate'];
    scenario = new OccScenario(congruence,utility,affectedAgent,relationWithAffected,newEvent,eventLikelihood,causalAgent,adherence,attractiveness);
    if(evaluatingChecker){
        outputEmotions = scenario.evaluateScenario();
        testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
    }else {
        mapper = new Mapper(scenario);
        if(mapper.getMappable() === true){
            outputEmotions = mapper.evaluateEmotion();
            testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
        }else {
            testResults.push(new TestResult(number, expectedEmotions, null, 'unmappable'));   
        }
    }
    
    //Test Case 32
    number++;
    congruence = 0.5;
    utility = 0.5;
    affectedAgent = 'self';
    relationWithAffected = 0;
    newEvent = true;
    eventLikelihood = 1;
    causalAgent = 'self';
    adherence = 0.5;
    attractiveness = 0;
    expectedEmotions = ['gratification'];
    scenario = new OccScenario(congruence,utility,affectedAgent,relationWithAffected,newEvent,eventLikelihood,causalAgent,adherence,attractiveness);
    if(evaluatingChecker){
        outputEmotions = scenario.evaluateScenario();
        testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
    }else {
        mapper = new Mapper(scenario);
        if(mapper.getMappable() === true){
            outputEmotions = mapper.evaluateEmotion();
            testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
        }else {
            testResults.push(new TestResult(number, expectedEmotions, null, 'unmappable'));   
        }
    }
    
    //Test Case 33
    number++;
    congruence = -0.5;
    utility = -0.5;
    affectedAgent = 'self';
    relationWithAffected = 0;
    newEvent = true;
    eventLikelihood = 1;
    causalAgent = 'self';
    adherence = 0.5;
    attractiveness = 0;
    expectedEmotions = ['gratification'];
    scenario = new OccScenario(congruence,utility,affectedAgent,relationWithAffected,newEvent,eventLikelihood,causalAgent,adherence,attractiveness);
    if(evaluatingChecker){
        outputEmotions = scenario.evaluateScenario();
        testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
    }else {
        mapper = new Mapper(scenario);
        if(mapper.getMappable() === true){
            outputEmotions = mapper.evaluateEmotion();
            testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
        }else {
            testResults.push(new TestResult(number, expectedEmotions, null, 'unmappable'));   
        }
    }
    
    //Test Case 34
    number++;
    congruence = -0.5;
    utility = 0.5;
    affectedAgent = 'self';
    relationWithAffected = 0;
    newEvent = true;
    eventLikelihood = 1;
    causalAgent = 'self';
    adherence = -0.5;
    attractiveness = 0;
    expectedEmotions = ['remorse'];
    scenario = new OccScenario(congruence,utility,affectedAgent,relationWithAffected,newEvent,eventLikelihood,causalAgent,adherence,attractiveness);
    if(evaluatingChecker){
        outputEmotions = scenario.evaluateScenario();
        testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
    }else {
        mapper = new Mapper(scenario);
        if(mapper.getMappable() === true){
            outputEmotions = mapper.evaluateEmotion();
            testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
        }else {
            testResults.push(new TestResult(number, expectedEmotions, null, 'unmappable'));   
        }
    }
    
    //Test Case 35
    number++;
    congruence = 0.5;
    utility = -0.5;
    affectedAgent = 'self';
    relationWithAffected = 0;
    newEvent = true;
    eventLikelihood = 1;
    causalAgent = 'self';
    adherence = -0.5;
    attractiveness = 0;
    expectedEmotions = ['remorse'];
    scenario = new OccScenario(congruence,utility,affectedAgent,relationWithAffected,newEvent,eventLikelihood,causalAgent,adherence,attractiveness);
    if(evaluatingChecker){
        outputEmotions = scenario.evaluateScenario();
        testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
    }else {
        mapper = new Mapper(scenario);
        if(mapper.getMappable() === true){
            outputEmotions = mapper.evaluateEmotion();
            testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
        }else {
            testResults.push(new TestResult(number, expectedEmotions, null, 'unmappable'));   
        }
    }
    
    //Test Case 36
    number++;
    congruence = 0.5;
    utility = 0.5;
    affectedAgent = 'self';
    relationWithAffected = 0;
    newEvent = true;
    eventLikelihood = 1;
    causalAgent = 'other';
    adherence = 0.5;
    attractiveness = 0;
    expectedEmotions = ['gratitude'];
    scenario = new OccScenario(congruence,utility,affectedAgent,relationWithAffected,newEvent,eventLikelihood,causalAgent,adherence,attractiveness);
    if(evaluatingChecker){
        outputEmotions = scenario.evaluateScenario();
        testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
    }else {
        mapper = new Mapper(scenario);
        if(mapper.getMappable() === true){
            outputEmotions = mapper.evaluateEmotion();
            testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
        }else {
            testResults.push(new TestResult(number, expectedEmotions, null, 'unmappable'));   
        }
    }
    
    //Test Case 37
    number++;
    congruence = -0.5;
    utility = -0.5;
    affectedAgent = 'self';
    relationWithAffected = 0;
    newEvent = true;
    eventLikelihood = 1;
    causalAgent = 'other';
    adherence = 0.5;
    attractiveness = 0;
    expectedEmotions = ['gratitude'];
    scenario = new OccScenario(congruence,utility,affectedAgent,relationWithAffected,newEvent,eventLikelihood,causalAgent,adherence,attractiveness);
    if(evaluatingChecker){
        outputEmotions = scenario.evaluateScenario();
        testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
    }else {
        mapper = new Mapper(scenario);
        if(mapper.getMappable() === true){
            outputEmotions = mapper.evaluateEmotion();
            testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
        }else {
            testResults.push(new TestResult(number, expectedEmotions, null, 'unmappable'));   
        }
    }

    //Test Case 38
    number++;
    congruence = -0.5;
    utility = 0.5;
    affectedAgent = 'self';
    relationWithAffected = 0;
    newEvent = true;
    eventLikelihood = 1;
    causalAgent = 'other';
    adherence = -0.5;
    attractiveness = 0;
    expectedEmotions = ['anger'];
    scenario = new OccScenario(congruence,utility,affectedAgent,relationWithAffected,newEvent,eventLikelihood,causalAgent,adherence,attractiveness);
    if(evaluatingChecker){
        outputEmotions = scenario.evaluateScenario();
        testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
    }else {
        mapper = new Mapper(scenario);
        if(mapper.getMappable() === true){
            outputEmotions = mapper.evaluateEmotion();
            testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
        }else {
            testResults.push(new TestResult(number, expectedEmotions, null, 'unmappable'));   
        }
    }
    
    //Test Case 39
    number++;
    congruence = 0.5;
    utility = -0.5;
    affectedAgent = 'self';
    relationWithAffected = 0;
    newEvent = true;
    eventLikelihood = 1;
    causalAgent = 'other';
    adherence = -0.5;
    attractiveness = 0;
    expectedEmotions = ['anger'];
    scenario = new OccScenario(congruence,utility,affectedAgent,relationWithAffected,newEvent,eventLikelihood,causalAgent,adherence,attractiveness);
    if(evaluatingChecker){
        outputEmotions = scenario.evaluateScenario();
        testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
    }else {
        mapper = new Mapper(scenario);
        if(mapper.getMappable() === true){
            outputEmotions = mapper.evaluateEmotion();
            testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
        }else {
            testResults.push(new TestResult(number, expectedEmotions, null, 'unmappable'));   
        }
    }
    
    //Test Case 40
    number++;
    congruence = 0.5;
    utility = 0.5;
    affectedAgent = 'self';
    relationWithAffected = 0;
    newEvent = true;
    eventLikelihood = 1;
    causalAgent = 'self';
    adherence = -0.5;
    attractiveness = 0;
    expectedEmotions = ['joy', 'shame'];
    scenario = new OccScenario(congruence,utility,affectedAgent,relationWithAffected,newEvent,eventLikelihood,causalAgent,adherence,attractiveness);
    if(evaluatingChecker){
        outputEmotions = scenario.evaluateScenario();
        testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
    }else {
        mapper = new Mapper(scenario);
        if(mapper.getMappable() === true){
            outputEmotions = mapper.evaluateEmotion();
            testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
        }else {
            testResults.push(new TestResult(number, expectedEmotions, null, 'unmappable'));   
        }
    }
    
    //Test Case 41
    number++;
    congruence = -0.5;
    utility = -0.5;
    affectedAgent = 'self';
    relationWithAffected = 0;
    newEvent = true;
    eventLikelihood = 1;
    causalAgent = 'self';
    adherence = -0.5;
    attractiveness = 0;
    expectedEmotions = ['joy', 'shame'];
    scenario = new OccScenario(congruence,utility,affectedAgent,relationWithAffected,newEvent,eventLikelihood,causalAgent,adherence,attractiveness);
    if(evaluatingChecker){
        outputEmotions = scenario.evaluateScenario();
        testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
    }else {
        mapper = new Mapper(scenario);
        if(mapper.getMappable() === true){
            outputEmotions = mapper.evaluateEmotion();
            testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
        }else {
            testResults.push(new TestResult(number, expectedEmotions, null, 'unmappable'));   
        }
    }

    //Test Case 42
    number++;
    congruence = -0.5;
    utility = 0.5;
    affectedAgent = 'self';
    relationWithAffected = 0;
    newEvent = true;
    eventLikelihood = 1;
    causalAgent = 'self';
    adherence = 0.5;
    attractiveness = 0;
    expectedEmotions = ['distress', 'pride'];
    scenario = new OccScenario(congruence,utility,affectedAgent,relationWithAffected,newEvent,eventLikelihood,causalAgent,adherence,attractiveness);
    if(evaluatingChecker){
        outputEmotions = scenario.evaluateScenario();
        testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
    }else {
        mapper = new Mapper(scenario);
        if(mapper.getMappable() === true){
            outputEmotions = mapper.evaluateEmotion();
            testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
        }else {
            testResults.push(new TestResult(number, expectedEmotions, null, 'unmappable'));   
        }
    }


    //Test Case 43
    number++;
    congruence = 0.5;
    utility = -0.5;
    affectedAgent = 'self';
    relationWithAffected = 0;
    newEvent = true;
    eventLikelihood = 1;
    causalAgent = 'self';
    adherence = 0.5;
    attractiveness = 0;
    expectedEmotions = ['distress', 'pride'];
    scenario = new OccScenario(congruence,utility,affectedAgent,relationWithAffected,newEvent,eventLikelihood,causalAgent,adherence,attractiveness);
    if(evaluatingChecker){
        outputEmotions = scenario.evaluateScenario();
        testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
    }else {
        mapper = new Mapper(scenario);
        if(mapper.getMappable() === true){
            outputEmotions = mapper.evaluateEmotion();
            testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
        }else {
            testResults.push(new TestResult(number, expectedEmotions, null, 'unmappable'));   
        }
    }
    
    //Test Case 44
    number++;
    congruence = 0.5;
    utility = 0.5;
    affectedAgent = 'self';
    relationWithAffected = 0;
    newEvent = true;
    eventLikelihood = 1;
    causalAgent = 'other';
    adherence = -0.5;
    attractiveness = 0;
    expectedEmotions = ['joy', 'reproach'];
    scenario = new OccScenario(congruence,utility,affectedAgent,relationWithAffected,newEvent,eventLikelihood,causalAgent,adherence,attractiveness);
    if(evaluatingChecker){
        outputEmotions = scenario.evaluateScenario();
        testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
    }else {
        mapper = new Mapper(scenario);
        if(mapper.getMappable() === true){
            outputEmotions = mapper.evaluateEmotion();
            testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
        }else {
            testResults.push(new TestResult(number, expectedEmotions, null, 'unmappable'));   
        }
    }
    
    //Test Case 45
    number++;
    congruence = -0.5;
    utility = -0.5;
    affectedAgent = 'self';
    relationWithAffected = 0;
    newEvent = true;
    eventLikelihood = 1;
    causalAgent = 'other';
    adherence = -0.5;
    attractiveness = 0;
    expectedEmotions = ['joy', 'reproach'];
    scenario = new OccScenario(congruence,utility,affectedAgent,relationWithAffected,newEvent,eventLikelihood,causalAgent,adherence,attractiveness);
    if(evaluatingChecker){
        outputEmotions = scenario.evaluateScenario();
        testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
    }else {
        mapper = new Mapper(scenario);
        if(mapper.getMappable() === true){
            outputEmotions = mapper.evaluateEmotion();
            testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
        }else {
            testResults.push(new TestResult(number, expectedEmotions, null, 'unmappable'));   
        }
    }
    
    //Test Case 46
    number++;
    congruence = -0.5;
    utility = 0.5;
    affectedAgent = 'self';
    relationWithAffected = 0;
    newEvent = true;
    eventLikelihood = 1;
    causalAgent = 'other';
    adherence = 0.5;
    attractiveness = 0;
    expectedEmotions = ['distress', 'admiration'];
    scenario = new OccScenario(congruence,utility,affectedAgent,relationWithAffected,newEvent,eventLikelihood,causalAgent,adherence,attractiveness);
    if(evaluatingChecker){
        outputEmotions = scenario.evaluateScenario();
        testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
    }else {
        mapper = new Mapper(scenario);
        if(mapper.getMappable() === true){
            outputEmotions = mapper.evaluateEmotion();
            testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
        }else {
            testResults.push(new TestResult(number, expectedEmotions, null, 'unmappable'));   
        }
    }
    
    //Test Case 47
    number++;
    congruence = 0.5;
    utility = -0.5;
    affectedAgent = 'self';
    relationWithAffected = 0;
    newEvent = true;
    eventLikelihood = 1;
    causalAgent = 'other';
    adherence = 0.5;
    attractiveness = 0;
    expectedEmotions = ['distress', 'admiration'];
    scenario = new OccScenario(congruence,utility,affectedAgent,relationWithAffected,newEvent,eventLikelihood,causalAgent,adherence,attractiveness);
    if(evaluatingChecker){
        outputEmotions = scenario.evaluateScenario();
        testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
    }else {
        mapper = new Mapper(scenario);
        if(mapper.getMappable() === true){
            outputEmotions = mapper.evaluateEmotion();
            testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
        }else {
            testResults.push(new TestResult(number, expectedEmotions, null, 'unmappable'));   
        }
    }
    
    //Test Case 48
    number++;
    congruence = 0.5;
    utility = 0.5;
    affectedAgent = 'other';
    relationWithAffected = 0.5;
    newEvent = true;
    eventLikelihood = 1;
    causalAgent = 'self';
    adherence = 0.5;
    attractiveness = 0;
    expectedEmotions = ['happy-for', 'pride'];
    scenario = new OccScenario(congruence,utility,affectedAgent,relationWithAffected,newEvent,eventLikelihood,causalAgent,adherence,attractiveness);
    if(evaluatingChecker){
        outputEmotions = scenario.evaluateScenario();
        testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
    }else {
        mapper = new Mapper(scenario);
        if(mapper.getMappable() === true){
            outputEmotions = mapper.evaluateEmotion();
            testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
        }else {
            testResults.push(new TestResult(number, expectedEmotions, null, 'unmappable'));   
        }
    }
    
    //Test Case 49
    number++;
    congruence = -0.5;
    utility = -0.5;
    affectedAgent = 'other';
    relationWithAffected = 0.5;
    newEvent = true;
    eventLikelihood = 1;
    causalAgent = 'self';
    adherence = 0.5;
    attractiveness = 0;
    expectedEmotions = ['happy-for', 'pride'];
    scenario = new OccScenario(congruence,utility,affectedAgent,relationWithAffected,newEvent,eventLikelihood,causalAgent,adherence,attractiveness);
    if(evaluatingChecker){
        outputEmotions = scenario.evaluateScenario();
        testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
    }else {
        mapper = new Mapper(scenario);
        if(mapper.getMappable() === true){
            outputEmotions = mapper.evaluateEmotion();
            testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
        }else {
            testResults.push(new TestResult(number, expectedEmotions, null, 'unmappable'));   
        }
    }
    
    //Test Case 50
    number++;
    congruence = 0.5;
    utility = 0.5;
    affectedAgent = 'other';
    relationWithAffected = 0.5;
    newEvent = true;
    eventLikelihood = 1;
    causalAgent = 'self';
    adherence = -0.5;
    attractiveness = 0;
    expectedEmotions = ['happy-for', 'shame'];
    scenario = new OccScenario(congruence,utility,affectedAgent,relationWithAffected,newEvent,eventLikelihood,causalAgent,adherence,attractiveness);
    if(evaluatingChecker){
        outputEmotions = scenario.evaluateScenario();
        testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
    }else {
        mapper = new Mapper(scenario);
        if(mapper.getMappable() === true){
            outputEmotions = mapper.evaluateEmotion();
            testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
        }else {
            testResults.push(new TestResult(number, expectedEmotions, null, 'unmappable'));   
        }
    }
    
    //Test Case 51
    number++;
    congruence = -0.5;
    utility = -0.5;
    affectedAgent = 'other';
    relationWithAffected = 0.5;
    newEvent = true;
    eventLikelihood = 1;
    causalAgent = 'self';
    adherence = -0.5;
    attractiveness = 0;
    expectedEmotions = ['happy-for', 'shame'];
    scenario = new OccScenario(congruence,utility,affectedAgent,relationWithAffected,newEvent,eventLikelihood,causalAgent,adherence,attractiveness);
    if(evaluatingChecker){
        outputEmotions = scenario.evaluateScenario();
        testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
    }else {
        mapper = new Mapper(scenario);
        if(mapper.getMappable() === true){
            outputEmotions = mapper.evaluateEmotion();
            testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
        }else {
            testResults.push(new TestResult(number, expectedEmotions, null, 'unmappable'));   
        }
    }
    
    //Test Case 52
    number++;
    congruence = -0.5;
    utility = 0.5;
    affectedAgent = 'other';
    relationWithAffected = 0.5;
    newEvent = true;
    eventLikelihood = 1;
    causalAgent = 'self';
    adherence = 0.5;
    attractiveness = 0;
    expectedEmotions = ['pity', 'pride'];
    scenario = new OccScenario(congruence,utility,affectedAgent,relationWithAffected,newEvent,eventLikelihood,causalAgent,adherence,attractiveness);
    if(evaluatingChecker){
        outputEmotions = scenario.evaluateScenario();
        testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
    }else {
        mapper = new Mapper(scenario);
        if(mapper.getMappable() === true){
            outputEmotions = mapper.evaluateEmotion();
            testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
        }else {
            testResults.push(new TestResult(number, expectedEmotions, null, 'unmappable'));   
        }
    }
    
    //Test Case 53
    number++;
    congruence = 0.5;
    utility = -0.5;
    affectedAgent = 'other';
    relationWithAffected = 0.5;
    newEvent = true;
    eventLikelihood = 1;
    causalAgent = 'self';
    adherence = 0.5;
    attractiveness = 0;
    expectedEmotions = ['pity', 'pride'];
    scenario = new OccScenario(congruence,utility,affectedAgent,relationWithAffected,newEvent,eventLikelihood,causalAgent,adherence,attractiveness);
    if(evaluatingChecker){
        outputEmotions = scenario.evaluateScenario();
        testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
    }else {
        mapper = new Mapper(scenario);
        if(mapper.getMappable() === true){
            outputEmotions = mapper.evaluateEmotion();
            testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
        }else {
            testResults.push(new TestResult(number, expectedEmotions, null, 'unmappable'));   
        }
    }
    
    //Test Case 54
    number++;
    congruence = -0.5;
    utility = 0.5;
    affectedAgent = 'other';
    relationWithAffected = 0.5;
    newEvent = true;
    eventLikelihood = 1;
    causalAgent = 'self';
    adherence = -0.5;
    attractiveness = 0;
    expectedEmotions = ['pity', 'shame'];
    scenario = new OccScenario(congruence,utility,affectedAgent,relationWithAffected,newEvent,eventLikelihood,causalAgent,adherence,attractiveness);
    if(evaluatingChecker){
        outputEmotions = scenario.evaluateScenario();
        testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
    }else {
        mapper = new Mapper(scenario);
        if(mapper.getMappable() === true){
            outputEmotions = mapper.evaluateEmotion();
            testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
        }else {
            testResults.push(new TestResult(number, expectedEmotions, null, 'unmappable'));   
        }
    }
    
    //Test Case 55
    number++;
    congruence = 0.5;
    utility = -0.5;
    affectedAgent = 'other';
    relationWithAffected = 0.5;
    newEvent = true;
    eventLikelihood = 1;
    causalAgent = 'self';
    adherence = -0.5;
    attractiveness = 0;
    expectedEmotions = ['pity', 'shame'];
    scenario = new OccScenario(congruence,utility,affectedAgent,relationWithAffected,newEvent,eventLikelihood,causalAgent,adherence,attractiveness);
    if(evaluatingChecker){
        outputEmotions = scenario.evaluateScenario();
        testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
    }else {
        mapper = new Mapper(scenario);
        if(mapper.getMappable() === true){
            outputEmotions = mapper.evaluateEmotion();
            testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
        }else {
            testResults.push(new TestResult(number, expectedEmotions, null, 'unmappable'));   
        }
    }
    
    //Test Case 56
    number++;
    congruence = 0.5;
    utility = 0.5;
    affectedAgent = 'other';
    relationWithAffected = -0.5;
    newEvent = true;
    eventLikelihood = 1;
    causalAgent = 'self';
    adherence = 0.5;
    attractiveness = 0;
    expectedEmotions = ['resentment', 'pride'];
    scenario = new OccScenario(congruence,utility,affectedAgent,relationWithAffected,newEvent,eventLikelihood,causalAgent,adherence,attractiveness);
    if(evaluatingChecker){
        outputEmotions = scenario.evaluateScenario();
        testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
    }else {
        mapper = new Mapper(scenario);
        if(mapper.getMappable() === true){
            outputEmotions = mapper.evaluateEmotion();
            testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
        }else {
            testResults.push(new TestResult(number, expectedEmotions, null, 'unmappable'));   
        }
    }
    
    //Test Case 57
    number++;
    congruence = -0.5;
    utility = -0.5;
    affectedAgent = 'other';
    relationWithAffected = -0.5;
    newEvent = true;
    eventLikelihood = 1;
    causalAgent = 'self';
    adherence = 0.5;
    attractiveness = 0;
    expectedEmotions = ['resentment', 'pride'];
    scenario = new OccScenario(congruence,utility,affectedAgent,relationWithAffected,newEvent,eventLikelihood,causalAgent,adherence,attractiveness);
    if(evaluatingChecker){
        outputEmotions = scenario.evaluateScenario();
        testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
    }else {
        mapper = new Mapper(scenario);
        if(mapper.getMappable() === true){
            outputEmotions = mapper.evaluateEmotion();
            testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
        }else {
            testResults.push(new TestResult(number, expectedEmotions, null, 'unmappable'));   
        }
    }
    
    //Test Case 58
    number++;
    congruence = 0.5;
    utility = 0.5;
    affectedAgent = 'other';
    relationWithAffected = -0.5;
    newEvent = true;
    eventLikelihood = 1;
    causalAgent = 'self';
    adherence = -0.5;
    attractiveness = 0;
    expectedEmotions = ['resentment', 'shame'];
    scenario = new OccScenario(congruence,utility,affectedAgent,relationWithAffected,newEvent,eventLikelihood,causalAgent,adherence,attractiveness);
    if(evaluatingChecker){
        outputEmotions = scenario.evaluateScenario();
        testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
    }else {
        mapper = new Mapper(scenario);
        if(mapper.getMappable() === true){
            outputEmotions = mapper.evaluateEmotion();
            testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
        }else {
            testResults.push(new TestResult(number, expectedEmotions, null, 'unmappable'));   
        }
    }

    //Test Case 59
    number++;
    congruence = -0.5;
    utility = -0.5;
    affectedAgent = 'other';
    relationWithAffected = -0.5;
    newEvent = true;
    eventLikelihood = 1;
    causalAgent = 'self';
    adherence = -0.5;
    attractiveness = 0;
    expectedEmotions = ['resentment', 'shame'];
    scenario = new OccScenario(congruence,utility,affectedAgent,relationWithAffected,newEvent,eventLikelihood,causalAgent,adherence,attractiveness);
    if(evaluatingChecker){
        outputEmotions = scenario.evaluateScenario();
        testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
    }else {
        mapper = new Mapper(scenario);
        if(mapper.getMappable() === true){
            outputEmotions = mapper.evaluateEmotion();
            testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
        }else {
            testResults.push(new TestResult(number, expectedEmotions, null, 'unmappable'));   
        }
    }
    
    //Test Case 60
    number++;
    congruence = -0.5;
    utility = 0.5;
    affectedAgent = 'other';
    relationWithAffected = -0.5;
    newEvent = true;
    eventLikelihood = 1;
    causalAgent = 'self';
    adherence = 0.5;
    attractiveness = 0;
    expectedEmotions = ['gloating', 'pride'];
    scenario = new OccScenario(congruence,utility,affectedAgent,relationWithAffected,newEvent,eventLikelihood,causalAgent,adherence,attractiveness);
    if(evaluatingChecker){
        outputEmotions = scenario.evaluateScenario();
        testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
    }else {
        mapper = new Mapper(scenario);
        if(mapper.getMappable() === true){
            outputEmotions = mapper.evaluateEmotion();
            testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
        }else {
            testResults.push(new TestResult(number, expectedEmotions, null, 'unmappable'));   
        }
    }
    
    //Test Case 61
    number++;
    congruence = 0.5;
    utility = -0.5;
    affectedAgent = 'other';
    relationWithAffected = -0.5;
    newEvent = true;
    eventLikelihood = 1;
    causalAgent = 'self';
    adherence = 0.5;
    attractiveness = 0;
    expectedEmotions = ['gloating', 'pride'];
    scenario = new OccScenario(congruence,utility,affectedAgent,relationWithAffected,newEvent,eventLikelihood,causalAgent,adherence,attractiveness);
    if(evaluatingChecker){
        outputEmotions = scenario.evaluateScenario();
        testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
    }else {
        mapper = new Mapper(scenario);
        if(mapper.getMappable() === true){
            outputEmotions = mapper.evaluateEmotion();
            testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
        }else {
            testResults.push(new TestResult(number, expectedEmotions, null, 'unmappable'));   
        }
    }
    
    //Test Case 62
    number++;
    congruence = -0.5;
    utility = 0.5;
    affectedAgent = 'other';
    relationWithAffected = -0.5;
    newEvent = true;
    eventLikelihood = 1;
    causalAgent = 'self';
    adherence = -0.5;
    attractiveness = 0;
    expectedEmotions = ['gloating', 'shame'];
    scenario = new OccScenario(congruence,utility,affectedAgent,relationWithAffected,newEvent,eventLikelihood,causalAgent,adherence,attractiveness);
    if(evaluatingChecker){
        outputEmotions = scenario.evaluateScenario();
        testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
    }else {
        mapper = new Mapper(scenario);
        if(mapper.getMappable() === true){
            outputEmotions = mapper.evaluateEmotion();
            testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
        }else {
            testResults.push(new TestResult(number, expectedEmotions, null, 'unmappable'));   
        }
    }
    
    //Test Case 63
    number++;
    congruence = 0.5;
    utility = -0.5;
    affectedAgent = 'other';
    relationWithAffected = -0.5;
    newEvent = true;
    eventLikelihood = 1;
    causalAgent = 'self';
    adherence = -0.5;
    attractiveness = 0;
    expectedEmotions = ['gloating', 'shame'];
    scenario = new OccScenario(congruence,utility,affectedAgent,relationWithAffected,newEvent,eventLikelihood,causalAgent,adherence,attractiveness);
    if(evaluatingChecker){
        outputEmotions = scenario.evaluateScenario();
        testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
    }else {
        mapper = new Mapper(scenario);
        if(mapper.getMappable() === true){
            outputEmotions = mapper.evaluateEmotion();
            testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
        }else {
            testResults.push(new TestResult(number, expectedEmotions, null, 'unmappable'));   
        }
    }
    
    //Test Case 64
    number++;
    congruence = 0.5;
    utility = 0.5;
    affectedAgent = 'other';
    relationWithAffected = 0.5;
    newEvent = true;
    eventLikelihood = 1;
    causalAgent = 'other';
    adherence = 0.5;
    attractiveness = 0;
    expectedEmotions = ['happy-for', 'admiration'];
    scenario = new OccScenario(congruence,utility,affectedAgent,relationWithAffected,newEvent,eventLikelihood,causalAgent,adherence,attractiveness);
    if(evaluatingChecker){
        outputEmotions = scenario.evaluateScenario();
        testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
    }else {
        mapper = new Mapper(scenario);
        if(mapper.getMappable() === true){
            outputEmotions = mapper.evaluateEmotion();
            testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
        }else {
            testResults.push(new TestResult(number, expectedEmotions, null, 'unmappable'));   
        }
    }
    
    //Test Case 65
    number++;
    congruence = -0.5;
    utility = -0.5;
    affectedAgent = 'other';
    relationWithAffected = 0.5;
    newEvent = true;
    eventLikelihood = 1;
    causalAgent = 'other';
    adherence = 0.5;
    attractiveness = 0;
    expectedEmotions = ['happy-for', 'admiration'];
    scenario = new OccScenario(congruence,utility,affectedAgent,relationWithAffected,newEvent,eventLikelihood,causalAgent,adherence,attractiveness);
    if(evaluatingChecker){
        outputEmotions = scenario.evaluateScenario();
        testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
    }else {
        mapper = new Mapper(scenario);
        if(mapper.getMappable() === true){
            outputEmotions = mapper.evaluateEmotion();
            testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
        }else {
            testResults.push(new TestResult(number, expectedEmotions, null, 'unmappable'));   
        }
    }
    
    //Test Case 66
    number++;
    congruence = 0.5;
    utility = 0.5;
    affectedAgent = 'other';
    relationWithAffected = 0.5;
    newEvent = true;
    eventLikelihood = 1;
    causalAgent = 'other';
    adherence = -0.5;
    attractiveness = 0;
    expectedEmotions = ['happy-for', 'reproach'];
    scenario = new OccScenario(congruence,utility,affectedAgent,relationWithAffected,newEvent,eventLikelihood,causalAgent,adherence,attractiveness);
    if(evaluatingChecker){
        outputEmotions = scenario.evaluateScenario();
        testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
    }else {
        mapper = new Mapper(scenario);
        if(mapper.getMappable() === true){
            outputEmotions = mapper.evaluateEmotion();
            testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
        }else {
            testResults.push(new TestResult(number, expectedEmotions, null, 'unmappable'));   
        }
    }
    
    //Test Case 67
    number++;
    congruence = -0.5;
    utility = -0.5;
    affectedAgent = 'other';
    relationWithAffected = 0.5;
    newEvent = true;
    eventLikelihood = 1;
    causalAgent = 'other';
    adherence = -0.5;
    attractiveness = 0;
    expectedEmotions = ['happy-for', 'reproach'];
    scenario = new OccScenario(congruence,utility,affectedAgent,relationWithAffected,newEvent,eventLikelihood,causalAgent,adherence,attractiveness);
    if(evaluatingChecker){
        outputEmotions = scenario.evaluateScenario();
        testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
    }else {
        mapper = new Mapper(scenario);
        if(mapper.getMappable() === true){
            outputEmotions = mapper.evaluateEmotion();
            testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
        }else {
            testResults.push(new TestResult(number, expectedEmotions, null, 'unmappable'));   
        }
    }
    
    //Test Case 68
    number++;
    congruence = -0.5;
    utility = 0.5;
    affectedAgent = 'other';
    relationWithAffected = 0.5;
    newEvent = true;
    eventLikelihood = 1;
    causalAgent = 'other';
    adherence = 0.5;
    attractiveness = 0;
    expectedEmotions = ['pity', 'admiration'];
    scenario = new OccScenario(congruence,utility,affectedAgent,relationWithAffected,newEvent,eventLikelihood,causalAgent,adherence,attractiveness);
    if(evaluatingChecker){
        outputEmotions = scenario.evaluateScenario();
        testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
    }else {
        mapper = new Mapper(scenario);
        if(mapper.getMappable() === true){
            outputEmotions = mapper.evaluateEmotion();
            testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
        }else {
            testResults.push(new TestResult(number, expectedEmotions, null, 'unmappable'));   
        }
    }
    
    //Test Case 69
    number++;
    congruence = 0.5;
    utility = -0.5;
    affectedAgent = 'other';
    relationWithAffected = 0.5;
    newEvent = true;
    eventLikelihood = 1;
    causalAgent = 'other';
    adherence = 0.5;
    attractiveness = 0;
    expectedEmotions = ['pity', 'admiration'];
    scenario = new OccScenario(congruence,utility,affectedAgent,relationWithAffected,newEvent,eventLikelihood,causalAgent,adherence,attractiveness);
    if(evaluatingChecker){
        outputEmotions = scenario.evaluateScenario();
        testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
    }else {
        mapper = new Mapper(scenario);
        if(mapper.getMappable() === true){
            outputEmotions = mapper.evaluateEmotion();
            testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
        }else {
            testResults.push(new TestResult(number, expectedEmotions, null, 'unmappable'));   
        }
    }
    
   //Test Case 70
    number++;
    congruence = -0.5;
    utility = 0.5;
    affectedAgent = 'other';
    relationWithAffected = 0.5;
    newEvent = true;
    eventLikelihood = 1;
    causalAgent = 'other';
    adherence = -0.5;
    attractiveness = 0;
    expectedEmotions = ['pity', 'reproach'];
    scenario = new OccScenario(congruence,utility,affectedAgent,relationWithAffected,newEvent,eventLikelihood,causalAgent,adherence,attractiveness);
    if(evaluatingChecker){
        outputEmotions = scenario.evaluateScenario();
        testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
    }else {
        mapper = new Mapper(scenario);
        if(mapper.getMappable() === true){
            outputEmotions = mapper.evaluateEmotion();
            testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
        }else {
            testResults.push(new TestResult(number, expectedEmotions, null, 'unmappable'));   
        }
    } 
    
    //Test Case 71
    number++;
    congruence = 0.5;
    utility = -0.5;
    affectedAgent = 'other';
    relationWithAffected = 0.5;
    newEvent = true;
    eventLikelihood = 1;
    causalAgent = 'other';
    adherence = -0.5;
    attractiveness = 0;
    expectedEmotions = ['pity', 'reproach'];
    scenario = new OccScenario(congruence,utility,affectedAgent,relationWithAffected,newEvent,eventLikelihood,causalAgent,adherence,attractiveness);
    if(evaluatingChecker){
        outputEmotions = scenario.evaluateScenario();
        testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
    }else {
        mapper = new Mapper(scenario);
        if(mapper.getMappable() === true){
            outputEmotions = mapper.evaluateEmotion();
            testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
        }else {
            testResults.push(new TestResult(number, expectedEmotions, null, 'unmappable'));   
        }
    }
    
    //Test Case 72
    number++;
    congruence = 0.5;
    utility = 0.5;
    affectedAgent = 'other';
    relationWithAffected = -0.5;
    newEvent = true;
    eventLikelihood = 1;
    causalAgent = 'other';
    adherence = 0.5;
    attractiveness = 0;
    expectedEmotions = ['resentment', 'admiration'];
    scenario = new OccScenario(congruence,utility,affectedAgent,relationWithAffected,newEvent,eventLikelihood,causalAgent,adherence,attractiveness);
    if(evaluatingChecker){
        outputEmotions = scenario.evaluateScenario();
        testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
    }else {
        mapper = new Mapper(scenario);
        if(mapper.getMappable() === true){
            outputEmotions = mapper.evaluateEmotion();
            testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
        }else {
            testResults.push(new TestResult(number, expectedEmotions, null, 'unmappable'));   
        }
    }
    
    //Test Case 73
    number++;
    congruence = -0.5;
    utility = -0.5;
    affectedAgent = 'other';
    relationWithAffected = -0.5;
    newEvent = true;
    eventLikelihood = 1;
    causalAgent = 'other';
    adherence = 0.5;
    attractiveness = 0;
    expectedEmotions = ['resentment', 'admiration'];
    scenario = new OccScenario(congruence,utility,affectedAgent,relationWithAffected,newEvent,eventLikelihood,causalAgent,adherence,attractiveness);
    if(evaluatingChecker){
        outputEmotions = scenario.evaluateScenario();
        testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
    }else {
        mapper = new Mapper(scenario);
        if(mapper.getMappable() === true){
            outputEmotions = mapper.evaluateEmotion();
            testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
        }else {
            testResults.push(new TestResult(number, expectedEmotions, null, 'unmappable'));   
        }
    } 
    
    //Test Case 74
    number++;
    congruence = 0.5;
    utility = 0.5;
    affectedAgent = 'other';
    relationWithAffected = -0.5;
    newEvent = true;
    eventLikelihood = 1;
    causalAgent = 'other';
    adherence = -0.5;
    attractiveness = 0;
    expectedEmotions = ['resentment', 'reproach'];
    scenario = new OccScenario(congruence,utility,affectedAgent,relationWithAffected,newEvent,eventLikelihood,causalAgent,adherence,attractiveness);
    if(evaluatingChecker){
        outputEmotions = scenario.evaluateScenario();
        testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
    }else {
        mapper = new Mapper(scenario);
        if(mapper.getMappable() === true){
            outputEmotions = mapper.evaluateEmotion();
            testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
        }else {
            testResults.push(new TestResult(number, expectedEmotions, null, 'unmappable'));   
        }
    } 
    
    //Test Case 75
    number++;
    congruence = -0.5;
    utility = -0.5;
    affectedAgent = 'other';
    relationWithAffected = -0.5;
    newEvent = true;
    eventLikelihood = 1;
    causalAgent = 'other';
    adherence = -0.5;
    attractiveness = 0;
    expectedEmotions = ['resentment', 'reproach'];
    scenario = new OccScenario(congruence,utility,affectedAgent,relationWithAffected,newEvent,eventLikelihood,causalAgent,adherence,attractiveness);
    if(evaluatingChecker){
        outputEmotions = scenario.evaluateScenario();
        testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
    }else {
        mapper = new Mapper(scenario);
        if(mapper.getMappable() === true){
            outputEmotions = mapper.evaluateEmotion();
            testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
        }else {
            testResults.push(new TestResult(number, expectedEmotions, null, 'unmappable'));   
        }
    } 
    
    //Test Case 76
    number++;
    congruence = -0.5;
    utility = 0.5;
    affectedAgent = 'other';
    relationWithAffected = -0.5;
    newEvent = true;
    eventLikelihood = 1;
    causalAgent = 'other';
    adherence = 0.5;
    attractiveness = 0;
    expectedEmotions = ['gloating', 'admiration'];
    scenario = new OccScenario(congruence,utility,affectedAgent,relationWithAffected,newEvent,eventLikelihood,causalAgent,adherence,attractiveness);
    if(evaluatingChecker){
        outputEmotions = scenario.evaluateScenario();
        testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
    }else {
        mapper = new Mapper(scenario);
        if(mapper.getMappable() === true){
            outputEmotions = mapper.evaluateEmotion();
            testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
        }else {
            testResults.push(new TestResult(number, expectedEmotions, null, 'unmappable'));   
        }
    } 
    
    //Test Case 77
    number++;
    congruence = 0.5;
    utility = -0.5;
    affectedAgent = 'other';
    relationWithAffected = -0.5;
    newEvent = true;
    eventLikelihood = 1;
    causalAgent = 'other';
    adherence = 0.5;
    attractiveness = 0;
    expectedEmotions = ['gloating', 'admiration'];
    scenario = new OccScenario(congruence,utility,affectedAgent,relationWithAffected,newEvent,eventLikelihood,causalAgent,adherence,attractiveness);
    if(evaluatingChecker){
        outputEmotions = scenario.evaluateScenario();
        testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
    }else {
        mapper = new Mapper(scenario);
        if(mapper.getMappable() === true){
            outputEmotions = mapper.evaluateEmotion();
            testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
        }else {
            testResults.push(new TestResult(number, expectedEmotions, null, 'unmappable'));   
        }
    } 

    //Test Case 78
    number++;
    congruence = -0.5;
    utility = 0.5;
    affectedAgent = 'other';
    relationWithAffected = -0.5;
    newEvent = true;
    eventLikelihood = 1;
    causalAgent = 'other';
    adherence = -0.5;
    attractiveness = 0;
    expectedEmotions = ['gloating', 'reproach'];
    scenario = new OccScenario(congruence,utility,affectedAgent,relationWithAffected,newEvent,eventLikelihood,causalAgent,adherence,attractiveness);
    if(evaluatingChecker){
        outputEmotions = scenario.evaluateScenario();
        testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
    }else {
        mapper = new Mapper(scenario);
        if(mapper.getMappable() === true){
            outputEmotions = mapper.evaluateEmotion();
            testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
        }else {
            testResults.push(new TestResult(number, expectedEmotions, null, 'unmappable'));   
        }
    }
    
    //Test Case 79
    number++;
    congruence = 0.5;
    utility = -0.5;
    affectedAgent = 'other';
    relationWithAffected = -0.5;
    newEvent = true;
    eventLikelihood = 1;
    causalAgent = 'other';
    adherence = -0.5;
    attractiveness = 0;
    expectedEmotions = ['gloating', 'reproach'];
    scenario = new OccScenario(congruence,utility,affectedAgent,relationWithAffected,newEvent,eventLikelihood,causalAgent,adherence,attractiveness);
    if(evaluatingChecker){
        outputEmotions = scenario.evaluateScenario();
        testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
    }else {
        mapper = new Mapper(scenario);
        if(mapper.getMappable() === true){
            outputEmotions = mapper.evaluateEmotion();
            testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
        }else {
            testResults.push(new TestResult(number, expectedEmotions, null, 'unmappable'));   
        }
    }
    
    //Test Case 80
    number++;
    congruence = 0;
    utility = 0.5;
    affectedAgent = 'self';
    relationWithAffected = 0;
    newEvent = true;
    eventLikelihood = 1;
    causalAgent = 'none';
    adherence = 0;
    attractiveness = 0;
    expectedEmotions = [];
    scenario = new OccScenario(congruence,utility,affectedAgent,relationWithAffected,newEvent,eventLikelihood,causalAgent,adherence,attractiveness);
    if(evaluatingChecker){
        outputEmotions = scenario.evaluateScenario();
        testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
    }else {
        mapper = new Mapper(scenario);
        if(mapper.getMappable() === true){
            outputEmotions = mapper.evaluateEmotion();
            testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
        }else {
            testResults.push(new TestResult(number, expectedEmotions, null, 'unmappable'));   
        }
    }
    
    //Test Case 81
    number++;
    congruence = 0.5;
    utility = 0;
    affectedAgent = 'self';
    relationWithAffected = 0;
    newEvent = true;
    eventLikelihood = 1;
    causalAgent = 'none';
    adherence = 0;
    attractiveness = 0;
    expectedEmotions = [];
    scenario = new OccScenario(congruence,utility,affectedAgent,relationWithAffected,newEvent,eventLikelihood,causalAgent,adherence,attractiveness);
    if(evaluatingChecker){
        outputEmotions = scenario.evaluateScenario();
        testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
    }else {
        mapper = new Mapper(scenario);
        if(mapper.getMappable() === true){
            outputEmotions = mapper.evaluateEmotion();
            testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
        }else {
            testResults.push(new TestResult(number, expectedEmotions, null, 'unmappable'));   
        }
    }
    
    //Test Case 82
    number++;
    congruence = 0;
    utility = 0.5;
    affectedAgent = 'other';
    relationWithAffected = 0;
    newEvent = true;
    eventLikelihood = 1;
    causalAgent = 'none';
    adherence = 0;
    attractiveness = 0;
    expectedEmotions = [];
    scenario = new OccScenario(congruence,utility,affectedAgent,relationWithAffected,newEvent,eventLikelihood,causalAgent,adherence,attractiveness);
    if(evaluatingChecker){
        outputEmotions = scenario.evaluateScenario();
        testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
    }else {
        mapper = new Mapper(scenario);
        if(mapper.getMappable() === true){
            outputEmotions = mapper.evaluateEmotion();
            testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
        }else {
            testResults.push(new TestResult(number, expectedEmotions, null, 'unmappable'));   
        }
    }
    
    //Test Case 83
    number++;
    congruence = 0.5;
    utility = 0;
    affectedAgent = 'other';
    relationWithAffected = 0;
    newEvent = true;
    eventLikelihood = 1;
    causalAgent = 'none';
    adherence = 0;
    attractiveness = 0;
    expectedEmotions = [];
    scenario = new OccScenario(congruence,utility,affectedAgent,relationWithAffected,newEvent,eventLikelihood,causalAgent,adherence,attractiveness);
    if(evaluatingChecker){
        outputEmotions = scenario.evaluateScenario();
        testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
    }else {
        mapper = new Mapper(scenario);
        if(mapper.getMappable() === true){
            outputEmotions = mapper.evaluateEmotion();
            testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
        }else {
            testResults.push(new TestResult(number, expectedEmotions, null, 'unmappable'));   
        }
    }
    
    //Test Case 84
    number++;
    congruence = 0.5;
    utility = 0.5;
    affectedAgent = 'other';
    relationWithAffected = 0;
    newEvent = true;
    eventLikelihood = 1;
    causalAgent = 'none';
    adherence = 0;
    attractiveness = 0;
    expectedEmotions = [];
    scenario = new OccScenario(congruence,utility,affectedAgent,relationWithAffected,newEvent,eventLikelihood,causalAgent,adherence,attractiveness);
    if(evaluatingChecker){
        outputEmotions = scenario.evaluateScenario();
        testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
    }else {
        mapper = new Mapper(scenario);
        if(mapper.getMappable() === true){
            outputEmotions = mapper.evaluateEmotion();
            testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
        }else {
            testResults.push(new TestResult(number, expectedEmotions, null, 'unmappable'));   
        }
    }
    
    //Test Case 85
    number++;
    congruence = 0;
    utility = 0;
    affectedAgent = 'self'; //This doesn't matter
    relationWithAffected = 0;
    newEvent = true;
    eventLikelihood = 1;
    causalAgent = 'self';
    adherence = 0;
    attractiveness = 0;
    expectedEmotions = [];
    scenario = new OccScenario(congruence,utility,affectedAgent,relationWithAffected,newEvent,eventLikelihood,causalAgent,adherence,attractiveness);
    if(evaluatingChecker){
        outputEmotions = scenario.evaluateScenario();
        testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
    }else {
        mapper = new Mapper(scenario);
        if(mapper.getMappable() === true){
            outputEmotions = mapper.evaluateEmotion();
            testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
        }else {
            testResults.push(new TestResult(number, expectedEmotions, null, 'unmappable'));   
        }
    }
    
    //Test Case 86
    number++;
    congruence = 0;
    utility = 0;
    affectedAgent = 'self'; //This doesn't matter
    relationWithAffected = 0;
    newEvent = true;
    eventLikelihood = 1;
    causalAgent = 'other';
    adherence = 0;
    attractiveness = 0;
    expectedEmotions = [];
    scenario = new OccScenario(congruence,utility,affectedAgent,relationWithAffected,newEvent,eventLikelihood,causalAgent,adherence,attractiveness);
    if(evaluatingChecker){
        outputEmotions = scenario.evaluateScenario();
        testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
    }else {
        mapper = new Mapper(scenario);
        if(mapper.getMappable() === true){
            outputEmotions = mapper.evaluateEmotion();
            testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
        }else {
            testResults.push(new TestResult(number, expectedEmotions, null, 'unmappable'));   
        }
    }
    
    //Test Case 87
    number++;
    congruence = 0;
    utility = 0;
    affectedAgent = 'self'; //This doesn't mater
    relationWithAffected = 0;
    newEvent = true;
    eventLikelihood = 1;
    causalAgent = 'none';
    adherence = 0;
    attractiveness = 0;
    expectedEmotions = [];
    scenario = new OccScenario(congruence,utility,affectedAgent,relationWithAffected,newEvent,eventLikelihood,causalAgent,adherence,attractiveness);
    if(evaluatingChecker){
        outputEmotions = scenario.evaluateScenario();
        testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
    }else {
        mapper = new Mapper(scenario);
        if(mapper.getMappable() === true){
            outputEmotions = mapper.evaluateEmotion();
            testResults.push(new TestResult(number, expectedEmotions, outputEmotions, compareStringArrays(expectedEmotions,outputEmotions)));
        }else {
            testResults.push(new TestResult(number, expectedEmotions, null, 'unmappable'));   
        }
    }



    var numberOfMapped = 0;
    var numberOfPassed = 0;
    //----------------------------------------------------------------
    //Print the results
    for(var i = 0; i < testResults.length; i++){
        if(testResults[i].getTestResult() === true){
            numberOfPassed++;   
            numberOfMapped++;
        }
        if(testResults[i].getTestResult() === false){
            numberOfMapped++;
        }
        console.log("Test number: " + testResults[i].getNumber() + ", expected emotions: " + testResults[i].getExpected() + ", output: " + testResults[i].getOutput() + ", result: " + testResults[i].getTestResult());   
    }
    console.log("Total number of mapped tests: " + numberOfMapped + " of " + number);
    console.log("Total number of passed tests: " + numberOfPassed + " of " + numberOfMapped);
}

function compareStringArrays(a, b){
    //This function compares the output emotions with the expected emotions.
    var equal = true;
    if(a.length > b.length){
        //There are too few output emotions. Fail!.
        equal = false;   
    }
    if(a.length === 0 && b.length > 0){
        //If we expect zero emotion in input then we also do that in the output!
        equal = false;   
    }
    else{
        for(var i = 0; i < a.length; i++){
            var emotion = a[i];
            var found = false;
            for(var j = 0; j < b.length; j++){
                //We look for the emotion in the other array.
                if(a[i] === b[j]){
                    found = true;
                }
            }
            if(found === false){
                equal = false;   
            }
            
        }
    }
    return equal;
}