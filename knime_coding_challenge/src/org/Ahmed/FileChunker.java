package org.Ahmed;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


/**
 * Utility class for dividing a file into chunks of lines to be processed in parallel.
 * The number of chunks is determined by the number of threads specified.
 * Each chunk represents an equal consecutive subset of lines from the input file for each thread.
 */
public class FileChunker {

    public static List<List<String>> chunkFile(String fileName, int threads) throws IOException {
        List<String> allLines = Files.readAllLines(Paths.get(fileName));
        int totalLines = allLines.size();
        int chunkSize = (int) Math.ceil((double) totalLines / threads);

        if (threads > totalLines) {
            threads = totalLines;
        }

        List<List<String>> chunks = new ArrayList<>();
        for (int i = 0; i < threads; i++) {
            int start = i * chunkSize;
            int end = Math.min(start + chunkSize, totalLines);
            chunks.add(allLines.subList(start, end));
        }
        return chunks;
    }

}
