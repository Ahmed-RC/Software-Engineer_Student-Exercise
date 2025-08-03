package org.Ahmed;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArguementReader {

    	private String inputFileName = null;
        private String inputType = null;
        private List<String> operations = new ArrayList<>();
		private String outputFileName = null;
		private int threads = 0;

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
        return InputValidator.validateInpueFile(inputFileName);
    }

    public String getInputType() {
        InputValidator.validateType(inputType);
        return inputType;
    }

    public List<String> getOperations() {
        InputValidator.validateOperations(this.operations);
        return operations;
    }

    public boolean createOutputFile() {
        return outputFileName != null && !outputFileName.isEmpty();
    }

    public String getOutputFileName(){
        return outputFileName;
    }

    public int getThreads() {
        InputValidator.validateThreads(threads);
        return threads;
    }
    
}
