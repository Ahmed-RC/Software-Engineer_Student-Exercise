package org.Ahmed;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ThreadManager {

    /**
     * Processes chunks of input lines in parallel using a thread pool.
     * Each chunk is submitted to a separate thread which processes the lines and returns the transformed results.
     * All results are written to the output in the original order.
     *
     * If any thread fails, all remaining threads are interrupted and the exception is propagated.
     *
     * @param chunks List of line chunks, where each inner list represents the lines for one thread
     * @param argumentReader Parsed command-line arguments, including input type and operations
     * @param outputFile Writer for the final output file (may be null if output is to console)
     * @throws Exception If any thread fails during processing
     */
    public static void processChunks(List<List<String>> chunks, ArguementReader argumentReader, FileWriter outputFile) throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(chunks.size());
        List<Future<List<String>>> futures = new ArrayList<>();

        boolean createOutputFile = argumentReader.createOutputFile();

        for (int i = 0; i < chunks.size(); i++) {
            final int threadIndex = i;
            final List<String> chunk = chunks.get(i);

            Callable<List<String>> task = () -> {
                List<String> processedLines = LineProcessor.processLines(chunk, argumentReader);
                List<String> tagged = new ArrayList<>();
                for (String line : processedLines) {
                    tagged.add("[Thread-" + threadIndex + "] " + line);
                }
                return tagged;
            };

            futures.add(executor.submit(task));
        }

        executor.shutdown();

        List<List<String>> orderedResults = new ArrayList<>();

        for (int i = 0; i < futures.size(); i++) {
            try {
                orderedResults.add(futures.get(i).get());
            } catch (ExecutionException e) {
                executor.shutdownNow();
                throw new Exception("Thread execution failed", e.getCause());
            }
        }

        if (createOutputFile) {
            writeResultsToFile(orderedResults, outputFile);
        } else {
            for (List<String> result : orderedResults) {
                for (String line : result) {
                    System.out.println(line);
                }
            }
        }
    }

    private static void writeResultsToFile(List<List<String>> results, FileWriter outputFile) throws Exception {
        for (List<String> result : results) {
            for (String line : result) {
                outputFile.write(line + System.lineSeparator());
            }
        }
        outputFile.flush();
    }
}

