package org.Ahmed;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileWriter;

/**
 * Main class.
 * 
 * @author KNIME GmbH
 */
public class Main {
		private static boolean createOutputFile;

	public static void main(String[] args) throws IOException {

		ArguementReader argumentReader = new ArguementReader();

		argumentReader.readArguements(args);

		final FileWriter outputFile = new FileWriter(File.createTempFile("output", ".txt"));

		createOutputFile = argumentReader.createOutputFile();

		List<String> allLines = Files.readAllLines(Paths.get(argumentReader.getInputFile().getAbsolutePath()));

		int threads = argumentReader.getThreads();
		int totalLines = allLines.size();
		int chunkSize = (int) Math.ceil((double) totalLines / threads);

		List<List<String>> chunks = new ArrayList<>();

		if(threads > totalLines) {
			threads = totalLines;
		}

		for (int i = 0; i < threads; i++) {
			int start = i * chunkSize;
			int end = Math.min(start + chunkSize, totalLines);
			chunks.add(allLines.subList(start, end));
		}

		ThreadManager.createThreads(chunks,argumentReader, outputFile);

		ThreadManager.startThreads();		

		if(createOutputFile){
			outputFile.write(String.format("Processed %d lines (%d of which were unique)", //
					Statistics.getInstance().getNoOfLinesRead(), //
					Statistics.getInstance().getNoOfUniqueLines()));
		}

		if(createOutputFile)
			outputFile.close();
		
		
		// DO NOT CHANGE THE FOLLOWING LINES OF CODE
		System.out.println(String.format("Processed %d lines (%d of which were unique)", //
				Statistics.getInstance().getNoOfLinesRead(), //
				Statistics.getInstance().getNoOfUniqueLines()));
	}

}
