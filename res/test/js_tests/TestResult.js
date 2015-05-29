var TestResult = function (number, expected, output, testResult) {
    'use strict';
    this.number = number;
    this.expected = expected;
    this.output = output;
    this.testResult = testResult; //[unmappable, fail, pass]
};

TestResult.prototype.getNumber = function () {
    'use strict';
    return this.number;
};

TestResult.prototype.getExpected = function () {
    'use strict';
    return this.expected;
};

TestResult.prototype.getOutput = function () {
    'use strict';
    return this.output;
};

TestResult.prototype.getTestResult = function () {
    'use strict';
    return this.testResult;
};