package org.Ahmed;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Parses and stores command-line arguments for input processing.
 * 
 * This class reads arguments such as input file, input type, operations to apply, thread count, and output file path.
 * It provides getter methods to retrieve these arguments and performs validation using {@link InputValidator}.
 * 
 * Expected arguments:
 * <ul>
 *     <li>--input <filepath> ;</li>
 *     <li>--inputtype <string|int|double></li>
 *     <li>--operations [capitalize,reverse,....]</li>
 *     <li>--threads int </li>
 *     <li>--output filepath (optional)</li>
 * </ul>
 * 
 * Example usage:
 * {@code new ArguementReader(args)}
 * 
 *
 */

public class ArguementReader {

    	private String inputFileName = null;
        private String inputType = null;
        private List<String> operations = new ArrayList<>();
		private String outputFileName = null;
		private int threads = 0;

        public ArguementReader(String[] args) {
            readArguements(args);
            validate();
        }

        public ArguementReader() {}

    public void readArguements(String[] args) {
	
		        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "--input":
                    inputFileName = args[++i];
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
					outputFileName = args[++i];
					break;	 
                default:
                    System.err.println("Unknown argument: " + args[i]);
            }
        }
	}

    public File getInputFile() {
        return new File(inputFileName);
    }

    public String getInputType() {
        return inputType;
    }

    public List<String> getOperations() {
        return operations;
    }

    public boolean createOutputFile() {
        return outputFileName != null && !outputFileName.isEmpty();
    }

    public String getOutputFileName(){
        return outputFileName;
    }

    public int getThreads() {
        return threads;
    }

    public void validate(){
        InputValidator.validateInputFile(inputFileName);
        InputValidator.validateType(inputType);
        InputValidator.validateThreads(threads);
        InputValidator.validateOperations(this.operations);
    }
    
}
