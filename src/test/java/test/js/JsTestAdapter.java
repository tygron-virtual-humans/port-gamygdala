package test.js;

import gamygdala.Engine;

import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * Adapter for the Gamygdala Test Suite in Javascript.
 */
public class JsTestAdapter {

    private ScriptEngine scriptEngine;

    private static String sourceFileLocation = "./res/test/js_tests/";
    private static String[] sourceFiles = new String[] { //
    "OccScenario.js", //
            "NewBenchmark.js", //
            "TestResult.js", //
            "Mapper.js", //
            "IntensityBenchmark.js", //
            "IntensityResult.js" //
    };

    public static void main(String[] args) {
        JsTestAdapter jsta = new JsTestAdapter();
        jsta.runTests();
    }

    /**
     * Initialize JS Test Adapter.
     */
    public JsTestAdapter() {

        // Create script engine manager
        ScriptEngineManager factory = new ScriptEngineManager();

        // Create a JavaScript engine (Nashorn)
        scriptEngine = factory.getEngineByName("nashorn");

        // Load test files
        loadScriptFiles();

    }

    /**
     * Loads Javascript Test files.
     */
    private void loadScriptFiles() {

        // Load source files
        for (String file : sourceFiles) {
            try {
                scriptEngine.eval(new FileReader(sourceFileLocation + file));
            } catch (FileNotFoundException e) {
                Engine.debug("[JSTest] Source test file not found: " + e.getMessage());
            } catch (ScriptException e) {
                Engine.debug("[JSTest] Script exception in source test files: " + e.getMessage());
            }
        }

    }

    private void adapt() throws ScriptException {

        scriptEngine.eval("var TUDelft = {}; TUDelft.Gamygdala = Packages.gamygdala.Engine;");
    }

    public void runTests() {

        try {

            // Adapt
            adapt();

            Object o = scriptEngine.get("TUDelft");
            System.out.println(o);

            System.out.println();

            // Benchmark
            scriptEngine.eval("benchmark()");

        } catch (ScriptException e) {
            e.printStackTrace();
        }

    }

}
