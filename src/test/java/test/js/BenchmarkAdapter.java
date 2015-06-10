package test.js;

import gamygdala.Engine;
import gamygdala.Gamygdala;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Adapter for the Gamygdala Test Suite in Javascript.
 */
public class BenchmarkAdapter {

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
        BenchmarkAdapter jsta = new BenchmarkAdapter();
        jsta.runTests();
    }

    /**
     * Initialize JS Test Adapter.
     */
    public BenchmarkAdapter() {
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
                Gamygdala.debug("[JSTest] Source test file not found: " + e.getMessage());
            } catch (ScriptException e) {
                Gamygdala.debug("[JSTest] Script exception in source test files: " + e.getMessage());
            }
        }
    }

    public void runTests() {
        try {
            // Benchmark
            scriptEngine.eval("benchmark()");
        } catch (ScriptException e) {
            e.printStackTrace();
        }
    }
}
