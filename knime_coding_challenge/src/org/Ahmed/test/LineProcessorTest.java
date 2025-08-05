package org.Ahmed.test;


import static org.junit.Assert.assertTrue;

import java.io.FileWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;

import org.Ahmed.ArguementReader;
import org.Ahmed.LineProcessor;
import org.junit.Test;


public class LineProcessorTest {

    @Test
    public void testProcessLines_integerNegation() throws Exception {
        ArguementReader dummyArgs = new ArguementReader() {
            public String getInputType() { return "int"; }
            public List<String> getOperations() { return Arrays.asList("neg"); }
            public boolean createOutputFile() { return true; }
        };

        List<String> result = LineProcessor.processLines(Arrays.asList("5", "10"), dummyArgs);
        assertTrue(result.contains("-5"));
        assertTrue(result.contains("-10"));
    }

    @Test
    public void testProcessLines_dobuleNegation() throws Exception {
        ArguementReader dummyArgs = new ArguementReader() {
            public String getInputType() { return "double"; }
            public List<String> getOperations() { return Arrays.asList("neg"); }
            public boolean createOutputFile() { return true; }
        };

        List<String> result = LineProcessor.processLines(Arrays.asList("5.2", "10.5"), dummyArgs);
        assertTrue(result.contains("-5.2"));
        assertTrue(result.contains("-10.5"));
    }

    @Test
    public void testProcessLines_stringReverse() throws Exception {
        ArguementReader dummyArgs = new ArguementReader() {
            public String getInputType() { return "string"; }
            public List<String> getOperations() { return Arrays.asList("reverse"); }
            public boolean createOutputFile() { return true; }
        };

        List<String> result = LineProcessor.processLines(Arrays.asList("Bye", "Hi","123"), dummyArgs);

        assertTrue(result.contains("eyB"));
        assertTrue(result.contains("iH"));
        assertTrue(result.contains("321"));
    }

    @Test
    public void testProcessLines_stringCapitalize() throws Exception {
        ArguementReader dummyArgs = new ArguementReader() {
            public String getInputType() { return "string"; }
            public List<String> getOperations() { return Arrays.asList("capitalize"); }
            public boolean createOutputFile() { return true; }
        };

        List<String> result = LineProcessor.processLines(Arrays.asList("bye", "hi","123"), dummyArgs);

        assertTrue(result.contains("BYE"));
        assertTrue(result.contains("HI"));
        assertTrue(result.contains("123"));
    }

    @Test
    public void testProcessLines_stringCapitaliseAndReverse() throws Exception {
        ArguementReader dummyArgs = new ArguementReader() {
            public String getInputType() { return "string"; }
            public List<String> getOperations() { return Arrays.asList("capitalize","reverse"); }
            public boolean createOutputFile() { return true; }
        };

        List<String> result = LineProcessor.processLines(Arrays.asList("Bye", "Hi","123"), dummyArgs);

        assertTrue(result.contains("EYB"));
        assertTrue(result.contains("IH"));
        assertTrue(result.contains("321"));
    }
    

    public static class FileWriterStub extends FileWriter {
        private final StringWriter sw;

        public FileWriterStub(StringWriter sw) {
            super(java.io.FileDescriptor.out);  // dummy
            this.sw = sw;
        }

        @Override
        public void write(String str) {
            sw.write(str);
        }

        @Override
        public void write(int c) {
            sw.write(c);
        }

        @Override
        public void close() {
        }
    }
}
