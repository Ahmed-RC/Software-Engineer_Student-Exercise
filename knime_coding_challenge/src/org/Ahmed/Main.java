package org.Ahmed;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;

/**
 * Main class.
 * 
 * @author KNIME GmbH
 */
public class Main {

		private static final List<String> validinputTypes = Arrays.asList("int", "string", "double");
		private static final List<String> validOperations = Arrays.asList("neg", "reverse", "capitalize");

		private static String inputRaw = null;
        private static String inputType = null;
        private static List<String> operations = new ArrayList<>();
		private static String outputRaw = null;
		private static int threads = 0;
		private static Queue<String> linesQueue = new LinkedList<>();
		private static int integerOutput = 0;
		private static double doubleOutput = 0.0;
		private static String stringOutput = "";


	private static void validateType(String inputType) {
		if(!validinputTypes.contains(inputType)) {
			throw new IllegalArgumentException("Invalid input type: " + inputType);
		}
	}

	private static void validateOperations(List<String> operations) {
		for (String operation : operations) {
			if (!validOperations.contains(operation)) {
				throw new IllegalArgumentException("Invalid operation: " + operation);
			}
		}
	}

	private static void validateThreads(int threads) {
		if (threads <= 0) {
			throw new IllegalArgumentException("Number of threads must be greater than 0");
		}
	}

	private static File validateInpueFile(String inputRaw) {
		if (inputRaw == null || inputRaw.isEmpty()) {
			throw new IllegalArgumentException("Input file cannot be null or empty");
		}
		File inputFile = new File(inputRaw);
		if (!inputFile.exists() || !inputFile.isFile()) {
			throw new IllegalArgumentException("Input file does not exist or is not a file: " + inputRaw);
		}
		return inputFile;
	}

	private static FileWriter validateOutputFile(String outPutFile) throws IOException {
		File outputFile = new File(outPutFile);
		if (!outputFile.exists()) {
			outputFile.createNewFile();
		}
		return new FileWriter(outputFile);
	}

	private static void readArguements(String[] args) {
	
		        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "--input":
                    inputRaw = args[++i];
                    break;
                case "--inputtype":
                    inputType = args[++i];
                    break;
                case "--operations":
                    operations = Arrays.asList(args[++i].split(","));
                    break;
				case "--threads":
					 threads = Integer.parseInt(args[++i]);
					break;
				case "--output":
					outputRaw = args[++i];
					break;	 
                default:
                    System.err.println("Unknown argument: " + args[i]);
            }
        }
	}

	


	public static void main(String[] args) throws IOException {

		readArguements(args);
		validateOperations(operations);
		validateType(inputType);
		validateThreads(threads);
		File inputFile = validateInpueFile(inputRaw);
		FileWriter outputFile = null;
		if(outputRaw!=null)
			outputFile = validateOutputFile(outputRaw);

		Statistics statistics = Statistics.getInstance();	


		long lineCount = Files.lines(Paths.get(inputRaw)).count();

		// for(int i =0; i< lineCount/threads;i++){
		// 	Thread thread = new Thread(() -> {
		// 		try {
		// 			processLines(inputFile, inputType, operations, outputFile);
		// 		} catch (IOException e) {
		// 			e.printStackTrace();
		// 		}
		// 	});
		// 	thread.setName(thread + "-" + i);
		// }



		try (Scanner scanner = new Scanner(inputFile)) {

			while (scanner.hasNextLine()) {
				String lin2 = scanner.skip("\\s*").nextLine();
				String line = scanner.nextLine();

				statistics.updateStatisticsWithLine(line);

				if (line.trim().isEmpty()) {

					continue; 
				}
				
				linesQueue.add(line);
			}
		}

		while(!linesQueue.isEmpty()){
			if (inputType.equals("int")) {

				integerOutput= Integer.parseInt(linesQueue.remove());
				if(operations.contains("neg")){
					integerOutput = -integerOutput;
				}

				if(outputFile != null){
					outputFile.write(integerOutput);
				}else{
					System.out.println(integerOutput);
				}
			}else if(inputType.equals("double")){
				doubleOutput = Double.parseDouble(linesQueue.remove());
					if(operations.contains("neg")){
						doubleOutput = -doubleOutput;
				}
				if(outputFile != null){
					outputFile.write(Double.toString(doubleOutput));
				}else{
					System.out.println(doubleOutput);
				}
			}else{
				stringOutput = linesQueue.remove();
				if(operations.contains("reverse")){
					stringOutput = new StringBuilder(stringOutput).reverse().toString();
				}
				if(operations.contains("capitalize")){
					stringOutput=stringOutput.toUpperCase();
				}
				if(outputFile!=null){
					outputFile.write(stringOutput+ System.lineSeparator());
				}else{
					System.out.println(stringOutput);
				}
			}

		}



		if(outputFile!=null)
		outputFile.write(String.format("Processed %d lines (%d of which were unique)", //
				Statistics.getInstance().getNoOfLinesRead(), //
				Statistics.getInstance().getNoOfUniqueLines()));

						if(outputFile != null)
		outputFile.close();
		
		
		// DO NOT CHANGE THE FOLLOWING LINES OF CODE
		System.out.println(String.format("Processed %d lines (%d of which were unique)", //
				Statistics.getInstance().getNoOfLinesRead(), //
				Statistics.getInstance().getNoOfUniqueLines()));
	}

}
