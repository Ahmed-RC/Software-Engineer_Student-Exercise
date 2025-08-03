package org.Ahmed;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ThreadManager  {

    private static List<Thread> threadList = new java.util.ArrayList<>();

    private ThreadManager() {

    }

    public static void createThreads(List<List<String>> chunks, ArguementReader argumentReader, FileWriter outputFile) throws IOException {
        int threads = argumentReader.getThreads();
        InputValidator.validateThreads(threads);
        LineProcessor lineProcessor = new LineProcessor();

    for (int i = 0; i < threads; i++) {
            final List<String> chunk = chunks.get(i);
            final int numberOfThread = i + 1; // Thread numbering starts from 1
            Thread thread = new Thread(() -> {
                try {
                    lineProcessor.processLines(chunk, argumentReader, outputFile, numberOfThread);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            threadList.add(thread);
        }
    }

    public static void startThreads() {
        for(Thread thread : threadList) {
            thread.start();
        }
    }

    
}
