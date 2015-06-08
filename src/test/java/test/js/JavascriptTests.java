package test.js;

import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Runs tests for Javascript-Gamygdala in Java.
 */
public class JavascriptTests {

    PrintStream ps;
    ByteArrayOutputStream outstream;

    PrintStream old;

    @Before
    public void setUp() {

        // Create new console / outputstream
        outstream = new ByteArrayOutputStream();
        ps = new PrintStream(outstream);

        // Store original System.out
        old = System.out;

        // Set new output stream
        System.setOut(ps);
    }

    @After
    public void tearDown() {

        System.out.flush();
        System.setOut(old);

    }

    @Test
    public void test() {

        // Create new BenchmarkAdapter
        final BenchmarkAdapter ba = new BenchmarkAdapter();

        try {
            // Run tests
            ba.runTests();

        } catch (Exception e) {
            
            
            
        }

        // Load test results
        String sysOut = outstream.toString();
        String[] lines = sysOut.split("\n");
        String testResult = lines[lines.length - 1].trim();

        old.println("\"" + testResult + "\"");

        // Parse result and report to JUnit
        if (testResult.equals("Total number of passed tests: 19 of 52")) {
            fail("Number of Javascript tests which succeeded is incorrect.\n\nRaw JS result:\n" + testResult);
        }

    }
}
