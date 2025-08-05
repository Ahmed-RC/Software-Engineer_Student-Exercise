package org.Ahmed.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.util.List;

import org.Ahmed.ArguementReader;
import org.Ahmed.FileChunker;
import org.junit.Before;
import org.junit.Test;

public class MutliThreadingTest {

    private File tempInputFile;

    @Before
    public void setUp() throws Exception {
        tempInputFile = File.createTempFile("test_input", ".txt");
        tempInputFile.deleteOnExit();

        FileWriter writer = new FileWriter(tempInputFile);
        writer.write("Hi1\n");
        writer.write("Bye1\n");
        writer.write("Hi2\n");
        writer.write("Bye2\n");
        writer.write("Hi3\n");
        writer.write("Bye3\n");
        writer.write("Hi4\n");
        writer.write("Bye4\n");
        writer.close();
    }

    @Test
    public void testChunksSizeAndNumberOfChunks() throws Exception {
        String[] args = {
            "--input", tempInputFile.getAbsolutePath(),
            "--inputtype", "string",
            "--operations", "capitalize",
            "--threads", "4",
            "--output", "out.txt"
        };

        ArguementReader reader = new ArguementReader(args);
        List<List<String>> chunks = FileChunker.chunkFile(reader.getInputFile().getPath(), reader.getThreads());
        
        assertEquals(reader.getThreads(), chunks.size());
        assertEquals(2, chunks.get(0).size());
       
    }

    @Test
    public void ifMoreChunksThanLines() throws Exception {
        String[] args = {
            "--input", tempInputFile.getAbsolutePath(),
            "--inputtype", "string",
            "--operations", "capitalize",
            "--threads", "10",
            "--output", "out.txt"
        };

        int allLines = Files.readAllLines(tempInputFile.toPath()).size();

        ArguementReader reader = new ArguementReader(args);
        List<List<String>> chunks = FileChunker.chunkFile(reader.getInputFile().getPath(), reader.getThreads());

        assertEquals(allLines, chunks.size());
        assertEquals(1, chunks.get(0).size());
    }

@Test
public void testThreadOutputWithCorrectChunkOrder() throws Exception {
    String[] args = {
        "--input", tempInputFile.getAbsolutePath(),
        "--inputtype", "string",
        "--operations", "capitalize",
        "--threads", "4",
    };

    ArguementReader reader = new ArguementReader(args);

    int totalLines = Files.readAllLines(tempInputFile.toPath()).size();
    int threads = reader.getThreads();
    int chunkSize = (int) Math.ceil((double) totalLines / threads);

    List<List<String>> chunks = new java.util.ArrayList<>();
    for (int i = 0; i < threads; i++) {
        int start = i * chunkSize;
        int end = Math.min(start + chunkSize, totalLines);
        List<String> lines = Files.readAllLines(tempInputFile.toPath()).subList(start, end);
        chunks.add(lines);
    }

    java.io.StringWriter outputCapture = new java.io.StringWriter();

    java.io.PrintStream originalOut = System.out;
    System.setOut(new java.io.PrintStream(new java.io.OutputStream() {
        public void write(int b) {
            outputCapture.write(b);
        }
    }));

    org.Ahmed.ThreadManager.processChunks(chunks, reader, new FileWriter(File.createTempFile("temp_out", ".txt")));

    System.setOut(originalOut);

    String[] lines = outputCapture.toString().split("\\R"); // split on line breaks
    assertEquals(totalLines, lines.length);

    for (String line : lines) {
        assertTrue(line.matches("\\[Thread-\\d+]\\s+[A-Z0-9]+"));
    }
}


}
