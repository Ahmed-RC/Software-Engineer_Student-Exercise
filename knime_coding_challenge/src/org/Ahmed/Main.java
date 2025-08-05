package org.Ahmed;

import java.io.IOException;
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

		ArguementReader argumentReader = new ArguementReader(args);

		createOutputFile = argumentReader.createOutputFile();

		File outputFile = setupOutputFile(argumentReader);

		FileWriter writer = new FileWriter(outputFile);

		List<List<String>> chunks = FileChunker.chunkFile(argumentReader.getInputFile().getPath(), argumentReader.getThreads());

		safelyProcessChunks(chunks, argumentReader, writer);

		if(createOutputFile){
			writeStatisticsSummary(writer);
		}
		
		// DO NOT CHANGE THE FOLLOWING LINES OF CODE
		System.out.println(String.format("Processed %d lines (%d of which were unique)", //
				Statistics.getInstance().getNoOfLinesRead(), //
				Statistics.getInstance().getNoOfUniqueLines()));
	}

	private static File setupOutputFile(ArguementReader argumentReader) throws IOException {
		return argumentReader.createOutputFile()
			? new File(argumentReader.getOutputFileName())
			: File.createTempFile("output", ".txt");
	}

	private static void writeStatisticsSummary(FileWriter writer) throws IOException {
		writer.write(String.format("Processed %d lines (%d of which were unique)", //
			Statistics.getInstance().getNoOfLinesRead(), //
			Statistics.getInstance().getNoOfUniqueLines()));
		writer.close();
	}

	private static void safelyProcessChunks(List<List<String>> chunks, ArguementReader argumentReader, FileWriter writer) {
		try {
			ThreadManager.processChunks(chunks, argumentReader, writer);
		} catch (Exception e) {
			System.err.println("Error occurred during processing: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
